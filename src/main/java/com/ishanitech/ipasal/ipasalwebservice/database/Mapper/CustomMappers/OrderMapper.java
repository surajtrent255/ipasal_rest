package com.ishanitech.ipasal.ipasalwebservice.database.Mapper.CustomMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.ishanitech.ipasal.ipasalwebservice.dto.NewOrderDto;
import com.ishanitech.ipasal.ipasalwebservice.dto.PaymentDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.PaymentMethodDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ProductDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ShippingDTO;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class OrderMapper implements ResultSetMapper<NewOrderDto> {
    private NewOrderDto order;
	@Override
	public NewOrderDto map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        order = new NewOrderDto();
        ProductDTO product = new ProductDTO();
        product.setProductId(r.getInt("product_id"));
        product.setProductName(r.getString("product_name"));
        product.setRate(r.getFloat("rate"));
        
        order.setOrderId(r.getInt("order_id"));
        order.setOrderDate(r.getDate("order_date"));
        order.setStatus(r.getString("status"));
        order.setProducts(Arrays.asList(product));
        order.setQuantities(new ArrayList<Integer>(Arrays.asList(r.getInt("quantity"))));
        PaymentDTO payment = new PaymentDTO();
        //payment.setPaymentName(r.getString("payment_type"));
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO();
        paymentMethod.setPaymentName(r.getString("payment_name"));
        paymentMethod.setPaymentStatus(r.getString("payment_status"));
        payment.setPaymentMethod(paymentMethod);
        payment.setAmount(r.getDouble("amount"));
        payment.setPaymentDate(r.getDate("payment_date"));
        order.setPaymentDetail(payment);

        ShippingDTO shipping = new ShippingDTO();
        shipping.setType(r.getString("shipping_type"));
        order.setShippingAddress(shipping);

		return order;
	}

}