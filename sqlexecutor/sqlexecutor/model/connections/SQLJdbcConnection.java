package io.blacktoast.utils.sqlexecutor.model.connections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SQLJdbcConnection {

	private String driverClassName;
	private String url;
	private String username;
	private String password;
	private Integer initialSize;
	private Integer maxTotal;
	private Integer maxIdle;

}
