package com.example.carritoWeb.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.carritoWeb.dto.CarritoDto;
import com.example.carritoWeb.model.Carrito;


public interface ICarritoRepo extends JpaRepository<Carrito, Integer>{

	
//	@Query("select c from carrito c")
//	List<Carrito> findAllCarritos();
	
	@Query(value = "select c.id_carrito, u.id_Usu, p.id_prod, u.nombre as \"cliente\", p.nombre as \"producto\", p.precio, pc.cantidad\n,  (select count(*) from productos_en_carrito pc2 where pc2.idc = c.id_carrito) as \"rows\", v.valor_total as \"total\""
			+ "from usuario u, carrito c, productos_en_carrito pc, producto p, venta v\n"
			+ "where c.usuario_id = u.id_usu and c.id_carrito = pc.idc and pc.idp = p.id_prod and c.id_carrito = v.idc", nativeQuery = true)
    List<CarritoDto> findAllCarritoDto();
 
	
	
	
	@Query(value = "select c.id_carrito, u.id_Usu, p.id_prod, u.nombre as cliente, p.nombre as producto, p.precio, pc.cantidad, (select count(*) from productos_en_carrito pc2 where pc2.idc = c.id_carrito) as rows, v.valor_total\n"
			+ "	from usuario u, carrito c, productos_en_carrito pc, producto p, venta v\n"
			+ "	where c.id_carrito = :id and c.usuario_id = u.id_usu and c.id_carrito = pc.idc and pc.idp = p.id_prod and c.id_carrito = v.idc", nativeQuery = true)
    List<CarritoDto> findAllCarritoDtoById(@Param("id") int id);
	

	@Query(value = "select  v.valor_total\n"
			+ "from carrito c, venta v\n"
			+ "where c.id_carrito = :id and c.id_carrito = v.idc", nativeQuery = true)
    float findAllCarritoDtoByIdTotal(@Param("id") int id);

	
}
