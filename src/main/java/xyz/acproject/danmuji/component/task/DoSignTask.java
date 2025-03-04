package xyz.acproject.danmuji.component.task;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.entity.user_data.UserMedal;
import xyz.acproject.danmuji.http.HttpOtherData;
import xyz.acproject.danmuji.http.HttpUserData;
import xyz.acproject.danmuji.service.SetService;
import xyz.acproject.danmuji.tools.CurrencyTools;

import java.util.List;

@Component("dosignTask")
public class DoSignTask {
	@Autowired
	private SetService setService;
	private static Logger LOGGER = LogManager.getLogger(DoSignTask.class);
	public void dosign() {
		if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
			HttpUserData.httpGetDoSign();
		}else {
			LOGGER.error("定时任务抛出： 未登录 签到失败");
		}
	}
	public void clockin() {
		if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
			List<UserMedal> userMedals = CurrencyTools.getAllUserMedals();
			int max = CurrencyTools.clockIn(userMedals);
			if(max == userMedals.size()){
				HttpOtherData.httpPOSTSetClockInRecord();
			}
		}else{
			LOGGER.error("定时任务抛出： 未登录 打卡失败");
		}
	}

	public void test(){
		System.err.println("test");
	}
}
