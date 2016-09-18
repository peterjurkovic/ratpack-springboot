package com.peterjurkovic;

import static ratpack.jackson.Jackson.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ratpack.exec.Blocking;
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
		log.info(repo.findAll().toString()); 
		SpringProductService sr = springBoot.get(SpringProductService.class);
		System.out.println("SPRING SERVICE " + sr) ;
		RatpackServer.start(s -> s 
			 .serverConfig( configBuilder -> configBuilder
	             .development(true)
			 )
			 .registry(springBoot)
			 .registryOf(r -> r
//					 .add(ProductService.class, new BlockingProductService())
					 .add(new ExecutionInterceptor())
//					 .add( springBoot )
			 )
			 .handlers( chain -> chain
				.prefix("product", a -> a.
					get(":id", ctx -> {
						Long id = ctx.getPathTokens().asLong("id");
						Blocking.get(() -> {
							return ctx.get(SpringProductService.class).getById(id);
						}).then( product -> {
							ctx.render( json( product ) ); 
						});
					})
					.get(ctx -> {
						log.info("Retrieving list of products...");
						Blocking.get(() -> {
							log.info("Blocking ...");
							return ctx.get(ProductService.class).list();
						}).then( list -> {
							log.info("all done! just rendering..");
							ctx.render( json( list ) ); 
						});
						
						log.info("after blocking statement");
					})
				)
			 )
			 
		);
	}
}
