package server;

import session.SessionManager;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.TimeZone;

import servicio.Billete;
import servicio.ConexionBD;
import servicio.Message;
import session.Session;

public class Autentica implements Runnable, Protocolo {

    Socket mSocket;
    public static final String MSG_WELCOME = "Bienvenido al servidor de pruebas";
    public static final String MSG_QUIT = "Adios, hasta la vista!";
    public static final String MSG_ERRORAUT = "Usuario o clave incorrectos";

    public Autentica(Socket s) {
        mSocket = s;
    }

    @Override
    public void run() {
        String inputData = null;
        String outputData = "";
        String comando = "";
        String parametro = "";
        String tempUser = "";
        boolean salir = false;
        int estado = S_USER;
        Message msg=new Message();
        Billete bi=new Billete();
        int st=0;
        LinkedList<Billete> bils=new LinkedList<Billete>();
        if (mSocket != null) {
            try {

                // Conexi�n con la entrada y salida del socket
                DataOutputStream outputStream = new DataOutputStream(mSocket.getOutputStream());
                BufferedReader inputStream = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));

                // Se env�a el mensaje de bienvenida
                outputData = OK + SP + MSG_WELCOME + CRLF;
                outputStream.write(outputData.getBytes());
                outputStream.flush();
                
                while ((inputData = inputStream.readLine()) != null && !salir) {
                    System.out.println("SERVIDOR [Recibido]> " + inputData);
                    String fields[] = inputData.split(" ");
                    if(estado<2){
                    	
	                    
						// Se compreba si el mensaje es un comando solo o comando
	                    // con par�metro
	                    if (fields.length == 1) {// No hay espacios, debe ser solo
	                        // un comando
	                        comando = inputData;
	                        parametro = null;
	                    } else if (fields.length >= 2) {// Hay al menos un espacio
	                        // ya qe el m�todo split ha
	                        // devuelto dos o m�s
	                        // cadenas
	                        comando = fields[0];// El comando debe ser el primer
	                        // campo
	                        // Se extrae el par�metro que se considera que es todo
	                        // aquello a partir del primer espacio
	                        parametro = inputData.substring(inputData.indexOf(" "));
	                        parametro = parametro.trim();// Se limpian los espacios
	                        // adicionales
	                    }
                    }else{//si ya estamos en el estado de operacion rellenamos el objeto de la clase message asegurandonos de que son datos validos.
                    	if (fields.length == 1) {// No hay espacios, debe ser solo
	                        // un comando
	                        comando = inputData;
	                        parametro = null;
                    	}else if(fields.length>1){
                    		comando="";
                    		System.out.println(fields[0]+fields[1]+fields[2]);
                    		try{
                    			msg.setEstado(Integer.parseInt(fields[0]));
                    		}catch(NumberFormatException e){
                    			System.out.println("1");
                    			comando=QUIT;
                    			parametro=null;
                    		}
                    		
                    		msg.setHeader(fields[1]);
                    		
                    		if(!msg.getHeader().equalsIgnoreCase("ORG")&&!msg.getHeader().equalsIgnoreCase("DST")&&!msg.getHeader().equalsIgnoreCase("FCH")){
                    			System.out.println("2");
                    			comando=QUIT;
                    			parametro=null;
                    		}
                    		try{
                    			msg.setData(fields[2]);
                    		}catch(IndexOutOfBoundsException e){
                    			System.out.println("3");
                    			comando=QUIT;
                    			parametro=null;
                    		}
                    	}
                    }
                    switch (estado) {
                        case S_USER:// Estado USER
                            if (comando.equalsIgnoreCase(QUIT)) {// En todos los
                                // estados, si
                                // de salida
                                outputData = OK + SP + MSG_QUIT + CRLF;
                                salir = true;
                            } else if (comando.equalsIgnoreCase(USER) && parametro != null) {
                                tempUser = parametro;
                                outputData = OK + CRLF;
                                estado++;// Se incrementa el estado aunque a�n no se
                                // compara
                            } else {// No es un comando v�lido
                                outputData = ERROR + CRLF;
                            }
                            break;
                        case S_PASS:// Estado PASS
                            if (comando.equalsIgnoreCase(QUIT)) {
                                outputData = OK + SP + MSG_QUIT + CRLF;
                                salir = true;
                            } else if (comando.equals(PASS) && parametro != null) {
                                if (tempUser.compareTo(USERNAME) == 0 && parametro.compareTo(PASSWORD) == 0) {
                                    outputData = Session.getNewSession(tempUser,parametro);
                                    estado++;// Como el usuario y clave coinciden se
                                    // incrementa estado
                                    System.out.println("SERVIDOR> Sesión iniciada. Datos: " + outputData);
                                } else {//Autenticaci�n err�nea
                                    outputData = ERROR + SP + MSG_ERRORAUT + CRLF;
                                    estado = S_USER;
                                }
                            } else {// No es un comando v�lido
                                outputData = ERROR + CRLF;
                            }
                            break;
                        case S_OPER:// Estado OPERACION
                        	System.out.println(msg.getEstado()+msg.getHeader()+msg.getData());
                            if (comando.equalsIgnoreCase(QUIT)) {
                                outputData = OK + SP + MSG_QUIT + CRLF;
                                salir = true;
                            }else if(comando.equalsIgnoreCase("ORIGEN")){
                            	ConexionBD bd=new ConexionBD();
                            	outputData=bd.orignenes();
                            }else if(comando.equalsIgnoreCase("DESTINO")){
                            	ConexionBD bd=new ConexionBD();
                            	outputData =bd.destinos();
                            }
                            
                            //Realizamos la union del mensaje de peticion y buscamos en la BBDD, ya comprobemos arriba que era un comando valido,
                            //en caso de no serlo finaliza el servicio ya que nuestro cliente esta preprogramado para enviar los mensajes
                            //de forma correcta y si se reciben mensajes erroneos es posible que sean efectuados por un tercero no deseado.
                            else{
                            	System.out.println(msg.getEstado()+msg.getHeader()+msg.getData());
                            	switch (msg.getEstado()){
                            	
                            	case 1:
                            		if(msg.getHeader().equals("ORG")){
                            			bi.setOrigen(msg.getData());
                            			outputData= OK+CRLF;
                            			st+=1;
                            		}else if(msg.getHeader().equals("DST")){
                            			bi.setDestino(msg.getData());
                            			outputData= OK+CRLF;
                            			st+=1;
                            		}else if(msg.getHeader().equals("FCH")){
                            			bi.setFecha(msg.getData());
                            			outputData= OK+CRLF;
                            			st+=1;
                            		}
                            		if(st==3){
                            			
                            			ConexionBD bd=new ConexionBD();
                            			bils=bd.BuscarBilletes(bi);
                            			
                            			outputData="{billetes:[";
                            			for(int i=0;i<bils.size();i++){
                            				outputData+="{origen:"+bils.get(i).getOrigen()+",destino:"+bils.get(i).getDestino()+",fecha:"+bils.get(i).getFecha()+",";
                            				outputData+="hora:"+bils.get(i).getHora()+",IDbillete:"+bils.get(i).getIdbillete();
                            				outputData+="}";
                            				if(i<bils.size()-1){
                            					outputData+=",";
                            				}
                            			}
                            			outputData+="]}"+CRLF;
                            			st=0;
                            		}
                            		break;
                            		
                            	case 2:
                            		break;
                            		
                            	case 3:
                            		break;
                            	
                            	
                            	}
                            }
                            break;
                    }
                    outputStream.write(outputData.getBytes());
                    outputStream.flush();
                }
                System.out.println("SERVIDOR> Conexi�n finalizada");
                outputStream.close();
                inputStream.close();
                mSocket.close();
            } catch (SocketException se) {
                System.err.println("SERVIDOR [Finalizado]> " + se.getMessage());
            } catch (IOException ioe) {
                System.err.println("SERVIDOR [Finalizado]> " + ioe.getMessage());
            }

        }

    }

}