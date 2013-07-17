package com.web.transtemare.scheduler;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;


public class SchedulerFilter implements org.apache.struts2.StrutsStatics, javax.servlet.Filter {
	

	
	protected void startScheduler() throws SchedulerException {  
//		   SchedulerFactory sf=new StdSchedulerFactory();  
//		    Scheduler sched=sf.getScheduler();  
//		    sched.start();  
//		    JobDetail jd=new JobDetail("myjob",Scheduler.DEFAULT_GROUP,ActionJob.class);  
//		    SimpleTrigger st=new SimpleTrigger("mytrigger",Scheduler.DEFAULT_GROUP,new Date(),  
//		    null,SimpleTrigger.REPEAT_INDEFINITELY,3611111111L);  
//		    sched.scheduleJob(jd, st);
		 }

	public void destroy() {
		SchedulerFactory sf=new StdSchedulerFactory();  
	    Scheduler sched;
		try {
			sched = sf.getScheduler();
			sched.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}  
	    
		
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
	}

	public void init(FilterConfig arg0) throws ServletException {
		try
		{
			startScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
	}  


}
