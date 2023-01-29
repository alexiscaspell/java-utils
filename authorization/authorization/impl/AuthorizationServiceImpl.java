package io.blacktoast.utils.authorization.impl;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import io.blacktoast.utils.authorization.AuthorizationService;
import io.blacktoast.utils.authorization.model.auth.Token;
import io.blacktoast.utils.redis.RedisService;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

	private static Logger LOG = Logger.getLogger(AuthorizationServiceImpl.class);

	@Autowired
	private RedisService redisService;

	@Override
	public Token getUserToken(String idRedis) {

		String tokenStr = redisService.getByKey(idRedis);
		Token token = new Token();

		LOG.info(new JSONObject(token));
		if (tokenStr != null && !tokenStr.isEmpty()) {
			token = new Gson().fromJson(tokenStr, Token.class);
		}

		return token;
	}
}
