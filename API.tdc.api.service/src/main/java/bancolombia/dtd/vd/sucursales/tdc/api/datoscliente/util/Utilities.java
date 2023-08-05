package bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.model.EnrollmentRS;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.joda.time.LocalDate;

import bancolombia.dtd.vd.li.commons.exception.ExceptionLI;
import bancolombia.dtd.vd.li.commons.util.CargadorPropiedades;
import bancolombia.dtd.vd.li.dto.proxy.clave.GeneracionClaveRQ;
import bancolombia.dtd.vd.li.dto.proxy.comun.Propiedad;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.Categoria;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.GestionCreditoConsumoRQ;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.GestionCreditoConsumoRS;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.OfertaDigital;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.Subproducto;
import bancolombia.dtd.vd.li.dto.utils.ProductoCreditoDto;

public final class Utilities {

	private static final Logger logger = LogManager.getLogger(Utilities.class);

	private String rutaFileTemp = null;

	private static CargadorPropiedades propiedades = CargadorPropiedades.getInstance();

	/**
	 * Metodo para obtener las tarjetas de la BD.
	 * 
	 * @param oferta
	 * @param montoSolicitado
	 * @param propiedades
	 * @return
	 * @throws ExceptionLI
	 */
	public List<ProductoCreditoDto> obtenerTarjetas(OfertaDigital oferta, String montoSolicitado,
			CargadorPropiedades propiedades, String idProductoPreaprobado) throws ExceptionLI {
		ProductoCreditoDto producto = null;
		List<ProductoCreditoDto> tarjetasCredito = new ArrayList<>();
		List<String> beneficios = null;

		try {
			boolean valido = false;
			Double monto = Double.parseDouble(montoSolicitado);
			List<Categoria> categorias = oferta.getOferta().getProducto().get(0).getCategoria();

			for (Categoria categoria : categorias) {
				List<Subproducto> subProductos;
				String codigoCategoria = categoria.getCodigoCategoria();
				subProductos = categoria.getSubproducto();

				Collections.sort((List<Subproducto>) subProductos);
				for (Subproducto subproducto : subProductos) {
					if (monto >= Double.parseDouble(subproducto.getCupoMinimo())
							&& monto <= Double.parseDouble(subproducto.getCupoMaximo())) {
						producto = new ProductoCreditoDto();
						beneficios = new ArrayList<>();
						valido = true;
						producto.setCodigoImagen(subproducto.getCodigoImagen());
						producto.setNombreSubProducto(subproducto.getNombreSubproducto());
						producto.setIdProducto(subproducto.getIdSubproducto());
						producto.setCodigoCategoria(codigoCategoria);
						if (null != subproducto.getTarjetasCredito() && !subproducto.getTarjetasCredito().isEmpty()) {
							producto.setNombreRealce(
									subproducto.getTarjetasCredito().get(ConstantesLI.CERO).getNombreRealce());
						}
						ingresarCiclosFacturacion(producto, idProductoPreaprobado, subproducto);

						if (validarTarjetas(propiedades, idProductoPreaprobado, subproducto.getCodigoImagen())
								&& mostrarTarjeta(propiedades, subproducto.getCodigoImagen())) {
							if (subproducto.getListaCondiciones().size() >= ConstantesLI.TRES
									&& subproducto.getListaCondiciones().size() <= ConstantesLI.CINCO) {
								for (int i = 0; i <= subproducto.getListaCondiciones().size()
										- ConstantesLI.TRES; i++) {
									beneficios.add(subproducto.getListaCondiciones().get(i));
								}
								beneficios.add(subproducto.getListaCondiciones()
										.get(subproducto.getListaCondiciones().size() - ConstantesLI.DOS));
								beneficios.add(subproducto.getListaCondiciones()
										.get(subproducto.getListaCondiciones().size() - ConstantesLI.UNO));
								producto.setListaCondiciones(beneficios);
								tarjetasCredito.add(producto);
							} else if (subproducto.getListaCondiciones().size() > ConstantesLI.CINCO) {
								for (int i = 0; i < ConstantesLI.TRES; i++) {
									beneficios.add(subproducto.getListaCondiciones().get(i));
								}
								beneficios.add(subproducto.getListaCondiciones()
										.get(subproducto.getListaCondiciones().size() - ConstantesLI.DOS));
								beneficios.add(subproducto.getListaCondiciones()
										.get(subproducto.getListaCondiciones().size() - ConstantesLI.UNO));
								producto.setListaCondiciones(beneficios);
								tarjetasCredito.add(producto);
							}
						}
					}
				}
			}

			if (!valido) {
				throw new ExceptionLI("Error obtenerTarjetas", null);
			}
		} catch (Exception e) {
			logger.error("Error obtenerTarjetasCredito::::", e);
		}
		return tarjetasCredito;
	}

