
# Rest Template

Template para generar hilos con temporizador

![alt text](img/java.png)


## Prerrequisitos

* JVM 1.8 o superior


## Despliegue

### Localmente

Crear una clase que herede de TimerTask, definir en el metodo run la acción que se quiera realizar en multiples hilos.

### Llamado a util (Generate)

Para iniciar los hilos, se debe llamar a TimerManager.run(claseDefinida, Lista de parametros de la clase, cantidad de hilos requeridos, nombre de los hilos (clave para borrarlos), delay inicial, intervalo de tiempo para volver a ejecutar)

### Prueba

Realizar un **POST** a http://localhost:8080/api/v1/ejemplos/test/generate/thread/{nombreHilos} para generar 5 hilos con una tarea de prueba con el nombre pasado como parametro

## Llamado a util (Remove)

Para remover los hilos, se debe llamar a TimerManager.interruptThread(nombre hilo/hilos).

## Prueba

Realizar un **POST** a http://localhost:8080/api/v1/ejemplos/test/remove/thread/{nombreHilos} para cancelar los hilos con ese nombre. En caso de no encontrar hilos con ese nombre, devuelve ThreadNotFoundException. Aclaracion: Al remover los hilos, no los "mata", el hilo actualmente ejecutado debe terminar su tarea, luego de eso no se volverá a generar.


## Ejemplo de uso
	Generar hilos:

		TimerManager.run(TareaPrueba.class, Arrays.asList("Primer parametro", 1), 5, name, 0, 2000);
		TareaPueba extends TimerTask, parametros de TareaPrueba (puede ser vacio)
	
	Eliminar hilos:
	
		TimerManager.interruptThread(name);
		Elimina hilos con ese nombre que hayas creado con el metodo run, ya que busca en un map interno (No tiene acceso a todos los hilos del sistema)