package io.blacktoast.utils.sqlexecutor.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.blacktoast.utils.sqlexecutor.SQLExecutorService;
import io.blacktoast.utils.sqlexecutor.model.executables.SQLQuery;
import io.blacktoast.utils.sqlexecutor.model.executables.StoreProcedure;
import io.blacktoast.utils.sqlexecutor.utils.DSManager;
import io.blacktoast.utils.sqlexecutor.utils.ResultBuilder;
import io.blacktoast.utils.sqlexecutor.utils.TypesConversor;

@Service
public class SQLExecutorServiceImpl implements SQLExecutorService {

	private static final Logger LOG = LoggerFactory.getLogger(SQLExecutorServiceImpl.class);

	/**
	 * Loguea el SP antes de ejecutarlo
	 *
	 * @param sp
	 */
	private void logSPBeforeExecute(StoreProcedure sp) {

		String parameters = sp.haveParameters() ? "Parameters: " + sp.getParametersMap().toString()
				: "without Parameters";

		LOG.debug(String.format("SP: %s %s", sp.getFullName(), parameters));
	}

	/**
	 * Loguea la QUERY antes de ejecutarlo
	 *
	 * @param query
	 */
	private void logQueryBeforeExecute(SQLQuery query) {

		LOG.debug(String.format("SQL QUERY: %s", query));
	}

	/**
	 *
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private List<String> getColumnsNames(ResultSet resultSet) throws SQLException {

		List<String> columnsNames = new ArrayList<>();
		int columnSize = resultSet.getMetaData().getColumnCount();
		for (int i = 1; i <= columnSize; i++) {
			columnsNames.add(resultSet.getMetaData().getColumnLabel(i));
		}
		return columnsNames;
	}

	/**
	 *
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private List<Map<String, Object>> getResultListMap(ResultSet resultSet) throws SQLException {

		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
		if (resultSet == null) {
			return resultMap;
		}

		while (resultSet.next()) {

			Map<String, Object> row = new LinkedHashMap<>();
			for (String columnName : getColumnsNames(resultSet)) {
				row.put(columnName, resultSet.getObject(columnName));
			}
			resultMap.add(row);
		}
		return resultMap;
	}

	/**
	 *
	 * @param con
	 * @param sp
	 * @return
	 * @throws SQLException
	 */
	private CallableStatement buildCalleableStatement(Connection con, StoreProcedure sp) throws SQLException {

		CallableStatement stmt = con.prepareCall(sp.getStatementCallString());

		if (sp.haveParameters()) {
			for (String key : sp.getParametersMap().keySet()) {
				stmt.setString(key, TypesConversor.toString(sp.getParametersMap().get(key)));
			}
		}

		return stmt;
	}

	@Override
	public List<Map<String, Object>> execute(StoreProcedure sp, String dataSourceName) throws SQLException {

		Connection con = DSManager.get(dataSourceName).getConnection();
		List<Map<String, Object>> result = execute(sp, con);

		commit(con);
		close(con);

		return result;
	}

	@Override
	public List<Map<String, Object>> execute(SQLQuery query, String dataSourceName) throws SQLException {

		Connection con = DSManager.get(dataSourceName).getConnection();
		List<Map<String, Object>> result = execute(query, con);

		commit(con);
		close(con);

		return result;
	}

	@Override
	public Connection getConnection(String dataSourceName) throws SQLException {
		return DSManager.get(dataSourceName).getConnection();
	}

	@Override
	public List<Map<String, Object>> execute(StoreProcedure sp, Connection con) throws SQLException {

		logSPBeforeExecute(sp);
		try {
			con.setAutoCommit(false);

			CallableStatement stmt = buildCalleableStatement(con, sp);
			stmt.execute();

			List<Map<String, Object>> result = getResultListMap(stmt.getResultSet());
			return ResultBuilder.getResultChangedColumns(result, sp.getResultColumns());

		} catch (Exception e) {

			LOG.error(e.getLocalizedMessage(), e);
			if (con != null) {
				close(con);
			}
			throw e;
		}
	}

	@Override
	public List<Map<String, Object>> execute(SQLQuery query, Connection con) throws SQLException {

		logQueryBeforeExecute(query);
		try {
			con.setAutoCommit(false);

			PreparedStatement stmt = con.prepareStatement(query.getQuery());
			stmt.execute();

			List<Map<String, Object>> result = getResultListMap(stmt.getResultSet());
			return ResultBuilder.getResultChangedColumns(result, query.getResultColumns());

		} catch (Exception e) {

			LOG.error(e.getLocalizedMessage(), e);
			if (con != null) {
				close(con);
			}
			throw e;
		}
	}

	@Override
	public void close(Connection con) throws SQLException {
		if (con != null) {
			con.setAutoCommit(true);
			con.close();
		}
	}

	@Override
	public void commit(Connection con) throws SQLException {
		if (con != null) {
			con.commit();
		}
	}

	@Override
	public void rollback(Connection con) throws SQLException {
		if (con != null) {
			con.rollback();
		}
	}

}
