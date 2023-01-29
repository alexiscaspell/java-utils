package io.blacktoast.utils.rest.entity;


import java.util.HashMap;
import java.util.Map;

public class RestCallerException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -332887165637456314L;
	
	private Map<String, Object> responseBody = new HashMap<String, Object>();
	private Integer statusCode;

	public RestCallerException(Map<String, Object> body, Integer responseCode) {
		this.responseBody = body;
		this.statusCode = responseCode;
	}

	public Map<String, Object> getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(Map<String, Object> responseBody) {
		this.responseBody = responseBody;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

}
