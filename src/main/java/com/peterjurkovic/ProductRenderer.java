package com.peterjurkovic;

import static ratpack.jackson.Jackson.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ratpack.handling.Context;
import ratpack.render.Renderer;

@Component
public class ProductRenderer implements Renderer<Product>{
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public Class<Product> getType() {
		return Product.class;
	}

	@Override
	public void render(Context context, Product product) throws Exception {
		context.byContent(spec -> spec
			.json( () -> {
				log.debug("rendering json..");
				context.render( json( product ) );
			})
			.xml( () -> {
				log.debug("rendering xml..");
				// not supported yet
			})
			.html( () -> {
				log.debug("rendering html..");
				context.render( json( product ) );
			})
		);
		
		
	}

}
