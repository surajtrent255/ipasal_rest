package com.ishanitech.ipasal.ipasalwebservice.dao;

import com.ishanitech.ipasal.ipasalwebservice.database.Mapper.CustomMappers.CustomPaymentInfoMapper;
import com.ishanitech.ipasal.ipasalwebservice.dto.*;

import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.Define;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

import java.util.Collection;
import java.util.List;

@UseStringTemplate3StatementLocator
public interface OrderDAO {
    
    @GetGeneratedKeys
    @SqlUpdate("insert into orders(order_date,shipping_details_id,payment_details_id,ordered_by, unique_order_identifier) values(:orderDate,:shippingDetailsId, :paymentDetailsId, :orderedBy, :uniqueOrderIdentifier)")
    public int addIntoOrders(@BindBean NewOrderDto orderDTO);

    @SqlBatch("insert into order_details(order_id,product_id,quantity) values(:orderId,:productId,:quantity)")
    public int[] addIntoOrderDetails(@BindBean Collection<NewOrderDetailsDto> orderDTOS);

    @SqlQuery
    public List<NewOrderDto> getAllOrders(@Define("caseQuery") String caseQuery);
    
    
    @SqlQuery("select * from order_details od inner join orders o on od.order_id = o.order_id inner join products p on od.product_id =p.product_id ")
    public List<OrderDetailsDTO> getOrderDetails(); //get all the order list

    @SqlUpdate("update orders set order_status = :status where order_id = :orderId")
    public Integer changeOrderStatus(@Bind("orderId") Integer orderId,
                                       @Bind("status") Integer status);

    @SqlUpdate("Update order_details set quantity = :quantity where order_id = :orderDetailsId")
    public Integer updateProductQuantity(@Bind("quantity") Integer quantity, @Bind("orderDetailsId") Integer orderDetailsId);

    @SqlQuery
    public List<NewOrderDto> getOrdersByUserId(@Define("caseQuery") String caseQuery, @Define("userId") Integer userId);
    
    @SqlQuery("SELECT * FROM orders WHERE order_id = :orderId")
    public NewOrderDto getSpecificOrderByOrderId(@Bind("orderId") Integer orderId);
    
    @GetGeneratedKeys
    @SqlUpdate("UPDATE orders SET order_status = 3 WHERE order_id = :orderId")
	public Integer cancelOrderById(@Bind("orderId") Integer orderId);
    
    @SqlQuery("SELECT count(*) FROM orders WHERE order_status = 1 AND ordered_by = :userId")
    public int getTotalOrdersByUser(@Bind("userId") Integer userId);

    
    @SqlQuery("SELECT p.*, ods.quantity FROM products p INNER JOIN order_details ods ON ods.product_id = p.product_id WHERE ods.order_id = :orderId")
	public List<ProductDTO> getOrderDetailsByOrderId(@Bind("orderId")Integer orderId);

    @SqlQuery("SELECT count(*) FROM orders WHERE order_status = 1")
	public int countTotalOrders();

//        Selecting the list of most ordered product id, as a popular product
    @SqlQuery("SELECT * FROM products p INNER JOIN order_details od ON p.product_id = od.product_id" + 
    			" GROUP BY od.product_id" + 
    			" ORDER BY COUNT(od.product_id) DESC" + 
    			" LIMIT 8")
    public List<ProductDTO> getMostBoughtProducts();

    //Retrieving order details of the specific user by userId
    @SqlQuery("SELECT * FROM orders WHERE order_id = :orderId")
    public OrderDTO getOrderByOrderId(@Bind("orderId") Integer orderId);

    
    @SqlQuery("SELECT pd.*, o.* FROM payment_details pd " + 
    		"    INNER JOIN orders o " + 
    		"    on o.payment_details_id = pd.payment_id " + 
    		"    where o.order_id = :orderId ")
    @RegisterMapper(CustomPaymentInfoMapper.class)
	public PaymentDTO getPaymentInfoByOrderId(@Bind("orderId") Integer orderId);

    
    @SqlQuery("SELECT * FROM order_details WHERE order_id =:orderId")
    public List<NewOrderDetailsDto> getOrderListByOrderId(@Bind("orderId") Integer orderId);

	@SqlQuery
	public List<NewOrderDto> getAllOrdersList(@Define("caseQuery") String caseQuery);

	@SqlQuery
	public List<NewOrderDto> getDeliveredOrdersList(@Define("caseQuery") String caseQuery);

	@SqlQuery
	public List<NewOrderDto> getCancelledOrdersList(@Define("caseQuery") String caseQuery);
	
	@SqlQuery("SELECT * FROM order_updates WHERE order_id=:orderId")
	public List<OrderUpdateDTO> getOrderUpdateByOrderId(@Bind("orderId") Integer orderId);

	@GetGeneratedKeys
	@SqlUpdate("INSERT INTO order_updates(order_id, comment_msg, commented_by) VALUES(:orderId, :commentMsg, :commentedBy)")
	public Integer addOrderUpdate(@BindBean OrderUpdateDTO orderUpdateDTO);
	
	
}
