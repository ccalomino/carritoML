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
class ProductoEliminarTest {

	
	private static Logger LOG = LoggerFactory.getLogger(ProductoEliminarTest.class);


	@Autowired
	private CarritoWebService serv;
	
	@Test
	void eliminarProduct() 
	{
		LOG.info("---------TEST Eliminar producto ---------  ");
		
		int size1 = serv.findAllProductos().size();		
		Producto n = serv.findProductoByNombre("Test");
		serv.deleteProducto(n);		
		int size2 = serv.findAllProductos().size();
		assertTrue(size2 < size1);
		
		LOG.info("---------TEST Eliminar producto FIN ---------");		
	}
	
	
}
