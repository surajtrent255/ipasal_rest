package com.ishanitech.ipasal.ipasalwebservice.Services;

import com.ishanitech.ipasal.ipasalwebservice.dto.PaymentDTO;

public interface PaymentService {
    int addPaymentDetails(PaymentDTO paymentDTO);
    PaymentDTO getPaymentDetailsById(Integer paymentDetailsId);
    int updatePaymentDetails(PaymentDTO paymentDTO);
    void updatePaidStatus(Integer paymentDetailsId);
}
