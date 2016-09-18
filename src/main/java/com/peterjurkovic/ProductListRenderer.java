package com.peterjurkovic;

import static ratpack.jackson.Jackson.json;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ratpack.handling.Context;
import ratpack.render.Renderer;
import ratpack.util.Types;

@Component
public class ProductListRenderer implements Renderer<List<Product>> {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	
	@Override
	public Class<List<Product>> getType() {
		// TODO Auto-generated method stub
		return (Class<List<Product>>) Types.listOf(Product.class).getRawType();
	}

	@Override
	public void render(Context context, List<Product> product) throws Exception {
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
