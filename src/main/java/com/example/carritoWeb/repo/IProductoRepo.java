package com.example.carritoWeb.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.carritoWeb.dto.ProductoDto;
import com.example.carritoWeb.model.Producto;

public interface IProductoRepo extends JpaRepository<Producto, Integer>{

	@Transactional
	Producto findByNombre(String nombre); 
	
	@Transactional
	Producto findByidProd(int idProd);
	
	@Query(value = "select * from producto order by id_prod", nativeQuery = true)
    List<ProductoDto> findAllProductoDto();
	
	@Query(value = "select * from producto order by nombre", nativeQuery = true)
    List<Producto> findAllProductoOrdenado();
}
