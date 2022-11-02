package com.example.carritoWeb.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.carritoWeb.model.Ubicacion;
import com.example.carritoWeb.model.Usuario;
import com.example.carritoWeb.service.CarritoWebService;

@Controller
@RequestMapping(path = "/usuarios")
public class UsuarioWebController {



	@Autowired
	private CarritoWebService serv;

	
	// --------------------------------------------------------------------------------------------
	// -------------------------------USUARIO------------------------------
	// --------------------------------------------------------------------------------------------

	@RequestMapping("/listarUsuarios")
	public String listarUsuarios(Model model) 
	{
		List<Usuario> list = serv.findAllUsuarios();
		model.addAttribute("listUser", list);
		model.addAttribute("content", "listasUsu"); 
		return "index";
	}
	
	@RequestMapping("/new")
	public String showNewUserPage(Model model, HttpServletRequest request) {
		Usuario u = new Usuario();
		model.addAttribute("user", u);	
		model.addAttribute("content", "newUser"); 
		return "index";
	}
	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") Usuario user, HttpServletRequest request) {
		//user.setClave(enc.encode(user.getClave()));
		
		Ubicacion ub = new Ubicacion();
		ub.setLatitud(user.getIdUbic().getLatitud());
		ub.setLongitud(user.getIdUbic().getLongitud());
		serv.saveUbicacion(ub);
		user.setIdUbic(ub);
		
		if (request.getSession() != null && request.getSession().getAttribute("idUsuSession")!=null) {
			int us = (int) request.getSession().getAttribute("idUsuSession");
			Usuario u2 = serv.findUsuarioByidUsu(us);
			u2.setIdUbic(user.getIdUbic());
			u2.setNombre(user.getNombre());
			serv.saveUsu(u2);
		}
		else
			serv.saveUsu(user);
		
		return "redirect:/";
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView showEditUserPage(@PathVariable(name = "id") int id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("index");
		Usuario u = serv.findUsuarioByidUsu(id);
		mav.addObject("idUsu",id);
		mav.addObject("user", u);	
		mav.addObject("content", "editUser"); 
		request.getSession().setAttribute("idUsuSession", u.getIdUsu());
		return mav;
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") int id) {
		Usuario u = serv.findUsuarioByidUsu(id);
		serv.deleteUsu(u);
		return "redirect:/";		
	}
	
	

	
}
