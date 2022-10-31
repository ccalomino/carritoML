package com.example.carritoWeb.dto;

import java.util.Date;

public interface CarritoDto {
	
	Date getFecha();
	int getId_carrito();
	int getId_Usu();
	int getId_Prod();
	String getCliente();
	String getProducto();
	float getPrecio();
	int getCantidad();
	int getRows();
	float getTotal();
	
	
}
