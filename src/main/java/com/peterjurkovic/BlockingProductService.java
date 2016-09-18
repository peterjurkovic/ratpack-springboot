package com.peterjurkovic;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BlockingProductService implements ProductService{
	
	private static final List<Product> STORAGE;
	
	static{
		STORAGE = Arrays.asList(new Product(1L, "Car"), new Product(2L, "Laptop"));
	}
	
	@Override
	public Product getById(Long id) {
		for(Product product : STORAGE){
			if(product.getId() == id){
				return product;
			}
		}
		return null;
	}

	@Override
	public List<Product> list() {
		return Collections.unmodifiableList(STORAGE);
	}

}
