package com.peterjurkovic;

import java.util.List;

import ratpack.exec.Promise;

public interface AsyncProductService {

	Promise<Product> getById(Long id);
	
	Promise<List<Product>> list();
}
