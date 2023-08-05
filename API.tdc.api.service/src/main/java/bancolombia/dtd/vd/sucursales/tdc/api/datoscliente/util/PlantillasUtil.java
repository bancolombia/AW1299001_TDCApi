package bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import bancolombia.dtd.vd.li.commons.util.CargadorPropiedades;
import bancolombia.dtd.vd.li.commons.util.ConstantesGeneracionPDF;
import bancolombia.dtd.vd.li.commons.util.Parametro;

/***
 * Clase de utilidades para la descarga de la carta de bienvenida
 */
public class PlantillasUtil {

	private static final Logger logger = LogManager.getLogger(PlantillasUtil.class);

	/***
	 * Lista de Parametros para agregar a la plantilla
	 */
	private List<Parametro> listaParametros = new ArrayList<>();

	private String rutaplantilla = null;
	private StringWriter writer = null;

	private File filePdf;

	@Inject
	CargadorPropiedades propiedades;
	
	private String rutaFileTemp = null;

	public PlantillasUtil(List<Parametro> listaParametros, CargadorPropiedades propiedades) {
		this.listaParametros = listaParametros;
		this.propiedades = propiedades;
	}

	/**
	 * Método que mapea los parametros recibidos y crea el cuerpo html del
	 * correo.
	 * 
	 * @return true si se genera exitosamente el html, de lo contrario retorna
	 *         false.
	 */
	public boolean createBodyHtml() {
		writer = new StringWriter();
		Template template = null;
		String plantilla;
		List<String> listaImagenesTarjetas = null;
		String imagenes[] = null;
		String imagenesTarjetas[] = new String[2];

		VelocityEngine velocityEngine;
		VelocityContext context;

		try {
			plantilla = propiedades.getValue(ConstantesGeneracionPDF.NOMBRE_PLANTILLA_CORREO) != null
					? propiedades.getValue(ConstantesGeneracionPDF.NOMBRE_PLANTILLA_CORREO).toString()
					: "";
			context = new VelocityContext();

			for (Parametro param : listaParametros) {
				context.put(param.getClave(), param.getValor());

				if (param.getObject() != null) {
					context.put(param.getClave(), param.getObject());
				}
				if (param.getClave().equals(ConstantesGeneracionPDF.IMAGENES)) {
					imagenes = param.getValor().split(",");
					context.put("IMAGES", imagenes);
				}
				if (param.getClave().equals(ConstantesGeneracionPDF.CODIGO_IMAGEN)) {
					listaImagenesTarjetas = (List<String>) param.getObject();
					for (int i = 0; i < listaImagenesTarjetas.size(); i++) {
						imagenesTarjetas[i] = listaImagenesTarjetas.get(i).concat(".png");
					}
					context.put("CARD_IMAGES", imagenesTarjetas);
				}
				if (param.getClave().equals(ConstantesGeneracionPDF.RUTA_PLANTILLA_CORREO)) {
					rutaplantilla = propiedades.getValue(param.getValor());
					context.put("RUTA_IMG", rutaplantilla);
				}
			}

			velocityEngine = new VelocityEngine();
			velocityEngine.setProperty("input.encoding", StandardCharsets.UTF_8.name());
			velocityEngine.setProperty("file.resource.loader.description", "Velocity File Resource Loader");
			velocityEngine.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
			velocityEngine.setProperty("file.resource.loader.path", rutaplantilla);
			velocityEngine.setProperty("file.resource.loader.cache", "false");
			velocityEngine.setProperty("file.resource.loader.modificationCheckInterval", "2");
			velocityEngine.init();

			template = velocityEngine.getTemplate(plantilla);
			template.merge(context, writer);
			return true;
		} catch (Exception e) {
			logger.error("Error creando body html::::" + e.getMessage(), e);
			return false;
		}
	}

	/**
	 * Método que se encarga de convertir la plantilla html en un archivo PDF
	 */
	public void createFile() {
		filePdf = null;
		Document document = new Document();
		PdfWriter pdfWriter = null;
		Path file = null;
		try {
			rutaFileTemp = propiedades.getValue(ConstantesLI.RUTA_FILE_TEMP);
			Path direct = Paths.get(rutaFileTemp);
			FileAttribute<Set<PosixFilePermission>> attributes
		      = PosixFilePermissions.asFileAttribute(new HashSet<>(
		          Arrays.asList(PosixFilePermission.OWNER_WRITE,
		                        PosixFilePermission.OWNER_READ)));
			String sistemaOperativo = System.getProperty("os.name").toUpperCase();
			if(sistemaOperativo.contains(ConstantesLI.SOWIND)) {
				file = Files.createTempFile(direct, "tmp", ".pdf");
			}else {
				file = Files.createTempFile(direct, "tmp", ".pdf", attributes);
			}
			filePdf = file.toFile();
			document.setPageSize(PageSize.LETTER);
			pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(filePdf.getPath()));
			document.open();

			createBodyHtml();
			String content = this.writer.toString();
			XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, new StringReader(content));
		} catch (Exception e) {
			logger.error("Error creando archivo::::" + e.getMessage(), e);
		} finally {
			document.close();
			if (null != pdfWriter) {
				pdfWriter.close();
			}
		}
	}

	/**
	 * Retorna el archivo en formato pdf.
	 * 
	 * @return File
	 */
	public File getFilePdf() {
		return this.filePdf;
	}
}
