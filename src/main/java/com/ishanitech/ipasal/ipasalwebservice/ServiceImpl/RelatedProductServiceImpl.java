package com.ishanitech.ipasal.ipasalwebservice.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ishanitech.ipasal.ipasalwebservice.Services.DbService;
import com.ishanitech.ipasal.ipasalwebservice.Services.RelatedProductService;
import com.ishanitech.ipasal.ipasalwebservice.dao.RelatedProductDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.RelatedProductDTO;
/**
 * 
 * @author Tanchhowpa
 *
 * Created on: Feb 1, 2019 5:36:31 PM
 */

@Service
public class RelatedProductServiceImpl implements RelatedProductService {
	
	private final DbService dbService;
	
	public RelatedProductServiceImpl(DbService dbService) {
		this.dbService = dbService;
	}
	
	@Override
	public void addRelatedProducts(List<RelatedProductDTO> relatedProducts) {
		RelatedProductDAO relatedProductDAO = dbService.getDao(RelatedProductDAO.class);
		
		
	}

	@Override
	public void removeRelatedProduct(Integer mainProductId, Integer relatedProductId) {
		RelatedProductDAO relatedProductDAO = dbService.getDao(RelatedProductDAO.class);
		
	}

}
