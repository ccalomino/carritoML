package com.example.carritoWeb.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="productosEnCarrito")
public class ProductosEnCarrito {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProdCarr;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="idC")
	private Carrito carr;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="idP")
	private Producto prod;
	
	private int cantidad;

	
	
	public ProductosEnCarrito(Carrito c, Producto p, int cant) {
		this.carr = c;
		this.prod = p;
		this.cantidad = cant;
	}
	
	public int getIdProdCarr() {
		return idProdCarr;
	}

	public void setIdProdCarr(int idProdCarr) {
		this.idProdCarr = idProdCarr;
	}

	public Carrito getCarr() {
		return carr;
	}

	public void setCarr(Carrito carr) {
		this.carr = carr;
	}

	public Producto getProd() {
		return prod;
	}

	public void setProd(Producto prod) {
		this.prod = prod;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	 public Float getTotal() {
	        return this.getProd().getPrecio() * this.cantidad;
	}	
	 
}
