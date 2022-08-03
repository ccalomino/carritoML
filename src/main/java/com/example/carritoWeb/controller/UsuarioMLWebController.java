package com.example.carritoWeb.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.carritoWeb.model.Usuario;
import com.example.carritoWeb.model.UsuarioML;
import com.example.carritoWeb.repo.IUsuarioMLRepo;

@Controller
public class UsuarioMLWebController {

	@Autowired
	private IUsuarioMLRepo repoUML;

	
	// Listar Usuarios ML
	
	@GetMapping("/listarUsuariosML")
	public String listarUsuariosML(Model model) 
	{
		List<UsuarioML> listu = repoUML.findAll();
		UsuarioML u = new UsuarioML();
		model.addAttribute("userML", u);	
		model.addAttribute("listUsuML", listu);	
		model.addAttribute("content", "listasUsuML"); 
		return "index";
	}
	
	// Grabar Usuario ML
	
	@RequestMapping(value = "/saveUsuML", method = RequestMethod.POST)
	public String saveUsuML(@ModelAttribute("usuarioML") UsuarioML user) throws IOException 
	{
		repoUML.save(user);	
		return "redirect:/listarUsuariosML";		
	}
	
	
	
	//Borrar Usuario ML
	
	@RequestMapping("/deleteUsuML/{id}")
	public String deleteUserML(@PathVariable(name = "id") int id) {
		UsuarioML u = repoUML.findByidUsuML(id);
		repoUML.delete(u);
		return "redirect:/listarUsuariosML";		
	}
	
}
