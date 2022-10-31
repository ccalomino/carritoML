package com.example.carritoWeb.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.carritoWeb.file.FileUploadUtil;
import com.example.carritoWeb.model.Categoria;
import com.example.carritoWeb.model.Producto;
import com.example.carritoWeb.repo.ICategoriaRepo;
import com.example.carritoWeb.repo.IProductoRepo;
import com.example.carritoWeb.service.ProductoService;

@Controller
@RequestMapping(path = "/productos")
public class ProductoWebController {

	@Autowired
	private IProductoRepo repoP;
	
	@Autowired
	private ICategoriaRepo repoCa;
	
    @Autowired
    private ProductoService productoService;
    
    static int PAGESIZE = 5;
	
	
		// --------------------------------------------------------------------------------------------
		// -------------------------------PRODUCTO------------------------------
		// --------------------------------------------------------------------------------------------

		@RequestMapping("/listarProductos")
		public String listarProductos(Model model) 
		{
			//List<Producto> listp = repoP.findAll();
			//List<Producto> listp = repoP.findAllProductoOrdenado();
			Page<Producto> page = productoService.findPaginated(1, PAGESIZE, "nombre", "asc");
			List<Producto> listp = page.getContent();
			
			model.addAttribute("currentPage", 1);
	        model.addAttribute("totalPages", page.getTotalPages());
	        model.addAttribute("totalItems", page.getTotalElements());

	        model.addAttribute("sortField", "nombre");
	        model.addAttribute("sortDir", "asc");
	        model.addAttribute("reverseSortDir", "desc");
				
			model.addAttribute("listProd", listp);	
			model.addAttribute("content", "listasProd"); 
			return "index";
		}
		
		
		@RequestMapping("/newProd")
		public String showNewProdPage(Model model, HttpServletRequest request) {
			request.getSession().setAttribute("idProdSession", -1);
			Producto p = new Producto();
			model.addAttribute("product", p);	
			model.addAttribute("content", "newProd"); 
			return "index";
		}

		
		@RequestMapping("/editProd/{id}")
		public ModelAndView showEditProductPage(@PathVariable(name = "id") int id, HttpServletRequest request) {
			ModelAndView mav = new ModelAndView("index");
			Producto p = repoP.findByidProd(id);
			mav.addObject("product", p);
			
			List<Categoria> list = repoCa.findAll();
			mav.addObject("listaCat", list);

			
			if (p.getImg()!=null)
				mav.addObject("imgData2",p.getImgData());
			else
				mav.addObject("imgData2",null);
			
			mav.addObject("content","editProd");
			request.getSession().setAttribute("idProdSession", p.getIdProd());
			return mav;
		}
		
		
		@RequestMapping("/deleteProd/{id}")
		public String deleteProduct(@PathVariable(name = "id") int id) {
			Producto p = repoP.findByidProd(id);
			repoP.delete(p);
			return "redirect:/productos/listarProductos";		
		}
		
		
		@RequestMapping(value = "/saveProd", method = RequestMethod.POST)
		public String saveProd(@ModelAttribute("product") Producto product, HttpServletRequest request, @RequestParam("image") MultipartFile multipartFile, BindingResult result) throws IOException 
		{
			int ps = (int) request.getSession().getAttribute("idProdSession");
			Producto product2 = new Producto();
			
			if (ps!=-1) {
				product.setIdProd(ps);
				product2 = repoP.findByidProd(ps);
			}
			
			
			
			 if (result.hasErrors()) {			
			        return "xxxx";
			    }
			 
			 if (multipartFile.getSize() != 0)
				 product.setImg(multipartFile.getBytes());
			 else
				 product.setImg(product2.getImg());

			
			repoP.save(product);	
			return "redirect:/productos/listarProductos";
		}
		
		
		@PostMapping("/saveProdImg")
	    public RedirectView saveProdImg(Producto prod,
	            @RequestParam("image") MultipartFile multipartFile) throws IOException {
	         
	        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	        prod.setImg(fileName.getBytes());
	         
	        Producto savedProd = repoP.save(prod);
	 
	        String uploadDir = "img/" + savedProd.getIdProd();
	 
	        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
	         
	        return new RedirectView("/editProd", true);
	    }
	
		@RequestMapping("/page/{pageNo}")
		public String findPaginated (@PathVariable(value="pageNo") int pageNo, @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir, Model model){
			
			
			Page<Producto> page = productoService.findPaginated(pageNo, PAGESIZE, sortField, sortDir);
			List<Producto> listProd = page.getContent();
	
		
	        model.addAttribute("currentPage", pageNo);
	        model.addAttribute("totalPages", page.getTotalPages());
	        model.addAttribute("totalItems", page.getTotalElements());

	        model.addAttribute("sortField", sortField);
	        model.addAttribute("sortDir", sortDir);
	        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
			
			model.addAttribute("listProd", listProd);	
			model.addAttribute("content", "listasProd"); 
			return "index";
			
		}
		
		// --------------------------------------------------------------------------------------------
		// --------------------------------------------------------------------------------------------
		// --------------------------------------------------------------------------------------------
		
}
