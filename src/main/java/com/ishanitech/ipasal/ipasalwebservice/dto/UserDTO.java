package com.ishanitech.ipasal.ipasalwebservice.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ishanitech.ipasal.ipasalwebservice.utilities.UserDtoDeserializer;

import java.io.Serializable;
import java.util.Collection;

@JsonDeserialize(using = UserDtoDeserializer.class)
public class UserDTO implements UserDetails, Serializable {

	private static final long serialVersionUID = 1L;
	
	private int userId;
    private String username;
    private String password;
    private String fName;
    private String mName;
    private String lName;
    private String email;
    private long phone;
    private int parentId;
    private String authority;
    private int roleId;
    private String token;
    private String city; 
    private String street;
    private boolean enabled;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public String toString() {
        return "{" +
            " userId='" + getUserId() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", fName='" + getfName() + "'" +
            ", mName='" + getmName() + "'" +
            ", lName='" + getlName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", parentId='" + getParentId() + "'" +
            ", authority='" + getAuthority() + "'" +
            ", roleId='" + getRoleId() + "'" +
            ", token='" + getToken() + "'" +
            ", city='" + getCity() + "'" +
            ", street='" + getStreet() + "'" +
            "}";
    }
    
    public UserDTO() {}
    
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    //@JsonIgnore
    public boolean isEnabled() {
        return enabled;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
    	this.authorities = authorities;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }



    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }


    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setToken(String token) {
    	this.token = token;
    }
    
    public String getToken() {
    	return this.token;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    
    public String getStreet() {
        return street;
    }
}
