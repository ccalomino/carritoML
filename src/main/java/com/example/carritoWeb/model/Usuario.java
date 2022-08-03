package com.example.carritoWeb.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idUsu;
	@Column(name="nombre",length=500)
	private String nombre;
	@Column(name="clave",length=500)
	private String clave;
	
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
	@JoinColumn(name="idUbic")
	private Ubicacion idUbic;
	
	public Usuario() {
		this.idUbic = new Ubicacion();
		
	}
	
	public Usuario(String n, String c) {
		this.nombre=n;
		this.clave=c;
		this.idUbic = new Ubicacion();
	}
	
	public int getIdUsu() {
		return idUsu;
	}
	public void setIdUsu(int idUsu) {
		this.idUsu = idUsu;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}

	public Ubicacion getIdUbic() {
		return idUbic;
	}

	public void setIdUbic(Ubicacion idUbic) {
		this.idUbic = idUbic;
	}

	
	
}
