package io.blacktoast.utils.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.blacktoast.utils.rest.entity.RestCallerConfig;

/**
 * @version 1.1.0
 * 
 *          <p>
 *          Para usarlo se puede inicializarlo o usarlo simplemente.
 *          <p>
 *          Ejemplo:
 *          <p>
 *          <code>RespuestaServicio respuestaServicio = HttpRestCaller.post("http://soyUnaUrl.com.ar", ObjetoBody objetoBody, RespuestaServicio.class);</code>
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
 * 		HttpRestCaller.initialize(config);
 * 	</code>
 */
public class HttpRestCaller implements IRestCaller {

	private static final String ACCEPT = "Accept";

	private static HttpRestCaller INSTANCE;

	private RestTemplate restTemplateCustom;

	private HttpRestCaller() {
		this.restTemplateCustom = createRestTemplateCustom(new RestCallerConfig());
	}

	/**
	 * @return
	 */
	@Bean(name = "HttpRestCaller")
	public static HttpRestCaller getInstance() {

		if (INSTANCE == null) {
			initialize(new RestCallerConfig());
		}
		return INSTANCE;
	}

	/**
	 * @param config
	 */
	public static void initialize(RestCallerConfig config) {
		INSTANCE = new HttpRestCaller();
		INSTANCE.restTemplateCustom = createRestTemplateCustom(config);
	}

	/**
	 * @param config
	 * @return
	 */
	private static RestTemplate createRestTemplateCustom(RestCallerConfig config) {

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		HttpComponentsClientHttpRequestFactory crf = new HttpComponentsClientHttpRequestFactory(httpClient);
		crf.setConnectTimeout(config.getConnectTimeOut());
		crf.setReadTimeout(config.getReadTimeOut());
		crf.setBufferRequestBody(true);

		List<HttpMessageConverter<?>> converters = new ArrayList<>();
		converters.add(new MappingJackson2HttpMessageConverter(new ObjectMapper()));

		RestTemplate restTemplate = new RestTemplate(crf);
		restTemplate.setMessageConverters(converters);

		return restTemplate;
	}

	/**
	 * @param url
	 * @param responseClass
	 * @param headers
	 * @return
	 */
	public static <T> T get(String url, Class<T> responseClass, HttpHeaders headers) {
		HttpHeaders headersToUse = RestCaller.getAllHeaders(headers);

		return getInstance().restTemplateCustom.getForEntity(url, responseClass, headersToUse).getBody();
	}

	/**
	 * @param url
	 * @param requestBody
	 * @param responseClass
	 * @param headers
	 * @return
	 */
	public static <T> T post(String url, Object requestBody, Class<T> responseClass, HttpHeaders headers) {
		HttpHeaders headersToUse = RestCaller.getAllHeaders(headers);

		HttpEntity<Object> request = new HttpEntity<>(requestBody, headersToUse);
		return getInstance().restTemplateCustom.postForEntity(url, request, responseClass).getBody();
	}

	//ESTO QUEDA POR AHORA ASI PARA QUE RESPETE LA SUPERCLASE
	public static <T> T put(String url, Object body, Class<T> clazz, HttpHeaders headers) {

		HttpHeaders headersToUse = RestCaller.getAllHeaders(headers);

		HttpEntity<Object> request = new HttpEntity<>(body, headersToUse);
		getInstance().restTemplateCustom.put(url, request);

		return null;
	}
}
