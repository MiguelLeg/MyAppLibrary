package com.autozone.models;

//clase historial para manejar informacion que se manda a llamar de la tabla de historial libros
public class Historial {

	String ISBN, titulo, autor, nombre, apellido, fecha, estatus;

	public Historial(String ISBN, String titulo, String autor, String nombre, String apellido, String fecha,
			String estatus) {
		this.ISBN = ISBN;
		this.titulo = titulo;
		this.autor = autor;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fecha = fecha;
		this.estatus = estatus;
	}

	@Override
	public String toString() {
		return "Historial [ISBN=" + ISBN + ", titulo=" + titulo + ", autor=" + autor + ", nombre=" + nombre
				+ ", apellido=" + apellido + ", fecha=" + fecha + ", estatus=" + estatus + "]";
	}

}
