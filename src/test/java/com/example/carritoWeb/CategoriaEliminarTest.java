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
class CategoriaEliminarTest {

	
	private static Logger LOG = LoggerFactory.getLogger(CategoriaEliminarTest.class);


	@Autowired
	private CarritoWebService serv;
	
	@Test
	void eliminarCategoria() 
	{
		LOG.info("---------TEST Eliminar categoria ---------  ");
		
		int size1 = serv.findAllCategorias().size();		
		Categoria c = serv.findByNombre("Test");
		serv.deleteCat(c);		
		int size2 = serv.findAllCategorias().size();
		assertTrue(size2 < size1);
		
		LOG.info("---------TEST Eliminar categoria FIN ---------");		
	}
	
	
}
