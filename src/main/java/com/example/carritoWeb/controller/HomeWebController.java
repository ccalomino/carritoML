package com.example.carritoWeb.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.carritoWeb.ReportGenerator;
import com.example.carritoWeb.dto.CarritoDto;
import com.example.carritoWeb.model.Carrito;
import com.example.carritoWeb.model.Categoria;
import com.example.carritoWeb.model.Producto;
import com.example.carritoWeb.model.ProductosEnCarrito;
import com.example.carritoWeb.model.Ubicacion;
import com.example.carritoWeb.model.Usuario;
import com.example.carritoWeb.model.Venta;
import com.example.carritoWeb.repo.ICarritoRepo;
import com.example.carritoWeb.repo.ICategoriaRepo;
import com.example.carritoWeb.repo.IProductoRepo;
import com.example.carritoWeb.repo.IProductosEnCarritoRepo;
import com.example.carritoWeb.repo.IUbicacionRepo;
import com.example.carritoWeb.repo.IUsuarioRepo;
import com.example.carritoWeb.repo.IVentaRepo;
import com.example.carritoWeb.service.CarritoWebService;

import net.sf.jasperreports.engine.JRException;

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
