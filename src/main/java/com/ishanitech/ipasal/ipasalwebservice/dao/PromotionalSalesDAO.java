package com.ishanitech.ipasal.ipasalwebservice.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import com.ishanitech.ipasal.ipasalwebservice.dto.PromotionalSalesDTO;

public interface PromotionalSalesDAO {

	@SqlQuery("SELECT * FROM promotional_sales")
	List<PromotionalSalesDTO> getAllPromotions();

	@SqlQuery("SELECT * FROM promotional_sales WHERE promotional_sales_id = :promotionalSalesId")
	PromotionalSalesDTO getPromotionById(@Bind("promotionalSalesId") Integer promotionalSalesId);

	@SqlUpdate("UPDATE promotional_sales SET promotional_title = :promotionalTitle, promotional_tag = :promotionalTag WHERE promotional_sales_id = :promoId")
	Integer updatePromotionDetails(@Bind("promoId") Integer promotionalSalesId,@BindBean PromotionalSalesDTO promotionalSalesDTO);

	@SqlQuery("SELECT * FROM promotional_sales WHERE active = 1")
	List<PromotionalSalesDTO> getAllActivePromotions();

	@SqlUpdate("UPDATE promotional_sales SET active = 1 WHERE promotional_sales_id = :promotionalSalesId")
	int setPromotionActive(@Bind("promotionalSalesId") Integer promotionalSalesId);

	@SqlUpdate("UPDATE promotional_sales SET active = 0 WHERE promotional_sales_id = :promotionalSalesId")
	int setPromotionInactive(@Bind("promotionalSalesId") Integer promotionalSalesId);

}
