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
class UsuarioEliminarTest {

	
	private static Logger LOG = LoggerFactory.getLogger(UsuarioEliminarTest.class);


	@Autowired
	private CarritoWebService serv;
	
	@Test
	void eliminarUsuario() 
	{
		LOG.info("---------TEST Eliminar usuario ---------  ");
		
		int size1 = serv.findAllUsuarios().size();		
		Usuario n = serv.findUsuarioByNombre("Test");
		serv.deleteUsu(n);		
		int size2 = serv.findAllUsuarios().size();
		assertTrue(size2 < size1);
		
		LOG.info("---------TEST Eliminar usuario FIN ---------");		
	}
	
	
}
