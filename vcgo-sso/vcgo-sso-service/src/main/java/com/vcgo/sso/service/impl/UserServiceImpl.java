package com.vcgo.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.DoubleAdder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.vcgo.common.pojo.JsonResult;
import com.vcgo.common.utils.JsonUtils;
import com.vcgo.mapper.TbUserMapper;
import com.vcgo.pojo.TbUser;
import com.vcgo.pojo.TbUserExample;
import com.vcgo.pojo.TbUserExample.Criteria;
import com.vcgo.sso.jedis.JedisClient;
import com.vcgo.sso.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper tbUserMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${USER_INFO}")
	private String USER_INFO;
	@Value("${USER_INFO_EXPIRETIME}")
	private int USER_INFO_EXPIRETIME;
	@Override
	public JsonResult checkUserMessage(String userMsg, int type) {
		TbUserExample example=new TbUserExample();
		Criteria criteria = example.createCriteria();
		if(type==1){//1用户名
			criteria.andUsernameEqualTo(userMsg);
			
		}else if(type==2){
			criteria.andPhoneEqualTo(userMsg);

		}else if(type==3){
			criteria.andEmailEqualTo(userMsg);
		}else{
			return JsonResult.build(400, "错误的验证类型");
		}
		if(tbUserMapper.countByExample(example)>0){
			return JsonResult.ok(false);
		}
		return JsonResult.ok(true);
	}

	@Override
	public JsonResult userRegister(TbUser user) {
		if(!StringUtils.isNotBlank(user.getUsername())){
			return JsonResult.build(400, "用户名不能为空");
		}
		if(!(boolean) checkUserMessage(user.getUsername(),1).getData()){
			return JsonResult.build(400, "用户名已存在");
		}
		if(!StringUtils.isNotBlank(user.getPassword())){
			return JsonResult.build(400, "密码不能为空");
		}
		if(StringUtils.isNotBlank(user.getPhone())){
			if(!(boolean) checkUserMessage(user.getPhone(),2).getData()){
				return JsonResult.build(400, "电话号码已存在");
			}
		}
		if(StringUtils.isNotBlank(user.getEmail())){
			if(!(boolean) checkUserMessage(user.getEmail(),3).getData()){
				return JsonResult.build(400, "Email已存在");
			}
		}
		user.setCreated(new Date());
		user.setUpdated(new Date());
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		
		tbUserMapper.insert(user);
		return JsonResult.ok();
	}

	@Override
	public JsonResult login(String username, String password) {
		TbUserExample example=new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> selectByExample = tbUserMapper.selectByExample(example);
		if(selectByExample.size()<0){
			return JsonResult.build(401, "用户名或密码错误");
		}
		if(!selectByExample.get(0).getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
			return JsonResult.build(401, "用户名或密码错误");
		}
		String uuid=UUID.randomUUID().toString();
		jedisClient.set(USER_INFO+":"+uuid,JsonUtils.objectToJson(selectByExample.get(0)));
		jedisClient.expire(USER_INFO+":"+uuid, USER_INFO_EXPIRETIME);
		return JsonResult.ok(uuid);
	}

	@Override
	public JsonResult getUser(String token) {
		String string = jedisClient.get(USER_INFO+":"+token);
		if(!StringUtils.isNotBlank(string)){
			return JsonResult.build(401, "身份信息过期，请重新登录");
		}
		TbUser user = JsonUtils.jsonToPojo(string, TbUser.class);
		jedisClient.expire(USER_INFO+":"+token,USER_INFO_EXPIRETIME);
		return JsonResult.ok(user);
	}

}
