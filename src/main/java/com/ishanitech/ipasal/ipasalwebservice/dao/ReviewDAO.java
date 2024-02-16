package com.ishanitech.ipasal.ipasalwebservice.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Define;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

import com.ishanitech.ipasal.ipasalwebservice.dto.ReviewDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ReviewDetailsDTO;

/**
 * 
 * @author Tanchhowpa
 * @author Yoomes <yoomesbhujel@gmail.com>
 * May 9, 2019 4:38:26 PM
 */


@UseStringTemplate3StatementLocator
public interface ReviewDAO {

	//Queries for 'review' table in the database 
	
	
	@GetGeneratedKeys
	@SqlUpdate("INSERT INTO review(review_title, review_desc, pros, cons, rating, user_id, product_id) VALUES(:reviewTitle, :reviewDesc, :pros, :cons, :rating, :userId, :productId)")
	Integer addReview(@BindBean ReviewDTO reviewDTO);

	
	//Gets all the reviews
	@SqlQuery
	List<ReviewDTO> getAllReviews(@Define("caseQuery") String caseQuery);

	//Gets all the approved reviews
	@SqlQuery //("SELECT * FROM review r INNER JOIN review_details rd ON r.review_id = rd.review_id WHERE r.deleted = 0 AND r.approved = 1 AND rd.review_status = 2")
	List<ReviewDTO> getAllApprovedReviews(@Define("caseQuery") String caseQuery);
	
	//Deletes the current review
	@SqlUpdate("UPDATE review SET deleted = 1 WHERE review_id = :reviewId")
	void deleteReview(@Bind("reviewId") Integer reviewId);

	//Gets the review by supplied review Id
	@SqlQuery("SELECT * FROM review WHERE review_id = :reviewId AND deleted = 0")
	ReviewDTO getReviewByReviewId(@Bind("reviewId") Integer reviewId);

	//Updates the current review 
//	@SqlUpdate("UPDATE review r SET r.review_title =:reviewTitle, r.review_desc =:reviewDesc, r.pros =:pros, r.cons =:cons, r.rating =:rating WHERE r.review_id = :reviewId")
//	Integer updateReview(@Bind("reviewId") Integer reviewId, @BindBean ReviewDTO reviewDTO);

	
	@SqlUpdate("UPDATE review r SET r.review_title =:reviewTitle, r.review_desc =:reviewDesc, r.pros =:pros, r.cons =:cons, r.rating =:rating, r.review_date =:reviewDate WHERE r.review_id = :reviewId")
	Integer updateReview(@Bind("reviewId") Integer reviewId, @BindBean ReviewDTO reviewDTO);
	
	// Queries for 'review_details' table in the database
	//Adds the details of a entered review
	@SqlUpdate("INSERT INTO review_details(review_id, user_id, product_id) VALUES(:reviewId, :userId, :productId)")
	void addReviewDetails(@BindBean ReviewDetailsDTO reviewDetails);

	@SqlUpdate("UPDATE review_details SET review_date =:reviewDate WHERE review_id =:reviewId" )
	void updateReviewDateinReviewDetails(@Bind("reviewDate") String reviewDate, @Bind("reviewId") Integer reviewId);
	
	//Changes the review status of a review based on review Id
	@SqlUpdate("UPDATE review_details SET review_status =:status WHERE review_id =:reviewId")
	int changeReviewStatus(@Bind("reviewId") Integer reviewId, @Bind("status") Integer status);

	//Gets the current status of the review (processing/approved/rejected)
	@SqlQuery("SELECT status FROM review_status rs INNER JOIN review_details rd ON rd.review_status = rs.status_id INNER JOIN review r ON r.review_id = rd.review_id WHERE r.review_id =:reviewId")
	String getReviewStatusByReviewId(@Bind("reviewId") Integer reviewId);

	//Sets the status of the review_status to rejected
	@SqlUpdate("UPDATE review_details rd, review r SET rd.review_status = 3, r.approved = 0 WHERE rd.review_id =:reviewId AND r.review_Id =:reviewId")
	Integer rejectReviewByReviewId(@Bind("reviewId") Integer reviewId);

	//Sets the status of the review_status to approved
	@SqlUpdate("UPDATE review_details rd, review r SET rd.review_status = 2, r.approved = 1 WHERE rd.review_id =:reviewId AND r.review_Id =:reviewId")
	Integer approveReviewByReviewId(@Bind("reviewId") Integer reviewId);

	//Changes the state of review to processing
	@SqlUpdate("UPDATE review_details rd, review r SET rd.review_status = 1, r.approved = 0 WHERE rd.review_id =:reviewId AND r.review_Id =:reviewId")
	Integer processReviewByReviewId(@Bind("reviewId") Integer reviewId);
	
