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
class CategoriaModificarTest {

	
	private static Logger LOG = LoggerFactory.getLogger(CategoriaModificarTest.class);


	@Autowired
	private CarritoWebService serv;
	
	@Test
	void modificarCategoria() 
	{
		LOG.info("---------TEST Modificacion categoria ---------  ");
		Categoria c = serv.findByNombre("Test Cat");
		if (c == null) {
			LOG.info("Categoria no existe");
		}
		else {
			c.setNombre("Test");;
			Categoria res = serv.saveCatGet(c);
			
			assertTrue(res.getNombre().equals(c.getNombre()));
		}
		
		LOG.info("---------TEST Modificacion categoria FIN ---------");		
	}
	
	
}
