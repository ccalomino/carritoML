package com.example.carritoWeb.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="carrito")
public class Carrito {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCarrito;
	
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario_id;

    @OneToMany(mappedBy="carr", cascade = CascadeType.ALL)
    @Column(name="idLista")
    private List<ProductosEnCarrito> productosEnCarrito;

    

//  @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
//  @JoinTable(name = "lista_productos",joinColumns = {@JoinColumn(name = "idCarrito")},inverseJoinColumns = {@JoinColumn(name = "idProd")})
//  private List<Producto> productos;
    
    public Carrito() {
    	
    }
    
    public Carrito(Usuario u, List<ProductosEnCarrito> l) {
    	this.usuario_id=u;
    	this.productosEnCarrito=l;
    }
    
	public int getIdCarrito() {
		return idCarrito;
	}


	public void setIdCarrito(int idCarrito) {
		this.idCarrito = idCarrito;
	}


	public Usuario getUsuario_id() {
		return usuario_id;
	}


	public void setUsuario_id(Usuario usuario_id) {
		this.usuario_id = usuario_id;
	}

	public List<ProductosEnCarrito> getProductosEnCarrito() {
		return productosEnCarrito;
	}

	public void setProductosEnCarrito(List<ProductosEnCarrito> productosEnCarrito) {
		this.productosEnCarrito = productosEnCarrito;
	}



	
}
