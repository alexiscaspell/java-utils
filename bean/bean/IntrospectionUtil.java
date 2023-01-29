package io.blacktoast.utils.bean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class IntrospectionUtil {

	private static final Logger LOG = LoggerFactory.getLogger(IntrospectionUtil.class);

	/**
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> objectToMap(Object obj) throws Exception {

		Map<String, Object> result = new LinkedHashMap<String, Object>();

		BeanInfo info = Introspector.getBeanInfo(obj.getClass());
		for (PropertyDescriptor pd : info.getPropertyDescriptors()) {

			Method reader = pd.getReadMethod();
			if (reader != null && !pd.getName().equals("class")) {
				result.put(pd.getName(), reader.invoke(obj));
			}
		}

		return result;
	}

	/**
	 * @param listObject
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> objectListToMapList(List<?> listObject) throws Exception {

		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		for (Object obj : listObject) {
			listMap.add(objectToMap(obj));
		}

		return listMap;
	}

	/**
	 * @param obj
	 * @param clazz
	 * @return
	 */
	public static <T> T mapToObject(Map<String, Object> obj, Class<T> clazz) {

		try {
			T result = clazz.newInstance();

			BeanInfo info = null;
			info = Introspector.getBeanInfo(clazz);

			for (PropertyDescriptor pd : info.getPropertyDescriptors()) {

				Method setter = pd.getWriteMethod();
				if (setter != null)
					setter.invoke(result, obj.get(pd.getName()));
			}
			return result;

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			return null;
		}
	}

	/**
	 * @param obj
	 * @return
	 * @throws JsonSyntaxException
	 * @throws JsonProcessingException
	 */
	public static Map<String, Object> objectToMapWithJackson(Object obj)
			throws JsonSyntaxException, JsonProcessingException {

		String json = objectToJsonWithJackson(obj);
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (new Gson()).fromJson(json, Map.class);
		return map;
	}

	/**
	 * @param obj
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String objectToJsonWithJackson(Object obj) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		mapper.setVisibility(PropertyAccessor.SETTER, Visibility.ANY);
		mapper.setVisibility(PropertyAccessor.GETTER, Visibility.ANY);

		return mapper.writeValueAsString(obj);
	}

	/**
	 * @param obj
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String objectToXmlWithJackson(Object obj) throws JsonProcessingException {
		XmlMapper mapper = new XmlMapper();
		mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		mapper.setVisibility(PropertyAccessor.SETTER, Visibility.ANY);
		mapper.setVisibility(PropertyAccessor.GETTER, Visibility.ANY);

		return mapper.writeValueAsString(obj);
	}

	/**
	 * @param json
	 * @param clazz
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> T jsonToObjectWithJackson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		// mapper.setVisibility(PropertyAccessor.SETTER, Visibility.ANY);
		// mapper.setVisibility(PropertyAccessor.GETTER, Visibility.ANY);
		return json == null ? null : mapper.readValue(json, clazz);
	}

	/**
	 * @param xml
	 * @param clazz
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */

	public static <T> T xmlToObjectWithJackson(String xml, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		XmlMapper xmlMapper = new XmlMapper();

		return xmlMapper.readValue(xml, clazz);
	}

	/**
	 * @param object
	 * @param methodName
	 * @param clazz
	 * @return T
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T callMethod(Object object, String methodName, Object[] args, Class<T> clazz)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		// Class[] classes = new Class[args.length];
		// classes =
		// Arrays.asList(args).stream().map(a->a.getClass()).collect(Collectors.toList()).toArray(classes);

		Method method = Arrays.asList(object.getClass().getDeclaredMethods()).stream()
				.filter(m -> m.getName().equals(methodName)).collect(Collectors.toList()).get(0);
		method.setAccessible(true);

		return (T) method.invoke(object, args);

	}

	public static void callMethod(Object object, String methodName, Object[] args) throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// Class[] classes = new Class[args.length];
		// classes =
		// Arrays.asList(args).stream().map(a->a.getClass()).collect(Collectors.toList()).toArray(classes);

		Method method = Arrays.asList(object.getClass().getDeclaredMethods()).stream()
				.filter(m -> m.getName().equals(methodName)).collect(Collectors.toList()).get(0);
		method.setAccessible(true);

		method.invoke(object, args);

	}

	/**
	 * @param object
	 * @param methodName
	 * @param clazz
	 * @return T
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T callMethodWithSpring(Object object, String methodName, Object[] args, Class<T> clazz)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		// Class[] classes = new Class[args.length];
		// classes =
		// Arrays.asList(args).stream().map(a->a.getClass()).collect(Collectors.toList()).toArray(classes);

		Method method = Arrays.asList(object.getClass().getDeclaredMethods()).stream()
				.filter(m -> m.getName().equals(methodName)).collect(Collectors.toList()).get(0);
		method.setAccessible(true);

		return (T) ReflectionUtils.invokeMethod(method, object, args);
	}

	public static Object getValue(String propertyName, Object obj)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Field field = getField(propertyName, obj.getClass());
		field.setAccessible(true);
		return field.get(obj);
	}

	public static <T extends Annotation> T getAnnotation(String propertyName, Object obj, Class<T> annotationClass)
			throws NoSuchFieldException, SecurityException {
		return getField(propertyName, obj.getClass()).getAnnotation(annotationClass);
	}

	private static Field getField(String propertyName, Class<?> type) throws NoSuchFieldException {
		return getAllFields(type).stream().filter(f -> f.getName().contentEquals(propertyName)).findFirst()
				.orElseThrow(NoSuchFieldException::new);
	}

	private static List<Field> getAllFields(Class<?> type) {
		List<Field> fields = new ArrayList<Field>();
		for (Class<?> c = type; c != null; c = c.getSuperclass()) {
			fields.addAll(Arrays.asList(c.getDeclaredFields()));
		}
		return fields;
	}

	public static <T extends Annotation> List<String> getPropertiesWithAnnotation(Object obj, Class<T> annotationClass)
			throws Exception {

		List<Field> fields = getAllFields(obj.getClass());

		return fields.stream().filter(f -> f.isAnnotationPresent(annotationClass)).map(f -> f.getName())
				.collect(Collectors.toList());
	}

}
