# UTILS DE PDF
Utils que permiten la manipulacion de archivos pdfs trabajando principalmente con sus bytes para hacerlo mas generico

## Dependencias 
En el archivo `pom` se encuentran las dependencias para agregar al proyecto

## Ejemplo de uso
Se debe utilizar inyectando las dependencias con Spring

```
    byte[] pdfPrincipal = pdfPrincipalEnBytes;
    String nombre = "nomdeAdjunto";
    String descripcion = "soy una descripcion muy copada";
    byte[] adjunto = adjuntoEnBytes;


    PDFUtil pdfUtil = new PDFUtil(); //se le puede pasar una ruta temporal para guarar todo ahi
    byte[] pdfConAdjuntos = pdfUtil.adjuntarAPDF(pdfPrincipal, nombre, descripcion, adjunto);
```
