package com.ishanitech.ipasal.ipasalwebservice.ServiceImpl;

import com.ishanitech.ipasal.ipasalwebservice.Services.DbService;
import com.ishanitech.ipasal.ipasalwebservice.Services.PaymentService;
import com.ishanitech.ipasal.ipasalwebservice.dao.PaymentDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.PaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private DbService dbService;

    @Autowired
    public PaymentServiceImpl(DbService dbService){
        this.dbService = dbService;
    }


    @Override
    public int addPaymentDetails(PaymentDTO paymentDTO) {
        PaymentDAO paymentDAO = dbService.getDao(PaymentDAO.class);
        return paymentDAO.addPaymentDetails(paymentDTO);
    }

    @Override
    public PaymentDTO getPaymentDetailsById(Integer paymentDetailsId) {
        PaymentDAO paymentDAO = dbService.getDao(PaymentDAO.class);
        return paymentDAO.getPaymentDetailsById(paymentDetailsId);
    }

    @Override
    public int updatePaymentDetails(PaymentDTO paymentDTO) {
        PaymentDAO paymentDAO = dbService.getDao(PaymentDAO.class);
        return paymentDAO.updatePaymentDetails(paymentDTO);
    }

    @Override
    public void updatePaidStatus(Integer paymentDetailsId) {
        PaymentDAO paymentDAO = dbService.getDao(PaymentDAO.class);
        paymentDAO.updatePaidStatus(paymentDetailsId);

    }
}
