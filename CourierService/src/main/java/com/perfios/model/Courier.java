package com.perfios.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="courier")
public class Courier {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long courierId;
	private String name;
	private LocalDate startDate;
	private LocalDate deliveryDate;
	private double weight;
	private double amount;
	private String deliveryStatus;
	private String pickup;
	private String delivery;
	private double distance;
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="UserId",referencedColumnName="id")
	private User user;
	public Courier() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getCourierId() {
		return courierId;
	}
	public void setCourierId(Long courierId) {
		this.courierId = courierId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	public String getPickup() {
		return pickup;
	}
	public void setPickup(String pickup) {
		this.pickup = pickup;
	}
	public String getDelivery() {
		return delivery;
	}
	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Courier(String name, LocalDate startDate, LocalDate deliveryDate, double weight, double amount,
			String deliveryStatus, String pickup, String delivery, double distance, User user) {
		super();
		this.name = name;
		this.startDate = startDate;
		this.deliveryDate = deliveryDate;
		this.weight = weight;
		this.amount = amount;
		this.deliveryStatus = deliveryStatus;
		this.pickup = pickup;
		this.delivery = delivery;
		this.distance = distance;
		this.user = user;
	}
	
}
