package com.example.carritoWeb.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.carritoWeb.model.Ubicacion;

public interface IUbicacionRepo extends JpaRepository<Ubicacion, Integer>{
	Ubicacion findByidUbic(int idUbic);
}
