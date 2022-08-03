package com.example.carritoWeb.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.carritoWeb.model.ProductosEnCarrito;

public interface IProductosEnCarritoRepo extends JpaRepository<ProductosEnCarrito, Integer>{

}
