package io.blacktoast.utils.redis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Objeto usado para la configuracion de redis en la aplicacion, para usar es
 * necesario crear un objeto en tu proyecto con el anotation <b>Configuration de
 * Spring</b> que devuelva una instancia de este objeto con sus parametros
 * seteados
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RedisUserConfig {

	private String url;
	private Integer port;
	private Integer maxIdlePool;
	private Integer maxTotalPool;
	private Integer popTimeOut;

}
