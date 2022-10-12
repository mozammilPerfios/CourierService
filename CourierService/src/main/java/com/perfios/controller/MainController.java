package com.perfios.controller;

import java.io.IOException;
import java.time.LocalDate;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.perfios.config.QuartzConfig;
import com.perfios.dto.CourierDto;
import com.perfios.model.Courier;
import com.perfios.model.User;
import com.perfios.job.DeliveryStatus;
import com.perfios.repository.CourierRepository;
import com.perfios.repository.UserRepository;
import com.perfios.service.UserService;
@Controller
public class MainController {
	@Autowired private QuartzConfig quartzConfig;
	@GetMapping("/login")
	public String login() throws IOException, SchedulerException {
		return "login";
	}
	@Autowired
	private UserRepository userRepository;
	@Autowired CourierRepository cr;
	@GetMapping("/")
    public String home(Model model) throws IOException, SchedulerException {
	 AbstractAuthenticationToken auth = (AbstractAuthenticationToken)
			    SecurityContextHolder.getContext().getAuthentication();
	 		
	 User user=userRepository.findByFirstName(auth.getName());
	 model.addAttribute(user);

        return "index";
    }
	@Autowired UserService userService;
	@GetMapping("/details")
	public String details(Model model)
	{
		 AbstractAuthenticationToken auth = (AbstractAuthenticationToken)
				    SecurityContextHolder.getContext().getAuthentication();
		 		
		 User user=userRepository.findByFirstName(auth.getName());
		 model.addAttribute("user",user);
		model.addAttribute("AuditTable",userService.getUserCourier());
		return "TrackingTable";
	 
	}
	@GetMapping("/charges")
	public String charges(Model model)
	{
		return "plans";
	}
	@GetMapping("/getCourierForm")
	public String courierForm(Model model) {
	
		Courier courier=new Courier();
		

		 AbstractAuthenticationToken auth = (AbstractAuthenticationToken)
				    SecurityContextHolder.getContext().getAuthentication();
		 		
		 User user=userRepository.findByFirstName(auth.getName());
		model.addAttribute(courier);
		model.addAttribute(user);
		return "courierForm";
	}
	@PostMapping("/getCourierForm")
	public String courier(@ModelAttribute("courier")CourierDto cdt,Model model) {
	
		Courier courier=new Courier();
		LocalDate now=LocalDate.now();
		
		if(cdt.getStartDate().isBefore(now)) {
			return "redirect:/getCourierForm?startDate";
		}
		if(cdt.getWeight()<=0) {
			return "redirect:/getCourierForm?weight";
		}
		if(cdt.getDistance()<=0)
		{
			return "redirect:/getCourierForm?ld";
		}
		if(cdt.getDistance()>=100)
		{
			return "redirect:/getCourierForm?md";

		}
		else {
			AbstractAuthenticationToken auth = (AbstractAuthenticationToken)
					SecurityContextHolder.getContext().getAuthentication();
					User user=userRepository.findByFirstName(auth.getName());
			double amount=calcAmount(cdt);
			courier.setName(cdt.getName());
			courier.setStartDate(cdt.getStartDate());
			courier.setDeliveryDate(calcDel(cdt));
			courier.setWeight(cdt.getWeight());
			courier.setAmount(amount);
			courier.setDeliveryStatus("Pending");
			courier.setPickup(cdt.getPickup());
			courier.setDelivery(cdt.getDelivery());
			courier.setDistance(cdt.getDistance());
			courier.setUser(user);
			cr.save(courier);
			model.addAttribute("courier",courier);
			return "shipped";
		}
	}
	@GetMapping("/shipped")
	public String shipped() {
		return "shipped";
	}
	private LocalDate calcDel(CourierDto cdt) {
		// TODO Auto-generated method stub
		double distance=cdt.getDistance();
		System.out.println(LocalDate.now().plusDays(2));
		if(distance<10) {
			return cdt.getStartDate().plusDays(2);
		}
		else if(distance>10 && distance<50){
			return cdt.getStartDate().plusDays(5);
		}
		else {
			return cdt.getStartDate().plusDays(10);
		}
	}
	private double calcAmount(CourierDto cdt) {
		// TODO Auto-generated method stub
		//weight
		double amount=0;
		double weight=cdt.getWeight();
		double distance=cdt.getDistance();
		if(weight<10)
		{
			amount=amount+weight*5;
		}
		if(weight>10 && weight<=50)
		{
			amount=amount+50+(weight-10)*10;
		}
		if(weight>50 && weight<=100) {
			amount=amount+50+400+(weight-50)*50;
		}
		if(distance<10) {
			amount=amount+distance*2;
		}
		if(distance>10 && distance<=50)
		{
			amount=amount+20+(distance-10)*5;
		}
		if(distance>50 && distance<=100)
		{
			amount=amount+20+200+(distance-50)*10;
		}
		System.out.println(amount);
		System.out.println(cdt.getStartDate());
		return amount;
	}
}
