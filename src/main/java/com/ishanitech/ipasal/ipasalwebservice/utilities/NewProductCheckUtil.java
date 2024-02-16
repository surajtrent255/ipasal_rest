package com.ishanitech.ipasal.ipasalwebservice.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ishanitech.ipasal.ipasalwebservice.dto.ProductDTO;

@Component
public class NewProductCheckUtil {
	
	public List<ProductDTO> checkListofProducts(List<ProductDTO> sentProducts) throws ParseException {
		List<ProductDTO> products = sentProducts;
		if(products.size() > 0) {
			 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	         Date presentDate = new Date();
	         String todayDate = simpleDateFormat.format(presentDate);
	         Date currentDate = simpleDateFormat.parse(todayDate);
	        for (ProductDTO pro: products){
	            if (pro.getEntryDate() != null || !pro.getEntryDate().isEmpty()) {
	                Date entryDate = simpleDateFormat.parse(pro.getEntryDate());
	                int diffInDays = (int)( (currentDate.getTime() - entryDate.getTime())
	                        / (1000 * 60 * 60 * 24) );
	                if (diffInDays <= 5) {
	                    pro.setNewProduct(true);
	                } else {
	                	pro.setNewProduct(false);
	                }
	            }
	        }
		}
        return products;
	}
	
	public ProductDTO checkListSingleProduct(ProductDTO product) throws ParseException {
			ProductDTO pro = product;
			 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	         Date presentDate = new Date();
	         String todayDate = simpleDateFormat.format(presentDate);
	         Date currentDate = simpleDateFormat.parse(todayDate);
	            if (pro.getEntryDate() != null || !pro.getEntryDate().isEmpty()) {
	                Date entryDate = simpleDateFormat.parse(pro.getEntryDate());
	                int diffInDays = (int)( (currentDate.getTime() - entryDate.getTime())
	                        / (1000 * 60 * 60 * 24) );
	                if (diffInDays <= 5) {
	                    pro.setNewProduct(true);
	                } else {
	                	pro.setNewProduct(false);
	                }
	            }
        return pro;
	}
	
}
