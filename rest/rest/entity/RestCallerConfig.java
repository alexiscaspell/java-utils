package io.blacktoast.utils.rest.entity;

public class RestCallerConfig {

	private static final Integer CONNECT_TIMEOUT_MILLISECON_DEFAULT = 10000;
	private static final Integer READ_TIMEOUT_MILLISECON_DEFAULT = 10000;

	private Integer connectTimeOut;
	private Integer readTimeOut;

	public RestCallerConfig() {
		super();
		this.connectTimeOut = CONNECT_TIMEOUT_MILLISECON_DEFAULT;
		this.readTimeOut = READ_TIMEOUT_MILLISECON_DEFAULT;
	}

	public Integer getConnectTimeOut() {
		return connectTimeOut;
	}

	public void setConnectTimeOut(Integer connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}

	public Integer getReadTimeOut() {
		return readTimeOut;
	}

	public void setReadTimeOut(Integer readTimeOut) {
		this.readTimeOut = readTimeOut;
	}

}
