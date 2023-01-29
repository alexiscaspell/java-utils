# DRUID SERVICE
Servicio que facilita la migracion de datos de java a una base de datos Druid 
En la carpeta "druid" se encuentra el codigo de la util

### Dependencias Maven
No requiere dependencias salvo las de Spring

### Ejemplo de uso
Suponiendo que se tiene lo siguiente:

```
DruidService druidService;

List<Map<String, Object>> listaConDatos = new ArrayList<?>();

Date startDate = new Date();
Date finishDate = new Date();

Map<String,Object> primeraFila = new HashMap<String,Object>();
primeraFila.put("id",1);
primeraFila.put("nombre","unNombre");
primeraFila.put("dni","soyUnDni");
primeraFila.put("columnaConFecha",startDate);

listaconDatos.add(primeraFila);
```

Entonces se puede ejecutar la migracion de la siguiente manera:
```
//RETORNA UN MAPA CON E RESULTADO DE LA EJECUCION
druidService.migrate(listaConDatos,"pathDelArchivoQueSeVaACrear",startDate,finishDate,"columnaConFecha","nombreDatasourceDruid");

```