	private boolean mostrarTarjeta(CargadorPropiedades propiedades, String codigoImagen) {
		String[] listaPrdNoMostrar = propiedades.getValue(ConstantesLI.COD_IMG_TDC_NO_MOSTRAR).split(",");
		if (Arrays.asList(listaPrdNoMostrar).contains(codigoImagen)) {
			return false;
		} else {
			return true;
		}

	}

	private void ingresarCiclosFacturacion(ProductoCreditoDto producto, String idProductoPreaprobado,
			Subproducto subproducto) {
		String[] listaPrdCodFactDefault = propiedades.getValue(ConstantesLI.COD_CICLO_FACT_DEFAULT).split(",");

		if (Arrays.asList(listaPrdCodFactDefault).contains(idProductoPreaprobado)) {
			producto.setCicloFactura(ConstantesLI.DEFAULT_CICLOS);
		} else {
			if (null != subproducto.getTarjetasCredito() && !subproducto.getTarjetasCredito().isEmpty()) {
				producto.setCicloFactura(subproducto.getTarjetasCredito().get(ConstantesLI.CERO).getCicloFactura());
			}
		}
	}

	private boolean validarTarjetas(CargadorPropiedades propiedades2, String idProductoPreaprobado, String codImagen) {

		String[] listaPrdTDCJoven = propiedades2.getValue(ConstantesLI.VAL_API_COD_PRODUCTO_TDC_JOVEN).split(",");
		String[] listaPrdTDCIdeal = propiedades2.getValue(ConstantesLI.VAL_API_COD_PRODUCTO_TDC_IDEAL).split(",");
		String[] listaImgTDCJoven = propiedades2.getValue(ConstantesLI.COD_IMAGEN_TDC_JOVEN).split(",");
		String[] listaImgTDCIdeal = propiedades2.getValue(ConstantesLI.COD_IMAGEN_TDC_IDEAL).split(",");

		return (validarTarjetasJoven(propiedades2, idProductoPreaprobado, codImagen)
				|| validarTarjetasIdeal(propiedades2, idProductoPreaprobado, codImagen)
				|| (!Arrays.asList(listaPrdTDCJoven).contains(idProductoPreaprobado)
						&& !Arrays.asList(listaPrdTDCIdeal).contains(idProductoPreaprobado)
						&& !Arrays.asList(listaImgTDCJoven).contains(codImagen)
						&& !Arrays.asList(listaImgTDCIdeal).contains(codImagen)));
	}

	private boolean validarTarjetasJoven(CargadorPropiedades propiedades2, String idProductoPreaprobado,
			String codImagen) {
		String[] listaPrdTDCJoven = propiedades2.getValue(ConstantesLI.VAL_API_COD_PRODUCTO_TDC_JOVEN).split(",");
		String[] listaImgTDCJoven = propiedades2.getValue(ConstantesLI.COD_IMAGEN_TDC_JOVEN).split(",");

		return (Arrays.asList(listaPrdTDCJoven).contains(idProductoPreaprobado)
				&& Arrays.asList(listaImgTDCJoven).contains(codImagen));
	}

