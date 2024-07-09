package com.autozone.models;

public class Usuario {
	
	int ID;
	String nombre, apellido;
	
	public Usuario(int ID, String nombre, String apellido) {
		this.nombre = nombre;
		this.ID = ID;
		this.apellido = apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getID() {
		return ID;
	}

	@Override
	public String toString() {
		return "Usuario [ID=" + ID + ", nombre=" + nombre + ", apellido=" + apellido + "]";
	}


}
