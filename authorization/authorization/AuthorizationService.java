package io.blacktoast.utils.authorization;

import io.blacktoast.utils.authorization.model.auth.Token;

/**
 * @author blacktoast
 * @version 1.0.0
 */
public interface AuthorizationService {

	/**
	 * @param idRedis
	 * @return
	 */
	Token getUserToken(String idRedis);
}
