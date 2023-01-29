package io.blacktoast.utils.soap;

import java.lang.reflect.Method;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.headers.Header;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;

/**
 * <p>
 * Para poder usarlo es necesario que las clases del web service a utlizar sean
 * creados con CXF de Apache
 * </p>
 * 
 * @author blacktoast
 * @version 1.0.0
 */
public class SoapEndpointMaker {

	private static final Logger LOG = Logger.getLogger(SoapEndpointMaker.class);

	/**
	 * @param serviceClass
	 * @param interfaceClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Service, K> K make(Class<T> serviceClass, Class<K> interfaceClass) {
		try {
			T serviceInstance = serviceClass.getConstructor().newInstance();

			ClientProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(interfaceClass);

			Method interfaceConstructMethod = serviceInstance.getClass().getMethod(getSOAPServiceName(serviceClass));
			LOG.info("SOAP maker -> created: " + serviceClass.getSimpleName());
			return (K) interfaceConstructMethod.invoke(serviceInstance);

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			return null;
		}
	}

	/**
	 * @param serviceClass
	 * @return
	 */
	private static <T extends Service> String getSOAPServiceName(Class<T> serviceClass) {
		return "get" + serviceClass.getSimpleName() + "Soap";
	}

	/**
	 * @param interfaceService
	 * @param headers
	 */
	public static void addHeaders(Object interfaceService, List<Header> headers) {

		Client proxy = ClientProxy.getClient(interfaceService);
		proxy.getRequestContext().put(Header.HEADER_LIST, headers);
	}

	/**
	 * Crea un header mediante un {@link QName} y un {@link JAXBDataBinding}
	 * 
	 * @param uri
	 * @param key
	 * @param value
	 * @param valueType
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Header makeHeader(String uri, String key, Object value, Class valueType) {
		try {
			QName qName = new QName(uri, key);
			JAXBDataBinding dataBinding = new JAXBDataBinding(valueType);

			return new Header(qName, value, dataBinding);

		} catch (JAXBException e) {
			LOG.error(e.getLocalizedMessage(), e);
			return null;
		}
	}

}
