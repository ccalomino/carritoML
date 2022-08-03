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

import net.sf.jasperreports.engine.JRException;

@Controller
public class CarritoWebController {

//	@Autowired
//	private BCryptPasswordEncoder enc;
//	
	@Autowired
	private IUsuarioRepo repoU;
	@Autowired
	private IProductoRepo repoP;
	@Autowired
	private ICarritoRepo repoC;
	@Autowired
	private IVentaRepo repoV;
	@Autowired
	private IProductosEnCarritoRepo repoPC;
	@Autowired
	private ICategoriaRepo repoCa;
	@Autowired
	private IUbicacionRepo repoUbic;

	
	
	// --------------------------------------------------------------------------------------------
	// -------------------------------HOME------------------------------
	// --------------------------------------------------------------------------------------------
	
	@RequestMapping("/")
	public String viewHomePage(Model model) 
	{
		List<Usuario> list = repoU.findAll();
		model.addAttribute("listUser", list);	
		
		List<Producto> listp = repoP.findAll();
		model.addAttribute("listProd", listp);	
		
		List<CarritoDto> listc = repoC.findAllCarritoDto();
		model.addAttribute("listCarrito", listc);	
		
		List<Categoria> listcat = repoCa.findAll();
		model.addAttribute("listCategoria",listcat);
		
		//model.addAttribute("content", "listas"); 
		model.addAttribute("content", "principal"); 
		return "index";
	}
	
	
	
	// --------------------------------------------------------------------------------------------
	// -------------------------------USUARIO------------------------------
	// --------------------------------------------------------------------------------------------

	@RequestMapping("/listarUsuarios")
	public String listarUsuarios(Model model) 
	{
		List<Usuario> list = repoU.findAll();
		model.addAttribute("listUser", list);
		model.addAttribute("content", "listasUsu"); 
		return "index";
	}
	
