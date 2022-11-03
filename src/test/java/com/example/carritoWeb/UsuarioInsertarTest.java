package com.example.carritoWeb;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.carritoWeb.model.Usuario;
import com.example.carritoWeb.service.CarritoWebService;

@SpringBootTest
class UsuarioInsertarTest {

	
	private static Logger LOG = LoggerFactory.getLogger(UsuarioInsertarTest.class);


	@Autowired
	private CarritoWebService serv;
	
	@Test
	void insertarUsuario() 
	{
		LOG.info("---------TEST Insertar usuario ---------  ");
		
		Usuario u = new Usuario();
		u.setNombre("Usu Nombre");
		u.setIdUbic(null);
		u.setClave("123");
		serv.saveUsu(u);
		Usuario u2 = serv.findUsuarioByNombre(u.getNombre());		
		assertTrue(u2.getNombre().equals(u.getNombre()));
		
		LOG.info("---------TEST Insertar usuairo FIN ---------");		
	}
	
	
}
