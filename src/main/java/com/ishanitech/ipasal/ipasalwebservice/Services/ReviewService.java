package com.ishanitech.ipasal.ipasalwebservice.Services;

import com.ishanitech.ipasal.ipasalwebservice.dto.ReviewDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 
 * @author Tanchhowpa
 *
 * May 9, 2019 4:13:24 PM
 */

public interface ReviewService {

	Integer addReview(ReviewDTO reviewDTO);

	List<ReviewDTO> getAllReviews(HttpServletRequest request);

	List<ReviewDTO> getAllApprovedReviews(HttpServletRequest request);
	
	void deleteReview(Integer reviewId);

	ReviewDTO getReviewByReviewId(Integer reviewId);

	Integer updateReview(Integer reviewId, ReviewDTO reviewDTO);

	int changeReviewStatus(Integer reviewId, Integer status);

	String getReviewStatusByReviewId(Integer reviewId);
	
	Integer rejectReviewByReviewId(Integer reviewId);

	Integer approveReviewByReviewId(Integer reviewId);
	
	Integer processReviewByReviewId(Integer reviewId);

	List<ReviewDTO> getAllProcessingReviews(HttpServletRequest request);

	List<ReviewDTO> getAllReviewsByUserId(Integer userId);

	List<ReviewDTO> getAllReviewsByProductId(Integer productId);

	List<ReviewDTO> getAllReviewofProductByUserId(Integer productId, Integer userId);

	List<ReviewDTO> getReviewByProductId(Integer productId, HttpServletRequest request);

	//Returns the total number of reviews for a given product
	Integer countTotalReviewsForProduct(Integer productId);

	//Returns the total number of reviews from a user
	Integer countTotalReviewsByUserId(Integer userId);
	
	//Returns the total number of unapproved reviews to the admin
	Integer countTotalUnapprovedReviews();
	
	//Returns the total number of all the review
	Integer countTotalReviews();
	
	//Returns the total number of rejected reviews
	Integer countTotalRejectedReviews();

	//Returns the total number of approved reviews
	Integer countTotalApprovedReviews();

	List<ReviewDTO> getAllRejectedReviews(HttpServletRequest request);

	
	
	
}
