package com.ishanitech.ipasal.ipasalwebservice.rest;

import com.ishanitech.ipasal.ipasalwebservice.Services.PaymentMethodService;
import com.ishanitech.ipasal.ipasalwebservice.dto.PaymentMethodDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.Response;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomSqlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Azens Eklak
 * Created On 2019-03-22
 */

@RequestMapping("api/v1/payment-methods")
@RestController
public class PaymentMethodResource {
    private Logger logger = LoggerFactory.getLogger(PaymentMethodResource.class);
    private PaymentMethodService paymentMethodService;

    @Autowired
    public PaymentMethodResource(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    /*
    * RestController to add the payment Method
    * @params PaymentMethodDTO
    * @returns paymentId
    * */
    @RequestMapping(method = RequestMethod.POST)
    public Response<?> addPaymentMethod(@RequestBody PaymentMethodDTO paymentMethodDTO){
        Integer paymentId = null;
        try{
            paymentId = paymentMethodService.addPaymentMethod(paymentMethodDTO);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomSqlException("Error while inserting the payment method");
        }
        return Response.ok(paymentId, HttpStatus.OK.value(), HttpStatus.OK.name());
    }

    /*
    * Controller to retrieve all the payment methods for the admin panel
    * @return List<PaymentMethodDTO>
    * */
    @GetMapping(value = "/all")
    public Response<?> getAllPaymentMethods(){
        List<PaymentMethodDTO> allPaymentMethodList;
        try{
            allPaymentMethodList = paymentMethodService.getAllPaymentMethods();
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomSqlException("Error while retrieving all payment methods");
        }
        return Response.ok(allPaymentMethodList, HttpStatus.OK.value(), HttpStatus.OK.name());
    }

    /*
    * Controller to retrieve all the active payment methods for the customer checkout payment options
    * @return List<PaymentMethodDTO>
    * */
    @GetMapping(value = "/active")
    public Response<?> getAllActivePaymentMethods(){
        List<PaymentMethodDTO> allActivePaymentMethods;
        try{
            allActivePaymentMethods = paymentMethodService.getAllActivePaymentMethods();
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomSqlException("Error while retrieving all active payment methods");
        }
        return Response.ok(allActivePaymentMethods, HttpStatus.OK.value(), HttpStatus.OK.name());
    }

    /*
    * Controller for updating the payment Status
    * @return void
    * */

    /*
    * Controller to update the whole payment details, changing typo errors and payment status
    * @return paymentId
    * */
    @PutMapping(value = "/update/{paymentId}")
    public Response<?> updatePaymentMethod(@PathVariable Integer paymentId,
                                           @RequestBody PaymentMethodDTO paymentMethodDTO){
        try{
            paymentMethodService.updatePaymentMethod(paymentId, paymentMethodDTO);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomSqlException("Error updating payment method details");
        }
        return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), HttpStatus.OK.name());
    }

    /*
    * Controller to delete the payment method
    * */

    /*
    * Getting the payment method using the id
    * */
    @GetMapping("/{id}")
    public Response<?> getPaymentMethodById(@PathVariable("id") Integer id){
        PaymentMethodDTO paymentMethod;
        try {
            paymentMethod = paymentMethodService.getPaymentMethodById(id);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomSqlException("Cannot get the payment method information for the given payment type.");
        }
        return Response.ok(paymentMethod, HttpStatus.OK.value(), HttpStatus.OK.name());
    }
}
