package com.ishanitech.ipasal.ipasalwebservice.ServiceImpl;


import com.ishanitech.ipasal.ipasalwebservice.Services.DbService;
import com.ishanitech.ipasal.ipasalwebservice.Services.VerificationTokenService;
import com.ishanitech.ipasal.ipasalwebservice.dao.UserDAO;
import com.ishanitech.ipasal.ipasalwebservice.dao.VerificationTokenDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.VerificationTokenDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
    Logger logger = LoggerFactory.getLogger(VerificationTokenServiceImpl.class);
    private DbService dbService;
    public VerificationTokenServiceImpl(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public int addVerificationToken(VerificationTokenDTO tokenDTO) {
        VerificationTokenDAO verificationDao = dbService.getDao(VerificationTokenDAO.class);
        return verificationDao.addVerificationToken(tokenDTO); 
	}

    @Override
    public VerificationTokenDTO getVerificationToken(String validationToken) {
        VerificationTokenDAO verificationDao = dbService.getDao(VerificationTokenDAO.class);
        return verificationDao.getVerificationToken(validationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationTokenDAO verificationDao = dbService.getDao(VerificationTokenDAO.class);
        VerificationTokenDTO tokenDetails = verificationDao.getVerificationToken(token);
        if(tokenDetails == null) {
            return "INVALID_TOKEN";
        }

        // Calendar calendar = Calendar.getInstance();
        // if((tokenDetails.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
        //     return "TOKEN_EXPIRED";
        // }

        UserDAO userDAO = dbService.getDao(UserDAO.class);
        userDAO.enableUserAccount(tokenDetails.getUserId());
        verificationDao.deleteToken(token);
        return "VALID_TOKEN";
    }

    //Service to check if the token is valid or not
    @Override
    public Integer resetPasswordTokenVerification(String token) {
        VerificationTokenDAO verificationDao = dbService.getDao(VerificationTokenDAO.class);
        VerificationTokenDTO tokenDetails = verificationDao.getVerificationToken(token);
        if(tokenDetails != null) {
            verificationDao.deleteToken(token);
            return tokenDetails.getUserId();
        }else {
            return null;
        }
    }

}