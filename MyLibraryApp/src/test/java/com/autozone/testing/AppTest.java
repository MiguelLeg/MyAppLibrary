package com.autozone.testing;

import junit.framework.TestCase;
import org.junit.platform.commons.annotation.Testable;
import com.autozone.models.*;

public class AppTest extends TestCase {

	
	@Testable
	public void testLibro() {
		Libro libro = new Libro("Les miserables", "Victor Hugo", "B0025", 'D');
		assertEquals("Les miserables", libro.getTitulo());
		assertEquals("Victor Hugo", libro.getAutor());
		assertEquals("B0025", libro.getISBN());
		assertEquals('D', libro.getStatus());
		assertEquals("Libro [titulo=Les miserables, autor=Victor Hugo, ISBN=B0025, status=D]", libro.toString());
	}

	@Testable
	public void testUsuario() {
		Usuario usuario = new Usuario(1, "Juan", "Perez");
		assertEquals(1, usuario.getID());
		assertEquals("Juan", usuario.getNombre());
		assertEquals("Perez", usuario.getApellido());
		assertEquals("Usuario [ID=1, nombre=Juan, apellido=Perez]", usuario.toString());
	}

	@Testable
	public void testHistorial() {
		Historial historial = new Historial("B0025", "Les miserables", "Victor Hugo", "Juan", "Perez",
				"2024-07-07 16:53:13", "Devuelto");
		assertEquals("Historial [ISBN=B0025, titulo=Les miserables, "
				+ "autor=Victor Hugo, nombre=Juan, apellido=Perez, fecha=2024-07-07 16:53:13, estatus=Devuelto]",
				historial.toString());
	}

}
