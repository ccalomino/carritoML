package com.example.carritoWeb;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.carritoWeb.model.Carrito;
import com.example.carritoWeb.model.Producto;
import com.example.carritoWeb.model.ProductosEnCarrito;
import com.example.carritoWeb.model.Usuario;
import com.example.carritoWeb.model.Venta;
import com.example.carritoWeb.repo.ICarritoRepo;
import com.example.carritoWeb.repo.IProductoRepo;
import com.example.carritoWeb.repo.IProductosEnCarritoRepo;
import com.example.carritoWeb.repo.IUsuarioRepo;
import com.example.carritoWeb.repo.IVentaRepo;

@SpringBootTest
class CarritoWebApplicationTests {

	
	@Autowired
	private IUsuarioRepo repUsu;	
	@Autowired
	private IProductoRepo repProd;
	@Autowired
	private ICarritoRepo repCarr;
	@Autowired
	private IProductosEnCarritoRepo repPC;
	@Autowired
	private IVentaRepo repVenta;
//	@Autowired
//	private BCryptPasswordEncoder enc;
	
	
	
	
	@Test
	public void crearUsuarioTest() {
		
		//EntityManager manager = EntityManagerUtil.getEntityManager();
		
//		Usuario u1 = new Usuario("admin",enc.encode("123"));
//		Usuario u2 = new Usuario("admin3",enc.encode("123"));
		//Usuario ret = repUsu.save(u1);
//		Usuario ret = repUsu.save(u2);
		
		
		
//		Producto p1 = new Producto("Coca Cola","Lata",10,100,null);
//		Producto p2 = new Producto("Sprite","Lata",20,200,null);
//		Producto p3 = new Producto("Lasagna Matarazzo","Paquete",30,300,null);
//		Producto p4 = new Producto("Fideos Matarazzo","Paquete",40,400,null);
//		Producto p5 = new Producto("Pan","Kg",50,500,null);	
//		
		
//		Carrito c1 = new Carrito(u1,null);
//		Carrito c2 = new Carrito(u2,null);
//		
//		
//		ProductosEnCarrito pc1 = new ProductosEnCarrito(c1,p1,1);
//		ProductosEnCarrito pc2 = new ProductosEnCarrito(c1,p2,1);
//		ProductosEnCarrito pc3 = new ProductosEnCarrito(c1,p3,1);
//		ProductosEnCarrito pc4 = new ProductosEnCarrito(c1,p4,1);
//		List<ProductosEnCarrito> listpc1 = new ArrayList<ProductosEnCarrito>();
//		listpc1.add(pc1);
//		listpc1.add(pc2);
//		listpc1.add(pc3);
//		listpc1.add(pc4);	
//		
//		
//		
//		ProductosEnCarrito pc5 = new ProductosEnCarrito(c2,p1,3);
//		ProductosEnCarrito pc6 = new ProductosEnCarrito(c2,p2,3);
//		List<ProductosEnCarrito> listpc2 = new ArrayList<ProductosEnCarrito>();
//		listpc2.add(pc5);
//		listpc2.add(pc6);
//		
//		c1.setProductosEnCarrito(listpc1);
//		c2.setProductosEnCarrito(listpc2);
//		//p1.setProductosEnCarrito(listpc2);

//		repProd.save(p1);
//		repProd.save(p2);
//		repProd.save(p3);
//		repProd.save(p4);
//		repProd.save(p5);
		
//		repCarr.save(c1);
//		repCarr.save(c2);
//		
//		repPC.save(pc1);
//		repPC.save(pc2);
//		repPC.save(pc3);
//		repPC.save(pc4);
//		repPC.save(pc5);
//		repPC.save(pc6);
//
//		@SuppressWarnings("deprecation")
//		Venta v1 = new Venta(c1,300,new Date(2021,11,4));
//		@SuppressWarnings("deprecation")
//		Venta v2 = new Venta(c2,4300,new Date(2021,11,4));
//	
//		repVenta.save(v1);
//		repVenta.save(v2);
//		
	
		//u.setId(1);
		//System.out.println(" ------------>>>>>>>>>>>> "+ enc.encode("123"));
		//u.setClave(enc.encode("123"));
		//Usuario ret = rep.save(u);
//		assertTrue(ret.getClave().equalsIgnoreCase(u2.getClave()));
	}
	
	
	@Test
	void contextLoads() {
	}

}
