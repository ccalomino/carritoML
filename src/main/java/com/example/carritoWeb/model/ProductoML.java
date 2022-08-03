package com.example.carritoWeb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="productoml")
public class ProductoML {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProdML;
	
	@Column(name="nombre",length=500)
	@NotEmpty(message="El nombre del producto es obligatorio")
	private String nombre;
	
	@Column(name="precio")
	private float precio;

	public int getIdProdML() {
		return idProdML;
	}

	public void setIdProdML(int idProdML) {
		this.idProdML = idProdML;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}
	
	
}
