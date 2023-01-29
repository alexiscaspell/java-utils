package io.blacktoast.utils.rest;

import org.springframework.http.HttpHeaders;

import io.blacktoast.utils.rest.entity.RestCallerConfig;
import io.blacktoast.utils.rest.entity.RestCallerException;

/**
 * @version 1.1.0
 * 
 *          <p>
 *          Para usarlo se puede inicializarlo o usarlo simplemente.
 *          <p>
 *          Ejemplo:
 *          <p>
 *          <code>RespuestaServicio respuestaServicio = RestCaller.post("http://soyUnaUrl.com.ar", ObjetoBody objetoBody, RespuestaServicio.class);</code>
 * 
 *          <p>
 *          Para inicializarlo con una <b>configuracion</b> se usa el metodo
 *          "initialize" una unica vez
 *          <p>
 *          Ejemplo:
 *          <p>
 *          <code>
 * 		RestCallerConfig config = new RestCallerConfig();
 * 		confing.setConnectTimeOut(500); 
 * 		confing.setReadTimeOut(200);
 * 
 * 		RestCaller.initialize(config);
 * 	</code>
 */
public interface IRestCaller {

	/**
	 * @param config
	 */
	static void initialize(RestCallerConfig config) {

		HttpRestCaller.initialize(config);

		// HAY QUE AGREGAR UN INIT PARA HTTPS
	}

	/**
	 * @param url
	 * @param responseClass
	 * @param headers
	 * @return
	 */
	public static <T> T get(String url, Class<T> responseClass, HttpHeaders headers)
			throws RestCallerException, Exception {

		return null;
	}

	/**
	 * @param url
	 * @param requestBody
	 * @param responseClass
	 * @param headers
	 * @return
	 */
	public static <T> T post(String url, Object requestBody, Class<T> responseClass, HttpHeaders headers)
			throws RestCallerException, Exception {

		return null;
	}

	/**
	 * @param url
	 * @param requestBody
	 * @param responseClass
	 * @param headers
	 * @return
	 */
	public static <T> T put(String url, Object requestBody, Class<T> responseClass, HttpHeaders headers)
			throws RestCallerException, Exception {

		return null;
	}
	
}