	private boolean validarTarjetasIdeal(CargadorPropiedades propiedades2, String idProductoPreaprobado,
			String codImagen) {
		String[] listaPrdTDCIdeal = propiedades2.getValue(ConstantesLI.VAL_API_COD_PRODUCTO_TDC_IDEAL).split(",");
		String[] listaImgTDCIdeal = propiedades2.getValue(ConstantesLI.COD_IMAGEN_TDC_IDEAL).split(",");

		return (Arrays.asList(listaPrdTDCIdeal).contains(idProductoPreaprobado)
				&& Arrays.asList(listaImgTDCIdeal).contains(codImagen));
	}

	public String getFechaActual() {
		Date myDate = new LocalDate().toDate();

		return new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss").format(myDate);
	}

	/**
	 * Metodo que permite generar la lista de variables de configuracion para envio
	 * de parametros a consumo back internos
	 * 
	 * @param propiedades CargadorPropiedades usado por la aplicacion de donde se
	 *                    sacaran las variables para el mapeo a construir
	 * @param varParam    lista de nombres de parametros separados por comas y que
	 *                    seran los utilizados para la construccion de la respuesta
	 * 
	 * @return List de parametros
	 */
	public List<Propiedad> crearListaConfiguracion(CargadorPropiedades propiedades, String varParam) {
		List<Propiedad> resp = new ArrayList<>();
		Propiedad para;
		String[] param = varParam.split(",");
		for (int i = 0; i < param.length; i++) {
			para = new Propiedad();
			para.setLlave(param[i]);
			para.setValor(propiedades.getValue(param[i]));
			resp.add(para);
		}
		return resp;
	}

	public File transformStringB64ToFile(String encodeStringB64, String fileExtention) {
		byte[] byteArrayFile = Base64.getDecoder().decode(encodeStringB64);
		File tempFile = null;
		Path tempFileDir = null;

		try {
			rutaFileTemp = propiedades.getValue(ConstantesLI.RUTA_FILE_TEMP);
			Path direct = Paths.get(rutaFileTemp);
			String sistemaOperativo = System.getProperty("os.name").toUpperCase();
			FileAttribute<Set<PosixFilePermission>> attributes = PosixFilePermissions.asFileAttribute(
					new HashSet<>(Arrays.asList(PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_READ)));
			if (sistemaOperativo.contains(ConstantesLI.SOWIND)) {
				tempFileDir = Files.createTempFile(direct, "tmp", ".".concat(fileExtention));
			} else {
				tempFileDir = Files.createTempFile(direct, "tmp", ".".concat(fileExtention), attributes);
			}
			tempFile = tempFileDir.toFile();
		} catch (IOException e) {
			logger.error("Error haciendo merge del documento:::::", e);
		}

		try (FileOutputStream fos = new FileOutputStream(tempFile);) {

			fos.write(byteArrayFile);
			fos.close();
		} catch (FileNotFoundException e) {
			logger.error("Error haciendo merge del documento:::::::", e);
		} catch (IOException e) {
			logger.error("Error transformando bytes a File:::::", e);
		}
		return tempFile;
	}

	/**
	 * Funcion que une varios archivos pdf
	 *
	 * @return Archivo pdf unido
	 */
	public File mergePDFFiles(List<File> listFiles, String fileExtention) {
		// Instantiating PDFMergerUtility class
		PDFMergerUtility pdfMerger = new PDFMergerUtility();
		File mergedFile;
		Path mergedFileDir = null;
		try {
			// Setting the destination file
			rutaFileTemp = propiedades.getValue(ConstantesLI.RUTA_FILE_TEMP);
			Path direct = Paths.get(rutaFileTemp);
			String sistemaOperativo = System.getProperty("os.name").toUpperCase();
			FileAttribute<Set<PosixFilePermission>> attributes = PosixFilePermissions.asFileAttribute(
					new HashSet<>(Arrays.asList(PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_READ)));
			if (sistemaOperativo.contains(ConstantesLI.SOWIND)) {
				mergedFileDir = Files.createTempFile(direct, "merged", ".".concat(fileExtention));
			} else {
				mergedFileDir = Files.createTempFile(direct, "merged", ".".concat(fileExtention), attributes);
			}
			mergedFile = mergedFileDir.toFile();
			pdfMerger.setDestinationFileName(mergedFile.getAbsolutePath());

			for (File file : listFiles) {
				pdfMerger.addSource(file);
			}

			pdfMerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
			return mergedFile;
		} catch (IOException e) {
			logger.error("Error haciendo merge del documento::::", e);
		}

		return null;
	}

