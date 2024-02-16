package com.ishanitech.ipasal.ipasalwebservice.rest;

import com.ishanitech.ipasal.ipasalwebservice.Services.ReportService;
import com.ishanitech.ipasal.ipasalwebservice.dto.NewOrderDto;
import com.ishanitech.ipasal.ipasalwebservice.dto.ProductDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ReportDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.Response;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomSqlException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/report")
public class ReportResources {
	private final Logger logger = LoggerFactory.getLogger(ReportResources.class);
	private ReportService reportService;

	@Autowired
	public ReportResources(ReportService reportService) {
		this.reportService = reportService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Response<?> getReports() {
		List<ReportDTO> reports;
		try {
			reports = reportService.getReport();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new CustomSqlException("Something went wrong in database while getting reports.");
		}
		if (reports != null && reports.size() > 0) {
			return Response.ok(reports, HttpStatus.OK.value(), HttpStatus.OK.name());
		}

		throw new ResourceNotFoundException("Currently there are no reports in database.");
	}
	
	@GetMapping("/topSales")
	public Response<?> getSalesReport() {
		List<ProductDTO> reports;
		try {
			reports = reportService.getSalesReport();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new CustomSqlException("Something went wrong in database while getting reports.");
		}
		if(reports != null && reports.size() > 0) {
			return Response.ok(reports, HttpStatus.OK.value(), HttpStatus.OK.name());
		}
		throw new ResourceNotFoundException("Currently there are no reports in the database");
	}
	
	//Getting the reports between two dates 
    @GetMapping("/salesRange")
    public Response<?> getReportsBetweenRange(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate){
    	List<ProductDTO> reportList;
    	try{
	       reportList  = reportService.getReportsBetweenRange(startDate, endDate);
        } catch (Exception e) {
	        logger.error(e.getMessage());
	        throw new CustomSqlException("Something went wrong while getting reports from database");
        }
	    if(reportList != null && reportList.size() > 0) {
	    	return Response.ok(reportList, HttpStatus.OK.value(), HttpStatus.OK.name());
	    }
	    throw new ResourceNotFoundException("Currently there are no reports in the database");
    }
    
    
    @GetMapping("/ordersRange")
    public Response<?> getOrdersBetweenRange(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("status") Integer status, HttpServletRequest request) {
		List<NewOrderDto> orders;
		try {
			orders = reportService.getOrdersBetweenRange(startDate, endDate, status, request);
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
    
    
    @GetMapping("/allOrdersRange")
    public Response<?> getAllOrdersBetweenRange(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, HttpServletRequest request) {
		List<NewOrderDto> orders;
		try {
			orders = reportService.getAllOrdersBetweenRange(startDate, endDate, request);
			//logger.info(orders.get(0).getOrderDate().toString());
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

}
