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
class UsuarioModificarTest {

	
	private static Logger LOG = LoggerFactory.getLogger(UsuarioModificarTest.class);


	@Autowired
	private CarritoWebService serv;
	
	@Test
	void modificarUsuario() 
	{
		LOG.info("---------TEST Modificacion usuario ---------  ");
		Usuario u = serv.findUsuarioByNombre("Usu Nombre");
		if (u == null) {
			LOG.info("Usuario no existe");
		}
		else {
			u.setNombre("Test");;
			Usuario res = serv.saveUsuGet(u);
			
			assertTrue(res.getNombre().equals(u.getNombre()));
		}
		
		LOG.info("---------TEST Modificacion usuario FIN ---------");		
	}
	
	
}
