package io.blacktoast.utils.sqlexecutor.model.executables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class StoreProcedure extends SQLExecutable {

	private static final String SCHEMA_DEFAULT = "dbo";
	private static final String CALL_FORMAT = "CALL %s.%s (%s)";
	private static final String EXEC_FORMAT = "EXEC %s.%s %s";

	/**
	 * Tipo de llamada de ejecucion para el sql, por ejemplo sql server usa EXEC y
	 * DB2 usa CALL
	 */
	public static enum TypeCall {
		CALL, EXEC
	}

	private String schema;
	private String name;
	private Map<String, Object> parametersMap;
	private TypeCall typeCall;

	public StoreProcedure() {
		super();
		this.schema = SCHEMA_DEFAULT;
		this.typeCall = TypeCall.CALL;
	}

	public StoreProcedure(String schema, String name, Map<String, Object> parametersMap) {
		super(new ArrayList<>());
		this.schema = schema;
		this.name = name;
		this.parametersMap = parametersMap;
	}

	public StoreProcedure(String schema, String name, Map<String, Object> parametersMap, String typeCall) {
		super(new ArrayList<>());
		this.schema = schema;
		this.name = name;
		this.parametersMap = parametersMap;
		this.typeCall = TypeCall.valueOf(typeCall);
	}

	@Builder
	public StoreProcedure(List<String> resultColumns, String schema, String name, Map<String, Object> parametersMap,
			String typeCall) {
		super(resultColumns);
		this.schema = schema;
		this.name = name;
		this.parametersMap = parametersMap;
		this.typeCall = TypeCall.valueOf(typeCall);
	}

	public Boolean haveParameters() {
		return parametersMap != null && !parametersMap.isEmpty();
	}

	public String getFullName() {
		return getSchema() + "." + getName();
	}

	/**
	 * 
	 * @return
	 */
	public String getStatementCallString() {

		String paramsString = "";
		if (haveParameters()) {

			for (int i = 0; i < parametersMap.values().size(); i++) {
				paramsString += "?, ";
			}
			paramsString = paramsString.substring(0, paramsString.length() - 2);
		}

		String formatQuery = typeCall == TypeCall.CALL ? CALL_FORMAT : EXEC_FORMAT;
		return String.format(formatQuery, getSchema(), getName(), paramsString);
	}

}
