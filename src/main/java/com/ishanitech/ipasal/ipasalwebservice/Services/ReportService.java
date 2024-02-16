package com.ishanitech.ipasal.ipasalwebservice.Services;

import com.ishanitech.ipasal.ipasalwebservice.dto.NewOrderDto;
import com.ishanitech.ipasal.ipasalwebservice.dto.ProductDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ReportDTO;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface ReportService {
    List<ReportDTO> getReport();
    List<ProductDTO> getSalesReport();
	List<ProductDTO> getReportsBetweenRange(String startDate, String endDate);
	List<NewOrderDto> getOrdersBetweenRange(String startDate, String endDate, Integer status,
			HttpServletRequest request);
	List<NewOrderDto> getAllOrdersBetweenRange(String startDate, String endDate, HttpServletRequest request);
}
