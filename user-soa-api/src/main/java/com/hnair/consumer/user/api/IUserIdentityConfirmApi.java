package com.hnair.consumer.user.api;

import java.util.List;

import com.hnair.consumer.user.model.NewPayBackInfo;
import com.hnair.consumer.user.model.UserIdentityConfirm;

public interface IUserIdentityConfirmApi {
	
	public Integer saveUserIdentityConfirmInfo(UserIdentityConfirm userConfirm,NewPayBackInfo newpay,String photo);
	
	public Integer updateUserIdentityConfirmInfo(NewPayBackInfo newpay,String photo);
	
	public UserIdentityConfirm selectUserIdentityConfirmInfo(UserIdentityConfirm userConfirm);
	
	public Integer saveNewPayBackInfo(NewPayBackInfo newpay);
	
	public List<NewPayBackInfo> getNewPayBackInfo(NewPayBackInfo newpay);
}
