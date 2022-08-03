package com.example.carritoWeb.dto;

public interface ProductoDto {
	
	int getIdProd();
	String getNombre();
	String getDescripcion();
	int getStock();
	float getPrecio();
	boolean isSelected();
	byte getImg();

}
