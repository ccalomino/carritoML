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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.carritoWeb.ReportGenerator;
import com.example.carritoWeb.dto.CarritoDto;
import com.example.carritoWeb.model.Carrito;
import com.example.carritoWeb.model.Categoria;
import com.example.carritoWeb.model.Producto;
import com.example.carritoWeb.model.ProductosEnCarrito;
import com.example.carritoWeb.model.Usuario;
import com.example.carritoWeb.model.Venta;
import com.example.carritoWeb.service.CarritoWebService;

import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping(path = "/carritos")
public class CarritoWebController {

	@Autowired
	private CarritoWebService serv;


	
	


	
	
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
		
		List<Usuario> list = serv.findAllUsuarios();
		model.addAttribute("listUser", list);	
		
		
		@SuppressWarnings("unchecked")
		List<Producto> listp2 = (List<Producto>) request.getSession().getAttribute("listProd");
		
		if (listp2 == null) {
			List<Producto> listp = serv.findAllProductos();
			model.addAttribute("listProd", listp);			
		}
		else
			model.addAttribute("listProd", listp2);
		
		List<Categoria> listc = serv.findAllCategorias();
		model.addAttribute("listaCat", listc);
		
		
		
		int idComboCat = 0;
		if (request.getSession().getAttribute("idComboCat") != null)
			idComboCat = (int) request.getSession().getAttribute("idComboCat");
		
		model.addAttribute("idComboCat", idComboCat);
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
	    Producto p = serv.findProductoByidProd(id);
	    
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
		   
		    serv.saveProducto(p);
		    	
		   float total = this.obtenerTotal(carrito);  
		   request.getSession().setAttribute("totalCarrito", total);
		   this.guardarCarrito(carrito, request);
		   request.getSession().setAttribute("error", "");
	    }
	    else {
	    	request.getSession().setAttribute("error", "No hay stock de " + p.getNombre()+ " !!! ");    	
	    }
	     
	    
	    return "redirect:/carritos/newCarr/";
	}
	
	
	
	@RequestMapping("/eliminarDelCarrito/{id}")
	public String eliminarDelCarrito(Model model, HttpServletRequest request, RedirectAttributes redirectAttrs, @PathVariable(name = "id") int id) {
		ArrayList<ProductosEnCarrito> carrito = this.obtenerCarrito(request);
		// Devolver el producto al stock
		ProductosEnCarrito pc = carrito.get(id);
		Producto p = serv.findProductoByidProd(pc.getProd().getIdProd());
		p.devolucionStock(pc.getCantidad());
		serv.saveProducto(p);
		
		carrito.remove(id);
		float total = this.obtenerTotal(carrito);  
		request.getSession().setAttribute("totalCarrito", total);
		this.guardarCarrito(carrito, request); 
		return "redirect:/carritos/newCarr/";
	}
	
	
	@RequestMapping("/seleccionarUsu/{id}")
	public String agregarUsuarioAlCarrito(Model model, HttpServletRequest request, RedirectAttributes redirectAttrs, @PathVariable(name = "id") int id) {
		Usuario u = serv.findUsuarioByidUsu(id);
		//Carrito c = (Carrito) model.getAttribute("carrito");
		Carrito c = (Carrito) request.getSession().getAttribute("carrito");
		c.setUsuario_id(u);
		model.addAttribute("carrito", c);
		request.getSession().setAttribute("carrito",c);
		return "redirect:/carritos/newCarr/";
	}
	
	
	@RequestMapping("/filtrarCategoria/{id}")
	public String filtrarCategoria(Model model, HttpServletRequest request, RedirectAttributes redirectAttrs, @PathVariable(name = "id") int id) {
		
		List<Producto> listFiltrada;
		if (id==0)
			listFiltrada = serv.findAllProductos();
		else
			listFiltrada = serv.findAllProductosByCateg(id);
		
		model.addAttribute("listProd", listFiltrada);
		request.getSession().setAttribute("listProd",listFiltrada);
		request.getSession().setAttribute("idComboCat", id);

		return "redirect:/carritos/newCarr/";
	}
	
	
	
	@RequestMapping("/saveCarr")
	public String saveCarrito(Model model, HttpServletRequest request) {
		//--------------------------------------------------------------
		//Se guarda el carrito
		Carrito carr = (Carrito) request.getSession().getAttribute("carrito"); 
		@SuppressWarnings("unchecked")
		ArrayList<ProductosEnCarrito> carritoA = (ArrayList<ProductosEnCarrito>) request.getSession().getAttribute("carritoArray");
		
		if (carritoA != null)
		{
			
			Float total = (Float) request.getSession().getAttribute("totalCarrito");	
			carr.setProductosEnCarrito(carritoA);
			//-------------------------------------------------------------
			Calendar calendar = Calendar.getInstance();
			Date date =  calendar.getTime();
			//-------------------------------------------------------------
			carr.setFecha(date);
			Carrito carrSaved = serv.saveCarrito(carr);
			//--------------------------------------------------------------	
			//Se guarda los prod en carrito
			for (int i=0; i<carritoA.size();i++){
				ProductosEnCarrito pc = carritoA.get(i);
				pc.setCarr(carrSaved);
				serv.saveProductosEnCarrito(pc)	;
			}
			//--------------------------------------------------------------				
			//Se genera la venta			
			Venta v = new Venta(carr,total,date);
			serv.saveVenta(v);
			
			request.getSession().setAttribute("carrito", null);
			request.getSession().setAttribute("listProd", null);
			request.getSession().setAttribute("carritoArray", null);
			
			return "redirect:/carritos/listarCarritos/";
			
		}


		
		//Se muestra reporte-factura
		
		return "redirect:/carritos/newCarr";
		
		
	}
	
	
	@RequestMapping("/listarCarritos")
	public String listarCarritos(Model model) 
	{		
		List<CarritoDto> listCarr = serv.findAllCarritoDto();
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
		
		
		List<CarritoDto> listaProd = serv.findAllCarritoDtoById(id);
		float total1 = serv.findAllCarritoDtoByIdTotal(id);

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
