package com.autozone.database;

import java.sql.*;

public class ConexionBasedeDatos {

	private static ConexionBasedeDatos instance;//objeto creado para conexion a la base de datos
	private Connection connection;// variable connection habilitada
	String url = "jdbc:mysql://localhost:3306/DB_BIBLIOTECA";//url de la ubicacion de la base de datos
	private String username = "root";//user name de la base de datos
	
	//metodo para conectarse a la base de datos, requiere de una variable string que es el password a utilizar
	private ConexionBasedeDatos(String password) throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");//crea una clase para registrar una instancia del driver a usar para la comunicacion entra java y mysql
			this.connection = DriverManager.getConnection(url,username,password);//establece conexion con la base de datos
		}catch (ClassNotFoundException | SQLException exception) {
			throw new SQLException(exception.getMessage());
			//log de fallo la conexion
		}
	}
	
	//metodo que nos devuelve la conexion establecida
	public Connection getConnection() {
		return connection;
	}
	
	public static ConexionBasedeDatos getInstance(String password) throws SQLException{
		if(instance == null) {
			instance = new ConexionBasedeDatos(password);//manipulacion del objecto conexionbasededatos para establecer una conexion cada vez que se requiera
		}else if(instance.getConnection().isClosed()) {
			instance = new ConexionBasedeDatos(password);
		}
		return instance;
	}
}
