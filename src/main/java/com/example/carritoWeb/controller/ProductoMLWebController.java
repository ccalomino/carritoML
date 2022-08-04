package com.example.carritoWeb.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.example.carritoWeb.dto.ProductoMLDto;
import com.example.carritoWeb.dto.ProductoMLDto2;
import com.example.carritoWeb.model.ProductoML;
import com.example.carritoWeb.repo.IProductoMLRepo;

@Controller
public class ProductoMLWebController {

	@Autowired
	private IProductoMLRepo repoPML;
	

	// -------------------------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------------------------
	@RequestMapping("/hello")
	public String hello() {
		return "Hellooooo";
	}
	
	@RequestMapping(value="/clientHello", method=RequestMethod.GET)
	public @ResponseBody String getHelloClient() {
		String uri = "http://localhost:8098/hello";
		RestTemplate rest = new RestTemplate();
		String result = rest.getForObject(uri, String.class);
		return result;
	}
	

	// -------------------------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------------------------
	
	@GetMapping(value="/getPrecioAPI/{prod}")
	private String getPrecioAPI(@PathVariable(name = "prod") String prod) {
		final String uri = "https://api.mercadolibre.com/items/";
	    final String price="/price";
	    
	    String pp = "";
	    RestTemplate restTemplate = new RestTemplate();
	    try {
	    	pp = restTemplate.getForObject(uri+prod+price, String.class);
	    	System.out.println(pp);
	    	return (pp);
		    
		} catch (Exception e) {
			System.out.println(pp);
			return "0";
		}
	}

	// -------------------------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------------------------

	// Precio por API ML

	private float getPrecio(String prod)
	{

    	String result = this.getPrecioAPI(prod);
    	
    	if (!result.equals("0")) 
    	{
        	String split[] = result.split(",");
        	
    		if (split[1].contains("price")){
    		    	String priceA[]=split[1].split(":");
    		    	return Float.parseFloat(priceA[1]);
    		    }  		
    	}

		    
		return 0;	    
	}
	
	// -------------------------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------------------------
	
	// Listar Productos ML
	
	@GetMapping("/listarProductosML")
	public String listarProductosML(Model model) 
	{
		
		List<ProductoML> listp = repoPML.findAll();
		ProductoML p = new ProductoML();
		model.addAttribute("productoML", p);	
		model.addAttribute("listProdML", listp);	
		model.addAttribute("content", "listasProdML"); 
		return "index";
	}
	
	// Grabar Producto ML
	
	@RequestMapping(value = "/saveProdML", method = RequestMethod.POST)
	public String saveProdML(@ModelAttribute("productoML") ProductoML product) throws IOException 
	{
		float precioML = this.getPrecio(product.getNombre());
		if (precioML!=0)
		{
			product.setPrecio(precioML);
			
		}
		repoPML.save(product);	
		return "redirect:/listarProductosML";		
	}
	
	// Borrar Producto ML
	
	@RequestMapping("/deleteProdML/{id}")
	public String deleteProdML(@PathVariable(name = "id") int id) {
		ProductoML p = repoPML.findByidProdML(id);
		repoPML.delete(p);
		return "redirect:/listarProductosML";		
	}
	

	
	
	// -----------------------------------------------------------------
	// -----------------------------------------------------------------
	// Nivel 1
	// -----------------------------------------------------------------
	// -----------------------------------------------------------------
	
	@GetMapping("/pruebasML1")
	public String pruebasML1(Model model, HttpSession session) {		
		List<ProductoML> listp = repoPML.findAll();	
		model.addAttribute("listProdML", listp);	
		session.setAttribute("listProdML", listp);
		model.addAttribute("content", "challenge1ML"); 
		return "index";
		
	
	}
	
