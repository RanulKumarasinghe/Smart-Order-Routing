package com.ab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ab.controllers.OrderController;

@SpringBootApplication
public class SpringSmartOrderRoutingApplication implements CommandLineRunner {
	@Autowired
	public OrderController orderController;
	public static void main(String[] args) {
		SpringApplication.run(SpringSmartOrderRoutingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		
		
		//int order  = orderController.createOrder(3.0, 360.0, "Buy", 1, 1);
		//System.out.println("Order -->  "+order);
		
		//System.out.println(orderController.getAllUserOrders(1));
//		int update = orderController.updateOrder(4,480, 1);
//		System.out.println(update);
//		
		System.out.println(orderController.cancelOrder(1));
	}

}
