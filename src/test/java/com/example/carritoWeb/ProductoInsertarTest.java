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
class ProductoInsertarTest {

	
	private static Logger LOG = LoggerFactory.getLogger(ProductoInsertarTest.class);


	@Autowired
	private CarritoWebService serv;
	
	@Test
	void insertarProduct() 
	{
		LOG.info("---------TEST Insertar producto ---------  ");
		
		Producto n = new Producto();
		n.setDescripcion("d1");
		n.setNombre("Test");
		n.setPrecio(200);
		n.setStock(100);
		serv.saveProducto(n);
		Producto n2 = serv.findProductoByNombre(n.getNombre());		
		assertTrue(n2.getNombre().equals(n.getNombre()));
		
		LOG.info("---------TEST Insertar producto FIN ---------");		
	}
	
	
}
