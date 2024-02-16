package com.ishanitech.ipasal.ipasalwebservice.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ishanitech.ipasal.ipasalwebservice.Services.DbService;
import com.ishanitech.ipasal.ipasalwebservice.Services.EmailService;
import com.ishanitech.ipasal.ipasalwebservice.Services.UserService;
import com.ishanitech.ipasal.ipasalwebservice.Services.VerificationTokenService;
import com.ishanitech.ipasal.ipasalwebservice.dao.UserDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.EmailDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.UserDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.VerificationTokenDTO;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.ResourceNotFoundException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.UserRegistrationException;

import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;

@Service
public class UserServiceImpl implements UserService {
	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String ROLE_PREFIX = "ROLE_";
    private final DbService dbService;
    
    @Value("${webservice.host-url}")
    private String hostUrl;
    
    @Autowired
    EmailService emailService;
    
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    VerificationTokenService verificationTokenService;

    @Autowired
    public UserServiceImpl(DbService dbService){
        this.dbService = dbService;
    }

    @Override
    public int addUser(UserDTO userDTO) {
     UserDAO userDAO = dbService.getDao(UserDAO.class);
     System.out.println("The user information is: "+ userDTO);
        return userDAO.addUser(userDTO);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        UserDAO userDAO = dbService.getDao(UserDAO.class);
        UserDTO userDto = null;
        userDto = userDAO.getUserByUsername(username);
        userDto.setAuthorities(AuthorityUtils.createAuthorityList(ROLE_PREFIX + userDto.getAuthority()));
        return userDto;
    }

    @Override
    public UserDTO getUserByEmailAddress(String email) throws ResourceNotFoundException {
        UserDAO userDAO = dbService.getDao(UserDAO.class);
        UserDTO user = userDAO.getUserByEmail(email);
        if(user == null) {
        	throw new ResourceNotFoundException("No user found with email: " + email);
        }
        user.setAuthorities(AuthorityUtils.createAuthorityList(ROLE_PREFIX + user.getAuthority()));
        return user;
    }

