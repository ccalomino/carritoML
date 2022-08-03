package com.example.carritoWeb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.carritoWeb.model.Producto;
import com.example.carritoWeb.repo.IProductoRepo;

@SpringBootTest
public class ProductApplicationTests {

	@Autowired
	private IProductoRepo repProd;
	
	
	@Test
	public void crearProductoTest(){
		
		Producto p6 = new Producto("Coca Cola","Lata",50,500,null);	
		
		try {
		
		File file = new File("/home/ccalomino/Escritorio/Sist-Venta/coca.jpeg");
		byte[] picInBytes = new byte[(int) file.length()];
		FileInputStream fileInputStream = new FileInputStream(file);
		fileInputStream.read(picInBytes);
		fileInputStream.close();
		p6.setImg(picInBytes);
		}catch(Exception e) {
			
		}
		
		repProd.save(p6);
	}
}
