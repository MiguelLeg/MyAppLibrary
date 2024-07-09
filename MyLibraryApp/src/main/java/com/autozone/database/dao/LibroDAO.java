package com.autozone.database.dao;

import java.sql.*;
import java.util.*;

import com.autozone.database.ConexionBasedeDatos;
import com.autozone.models.Historial;
import com.autozone.models.Libro;

public class LibroDAO {
	public static String password;
	
	public static void insertar(String titulo, String autor, String ISBN) {
		//metodo para inserrtar informacion en la tabla de libros
		String sql = "CALL sp_insertar_nuevo_libro_(?,?,?);";//comando que manda a llamar metodo dentro de la base de datos

		try (Connection connection = ConexionBasedeDatos.getInstance(password).getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {// conexion a la base de datos y se coloca dentro de un try/catch para manejar posibles excepciones

			connection.setAutoCommit(false);//se desactiva el autocommit para la recoleccion de posibles errores

			statement.setString(1, titulo);//se pasan variables para que sean ingresados en la tabla por el metodo llamado de la base de datos
			statement.setString(2, autor);
			statement.setString(3, ISBN);
			statement.addBatch();

			int[] results = statement.executeBatch();// se guarda la ejecucion del comando para verificar errores
			connection.commit();//se realiza el commit para finalizar la accion del comando a sql

			for (int index : results) {//se verifica por posibles errores en la ejecucion
				if (index == PreparedStatement.EXECUTE_FAILED) {
					throw new SQLException("Error al insertar un libro");
				}
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}

	public static <T> List<Libro> buscar(T valor) {
		//metodo para buscar informacion en la tabla
		String sql = "SELECT * from tbl_libros WHERE  libro_id = ? OR ISBN = ? OR titulo = ? OR autor = ?";
		ResultSet resultSet;//se guarda resultados de la buscada en las tabla
		List<Libro> usuarios = new ArrayList<Libro>();// lista para guardar resultados de la busqueda

		try (Connection connection = ConexionBasedeDatos.getInstance(password).getConnection()) {
			CallableStatement statement = connection.prepareCall(sql);
			statement.setObject(1, valor);
			statement.setObject(2, valor);
			statement.setObject(3, valor);
			statement.setObject(4, valor);

			resultSet = statement.executeQuery();
			//se guardan los resultados haciendo uso de un ciclo, mandando a llamar las columnas individuales de cada columna y guardandolas dentro de un objeto libro
			while (resultSet.next()) {
				Libro usuarioEncontrado = new Libro(resultSet.getString("titulo"), resultSet.getString("autor"), resultSet.getString("ISBN"), resultSet.getString("estatus").charAt(0));
				usuarios.add(usuarioEncontrado);
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return usuarios;// se devuelve lista para que sea iterada en le menu para que se muestre
	}
	
	public static void ajustar(String titulo, String tituloAjustar, String autor, String ISBN) {
		//metodo para ajustar informacion en la tabla
		String sql = "CALL sp_actualizar_libro_(?,?,?,?)";

		try (Connection connection = ConexionBasedeDatos.getInstance(password).getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, titulo);
			statement.setString(2, autor);
			statement.setString(3, ISBN);
			statement.setString(4, tituloAjustar);
			statement.executeQuery();

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
	
	public static void borrar(String titulo, String autor, String ISBN) {
		//metodo para borrar informacion en la tabla
		String sql = "CALL sp_borrar_libro_(?,?,?)";

		try (Connection connection = ConexionBasedeDatos.getInstance(password).getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, titulo);
			statement.setString(2, autor);
			statement.setString(3, ISBN);
			statement.executeQuery();

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
	
	public static void prestamo(String ISBN, String miembroID){
		//metodo para registrar prestamo modificando informacion en la tabla
		String sql = "CALL sp_prestamo_libro_(?,?)";

		try (Connection connection = ConexionBasedeDatos.getInstance(password).getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, ISBN);
			statement.setString(2, miembroID);
			statement.executeQuery();

		}catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
	
	public static void devolucion(String ISBN, String miembroID){
		//metodo para registrar devolucion modificando informacion en la tabla
		String sql = "CALL sp_devolucion_libro_(?,?)";

		try (Connection connection = ConexionBasedeDatos.getInstance(password).getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, ISBN);
			statement.setString(2, miembroID);
			statement.executeQuery();

		}catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
	public static List<Historial> consulta(String miembroID){
		//metodo para buscar informacion en la tabla historial libros
		String sql = "SELECT * from tbl_historial_libros WHERE  miembro_id = ?";
		ResultSet resultSet;
		List<Historial> historial = new ArrayList<Historial>();

		try (Connection connection = ConexionBasedeDatos.getInstance(password).getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, miembroID);
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				Historial usuarioEncontrado = new Historial(resultSet.getString("ISBN"), resultSet.getString("titulo"), resultSet.getString("autor"), 
						resultSet.getString("nombre"), resultSet.getString("apellido"), resultSet.getString("fecha"), resultSet.getString("estatus"));
				historial.add(usuarioEncontrado);
			}

		}catch (SQLException exception) {
			exception.printStackTrace();
		}
		return historial;
	}

}
