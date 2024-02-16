package com.ishanitech.ipasal.ipasalwebservice.dao;

import com.ishanitech.ipasal.ipasalwebservice.dto.PaymentMethodDTO;
import org.skife.jdbi.v2.sqlobject.*;

import java.util.List;

/**
 * @author Azens Eklak
 * Created On 2019-03-22
 */

public interface PaymentMethodDAO {

    /*
    * Inserting payment method details into database using PaymentMethodDTO
    * Get the Id of the inserted payment method suing @GetGeneratedKeys
    * */
    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO payment_method(payment_name, payment_status) VALUES(:paymentName, :paymentStatus)")
    public Integer insertPaymentMethod(@BindBean PaymentMethodDTO paymentMethodDTO);


    /*
    * Retrieving all the list of the payment methods to view in the admin panel
    * */
    @SqlQuery("SELECT * FROM payment_method")
    public List<PaymentMethodDTO> getAllPaymentMethods();


    /*
    * Retrieve all the list of the payment methods to view to the customer during the checkout
    * @returns the list of the PaymentMethodDTO which has status active
    * */
    @SqlQuery("SELECT * FROM payment_method WHERE payment_status = :status")
    public List<PaymentMethodDTO> getAllActivePaymentMethods(@Bind("status") String status);

    /*
    * Update PaymentMethodDTO's status and other details
    * */
    @SqlUpdate("UPDATE payment_method SET payment_name = :paymentName, payment_status = :paymentStatus WHERE id = :paymentId")
    public void updatePaymentMethod(@Bind("paymentId") Integer paymentId,
                                    @BindBean PaymentMethodDTO paymentMethodDTO);

    /*
    * Get Payment Method for edit mode using the payment method Id
    * */
    @SqlQuery("SELECT * FROM payment_method WHERE id = :paymentId")
    public PaymentMethodDTO getPaymentMethodById(@Bind("paymentId") Integer paymentId);
}
