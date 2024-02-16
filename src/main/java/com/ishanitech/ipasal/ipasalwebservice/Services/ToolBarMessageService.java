/**
 * 
 */
package com.ishanitech.ipasal.ipasalwebservice.Services;

import com.ishanitech.ipasal.ipasalwebservice.dto.ToolBarMessageDTO;

/**
 * @author Pujan K.C. <pujanov69@gmail.com>
 *
 * Created on Sep 6, 2019
 */
public interface ToolBarMessageService {

	ToolBarMessageDTO getToolBarMessage();
	
	void addToolBarMessage(ToolBarMessageDTO toolBarMessageDTO);
}
