package com.ishanitech.ipasal.ipasalwebservice.utilities;

public class HateoasLinkBuilderUtil {
	private static final String PAGE = "?page=";
	private static final String SIZE = "?size=";
	private static final String AND = "&";
	public static String generatePageAndSizeLink(Integer pageNo, Integer pageSize) {
		return PAGE + pageNo + AND + SIZE + pageSize;
	}
	
	public static boolean hasNextPage(Integer pageNo, Integer totalPages) {
		return pageNo < totalPages;
	}
	
	public static boolean hasPrevPage(Integer pageNo) {
		return pageNo > 1;
	}
	
	public static boolean hasLastPage(Integer pageNo, Integer totalPages) {
		return (totalPages > 1) && (hasNextPage(pageNo, totalPages));
	}
	
	public static boolean hasFirstPage(Integer pageNo) {
		return hasPrevPage(pageNo);
	}
}