	//Gets all the reviews that are in processing status
	@SqlQuery //("SELECT * FROM review r INNER JOIN review_details rd ON r.review_id = rd.review_id WHERE rd.review_status = 1 AND r.deleted = 0")
	List<ReviewDTO> getAllProcessingReviews(@Define("caseQuery") String caseQuery);

	
	//Gets all the reviews that are in rejected state
	@SqlQuery //("SELECT * FROM review r INNER JOIN review_details rd ON r.review_id = rd.review_id WHERE rd.review_status = 3 AND r.deleted = 0")
	List<ReviewDTO> getAllRejectedReviews(@Define("caseQuery") String caseQuery);
	
	
	//Gets all the reviews from a particular user
	@SqlQuery("SELECT * FROM review r INNER JOIN review_details rd ON r.review_id = rd.review_id WHERE r.user_id =:userId AND r.deleted = 0")
	List<ReviewDTO> getAllReviewsByUserId(@Bind("userId") Integer userId);

	//Gets all the reviews for a particular product
	//@SqlQuery("SELECT * FROM review r INNER JOIN review_details rd ON r.product_id = rd.product_id WHERE r.product_id =:productId AND r.deleted = 0 AND r.approved = 1")

	@SqlQuery("SELECT * FROM review r WHERE (r.product_id =:productId AND r.deleted = 0 AND r.approved = 1)")
	List<ReviewDTO> getAllReviewsByProductId(@Bind("productId") Integer productId);

	
	//Gets all the reviews for a particular product by a particular user
	//@SqlQuery("SELECT * FROM review r INNER JOIN review_details rd ON r.review_id = rd.review_id WHERE r.product_id =:productId AND r.user_id =:userId AND r.deleted = 0 AND r.approved = 1")

	@SqlQuery("SELECT * FROM review r WHERE (r.product_id =:productId AND r.user_id =:userId AND r.deleted = 0 AND r.approved = 1)")
	List<ReviewDTO> getAllReviewofProductByUserId(@Bind("productId") Integer productId,@Bind("userId") Integer userId);


	//Returns the total number of reviews for given product
	@SqlQuery("SELECT COUNT(*) FROM review r INNER JOIN review_details rd ON r.review_id = rd.review_id WHERE (r.product_id =:productId AND r.deleted = 0 AND r.approved = 1)")
	Integer getTotalNumberofReviewsForAProduct(@Bind("productId") Integer productId);

	//Returns the total number of reviews from a user
	@SqlQuery("SELECT COUNT(*) FROM review r INNER JOIN review_details rd ON r.review_id = rd.review_id WHERE (r.user_id =:userId AND r.deleted = 0)")
	Integer getTotalNumberofReviewsFromAUser(@Bind("userId") Integer userId);

	//Returns the total number of unapproved (in processin state) reviews to the admin
	@SqlQuery("SELECT COUNT(*) FROM review r INNER JOIN review_details rd ON r.review_id = rd.review_id WHERE (rd.review_status = 1 AND r.approved = 0 AND r.deleted = 0)")
	Integer getTotalNumberofUnapprovedReviews();

	//Returns the total number of all reviews
	@SqlQuery("SELECT COUNT(*) FROM review r INNER JOIN review_details rd ON r.review_id = rd.review_id WHERE r.deleted = 0 ")
	Integer getTotalNumberofAllReviews();

	//Returns the total number of rejected reviews
	@SqlQuery("SELECT COUNT(*) FROM review r INNER JOIN review_details rd ON r.review_id = rd.review_id WHERE (rd.review_status = 3 AND r.approved = 0 AND r.deleted = 0)")
	Integer getTotalNumberofRejectedReviews();

	//Returns the total number of approved reviews
	@SqlQuery("SELECT COUNT(*) FROM review r INNER JOIN review_details rd ON r.review_id = rd.review_id WHERE (rd.review_status = 2 AND r.approved = 1 AND r.deleted = 0)")
	Integer getTotalNumberofApprovedReviews();
//
//	@SqlUpdate("UPDATE review SET review_title =:reviewTitle, review_desc =:reviewDesc, pros=:pros, cons=:cons, rating=:rating, review_date=:reviewDate WHERE review_id =:reviewId")
//	Integer updateReview(@Bind("reviewId") Integer reviewId,@BindBean ReviewDTO reviewDTO);


	@SqlQuery
	List<ReviewDTO> getReviewsByProductId(@Define("productId") Integer productId, @Define("caseQuery") String caseQuery);

	
	@SqlQuery("SELECT order_details_id FROM order_details od INNER JOIN orders o " + 
			"ON od.order_id = o.order_id " + 
			"INNER JOIN review r " + 
			"ON od.product_id = r.product_id " + 
			"WHERE r.user_id = o.ordered_by " + 
			"AND o.order_status = 2 " + 
			"AND r.product_id = :productId  " + 
			"AND r.user_id = :userId " + 
			"AND r.deleted = 0")
	List<Integer> checkVerifiedPurchase(@Bind("productId") Integer productId, @Bind("userId") Integer userId);
}
