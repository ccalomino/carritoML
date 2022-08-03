package com.example.carritoWeb.comparator;

import java.util.Comparator;

import com.example.carritoWeb.model.Producto;

public class ComparatorProducto implements Comparator<Producto>{

	@Override
	public int compare(Producto o1, Producto o2) {
		if (o1.getIdProd() < o2.getIdProd())
			return 1;
		return 0;
	}

}
