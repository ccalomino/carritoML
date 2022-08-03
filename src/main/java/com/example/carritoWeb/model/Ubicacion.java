package com.example.carritoWeb.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ubicacion")
public class Ubicacion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idUbic;
	
	@Column(name="latitud")
	private String latitud;
	
	@Column(name="longitud")
	private String longitud;

	
	
	public int getIdUbic() {
		return idUbic;
	}

	public void setIdUbic(int idUbic) {
		this.idUbic = idUbic;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	
	

	
	
}
