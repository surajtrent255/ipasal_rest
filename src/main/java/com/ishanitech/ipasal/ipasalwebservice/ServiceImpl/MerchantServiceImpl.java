package com.ishanitech.ipasal.ipasalwebservice.ServiceImpl;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.ishanitech.ipasal.ipasalwebservice.Services.DbService;
import com.ishanitech.ipasal.ipasalwebservice.Services.MerchantService;
import com.ishanitech.ipasal.ipasalwebservice.dao.MerchantDAO;
import com.ishanitech.ipasal.ipasalwebservice.dao.ProductDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.MerchantDTO;


/**
 * 
 * @author Tanchhowpa
 *
 * Apr 25, 2019 10:09:56 AM
 */


@Service
public class MerchantServiceImpl implements MerchantService {

	
//	private static final String STOCK_FILTER = "";
//	private static final Integer LAST_SEEN_ID = 0;
//	private static final Integer PAGE_SIZE = 9;
//	private static final String ACTION = "first";
	private final DbService dbService;
	
	
	public MerchantServiceImpl(DbService dbService) {
		this.dbService = dbService;
	}

	@Override
	public Integer addMerchant(MerchantDTO merchantDTO) {
		MerchantDAO merchantDAO = dbService.getDao(MerchantDAO.class);
		return merchantDAO.addMerchant(merchantDTO);
	}

	@Override
	public List<MerchantDTO> getAllMerchants() {
		MerchantDAO merchantDAO = dbService.getDao(MerchantDAO.class);
		List<MerchantDTO> returnedList = merchantDAO.getAllMerchants();
		return returnedList;
	}

	@Override
	public MerchantDTO getMerchantById(Integer merchantId) {
		MerchantDAO merchantDAO = dbService.getDao(MerchantDAO.class);
		MerchantDTO merchant = merchantDAO.getMerchantById(merchantId);
		return merchant;
	}

	@Override
	public Integer updateMerchant(Integer merchantId, MerchantDTO merchantDTO) {
		MerchantDAO merchantDAO = dbService.getDao(MerchantDAO.class);
		return merchantDAO.updateMerchant(merchantId, merchantDTO);
	}

	@Override
	public void removeMerchant(Integer merchantId) {
		MerchantDAO merchantDAO = dbService.getDao(MerchantDAO.class);
		ProductDAO productDAO = dbService.getDao(ProductDAO.class);
		productDAO.removeMerchantProducts(merchantId);
		merchantDAO.removeMerchant(merchantId);
		
	}
	
	//Commenting the 
	
	/*
	 * @Override public List<MerchantDTO> searchMerchant(HttpServletRequest request)
	 * {
	 * 
	 * String searchKey = ""; String caseQuery = generateQueryWithCase(request);
	 * 
	 * if (checkParameter("searchKey", request)) { searchKey = "'%" + (String)
	 * getParameterFromRequestObject("searchKey", request) + "%'"; }
	 * System.out.println("SEARCH KEY : " + searchKey); MerchantDAO merchantDAO =
	 * dbService.getDao(MerchantDAO.class); List<MerchantDTO> searchResults =
	 * merchantDAO.searchMerchant(searchKey, caseQuery);
	 * 
	 * if (checkParameter("action", request)) { String actionName = (String)
	 * getParameterFromRequestObject("action", request); searchResults =
	 * reverseMerchantList(actionName, searchResults); }
	 * 
	 * // return imageResourceUrlCreatorUtil.createProductWithImages(searchResults,
	 * productDAO, ImageType.THUMBNAIL); return searchResults; }
	 * 
	 */

	@Override
	public List<MerchantDTO> searchMerchant(String searchKey) {
		MerchantDAO merchantDAO = dbService.getDao(MerchantDAO.class);
		String extractedSearchKey = "%"+ searchKey +"%";
		List<MerchantDTO> merchant =  merchantDAO.searchMerchant(extractedSearchKey);
		return merchant;
	}
	
	
	/*
	 * 
	 * 
	 * // Generates the dynamic query based on filters and query parameters public
	 * static String generateQueryWithCase(HttpServletRequest request) { String
	 * caseQuery = ""; String stockFilter; Integer lastSeenId; String action;
	 * Integer pageSize;
	 * 
	 * if (checkParameter("stockFilter", request)) { stockFilter = (String)
	 * getParameterFromRequestObject("stockFilter", request); } else { stockFilter =
	 * STOCK_FILTER; }
	 * 
	 * if (checkParameter("last_seen", request)) { lastSeenId =
	 * Integer.parseInt(getParameterFromRequestObject("last_seen", request)); } else
	 * { lastSeenId = LAST_SEEN_ID; }
	 * 
	 * if (checkParameter("action", request)) { action = (String)
	 * getParameterFromRequestObject("action", request); } else { action = ACTION; }
	 * 
	 * if (checkParameter("pageSize", request)) { pageSize =
	 * Integer.parseInt(getParameterFromRequestObject("pageSize", request)); } else
	 * { pageSize = PAGE_SIZE; }
	 * 
	 * // switch (stockFilter.toLowerCase()) { // case "in-stock": // caseQuery +=
	 * " AND p.available_items > 0 "; // break; // // case "out-of-stock": //
	 * caseQuery += " AND p.available_items < 0 "; // break; // // default: //
	 * break; // } // // switch (action.toLowerCase()) { // case "next": // if
	 * (lastSeenId > 0) { // caseQuery += " AND p.merchant_id < " + lastSeenId; //
	 * caseQuery += " ORDER BY p.merchant_id DESC "; // } // break; // case "prev":
	 * // if (lastSeenId > 0) { // caseQuery += " AND p.merchant_id > " +
	 * lastSeenId; // caseQuery += " ORDER BY p.merchant_id ASC "; // } // break; //
	 * default: // caseQuery += " ORDER BY p.merchant_id DESC "; // break; // }
	 * 
	 * caseQuery += "LIMIT " + pageSize;
	 * 
	 * return caseQuery; }
	 * 
	 */
	
	// This function checks whether there is given parameter in request object
	public static boolean checkParameter(String parameterName, HttpServletRequest request) {
		return (request.getParameter(parameterName) != null ? true : false);
	}
	
	// This function returns request parameter values as generic type. You have to
	// cast the result in caller object
	@SuppressWarnings("unchecked")
	public static <T> T getParameterFromRequestObject(String parameter, HttpServletRequest request) {
		return (T) request.getParameter(parameter);
	}
	
	// This function sorts arraylist into reverse order using their id
	public static List<MerchantDTO> reverseMerchantList(String actionName, List<MerchantDTO> listOfItems) {
		if (actionName.equalsIgnoreCase("prev")) {
			List<MerchantDTO> merchants = listOfItems;
			merchants.sort((m1, m2) -> {
				int merchantId = m1.getMerchantId();
				int merchantId2 = m2.getMerchantId();
				if (merchantId == merchantId2) {
					return 0;
				}
				return (merchantId < merchantId2) ? 1 : -1;
			});

			return merchants;
		} else {
			return listOfItems;
		}
	}


	
}
