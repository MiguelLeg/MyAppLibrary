package com.principal;

import java.sql.SQLException;
import java.util.*;

import com.autozone.database.ConexionBasedeDatos;
import com.autozone.database.dao.*;

public class PrincipalMenu {

	public static void main(String[] args) {
		String password = "";
		boolean menu = true;
		while (password.isBlank()) {// se realiza ciclo hasta que se ingrese la clave correcta
			Scanner input = new Scanner(System.in);
			password = ((password.isBlank()) ? ingresePassword(input, password) : "");// verificacion del password a
																						// usar
		}

		while (menu) {
			try {
				Scanner input = new Scanner(System.in);// se habilita scanner para registrar los comandos del usuario
				System.out.println("Bienvenido, elige una categoria:");
				System.out.println(" 1. Prestamos \n 2. Libros \n 3. Usuarios \n 4. Salir");// opciones del menu
				int opcion = input.nextInt();
				// se manda a llamar el metodo donde se encuentra un menu dedicado para cada una
				// de las opciones
				switch (opcion) {
				case 1:
					PrestamodeLibros(input);// al llamar un metodo de un menu se pasa la variable del scanner del
											// usuario
					break;
				case 2:
					ManejodeLibros(input);
					break;
				case 3:
					ManejodeUsuarios(input);
					break;
				case 4:
					menu = false;
					input.close();// se cierra el scanner
					break;
				default:
					System.out.println("Favor de ingresar una opcion valida \n");
					break;
				}
			} catch (NoSuchElementException exception) {
				System.out.println("Favor de ingresar una opcion valida \n"); // manejo de excepciones en caso de se
																				// ingrese algun valor no deseado
			}

		}

	}

	public static String ingresePassword(Scanner input, String password) {
		try {
			System.out.println("Favor de ingresar contrasena:");
			password = input.next();
			ConexionBasedeDatos.getInstance(password);// se verifica si tenemos conexion con la base de datos
			LibroDAO.password = password;
			UsuarioDAO.password = password;
		} catch (SQLException exception) {
			// si no tenemos una conexion con la base de datos, arrojamos el stacktrace del
			// error y cambiamos a falso la variable del menu para que no se inicialice
			exception.getStackTrace();
			password = "";
			System.out.println("No se pudo establecer la conexion a la base de datos");
			System.out.println(exception.getMessage());
		} catch (NoSuchElementException exception) {
			System.out.println("password no valido \n"); // manejo de excepciones en caso de se ingrese algun valor no
															// deseado
		}
		return password;
	}

	// Los metodos de los menus realizan practicamente la mismas acciones, solo
	// recolectan datos del usuario
	// y mandan a llamar los metodos de las clases DAO para que interactuen con la
	// base de datos
	public static void PrestamodeLibros(Scanner input) {
		try {
			System.out.println("Elige una opcion:");
			System.out.println(" 1. Registrar prestamo \n 2. Registrar Devolucion \n 3. Historial de miembros \n ");
			int opcion = input.nextInt();
			String titulo, autor, ISBN, miembroID;// variables donde se guardan inputs del usuario
			input.nextLine();
			switch (opcion) {
			case 1:
				System.out.println("Ingrese el ISBN del libro:");
				ISBN = input.next();
				System.out.println("Ingrese el ID del miembro:");
				miembroID = input.next();
				LibroDAO.prestamo(ISBN, miembroID);// se manda llamar metodo del DAO para pasarle los datos que se van a
													// ingresar/borrar/modificar en la tabla
				break;
			case 2:
				System.out.println("Ingrese el ISBN del libro:");
				ISBN = input.next();
				System.out.println("Ingrese el ID del miembro:");
				miembroID = input.next();
				LibroDAO.devolucion(ISBN, miembroID);
				break;
			case 3:
				System.out.println("Ingrese el ID del miembro:");
				miembroID = input.next();
				// se manda a llamar el metodo dandole los parametros recolectados del usuario e
				// iterando la lista que nos arroja para immprimir los resultados en pantalla
				LibroDAO.consulta(miembroID).stream().forEach(System.out::println);
				break;
			case 4:
				System.out.println("Ingrese el titulo del libro:\n(Favor de dar doble enter)");
				titulo = input.nextLine();
				input.nextLine();
				System.out.println("Ingrese el autor del libro:\n(Favor de dar doble enter)");
				autor = input.nextLine();
				input.nextLine();
				System.out.println("Ingrese el ISBN del libro:\n(Favor de dar doble enter)");
				ISBN = input.nextLine();
				input.nextLine();
				LibroDAO.borrar(titulo, autor, ISBN);
				break;
			case 5:
				break;
			default:
				System.out.println("Favor de ingresar una opcion valida \n");
				break;
			}
		} catch (NoSuchElementException exception) {
			System.out.println("Favor de ingresar una opcion valida \n");
		}
	}

