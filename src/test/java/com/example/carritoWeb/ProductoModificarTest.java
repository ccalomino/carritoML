package com.example.carritoWeb;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.carritoWeb.model.Producto;
import com.example.carritoWeb.service.CarritoWebService;

@SpringBootTest
class ProductoModificarTest {

	
	private static Logger LOG = LoggerFactory.getLogger(ProductoModificarTest.class);


	@Autowired
	private CarritoWebService serv;
	
	@Test
	void modificarProduct() 
	{
		LOG.info("---------TEST Modificacion producto ---------  ");
		Producto p = serv.findProductoByNombre("Test");
		if (p == null) {
			LOG.info("Producto no existe");
		}
		else {
			p.setNombre("Test");
			p.setStock(10);
			Producto res = serv.saveProductoGet(p);
			
			assertTrue(res.getNombre().equals(p.getNombre()));
		}
		
		LOG.info("---------TEST Modificacion producto FIN ---------");		
	}
	
	
}
