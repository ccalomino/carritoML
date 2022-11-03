package com.example.carritoWeb;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.carritoWeb.model.Categoria;
import com.example.carritoWeb.service.CarritoWebService;

@SpringBootTest
class CategoriaInsertarTest {

	
	private static Logger LOG = LoggerFactory.getLogger(CategoriaInsertarTest.class);


	@Autowired
	private CarritoWebService serv;
	
	@Test
	void insertarCategoria() 
	{
		LOG.info("---------TEST Insertar categoria ---------  ");
		
		Categoria c = new Categoria();
		c.setDescripcion("Test Cat");
		c.setNombre("Test Cat");
		serv.saveCat(c);
		Categoria c2 = serv.findByNombre(c.getNombre());		
		assertTrue(c2.getNombre().equals(c.getNombre()));
		
		LOG.info("---------TEST Insertar categoria FIN ---------");		
	}
	
	
}
