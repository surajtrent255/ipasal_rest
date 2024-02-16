package com.ishanitech.ipasal.ipasalwebservice.dao;

import com.ishanitech.ipasal.ipasalwebservice.dto.ShippingDTO;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface ShippingDAO {

  @GetGeneratedKeys
  @SqlUpdate("Insert into shipping_details(customer_id, address, city, state, zipcode, phone, shipping_type_id) values(:customerId, :address, :city, :state, :zipcode, :phone, :shippingTypeId)")
    public Integer addShippingDetails(@BindBean ShippingDTO shippingDTO);

  @SqlQuery("Select sd.*,st.* from shipping_details sd inner join shipping_type st on sd.shipping_type = st.shipping_id where sd.customer_id = :customerId")
    public ShippingDTO getShippingDetailsById(@Bind("customerId") Integer customerId);

  @SqlUpdate("Update shipping_details set address = :address, city = :city, zipcode = :zipcode, email = :email, phone = :phone, shipping_type =:ShippingType where customer_id = :customerId and shipping_details_id =:shippingDetailsId")
    public Integer updateShippingDetails(@BindBean ShippingDTO shippingDTO);

  @SqlQuery("SELECT * FROM shipping_details sd INNER JOIN orders o ON sd.shipping_details_id = o.shipping_details_id WHERE o.order_id =:orderId")
  public List<ShippingDTO> getShippingDetailsByOrderId(@Bind("orderId") Integer orderId);


}
