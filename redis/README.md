# REDIS UTILS
Utils para manejo de redis
En la carpeta "redis" se encuentra el codigo de la util


### Dependencias Maven
No requiere dependencias salvo las de Spring

## Prerrequisitos

Agregar en la clase **ConfigurationUtils** el siguiente Bean para la configuracion de la util

```
@Bean
public RedisUserConfig redisUserConfig() {

	RedisUserConfig configEntity = new RedisUserConfig();
	configEntity.setUrl(ConfigurationVars.get(Vars.REDIS_URL));
	configEntity.setPort(ConfigurationVars.get(Vars.REDIS_PORT, Integer.class));
	configEntity.setMaxIdlePool(ConfigurationVars.get(Vars.REDIS_MAX_IDLE_POOL, Integer.class));
	configEntity.setMaxTotalPool(ConfigurationVars.get(Vars.REDIS_MAX_TOTAL_POOL, Integer.class));
	configEntity.setPopTimeOut(ConfigurationVars.get(Vars.REDIS_POP_TIME_OUT, Integer.class));

	return configEntity;
}
```


## Redis Service
Es el servicio que abstrae el acceso a redis.

## Redis Config
Contiene la configuracion de redis, por default la obtiene de las variables mencionadas en los prrerequisitos


### Ejemplo de uso
Suponiendo que se tiene la siqguiente linea:

	@Autowired
	private RedisService redisService;

Entonces se puede realizar lo siguiente:
```

//SETEA LA KEY 'unaKey' CON EL VALOR 'unValor'
redisService.setByKey("unaKey","unValor",false,null);

//OBTIENE EL VALOR DE LA KEY 'unaKey'
String unValor = redisService.getByKey("unaKey");

//TAMBIEN SE PUEDEN USAR LISTAS EN REDIS, ACA SE INSERTA UN ELEMENTO A LA LISTA 'unaCola'
redisService.pushToQueue("unaCola","unValor")

//SE RETORNA UN ELEMENTO DE LA LISTA 'unaCola' (esta operacion seria un equivalente a un 'pop', osea quita el elemento de la lista en redis y lo devuelve)
String unValor = redisService.retrieveFromQueue("unaCola")

```

