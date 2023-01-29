package io.blacktoast.utils.file;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.io.SequenceInputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class FileUtils {

	private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);

	public static String getB64FromMultipart(MultipartFile file) throws IOException {

		InputStream inputStream = file.getInputStream();

		return getB64FromInputStream(inputStream);
	}

	public static String getB64FromInputStream(InputStream inputStream) throws IOException {
		byte[] bytes = IOUtils.toByteArray(inputStream);

		String b64String = Base64.getEncoder().encodeToString(bytes);

		return b64String;
	}

	public static InputStream getInputStreamFromB64(String fileB64) {

		return new ByteArrayInputStream(Base64.getDecoder().decode(fileB64));
	}

	public static void extractZip(String directoryPath) throws Exception {
		String zipFilePath = listAllFiles(directoryPath, ".zip").get(0);
		extractZip(directoryPath,zipFilePath);
	}
	
	public static void extractZip(String extractPath, String zipFilePath) throws Exception {
		try {
			// Initiate ZipFile object with the path/name of the zip file.
			ZipFile zipFile = new ZipFile(zipFilePath);
			
			// Extracts all files to the path specified
			zipFile.extractAll(extractPath);
			
		} catch (ZipException e) {
			e.printStackTrace();
		}
		
	}

	public static List<String> listAllFiles(String path, String extension) {

		List<String> result = new ArrayList<String>();

		try (Stream<Path> walk = Files.walk(Paths.get(path))) {

			result = walk.filter(Files::isRegularFile).map(x -> x.toString())
					.filter(f -> extension == null || f.endsWith(extension)).collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void oldExtractZip(String extractPath, String zipFilePath) throws Exception {

		String[] args = listAllFiles(zipFilePath, null).stream().sorted().collect(Collectors.toList())
				.toArray(new String[0]);

		byte[] SPANNING_SIGNATURE_1 = { 0x50, 0x4b, 0x07, 0x08 };
		byte[] SPANNING_SIGNATURE_2 = { 80, 75, 3, 4 };
		List<InputStream> asList = new ArrayList<>();
		byte[] buf4 = new byte[4];
		PushbackInputStream pis = new PushbackInputStream(new FileInputStream(args[0]), buf4.length);
		asList.add(pis);
		if (pis.read(buf4) != buf4.length) {
			throw new IOException(args[0] + " is too small for a zip file/segment");
		}
		if (Arrays.equals(buf4, SPANNING_SIGNATURE_1) || Arrays.equals(buf4, SPANNING_SIGNATURE_2)) {
			pis.unread(buf4, 0, buf4.length);
		}
		for (int i = 1; i < args.length; i++) {
			asList.add(new FileInputStream(args[i]));
		}

		try (ZipInputStream is = new ZipInputStream(new SequenceInputStream(Collections.enumeration(asList)))) {
			for (ZipEntry entry = null; (entry = is.getNextEntry()) != null;) {
				if (entry.isDirectory()) {
					new File(extractPath + "/" + entry.getName()).mkdirs();
				} else {
					try (OutputStream os = new BufferedOutputStream(
							new FileOutputStream(extractPath + "/" + entry.getName()))) {
						byte[] buffer = new byte[1024];
						int count = -1;
						while ((count = is.read(buffer)) != -1) {
							os.write(buffer, 0, count);
						}
					}
				}
			}
		}
	}

	public static File[] listAllFolders(String path) {
		return new File(path).listFiles(File::isDirectory);
	}

	public static InputStream stringToInputStream(String unString) {
		return new ByteArrayInputStream(unString.getBytes(Charset.forName("UTF-8")));
	}

	public static void createFolder(String path) {
		new File(path).mkdirs();
	}

	public static void deleteFolder(String pathCarpetaPartes) throws IOException {
		Path path = Paths.get(pathCarpetaPartes);
		Stream<Path> stream = null;
		try {
			stream = Files.walk(path);
			stream.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);

		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	public static void cleanFolder(String pathCarpetaPartes) throws IOException {
		Path path = Paths.get(pathCarpetaPartes);
		Stream<Path> stream = null;
		try {
			stream = Files.walk(path);
			stream.sorted(Comparator.reverseOrder())
					.filter(f -> !f.toAbsolutePath().toString().equals(pathCarpetaPartes))
					.map(Path::toFile)
					.forEach(File::delete);

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);

		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	public static void moveFiles(String pathOrigen, String pathDestino, String extension) throws IOException {

		List<String> archivos = listAllFiles(pathOrigen, extension);

		for (String archivo : archivos) {

			File file = new File(archivo);

			Files.move(Paths.get(pathOrigen, file.getName()), Paths.get(pathDestino, file.getName()),
					StandardCopyOption.REPLACE_EXISTING);
		}

	}

	public static void deleteFile(String path) throws IOException {
		Files.deleteIfExists(Paths.get(path));
	}

	public static void armarZip(List<String> archivos, String pathDestino, String nombreArchivo) throws IOException {
		String path = Paths.get(pathDestino, nombreArchivo).toString();
		FileOutputStream fos = new FileOutputStream(path);

		ZipOutputStream zipOut = new ZipOutputStream(fos);

		for (String archivo : archivos) {

			File fileToZip = new File(archivo);
			FileInputStream fis = new FileInputStream(fileToZip);

			ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
			zipOut.putNextEntry(zipEntry);

			byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zipOut.write(bytes, 0, length);
			}
			fis.close();
		}
		zipOut.close();
		fos.close();
	}

}
