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
import com.ab.services.UserService;
import com.ab.services.UserStockService;
@SessionAttributes({"loggedInUser","stocks","exchanges","userStocks","userStock","result","pendingOrders","cOrder"})
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
	@Autowired
	private UserService userService;

	private ModelAndView mv = new ModelAndView();
	private int stock_Id = 0;
	private int exchange_Id = 0;
	private Order order = new Order();
	private double stockPrice = 0.0;
	private double totalPrice = 0.0;
	private double stockAmount = 0.0;
	

	@Autowired
	private UserStockService userStockService;
	
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
		 
		double newStockAmount = userStockService.getStockAmount(userId, userStockId) - stockAmount;
		userStockService.updateStockAmount(userId, userStockId, newStockAmount);
		
		System.out.println(newStockAmount);
		List<Order> pendingOrders = orderService.getUserPendingOrders(userId);
		mv.setViewName("pendingOrders");
		mv.addObject("pendingOrders",pendingOrders);
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/sellStock")
	public String loadSellPage() {
		return "sellOrder";
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
	@RequestMapping(method = RequestMethod.GET, value="/pendingOrders")
	public ModelAndView loadPendingOrderPage(@ModelAttribute("loggedInUser") User u) {
		int userId = u.getUserId();
		List<Order> BuyOrders = findPendingBuyOrders(u.getUserId());
		List<Order> SellOrders = findPendingSaleOrders(stock_Id);
		updateToFullfilled(BuyOrders, SellOrders);
		List<Order> pendingOrders = orderService.getUserPendingOrders(userId);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("pendingOrders");
		mv.addObject("pendingOrders",pendingOrders);
		
		return mv;
	}
	@GetMapping("/cancelOrder/{orderId}/order")
	public ModelAndView loadCancelPage( @PathVariable("orderId") int orderId) {
		Order cOrder = (Order) orderService.getOrderByOrderId(orderId);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("cancelOrder");
		mv.addObject("cOrder",cOrder);
		return mv;
	}
	//cancel order
	@RequestMapping(method = RequestMethod.POST, value="/cancelOrder")
	public ModelAndView cancelOrder(@ModelAttribute("loggedInUser") User u, @ModelAttribute("cOrder") Order cOrder) {
		int userId = u.getUserId();
		int orderId = cOrder.getOrderId();
		Order order = (Order) orderService.getOrderByOrderId(orderId);
		if(order.getOrderType().equals("Sell")) {
			double stockAmount = order.getOrderStockAmount();
			int stockId = order.getStock().getStockId();
			//cancel and update stock amount
			orderService.cancelOrderByOrderId(orderId);
			double newAmount = userStockService.getStockAmount(userId, stockId) + stockAmount;
			userStockService.updateStockAmount(userId, stockId, newAmount);	
			
		}else {
			//cancel the buy order
			orderService.cancelOrderByOrderId(orderId);
		}
		List<Order> pendingOrders = orderService.getUserPendingOrders(userId);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("pendingOrders");
		mv.addObject("pendingOrders",pendingOrders);
		return mv;
		
	}
	
	
	@GetMapping("/buyOrder/{id}/stock")
	public ModelAndView buyOrder(@PathVariable("id")int stock_id, @ModelAttribute("loggedInUser") User u) {
//		String region = u.getUserRegion().toString();
		stock_Id = stock_id;
		
//		int exchangeId = exchangeService.getExchangeIdByRegion(region);
//		exchange_Id = exchangeId;
		double stockExchange = stockExchangeService.findLowestStockPrice(stock_id);
		stockPrice = stockExchange;
		String stock = stockService.getStockNameById(stock_id);
//		List<Order> BuyOrders = findPendingBuyOrders(u.getUserId());
//		List<Order> SellOrders = findPendingSaleOrders(stock_id);
//		updateToFullfilled(BuyOrders, SellOrders);
		double userBallance = userService.findUserBalance(u.getUserId());
		mv.addObject("ballance",userBallance);
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
		int userId = u.getUserId();
		List<Order> pendingOrders = orderService.getUserPendingOrders(userId);
		mv.addObject("pendingOrders",pendingOrders);
		mv.setViewName("pendingOrders");
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
	//@PostMapping("/cancelOrder")
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
