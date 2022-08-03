package com.example.carritoWeb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usuarioml")
public class UsuarioML {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idUsuML;
	@Column(name="nombre",length=500)
	private String nombre;
	
	
	public int getIdUsuML() {
		return idUsuML;
	}
	public void setIdUsuML(int idUsuML) {
		this.idUsuML = idUsuML;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	
}
