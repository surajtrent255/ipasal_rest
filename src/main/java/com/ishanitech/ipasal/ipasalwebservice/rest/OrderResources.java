package com.ishanitech.ipasal.ipasalwebservice.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
/*import com.ipasal.ipasalwebapp.dto.KhaltiMerchant;
import com.ipasal.ipasalwebapp.dto.KhaltiResponse;
import com.ipasal.ipasalwebapp.dto.KhaltiState;
import com.ipasal.ipasalwebapp.dto.KhaltiType;
import com.ipasal.ipasalwebapp.dto.KhaltiUser;
import com.ipasal.ipasalwebapp.utilities.UserDetailsUtil;*/
import com.ishanitech.ipasal.ipasalwebservice.Services.OrderServce;
import com.ishanitech.ipasal.ipasalwebservice.Services.PaymentMethodService;
import com.ishanitech.ipasal.ipasalwebservice.dto.NewOrderDto;
import com.ishanitech.ipasal.ipasalwebservice.dto.OrderUpdateDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.PaymentDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.PaymentMethodDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ProductDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.Response;
import com.ishanitech.ipasal.ipasalwebservice.dto.UserDTO;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomSqlException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/order")
public class OrderResources {
	private final Logger logger = LoggerFactory.getLogger(OrderResources.class);
	private OrderServce orderServce;
	private PaymentMethodService paymentMethodService;
	@Autowired
	public OrderResources(OrderServce orderServce, PaymentMethodService paymentMethodService) {
		this.orderServce = orderServce;
		this.paymentMethodService = paymentMethodService;
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Response<?> getOrderDetails(HttpServletRequest request) {
		List<NewOrderDto> orders;
		try {
			orders = orderServce.getAllOrders(request);
		} catch (Exception ex) {
			throw new CustomSqlException("Something went wrong while getting order from database!");
		}

		if (orders != null && orders.size() > 0) {
			return Response.ok(orders, HttpStatus.OK.value(), HttpStatus.OK.name());
		}
		
		if(request.getParameter("action").equals("first")) {
			throw new ResourceNotFoundException("Currently there are no order in database!");
		}
		
		throw new ResourceNotFoundException("No more data found! :(");
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Response<?> getAllOrders(HttpServletRequest request) {
		List<NewOrderDto> orders;
		try {
			orders = orderServce.getAllOrdersList(request);
		} catch (Exception ex) {
			throw new CustomSqlException("Something went wrong while getting order from database!");
		}

		if (orders != null && orders.size() > 0) {
			return Response.ok(orders, HttpStatus.OK.value(), HttpStatus.OK.name());
		}
		
		if(request.getParameter("action").equals("first")) {
			throw new ResourceNotFoundException("Currently there are no order in database!");
		}
		
		throw new ResourceNotFoundException("No more data found! :(");
	}

	@GetMapping("/delivered")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Response<?> getDeliveredOrders(HttpServletRequest request) {
		List<NewOrderDto> orders;
		try {
			orders = orderServce.getDeliveredOrdersList(request);
		} catch (Exception ex) {
			throw new CustomSqlException("Something went wrong while getting order from database!");
		}

		if (orders != null && orders.size() > 0) {
			return Response.ok(orders, HttpStatus.OK.value(), HttpStatus.OK.name());
		}
		
		if(request.getParameter("action").equals("first")) {
			throw new ResourceNotFoundException("Currently there are no order in database!");
		}
		
		throw new ResourceNotFoundException("No more data found! :(");
	}
	
	@GetMapping("/cancelled")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Response<?> getCancelledOrders(HttpServletRequest request) {
		List<NewOrderDto> orders;
		try {
			orders = orderServce.getCancelledOrdersList(request);
		} catch (Exception ex) {
			throw new CustomSqlException("Something went wrong while getting order from database!");
		}

		if (orders != null && orders.size() > 0) {
			return Response.ok(orders, HttpStatus.OK.value(), HttpStatus.OK.name());
		}
		
		if(request.getParameter("action").equals("first")) {
			throw new ResourceNotFoundException("Currently there are no order in database!");
		}
		
		throw new ResourceNotFoundException("No more data found! :(");
	}

	
//		Controller for retrieving the list of the most bought product ids
	@RequestMapping(method = RequestMethod.GET, value = "/most-bought-product")
	public Response<?> getMostBoughtProductss(){
		List<ProductDTO> productList;
		try {
			productList = orderServce.getMostBoughtProducts();
		}catch (Exception e){
			logger.error(e.getMessage());
			throw new CustomSqlException("Error while retrieving the product id list");
		}
		if (productList != null && productList.size() >0){
			return Response.ok(productList, HttpStatus.OK.value(), HttpStatus.OK.name());
		}
		throw new ResourceNotFoundException("Most bought product list was empty");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{orderId}")
	public Response<List<ProductDTO>> getOrderByOrderId(@PathVariable("orderId") Integer orderId) {
		List<ProductDTO> orderDetails;
		try {
			orderDetails = orderServce.getOrderDetailsByOrderId(orderId);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new CustomSqlException("Couldn't get order from database!");
		}

		if (orderDetails != null && orderDetails.size() > 0) {
			return Response.ok(orderDetails, HttpStatus.OK.value(), HttpStatus.OK.name());
		}

		throw new ResourceNotFoundException("No order details found for this order.");

	}

	@RequestMapping(method = RequestMethod.GET, value = "/specificOrder/{orderId}")
	public Response<NewOrderDto> getSpecificOrderByOrderId(@PathVariable("orderId") Integer orderId){
		NewOrderDto order;
		try {
			order = orderServce.getSpecificOrderByOrderId(orderId);
			return Response.ok(order, HttpStatus.OK.value(), HttpStatus.OK.name());
		}catch(Exception ex) {
			logger.error(ex.getMessage());
			throw new CustomSqlException("Couldn't get order from database!");
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/quantity/{quantity}/{orderDetailsId}/{id}")
	public Response<?> updateQuantity(@PathVariable("quantity") Integer quantity,
			@PathVariable("id") Integer orderDetailsId) {
		try {
			int result = orderServce.updateProductQuantity(quantity, orderDetailsId);
			return Response.ok(result, HttpStatus.OK.value(), HttpStatus.OK.name());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Couldn't update item quantity in order.");
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/ordeeId/{orderId}/status/{stat}")
	public Response<?> changeOrderStatus(@PathVariable("orderId") Integer orderId,
			@PathVariable("stat") Integer status) {
		try {
			int result = orderServce.changeOrderStatus(orderId, status);
			return Response.ok(result, HttpStatus.OK.value(), HttpStatus.OK.name());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Couldn't update order status!");
		}
	}

	@PostMapping("/cancel/{orderId}")
	public Response<?> cancelOrderByOrderId(@PathVariable("orderId") Integer orderId) {
		try {
			orderServce.cancelOrderById(orderId);
			return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "Order cancelled successfully.");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new CustomSqlException("Order cancel action couldn't perform successfully!");
		}
	}

	@PostMapping("/deliver/{orderId}")
	public Response<?> changeOrderStatusByOrderId(@PathVariable("orderId") Integer orderId) {
		try {
			orderServce.changeOrderStatusByOrderId(orderId);
			return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "Order delivered successfully.");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new CustomSqlException("Couldn't change order status to delivery!");
		}
	}

	@PostMapping("/confirm")
	public Response<?> confirmOrder(@RequestBody NewOrderDto requestObject) {
		try {
			System.out.println("Request Hit");
			int orderId = orderServce.addOrder(requestObject);
			System.out.println("After 1");
			return Response.ok(orderId, HttpStatus.OK.value(), "Order Confirmed");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new CustomSqlException("Something went wrong while confirming order!");
		}

	}
	
	@GetMapping("/user/{userId}")
	public Response<?> getOrderDetails(HttpServletRequest request, @PathVariable("userId") Integer userId) {
		List<NewOrderDto> orders = new ArrayList<>();
		try {
			orders = orderServce.getOrdersByUserId(request, userId);
		} catch(Exception ex) {
			logger.error(ex.getMessage());
		}
		
		if (orders != null && orders.size() > 0) {
			return Response.ok(orders, HttpStatus.OK.value(), HttpStatus.OK.name());
		}
		
		if(request.getParameter("action").equals("first")) {
			throw new ResourceNotFoundException("Currently there are no order in database!");
		}
		
		throw new ResourceNotFoundException("No more data found! :(");
	}
	

	@GetMapping("/totalOrders/{userId}")
	public Response<Integer> getAllOrdersByUserId(@PathVariable("userId") Integer userId) {
		Integer totalOrdersForUsers;
		try {
			totalOrdersForUsers = orderServce.getTotalOrdersByUser(userId);
			return Response.ok(totalOrdersForUsers, HttpStatus.OK.value(), HttpStatus.OK.name());
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new CustomSqlException("Something went wrong while getting total order for this user.");
		}

	}

	@GetMapping("/totalOrders")
	public Response<Integer> getAllOrders() {
		try {
			Integer totalOrders = orderServce.countTotalOrders();
			return Response.ok(totalOrders, HttpStatus.OK.value(), HttpStatus.OK.name());
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new CustomSqlException("Something went wrong while getting total orders in database");
		}

	}

	@GetMapping("/order-info/{orderId}")
	public Response<UserDTO> getOrderInfo(@PathVariable("orderId") Integer orderId){
		try {
			UserDTO userInfo = orderServce.getOrderByOrderId(orderId);
			return Response.ok(userInfo, HttpStatus.OK.value(), HttpStatus.OK.name());
		}catch (Exception e){
			logger.error(e.getMessage());
			throw new CustomSqlException("Error while retrieving order information");
		}
	}
	
	@GetMapping("/paymentDetail/{orderId}")
	public Response<List<PaymentDTO>> getPaymentDetailByOrderId(@PathVariable("orderId") Integer orderId) {
		try {
			PaymentDTO paymentInfo = orderServce.getPaymentInfoByOrderId(orderId);
			PaymentMethodDTO paymentMethod = paymentMethodService.getPaymentMethodById(paymentInfo.getPaymentMethodId());
			paymentInfo.setPaymentMethod(paymentMethod);
			return Response.ok(Arrays.asList(paymentInfo), HttpStatus.OK.value(), HttpStatus.OK.name());
		} catch (Exception ex) {
			throw new CustomSqlException(ex.getMessage());
		}
	}
	
	@GetMapping("/orderUpdates/{orderId}")
	public Response<?> getOrderUpdateByOrderId(@PathVariable("orderId") Integer orderId) {
		List<OrderUpdateDTO> orderUpdateInfo;
		try {
			orderUpdateInfo = orderServce.getOrderUpdateByOrderId(orderId);
			
		} catch (Exception ex) {
			throw new CustomSqlException(ex.getMessage());
		}
		if (orderUpdateInfo != null && orderUpdateInfo.size() > 0) {
			return Response.ok(orderUpdateInfo, HttpStatus.OK.value(), HttpStatus.OK.name());
		}
		throw new ResourceNotFoundException("No order comments found for this order.");
	}
	
	@PostMapping("orderUpdates/add")
	public Response<?> addOrderUpdate(@RequestBody OrderUpdateDTO orderUpdateDTO) {
        Integer result = null;
        try {
            result = orderServce.addOrderUpdate(orderUpdateDTO);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Something went wrong while entering the data.");
        }
        return Response.ok(result, HttpStatus.OK.value(), HttpStatus.OK.name());
    }
	
	
	/*
	 * @GetMapping("/verifyKhaltiPayment") public Response<?>
	 * verifyKhaltiPayment(@RequestBody Object body) {
	 * 
	 * String completeUrl = "https://khalti.com/api/v2/payment/verify/";
	 * KhaltiResponse response = new KhaltiResponse(); JsonNode jsonNodeRoot = null;
	 * String stringRequest = ""; HttpHeaders headers = new HttpHeaders();
	 * Authentication authentication =
	 * SecurityContextHolder.getContext().getAuthentication(); UserDTO loggedInUser
	 * = null; if(UserDetailsUtil.isLoggedIn(authentication)) {
	 * headers.add("Authorization", "Key " + authKeyKhalti); }
	 * 
	 * try{ stringRequest = objectMapper.writeValueAsString(body); }catch
	 * (JsonProcessingException jsonProcessingException) {
	 * LOGGER.info(jsonProcessingException.getMessage()); }
	 * 
	 * headers.setContentType(MediaType.APPLICATION_JSON); HttpEntity<String> entity
	 * = new HttpEntity<>(stringRequest,headers); ResponseEntity<String> result =
	 * null; try { result = restClient.exchange(completeUrl, HttpMethod.POST,
	 * entity, String.class);
	 * LOGGER.info("The response from the Khalti is ->"+result.getBody());
	 * jsonNodeRoot = objectMapper.readTree(result.getBody()); JsonNode
	 * jsonNodeMerchant = jsonNodeRoot.get("merchant"); JsonNode jsonNodeUser =
	 * jsonNodeRoot.get("user"); JsonNode jsonNodeType = jsonNodeRoot.get("type");
	 * JsonNode jsonNodeState = jsonNodeRoot.get("state"); JsonNode jsonNodeIdx =
	 * jsonNodeRoot.get("idx"); JsonNode jsonNodeAmount =
	 * jsonNodeRoot.get("amount"); JsonNode jsonNodeFeeAmt =
	 * jsonNodeRoot.get("fee_amount"); JsonNode jsonNodeRefunded =
	 * jsonNodeRoot.get("refunded"); JsonNode jsonNodeCreated =
	 * jsonNodeRoot.get("created_on"); JsonNode jsonNodeBanker =
	 * jsonNodeRoot.get("ebanker");
	 * 
	 * KhaltiMerchant khaltiMerchant = new
	 * KhaltiMerchant(jsonNodeMerchant.get("idx").toString(),
	 * jsonNodeMerchant.get("name").toString(),
	 * jsonNodeMerchant.get("mobile").toString()); KhaltiUser khaltiUser = new
	 * KhaltiUser(jsonNodeUser.get("idx").toString(),
	 * jsonNodeUser.get("name").toString(), jsonNodeUser.get("mobile").toString());
	 * KhaltiType khaltiType = new KhaltiType(jsonNodeType.get("idx").toString(),
	 * jsonNodeType.get("name").toString()); KhaltiState khaltiState = new
	 * KhaltiState(jsonNodeState.get("idx").toString(),
	 * jsonNodeState.get("name").toString(),
	 * jsonNodeState.get("template").toString());
	 * response.setMerchant(khaltiMerchant); response.setUser(khaltiUser);
	 * response.setType(khaltiType); response.setState(khaltiState);
	 * response.setIdx(jsonNodeIdx.toString());
	 * response.setAmount(Integer.parseInt(jsonNodeAmount.toString()));
	 * response.setFee_amount(Integer.parseInt(jsonNodeFeeAmt.toString()));
	 * response.setCreated_on(jsonNodeCreated.toString());
	 * response.setEbanker(null);
	 * response.setRefunded(Boolean.valueOf(jsonNodeRefunded.toString()));
	 * 
	 * //
	 * LOGGER.info("Response being mapped is ->"+response.getMerchant().toString());
	 * 
	 * //response = objectMapper.readValue(result.getBody(), responseType); }
	 * catch(HttpStatusCodeException sce) { //
	 * LOGGER.info("INSIDE POSTDATA HTTPSTATUSCODE EXCEPTION");
	 * LOGGER.error("STATUS CODE: " + sce.getStatusCode()); // try { // //response =
	 * objectMapper.readValue(sce.getResponseBodyAsString(), responseType); // }
	 * catch (IOException e) { // // } } catch(RestClientException rce) { //
	 * LOGGER.info("INSIDE RESTCLIENT ERROR OF POSTDATA");
	 * LOGGER.error("ERROR MSG: " + rce.getMessage()); } catch(IOException ioEx) {
	 * // LOGGER.info("INSIDE IOEXCEPTION OF POST DATA"); LOGGER.error("ERROR MSG: "
	 * + ioEx.getMessage()); }
	 * 
	 * return response;
	 * 
	 * }
	 */

}
