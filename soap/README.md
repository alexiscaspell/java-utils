# SOAP UTILS
Utils relacionadas con servicios SOAP
En la carpeta "io" se encuentra el codigo de la util

## ENDPOINT MAKER
Ejecuta los metodos necesarios para invocar un servivio SOAP, estos servicios deben ser creados 
mediante CXF de Apache

## HERRAMIENTA CXF
En la carpeta "wsdlToJavaClass" hay una herramienta que ejecuta cfx para obtener las clases java de un wsdl,
en "in" se guardan el wsdl que se quiere pasar y en "out" se obtiene el resultado, es necesario modificar el batch 
para agregarle la configuracion del wsdl que se quiere convertir y poder usarlo

### Dependencias Maven
No requiere dependencias salvo las de Spring

### Ejemplo de uso
Suponiendo que:

    ADPDescripcionesWS: es el servicio que extiende de Service
    ADPDescripcionesWSSoap: es la interface con los metodos que se van a llamar
    DocumentoDTO: objeto resultado de la invocacion del servicio SOAP

```
ADPDescripcionesWSSoap descripcionesSoap = SoapEndpointMaker.makeWithGenericHeaders(
    ADPDescripcionesWS.class, 
    ADPDescripcionesWSSoap.class
);
DocumentoDTO tipoDocumento = descripcionesSoap.obtenerDocumentoPorCodigo(1234567);

```

## HERRAMIENTA UTIL

* Existe un script que convierte los .wsdl a clases .java, se encuentra en [scripts/wsdlToJavaClass](https://github.com/alexiscaspell/java-utils/tree/main/scripts/wsdlToJavaClass)