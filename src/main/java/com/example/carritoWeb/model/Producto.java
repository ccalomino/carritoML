package com.example.carritoWeb.model;

import java.util.Base64;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Type;

@Entity
@Table(name="producto")
public class Producto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProd;
	
	@Column(name="nombre",length=500)
	@NotEmpty(message="El nombre del producto es obligatorio")
	private String nombre;
	
	@Column(name="descripcion",length=1000)
	private String descripcion;
	
	@Column(name="stock")
	private int stock;
	
	@Column(name="precio")
	private float precio;
	
    @OneToMany(mappedBy="prod")
    @Column(name="idLista")
    private List<ProductosEnCarrito> productosEnCarrito;
    

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "idCateg")
    private Categoria idCateg;
    
    
    @Column(name="selected")
    private boolean selected;
    
    @Lob
    @Column(name="img", length=100000)
    //@Type(type = "org.hibernate.type.ImageType")
    private byte[] img;
	
    
    
    public Producto() {
    	
    }
    
	public Producto(String nombre, String desc, int s, float p, List<ProductosEnCarrito> l) {
		this.nombre=nombre;
		this.descripcion=desc;
		this.stock=s;
		this.precio=p;
		this.productosEnCarrito=l;
		this.selected=false;
	}
	

	public int getIdProd() {
		return idProd;
	}


	public void setIdProd(int idProd) {
		this.idProd = idProd;
	}


	public float getPrecio() {
		return precio;
	}


	public void setPrecio(float precio) {
		this.precio = precio;
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
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}


	public List<ProductosEnCarrito> getProductosEnCarrito() {
		return productosEnCarrito;
	}


	public void setProductosEnCarrito(List<ProductosEnCarrito> productosEnCarrito) {
		this.productosEnCarrito = productosEnCarrito;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public String getImgData() {
		return Base64.getMimeEncoder().encodeToString(this.img);
	}

	@Transient
    public String getImgPath() {
        if (this.img == null) return null;
         
        return "/img/" + idProd;
    }
	
	// Decrementa de a 1 en el stock
	public void decrementarStock() {
		this.stock=this.stock-1;
	}
	
	// Renueva el stock por alguna eliminacion del producto
	public void devolucionStock(int cant) {
		this.stock=this.stock+cant;
	}

	public Categoria getIdCateg() {
		return idCateg;
	}

	public void setIdCateg(Categoria idCateg) {
		this.idCateg = idCateg;
	}


	
	
}


