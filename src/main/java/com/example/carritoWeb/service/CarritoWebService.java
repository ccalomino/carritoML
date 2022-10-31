package com.example.carritoWeb.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.carritoWeb.dto.CarritoDto;
import com.example.carritoWeb.model.Carrito;
import com.example.carritoWeb.model.Categoria;
import com.example.carritoWeb.model.Producto;
import com.example.carritoWeb.model.ProductosEnCarrito;
import com.example.carritoWeb.model.Ubicacion;
import com.example.carritoWeb.model.Usuario;
import com.example.carritoWeb.model.Venta;

@Component
public interface CarritoWebService {
	
	
	public List<Usuario> findAllUsuarios();	
	
	public Usuario findUsuarioByidUsu(int id);
	
	public void saveUsu(Usuario usu);
	
	public void deleteUsu(Usuario usu);
	
	// --------------------------------------------------------------------------------------------
	
	public void saveUbicacion(Ubicacion ub);
	
	// --------------------------------------------------------------------------------------------

	public List<Producto> findAllProductos();	
	
	public Producto findProductoByidProd(int id);
	
	public void saveProducto(Producto p);
	
	// --------------------------------------------------------------------------------------------

	public List<Categoria> findAllCategorias();
	
	public List<Categoria> findAllCategoriasSort();
	
	public Categoria findByIdCat(int id);
	
	public List<Producto> findAllProductosByCateg(int id);
	
	// --------------------------------------------------------------------------------------------
	
	public Carrito saveCarrito(Carrito c);
	
	public void saveProductosEnCarrito(ProductosEnCarrito pc);
	
	public List<CarritoDto> findAllCarritoDto();
	
	public float findAllCarritoDtoByIdTotal(int id);
	
	public List<CarritoDto> findAllCarritoDtoById(int id);
	
	public void saveCat(Categoria cat);
	
	public void deleteCat(Categoria c);
	
	// --------------------------------------------------------------------------------------------

	public void saveVenta(Venta v);
	
	
	
}