	/**
	 * Metodo para recuperar la IP de quien genera la peticion a los diferentes
	 * consumos sobre la API
	 * 
	 * @param requestContext request recibido de quien genera la peticion
	 * @return IP desde donde se realiza la peticion
	 */
	public String getIpCliente(HttpServletRequest requestContext) {
		String ipCliente = requestContext.getHeader("X-FORWARDED-FOR");
		if (ipCliente == null) {
			ipCliente = requestContext.getRemoteAddr();
		} else {
			ipCliente = ipCliente.contains(",") ? ipCliente.split(",")[0] : ipCliente;
		}
		if (ipCliente != null && !ipCliente.matches(ConstantesLI.REGINFODISPOSITIVO3)) {
			ipCliente = "127.0.0.1";
		}
		return ipCliente;
	}

	/**
	 * Metodo para convertir los ciclos de pagos de las tarjetas de credito a fechas
	 * de pagos
	 * 
	 * @param cicloFactura objeto TarjetasCredito return TarjetasCredito
	 */
	public String convertirFechaPago(String cicloFactura) {
		String fechaPago = "";
		int cicloFact = 0;
		String cicloFactdc = "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(LocalDate.now().toDate());
		int mes = cal.get(Calendar.MONTH) + 1;
		int anno = cal.get(Calendar.YEAR);
		Integer siguienteMes = mes + 1;

		try {

			if (siguienteMes == 13) {// si es diciembre
				siguienteMes = 1;
			}
			cicloFactdc = cicloFactura;

			// Validamos que el siguiente mes sea febrero
			cicloFact = validarMesFebrero(cicloFact, cicloFactdc, anno, siguienteMes);

			// Validamos que el siguiente mes sea uno de 30 dias
			cicloFact = validarMesTreintaDias(cicloFact, cicloFactdc, siguienteMes);

			// Validamos que el siguiente mes sea uno de 31 dias
			cicloFact = validarMesTreintayUnDias(cicloFact, cicloFactdc, siguienteMes);

			fechaPago = String.valueOf(cicloFact);

		} catch (Exception e) {
			logger.error("Error conversionCicloFacturacion::::", e);
		}
		return fechaPago;
	}

	/**
	 * @param cicloFact
	 * @param cicloFactdc
	 * @param siguienteMes
	 * @return
	 */
	private int validarMesTreintayUnDias(int cicloFact, String cicloFactdc, Integer siguienteMes) {
		if (validacionMes(siguienteMes) && cicloFactdc.equalsIgnoreCase(ConstantesLI.CICLO_PAGO_TDC)) {
			cicloFact = Integer.parseInt(cicloFactdc) + ConstantesLI.DIECISEIS;
		} else if ((validacionMes(siguienteMes)) && !cicloFactdc.equalsIgnoreCase(ConstantesLI.CICLO_PAGO_TDC)) {
			cicloFact = Integer.parseInt(cicloFactdc) - ConstantesLI.QUINCE;
		}
		return cicloFact;
	}

	private boolean validacionMes(Integer siguienteMes) {
		return (siguienteMes == ConstantesLI.UNO || siguienteMes == ConstantesLI.TRES
				|| siguienteMes == ConstantesLI.CINCO || siguienteMes == ConstantesLI.SIETE
				|| siguienteMes == ConstantesLI.OCHO || siguienteMes == ConstantesLI.DIEZ
				|| siguienteMes == ConstantesLI.DOCE);
	}

