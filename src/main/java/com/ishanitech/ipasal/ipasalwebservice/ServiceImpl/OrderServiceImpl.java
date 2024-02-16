package com.ishanitech.ipasal.ipasalwebservice.ServiceImpl;

import com.ishanitech.ipasal.ipasalwebservice.Services.DbService;
import com.ishanitech.ipasal.ipasalwebservice.Services.EmailService;
import com.ishanitech.ipasal.ipasalwebservice.Services.OrderServce;
import com.ishanitech.ipasal.ipasalwebservice.dao.OrderDAO;
import com.ishanitech.ipasal.ipasalwebservice.dao.PaymentDAO;
import com.ishanitech.ipasal.ipasalwebservice.dao.ProductDAO;
import com.ishanitech.ipasal.ipasalwebservice.dao.ShippingDAO;
import com.ishanitech.ipasal.ipasalwebservice.dao.UserDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.*;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomEmailException;
import com.ishanitech.ipasal.ipasalwebservice.utilities.CustomQueryCreator;
import com.ishanitech.ipasal.ipasalwebservice.utilities.ImageResourceUrlCreatorUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
@Service
public class OrderServiceImpl implements OrderServce {
	Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private DbService dbService;
    @Autowired
    ImageResourceUrlCreatorUtil imageResourceUrlCreatorUtil;
    
    @Autowired
    EmailService emailService;
    
    @Autowired
    public OrderServiceImpl(DbService dbService){
        this.dbService = dbService;
    }


    @Override
    @Transactional
    public int addOrder(NewOrderDto orderDTOS) {
        OrderDAO orderDAO = dbService.getDao(OrderDAO.class);
        ShippingDAO shippingDAO = dbService.getDao(ShippingDAO.class);
        PaymentDAO paymentDAO = dbService.getDao(PaymentDAO.class);
        UserDAO userDAO = dbService.getDao(UserDAO.class);

        //step1 add shipping detail
        //step2 add payment detail
        //step3 populate order table
        //step4 populate order details table
        //UserDTO user = new UserDTO();
        
        ShippingDTO shippinDetails; //= orderDTOS.getShippingAddress();
        PaymentDTO paymentInfo = orderDTOS.getPaymentDetail();
        
        if(orderDTOS.getShippingAddress() == null) {
        	shippinDetails = new ShippingDTO();
        	UserDTO user = orderDTOS.getUser();
        	shippinDetails.setPhone(user.getPhone());
        	shippinDetails.setCity(user.getCity());
        	shippinDetails.setAddress(user.getStreet());
        	shippinDetails.setEmail(user.getEmail());
        } else {
        	shippinDetails = orderDTOS.getShippingAddress();
        }
        
        if(orderDTOS.getOrderedBy() == null) {
            int generatedGuestUserId = userDAO.addUser(orderDTOS.getUser());
            orderDTOS.setOrderedBy(generatedGuestUserId);
            shippinDetails.setCustomerId(generatedGuestUserId);
        } else {
            shippinDetails.setCustomerId(orderDTOS.getOrderedBy());
        }

        int paymentId = paymentDAO.addPaymentDetails(paymentInfo);
        int shippingId = shippingDAO.addShippingDetails(shippinDetails);

        orderDTOS.setShippingDetailsId(shippingId);
        orderDTOS.setPaymentDetailsId(paymentId);
        

        int orderId = orderDAO.addIntoOrders(orderDTOS);
        List<NewOrderDetailsDto> orderDetailsDto = orderDTOS.getProductIds().stream()
                .map(productId -> {
                    NewOrderDetailsDto dto = new NewOrderDetailsDto();
                    dto.setProductId(productId);
                    dto.setOrderId(orderId);
                    return dto;
                })

                .collect(Collectors.toList());

        for(int i = 0; i < orderDTOS.getQuantities().size(); i++) {
            orderDetailsDto.get(i).setQuantity(orderDTOS.getQuantities().get(i));
        }
        
        orderDetailsDto.forEach(item -> {
        	dbService.getDao(ProductDAO.class).decreaseProductQuantity(item.getQuantity(), item.getProductId());
        });
        
        List<UserDTO> adminUsers = userDAO.getAllAdminUsers();
        String recievers = "";
        for(UserDTO adminUser : adminUsers) {
        	recievers += adminUser.getEmail() + ',';
        }
        //Email-send to admin on new order
        
        try {
        orderDAO.addIntoOrderDetails(orderDetailsDto);
        EmailDTO emailingDetail = new EmailDTO();
        emailingDetail.setFrom("no-reply@ishanitech.com");
        emailingDetail.setTo(recievers);
        emailingDetail.setSubject("New Order placed!");
        Map<String, Object> map = new HashMap<>();
        map.put("fName", orderDTOS.getUser().getfName());
        map.put("mName", orderDTOS.getUser().getmName());
        map.put("lName", orderDTOS.getUser().getlName());
        map.put("orderId", orderId);
        map.put("location", "Hattiban-1, Lalitpur");
        map.put("officeContactNo", "01-4331266");
        map.put("signature", "http://www.ishanitech.com/ishanitech");
        emailingDetail.setModel(map);
        //emailService.sendAccountActivationEmail(emailingDetail, "neworder");
        logger.info("#############Email-sent!!!");
        }catch(Exception ex){
        	logger.error(ex.getMessage());
        	throw new CustomEmailException("Something went wrong while sending email");
        }
//Email-send block ends 
        return orderId;
          
    }

