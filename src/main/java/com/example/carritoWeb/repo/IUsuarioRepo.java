package com.example.carritoWeb.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.carritoWeb.model.Usuario;

public interface IUsuarioRepo extends JpaRepository<Usuario, Integer>{

	Usuario findByNombre(String nombre);
	Usuario findByidUsu(int idUsu);
}
