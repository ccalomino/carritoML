package com.example.carritoWeb.model;

import java.util.Date;

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
@Table(name="venta")
public class Venta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idVenta;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idc")
    private Carrito carr;
    
    @Column(name="valorTotal")
    private float valorTotal;
    
    @Column(name = "fecha", columnDefinition="TIMESTAMP")
    private Date fecha;

    
    public Venta(Carrito c, float v, Date f) {
    	this.carr = c;
    	this.valorTotal = v;
    	this.fecha = f;
    }
    

	public int getIdVenta() {
		return idVenta;
	}


	public void setIdVenta(int idVenta) {
		this.idVenta = idVenta;
	}


	public Carrito getCarr() {
		return carr;
	}

	public void setCarr(Carrito carr) {
		this.carr = carr;
	}

	public float getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(float valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

    
	

}