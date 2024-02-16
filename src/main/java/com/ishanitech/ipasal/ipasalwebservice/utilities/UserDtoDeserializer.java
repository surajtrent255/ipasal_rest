package com.ishanitech.ipasal.ipasalwebservice.utilities;

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.ishanitech.ipasal.ipasalwebservice.dto.UserDTO;

import org.springframework.security.core.authority.AuthorityUtils;

public class UserDtoDeserializer extends JsonDeserializer<UserDTO> {

	@Override
	public UserDTO deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        UserDTO user = new UserDTO();
        ObjectCodec oc = p.getCodec();
        JsonNode node = oc.readTree(p);
        Iterator<JsonNode> elements = null;
        
        if(node.get("authorities") != null) {
        	elements = node.get("authorities").elements();
        }
        
        if(node.get("userId") != null) {
        	user.setUserId(Integer.parseInt(node.get("userId").toString()));
        }
        
        user.setUsername(UserDtoDeserializer.removeDoubleQuote(node.get("username").toString()));
        user.setPassword(UserDtoDeserializer.removeDoubleQuote(node.get("password").toString()));
        user.setfName(UserDtoDeserializer.removeDoubleQuote(node.get("fName").toString()));
        user.setlName(UserDtoDeserializer.removeDoubleQuote(node.get("lName").toString()));
        user.setEmail(UserDtoDeserializer.removeDoubleQuote(node.get("email").toString()));
        if(node.get("enabled") != null){
        	user.setEnabled(Boolean.parseBoolean(node.get("enabled").toString()));
        }
        
        if(node.get("mName") != null) {
        	user.setmName(UserDtoDeserializer.removeDoubleQuote(node.get("mName").toString()));
        }
        
        if(node.get("authority") != null) {
        	user.setAuthority(node.get("authority").toString());
        } else {
        	user.setAuthority("ROLE_CUSTOMER");
        }
        
        
        user.setPhone(Long.parseLong(UserDtoDeserializer.removeDoubleQuote(node.get("phone").toString())));
        if(node.get("parentId") != null) {
        	user.setParentId(Integer.parseInt(node.get("parentId").toString()));
        }
        
        if(node.get("roleId") != null) {
        	user.setRoleId(Integer.parseInt(node.get("roleId").toString()));
        }
        user.setStreet(UserDtoDeserializer.removeDoubleQuote(node.get("street").toString()));
        user.setCity(UserDtoDeserializer.removeDoubleQuote(node.get("city").toString()));
        if(node.get("token") != null){
        	user.setToken(UserDtoDeserializer.removeDoubleQuote(node.get("token").toString()));
        }
        
        if(elements != null) {
	        while(elements.hasNext()) {
	            JsonNode next = elements.next();
	            JsonNode authority = next.get("authority");
	            user.setAuthorities(AuthorityUtils.createAuthorityList(authority.asText()));
	        }
        }
		return user;
    }
    
    private static String removeDoubleQuote(String inputString) {
        return inputString.replace('"', ' ').trim();
    }

}