	public static void ManejodeLibros(Scanner input) {
		try {
			System.out.println("Elige una opcion:");
			System.out.println(
					" 1. Buscar un libro \n 2. Modificar un libro \n 3. Registrar un nuevo libro \n 4. Borrar un libro \n 5. Salir \n");
			int opcion = input.nextInt();
			String titulo, autor, ISBN;
			String valor = new String();
			input.nextLine();
			switch (opcion) {
			case 1:
				System.out.println("Ingrese titulo, autor o ISBN del libro:\n(Favor de dar doble enter)");
				valor = input.nextLine();
				input.nextLine();
				LibroDAO.buscar(valor).stream().forEach(System.out::println);
				System.out.println("fui llamado");
				break;
			case 2:
				System.out.println("Ingrese el titulo del libro a modificar:\n(Favor de dar doble enter)");
				titulo = input.nextLine();
				input.nextLine();
				System.out.println("Ingrese nuevo titulo del libro:\n(Favor de dar doble enter)");
				String tituloAjuste = input.nextLine();
				input.nextLine();
				System.out.println("Ingrese nuevo autor del libro:\n(Favor de dar doble enter)");
				autor = input.nextLine();
				input.nextLine();
				System.out.println("Ingrese nuevo ISBN del libro:\n(Favor de dar doble enter)");
				ISBN = input.nextLine();
				input.nextLine();
				LibroDAO.ajustar(titulo, tituloAjuste, autor, ISBN);
				break;
			case 3:
				System.out.println("Ingrese el titulo del libro:\n(Favor de dar doble enter)");
				titulo = input.nextLine();
				input.nextLine();
				System.out.println("Ingrese el autor del libro:\n(Favor de dar doble enter)");
				autor = input.nextLine();
				input.nextLine();
				System.out.println("Ingrese el ISBN del libro:\n(Favor de dar doble enter)");
				ISBN = input.nextLine();
				input.nextLine();
				LibroDAO.insertar(titulo, autor, ISBN);
				break;
			case 4:
				System.out.println("Ingrese el titulo del libro:\n(Favor de dar doble enter)");
				titulo = input.nextLine();
				input.nextLine();
				System.out.println("Ingrese el autor del libro:\n(Favor de dar doble enter)");
				autor = input.nextLine();
				input.nextLine();
				System.out.println("Ingrese el ISBN del libro:\n(Favor de dar doble enter)");
				ISBN = input.nextLine();
				input.nextLine();
				LibroDAO.borrar(titulo, autor, ISBN);
				break;
			case 5:
				break;
			default:
				System.out.println("Favor de ingresar una opcion valida \n");
				break;
			}
		} catch (NoSuchElementException exception) {
			System.out.println("Favor de ingresar una opcion valida \n");
		}
		System.out.println("accion realizada con exito");
	}

	public static void ManejodeUsuarios(Scanner input) {
		try {
			System.out.println("Elige una opcion:");
			System.out.println(
					" 1. Buscar un miembro \n 2. Modificar un miembro \n 3. Registrar un nuevo miembro \n 4. Borrar un miembro \n 5. Salir \n");
			int opcion = input.nextInt();
			String valor = new String();
			switch (opcion) {
			case 1:
				System.out.println("Ingrese nombre, apellido o ID del miembro:");
				valor = input.next();
				UsuarioDAO.buscar(valor).stream().forEach(System.out::println);
				break;
			case 2:
				System.out.println("Ingrese nombre del miembro a modificar:");
				valor = input.next();
				System.out.println("Ingrese nuevo nombre:");
				String AjusteNombre = input.next();
				System.out.println("Ingrese nuevo apellido:");
				String AjusteApellido = input.next();
				UsuarioDAO.ajustar(AjusteNombre, valor, AjusteApellido);
				break;
			case 3:
				System.out.println("Ingrese el nombre:");
				valor = input.next();
				System.out.println("Ingrese el apellido:");
				UsuarioDAO.insertar(valor, input.next());
				break;
			case 4:
				System.out.println("Ingrese el nombre:");
				String nombre = input.next();
				System.out.println("Ingrese el apellido:");
				String apellido = input.next();
				UsuarioDAO.borrar(nombre, apellido);
				break;
			case 5:
				break;
			default:
				System.out.println("Favor de ingresar una opcion valida \n");
				break;
			}
		} catch (NoSuchElementException exception) {
			System.out.println("Favor de ingresar una opcion valida \n");
		}
	}

}
