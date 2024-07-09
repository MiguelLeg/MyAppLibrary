package com.autozone.database.dao;

import java.sql.*;
import java.util.*;

import com.autozone.database.ConexionBasedeDatos;
import com.autozone.models.Usuario;

public class UsuarioDAO {
	public static String password;

	public static void insertar(String nombre, String apellido) {
		String sql = "CALL sp_insertar_nuevo_usuario_(?,?);";

		try (Connection connection = ConexionBasedeDatos.getInstance(password).getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			connection.setAutoCommit(false);

			statement.setString(1, nombre);
			statement.setString(2, apellido);
			statement.addBatch();

			int[] results = statement.executeBatch();
			connection.commit();

			for (int index : results) {
				if (index == PreparedStatement.EXECUTE_FAILED) {
					throw new SQLException("Error al insertar un miembro");
				}
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}

	public static <T> List<Usuario> buscar(T valor) {
		String sql = "SELECT * from tbl_miembros WHERE  miembro_id = ? OR nombre = ? OR apellido = ?";
		ResultSet resultSet;
		List<Usuario> usuarios = new ArrayList<Usuario>();

		try (Connection connection = ConexionBasedeDatos.getInstance(password).getConnection()) {
			CallableStatement statement = connection.prepareCall(sql);
			statement.setObject(1, valor);
			statement.setObject(2, valor);
			statement.setObject(3, valor);

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Usuario usuarioEncontrado = new Usuario(resultSet.getInt("miembro_id"), resultSet.getString("nombre"), resultSet.getString("apellido"));
				usuarios.add(usuarioEncontrado);
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return usuarios;
	}
	
	public static void ajustar(String nombreAjuste, String nombreActual, String apellidoAjuste) {
		String sql = "CALL sp_actualizar_usuario_(?,?,?)";

		try (Connection connection = ConexionBasedeDatos.getInstance(password).getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, nombreAjuste);
			statement.setString(2, nombreActual);
			statement.setString(3, apellidoAjuste);
			statement.executeQuery();

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
	
	public static void borrar(String nombre, String apellido) {
		String sql = "CALL sp_borrar_usuario_(?,?)";

		try (Connection connection = ConexionBasedeDatos.getInstance(password).getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, nombre);
			statement.setString(2, apellido);
			statement.executeQuery();

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}

}
