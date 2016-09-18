package com.peterjurkovic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ratpack.registry.Registry;
import ratpack.server.RatpackServer;
import ratpack.spring.Spring;

public class App {

	static Logger log = LoggerFactory.getLogger(App.class); 
	
	public static void main(String[] args) throws Exception {
		Registry springBoot = Spring.spring(SpringBootApp.class);
		ProductRepository repo = springBoot.get(ProductRepository.class);
		repo.save(new Product(1L, "test 1"));
		repo.save(new Product(2L, "test 2"));
		
		RatpackServer.start(s -> s 
			 .serverConfig( configBuilder -> configBuilder
	             .development(true)
			 )
			 .registry (springBoot)
			 .handlers( chain -> chain
				.prefix("product", a -> a.
					get(":id", ctx -> {
						Long id = ctx.getPathTokens().asLong("id");
						ctx.get(AsyncProductService.class)
							.getById(id)
							.then(ctx::render);
						
					})
					.get(ctx -> {
						log.info("Retrieving list of products...");
						ctx.get(AsyncProductService.class)
						.list()
						.then(ctx::render);
					})
				)
			 )
			 
		);
	}
}
