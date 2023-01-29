package io.blacktoast.utils.threads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import io.blacktoast.utils.threads.model.ThreadNotFoundException;
import lombok.Data;

/*
 * @version 1.0.0
 * @author Erhardt Tomás
 */
@Data
public class TimerManager {
	private static TimerManager timerManager;
	private Map<String, List<Timer>> timers;

	public static TimerManager getTimerManager() {
		if (timerManager == null) {
			timerManager = new TimerManager();
		}
		return timerManager;
	}

	private TimerManager() {
		timers = new HashMap<String, List<Timer>>();
	}

	/**
	 * 
	 * Crea una cantidad de hilos que se ejecutan cada cierto tiempo, esos hilos ejecutan una instancia de la clase pasada como parametro.
	 * @param clazz	Clase que hereda de TimerTask, la cual debe tener definida, en el metodo run, la acción a ejecutar por los hilos.
	 * @param parametros Parametros que recibe la clase previamente pasada. Los parametros deben estar en el orden que los recibe el constructor.
	 * @param cantidadHilos Cantidad de hilos de se desea crear
	 * @param delayInicial Tiempo que se espera antes de crear los hilos.
	 * @param intervaloTiempo Cada cuanto tiempo se vuelven a ejecutar los hilos
	 * @throws Exception Exception lanzada al no encontrar los hilos buscados para eliminarlos.
	 * 
	 */
	public static void run(Class<? extends TimerTask> clazz, List<Object> parametros, Integer cantidadHilos,
			String nameThread, Integer delayInicial, Integer intervaloTiempo) throws Exception {

		Class<?>[] clasesConstructor = new Class<?>[parametros.size()];
		for (int i = 0; i < parametros.size(); i++) {
			clasesConstructor[i] = parametros.get(i).getClass();
		}

		List<Timer> newTimers = new ArrayList<Timer>();
		for (int i = 0; i < cantidadHilos; i++) {
			Timer timer = new Timer(nameThread);
			timer.scheduleAtFixedRate(getInstance(clazz, clasesConstructor, parametros), delayInicial, intervaloTiempo);
			newTimers.add(timer);
		}
		addTimers(nameThread, newTimers);
	}

	/**
	 * @param nameThread Clave del Map que guarda los hilos creados.
	 * @param newTimers	Lista de timers creados con el mismo nombre.
	 */
	private static void addTimers(String nameThread, List<Timer> newTimers) {
		Map<String, List<Timer>> timers = TimerManager.getTimerManager().getTimers();
		if (!timers.containsKey(nameThread))
			timers.put(nameThread, newTimers);

		else {
			timers.get(nameThread).addAll(newTimers);
		}

	}

	/**
	 * @param nameThread Nombre de timers que se desea interrumpir.
	 * @throws ThreadNotFoundException Exception lanzada al no encontrar hilos con ese nombre.
	 */
	public static void interruptThread(String nameThread) throws ThreadNotFoundException {
		Map<String, List<Timer>> timers = TimerManager.getTimerManager().getTimers();
		if (!timers.containsKey(nameThread))
			throw new ThreadNotFoundException();

		for (Timer t : timers.get(nameThread)) {
			t.cancel();
		}
	}

	/**
	 * @param clazz Clase que hereda de TimerTask, la cual debe tener definida, en el metodo run, la acción a ejecutar por los hilos.
	 * @param clasesConstructor Tipo de datos de los parametros del constructor.
	 * @param parametros Parametros que recibe la clase previamente pasada. Los parametros deben estar en el orden que los recibe el constructor.
	 * @return Retorna una instancia de la clase
	 * @throws Exception
	 */
	private static TimerTask getInstance(Class<? extends TimerTask> clazz, Class<?>[] clasesConstructor,
			List<Object> parametros) throws Exception {

		if (parametros.isEmpty() && clasesConstructor.length == 0)
			return clazz.getConstructor().newInstance();
		return clazz.getConstructor(clasesConstructor).newInstance(parametros.toArray());
	}

}
