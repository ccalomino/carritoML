package com.example.carritoWeb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.carritoWeb.dto.ProductoMLDto;
import com.example.carritoWeb.model.FavoritoML;
import com.example.carritoWeb.model.ProductoML;
import com.example.carritoWeb.model.UsuarioML;
import com.example.carritoWeb.repo.IFavoritosMLRepo;
import com.example.carritoWeb.repo.IProductoMLRepo;
import com.example.carritoWeb.repo.IUsuarioMLRepo;

@Controller
public class FavoritoMLWebController {

	@Autowired
	private IFavoritosMLRepo repoFML;
	@Autowired
	private IUsuarioMLRepo repoUML;
	@Autowired
	private IProductoMLRepo repoPML;

	
	// Listar Favoritos ML
	
	@GetMapping("/listarFavoritosML")
	public String listarTodosFavoritosML(Model model, HttpServletRequest request) 
	{
		List<UsuarioML> listUsuML = repoUML.findAll();
		model.addAttribute("listUsuML", listUsuML);
		
		List<ProductoML> listProdML = repoPML.findAllProductoOrdenado();
		model.addAttribute("listProdML", listProdML);
		
		List<FavoritoML> listFavML = repoFML.findAll();		
		model.addAttribute("listFavML", listFavML);	
		
		
		FavoritoML f = (FavoritoML) request.getSession().getAttribute("favorito");
		
		if (f==null) {
			f=new FavoritoML();
		}
		else
		{
			List<ProductoMLDto> listDto = repoPML.findAllProductoConFavoritos(f.getUsuML().getIdUsuML());
			model.addAttribute("listProdMLDto",listDto);
		}
		
		model.addAttribute("favorito", f);
		request.getSession().setAttribute("favorito", f);
		
		model.addAttribute("content", "listasFavML"); 
		return "index";
	}
	
	
	@RequestMapping("/seleccionarUsuML/{id}")
	public String seleccionarUsuarioML(Model model, HttpServletRequest request, RedirectAttributes redirectAttrs, @PathVariable(name = "id") int id) {
		UsuarioML u = repoUML.findByidUsuML(id);
		
		FavoritoML f = (FavoritoML) request.getSession().getAttribute("favorito");
		f.setUsuML(u);
		
		model.addAttribute("favorito", f);

		request.getSession().setAttribute("favorito",f);
		return "redirect:/listarFavoritosML";
	}
	
	
	@RequestMapping("/agregarFavorito/{id}")
	public String agregarFavorito(Model model, HttpServletRequest request, RedirectAttributes redirectAttrs, @PathVariable(name = "id") int id) {
		
		FavoritoML f = (FavoritoML) request.getSession().getAttribute("favorito");
		int idProd = id;
		int idUsu = f.getUsuML().getIdUsuML();
		int count = repoPML.cantProdFav(idUsu, idProd);
		
		if (count == 0) {
			ProductoML p = repoPML.getById(id);
			FavoritoML fn = new FavoritoML();
			UsuarioML usu = repoUML.getById(idUsu);
			fn.setUsuML(usu);
			fn.setProdML(p);		
			repoFML.save(fn);
		}
		else
			repoFML.deleteByUsuProd(idUsu,idProd);


		
		return "redirect:/listarFavoritosML";
	}
	
}
