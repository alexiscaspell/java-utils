# REST UTILS
Utils relacionadas con servicios REST, herramientas comun para los proyectos que son utiles cuando se usan estos servicio

## REST CALLER
Ejecutador de REST, consta de una clase estatica con la que se puede hacer post, get, etc.

### Dependencias Maven
```
    <dependency>
        <groupId>org.codehaus.jackson</groupId>
        <artifactId>jackson-mapper-asl</artifactId>
        <version>1.5.0</version>
    </dependency>

```

### Ejemplo de uso
Se utiliza spring para realizar los llamados y jackson para el mapeo de objetos, por lo que sus anotations funcionan (ejempo: @JsonIgnore)

```
BodyEntrada bodyEntrada = new BodyEntrada();

ResponseEntity<Resultado> rest = RestCaller.post("http://soy.una.url:8000/demostracion", bodyEntrada, Resultado.class);

Resultado resultado = rest.getBody();
```

### Configuracion
Existe un objeto para configurar el RestCaller, este es *RestCallerConfig*.
Para onfigurarlo es necesario hacer un *initialize* pasandole como parametro
un *RestCallerConfig* como parametro una unica vez
En caso de no configurar se usan parametros predeterminados en el codigo

Ejemplo:
```
RestCallerConfig config = new RestCallerConfig();
config.setConnectTimeOut(5000);
config.setReadTimeOut(1000);

RestCaller.initialize(config);
```