    @Override
    public List<NewOrderDto> getAllOrders(Integer startingIndex, Integer maxPageLimit) {
        //OrderDAO orderDAO = dbService.getDao(OrderDAO.class);
        List<NewOrderDto> orderDetails = new ArrayList<>();//orderDAO.getAllOrders(startingIndex, maxPageLimit);
        for(NewOrderDto order : orderDetails) {
        	UserDTO user = dbService.getDao(UserDAO.class).getUserByUserId(order.getOrderedBy());
        	order.setUser(user);
        }
        return orderDetails;
    }
    
    @Override
	public List<NewOrderDto> getOrdersByUserId(HttpServletRequest request, Integer userId) {
		// TODO Auto-generated method stub
    	String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.ORDER);
    	OrderDAO orderDAO = dbService.getDao(OrderDAO.class);
    	List<NewOrderDto> orderDetails = new ArrayList<>();
    	orderDetails = orderDAO.getOrdersByUserId(caseQuery, userId);
    	if(CustomQueryCreator.checkParameter("action", request)) {
    		String action = (String) CustomQueryCreator.getParameterFromRequestObject("action", request);
    		orderDetails = reverseOrderList(action, orderDetails);
    	}
		return orderDetails;
	}

    @Override
	public List<NewOrderDto> getAllOrders(HttpServletRequest request) {
    	String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.ORDER);
		OrderDAO orderDAO = dbService.getDao(OrderDAO.class);
		List<NewOrderDto> orderDetails = new ArrayList<>();
		orderDetails = orderDAO.getAllOrders(caseQuery);
		for(NewOrderDto order: orderDetails) {
			UserDTO user = dbService.getDao(UserDAO.class).getUserByUserId(order.getOrderedBy());
			order.setUser(user);
		}
		
		if(CustomQueryCreator.checkParameter("action", request)) {
    		String action = (String) CustomQueryCreator.getParameterFromRequestObject("action", request);
    		orderDetails = reverseOrderList(action, orderDetails);
    	}
		return orderDetails;
	}

	@Override
	public List<ProductDTO> getMostBoughtProducts() {
    	OrderDAO orderDAO = dbService.getDao(OrderDAO.class);
    	ProductDAO productDAO = dbService.getDao(ProductDAO.class);
    	List<ProductDTO> productList = orderDAO.getMostBoughtProducts();
    	return imageResourceUrlCreatorUtil.createProductWithImages(productList, productDAO, ImageType.THUMBNAIL);
	}

	// retrieve order information of the specific order
	@Override
	public UserDTO getOrderByOrderId(Integer orderId) {
		OrderDAO orderDAO = dbService.getDao(OrderDAO.class);
		OrderDTO orderInfo = orderDAO.getOrderByOrderId(orderId);
		UserDTO userInfo = dbService.getDao(UserDAO.class).getUserByUserId(orderInfo.getOrderedBy());
		return userInfo;
	}
	
	@Override
	public NewOrderDto getSpecificOrderByOrderId(Integer orderId) {
		OrderDAO orderDAO = dbService.getDao(OrderDAO.class);
		NewOrderDto order = orderDAO.getSpecificOrderByOrderId(orderId);
		return order;
	}
	


	@Override
    public int changeOrderStatus(Integer orderId, Integer status) {
        OrderDAO orderDAO = dbService.getDao(OrderDAO.class);
        return orderDAO.changeOrderStatus(orderId,status);
    }

    @Override
    public int updateProductQuantity(Integer quantity, Integer orderDetailsId) {

        OrderDAO orderDAO = dbService.getDao(OrderDAO.class);
        return orderDAO.updateProductQuantity(quantity,orderDetailsId);
    }

    @Override
    public Integer cancelOrderById(Integer orderId) {
        OrderDAO orderDAO = dbService.getDao(OrderDAO.class);
        ProductDAO productDAO = dbService.getDao(ProductDAO.class);
        Integer updatedRowId = orderDAO.cancelOrderById(orderId);
        
        List<NewOrderDetailsDto> orderList = orderDAO.getOrderListByOrderId(orderId);
        
        for(int i = 0; i < orderList.size(); i++) {
        	int productId = orderList.get(i).getProductId();
        	int orderedQty = orderList.get(i).getQuantity();
        	productDAO.increaseProductQuantity(orderedQty, productId);
        }
        return updatedRowId;
    }

    @Override
	public Integer changeOrderStatusByOrderId(Integer orderId) {
		Integer updatedRowId = dbService.getDao(OrderDAO.class).changeOrderStatus(orderId, 2);
		return updatedRowId;
	}
    
    //returns total number of active orders user currenty has on database.
	@Override
	public int getTotalOrdersByUser(Integer userId) {
		return dbService.getDao(OrderDAO.class).getTotalOrdersByUser(userId);
	}


	@Override
	public List<ProductDTO> getOrderDetailsByOrderId(Integer orderId) {
		List<ProductDTO> orderDetails = dbService.getDao(OrderDAO.class).getOrderDetailsByOrderId(orderId);
		return imageResourceUrlCreatorUtil.createProductWithImages(orderDetails, dbService.getDao(ProductDAO.class), ImageType.THUMBNAIL);
	}


	@Override
	public int countTotalOrders() {
		return dbService.getDao(OrderDAO.class).countTotalOrders();
	}

		//This function sorts arraylist into reverse order using their id
		public static List<NewOrderDto> reverseOrderList(String actionName, List<NewOrderDto> listOfItems) {
			if(actionName.equalsIgnoreCase("prev")) {
				List<NewOrderDto> orders = listOfItems;
				orders.sort((o1, o2) -> {
	    			int orderId = o1.getOrderId();
	    			int orderd2 = o2.getOrderId();
	    			if(orderId == orderd2) {
	    				return 0;
	    			}
	    			return (orderId < orderd2) ? 1: -1;
	    		});
				
				return orders;
			} else {
				return listOfItems;
			}
		}


		@Override
		public PaymentDTO getPaymentInfoByOrderId(Integer orderId) {
			OrderDAO orderDao = dbService.getDao(OrderDAO.class);
			return orderDao.getPaymentInfoByOrderId(orderId);
		}


		@Override
		public List<NewOrderDto> getAllOrdersList(HttpServletRequest request) {
			String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.ORDER);
			OrderDAO orderDAO = dbService.getDao(OrderDAO.class);
			List<NewOrderDto> orderDetails = new ArrayList<>();
			orderDetails = orderDAO.getAllOrdersList(caseQuery);
			for(NewOrderDto order: orderDetails) {
				UserDTO user = dbService.getDao(UserDAO.class).getUserByUserId(order.getOrderedBy());
				order.setUser(user);
			}
			
			if(CustomQueryCreator.checkParameter("action", request)) {
	    		String action = (String) CustomQueryCreator.getParameterFromRequestObject("action", request);
	    		orderDetails = reverseOrderList(action, orderDetails);
	    	}
			return orderDetails;
		}


		@Override
		public List<NewOrderDto> getDeliveredOrdersList(HttpServletRequest request) {
			String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.ORDER);
			OrderDAO orderDAO = dbService.getDao(OrderDAO.class);
			List<NewOrderDto> orderDetails = new ArrayList<>();
			orderDetails = orderDAO.getDeliveredOrdersList(caseQuery);
			for(NewOrderDto order: orderDetails) {
				UserDTO user = dbService.getDao(UserDAO.class).getUserByUserId(order.getOrderedBy());
				order.setUser(user);
			}
			
			if(CustomQueryCreator.checkParameter("action", request)) {
	    		String action = (String) CustomQueryCreator.getParameterFromRequestObject("action", request);
	    		orderDetails = reverseOrderList(action, orderDetails);
	    	}
			return orderDetails;
		}


		@Override
		public List<NewOrderDto> getCancelledOrdersList(HttpServletRequest request) {
			String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.ORDER);
			OrderDAO orderDAO = dbService.getDao(OrderDAO.class);
			List<NewOrderDto> orderDetails = new ArrayList<>();
			orderDetails = orderDAO.getCancelledOrdersList(caseQuery);
			for(NewOrderDto order: orderDetails) {
				UserDTO user = dbService.getDao(UserDAO.class).getUserByUserId(order.getOrderedBy());
				order.setUser(user);
			}
			
			if(CustomQueryCreator.checkParameter("action", request)) {
	    		String action = (String) CustomQueryCreator.getParameterFromRequestObject("action", request);
	    		orderDetails = reverseOrderList(action, orderDetails);
	    	}
			return orderDetails;
		}


		@Override
		public List<OrderUpdateDTO> getOrderUpdateByOrderId(Integer orderId) {
			OrderDAO orderDAO = dbService.getDao(OrderDAO.class);
			List<OrderUpdateDTO> orderUpdates = orderDAO.getOrderUpdateByOrderId(orderId);
			for(OrderUpdateDTO oUpdate: orderUpdates) {
				UserDTO user = dbService.getDao(UserDAO.class).getUserByUserId(oUpdate.getCommentedBy());
				oUpdate.setUser(user);
			}
			return orderUpdates;
		}


		@Override
		@Transactional
		public Integer addOrderUpdate(OrderUpdateDTO orderUpdateDTO) {
			OrderDAO orderDAO = dbService.getDao(OrderDAO.class);
			return orderDAO.addOrderUpdate(orderUpdateDTO);
		}



}
