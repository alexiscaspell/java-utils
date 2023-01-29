package io.blacktoast.utils.rest;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import io.blacktoast.utils.bean.IntrospectionUtil;
import io.blacktoast.utils.rest.entity.RestCallerException;

public class HttpsRestCaller implements IRestCaller{

	private static final String METHOD_DELETE = "DELETE";
	private static final String METHOD_GET = "GET";
	private static final String METHOD_POST = "POST";
	private static final String METHOD_PUT = "PUT";
	private static final String GZIP_ENCODING = "gzip";
	private static final Charset UTF_8_CHAR_SET = Charset.forName("UTF-8");

	private final static JsonParser parser = new JsonParser();

	@SuppressWarnings("unchecked")
	private static InputStream doRequest(String url, String requestMethod, String mapBody, HttpHeaders headers)
			throws JsonSyntaxException, RestCallerException {

		try {

			HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
			conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
			headers.keySet().forEach(k -> conn.setRequestProperty(k, headers.get(k).get(0)));
			conn.setDoOutput(requestMethod.equals(METHOD_POST) || requestMethod.equals(METHOD_PUT));
			conn.setRequestMethod(requestMethod);

			if (mapBody != null && !mapBody.trim().isEmpty()) {
				conn.getOutputStream().write(mapBody.getBytes());
				conn.getOutputStream().close();
			}

			if (conn.getResponseCode() > 399) {
				throw new RestCallerException(
						(new Gson()).fromJson(unmarshallToJson(getWrappedInputStream(conn.getErrorStream(),
								GZIP_ENCODING.equalsIgnoreCase(conn.getContentEncoding()))), Map.class),
						conn.getResponseCode());
			} else {
				return getWrappedInputStream(conn.getInputStream(),
						GZIP_ENCODING.equalsIgnoreCase(conn.getContentEncoding()));
			}
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private static InputStream getWrappedInputStream(InputStream is, boolean gzip) throws IOException {
		/*
		 * TODO: What about this? ---------------------- "Java clients which use
		 * java.util.zip.GZIPInputStream() and wrap it with a java.io.BufferedReader()
		 * to read streaming API data will encounter buffering on low volume streams,
		 * since GZIPInputStream's available() method is not suitable for streaming
		 * purposes. To fix this, create a subclass of GZIPInputStream() which overrides
		 * the available() method."
		 * 
		 * https://dev.twitter.com/docs/streaming-api/concepts#gzip-compression
		 */
		if (gzip) {
			return new BufferedInputStream(new GZIPInputStream(is));
		} else {
			return new BufferedInputStream(is);
		}
	}

	private static JsonElement unmarshallToJson(InputStream jsonContent) {
		try {
			JsonElement element = parser.parse(new InputStreamReader(jsonContent, UTF_8_CHAR_SET));
			if (element.isJsonObject()) {
				return element.getAsJsonObject();
			} else if (element.isJsonArray()) {
				return element.getAsJsonArray();
			} else {
				throw new IllegalStateException("Unknown content found in response." + element);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			closeStream(jsonContent);
		}
	}

	/**
	 * Close stream.
	 * 
	 * @param is the is
	 */
	private static void closeStream(InputStream is) {
		try {
			if (is != null) {
				is.close();
			}
		} catch (IOException e) {
			new RuntimeException();
		}
	}

	public static <T> T get(String url, Class<T> clazz, HttpHeaders headers) throws RestCallerException {

		return (new Gson()).fromJson(unmarshallToJson(doRequest(url, METHOD_GET, null, headers)), clazz);
	}

	public static <T> T post(String url, Object body, Class<T> clazz, HttpHeaders headers)
			throws RestCallerException, JsonSyntaxException, JsonProcessingException {

		return (new Gson()).fromJson(
				unmarshallToJson(doRequest(url, METHOD_POST, IntrospectionUtil.objectToJsonWithJackson(body), headers)),
				clazz);
	}

	public static <T> T put(String url, Object body, Class<T> clazz, HttpHeaders headers)
			throws RestCallerException, JsonSyntaxException, JsonProcessingException {

		return (new Gson()).fromJson(
				unmarshallToJson(doRequest(url, METHOD_PUT, IntrospectionUtil.objectToJsonWithJackson(body), headers)),
				clazz);
	}

}