	private void maximizarList(List<ProductoML> listProdMLResult, List<ProductoML> listp, int index, float suma, float total) {
		if (index<listp.size())
		{
			if (listp.get(index).getPrecio()+suma <= total) {
				suma+=listp.get(index).getPrecio();
				listProdMLResult.add(listp.get(index));
				this.maximizarList(listProdMLResult, listp, index+1, suma, total);
			}
			else
				this.maximizarList(listProdMLResult, listp, index+1, suma, total);
		}

	}
	
	
	private float sumar(List<ProductoML> listProdMLResult) {
		
		float sum = 0;
		for (int i=0; i<listProdMLResult.size(); i++) {
			sum += listProdMLResult.get(i).getPrecio();
		}
		return sum;
		
	}
	

	@RequestMapping(value="/coupon", method=RequestMethod.GET)
	public @ResponseBody List<ProductoML> coupon(@RequestParam(value = "listp",required = false) List<ProductoML> listp, @RequestParam(value = "monto", defaultValue = "0") float monto) {
		
		List<ProductoML> listProdMLResultMax = new ArrayList<ProductoML>();
		if (listp!=null)
		{
			
			//maximizar
			
			//int index = 0;
			float maximo = 0;
			float maximaSuma = 0;
			
			for (int index = 0; index < listp.size(); index++) {
				float suma = 0;
				List<ProductoML> listProdMLResult = new ArrayList<ProductoML>();
				this.maximizarList(listProdMLResult, listp, index, suma, monto);
				float sumaParcial = this.sumar(listProdMLResult);
				if (sumaParcial > maximaSuma)
				{
					listProdMLResultMax = listProdMLResult;
					maximaSuma = sumaParcial;
					// Corto la busqueda si llego al monto
					if (maximaSuma == monto) {
						index = listp.size();
					}
				}
			}
			
		}

		
		return listProdMLResultMax;
		
//		String uri = "http://localhost:8098/hello";
//		RestTemplate rest = new RestTemplate();
//		String result = rest.getForObject(uri, String.class);
//		return result;
	}
	
	
	
	@RequestMapping(value = "/ejecutarNivel1", method = RequestMethod.POST)
	public String ejecutarNivel1(Model model, HttpSession session, @RequestParam(value = "monto", defaultValue = "0") float monto) throws IOException 
	{	
		List<ProductoML> listp = repoPML.findAll();
		List<ProductoML> listProdMLResultMaxT = this.coupon(listp,monto);
		float maximaSuma = this.sumar(listProdMLResultMaxT);
		model.addAttribute("maximaSuma",maximaSuma);
		model.addAttribute("listProdMLResult", listProdMLResultMaxT);		
		model.addAttribute("listProdML", listp);	
		model.addAttribute("content", "challenge1ML"); 
		return "index";	
	}

	// -----------------------------------------------------------------
	// -----------------------------------------------------------------
	// -----------------------------------------------------------------
	// -----------------------------------------------------------------

	
	
	// -----------------------------------------------------------------
	// -----------------------------------------------------------------
	// Nivel 2
	// -----------------------------------------------------------------
	// -----------------------------------------------------------------
	
	
		@RequestMapping(value="/coupon/stats", method=RequestMethod.GET)
		public @ResponseBody List<ProductoMLDto2> topFive(){
			
			try {
				return repoPML.findTopFive();
			}catch(Exception e) {
				return new ArrayList<ProductoMLDto2>();
			}
			
			
		}
	
	
		@GetMapping("/pruebasML2")
		public String pruebasML2(Model model, HttpSession session) {		
			List<ProductoML> listp = repoPML.findAll();	
			model.addAttribute("listProdML", listp);	
			session.setAttribute("listProdML", listp);
			model.addAttribute("content", "challenge2ML"); 
			return "index";	
		}

		
		@RequestMapping(value = "/ejecutarNivel2", method = RequestMethod.POST)
		public String ejecutarNivel2(Model model, HttpSession session) throws IOException 
		{		
			List<ProductoMLDto2> listProdMLResult = this.topFive();
			model.addAttribute("listProdMLResult", listProdMLResult);			
			model.addAttribute("content", "challenge2ML"); 
			return "index";	
		}
	
		// -----------------------------------------------------------------
		// -----------------------------------------------------------------
		// -----------------------------------------------------------------
		// -----------------------------------------------------------------	
		
		
		
		
		

		
		
}
