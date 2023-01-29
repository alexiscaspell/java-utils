package io.blacktoast.utils.sqlexecutor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import io.blacktoast.utils.sqlexecutor.model.executables.SQLQuery;
import io.blacktoast.utils.sqlexecutor.model.executables.StoreProcedure;

/**
 * 
 * Se debe usar con un "@Autowired" de springboot y habiendo cargado los
 * datasource previemente en el
 * {@link io.blacktoast.utils.sqlexecutor.utils.DSManager}
 * 
 * @version: 2.0.3
 */
public interface SQLExecutorService {

	/**
	 * Ejecuta un SP en el datasource parametro existente en el
	 * {@link io.blacktoast.utils.sqlexecutor.utils.DSManager}. Realiza el commit y el
	 * cerrado de la conexion al terminar de ejecutar.
	 * 
	 * @param sp
	 * @param dataSourceName
	 * @return
	 * @throws SQLException
	 */
	List<Map<String, Object>> execute(StoreProcedure sp, String dataSourceName) throws SQLException;

	/**
	 * Ejecuta una query en el datasource parametro existente en el
	 * {@link io.blacktoast.utils.sqlexecutor.utils.DSManager}. Realiza el commit y el
	 * cerrado de la conexion al terminar de ejecutar.
	 * 
	 * @param query
	 * @param dataSourceName
	 * @return
	 * @throws SQLException
	 */
	List<Map<String, Object>> execute(SQLQuery query, String dataSourceName) throws SQLException;

	/**
	 * Obtiene la conexion de un datasource agregado en el
	 * {@link io.blacktoast.utils.sqlexecutor.utils.DSManager}.
	 * 
	 * @param dataSourceName
	 * @return
	 * @throws SQLException
	 */
	Connection getConnection(String dataSourceName) throws SQLException;

	/**
	 * Ejecuta un SP usando la conexion por parametro. Este metodo es manual por lo
	 * que <b> NO hace el commit NI cierra la conexion </b>.
	 * 
	 * 
	 * @param sp
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	List<Map<String, Object>> execute(StoreProcedure sp, Connection con) throws SQLException;

	/**
	 * Ejecuta una query usando la conexion por parametro. Este metodo es manual por
	 * lo que <b> NO hace el commit NI cierra la conexion </b>.
	 * 
	 * @param query
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	List<Map<String, Object>> execute(SQLQuery query, Connection con) throws SQLException;

	/**
	 * Realiza el commit en la conexion parametro. Es usado junto con los metodos
	 * manuales para ejecutar queries y SPs.
	 * 
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	void commit(Connection con) throws SQLException;

	/**
	 * Realiza un rollback en la conexion parametro. Es usado junto con los metodos
	 * manuales para ejecutar queries y SPs. El rollback <b> NO FUNCIONA si
	 * previamente se ejecuto un COMMIT </b>.
	 * 
	 * @param con
	 * @throws SQLException
	 */
	void rollback(Connection con) throws SQLException;

	/**
	 * Cierra la conexion parametro. Es usado junto con los metodos manuales para
	 * ejecutar queries y SPs.
	 * 
	 * @param con
	 * @throws SQLException
	 */
	void close(Connection con) throws SQLException;
}
