package com.web.transtemare.scheduler;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionException;

import com.core.transtemare.commons.Fachada;


public class ActionJob
{
	private Fachada fac;
	Logger logger = Logger.getLogger(ActionJob.class);
	
	public ActionJob(Fachada fac) {
		super();
		this.fac = fac;
	}
	
    public void execute() throws JobExecutionException
    {	
    	logger.debug("Iniciando Rutina...");
//    	logger.info("Total de alarmas: " + fac.totalAlarmas());
    	logger.debug("Finalizando rutina"); 
    }


	public Fachada getFac() {
		return fac;
	}

	public void setFac(Fachada fac) {
		this.fac = fac;
	}
    
    
    
}