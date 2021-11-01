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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ab.entities.Order;
import com.ab.entities.Region;
import com.ab.entities.Stock;
import com.ab.entities.StockExchange;
import com.ab.entities.User;
import com.ab.entities.UserStock;
import com.ab.services.ExchangeService;
import com.ab.services.OrderService;
import com.ab.services.StockExchangeService;
import com.ab.services.StockService;
@SessionAttributes({"loggedInUser","stocks","exchanges","userStocks","userStock","result","pendingOrders"})
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
	
	
	private ModelAndView mv = new ModelAndView();
	private int stock_Id = 0;
	private int exchange_Id = 0;
	private Order order = new Order();
	private double stockPrice = 0.0;
	private double totalPrice = 0.0;
	private double stockAmount = 0.0;
	
	public int createOrder(double orderStockAmount, double orderTotalPrice, String orderType, int orderbookId, int stockId,int userId) {
		return orderService.createOrder(orderStockAmount, orderTotalPrice, orderType, orderbookId, stockId, userId);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/sellStock")
	public ModelAndView placeSellOrder(@ModelAttribute("loggedInUser") User u, @ModelAttribute("userStock") UserStock userStock, @RequestParam(value="stockAmount") double stockAmount ) {
		
		int userStockId = userStock.getStock().getStockId();
		int userId = u.getUserId();
		//double stockPrice = userStock.getStock().getStockPrice();
		double stockPrice = stockExchangeService.findLowestStockPrice(userStockId);
		double orderTotalPrice = stockAmount * stockPrice;
		int orderbookId = exchangeService.getExchangeIdByRegion(u.getUserRegion().toString());	
		int result = orderService.createOrder(stockAmount, orderTotalPrice, "Sell", orderbookId, userStockId, userId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("result","Order Placed Successfully!");
		System.out.println();
		mv.setViewName("sellOrder");
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/sellStock")
	public String loadSellPage() {
		return "sellOrder";
	}
	@RequestMapping(method = RequestMethod.GET, value="/pendingOrders")
	public ModelAndView loadPendingOrderPage(@ModelAttribute("loggedInUser") User u) {
		int userId = u.getUserId();
		List<Order> pendingOrders = orderService.getUserPendingOrders(userId);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("pendingOrders");
		mv.addObject("pendingOrders",pendingOrders);
		
		return mv;
	}
	
	@GetMapping("/buyOrder/{id}/stock")
	public ModelAndView buyOrder(@PathVariable("id")int stock_id, @ModelAttribute("loggedInUser") User u) {
		String region = u.getUserRegion().toString();
		stock_Id = stock_id;
		int exchangeId = exchangeService.getExchangeIdByRegion(region);
		exchange_Id = exchangeId;
		double stockExchange = stockExchangeService.findLowestStockPrice(stock_id);
		stockPrice = stockExchange;
		Stock stock = stockService.getStockById(stock_id);
		List<Order> BuyOrders = findPendingBuyOrders(u.getUserId());
		List<Order> SellOrders = findPendingSaleOrders(stock_id);
		updateToFullfilled(BuyOrders, SellOrders);
		mv.addObject("stockExchange", stockExchange);
		mv.addObject("stock", stock);
		mv.setViewName("buyOrder");		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/buyOrder")
	public ModelAndView enterAmount(@RequestParam (value = "amount") double amount) {
		totalPrice = stockPrice * amount;
		stockAmount = amount;
		mv.addObject("price", totalPrice);
		mv.addObject("amount", amount);
		mv.setViewName("buyOrderConfirm");	
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/buyOrderConfirm")
	public ModelAndView confirmBuyOrder(@ModelAttribute("loggedInUser") User u) {
		createOrder(stockAmount,totalPrice,"Buy",exchange_Id,stock_Id,u.getUserId());
		mv.setViewName("dashboard");
		return mv;
	}
	
	
	
	public List<Order> findPendingBuyOrders(int user_id){
		return orderService.findPendingBuyOrders(user_id);
	}
	
	public List<Order> findPendingSaleOrders(int stockId){
		return orderService.findPendingSaleOrders(stockId);
	}
	
	public void updateToFullfilled(List<Order> BuyOrders, List<Order> SellOrders) {
		orderService.updateOrdersToFullfilled(BuyOrders, SellOrders);
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
	
	@GetMapping("pendingsaleorders/{stockId}/{orderBookId}")
	public List<Order> findPendingSaleOrdersByOrderBookId(@PathVariable("stockId") int stockId, @PathVariable("orderBookId") int orderBookId){
		return orderService.findPendingSaleOrdersByOrderBookId(stockId, orderBookId);
	}
	
	
//	@GetMapping("pendingsaleorders/{stockId}/{orderBookId}/{buyAmount}")
//	public Order findBestOrder(@PathVariable("stockId") int stockId, @PathVariable("orderBookId") int orderBookId,@PathVariable("buyAmount") double buyAmount) {
//		return orderService.findBestOrder(stockId, orderBookId, buyAmount);
//	}
	

}
