package io.blacktoast.repository.druid;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DruidService {

	Map<String, Object> migrate(List<Map<String, Object>> list, String hadoopFilePath, Date startDate, Date finishDate,
			String timestampNameColumn, String druidDatasource);
}
