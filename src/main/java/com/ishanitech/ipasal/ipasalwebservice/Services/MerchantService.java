package com.ishanitech.ipasal.ipasalwebservice.Services;

import java.util.List;


import com.ishanitech.ipasal.ipasalwebservice.dto.MerchantDTO;

/**
 * 
 * @author Tanchhowpa
 *
 * Apr 12, 2019 10:54:13 AM
 */

public interface MerchantService {

	Integer addMerchant(MerchantDTO merchantDTO);

	List<MerchantDTO> getAllMerchants();

	MerchantDTO getMerchantById(Integer merchantId);

	Integer updateMerchant(Integer merchantId, MerchantDTO merchantDTO);

	void removeMerchant(Integer merchantId);

	List<MerchantDTO> searchMerchant(String searchKey);

	
}