    @Transactional
    @Override
    public UserDTO registerUser(UserDTO user){
        UserDAO userDAO = dbService.getDao(UserDAO.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        
        try {
            Integer registeredUserId =  userDAO.registerUser(user);
            if( registeredUserId != null) {
                String token = UUID.randomUUID().toString();
                VerificationTokenDTO verificationToken = new VerificationTokenDTO(token, registeredUserId);

                //insert into VerificationToken table
                verificationTokenService.addVerificationToken(verificationToken);
                
                EmailDTO emailingDetail = new EmailDTO();
                emailingDetail.setFrom("no-reply@ishanitech.com");
                emailingDetail.setTo(user.getEmail());
                emailingDetail.setSubject("Account Activation Email");
                Map<String, Object> map = new HashMap<>();
                map.put("cname", user.getfName());
                map.put("location", "Hattiban-1, Lalitpur");
                map.put("officeContactNo", "01-4331266");
                map.put("activationLink", hostUrl + "registration/registrationConfirm?token=" + token);
                map.put("signature", "http://www.ishanitech.com/ishanitech");
                emailingDetail.setModel(map);
                emailService.sendAccountActivationEmail(emailingDetail, "register");
                return user;
            }
            
            throw new UserRegistrationException("Something went wrong while registring user");
            
        } catch(Exception ex){
        	logger.error(ex.getMessage());
           throw new UserRegistrationException("Something went wrong while registring user");
        }
        //userDato.registerUser(user);
        
    }

    @Override
    public boolean checkUserName(String username) {
        UserDAO userDAO = dbService.getDao(UserDAO.class);
        UserDTO user = userDAO.getUserByUsername(username);
        return user != null ? true : false;
    }

    @Override
    public boolean checkEmail(String email) {
        UserDAO userDAO = dbService.getDao(UserDAO.class);
        UserDTO user = userDAO.getUserByEmail(email);
        return user != null ? true : false;
    }

	@Override
	public List<UserDTO> findAll(Integer pageNo, Integer limitSize) {
		UserDAO userDAO = dbService.getDao(UserDAO.class);
		return userDAO.findAll(pageNo, limitSize);
	}

	@Override
	public long getTotalUser() {
		UserDAO userDAO = dbService.getDao(UserDAO.class);
		return userDAO.countTotalNoOfUser();
	}

    @Override
    public UserDTO getUserByEmail(String email) {
        UserDAO userDAO = dbService.getDao(UserDAO.class);
        return userDAO.getUserByEmail(email);
    }

    @Override
    public String resetPassword(String email) {
        UserDAO userDAO = dbService.getDao(UserDAO.class);
        UserDTO user = userDAO.getUserByEmail(email);
        try {
            Integer userId =  user.getUserId();
            if( userId != null) {
                String token = UUID.randomUUID().toString();
                VerificationTokenDTO verificationToken = new VerificationTokenDTO(token, userId);

                //insert into VerificationToken table
                verificationTokenService.addVerificationToken(verificationToken);

                EmailDTO emailingDetail = new EmailDTO();
                emailingDetail.setFrom("no-reply@ishanitech.com");
                emailingDetail.setTo(user.getEmail());
                emailingDetail.setSubject("Reset Password");
                Map<String, Object> map = new HashMap<>();
                map.put("name", user.getfName());
                map.put("location", "Hattiban-1, Lalitpur");
                map.put("officeContactNo", "01-4331266");
                map.put("resetLink", hostUrl +"/forgot-password/verifyPasswordToken?token=" + token);
                map.put("signature", "http://www.ishanitech.com/ishanitech");
                emailingDetail.setModel(map);
                emailService.sendAccountActivationEmail(emailingDetail, "pw");
                return "Success";
            }
            throw new UserRegistrationException("Could not send reset email to the user.");
        } catch(Exception ex){
            logger.error(ex.getMessage());
            throw new UserRegistrationException("Something went wrong while resetting password");
        }
    }

    @Override
    public UserDTO getUserByUserId(Integer userId) {
        UserDAO userDAO = dbService.getDao(UserDAO.class);
        UserDTO user = userDAO.getUserByUserId(userId);
        return user;
    }

    //updating user password for the given user id
    @Override
    public void updateUserPassword(Integer userId, String password) {
        UserDAO userDAO = dbService.getDao(UserDAO.class);
        userDAO.updateUserPassword(passwordEncoder.encode(password), userId);
    }

    //Sending an email to the admin
    @Override
    public boolean notifyAdmin(Integer userId) {
        UserDAO userDAO = dbService.getDao(UserDAO.class);
        UserDTO user = userDAO.getUserByUserId(userId);
        if (user != null){
            EmailDTO emailingDetail = new EmailDTO();
            emailingDetail.setFrom("no-reply@ishanitech.com");
            emailingDetail.setTo("azens1995@gmail.com");
            emailingDetail.setSubject("Whole-Seller further query");
            Map<String, Object> map = new HashMap<>();
            map.put("name", user.getfName());
            map.put("lastname", user.getlName());
            map.put("userId", user.getUserId());
            map.put("contact", user.getPhone());
            map.put("location", "Hattiban-1, Lalitpur");
            map.put("officeContactNo", "01-4331266");
            map.put("signature", "http://www.ishanitech.com/ishanitech");
            emailingDetail.setModel(map);
            try {
                emailService.sendAccountActivationEmail(emailingDetail, "wholeseller");
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    // Retrieving all whole -seller user
    @Override
    public List<UserDTO> getAllWholeSeller() {
        UserDAO userDAO = dbService.getDao(UserDAO.class);
        return userDAO.getAllWholeSeller();
    }

    @Override
    public UserDTO searchWholeSellerById(Integer wholeSellerId) {
        UserDAO userDAO = dbService.getDao(UserDAO.class);
        return userDAO.seachWholeSellerById(wholeSellerId);
    }

    @Override
    public List<UserDTO> getAllCustomers() {
        UserDAO userDAO = dbService.getDao(UserDAO.class);
        return userDAO.getAllCustomers();
    }

	@Override
	public Integer updateUser(Integer userId, UserDTO userDTO) {
		UserDAO userDAO = dbService.getDao(UserDAO.class);
		return userDAO.updateUserDetails(userId, userDTO);
	}

}
