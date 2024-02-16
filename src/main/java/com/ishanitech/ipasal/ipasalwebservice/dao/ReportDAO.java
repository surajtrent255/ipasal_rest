package com.ishanitech.ipasal.ipasalwebservice.dao;

import com.ishanitech.ipasal.ipasalwebservice.dto.NewOrderDto;
import com.ishanitech.ipasal.ipasalwebservice.dto.ProductDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ReportDTO;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Define;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

import java.util.List;

@UseStringTemplate3StatementLocator
public interface ReportDAO {

    @SqlQuery("SELECT p.*, c.*, o.*, SUM(od.quantity) AS total_quantity FROM products p INNER JOIN product_category pc ON p.product_id = pc.product_id " +
            " INNER JOIN category c ON c.category_id = pc.category_id " +
            " INNER JOIN order_details od ON p.product_id = od.product_id " +
            " INNER JOIN orders o ON o.order_id = od.order_id GROUP BY(p.product_id) ")
    public List<ReportDTO> getReport();
    // gets the report based on the products : it only returns one order id - order data is getting lost
    // and if group by order_id is done ,the product data is getting lost
    
    
    @SqlQuery("SELECT p.*, c.*, o.*, SUM(od.quantity) AS total_sold_quantity FROM products p INNER JOIN product_category pc ON p.product_id = pc.product_id " + 
    		" INNER JOIN category c ON c.category_id = pc.category_id " + 
    		" INNER JOIN order_details od ON p.product_id = od.product_id " + 
    		" INNER JOIN orders o ON o.order_id = od.order_id WHERE o.order_status = 2 GROUP BY(p.product_id) ORDER BY total_sold_quantity DESC LIMIT 10")
    public List<ProductDTO> getSalesReport();


    @SqlQuery("SELECT p.*, c.*, o.*, sum(od.quantity) AS total_sold_quantity FROM products p INNER JOIN product_category pc on p.product_id = pc.product_id " + 
    		" INNER JOIN category c ON c.category_id = pc.category_id " + 
    		" INNER JOIN order_details od ON p.product_id = od.product_id " + 
    		" INNER JOIN orders o ON o.order_id = od.order_id WHERE o.order_date BETWEEN :startDate and :endDate AND o.order_status = 2 GROUP BY(p.product_id) ORDER BY total_sold_quantity DESC LIMIT 10 ")
	public List<ProductDTO> getSalesReportsBetweenRange(@Bind("startDate") String startDate, @Bind("endDate") String endDate);
    
	@SqlQuery
	public List<NewOrderDto> getOrdersBetweenRange(@Define("caseQuery") String caseQuery, @Define("status") Integer status, @Define("startDate") String startDate, @Define("endDate") String endDate);

	@SqlQuery
	public List<NewOrderDto> getAllOrdersBetweenRange(@Define("caseQuery") String caseQuery, @Define("startDate") String startDate, @Define("endDate") String endDate);
    
    
}