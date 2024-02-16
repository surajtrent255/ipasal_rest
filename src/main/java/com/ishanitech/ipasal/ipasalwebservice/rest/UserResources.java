package com.ishanitech.ipasal.ipasalwebservice.rest;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import com.ishanitech.ipasal.ipasalwebservice.Services.UserService;
import com.ishanitech.ipasal.ipasalwebservice.Services.VerificationTokenService;
import com.ishanitech.ipasal.ipasalwebservice.dto.Response;
import com.ishanitech.ipasal.ipasalwebservice.dto.UserDTO;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomSqlException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.ResourceNotFoundException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.UserRegistrationException;
import com.ishanitech.ipasal.ipasalwebservice.utilities.HateoasLinkBuilderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserResources {
	private final Logger logger = LoggerFactory.getLogger(UserResources.class);
	private final UserService userService;
	private final VerificationTokenService verificationTokenService;

	@Autowired
	public UserResources(UserService userService, VerificationTokenService verificationTokenService) {
		this.userService = userService;
		this.verificationTokenService = verificationTokenService;
	}

	@GetMapping
	public Resources<UserDTO> findAll(@PageableDefault(page = 0, size = 2) Pageable pageable) {
		Long totalUsersInDatabase = userService.getTotalUser();
		Integer maxPageSize = pageable.getPageSize();
		Integer totalPages = (int) (totalUsersInDatabase / maxPageSize)
				+ ((totalUsersInDatabase % maxPageSize == 0) ? 0 : 1);
		Integer pageNo;
		Integer pageSize = pageable.getPageSize();
		if (pageable.getPageNumber() <= 1) {
			pageNo = 1;
		} else {
			pageNo = pageable.getPageNumber();
		}

		Integer startingPoint = (pageNo - 1) * pageable.getPageSize();

		List<UserDTO> users = userService.findAll(startingPoint, pageable.getPageSize());

//		for (UserDTO user : users) {
//			int userId = user.getUserId();
//			//Link link = linkTo(UserResources.class).slash(userId).withSelfRel();
//			// user.add(link);
//
//		}

		Link link = linkTo(UserResources.class).slash(HateoasLinkBuilderUtil.generatePageAndSizeLink(pageNo, pageSize))
				.withSelfRel();
		Resources<UserDTO> usersResource = new Resources<UserDTO>(users, link);

		// checks if there is next page.
		if (HateoasLinkBuilderUtil.hasNextPage(pageNo, totalPages)) {
			Link nextlink = linkTo(UserResources.class)
					.slash(HateoasLinkBuilderUtil.generatePageAndSizeLink(pageNo + 1, pageSize)).withRel("next");
			usersResource.add(nextlink);
		}

		// checks if there is previous page.
		if (HateoasLinkBuilderUtil.hasPrevPage(pageNo)) {
			usersResource.add(linkTo(UserResources.class)
					.slash(HateoasLinkBuilderUtil.generatePageAndSizeLink(pageNo - 1, pageSize)).withRel("prev"));
		}

		if (HateoasLinkBuilderUtil.hasFirstPage(pageNo)) {
			Link nextlink = linkTo(UserResources.class)
					.slash(HateoasLinkBuilderUtil.generatePageAndSizeLink(1, pageSize)).withRel("first");
			usersResource.add(nextlink);
		}

		if (HateoasLinkBuilderUtil.hasLastPage(pageNo, totalPages)) {
			Link nextlink = linkTo(UserResources.class)
					.slash(HateoasLinkBuilderUtil.generatePageAndSizeLink(totalPages, pageSize)).withRel("last");
			usersResource.add(nextlink);
		}

		return usersResource;
	}

	@GetMapping("/checkUserName")
	public Response<?> checkUserName(@RequestParam("username") String username) {
		boolean userExists = userService.checkUserName(username);
		if (userExists) {
			return Response.ok(userExists, HttpStatus.OK.value(), "Username Already Exists In Database!");
		} else {
			return Response.ok(userExists, HttpStatus.OK.value(),
					"Username not found in database. You can use current username.!");
		}
	}

	@GetMapping("/checkDuplicateEmail")
	public Response<?> checkDuplicateEmail(@RequestParam("email") String email) {
		boolean emailExists = userService.checkEmail(email);
		if (emailExists) {
			return Response.ok(emailExists, HttpStatus.OK.value(), "Email Already Exists In Database!");
		} else {
			return Response.ok(emailExists, HttpStatus.OK.value(),
					"Email not found in database. You can use this email.");
		}
	}

	//Getting the UserDto using the email address
	@GetMapping("/reset-user")
	Response<?> getUserByEmail(@RequestParam("email") String email){
		String status = userService.resetPassword(email);
		return Response.ok(status, HttpStatus.OK.value(), HttpStatus.OK.name());
	}

	@PostMapping("/register")
	public Response<?> registerUser(@RequestBody UserDTO user) {
		try {
			userService.registerUser(user);
			return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "Successfully registered the user.");

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new UserRegistrationException("Unable to register the user!");
		}

	}

	@RequestMapping(method = RequestMethod.POST)
	public Response<?> addUser(@RequestBody UserDTO userDTO) {
		try {
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(11);
			String encoded = passwordEncoder.encode(userDTO.getPassword());
			userDTO.setPassword(encoded);

			Integer result = userService.addUser(userDTO);
			return Response.ok(result, HttpStatus.OK.value(), "User has been added.");

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Unable to add user to dabase!");
		}
	}

	@GetMapping("/activateUser")
	public Response<?> activateUserAccount(@RequestParam("token") String token) {
		String tokenVerificationStatus = verificationTokenService.validateVerificationToken(token);
		if (tokenVerificationStatus.equals("INVALID_TOKEN")) {
			throw new UserRegistrationException("Account activation unsuccessful because of invalid activation token!");
		} else {
			return Response.ok(new ArrayList<>(), HttpStatus.OK.value(),
					"Your account has been activated. Now you can login to our system.");
		}
	}

	//verify the token being send in the email for resetting password
	// If the token is valid, get the user Id for that given token
	@GetMapping("/pw-reset-token-verify")
	public Response<?> verifyResetToken(@RequestParam("token") String token){
		Integer userId = verificationTokenService.resetPasswordTokenVerification(token);
		if (userId == null) {
			throw new UserRegistrationException("Couldn't reset password cause of invalid token");
		} else {
			return Response.ok(userId, HttpStatus.OK.value(),
					HttpStatus.OK.name());
		}
	}


	@GetMapping("/update-password/{userId}")
	public Response<?> updateUserPassword(@RequestParam("password") String password,
										  @PathVariable("userId") Integer userId){
		userService.updateUserPassword(userId, password);
		return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), HttpStatus.OK.name());
	}

	/*
	* Notify admin if the user is Whole-seller for the further contact
	* Send email to the admin, with details of the user
	* */
	@GetMapping(value = "/notify/{userId}")
	public Response<?> notifyAdmin(@PathVariable Integer userId){
		boolean status = userService.notifyAdmin(userId);
		return Response.ok(status, HttpStatus.OK.value(), HttpStatus.OK.name());
	}

	/*
	* Retrieving all whole-seller users
	* */
	@GetMapping(value = "/whole-seller")
	public Response<?> getAllWholeSeller(){
		List<UserDTO> wholeSellerList = null;
		try {
			wholeSellerList = userService.getAllWholeSeller();
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new CustomSqlException(ex.getMessage());
		}
		
		if(wholeSellerList != null && wholeSellerList.size() > 0) {
			return Response.ok(wholeSellerList, HttpStatus.OK.value(), HttpStatus.OK.name());
		}
		
		if(wholeSellerList == null) {
			throw new ResourceNotFoundException("Currently there are no wholeseller in the system.");
		}
		
		throw new CustomSqlException("Something went wrong while getting whole sellers from database");
	}

	/*
	* Searching and retrieving whole seller by whole seller Id
	* @returns UserDTO iff id is present
	* else return null
	* */
	@GetMapping(value = "/whole-seller/{id}")
	public Response<?> searchWholeSellerById(@PathVariable Integer id){
		UserDTO wholeSeller = userService.searchWholeSellerById(id);
			return Response.ok(wholeSeller, HttpStatus.OK.value(), HttpStatus.OK.name());

	}

	/*
	* Getting list of all customers from the users list
	* @returns List of UserDTO where authority = 3, which is for Customer
	* */
	@GetMapping(value = "/customers")
	public Response<?> getAllCustomersList(){
		List<UserDTO> customersList = userService.getAllCustomers();
		return Response.ok(customersList, HttpStatus.OK.value(), HttpStatus.OK.name());
	}
	
	@RequestMapping(method = RequestMethod.PUT , value = "/{userId}")
	public Response<?> updateUser(@PathVariable("userId") Integer userId, @RequestBody UserDTO userDTO) {
        try {
            userService.updateUser(userId, userDTO);
            return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "User info update successfully!");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Something went wrong updating user info.");
        }
    }
	
//	@PutMapping("/user/{userId}")
//	public Response<?> updateUser(@PathVariable("userId") Integer userId, @RequestBody UserDTO userDTO) {
//		try {
//			userService.updateUser(userId, userDTO);
//			return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "Update Succesful");
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			throw new CustomSqlException("Some thing went wrong");
//		}
//		
//	}
}
