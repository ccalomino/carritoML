package com.example.carritoWeb;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.carritoWeb.model.Carrito;
import com.example.carritoWeb.model.Producto;
import com.example.carritoWeb.model.ProductosEnCarrito;
import com.example.carritoWeb.service.CarritoWebService;

@SpringBootTest
class CarritoInsertarTest {

	
	private static Logger LOG = LoggerFactory.getLogger(CarritoInsertarTest.class);


	@Autowired
	private CarritoWebService serv;
	
	@Test
	void insertarCarritoo() 
	{
		LOG.info("---------TEST Insertar carrito ---------  ");
	
		Carrito c = new Carrito();
		Producto p1 = serv.findProductoByidProd(1);
		Producto p2 = serv.findProductoByidProd(2);	
		ProductosEnCarrito pc1 = new ProductosEnCarrito(c,p1,2);
		ProductosEnCarrito pc2 = new ProductosEnCarrito(c,p2,2);
		
		ArrayList<ProductosEnCarrito> carritoA = new ArrayList<ProductosEnCarrito>();
		carritoA.add(pc1);
		carritoA.add(pc2);
		
		c.setProductosEnCarrito(carritoA);
		//-------------------------------------------------------------
		Calendar calendar = Calendar.getInstance();
		Date date =  calendar.getTime();
		c.setFecha(date);
		Carrito carrSaved = serv.saveCarrito(c);
		//--------------------------------------------------------------	
	
		if (carrSaved.getFecha() == c.getFecha())
			assertTrue(true);
		else		
			assertTrue(false);
			

		
		LOG.info("---------TEST Insertar carrito FIN ---------");		
	}
	
	
}
