package com.vcgo.sso.service;

import com.vcgo.common.pojo.JsonResult;
import com.vcgo.pojo.TbUser;

public interface UserService {
	JsonResult checkUserMessage(String userMsg,int type);
	JsonResult userRegister(TbUser user);
	JsonResult login(String username,String password);
	JsonResult getUser(String token);
}
