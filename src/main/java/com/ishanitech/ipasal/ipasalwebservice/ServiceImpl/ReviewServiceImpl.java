package com.ishanitech.ipasal.ipasalwebservice.ServiceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.ishanitech.ipasal.ipasalwebservice.dto.UserDTO;
import com.ishanitech.ipasal.ipasalwebservice.utilities.CustomQueryCreator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ishanitech.ipasal.ipasalwebservice.Services.DbService;
import com.ishanitech.ipasal.ipasalwebservice.Services.ReviewService;
import com.ishanitech.ipasal.ipasalwebservice.dao.ProductDAO;
import com.ishanitech.ipasal.ipasalwebservice.dao.ReviewDAO;
import com.ishanitech.ipasal.ipasalwebservice.dao.UserDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.PaginationTypeClass;
import com.ishanitech.ipasal.ipasalwebservice.dto.ReviewDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ReviewDetailsDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tanchhowpa
 * <p>
 * May 9, 2019 4:15:26 PM
 */

@Service
public class ReviewServiceImpl implements ReviewService {
    private final DbService dbService;
    private final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);
    public ReviewServiceImpl(DbService dbService) {
        this.dbService = dbService;
    }


    @Override
    @Transactional
    public Integer addReview(ReviewDTO reviewDTO) {
        ReviewDAO reviewDAO = dbService.getDao(ReviewDAO.class);
        int reviewId = reviewDAO.addReview(reviewDTO);

        ReviewDetailsDTO reviewDetails = new ReviewDetailsDTO();
        reviewDetails.setReviewId(reviewId);
        reviewDetails.setProductId(reviewDTO.getProductId());
        reviewDetails.setUserId(reviewDTO.getUserId());

        reviewDAO.addReviewDetails(reviewDetails);

        return reviewId;
    }


    @Override
    public List<ReviewDTO> getAllReviews(HttpServletRequest request) {
        ReviewDAO reviewDAO = dbService.getDao(ReviewDAO.class);
        UserDAO userDAO = dbService.getDao(UserDAO.class);
        ProductDAO productDAO = dbService.getDao(ProductDAO.class);
        String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.REVIEW);
        List<ReviewDTO> returnedList = reviewDAO.getAllReviews(caseQuery);

        for (int i = 0; i < returnedList.size(); i++) {
            returnedList.get(i).setUserDto(userDAO.getUserByUserId(returnedList.get(i).getUserId()));
            returnedList.get(i).setStatus(reviewDAO.getReviewStatusByReviewId(returnedList.get(i).getReviewId()));
            returnedList.get(i).setProductName(productDAO.getProductById(returnedList.get(i).getProductId()).getProductName());
        }


        return returnedList;
    }

    @Override
    public List<ReviewDTO> getAllApprovedReviews(HttpServletRequest request) {
        ReviewDAO reviewDAO = dbService.getDao(ReviewDAO.class);
        UserDAO userDAO = dbService.getDao(UserDAO.class);
        ProductDAO productDAO = dbService.getDao(ProductDAO.class);
        String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.REVIEW);
        List<ReviewDTO> returnedList = reviewDAO.getAllApprovedReviews(caseQuery);

        for (int i = 0; i < returnedList.size(); i++) {
            returnedList.get(i).setUserDto(userDAO.getUserByUserId(returnedList.get(i).getUserId()));
            returnedList.get(i).setStatus(reviewDAO.getReviewStatusByReviewId(returnedList.get(i).getReviewId()));
            returnedList.get(i).setProductName(productDAO.getProductById(returnedList.get(i).getProductId()).getProductName());
        }
        return returnedList;
    }


    @Override
    public void deleteReview(Integer reviewId) {
        ReviewDAO reviewDAO = dbService.getDao(ReviewDAO.class);
        reviewDAO.deleteReview(reviewId);

    }


    @Override
    public ReviewDTO getReviewByReviewId(Integer reviewId) {
        ReviewDAO reviewDAO = dbService.getDao(ReviewDAO.class);
        UserDAO userDAO = dbService.getDao(UserDAO.class);
        ProductDAO productDAO = dbService.getDao(ProductDAO.class);

        ReviewDTO returnedReview = reviewDAO.getReviewByReviewId(reviewId);
        returnedReview.setUserDto(userDAO.getUserByUserId(returnedReview.getUserId()));
        returnedReview.setStatus(reviewDAO.getReviewStatusByReviewId(returnedReview.getReviewId()));
        returnedReview.setProductName(productDAO.getProductById(returnedReview.getProductId()).getProductName());

        return returnedReview;

    }


    @Override
    public Integer updateReview(Integer reviewId, ReviewDTO reviewDTO) {
        ReviewDAO reviewDAO = dbService.getDao(ReviewDAO.class);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String reviewDate = dateFormat.format(Calendar.getInstance().getTime());


//	    ReviewDetailsDTO reviewDetails = new ReviewDetailsDTO();
//	    reviewDetails.setReviewDate(reviewDAO.updateReviewDateinReviewDetails(reviewDate ,reviewId));
        reviewDAO.updateReviewDateinReviewDetails(reviewDate, reviewId);
        reviewDTO.setReviewDate(reviewDate);
        reviewDAO.processReviewByReviewId(reviewId);
        return reviewDAO.updateReview(reviewId, reviewDTO);

    }


    @Override
    public int changeReviewStatus(Integer reviewId, Integer status) {
        ReviewDAO reviewDAO = dbService.getDao(ReviewDAO.class);
        return reviewDAO.changeReviewStatus(reviewId, status);
    }

    @Override
    public String getReviewStatusByReviewId(Integer reviewId) {
        ReviewDAO reviewDAO = dbService.getDao(ReviewDAO.class);
        return reviewDAO.getReviewStatusByReviewId(reviewId);
    }

    @Override
    public Integer rejectReviewByReviewId(Integer reviewId) {
        ReviewDAO reviewDAO = dbService.getDao(ReviewDAO.class);
        return reviewDAO.rejectReviewByReviewId(reviewId);
    }


    @Override
    public Integer approveReviewByReviewId(Integer reviewId) {
        ReviewDAO reviewDAO = dbService.getDao(ReviewDAO.class);
        return reviewDAO.approveReviewByReviewId(reviewId);
    }


    @Override
    public Integer processReviewByReviewId(Integer reviewId) {
        ReviewDAO reviewDAO = dbService.getDao(ReviewDAO.class);
        return reviewDAO.processReviewByReviewId(reviewId);
    }


    @Override
    public List<ReviewDTO> getAllProcessingReviews(HttpServletRequest request) {
        ReviewDAO reviewDAO = dbService.getDao(ReviewDAO.class);
        UserDAO userDAO = dbService.getDao(UserDAO.class);
        ProductDAO productDAO = dbService.getDao(ProductDAO.class);
        String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.REVIEW);
        LOGGER.info("CASE QUERY : " + caseQuery);
        List<ReviewDTO> returnedList = reviewDAO.getAllProcessingReviews(caseQuery);
        for (int i = 0; i < returnedList.size(); i++) {
            returnedList.get(i).setUserDto(userDAO.getUserByUserId(returnedList.get(i).getUserId()));
            returnedList.get(i).setStatus(reviewDAO.getReviewStatusByReviewId(returnedList.get(i).getReviewId()));
            returnedList.get(i).setProductName(productDAO.getProductById(returnedList.get(i).getProductId()).getProductName());
        }

        return returnedList;
    }

    @Override
    public List<ReviewDTO> getAllRejectedReviews(HttpServletRequest request) {
        ReviewDAO reviewDAO = dbService.getDao(ReviewDAO.class);
        UserDAO userDAO = dbService.getDao(UserDAO.class);
        ProductDAO productDAO = dbService.getDao(ProductDAO.class);
        String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.REVIEW);
        List<ReviewDTO> returnedList = reviewDAO.getAllRejectedReviews(caseQuery);

        for (int i = 0; i < returnedList.size(); i++) {
            returnedList.get(i).setUserDto(userDAO.getUserByUserId(returnedList.get(i).getUserId()));
            returnedList.get(i).setStatus(reviewDAO.getReviewStatusByReviewId(returnedList.get(i).getReviewId()));
            returnedList.get(i).setProductName(productDAO.getProductById(returnedList.get(i).getProductId()).getProductName());
        }
        return returnedList;
    }


    @Override
    public List<ReviewDTO> getAllReviewsByUserId(Integer userId) {
        ReviewDAO reviewDAO = dbService.getDao(ReviewDAO.class);
        UserDAO userDAO = dbService.getDao(UserDAO.class);
        ProductDAO productDAO = dbService.getDao(ProductDAO.class);
        List<ReviewDTO> returnedList = reviewDAO.getAllReviewsByUserId(userId);

        for (int i = 0; i < returnedList.size(); i++) {
            returnedList.get(i).setUserDto(userDAO.getUserByUserId(returnedList.get(i).getUserId()));
            returnedList.get(i).setStatus(reviewDAO.getReviewStatusByReviewId(returnedList.get(i).getReviewId()));
            returnedList.get(i).setProductName(productDAO.getProductById(returnedList.get(i).getProductId()).getProductName());
        }
        return returnedList;

    }


    @Override
    public List<ReviewDTO> getAllReviewsByProductId(Integer productId) {
        ReviewDAO reviewDAO = dbService.getDao(ReviewDAO.class);
        UserDAO userDAO = dbService.getDao(UserDAO.class);
        List<ReviewDTO> returnedList = reviewDAO.getAllReviewsByProductId(productId);

        for (int i = 0; i < returnedList.size(); i++) {
            returnedList.get(i).setUserDto(userDAO.getUserByUserId(returnedList.get(i).getUserId()));
        }
        return returnedList;
    }


    @Override
    public List<ReviewDTO> getAllReviewofProductByUserId(Integer productId, Integer userId) {
        ReviewDAO reviewDAO = dbService.getDao(ReviewDAO.class);
        List<ReviewDTO> returnedList = reviewDAO.getAllReviewofProductByUserId(productId, userId);
        return returnedList;
    }


    @Override
    public List<ReviewDTO> getReviewByProductId(Integer productId, HttpServletRequest request) {
        ReviewDAO reviewDAO = dbService.getDao(ReviewDAO.class);
        UserDAO userDAO = dbService.getDao(UserDAO.class);

        String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.REVIEW);

        List<ReviewDTO> reviewList = new ArrayList<>();
        List<UserDTO> userDTOList = new ArrayList<>();
        Set<Integer> userIdLst = new HashSet<>();
        reviewList = reviewDAO.getReviewsByProductId(productId, caseQuery);
        for (ReviewDTO reviewDTO : reviewList) {
            userIdLst.add(reviewDTO.getUserId());
        }
        userDTOList = userDAO.getUserByUserIdList(new ArrayList<>(userIdLst));

        for (int i = 0; i < reviewList.size(); i++) {
            Integer userId = reviewList.get(i).getUserId();
            for (UserDTO userDTO : userDTOList) {
                if (userDTO.getUserId() == userId) {
                    userDTO.setPassword("");
                    reviewList.get(i).setUserDto(userDTO);
                }
            }

        }
        return reviewList;

    }


    //Returns the total number of reviews for any given product

    @Override
    public Integer countTotalReviewsForProduct(Integer productId) {
        return dbService.getDao(ReviewDAO.class).getTotalNumberofReviewsForAProduct(productId);
    }


    @Override
    public Integer countTotalReviewsByUserId(Integer userId) {
        return dbService.getDao(ReviewDAO.class).getTotalNumberofReviewsFromAUser(userId);
    }


    @Override
    public Integer countTotalUnapprovedReviews() {
        return dbService.getDao(ReviewDAO.class).getTotalNumberofUnapprovedReviews();
    }


    @Override
    public Integer countTotalReviews() {
        return dbService.getDao(ReviewDAO.class).getTotalNumberofAllReviews();
    }


    @Override
    public Integer countTotalRejectedReviews() {
        return dbService.getDao(ReviewDAO.class).getTotalNumberofRejectedReviews();
    }


    @Override
    public Integer countTotalApprovedReviews() {
        return dbService.getDao(ReviewDAO.class).getTotalNumberofApprovedReviews();
    }

}
