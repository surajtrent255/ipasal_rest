package com.ishanitech.ipasal.ipasalwebservice.database.Mapper.CustomMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.ishanitech.ipasal.ipasalwebservice.dto.PaymentDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.PaymentMethodDTO;


public class CustomPaymentInfoMapper implements ResultSetMapper<PaymentDTO>{
	@Override
	public PaymentDTO map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		PaymentDTO paymentDTO = new PaymentDTO();
		paymentDTO.setPaymentId(r.getInt("payment_id"));
		paymentDTO.setStatus(r.getBoolean("status"));
		paymentDTO.setPaymentMethodId(r.getInt("payment_method_id"));
		paymentDTO.setAmount(r.getDouble("amount"));
		paymentDTO.setUniqueOrderIdentifier(r.getString("unique_order_identifier"));
		
		PaymentMethodDTO paymentMethod = new PaymentMethodDTO();
		paymentMethod.setId(r.getInt("id"));
		if(r.getBoolean("status") == false) {
			paymentMethod.setPaymentStatus("Unpaid");
		} else {
			paymentMethod.setPaymentStatus("paid");
		}
		paymentMethod.setPaymentName(r.getString("payment_name"));
		
		paymentDTO.setPaymentMethod(paymentMethod);
		return paymentDTO;
	}
	
}
