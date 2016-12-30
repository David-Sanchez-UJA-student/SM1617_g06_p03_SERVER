package servicio;

import java.util.LinkedList;

public abstract class Service {
	//Metodos a usar por los clientes
	/**
	 * Metodo para autenticarse
	 * @param user Nick de usuario
	 * @param pass contraseña del usuario
	 * @return true o false en funcion de si la autenticacion es correcta o no
	 */
	public abstract boolean autentica(String user,String pass);
	/**
	 * Metodo para el registro de usuarios
	 * @param user Nick de usuario
	 * @param name Nombre de usuario
	 * @param surname Apellidos del usuario
	 * @param DNI DNI del usuario
	 * @param pass contraseña del usuario
	 * @return true o false en funcion de si se ha podido crear el usuario.
	 */
	public abstract boolean registra(String user,String name,String surname,String DNI,String pass);
	/**
	 * Metodo para buscar billetes
	 * @param origen Estacion y ciudad de origen
	 * @param destino Estacion y ciudad de destino
	 * @param fecha Fecha en la que se busca el billete
	 * @return Lista de objetos Billete con los billetes para la fecha seleccionada.
	 */
	public abstract LinkedList<Billete> buscarbilletes(String origen, String destino, String fecha);
	/**
	 * Metodo para la compra del billete
	 * @param IDbillete Es un ID que se generara cuando se presenten los billetes al usuario para su seleccion.
	 * @param NumPlazas numero de plazas que va a comprar
	 * @param user Nick del usuario
	 * @return true o false en funcion de si se ha realizado la compra
	 */
	public abstract boolean comprabillete(int IDbillete, int NumPlazas, String user);
	/**
	 * Metodo para obtener el último billete que ha adquirido el usuario.
	 * @param user Nick del usuario.
	 * @return Objeto Billete con los datos del billete
	 */
	public abstract Billete ultimoBilleteComprado(String user);
	/**
	 * Metodo para obtener el historial de billetes comprados por un usuario.
	 * @param user Nick del usuario
	 * @return Lista de objetos Billete con los datos de los billetes comprados.
	 */
	public abstract LinkedList<Billete> historialBilletes(String user);
	/**
	 * Metodo para que los usuarios eliminen su cuenta
	 * @param user Nick de usuario
	 * @param pass Contraseña del usuario.
	 * @return true o false en funcion de si se ha eliminado el usuario.
	 */
	public abstract boolean eliminaUsuario(String user, String pass);
	
	//Metodos a usar por los sistemas revisores
	/**
	 * Metodo para validar/revisar los billetes 
	 * @param idBillete Identificador del billete obtenido mediante la lectura del codigo QR del billete.
	 * @return true o false en funcion de si estaba o no revisado.
	 */
	public abstract boolean validaBillete(int idBillete);
	//Metodos a usar por los administradores
	/**
	 * Metodo para la autenticacion de los administradores
	 * @param User Nick del administrador
	 * @param pass contraseña del administrador
	 * @return true o false en funcion de si se ha podido autenticar
	 */
	public abstract boolean autenticaAdmind(String User, String pass);//se podria utilizar el mismo metodo abstracto que con el cliente pero implementado para el administrador
	/**
	 * Metodo para añadir trayectos al sistema.
	 * @param origen Estacion y ciudad de origen
	 * @param destino Estacion y ciudad de destino.
	 * @param dias Dias de la semana en que estara la ruta activa.
	 * @param fechaIni Primera fecha en que la ruta se efectuara.
	 * @param fechaFin Ultima fecha en que la ruta se efectuara.
	 * @return true o false en funcion de si se ha podido añadir el trayecto al sistema.
	 */
	public abstract boolean insertaTrayectos(String origen, String destino, String[] dias,String fechaIni, String fechaFin);
	/**
	 * Metodo para cancelar un trayecto antes de la fecha de fin.
	 * @param IdTrayecto Identificador del trayecto a eliminar.
	 * @return true o false en funcion de si se ha podido eliminar el trayecto.
	 */
	public abstract boolean CancelarTrayecto(int IdTrayecto);
	
}