	/**
	 * @param cicloFact
	 * @param cicloFactdc
	 * @param siguienteMes
	 * @return
	 */
	private int validarMesTreintaDias(int cicloFact, String cicloFactdc, Integer siguienteMes) {
		if (validarMesTreintaDiasAux(siguienteMes) && cicloFactdc.equalsIgnoreCase(ConstantesLI.CICLO_PAGO_TDC)) {
			cicloFact = Integer.parseInt(cicloFactdc) + ConstantesLI.QUINCE;
		} else if (validarMesTreintaDiasAux(siguienteMes)
				&& !cicloFactdc.equalsIgnoreCase(ConstantesLI.CICLO_PAGO_TDC)) {
			cicloFact = Integer.parseInt(cicloFactdc) - ConstantesLI.QUINCE;
		}
		return cicloFact;
	}

	private boolean validarMesTreintaDiasAux(Integer siguienteMes) {
		return (siguienteMes == ConstantesLI.CUATRO || siguienteMes == ConstantesLI.SEIS
				|| siguienteMes == ConstantesLI.NUEVE || siguienteMes == ConstantesLI.ONCE);
	}

	/**
	 * @param cicloFact
	 * @param cicloFactdc
	 * @param anno
	 * @param siguienteMes
	 * @return
	 */
	private int validarMesFebrero(int cicloFact, String cicloFactdc, int anno, Integer siguienteMes) {
		GregorianCalendar calendar = new GregorianCalendar();
		if (siguienteMes == ConstantesLI.DOS && calendar.isLeapYear(anno)
				&& cicloFactdc.equalsIgnoreCase(ConstantesLI.CICLO_PAGO_TDC)) {
			cicloFact = Integer.parseInt(cicloFactdc) + ConstantesLI.CATORCE;
		} else if (siguienteMes == ConstantesLI.DOS && !calendar.isLeapYear(anno)
				&& cicloFactdc.equalsIgnoreCase(ConstantesLI.CICLO_PAGO_TDC)) {
			cicloFact = Integer.parseInt(cicloFactdc) + ConstantesLI.TRECE;
		} else if (siguienteMes == ConstantesLI.DOS && !calendar.isLeapYear(anno)
				&& !cicloFactdc.equalsIgnoreCase(ConstantesLI.CICLO_PAGO_TDC)) {
			cicloFact = Integer.parseInt(cicloFactdc) - ConstantesLI.QUINCE;
		}
		return cicloFact;
	}

	public String generarSharedKey(String tipoDocumento, String numerodocumento) {
		return tipoDocumento.substring(tipoDocumento.length() - ConstantesLI.DOS, tipoDocumento.length())
				+ numerodocumento;
	}

	public String buscarTipoMensaje(GeneracionClaveRQ generacionClaveRQ) {
		if (StringUtils.isNotBlank(generacionClaveRQ.getMetodoEnvioOTPODA())) {
			if (generacionClaveRQ.getMetodoEnvioOTPODA().equals(ConstantesLI.TIPO_MENSAJE_TEXTO)) {
				return ConstantesLI.TIPO_MENSAJE_M;
			} else if (generacionClaveRQ.getMetodoEnvioOTPODA().equals(ConstantesLI.TIPO_MENSAJE_CORREO)
					|| generacionClaveRQ.getMetodoEnvioOTPODA().equals(ConstantesLI.TIPO_MENSAJE_MIXTO)) {
				return ConstantesLI.TIPO_MENSAJE_C;
			}
		} else if (generacionClaveRQ.getMecanismo().equals(ConstantesLI.VAR_CLAVE_SOFTOKEN)) {
			return ConstantesLI.TIPO_MENSAJE_C;
		}
		return null;
	}

	/**
	 * Metodo para convertir los ciclos de facturacion en fechas de pago
	 * 
	 * @param respuestaService Objeto de referencia para llenar los datos
	 */
	public void mapeoFechasPago(GestionCreditoConsumoRS respuestaService) {
		if (null != respuestaService.getProductosTarjeta()) {
			for (int i = 0; i < respuestaService.getProductosTarjeta().size(); i++) {
				if (null != respuestaService.getProductosTarjeta().get(i).getCicloFactura()) {
					for (int j = 0; j < respuestaService.getProductosTarjeta().get(i).getCicloFactura().size(); j++) {
						String fechaPago = convertirFechaPago(
								respuestaService.getProductosTarjeta().get(i).getCicloFactura().get(j));
						respuestaService.getProductosTarjeta().get(i).getFechaPago().add(fechaPago);
					}
				}
			}
		}
	}

