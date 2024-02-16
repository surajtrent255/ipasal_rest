package com.ishanitech.ipasal.ipasalwebservice.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ishanitech.ipasal.ipasalwebservice.Services.ReviewService;
import com.ishanitech.ipasal.ipasalwebservice.dto.Response;
import com.ishanitech.ipasal.ipasalwebservice.dto.ReviewDTO;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomSqlException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.ResourceNotFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tanchhowpa
 * 
 * May 9, 2019 4:09:32 PM
 */


@RestController
@RequestMapping("api/v1/review")
public class ReviewResources {

    private ReviewService reviewService;
    Logger logger = LoggerFactory.getLogger(ReviewResources.class);

    public ReviewResources(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @RequestMapping(method = RequestMethod.POST)
    public Response<?> addReview(@RequestBody ReviewDTO reviewDTO) {
        Integer result = null;
        try {
            result = reviewService.addReview(reviewDTO);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Something went wrong while entering the data.");
        }

        return Response.ok(result, HttpStatus.OK.value(), HttpStatus.OK.name());
    }

    @RequestMapping(method = RequestMethod.GET)
    public Response<?> getAllReivew(HttpServletRequest request) {
        List<ReviewDTO> reviewList = null;
        try {
            reviewList = reviewService.getAllReviews(request);
        } catch (Exception e) {
        	e.printStackTrace();
        	logger.error(e.getLocalizedMessage());
            throw new CustomSqlException("Something went wrong while getting data from database");
        }

        if (reviewList != null) {
            return Response.ok(reviewList, HttpStatus.OK.value(), HttpStatus.OK.name());
        } else {
            throw new ResourceNotFoundException("No reviews found in the database");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/approved")
    public Response<?> getAllApprovedReviews(HttpServletRequest request) {
        List<ReviewDTO> reviewList = null;
        try {
            reviewList = reviewService.getAllApprovedReviews(request);
        } catch (Exception e) {
            throw new CustomSqlException("Something went wrong while getting data from the database");
        }

        if (reviewList != null) {
            return Response.ok(reviewList, HttpStatus.OK.value(), HttpStatus.OK.name());
        } else {
            throw new ResourceNotFoundException("No review found in the database");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{reviewId}")
    public Response<?> deleteReview(@PathVariable("reviewId") Integer reviewId) {
        try {
            reviewService.deleteReview(reviewId);
            return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "Review has been deleted Successfully");
        } catch (Exception e) {
            throw new CustomSqlException("Something went wrong while getting data from database");
        }
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{reviewId}")
    public Response<?> getReviewByReviewId(@PathVariable("reviewId") Integer reviewId) {
        ReviewDTO review = null;
        try {
            review = reviewService.getReviewByReviewId(reviewId);
        } catch (Exception e) {
            throw new CustomSqlException("Someting went wrong while getting data from database");
        }
        if (review != null) {
            return Response.ok(Arrays.asList(review), HttpStatus.OK.value(), HttpStatus.OK.name());
        } else {
            throw new ResourceNotFoundException("No rewview found.");
        }

    }


    //Update Review for changes
//	@RequestMapping(method = RequestMethod.PUT, value = "/update/{reviewId}")
    @PutMapping("/{reviewId}")
    public Response<?> updateReview(@PathVariable("reviewId") Integer reviewId, @RequestBody ReviewDTO reviewDTO) {
        try {
            reviewService.updateReview(reviewId, reviewDTO);
            return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "Review updated Successfully. Please wait for the admin to approve the updated review! :)");
        } catch (Exception e) {
            throw new CustomSqlException("Something went wrong while updating the review");
        }
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/{reviewId}/{status}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response<?> changeReviewStatus(@PathVariable("reviewId") Integer reviewId, @PathVariable("status") Integer status) {
        try {
            int result = reviewService.changeReviewStatus(reviewId, status);
            return Response.ok(result, HttpStatus.OK.value(), HttpStatus.OK.name());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Could not change the status of the review");
        }
    }

    //Get the current status of the review.

    @RequestMapping(method = RequestMethod.GET, value = "/status/{reviewId}")
    public Response<?> getReviewStatusByReviewId(@PathVariable("reviewId") Integer reviewId) {
        try {
            reviewService.getReviewStatusByReviewId(reviewId);
            return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), HttpStatus.OK.name());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("The fetching of status failed");
        }
    }


    //Rejects the review by Review ID

    @RequestMapping(method = RequestMethod.POST, value = "/reject/{reviewId}")
    public Response<?> rejectReviewByReviewId(@PathVariable("reviewId") Integer reviewId) {
        try {
            reviewService.rejectReviewByReviewId(reviewId);
            return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "Review Rejected!");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("The reject operation failed");
        }
    }


    //Approves the review by Review ID

    @RequestMapping(method = RequestMethod.POST, value = "/approve/{reviewId}")
    public Response<?> approveReviewByReviewId(@PathVariable("reviewId") Integer reviewId) {
        try {
            reviewService.approveReviewByReviewId(reviewId);
            return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "Review Approved");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("The approval operation failed");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/process/{reviewId}")
    public Response<?> processReviewByReviewId(@PathVariable("reviewId") Integer reviewId) {
        try {
            reviewService.processReviewByReviewId(reviewId);
            return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "Review put in Processing State");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("The status change to processing failed");
        }
    }


    //Get all reviews in Processing State
    @RequestMapping(method = RequestMethod.GET, value = "/processing")
    public Response<?> getAllPrcoessingReviews(HttpServletRequest request) {
        List<ReviewDTO> reviewList = null;
        try {
            reviewList = reviewService.getAllProcessingReviews(request);
        } catch (Exception e) {
        	e.printStackTrace();
            throw new CustomSqlException("Something went wrong while getting data from the database");
        }
        if (reviewList != null) {
            return Response.ok(reviewList, HttpStatus.OK.value(), HttpStatus.OK.name());
        } else {
            throw new ResourceNotFoundException("No reviews found in the database");
        }
    }

    //Get all the reviews that are rejected
    @RequestMapping(method = RequestMethod.GET, value = "/rejected")
    public Response<?> getAllRejectedReviews(HttpServletRequest request) {
        List<ReviewDTO> reviewList = null;
        try {
            reviewList = reviewService.getAllRejectedReviews(request);
        } catch (Exception e) {
            throw new CustomSqlException("Something went wrong while getting data from the database");
        }
        if (reviewList != null) {
            return Response.ok(reviewList, HttpStatus.OK.value(), HttpStatus.OK.name());
        } else {
            throw new ResourceNotFoundException("No reviews found in the database");
        }
    }


    //Get all the reviews from a particular user
    @RequestMapping(method = RequestMethod.GET, value = "/{userId}/user")
    public Response<?> getAllReviewsByUserId(@PathVariable("userId") Integer userId) {
        List<ReviewDTO> userReviewList = null;
        try {
            userReviewList = reviewService.getAllReviewsByUserId(userId);
        } catch (Exception e) {
            throw new CustomSqlException("Someting went wrong while getting data from database");
        }
        if (userReviewList != null) {
            return Response.ok(userReviewList, HttpStatus.OK.value(), HttpStatus.OK.name());
        } else {
            throw new ResourceNotFoundException("No reviews found for this user.");
        }

    }

    //Get all the reviews of a particular product
    @RequestMapping(method = RequestMethod.GET, value = "/product/{productId}")
    public Response<?> getAllReviewsByProductId(@PathVariable("productId") Integer productId) {
        List<ReviewDTO> productReviewList = null;
        try {
            productReviewList = reviewService.getAllReviewsByProductId(productId);
        } catch (Exception e) {
            throw new CustomSqlException("Something went wrong while getting data from the database");
        }
        if (productReviewList != null) {
            return Response.ok(productReviewList, HttpStatus.OK.value(), HttpStatus.OK.name());
        } else {
            throw new ResourceNotFoundException("No reviews found for this product.");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/reviewProduct/{productId}")
    public Response<?> getReviewsByProductId(@PathVariable("productId") Integer productId, HttpServletRequest request) {
        List<ReviewDTO> productReviewList = new ArrayList<>();
        try {
            productReviewList = reviewService.getReviewByProductId(productId, request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomSqlException("Something went wrong while getting data from the database");
        }
        if (productReviewList != null) {
            return Response.ok(productReviewList, HttpStatus.OK.value(), HttpStatus.OK.name());
        } else {
            throw new ResourceNotFoundException("No reviews found.");
        }
    }


    //Get the review of product by a particular user
    @RequestMapping(method = RequestMethod.GET, value = "/product/{productId}/{userId}")
    public Response<?> getAllReviewofProductByUserId(@PathVariable("productId") Integer productId, @PathVariable("userId") Integer userId) {
        List<ReviewDTO> reviewList = null;
        try {
            reviewList = reviewService.getAllReviewofProductByUserId(productId, userId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Something went wrong while getting data from the database");
        }
        if (reviewList != null) {
            return Response.ok(reviewList, HttpStatus.OK.value(), HttpStatus.OK.name());
        } else {
            throw new ResourceNotFoundException("No reviews found for product.");
        }
    }


    @GetMapping("/totalReviews")
    public Response<?> getTotalNumberofAllReviews() {
        Integer totalReviews = null;
        try {
            totalReviews = reviewService.countTotalReviews();
            return Response.ok(totalReviews, HttpStatus.OK.value(), HttpStatus.OK.name());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Something went wrong while getting data from the database");
        }
    }

    @GetMapping("/totalReviews/user/{userId}")
    public Response<?> getTotalNumberofReviewsFromAUser(@PathVariable("userId") Integer userId) {
        Integer totalReviewsFromUser;
        try {
            totalReviewsFromUser = reviewService.countTotalReviewsByUserId(userId);
            return Response.ok(totalReviewsFromUser, HttpStatus.OK.value(), HttpStatus.OK.name());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Something went wrong while getting data from the database");
        }
    }

    @GetMapping("/totalReviews/product/{productId}")
    public Response<?> getTotalNumberofReviewsForAProduct(@PathVariable("productId") Integer productId) {
        Integer totalReviewsForProduct;
        try {
            totalReviewsForProduct = reviewService.countTotalReviewsForProduct(productId);
            return Response.ok(totalReviewsForProduct, HttpStatus.OK.value(), HttpStatus.OK.name());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Something went wrong while getting data from the database.");
        }
    }


    @GetMapping("/totalReviews/unapproved")
    public Response<?> getTotalNumberofUnapprovedReviews() {
        try {
            Integer totalUnapprovedReviews = reviewService.countTotalUnapprovedReviews();
            return Response.ok(totalUnapprovedReviews, HttpStatus.OK.value(), HttpStatus.OK.name());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Something went wrong while getting data from the database");
        }
    }


    @GetMapping("/totalReviews/rejected")
    public Response<?> getTotalNumberofRejectedReviews() {
        try {
            Integer totalRejectedReviews = reviewService.countTotalRejectedReviews();
            return Response.ok(totalRejectedReviews, HttpStatus.OK.value(), HttpStatus.OK.name());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Something went wrong while getting data from the database");
        }
    }


    @GetMapping("/totalReviews/approved")
    public Response<?> getTotalNumberofApprovedReviews() {
        try {
            Integer totalApprovedReviews = reviewService.countTotalApprovedReviews();
            return Response.ok(totalApprovedReviews, HttpStatus.OK.value(), HttpStatus.OK.name());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Something went wrong while getting data from the database");
        }
    }
}
