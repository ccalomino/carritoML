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
@Table(name="favoritoml")
public class FavoritoML {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idFav;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="idUsuML")
	private UsuarioML usuML;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="idProdML")
	private ProductoML prodML;

	public int getIdFav() {
		return idFav;
	}

	public void setIdFav(int idFav) {
		this.idFav = idFav;
	}

	public UsuarioML getUsuML() {
		return usuML;
	}

	public void setUsuML(UsuarioML usuML) {
		this.usuML = usuML;
	}

	public ProductoML getProdML() {
		return prodML;
	}

	public void setProdML(ProductoML prodML) {
		this.prodML = prodML;
	}
	
	
	
	
}
