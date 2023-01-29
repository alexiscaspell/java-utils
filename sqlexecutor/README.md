# SQL UTILS
Utils para manejo de queries y ejecucion de sps
En la carpeta "io" se encuentra el codigo de la util

## SQL Executor Service
Ejecuta uno o varios sps con una conexion jdbc pasada por parametro o la provista en la config de springboot

### Dependencias Maven
No requiere dependencias salvo las de Spring

### Ejemplo de uso
Suponiendo que se ejecuto el siguiente codigo:

	SQLExecutorService sqlService;

    DataSource dataSourceJNDI = DSBuilder.makeJNDI("unSTringJndi","datasourceJNDI");

	Map<String,DataSource> datasources = new HashMap<String,Datasource>();
	datasources.put("datasourceJNDI",dataSourceJNDI);

	DSBuilder.setDatasources(datasources);

	StoreProcedure sp = new StoreProcedure();
	sp.setDataSourceName("datasourceJNDI");
	sp.setName("unNombreDeSP");
	sp.setParametersMap(new HasMap<String,Object>());
	sp.setResultColumns({"id","nombre","dni"});

Entonces se puede ejecutar el sp de la siguiente manera:
```
SQLResult result = sqlService.execute(sp);

//Mapa con los resultados del sp
result.getResultMap();

```