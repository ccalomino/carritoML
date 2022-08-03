package com.example.carritoWeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.carritoWeb.model.Producto;
import com.example.carritoWeb.repo.IProductoRepo;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private IProductoRepo repoP;
	
	@Override
	public Page<Producto> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
		Pageable pageable = PageRequest.of(pageNo-1,pageSize,sort);
		return this.repoP.findAll(pageable);
	}

}
