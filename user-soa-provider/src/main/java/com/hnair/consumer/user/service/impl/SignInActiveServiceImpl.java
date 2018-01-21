package com.hnair.consumer.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.hnair.consumer.dao.spi.ICommonDao;
import com.hnair.consumer.user.model.SignInActive;
import com.hnair.consumer.user.model.SignInAwardRule;
import com.hnair.consumer.user.service.ISignInActiveService;
import com.hnair.consumer.user.vo.SignInActiveVo;

@Service("signInActiveService")
public class SignInActiveServiceImpl implements ISignInActiveService {

	private final static Logger logger = LoggerFactory.getLogger(SignInActiveServiceImpl.class);

	@Resource(name = "ucenterCommonDao")
	private ICommonDao signInActiveDao;

	public SignInActiveVo getSignInActiveByStatus(int status, Date date) {
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("status", status);
		queryMap.put("nowDate", date);
		SignInActiveVo signInActiveVo = new SignInActiveVo();
		try {
			List<SignInActiveVo> signInActiveVoList = signInActiveDao.getListBySqlId(SignInActive.class,
					"querySignInActiveByStatus", queryMap);
			if (CollectionUtils.isEmpty(signInActiveVoList)) {
				logger.info("没有查询到当前时间生效的签到活动");
				return null;
			} else {
				signInActiveVo = signInActiveVoList.get(0);
			}
			Long signInActiveId = signInActiveVo.getId();
			List<SignInAwardRule> signInAwardRuleList = signInActiveDao.getListBySqlId(SignInAwardRule.class, "querySignInAwardRuleByActiveId", "signInActiveId", signInActiveId);
			logger.info("SignInAwardRuleListSize:",signInAwardRuleList.size());
			signInActiveVo.setSignInAwardRuleList(signInAwardRuleList);
		} catch (Exception e) {
			logger.info("查询到当前时间生效的签到活动异常:", e);
		}
		return signInActiveVo;
	}
	
	public SignInActiveVo getSignInActiveById(Long id) {
		SignInActiveVo signInActiveVo = new SignInActiveVo();
		try {
			signInActiveVo = signInActiveDao.getBySqlId(SignInActive.class,
					"querySignInActiveById", "id", id);
			List<SignInAwardRule> signInAwardRuleList = signInActiveDao.getListBySqlId(SignInAwardRule.class, "querySignInAwardRuleByActiveId", "signInActiveId", id);
			logger.info("SignInAwardRuleListSize:",signInAwardRuleList.size());
			signInActiveVo.setSignInAwardRuleList(signInAwardRuleList);
		} catch (Exception e) {
			logger.info("查询活动异常:", e);
		}
		return signInActiveVo;
	}
}
