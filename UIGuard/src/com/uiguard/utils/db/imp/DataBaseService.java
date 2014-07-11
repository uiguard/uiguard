package com.uiguard.utils.db.imp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.uiguard.exception.UiGuardException;
import com.uiguard.logger.IUiGuardLogger;
import com.uiguard.utils.db.IDataBaseService;
import com.uiguard.utils.setting.UiGuardSettings;

public class DataBaseService implements IDataBaseService {
	
	private Map<String,Connection> connectionPool = new ConcurrentHashMap<String,Connection>();
	
	private IUiGuardLogger ugLogger;
	
	private static final String DBDRIVERCLASSNAME="DriverClassName";
	
	private static final String DBURL="Url";
	
	private static final String DBUSER="User";
	
	private static final String DBPASSWORD="Password";
	
	private DataBaseService(IUiGuardLogger ugLogger) {
		this.ugLogger = ugLogger;
	}

	public static IDataBaseService getInstance(IUiGuardLogger ugLogger){
		return new DataBaseService(ugLogger);
	}
	
	@Override
	public Connection getDataBaseConnection(final String prefix) throws UiGuardException{
		if(connectionPool.containsKey(prefix)){
			return connectionPool.get(prefix);
		}
		return createConnection(prefix);
	}
	
	@Override
	public List<Map<String,Object>> query(final String prefix, final String sql) throws UiGuardException{
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Statement st;
		try {
			st = getDataBaseConnection(prefix).createStatement();
			ResultSet rs = st.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while(rs.next()){
				Map<String,Object> map = new ConcurrentHashMap<String,Object>();
				for(int i=1;i<columnCount+1;i++){
					map.put(rsmd.getColumnName(i), rs.getObject(i));
				}
				list.add(map);
			}		
			st.close();
			return list;
		} catch (SQLException e) {
			throw new UiGuardException("Query error:" + sql, e);
		} 
		
	}
	
	@Override
	public boolean execute(final String prefix, final String sql) throws UiGuardException{
		Statement st;
		try {
			st = getDataBaseConnection(prefix).createStatement();
			return st.execute(sql);
		} catch (SQLException e) {
			throw new UiGuardException("Execute error:" + sql, e);
		} 
	}
	
	private Connection createConnection(final String prefix) throws UiGuardException{
		try {
			Class.forName(getDriverClassName(prefix));
			Connection conn = DriverManager.getConnection(getUrl(prefix), getUser(prefix), getPassword(prefix));
			conn.setAutoCommit(true);
			return conn;
		} catch (ClassNotFoundException e) {
			throw new UiGuardException("Database driver class not find, make sure you have add expected jar.", e);
		} catch (SQLException e) {
			throw new UiGuardException("Create Connection Error", e);
		}
		
	}
	
	private String getDriverClassName(final String prefix){
		return getProperty(prefix,DBDRIVERCLASSNAME);
	}
	
	private String getUrl(final String prefix){
		return getProperty(prefix,DBURL);
	}
	
	private String getUser(final String prefix){
		return getProperty(prefix,DBUSER);
	}
	
	private String getPassword(final String prefix){
		return getProperty(prefix,DBPASSWORD);
	}
	
	private String getProperty(final String prefix, final String postfix){
		return UiGuardSettings.getProperty(prefix.concat(".").concat(postfix));
	}
	
	@Override
	protected void finalize(){
		closeConnections();
	}
	
	@Override
	public void closeConnections(){
		for(String dbName : connectionPool.keySet()){
			try {
				Connection conn = connectionPool.get(dbName);
				conn.close();
				connectionPool.remove(dbName);
			} catch (SQLException e) {
				ugLogger.error("Database connection close error", e);
			}
		}
	}

}
