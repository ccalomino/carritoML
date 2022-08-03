package com.example.carritoWeb.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.carritoWeb.model.FavoritoML;

@Transactional
public interface IFavoritosMLRepo extends JpaRepository<FavoritoML, Integer>{

	


	@Modifying
	@Query(nativeQuery = true, value="DELETE FROM favoritoml WHERE id_prodml=:idProd AND id_usuml=:idUsu")
	void deleteByUsuProd(@Param("idUsu") int idUsu, @Param("idProd") int idProd);
	
	

	
	
}
