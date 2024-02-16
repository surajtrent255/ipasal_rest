package com.ishanitech.ipasal.ipasalwebservice.Services;

import com.ishanitech.ipasal.ipasalwebservice.dto.NewOrderDto;
import com.ishanitech.ipasal.ipasalwebservice.dto.OrderUpdateDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.PaymentDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ProductDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.UserDTO;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface OrderServce {

	int addOrder(NewOrderDto orderDTO);

	List<NewOrderDto> getAllOrders(Integer startingIndex, Integer maxPageLimit);

	int changeOrderStatus(Integer orderId, Integer Status);

	int updateProductQuantity(Integer quantity, Integer orderDetailsId);

	List<NewOrderDto> getOrdersByUserId(HttpServletRequest request, Integer userId);
	
	Integer cancelOrderById(Integer orderId);

	int getTotalOrdersByUser(Integer userId);
	
	int countTotalOrders();

	List<ProductDTO> getOrderDetailsByOrderId(Integer orderId);

	Integer changeOrderStatusByOrderId(Integer orderId);

	List<NewOrderDto> getAllOrders(HttpServletRequest request);

	//retrieving the most ordered product ids
	List<ProductDTO> getMostBoughtProducts();

	// Retrieve order info about the specific order Id
	UserDTO getOrderByOrderId(Integer orderId);
	
	NewOrderDto getSpecificOrderByOrderId(Integer orderId);

	PaymentDTO getPaymentInfoByOrderId(Integer orderId);

	List<NewOrderDto> getAllOrdersList(HttpServletRequest request);

	List<NewOrderDto> getDeliveredOrdersList(HttpServletRequest request);

	List<NewOrderDto> getCancelledOrdersList(HttpServletRequest request);

	List<OrderUpdateDTO> getOrderUpdateByOrderId(Integer orderId);

	Integer addOrderUpdate(OrderUpdateDTO orderUpdateDTO);
}
