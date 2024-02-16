package com.ishanitech.ipasal.ipasalwebservice.ServiceImpl;

import com.ishanitech.ipasal.ipasalwebservice.Services.DbService;
import com.ishanitech.ipasal.ipasalwebservice.Services.PaymentMethodService;
import com.ishanitech.ipasal.ipasalwebservice.dao.PaymentMethodDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.PaymentMethodDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Azens Eklak
 * Created On 2019-03-22
 */

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    private DbService dbService;

    @Autowired
    public PaymentMethodServiceImpl(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public int addPaymentMethod(PaymentMethodDTO paymentMethodDTO) {
        if (dbService != null){
            PaymentMethodDAO paymentMethodDAO = dbService.getDao(PaymentMethodDAO.class);
            return paymentMethodDAO.insertPaymentMethod(paymentMethodDTO);
        }else {
            return 0;
        }
    }

    @Override
    public List<PaymentMethodDTO> getAllPaymentMethods() {
        PaymentMethodDAO paymentMethodDAO = dbService.getDao(PaymentMethodDAO.class);
        return paymentMethodDAO.getAllPaymentMethods();
    }

    @Override
    public List<PaymentMethodDTO> getAllActivePaymentMethods() {
        PaymentMethodDAO paymentMethodDAO = dbService.getDao(PaymentMethodDAO.class);
        return paymentMethodDAO.getAllActivePaymentMethods("online");
    }

    @Override
    public void updatePaymentMethod(int paymentId, PaymentMethodDTO paymentMethodDTO) {
        PaymentMethodDAO paymentMethodDAO = dbService.getDao(PaymentMethodDAO.class);
        paymentMethodDAO.updatePaymentMethod(paymentId, paymentMethodDTO);
    }

    @Override
    public PaymentMethodDTO getPaymentMethodById(Integer id) {
        PaymentMethodDAO paymentMethodDAO = dbService.getDao(PaymentMethodDAO.class);
        return paymentMethodDAO.getPaymentMethodById(id);
    }
}
