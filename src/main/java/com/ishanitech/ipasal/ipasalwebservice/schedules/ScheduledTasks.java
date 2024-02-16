/**
 * 
 */
package com.ishanitech.ipasal.ipasalwebservice.schedules;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ishanitech.ipasal.ipasalwebservice.Services.HotDealsService;
import com.ishanitech.ipasal.ipasalwebservice.dto.HotDealsDTO;



/**
 * @author Pujan K.C. <pujanov69@gmail.com>
 *
 * Created on Sep 19, 2019
 */
@Component
public class ScheduledTasks {
	
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private HotDealsService hotDealsService;
    
    public ScheduledTasks(HotDealsService hotDealsService) {
    	this.hotDealsService = hotDealsService;
    }
    
    @Scheduled(cron = "0 0 0 * * *")
    public void checkHotDealsExpiry() throws ParseException {
        log.info("The time is now {}", dateFormat.format(new Date()));
        List<HotDealsDTO> hotDealsList =  hotDealsService.getAllHotDeals();
        if(hotDealsList != null && hotDealsList.size() > 0) {
			for(int i=0; i<hotDealsList.size();i++) {
				String finishDate = hotDealsList.get(i).getFinishDate();
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date finDate = sdf.parse(finishDate);
				
				
				if(finDate.before(date)) {
					hotDealsService.deleteHotDeal(hotDealsList.get(i).getHotDealId());
					log.info("Hot deal with hot deal id " + hotDealsList.get(i).getHotDealId() + " deleted!");
				}
			}
		}
    }
}
