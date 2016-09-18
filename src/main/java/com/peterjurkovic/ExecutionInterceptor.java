package com.peterjurkovic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ratpack.exec.ExecInterceptor;
import ratpack.exec.Execution;
import ratpack.func.Block;

public class ExecutionInterceptor implements ExecInterceptor{
	
	static Logger log = LoggerFactory.getLogger(App.class); 

	@Override
	public void intercept(Execution execution, ExecType execType, Block executionSegment) throws Exception {
		log.info("BEFORE " + execType.toString()) ;
		executionSegment.execute();
		log.info("AFTER " + execType.toString()) ;
	}

}
