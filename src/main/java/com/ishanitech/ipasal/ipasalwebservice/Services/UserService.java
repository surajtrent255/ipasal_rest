package com.ishanitech.ipasal.ipasalwebservice.Services;



import java.util.List;

import com.ishanitech.ipasal.ipasalwebservice.dto.UserDTO;

public interface UserService {
    int addUser (UserDTO userDTO);
    UserDTO getUserByUsername (String username);
    UserDTO getUserByEmailAddress(String email);
    UserDTO registerUser(UserDTO user);
    boolean checkUserName(String username);
	boolean checkEmail(String email);
	List<UserDTO> findAll(Integer pageNo, Integer limitSize);
	long getTotalUser();
	UserDTO getUserByEmail(String email);
	String resetPassword(String email);
	UserDTO getUserByUserId(Integer userId);
	void updateUserPassword(Integer userId, String password);
	boolean notifyAdmin(Integer userId);
	List<UserDTO> getAllWholeSeller();
	UserDTO searchWholeSellerById(Integer wholeSellerId);
	List<UserDTO> getAllCustomers();
	Integer updateUser(Integer userId, UserDTO userDTO);
}
