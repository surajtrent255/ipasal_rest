package com.ishanitech.ipasal.ipasalwebservice.ServiceImpl;

import com.ishanitech.ipasal.ipasalwebservice.Services.DbService;
import com.ishanitech.ipasal.ipasalwebservice.Services.ReportService;
import com.ishanitech.ipasal.ipasalwebservice.dao.ReportDAO;
import com.ishanitech.ipasal.ipasalwebservice.dao.UserDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.NewOrderDto;
import com.ishanitech.ipasal.ipasalwebservice.dto.PaginationTypeClass;
import com.ishanitech.ipasal.ipasalwebservice.dto.ProductDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ReportDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.UserDTO;
import com.ishanitech.ipasal.ipasalwebservice.utilities.CustomQueryCreator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
@Service
public class ReportServiceImpl implements ReportService {
    private DbService dbService;

    @Autowired
    public ReportServiceImpl(DbService dbService){
        this.dbService = dbService;
    }

    @Override
    public List<ReportDTO> getReport() {
        ReportDAO reportDAO = dbService.getDao(ReportDAO.class);
        return reportDAO.getReport() ;
    }
    
    
    public List<ProductDTO> getSalesReport() {
    	ReportDAO reportDAO = dbService.getDao(ReportDAO.class);
    	return reportDAO.getSalesReport();
    }

	@Override
	public List<ProductDTO> getReportsBetweenRange(String startDate, String endDate) {
		ReportDAO reportDAO = dbService.getDao(ReportDAO.class);
		return reportDAO.getSalesReportsBetweenRange(startDate, endDate);
	}

	@Override
	public List<NewOrderDto> getOrdersBetweenRange(String startDate, String endDate, Integer status,
			HttpServletRequest request) {
		ReportDAO reportDAO = dbService.getDao(ReportDAO.class);
		String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.ORDER);
		List<NewOrderDto> orderDetails = new ArrayList<>();
		orderDetails = reportDAO.getOrdersBetweenRange(caseQuery, status , "'" + startDate + "'","'" + endDate + "'");
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
	public List<NewOrderDto> getAllOrdersBetweenRange(String startDate, String endDate, HttpServletRequest request) {
		ReportDAO reportDAO = dbService.getDao(ReportDAO.class);
		String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.ORDER);
		List<NewOrderDto> orderDetails = new ArrayList<>();
		orderDetails = reportDAO.getAllOrdersBetweenRange(caseQuery, "'" + startDate + "'","'" + endDate + "'");
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
}
