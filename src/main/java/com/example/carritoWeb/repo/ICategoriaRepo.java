package com.example.carritoWeb.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.carritoWeb.dto.CategoriaDto;
import com.example.carritoWeb.model.Categoria;


public interface ICategoriaRepo extends JpaRepository<Categoria, Integer>{

	@Transactional
	Categoria findByNombre(String nombre); 
	
	@Transactional
	Categoria findByidCateg(int idCateg);
	
	@Query(value = "select * from categria order by id_prod", nativeQuery = true)
    List<CategoriaDto> findAllCategoriaDto();
	
}

