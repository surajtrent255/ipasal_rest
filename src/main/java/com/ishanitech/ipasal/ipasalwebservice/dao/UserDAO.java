package com.ishanitech.ipasal.ipasalwebservice.dao;

import com.ishanitech.ipasal.ipasalwebservice.dto.UserDTO;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;
import org.skife.jdbi.v2.unstable.BindIn;

@UseStringTemplate3StatementLocator
public interface UserDAO {

    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO user(username, password, f_name, m_name, l_name, phone, email, role_id , parent_id, street, city) values(:username, :password, :fName, :mName, :lName, :phone, :email, :roleId, :parentId, :street, :city)")
    int addUser(@BindBean UserDTO userDTO);

    @SqlQuery("SELECT u.*, r.role_name as authority FROM user u inner join role r on u.role_id= r.role_id and u.username = :username")
    UserDTO getUserByUsername(@Bind("username") String username);

    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO user(username, password, f_name, m_name, l_name, phone, email, street, city, role_id , parent_id, enabled) values(:username, :password, :fName, :mName, :lName, :phone, :email, :street, :city, :roleId, :parentId, :enabled)")
	Integer registerUser(@BindBean UserDTO user);

    @SqlQuery("SELECT u.*, r.role_name as authority FROM user u inner join role r on u.role_id = r.role_id and u.email = :email AND u.enabled = TRUE")
	UserDTO getUserByEmail(@Bind("email") String email);

    @SqlUpdate("UPDATE user set enabled = TRUE WHERE user_id = :userId")
	void enableUserAccount(@Bind("userId") Integer userId);

    @SqlQuery("SELECT * FROM user LIMIT :pageNo, :limitSize")
    List<UserDTO> findAll(@Bind("pageNo") Integer pageNo, @Bind("limitSize") Integer limitSize);
    
    @SqlQuery("SELECT count(*) from user")
    Long countTotalNoOfUser();

    @SqlQuery("SELECT * FROM user WHERE user_id = :userId")
	UserDTO getUserByUserId(@Bind("userId") Integer userId);
    
    @SqlQuery("SELECT * FROM user WHERE role_id = 1")
    List<UserDTO> getAllAdminUsers();

    //Getting userId using the email address
    @SqlQuery("SELECT * FROM user WHERE email = :email")
    UserDTO getUserByEmailAddress (@Bind("email") String email);

    //Updating user password
    @SqlUpdate("UPDATE user set password = :password WHERE user_id = :userId")
    void updateUserPassword(@Bind("password") String password,
                            @Bind("userId") Integer userId);

    //Retrieving all user who are whole-seller
    @SqlQuery("SELECT * FROM user WHERE role_id = 4")
    List<UserDTO> getAllWholeSeller();

    // Retrieving single whole seller with given Id
    @SqlQuery("SELECT * FROM user WHERE role_id = 4 AND user_id = :wholeSellerId")
    UserDTO seachWholeSellerById(@Bind("wholeSellerId") Integer wholeSellerId);

    //Retrieve all customers/users whose role id is 3(customer)
    @SqlQuery("SELECT * FROM user WHERE role_id =3")
    List<UserDTO> getAllCustomers();

    @SqlQuery("SELECT * FROM user WHERE user_id IN (<userIdList>)")
    List<UserDTO> getUserByUserIdList(@BindIn("userIdList") List<Integer> userIdList);

    @SqlUpdate("Update user SET username =:username, f_name = :fName, m_name = :mName, l_name = :lName, street=:street, city=:city, phone=:phone  where user_id =:userId")
	public Integer updateUserDetails(@Bind("userId") Integer userId, @BindBean UserDTO userDTO);

}
