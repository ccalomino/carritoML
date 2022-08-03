package com.example.carritoWeb.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.carritoWeb.model.UsuarioML;

public interface IUsuarioMLRepo extends JpaRepository<UsuarioML, Integer>{

	@Transactional
	UsuarioML findByNombre(String nombre);
	
	@Transactional
	UsuarioML findByidUsuML(int idUsuML);
	

}
