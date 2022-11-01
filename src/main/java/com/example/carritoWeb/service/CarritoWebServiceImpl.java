package com.example.carritoWeb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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


@Service("carritoWebServiceImpl")	
public class CarritoWebServiceImpl implements CarritoWebService{
	
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
	// --------------------------------------------------------------------------------------------
	
	@Override
	public List<Usuario> findAllUsuarios() {
		List<Usuario> list = repoU.findAll();
		return list;
	}
	
	@Override
	public Usuario findUsuarioByidUsu(int id) {
		return repoU.findByidUsu(id);
	}	
	
	@Override
	public void saveUsu(Usuario usu) {
		repoU.save(usu);
	}	

	
	@Override
	public void deleteUsu(Usuario usu) {
		repoU.delete(usu);
	}	
	
	// --------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------

	@Override
	public void saveUbicacion(Ubicacion ub) {
		repoUbic.save(ub);
	}		
	// --------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------
	
	@Override
	public List<Producto> findAllProductos() {
		List<Producto> list = repoP.findAll(Sort.by(Sort.Direction.ASC, "idProd"));
		return list;
	}

	@Override
	public Producto findProductoByidProd(int id) {
		return repoP.findByidProd(id);
	}

	@Override
	public void saveProducto(Producto p) {
		repoP.save(p);
	}
	
	@Override
	public Producto saveProductoGet(Producto p) {
		return repoP.save(p);
	}

	@Override
	public List<Producto> findAllProductosByCateg(int id) {
		List<Producto> list = repoP.findAllProductoByIdCategoria(id);
		return list;
	}	
	
	@Override
	public void deleteProducto(Producto p) {
		repoP.delete(p);
	}

	// --------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------

	@Override
	public List<Categoria> findAllCategorias() {
		List<Categoria> list = repoCa.findAll();
		return list;
	}
	

	@Override
	public List<Categoria> findAllCategoriasSort() {
		List<Categoria> list = repoCa.findAll(Sort.by(Sort.Direction.ASC, "idCateg"));
		return list;
	}

	@Override
	public Categoria findByIdCat(int id) {
		return repoCa.findByidCateg(id);
	}
	
	@Override
	public void saveCat(Categoria cat) {
		repoCa.save(cat);
	}

	@Override
	public void deleteCat(Categoria c) {
		repoCa.delete(c);
	}

	// --------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------

	@Override
	public List<CarritoDto> findAllCarritoDto() {
		List<CarritoDto> list = repoC.findAllCarritoDto();
		return list;
	}	
	
	
	@Override
	public float findAllCarritoDtoByIdTotal(int id) {		
		return repoC.findAllCarritoDtoByIdTotal(id);
	}	
	
	@Override
	public List<CarritoDto> findAllCarritoDtoById(int id) {		
		List<CarritoDto> list = repoC.findAllCarritoDtoById(id);
		return list;
	}	
	

	@Override
	public Carrito saveCarrito(Carrito c) {
		return repoC.save(c);
	}
	
	@Override
	public void saveProductosEnCarrito(ProductosEnCarrito pc) {
		repoPC.save(pc);
	}	
	

	// --------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------

	@Override
	public void saveVenta(Venta v) {
		repoV.save(v);
	}	
	
	// --------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------
	

	
	

}
