package servicio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;


public class ConexionBD {
	public static final String DRIVER_MYSQL = "com.mysql.jdbc.Driver"; 
	public static final String URL_MYSQL = "jdbc:mysql://localhost:3306/BusApp";
	public static int estado;
	public Connection conn;
	private static ConexionBD conexionMySql = null;
	
	public ConexionBD(){
		cargarDriver();
	}
	
	public static ConexionBD getInstancia(){
		if(conexionMySql == null){
			conexionMySql = new ConexionBD();
		}
		return conexionMySql;
	}
			
	public void cargarDriver() {
		try {
			Class.forName(DRIVER_MYSQL);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void getConexion(){
		
		try {
			conn = DriverManager.getConnection(URL_MYSQL,"root","123456");
		} catch (SQLException e) {
			e.printStackTrace();
		} 	
	}
	
	//TODO metodo para buscar los billetes
	
	public LinkedList<Billete> BuscarBilletes(Billete bil){
		LinkedList<Billete> bills=new LinkedList<Billete>();
		String sql="SELECT * FROM billetes WHERE origen= ? AND destino= ? AND fecha= ?";
		ResultSet rs=null;
		getConexion();
		try{
			PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setString(1, bil.getOrigen());
			stmt.setString(2, bil.getDestino());
			stmt.setString(3, bil.getFecha());
			rs=stmt.executeQuery();
			while(rs.next()){
				Billete bi=new Billete();
				bi.setIdbillete((rs.getInt(1)));
				bi.setHora(rs.getString(5));
				bi.setDestino(bil.getDestino());
				bi.setFecha(bil.getFecha());
				bi.setOrigen(bil.getOrigen());
				
				bills.add(bi);
			}
			stmt.close();
			conn.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return bills;
	}
	
}
