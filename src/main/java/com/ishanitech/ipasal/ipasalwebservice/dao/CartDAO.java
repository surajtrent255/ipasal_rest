package com.ishanitech.ipasal.ipasalwebservice.dao;

import com.ishanitech.ipasal.ipasalwebservice.dto.CartDTO;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import java.util.List;

public interface CartDAO {

    @SqlUpdate("Insert into cart(product_id, quantity, total_price,customer_id)  values(:productId,:quantity,:totalPrice,:customerId)")
    public Integer addToCart(@BindBean CartDTO cartDTO);

    //get all unordered items in the cart
    @SqlQuery("Select c.*,p.* from cart c inner join products p on c.product_id = p.product_id and c.customer_id = :customerId and c.ordered = 0 and c.deleted = 0")
    public List<CartDTO> getCartDetailsByCustomerId(@Bind("customerId") Integer customerId);

    @SqlUpdate("Update cart set product_id = :productId, quantity = :quantity, total_price = :totalPrice where customer_id = :customerId and cart_id = cartId")
    public Integer updateCart(@BindBean CartDTO cartDTO);

    @SqlUpdate("Update cart set deleted = 1 where cart_id = :cartId and customer_id = :customerId")
    public void deleteFromCart(@Bind("cartId") Integer caerId, @Bind("customerId") Integer customerId);
}