	@RequestMapping("/new")
	public String showNewUserPage(Model model) {
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
		repoUbic.save(ub);
		user.setIdUbic(ub);
		
		int us = (int) request.getSession().getAttribute("idUsuSession");
		Usuario u2 = repoU.findByidUsu(us);
		u2.setIdUbic(user.getIdUbic());

		

		repoU.save(u2);
		
		return "redirect:/";
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView showEditUserPage(@PathVariable(name = "id") int id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("index");
		Usuario u = repoU.findByidUsu(id);
		mav.addObject("idUsu",id);
		mav.addObject("user", u);	
		mav.addObject("content", "editUser"); 
		request.getSession().setAttribute("idUsuSession", u.getIdUsu());
		return mav;
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") int id) {
		Usuario u = repoU.findByidUsu(id);
		repoU.delete(u);
		return "redirect:/";		
	}
	
	

	
	
	
	// --------------------------------------------------------------------------------------------
	// -------------------------------CARRITO------------------------------
	// --------------------------------------------------------------------------------------------

	
	// Guarda los prod del carrito en session
	private void guardarCarrito(ArrayList<ProductosEnCarrito> carrito, HttpServletRequest request) {
	    request.getSession().setAttribute("carritoArray", carrito);
	}
	
	// Obtiene los prod del carrito en session
	private ArrayList<ProductosEnCarrito> obtenerCarrito(HttpServletRequest request) {
	    @SuppressWarnings("unchecked")
		ArrayList<ProductosEnCarrito> carrito = (ArrayList<ProductosEnCarrito>) request.getSession().getAttribute("carritoArray");
	    if (carrito == null) {
	        carrito = new ArrayList<>();
	    }
	    return carrito;
	}

	// Busca un prod en el carrito en session
	private ProductosEnCarrito buscarEnCarrito(ArrayList<ProductosEnCarrito> carrito, Producto p) {		
		for (ProductosEnCarrito pc : carrito) {
			if (pc.getProd().getIdProd() == p.getIdProd())
				return pc;
		}	
		return null;
	}


	
	// Crea un CARRITO nuevo, lista los prod y usuarios
	@RequestMapping("/newCarr")
	public String showNewCarrPage(Model model, HttpServletRequest request) {
		
		//Carrito c = (Carrito) model.getAttribute("carrito");
		Carrito c = (Carrito) request.getSession().getAttribute("carrito");
		
		if (c==null) {
			c=new Carrito();
			request.getSession().setAttribute("totalCarrito", 0);
			request.getSession().setAttribute("error", "");
		}
		
		model.addAttribute("carrito", c);
		request.getSession().setAttribute("carrito", c);
		
		List<Usuario> list = repoU.findAll();
		model.addAttribute("listUser", list);	
		
		List<Producto> listp = repoP.findAll(Sort.by(Sort.Direction.ASC, "idProd"));
		model.addAttribute("listProd", listp);
			
		model.addAttribute("content", "newCarr"); 
		return "index";
	}
	
	
	private float obtenerTotal(ArrayList<ProductosEnCarrito> carrito) {
		float f = 0;
		for (ProductosEnCarrito pc : carrito) {
			f = f + (pc.getProd().getPrecio() * pc.getCantidad());
		}
		return f;
	}
	
	@RequestMapping("/agregarAlCarrito/{id}")
	public String agregarAlCarrito(Model model, HttpServletRequest request, RedirectAttributes redirectAttrs, @PathVariable(name = "id") int id) {
	    ArrayList<ProductosEnCarrito> carrito = this.obtenerCarrito(request);
	    Producto p = repoP.findByidProd(id);
	    
	    if (p.getStock()!=0) 
	    {
	    	//Producto productoBuscadoPorCodigo = productosRepository.findFirstByCodigo(producto.getCodigo());
		    ProductosEnCarrito pc = this.buscarEnCarrito(carrito, p);
		    if (pc == null){
		    	pc = new ProductosEnCarrito(null,p,1);
		    	carrito.add(pc);
		    	
		    }
		    else {
		    	pc.setCantidad(pc.getCantidad()+1);
		    
		    }
		    p.decrementarStock();
		    repoP.save(p);
		    	
		   float total = this.obtenerTotal(carrito);  
		   request.getSession().setAttribute("totalCarrito", total);
		   this.guardarCarrito(carrito, request);
		   request.getSession().setAttribute("error", "");
	    }
	    else {
	    	request.getSession().setAttribute("error", "No hay stock de " + p.getNombre()+ " !!! ");    	
	    }
	     
	    
	    return "redirect:/newCarr/";
	}
	
	
	
	@RequestMapping("/eliminarDelCarrito/{id}")
	public String eliminarDelCarrito(Model model, HttpServletRequest request, RedirectAttributes redirectAttrs, @PathVariable(name = "id") int id) {
		ArrayList<ProductosEnCarrito> carrito = this.obtenerCarrito(request);
		// Devolver el producto al stock
		ProductosEnCarrito pc = carrito.get(id);
		Producto p = repoP.findByidProd(pc.getProd().getIdProd());
		p.devolucionStock(pc.getCantidad());
		repoP.save(p);
		
		carrito.remove(id);
		float total = this.obtenerTotal(carrito);  
		request.getSession().setAttribute("totalCarrito", total);
		this.guardarCarrito(carrito, request); 
		return "redirect:/newCarr/";
	}
	
	
	@RequestMapping("/seleccionarUsu/{id}")
	public String agregarUsuarioAlCarrito(Model model, HttpServletRequest request, RedirectAttributes redirectAttrs, @PathVariable(name = "id") int id) {
		Usuario u = repoU.findByidUsu(id);
		//Carrito c = (Carrito) model.getAttribute("carrito");
		Carrito c = (Carrito) request.getSession().getAttribute("carrito");
		c.setUsuario_id(u);
		model.addAttribute("carrito", c);
		request.getSession().setAttribute("carrito",c);
		return "redirect:/newCarr/";
	}
	
	@RequestMapping("/saveCarr")
	public String saveCarrito(Model model, HttpServletRequest request) {
		//--------------------------------------------------------------
		//Se guarda el carrito
		Carrito carr = (Carrito) request.getSession().getAttribute("carrito"); 
		@SuppressWarnings("unchecked")
		ArrayList<ProductosEnCarrito> carritoA = (ArrayList<ProductosEnCarrito>) request.getSession().getAttribute("carritoArray");
		Float total = (Float) request.getSession().getAttribute("totalCarrito");		
		carr.setProductosEnCarrito(carritoA);
		Carrito carrSaved = repoC.save(carr);
		//--------------------------------------------------------------	
		//Se guarda los prod en carrito
		for (int i=0; i<carritoA.size();i++){
			ProductosEnCarrito pc = carritoA.get(i);
			pc.setCarr(carrSaved);
			repoPC.save(pc);	
		}
		//--------------------------------------------------------------				
		//Se genera la venta
		Calendar calendar = Calendar.getInstance();
        Date date =  calendar.getTime();
		Venta v = new Venta(carr,total,date);
		repoV.save(v);
		
		//Se muestra reporte-factura
		
		
		
		return "redirect:/newCarr/";
	}
	
	
	@RequestMapping("/listarCarritos")
	public String listarCarritos(Model model) 
	{		
		List<CarritoDto> listCarr = repoC.findAllCarritoDto();
		//List<Carrito> listarCarrNew = new ArrayList<Carrito>(); 
		//this.migrarDeCarritoDtoACarrito(listCarr, listarCarrNew);
		model.addAttribute("counter", new Counter());
		model.addAttribute("lista", listCarr);
		model.addAttribute("content", "listasCarr"); 
		return "index";
	}
	
	
	// --------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------
	

	
	@RequestMapping("/downloadFactura/{id}")
	public ResponseEntity<ByteArrayResource> reporteProductos(Model model, @PathVariable(name = "id") int id) throws JRException, IOException 
	{	
		
		
		List<CarritoDto> listaProd = repoC.findAllCarritoDtoById(id);
		float total1 = repoC.findAllCarritoDtoByIdTotal(id);

		String reportJrxml = "factura.jrxml";
		// Generar PDF
		ReportGenerator rg = new ReportGenerator();
		rg.generatePdfReportFactura(id,reportJrxml,listaProd,total1);
		// Download la factura 	      
	    Path path = Paths.get("/home/ccalomino/Escritorio/Sist-Venta/workspace-2/carritoWeb/src/main/resources/reports/factura.pdf");
	    byte[] data = Files.readAllBytes(path);			
		ByteArrayResource inputStreamResourcePDF = new ByteArrayResource(data);	
		String fileName = "factura"+""+".pdf";		
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName).contentType(MediaType.APPLICATION_PDF)
				.contentLength(inputStreamResourcePDF.contentLength()).body(inputStreamResourcePDF);

//		
//		
//		
//		
//		
//		List<CarritoDto> listCarr = repoC.findAllCarritoDto();
//		//List<Carrito> listarCarrNew = new ArrayList<Carrito>(); 
//		//this.migrarDeCarritoDtoACarrito(listCarr, listarCarrNew);
//		model.addAttribute("counter", new Counter());
//		model.addAttribute("lista", listCarr);
//		model.addAttribute("content", "listasCarr"); 
//		return "index";
	}
	
