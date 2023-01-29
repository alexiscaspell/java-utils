package io.blacktoast.repository.druid;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.blacktoast.utils.bean.MapUtil;

public class DruidRequestBuilder {

	private String hadoopPath;
	private Date startDate;
	private Date finishDate;
	private String timestampNameColumn;
	private String druidDatasource;

	public Map<String, Object> buildBodyFromList(List<Map<String, Object>> list) {
		Map<String, Object> firstElement = list.get(0);

		return getBodyToDruidFromMap(firstElement);
	}

	private Map<String, Object> getBodyToDruidFromMap(Map<String, Object> firstElement) {

		Map<String, Object> map = new HashMap<String, Object>();

		List<String> intervals = new ArrayList<String>();
		intervals.add(getFirstCsvTimeStamp() + "/" + getLastCsvTimeStamp());

		List<String> columns = getColumns(firstElement);
		List<Object> dimensions = getDimensions(firstElement);
		String csvHadoopRoute = getCsvHadoopRoute();
		List<Map<String,Object>> metricsSpec = new ArrayList<Map<String,Object>>();
		Map<String,Object> mapSpecDummy = new HashMap<String,Object>();
		mapSpecDummy.put("name", "CANT");
		mapSpecDummy.put("type", "count");
		mapSpecDummy.put("fieldName", "CANT");
		metricsSpec.add(mapSpecDummy);
		
		MapUtil.setProperty("type", map, "index_hadoop");
		MapUtil.setProperty("spec.ioConfig.type", map, "hadoop");
		MapUtil.setProperty("spec.ioConfig.inputSpec.type", map, "static");
		// ACA TENES HAY QUE PONER LA RUTA DEL CSV QUE ESTA EN HADOOP
		MapUtil.setProperty("spec.ioConfig.inputSpec.paths", map, csvHadoopRoute);
		MapUtil.setProperty("spec.dataSchema.dataSource", map, getDruidDatasource());
		MapUtil.setProperty("spec.dataSchema.granularitySpec.type", map, "uniform");
		MapUtil.setProperty("spec.dataSchema.granularitySpec.segmentGranularity", map, "all");
		MapUtil.setProperty("spec.dataSchema.granularitySpec.queryGranularity", map, "none");
		// ACA VA EL TIMESTAMP DEL PRIMER Y ULTIMO CSV
		MapUtil.setProperty("spec.dataSchema.granularitySpec.intervals", map, intervals);
		MapUtil.setProperty("spec.dataSchema.parser.type", map, "hadoopyString");
		MapUtil.setProperty("spec.dataSchema.parser.parseSpec.format", map, "csv");
		// ESTE ES EL TIMESTAMP POR EL QUE DESPUES PRDENA DRUID
		MapUtil.setProperty("spec.dataSchema.parser.parseSpec.timestampSpec.column", map, getTimestampNameColumn());
		MapUtil.setProperty("spec.dataSchema.parser.parseSpec.columns", map, columns);
		MapUtil.setProperty("spec.dataSchema.parser.parseSpec.dimensionsSpec.dimensions", map, dimensions);
		MapUtil.setProperty("spec.dataSchema.metricsSpec", map, metricsSpec);
		MapUtil.setProperty("spec.tuningConfig.type", map, "hadoop");
		MapUtil.setProperty("spec.tuningConfig.partitionsSpec.type", map, "hashed");
		MapUtil.setProperty("spec.tuningConfig.partitionsSpec.targetPartitionSize", map, 500000);
		MapUtil.setProperty("spec.tuningConfig.jobProperties", map, new HashMap<String,Object>());
		//ESTO ES PARA IGNORAR LAS FILAS INVALIDAS
		MapUtil.setProperty("spec.tuningConfig.ignoreInvalidRows", map, false);

		return map;
	}

	private String getDruidDatasource() {
		return this.druidDatasource;
	}

	private String getLastCsvTimeStamp() {
		return formatToDruidDate(finishDate);
	}

	private String formatToDruidDate(Date date) {
		String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(date);
//		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(date) + "Z";
	}

	private String getFirstCsvTimeStamp() {
		
		return formatToDruidDate(startDate);
	}

	private List<Object> getDimensions(Map<String, Object> map) {
		
		//QUITO EL TIMESTAMP PARA QUE NO LO TENGA EN CUENTA EN LAS DIMENSIONES
		map.remove(getTimestampNameColumn());
		
		return getColumns(map).stream().map(c -> getDimensionFor(c, map.get(c)))
				.collect(Collectors.toList());
	}

	private Object getDimensionFor(String columnName, Object columnValue) {
		Map<String, Object> dimensionMap = new HashMap<String, Object>();

		dimensionMap.put("name", columnName);

		// ES UN NUMERO
		if (columnValue instanceof Number) {
			// ES LONG O FLOAT
			dimensionMap.put("type", isNotDecimalNumber(columnValue) ? "long" : "float");
			return dimensionMap;
		}

		return columnName;
	}

	// INTO THE DARKNESS
	@SuppressWarnings("unused")
	private boolean isNotDecimalNumber(Object columnValue) {
		try {
			Long longValue = Long.parseLong(String.valueOf(columnValue));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private List<String> getColumns(Map<String, Object> firstElement) {
		return new ArrayList<String>(firstElement.keySet());
	}

	public void setCsvHadoopFileRoute(String hadoopPath) {
		this.hadoopPath = hadoopPath;

	}

	public String getCsvHadoopRoute() {
		return this.hadoopPath;

	}

	public void setDruidDatasource(String druidDatasource) {
		this.druidDatasource = druidDatasource;
	}

	public void setCsvStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setCsvFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public void setTimestampNameColumn(String timestampNameColumn) {
		this.timestampNameColumn = timestampNameColumn;
	}
	public String getTimestampNameColumn() {
		return this.timestampNameColumn;
	}

}
