package com.perfios.job;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.perfios.model.Courier;
import com.perfios.model.User;
import com.perfios.repository.CourierRepository;
import com.perfios.repository.UserRepository;


@DisallowConcurrentExecution
public class DeliveryStatus extends QuartzJobBean 
{

	@Autowired UserRepository ur;
	@Autowired CourierRepository cr;
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException 
	{
		System.out.println("in job");
		List<Courier> rt=cr.findAll();
		for (int i = 0; i < rt.size(); i++) 
		{
			LocalDate today=LocalDate.now();
			if(rt.get(i).getDeliveryStatus().equalsIgnoreCase("pending") && !today.isBefore(rt.get(i).getDeliveryDate())){
				rt.get(i).setDeliveryStatus("delivered");
				cr.save(rt.get(i));
			}
		}
	}
}
