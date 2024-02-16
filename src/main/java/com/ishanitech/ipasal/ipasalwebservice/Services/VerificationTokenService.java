package com.ishanitech.ipasal.ipasalwebservice.Services;

import com.ishanitech.ipasal.ipasalwebservice.dto.VerificationTokenDTO;

public interface VerificationTokenService{
    public int addVerificationToken(VerificationTokenDTO tokenDTO);
    public VerificationTokenDTO getVerificationToken(String validationToken);
    public String validateVerificationToken(String token);
    public Integer resetPasswordTokenVerification(String token);
}