	public Response validacionRespuestaError(ExceptionLI e) {
		if (e.getCodigo().equalsIgnoreCase(ConstantesLI.ERROR_401)) {
			return Response.status(Status.UNAUTHORIZED).build();
		} else {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Metodo para obtener las tarjetas de la BD. para luego filtrarlas por
	 * compatibilidad
	 * 
	 * @param oferta
	 * @param propiedades
	 * @param tdcSeleccionada
	 * @return
	 * @throws ExceptionLI
	 */
	public List<ProductoCreditoDto> filtrarCompatibilidadTDC(GestionCreditoConsumoRS oferta,
			CargadorPropiedades propiedades, GestionCreditoConsumoRQ tdcSeleccionada) throws Exception {

		List<ProductoCreditoDto> tarjetasCredito = new ArrayList<>();
		List<ProductoCreditoDto> tarjetasCreditoResp = new ArrayList<>();
		String cantidadTDC = propiedades.getValue(ConstantesLI.CANTIDAD_COD_IMAGEN_TDC);
		String codImgTDC = "";
		String incopatibilidadTDC = "";
		try {
			int cantidadcodimgtdc = Integer.parseInt(cantidadTDC);
			tarjetasCredito = oferta.getProductosTarjeta();

			// Si ya seleccione alguna tarjeta busco la incompatibilidad
			for (int j = 0; j < tdcSeleccionada.getProducto().size(); j++) {
				// sino llego codImagen para filtrar retorno todas las tarjetas
				if (StringUtils.isNotBlank(tdcSeleccionada.getProducto().get(j).getCodigoImagen())) {
					// Nro de tarjetas configuradas
					for (int k = 1; k <= cantidadcodimgtdc; k++) {
						// busco la conf de la TDC
						codImgTDC = ConstantesLI.COD_IMAGEN_TDC + String.valueOf(k);
						// busco la lista de incompatibilidades
						if (Arrays.asList(propiedades.getValue(codImgTDC).split(","))
								.contains(tdcSeleccionada.getProducto().get(j).getCodigoImagen())) {
							// variable que tiene las incompatibilidades de las TDC
							incopatibilidadTDC = ConstantesLI.INCOPATIBILIDAD_TDC + String.valueOf(k);
						}
					}
					// de todas las tarjetas que cumplen monto busco la incompatibilidad y si es
					// compatible la agrego al objeto de respuesta
					for (ProductoCreditoDto productoCreditoDto : tarjetasCredito) {
						if (!Arrays.asList(propiedades.getValue(incopatibilidadTDC).split(","))
								.contains(productoCreditoDto.getCodigoImagen())) {
							tarjetasCreditoResp.add(productoCreditoDto);
						}
					}
				} else {
					tarjetasCreditoResp = tarjetasCredito;
				}
			}
		} catch (NumberFormatException e) {
			logger.error("Error filtrarCompatibilidadTDC::::", e);
		}

		return tarjetasCreditoResp;
	}

	public Date convertStringtoDate(String fecha) throws ParseException {
		if(!StringUtils.isEmpty(fecha)){
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
			return date1;
		}
		return null;
	}

	public String validateTipoEnvio(EnrollmentRS responseApi){
		if(!StringUtils.isEmpty(responseApi.getCustomerMobileNumber()) && !StringUtils.isEmpty(responseApi.getCustomerEmail())){
			return ConstantesLI.TIPO_MENSAJE_MIXTO;
		}
		else if(!StringUtils.isEmpty(responseApi.getCustomerMobileNumber())){
			return ConstantesLI.TIPO_MENSAJE_TEXTO;
		}else if(!StringUtils.isEmpty(responseApi.getCustomerEmail())){
			return ConstantesLI.TIPO_MENSAJE_CORREO;
		}else
			return ConstantesLI.TIPO_MENSAJE_TEXTO;
	}

}