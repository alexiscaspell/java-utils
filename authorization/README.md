# UTILS DE SEGURIDAD
Utils que permiten el acceso o hacer validaciones para brindar permisos a aplicaciones o temas relacionados

## AIUTHORIZATION SERVICE
Obtiene el Token de un usuario mediante su ID en Redis

### Dependencias 
Necesario tener las Utils de Redis en su version V1.1.0 como minimo

### Ejemplo de uso
Se debe utilizar injectando las dependencias con Spring

```
    @Autowired
    private AuthorizationService authorizationService;
    ...
    ...
    authorizationService.getUserToken("unaIdDeRedis");
```