	// --------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	//Si se encuentra en la lista
//	private boolean isCarritoInList(List<Carrito> listarCarrNew, CarritoDto cdto) {
//		for (Carrito c : listarCarrNew) {
//			if (c.getIdCarrito()==cdto.getId_carrito())
//				return true;
//		}
//		return false;
//	}
//	
//	//Agrega producto al carrito
//	private void agregarProductosAlCarrito(List<CarritoDto> listCarr, Carrito cn) {
//		
//		for (CarritoDto cdto : listCarr) {
//			//Producto p = repoP.getById(cdto.getId_Prod());
//			if (cdto.getId_carrito()==cn.getIdCarrito()) {
//				ProductosEnCarrito pc = repoPC.getById(cdto.getId_Prod());
//				cn.getProductosEnCarrito().add(pc);
//			}
//		}
//		
//	}
//	
//	//Migra datos de CarritoDto a Carrito
//	private void migrarDeCarritoDtoACarrito(List<CarritoDto> listCarr, List<Carrito> listarCarrNew) {
//		
//		for (CarritoDto cdto : listCarr) {
//			if (!isCarritoInList(listarCarrNew,cdto)) {
//				Carrito cn = new Carrito();
//				cn.setIdCarrito(cdto.getId_carrito());
//				Usuario u = repoU.findByidUsu(cdto.getId_Usu());
//				cn.setUsuario_id(u);
//				cn.setProductosEnCarrito(new ArrayList<ProductosEnCarrito>());
//				this.agregarProductosAlCarrito(listCarr,cn);
//			}
//		}
//		 
//	}
//	

	
	
	
//	@RequestMapping(value = "/modal")
//	public String export(Model model) {
//		model.addAttribute("content", "modal"); 
//		return "index";
//	}
//	

	
//	// Exportar factura
//	@RequestMapping(value = "/export", method = RequestMethod.POST)
//	 public String export(ModelAndView model, HttpServletResponse response) throws IOException, JRException, SQLException {
//	  JasperPrint jasperPrint = null;
//
//	  response.setContentType("application/x-download");
//	  response.setHeader("Content-Disposition", String.format("attachment; filename=\"factura.pdf\""));
//
//	  OutputStream out = response.getOutputStream();
//	  jasperPrint = userService.exportPdfFile();
//	  JasperExportManager.exportReportToPdfStream(jasperPrint, out);
//	  
//	  return "redirect:/listarCarritos/";
//	 }
	
	// --------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------
	
	
	
	
	
	
	
//	@GetMapping("/home")
//	public String home(ModelMap model) {
//	   model.addAttribute("content", "helloWorldView");     
//	   return ("index");
//	}
//	
//	
//	@GetMapping("/ingresar")
//	public String ingresar(@RequestParam(name="name", required=false,defaultValue="World") String name, Model model) {
//		Usuario u = new Usuario(name,name);
//		repoU.save(u);
//		model.addAttribute("name",name);
//		return ("home");
//	}
//	
//
//	@GetMapping("/listar")
//	public String listar(Model model) {
//		List<Usuario> list = repoU.findAll();
//		model.addAttribute("usuarios",list);
//		return ("home");
//	}
//	
}
