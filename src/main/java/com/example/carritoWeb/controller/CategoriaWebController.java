package com.example.carritoWeb.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.carritoWeb.model.Categoria;
import com.example.carritoWeb.model.Producto;
import com.example.carritoWeb.repo.ICategoriaRepo;
import com.example.carritoWeb.service.CarritoWebService;

import groovy.ui.Console;


@Controller
@RequestMapping(path = "/categorias")
public class CategoriaWebController {


	
	@Autowired
	private CarritoWebService serv;


// --------------------------------------------------------------------------------------------
// -------------------------------CATEGORIAS------------------------------
// --------------------------------------------------------------------------------------------

	@RequestMapping("/listarCategorias")
	public String listarCategorias(Model model) 
	{
		List<Categoria> list = serv.findAllCategoriasSort();
		model.addAttribute("listCategoria", list);
		model.addAttribute("content", "listasCateg"); 
		return "index";
	}
	
	@RequestMapping("/newCateg")
	public String showNewCategPage(Model model, HttpServletRequest request) {
		request.getSession().setAttribute("idCategSession", -1);
		Categoria c = new Categoria();
		model.addAttribute("categ", c);	
		model.addAttribute("content", "newCateg"); 
		return "index";
	}
	
	@RequestMapping("/addCateg/{id}")
	public String addCateg(@PathVariable(name = "id") int id) {
		Categoria c = serv.findByIdCat(id);
		serv.saveCat(c);
		return "redirect:/";		
	}	
	
	
	@RequestMapping(value = "/saveCateg", method = RequestMethod.POST)
	public String saveCateg(@ModelAttribute("categ") Categoria categ, HttpServletRequest request, @RequestParam("image") MultipartFile multipartFile, BindingResult result) throws IOException 
	{
		int cs = (int) request.getSession().getAttribute("idCategSession");
		
		if (cs!=-1)
			categ.setIdCateg(cs);
		
		 if (result.hasErrors()) {			
		        return "xxxx";
		    }
		 
		 if (multipartFile.getSize() == 0)
			 categ.setImg(null);
		 else
			 categ.setImg(multipartFile.getBytes());
		 
		serv.saveCat(categ);	
		return "redirect:/categorias/listarCategorias";
	}
	
	
	@RequestMapping("/deleteCateg/{id}")
	public String deleteCateg(@PathVariable(name = "id") int id) {
		Categoria c = serv.findByIdCat(id);
		serv.deleteCat(c);
		return "redirect:/categorias/listarCategorias";		
	}	

	@RequestMapping("/editCateg/{id}")
	public ModelAndView showEditCategPage(@PathVariable(name = "id") int id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("index");
		Categoria c = serv.findByIdCat(id);
		mav.addObject("categ", c);
		
		if (c.getImg()!=null)
			mav.addObject("imgData2",c.getImgData());
		else
			mav.addObject("imgData2",null);
		
		mav.addObject("content","editCateg");
		request.getSession().setAttribute("idCategSession", c.getIdCateg());
		return mav;
	}

}
