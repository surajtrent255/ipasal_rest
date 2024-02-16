package com.ishanitech.ipasal.ipasalwebservice.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

import com.ishanitech.ipasal.ipasalwebservice.dto.MerchantDTO;

@UseStringTemplate3StatementLocator
public interface MerchantDAO {

	@GetGeneratedKeys
	@SqlUpdate("INSERT INTO merchant(merchant_name, business_type, merchant_type, merchant_desc, street, city, contact_primary, contact_secondary) VALUES(:merchantName, :businessType, :merchantType, :merchantDesc, :street, :city, :contactPrimary, :contactSecondary)")
	public Integer addMerchant(@BindBean MerchantDTO merchantDTO);

	@SqlQuery("SELECT * FROM merchant WHERE deleted = 0")
	public List<MerchantDTO> getAllMerchants();

	@SqlQuery("SELECT * FROM merchant WHERE merchant_id = :merchantId AND deleted = 0")
	public MerchantDTO getMerchantById(@Bind("merchantId") Integer merchantId);
	
	
	@SqlUpdate("UPDATE merchant SET deleted = 1 WHERE merchant_id = :merchantId")
	public void removeMerchant(@Bind("merchantId") Integer merchantId);

	
	//Query for searching from the merchantlist
	
	@SqlQuery("SELECT * FROM merchant WHERE deleted = 0 AND merchant_name LIKE :searchKey")
	public List<MerchantDTO> searchMerchant(@Bind("searchKey") String searchKey);

	//Query for the updating the merchant information. This works
	
	@SqlUpdate("UPDATE merchant SET merchant_name =:merchantName, business_type =:businessType, merchant_type =:merchantType, merchant_desc =:merchantDesc, street =:street, city =:city, contact_primary =:contactPrimary, contact_secondary =:contactSecondary WHERE merchant_id =:merchantId")
	public Integer updateMerchant(@Bind("merchantId") Integer merchantId, @BindBean MerchantDTO merchantDTO);
	
}
