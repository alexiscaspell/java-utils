package io.blacktoast.utils.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

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
public abstract class RestCaller{

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
	public static <T> T get(String url, Class<T> responseClass, HttpHeaders headers) throws RestCallerException {
		try {

			HttpHeaders headersToUse = getAllHeaders(headers);

			return isHttps(url) ? HttpsRestCaller.get(url, responseClass, headersToUse)
					: HttpRestCaller.get(url, responseClass, headersToUse);

		} catch (RestCallerException e) {
			throw e;
		} catch (Exception e) {
			wrapException(e);
		}

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
			throws RestCallerException {
		try {

			HttpHeaders headersToUse = getAllHeaders(headers);

			return isHttps(url) ? HttpsRestCaller.post(url, requestBody, responseClass, headersToUse)
					: HttpRestCaller.post(url, requestBody, responseClass, headersToUse);

		} catch (RestCallerException e) {
			throw e;
		} catch (Exception e) {
			wrapException(e);
		}

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
			throws RestCallerException {
		try {

			HttpHeaders headersToUse = getAllHeaders(headers);

			return isHttps(url) ? HttpsRestCaller.post(url, requestBody, responseClass, headersToUse)
					: HttpRestCaller.post(url, requestBody, responseClass, headersToUse);

		} catch (RestCallerException e) {
			throw e;
		} catch (Exception e) {
			wrapException(e);
		}

		return null;
	}

	static void wrapException(Exception e) throws RestCallerException {
		e.printStackTrace();
		throw new RestCallerException(null, 500);
	}

	protected static boolean isHttps(String url) {
		return url.contains("https");
	}
	
	public static HttpHeaders getDefaultHeaders() {
		HttpHeaders headers = new HttpHeaders();
		List<MediaType> mediaTypesAccepted = new ArrayList<MediaType>();
		mediaTypesAccepted.add(MediaType.APPLICATION_JSON);

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(mediaTypesAccepted);

		return headers;
	}
	
	public static HttpHeaders getAllHeaders(HttpHeaders headers) {
		return headers == null ? getDefaultHeaders() : headers;
	}
}
