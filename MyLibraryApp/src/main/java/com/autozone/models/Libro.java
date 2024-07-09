package com.autozone.models;

public class Libro {
	
	private String titulo,autor,ISBN;
	private char status;
	
	public Libro(String titulo, String autor,String ISBN, char status) {
		this.titulo = titulo;
		this.autor = autor;
		this.ISBN = ISBN;
		this.status = status;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getISBN() {
		return ISBN;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Libro [titulo=" + titulo + ", autor=" + autor + ", ISBN=" + ISBN + ", status=" + status + "]";
	}
	

}
