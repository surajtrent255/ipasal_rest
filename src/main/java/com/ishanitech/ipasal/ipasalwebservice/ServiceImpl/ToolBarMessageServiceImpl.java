/**
 * 
 */
package com.ishanitech.ipasal.ipasalwebservice.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ishanitech.ipasal.ipasalwebservice.Services.DbService;
import com.ishanitech.ipasal.ipasalwebservice.Services.ToolBarMessageService;
import com.ishanitech.ipasal.ipasalwebservice.dao.ToolBarMessageDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ToolBarMessageDTO;

/**
 * @author Pujan K.C. <pujanov69@gmail.com>
 *
 * Created on Sep 6, 2019
 */
@Service
public class ToolBarMessageServiceImpl implements ToolBarMessageService {

private DbService dbService;
	
	@Autowired
	public ToolBarMessageServiceImpl(DbService dbService) {
		super();
		this.dbService = dbService;
	}
	
	@Override
	public ToolBarMessageDTO getToolBarMessage() {
		ToolBarMessageDAO toolBarMessageDAO = dbService.getDao(ToolBarMessageDAO.class);
		return toolBarMessageDAO.getToolBarMessage();
	}

	@Override
	public void addToolBarMessage(ToolBarMessageDTO toolBarMessageDTO) {
		ToolBarMessageDAO toolBarMessageDAO = dbService.getDao(ToolBarMessageDAO.class);
		toolBarMessageDAO.addToolBarMessage(toolBarMessageDTO);
		
	}

}
