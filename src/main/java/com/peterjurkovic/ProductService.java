package com.peterjurkovic;

import java.util.List;

public interface ProductService {
	
	Product getById(Long id);
	
	List<Product> list();
}
