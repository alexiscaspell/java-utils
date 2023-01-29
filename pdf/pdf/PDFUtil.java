package io.blacktoast.utils.pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfFileSpecification;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Nueva version pensada en trabajar creando archivos en disco y no usar
 * puramente la memoria RAM, para usarla se tiene que crear un nuevo objeto
 * usando el constructor
 * 
 * @author blacktoast
 * @version 3.0.0
 */
public class PDFUtil {

	private static final String RUTA_TMP_DEFAULT = "/tmp";

	private String rutaTmp;

	public PDFUtil(String rutaTmp) {
		this.rutaTmp = rutaTmp == null || rutaTmp.isEmpty() ? RUTA_TMP_DEFAULT : rutaTmp;
	}

	public PDFUtil() {
		this.rutaTmp = RUTA_TMP_DEFAULT;
	}

	/**
	 * Crea un archivo PDF <b>en blanco</b> y devuelve su contenido
	 * 
	 * @param path ruta completa incluyendo el nombre del archivo y la extension
	 * @return
	 * 
	 * @throws DocumentException
	 * @throws IOException
	 */
	public byte[] nuevoPDFVacio() throws DocumentException, IOException {

		String rutaArchivoTmp = crearArchivoTmp();
		FileOutputStream outputStream = new FileOutputStream(rutaArchivoTmp);

		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, outputStream);

		document.open();
		document.add(new Paragraph());
		document.close();

		writer.close();

		outputStream.close();
		removerArchivoTmp(rutaArchivoTmp);

		return Files.readAllBytes(Paths.get(rutaArchivoTmp));
	}

	/**
	 * Adjunta archivos a un PDF devolviendo el contenido del nuevo archivo PDF
	 * generado el cual contiene el adjunto
	 * 
	 * @param contenidoPDF contenido del archivo PDF
	 * @param nombre       nombre del adjunto
	 * @param descripcion  descripcion del adjunto
	 * @param adjunto      contenido del archivo adjunto
	 * @return contenido del archivo con el adjunto
	 * @throws IOException
	 * @throws DocumentException
	 */
	public byte[] adjuntarAPDF(byte[] contenidoPDF, String nombre, String descripcion, byte[] adjunto)
			throws IOException, DocumentException {

		String rutaArchivoTmp = crearArchivoTmp();
		FileOutputStream outputStream = new FileOutputStream(rutaArchivoTmp);

		PdfReader reader = new PdfReader(contenidoPDF);
		PdfStamper stamper = new PdfStamper(reader, outputStream);

		PdfFileSpecification fs = PdfFileSpecification.fileEmbedded(stamper.getWriter(), null, nombre, adjunto);
		stamper.addFileAttachment(descripcion, fs);
		stamper.close();
		reader.close();

		outputStream.close();
		byte[] bytes = Files.readAllBytes(Paths.get(rutaArchivoTmp));
		removerArchivoTmp(rutaArchivoTmp);

		return bytes;
	}

	/**
	 * Remueve un campo de propiedad de un PDF
	 * 
	 * @param contenidoPDF
	 * @param nombre
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public byte[] removerCampo(byte[] contenidoPDF, String nombre) throws IOException, DocumentException {

		String rutaArchivoTmp = crearArchivoTmp();
		FileOutputStream outputStream = new FileOutputStream(rutaArchivoTmp);

		PdfReader reader = new PdfReader(contenidoPDF);
		PdfStamper stamper = new PdfStamper(reader, outputStream);

		AcroFields acroFields = reader.getAcroFields();
		acroFields.removeField(nombre);

		stamper.close();
		reader.close();

		outputStream.close();
		byte[] bytes = Files.readAllBytes(Paths.get(rutaArchivoTmp));
		removerArchivoTmp(rutaArchivoTmp);

		return bytes;
	}

	/**
	 * Remueve las firmas existentes
	 * 
	 * @param contenidoPDF
	 * @return contenido del nuevo PDF creado sin firmas
	 * @throws IOException
	 * @throws DocumentException
	 */
	public byte[] removerFirmas(byte[] contenidoPDF) throws IOException, DocumentException {

		String rutaArchivoTmp = crearArchivoTmp();
		FileOutputStream outputStream = new FileOutputStream(rutaArchivoTmp);

		PdfReader reader = new PdfReader(contenidoPDF);
		PdfStamper stamper = new PdfStamper(reader, outputStream);

		AcroFields fields = reader.getAcroFields();
		for (String signatureName : fields.getSignatureNames()) {
			fields.clearSignatureField(signatureName);
			fields.removeField(signatureName);
		}

		stamper.close();
		reader.close();

		outputStream.close();
		byte[] bytes = Files.readAllBytes(Paths.get(rutaArchivoTmp));
		removerArchivoTmp(rutaArchivoTmp);

		return bytes;
	}

	/**
	 * Crea un archivo bacio en la carpeta temporal usando de nombre un UUID
	 * 
	 * @return ruta del archivo creado
	 * @throws IOException
	 */
	private String crearArchivoTmp() throws IOException {

		Path rutaArchivo = Paths.get(rutaTmp, UUID.randomUUID().toString());
		rutaArchivo.toFile().createNewFile();

		return rutaArchivo.toString();
	}

	/**
	 * Borra el archivo en la ruta especificada
	 * 
	 * @param ruta ruta del archivo a borrar
	 */
	private void removerArchivoTmp(String ruta) {
		Paths.get(ruta).toFile().delete();
	}
}
