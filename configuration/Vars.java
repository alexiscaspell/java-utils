package io.blacktoast.configuration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Vars {

	public static final String PROJECT_NAME = "pullandpush";
	
	public static final String REDIS_PORT = PROJECT_NAME+".redis.port";
	public static final String REDIS_URL = PROJECT_NAME+".redis.url";
	public static final String REDIS_MAX_POOL = PROJECT_NAME+".redis.maxPool";
	public static final String REDIS_POP_TIME_OUT = null;
	
	public static final String MIGRATION_PROCESS_NAME = PROJECT_NAME+".migrationService.processName";
	public static final String MIGRATION_PROCESS_POOL_SIZE = PROJECT_NAME+".migrationService.threadPoolSize";
	
	public static final String HADOOP_LOCAL_DIR = PROJECT_NAME+".hadoop.home.dir";
	public static final String HADOOP_FS_NAME = PROJECT_NAME+".hadoop.fs.default.name";
	public static final String HADOOP_CSV_FOLDER = PROJECT_NAME+".hadoop.csv.folderpath";
	
	public static final String DRUID_HOST_URL = PROJECT_NAME+".druid.host.url";
	
	public static final String MONGO_DATABASE = PROJECT_NAME+".mongo.database";
	public static final String MONGO_PORT = PROJECT_NAME+".mongo.port";
	public static final String MONGO_HOST = PROJECT_NAME+".mongo.host";
	public static final String MONGO_PASSWORD = PROJECT_NAME+".mongo.pass";
	public static final String MONGO_USER = PROJECT_NAME+".mongo.user";


	public static List<String> getConfigurationKeys() {
		
		List<String> keys = new ArrayList<String>();
		
		Field[] fields = Vars.class.getFields();
		for (Field field : fields) {
				try {
					keys.add(field.get(String.class).toString());
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		
		return keys;
	}
}
