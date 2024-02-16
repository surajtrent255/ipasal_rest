package com.ishanitech.ipasal.ipasalwebservice.dao;

import com.ishanitech.ipasal.ipasalwebservice.dto.PaymentDTO;
import org.skife.jdbi.v2.sqlobject.*;

public interface PaymentDAO {

    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO payment_details(payment_method_id, amount, payment_date, status) "
    		+ "VALUES (:paymentMethodId, :amount, :paymentDate, :status)")
    public int addPaymentDetails(@BindBean PaymentDTO paymentDTO);

    @SqlQuery("SELECT * FROM payment_details where payment_id = :paymentId")
    public PaymentDTO getPaymentDetailsById(@Bind("paymentDetailsId") Integer paymentDetailsId);

    @SqlUpdate("UPDATE payment_details SET payment_method_id = :paymentMethodId, "
    		+ "amount = :amount, payment_date = :paymentDate, "
    		+ "status = :status WHERE payment_id = :paymentId")
    public int updatePaymentDetails(@BindBean PaymentDTO paymentDTO);

    @SqlUpdate("Update payment_details SET status = 1 where payment_id = :paymentId")
    public void updatePaidStatus(@Bind("paymentDetailsId") Integer paymentDetailsId);


}
