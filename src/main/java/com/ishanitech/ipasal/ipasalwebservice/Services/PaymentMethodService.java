package com.ishanitech.ipasal.ipasalwebservice.Services;

import com.ishanitech.ipasal.ipasalwebservice.dto.PaymentMethodDTO;

import java.util.List;

/**
 * @author Azens Eklak
 * Created On 2019-03-22
 */
public interface PaymentMethodService {

    int addPaymentMethod(PaymentMethodDTO paymentMethodDTO);
    List<PaymentMethodDTO> getAllPaymentMethods();
    List<PaymentMethodDTO> getAllActivePaymentMethods();
    void updatePaymentMethod(int paymentId, PaymentMethodDTO paymentMethodDTO);
    PaymentMethodDTO getPaymentMethodById(Integer id);
}
