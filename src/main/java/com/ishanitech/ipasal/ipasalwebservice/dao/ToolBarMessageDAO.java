/**
 * 
 */
package com.ishanitech.ipasal.ipasalwebservice.dao;

import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import com.ishanitech.ipasal.ipasalwebservice.dto.ToolBarMessageDTO;

/**
 * @author Pujan K.C. <pujanov69@gmail.com>
 *
 * Created on Sep 6, 2019
 */
public interface ToolBarMessageDAO {
	
	@SqlQuery("SELECT * FROM toolbar_message WHERE message_id = 1")
	ToolBarMessageDTO getToolBarMessage();
	
	@SqlUpdate("UPDATE toolbar_message SET message = :message WHERE message_id = 1")
	void addToolBarMessage(@BindBean ToolBarMessageDTO toolbarMessageDTO);

}
