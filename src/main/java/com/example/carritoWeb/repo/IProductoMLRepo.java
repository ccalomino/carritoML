package com.example.carritoWeb.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.carritoWeb.dto.ProductoMLDto;
import com.example.carritoWeb.dto.ProductoMLDto2;
import com.example.carritoWeb.model.ProductoML;

public interface IProductoMLRepo extends JpaRepository<ProductoML, Integer>{

	@Transactional
	ProductoML findByNombre(String nombre); 
	
	@Transactional
	ProductoML findByidProdML(int idProdML);
	
	@Query(value = "select * from productoml order by nombre", nativeQuery = true)
    List<ProductoML> findAllProductoOrdenado();
	
	@Query(nativeQuery = true,
            value = "SELECT p.id_prodml, p.nombre, COALESCE(f.id_usuml,0) as id_usuml FROM productoml p LEFT JOIN favoritoml f ON f.id_usuml = :idUsuML AND p.id_prodml = f.id_prodml ORDER BY p.nombre")
    List<ProductoMLDto> findAllProductoConFavoritos(@Param("idUsuML") int idUsuML);
	
	@Query(nativeQuery = true,
			value = "SELECT COUNT(id_usuml) FROM favoritoml WHERE id_usuml = :idUsuML AND id_prodml = :idProdML")
	int cantProdFav(@Param("idUsuML") int idUsuML, @Param("idProdML") int idProdML);

	@Query(nativeQuery = true,
            value = "SELECT f.id_prodml, (select p.nombre from productoml p where p.id_prodml = f.id_prodml), COUNT(*) as cant FROM favoritoml f GROUP BY f.id_prodml ORDER BY 3 desc FETCH FIRST 5 ROWS ONLY")	
    List<ProductoMLDto2> findTopFive();

//	@Modifying
//	@Query("DELETE FROM favoritoml WHERE id_prodml=:idProd AND d_usuml=:idUsu")
//	List<FavoritoML> deleteByUsuProd(@Param("idUsu") int idUsu, @Param("idProd") int idProd);

	
}
