package com.ab.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ab.entities.Order;
import com.ab.entities.Region;
import com.ab.entities.Stock;
import com.ab.entities.StockExchange;
import com.ab.entities.User;
import com.ab.services.ExchangeService;
import com.ab.services.OrderService;
import com.ab.services.StockExchangeService;
import com.ab.services.StockService;
@SessionAttributes({"loggedInUser","stocks","exchanges","userStocks"})
@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ExchangeService exchangeService;
	
	@Autowired
	private StockExchangeService stockExchangeService;
	@Autowired
	private StockService stockService;
	ModelAndView mv = new ModelAndView();
	
	@PostMapping("/placeOrder/")
	public int createOrder(@RequestParam("orderStockAmount") double orderStockAmount, @RequestParam("orderTotalPrice") double orderTotalPrice, @RequestParam("orderType") String orderType, @RequestParam("orderbookId") int orderbookId, @RequestParam("stockId") int stockId, @RequestParam("userId") int userId) {
		return orderService.createOrder(orderStockAmount, orderTotalPrice, orderType, orderbookId, stockId, userId);
	}
	
	@GetMapping("/buyOrder/{id}/stock")
	public ModelAndView buyOrder(@PathVariable("id")int stock_id, @ModelAttribute("loggedInUser") User u) {
		String region = u.getUserRegion().toString();
		
		int exchangeId = exchangeService.getExchangeIdByRegion(region);
		double stockExchange = stockExchangeService.findBestStockPriceOnExchange(stock_id, exchangeId);
		Stock stock = stockService.getStockById(stock_id);
		
		mv.addObject("stockExchange", stockExchange);
		mv.addObject("stock", stock);
		mv.setViewName("buyOrder");		
		return mv;
	}
	
	@GetMapping("/userorders/{userId}")
	public  List<Order> getAllUserOrders(@PathVariable("userId") int userId) {
		return orderService.getAllOrdersByUserId(userId);
	}
	
	@GetMapping("/stockorders/{stockId}")
	public  List<Order> getAllOrdersByStockId(@PathVariable("stockId") int stockId) {
		return orderService.getAllOrdersByStockId(stockId);
	}
	
	@GetMapping("/orders/{orderId}")
	public Order getOrderByOrderId(@PathVariable("orderId") int orderId) {
		return orderService.getOrderByOrderId(orderId);
	}
	
	@GetMapping("/stockAmount/{userId}/{stockId}")
	public double getUserStockAmount(@PathVariable("userId") int userId, @PathVariable("stockId") int stockId) {
		double buyAmount = orderService.getBuyStockAmount(stockId, userId);
		double sellAmount = orderService.getSellStockAmount(stockId, userId);
		double amount = buyAmount - sellAmount;
		return amount;
	}
	
	//Calculate the stock total price on base of amount
	@PostMapping("/updateOrder")
	public int updateOrder(@RequestParam("orderStockAmount") double orderStockAmount, @RequestParam("orderTotalPrice") double orderTotalPrice, @RequestParam("userId") int userId) {
		return orderService.updateOrder(orderStockAmount, orderTotalPrice, userId);
	}
	
	//Cancel order
	@PostMapping("/cancelOrder")
	public int cancelOrder(@RequestParam("orderId") int orderId) {
		return orderService.cancelOrderByOrderId(orderId);
	}
	
	//Update order to Fulfilled
	@PostMapping("/updateToFulfilled")
	public int updateToFulfilled(@RequestParam("orderId") int orderId) {
		return orderService.updateOrderToFullfilled(orderId);
	}
	
	//Update order to Partially Fulfilled
	@PostMapping("/updateToPartiallyFulfilled")
	public int updateToPartiallyFulfilled(@RequestParam("orderId") int orderId) {
		return orderService.updateOrderToPartiallyFullfilled(orderId);
	}
	
	@GetMapping("/orderbook/orders/{orderbookId}")
	public List<Order> getAllOrdersByOrderBookId(@PathVariable("orderbookId") int orderbookId){
		int exchangeId =1;
		orderbookId = exchangeService.getExchangeOrderBookId(exchangeId);
		return orderService.findAllOrdersByOrderBookId(orderbookId);
	}
	@GetMapping("sellorders/{userId}")
	public List<Order> getUserSellOrders(@PathVariable("userId") int userId){
		return orderService.getUserSellOrders(userId);
	}
	@GetMapping("buyorders/{userId}")
	public List<Order> getUserBuyOrders(@PathVariable("userId") int userId){
		return orderService.getUserBuyOrders(userId);
	}
	@GetMapping("tradehistory/{userId}")
	public List<Order> getUserTradeHistory(@PathVariable("userId") int userId){
		return orderService.getUserTradeHistory(userId);
	}
	@GetMapping("pendingorders/{userId}")
	public List<Order> getUserPendingOrders(@PathVariable("userId") int userId){
		return orderService.getUserPendingOrders(userId);
	}
	@GetMapping("pendingsaleorders/{stockId}")
	public List<Order> findPendingSaleOrders(@PathVariable("stockId") int stockId){
		return orderService.findPendingSaleOrders(stockId);
	}
	@GetMapping("pendingsaleorders/{stockId}/{orderBookId}")
	public List<Order> findPendingSaleOrdersByOrderBookId(@PathVariable("stockId") int stockId, @PathVariable("orderBookId") int orderBookId){
		return orderService.findPendingSaleOrdersByOrderBookId(stockId, orderBookId);
	}
//	@GetMapping("pendingsaleorders/{stockId}/{orderBookId}/{buyAmount}")
//	public Order findBestOrder(@PathVariable("stockId") int stockId, @PathVariable("orderBookId") int orderBookId,@PathVariable("buyAmount") double buyAmount) {
//		return orderService.findBestOrder(stockId, orderBookId, buyAmount);
//	}
	

}
