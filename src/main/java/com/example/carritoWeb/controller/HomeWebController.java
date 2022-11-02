package com.example.carritoWeb.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.carritoWeb.model.Categoria;
import com.example.carritoWeb.model.Producto;
import com.example.carritoWeb.model.Usuario;
import com.example.carritoWeb.service.CarritoWebService;

@Controller
public class HomeWebController {


	@Autowired
	private CarritoWebService serv;
	
	
	// --------------------------------------------------------------------------------------------
	// -------------------------------HOME------------------------------
	// --------------------------------------------------------------------------------------------
	
	@RequestMapping("/")
	public String viewHomePage(Model model) 
	{
		List<Usuario> list = serv.findAllUsuarios();
		model.addAttribute("listUser", list);	
		
		List<Producto> listp = serv.findAllProductos();
		model.addAttribute("listProd", listp);	
		
//		List<CarritoDto> listc = repoC.findAllCarritoDto();
//		model.addAttribute("listCarrito", listc);	
		
		List<Categoria> listcat = serv.findAllCategorias();
		model.addAttribute("listCategoria",listcat);
		
		//model.addAttribute("content", "listas"); 
		model.addAttribute("content", "principal"); 
		return "index";
	}
	
	


	
	
}
