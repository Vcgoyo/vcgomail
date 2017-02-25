package com.vcgo.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vcgo.common.pojo.JsonResult;
import com.vcgo.common.utils.CookieUtils;
import com.vcgo.common.utils.JsonUtils;
import com.vcgo.pojo.TbUser;
import com.vcgo.sso.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public JsonResult UserCheck(@PathVariable String param,@PathVariable Integer type) {
		return userService.checkUserMessage(param, type);
	}
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult register(TbUser user){
		return userService.userRegister(user);
	}
	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult login(String username,String password,HttpServletRequest request,HttpServletResponse response){
		
		JsonResult result = userService.login(username, password);
		CookieUtils.setCookie(request, response, "TT_TOKEN", result.getData().toString());
		return result;
	}
//	@RequestMapping("/user/token/{token}")
//	@ResponseBody
//	public String getUserByToken(@PathVariable String token,String callback) {
//		JsonResult result = userService.getUser(token);
//		if(StringUtils.isNotBlank(callback)){
//			return callback+"("+JsonUtils.objectToJson(result)+");";
//		}
//		return JsonUtils.objectToJson(result);
//	}
	@RequestMapping("/user/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token,String callback) {
		JsonResult result = userService.getUser(token);
		if(StringUtils.isNotBlank(callback)){
			MappingJacksonValue value=new MappingJacksonValue(result);
			value.setJsonpFunction(callback);
			return value;
		}
		return result;
	}
	
	
}
