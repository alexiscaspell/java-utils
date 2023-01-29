package io.blacktoast.repository.druid.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import io.blacktoast.utils.bean.DateUtil;
import io.blacktoast.configuration.ConfigurationVars;
import io.blacktoast.configuration.Vars;
import io.blacktoast.repository.druid.DruidRequestBuilder;
import io.blacktoast.repository.druid.DruidService;
import io.blacktoast.utils.rest.RestCaller;

@Service
public class DruidServiceImpl implements DruidService {

	private static final Logger LOG = LoggerFactory.getLogger(DruidServiceImpl.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<String, Object> migrate(List<Map<String, Object>> list, String hadoopFilePath, Date startDate, Date finishDate,
			String timestampNameColumn,String druidDatasource) {
		
		Map<String,Object> body = getBodyToDruidFromList(list,hadoopFilePath, startDate,
				 finishDate,timestampNameColumn,druidDatasource);
		
		LOG.info("POST A DRUID DE: " +(new Gson()).toJson(body));
		
		String druidHostUrl = ConfigurationVars.get(Vars.DRUID_HOST_URL);
		ResponseEntity<Map> response = RestCaller.post(druidHostUrl, body, Map.class);
		
		return response.getBody();
		
	}

	private Map<String, Object> getBodyToDruidFromList(List<Map<String, Object>> list,String hadoopFilePath,Date startDate,
			Date finishDate,String timestampNameColumn, String druidDatasource) {
		
		DruidRequestBuilder druidRequestBuilder = new DruidRequestBuilder();
		startDate=startDate==null?(Date) list.get(0).get(timestampNameColumn):startDate;
		finishDate=finishDate==null?DateUtil.getNow():finishDate;
		
		druidRequestBuilder.setCsvHadoopFileRoute(hadoopFilePath);
		druidRequestBuilder.setCsvStartDate(startDate);
		druidRequestBuilder.setCsvFinishDate(finishDate);
		druidRequestBuilder.setTimestampNameColumn(timestampNameColumn);
		druidRequestBuilder.setDruidDatasource(druidDatasource);
		
		return druidRequestBuilder.buildBodyFromList(list);
	}

}
