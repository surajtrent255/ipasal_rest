package com.ishanitech.ipasal.ipasalwebservice.dao;

import com.ishanitech.ipasal.ipasalwebservice.dto.ShippingRateDTO;
import org.skife.jdbi.v2.sqlobject.*;

import java.util.List;

/**
 * @author 'Azens Eklak'
 * email: azens1995@gmail.com
 * created on Mar 01, 2019
 * since 2017
 **/
public interface ShippingRateDAO {
    /*
    * Inserting shipping info [location, amount, information] to the database
    * @return shipping Id
    * */
    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO shipping_rate(location ,amount, information) VALUES(:location, :amount, :information)")
    public Integer addShippingInfo(@BindBean ShippingRateDTO shippingRateDTO);


    /*
    * Retrieving all the shipping information for display to the admin
    * */
    @SqlQuery("SELECT * FROM shipping_rate")
    public List<ShippingRateDTO> getAllShippingInfo();

    /*
    * Retrieving shipping information from the database as per the address provided
    * */
    @SqlQuery("SELECT * FROM shipping_rate WHERE location = :location")
    public ShippingRateDTO getShippingInfo(@Bind("location") String location);


    /*
    * Updating the shipping rate for the given/mentioned Id
    * */
    @SqlUpdate("UPDATE shipping_rate SET amount = :shipAmount WHERE id = :shipId")
    public void updateShippingInfo(@Bind("shipAmount") Integer shipAmount,
                                   @Bind("shipId") Integer shipId);

}
