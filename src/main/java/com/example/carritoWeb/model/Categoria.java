package com.example.carritoWeb.model;

import java.util.Base64;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Type;

@Entity
@Table(name="categoria")
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCateg;

	@Column(name="nombre",length=500)
	@NotEmpty(message="El nombre del producto es obligatorio")
	private String nombre;
	
	@Column(name="descripcion",length=1000)
	private String descripcion;
	
    @Lob
    @Column(name="img", length=100000)
    private byte[] img;
    
    
    
    
    

	public int getIdCateg() {
		return idCateg;
	}

	public void setIdCateg(int idCateg) {
		this.idCateg = idCateg;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public void setImg(byte[] img) {
		this.img = img;
	}

	public String getImgData() {
		return Base64.getMimeEncoder().encodeToString(this.img);
	}

	public byte[] getImg() {
		return img;
	}
	
	
	
}
