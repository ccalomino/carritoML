package com.example.carritoWeb.service;

import org.springframework.data.domain.Page;

import com.example.carritoWeb.model.Producto;

public interface ProductoService {

    Page < Producto > findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
    
}
