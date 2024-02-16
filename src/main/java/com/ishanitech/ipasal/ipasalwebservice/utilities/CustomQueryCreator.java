package com.ishanitech.ipasal.ipasalwebservice.utilities;


import javax.servlet.http.HttpServletRequest;

import com.ishanitech.ipasal.ipasalwebservice.dto.PaginationTypeClass;

public class CustomQueryCreator {
	private static final String STOCK_FILTER = "";
    private static final Integer LAST_SEEN_ID = 0;
    private static final Integer PAGE_SIZE = 9;
    private static final String ACTION = "first";
    
	// Generates the dynamic query based on filters and query parameters
    public static String generateQueryWithCase(HttpServletRequest request, PaginationTypeClass ptc) {
        String caseQuery = "";
        String stockFilter;
        Integer lastSeenId;
        String action;
        Integer pageSize;
        
        if (checkParameter("last_seen", request)) {
            lastSeenId = Integer.parseInt(getParameterFromRequestObject("last_seen", request));
        } else {
            lastSeenId = LAST_SEEN_ID;
        }
        
        if (checkParameter("action", request)) {
            action = (String) getParameterFromRequestObject("action", request);
        } else {
            action = ACTION;
        }
        
        if (checkParameter("pageSize", request)) {
            pageSize = Integer.parseInt(getParameterFromRequestObject("pageSize", request));
        } else {
            pageSize = PAGE_SIZE;
        }

        
        switch(ptc) {
        	case PRODUCT:
        		if (checkParameter("stockFilter", request)) {
                    stockFilter = (String) getParameterFromRequestObject("stockFilter", request);
                } else {
                    stockFilter = STOCK_FILTER;
                }
        		switch (stockFilter.toLowerCase()) {
	                case "in-stock":
	                    caseQuery += " AND p.available_items > 0 ";
	                    break;
	
	                case "out-of-stock":
	                    caseQuery += " AND p.available_items < 0 ";
	                    break;
	
	                default:
	                    break;
        		}
        		
        		switch (action.toLowerCase()) {
                case "next":
                    if (lastSeenId > 0) {
                        caseQuery += " AND p.product_id < " + lastSeenId;
                        caseQuery += " ORDER BY p.product_id DESC ";
                    }
                    break;
                case "prev":
                    if (lastSeenId > 0) {
                        caseQuery += " AND p.product_id > " + lastSeenId;
                        caseQuery += " ORDER BY p.product_id ASC ";
                    }
                    break;
                default:
                    caseQuery += " ORDER BY p.product_id DESC ";
                    break;
        		}
        		break;
        	case ORDER:
        		switch(action.toLowerCase()) {
			    	case "next":
			    		if(lastSeenId > 0) {
			    			caseQuery += " AND o.order_id < " + lastSeenId;
			    			caseQuery += " ORDER BY o.order_id DESC ";
			    		}
			    		break;
			    	case "prev":
			    		if(lastSeenId > 0) {
							caseQuery += " AND o.order_id > " + lastSeenId;
							caseQuery += " ORDER BY o.order_id ASC ";
						}
			    		break;
			    	default: 
			    		caseQuery += " ORDER BY o.order_id DESC ";
			    		break;
	        		}
        		break;
        		
        	case REVIEW:
        		switch (action.toLowerCase()) {
                case "next":
                    if (lastSeenId > 0) {
                        caseQuery += " AND r.review_id < " + lastSeenId;
                        caseQuery += " ORDER BY r.review_id DESC ";
                    }
                    break;
                case "prev":
                    if (lastSeenId > 0) {
                        caseQuery += " AND r.review_id > " + lastSeenId;
                        caseQuery += " ORDER BY r.review_id ASC ";
                    }
                    break;
                default:
                    caseQuery += " ORDER BY r.review_id DESC ";
                    break;
        		}
        		break;
        	case USER:
        		break;
        	default: 
        		break;
        }
        caseQuery += "LIMIT " + pageSize;
        return caseQuery;
    }

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
}
