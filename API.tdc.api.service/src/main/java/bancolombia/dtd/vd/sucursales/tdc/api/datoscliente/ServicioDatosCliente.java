package bancolombia.dtd.vd.sucursales.tdc.api.datoscliente;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.ws.rs.core.Response;


import bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.model.EnrollmentRQ;
import bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.model.EnrollmentRS;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.joda.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.grupobancolombia.intf.canal.movil.generaciontoken.v1.SolicitarTokenResponse;
import com.grupobancolombia.intf.canal.movil.generaciontoken.v1.ValidarTokenResponse;
import com.grupobancolombia.intf.seguridadcorporativa.canales.autenticarclienteotp.v2.AutenticarClienteOTPODAResponse;
import com.grupobancolombia.intf.seguridadcorporativa.canales.autenticarclienteotp.v2.AutenticarClienteOTPSoftokenResponse;
import com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.v2.GenerarEnviarOTPODAResponse;

import bancolombia.dtd.vd.li.api.autenticacion.ServicioAutenticacion;
import bancolombia.dtd.vd.li.api.autenticacion.util.UtilsJwt;
import bancolombia.dtd.vd.li.commons.exception.ExceptionLI;
import bancolombia.dtd.vd.li.commons.util.CargadorPropiedades;
import bancolombia.dtd.vd.li.commons.util.ConstantesGeneracionPDF;
import bancolombia.dtd.vd.li.commons.util.Parametro;
import bancolombia.dtd.vd.li.dto.autenticacion.AutenticacionRS;
import bancolombia.dtd.vd.li.dto.mensajesError.ErrorRequestDTO;
import bancolombia.dtd.vd.li.dto.mensajesError.ErrorResponseDTO;
import bancolombia.dtd.vd.li.dto.mensajesError.MensajeFuncionalDTO;
import bancolombia.dtd.vd.li.dto.proxy.authFuerte.AutenticacionFuerteRQ;
import bancolombia.dtd.vd.li.dto.proxy.authFuerte.AutenticacionFuerteRS;
import bancolombia.dtd.vd.li.dto.proxy.clave.GeneracionClaveRQ;
import bancolombia.dtd.vd.li.dto.proxy.clave.GeneracionClaveRS;
import bancolombia.dtd.vd.li.dto.proxy.clave.ValidacionClaveRQ;
import bancolombia.dtd.vd.li.dto.proxy.clave.ValidacionClaveRS;
import bancolombia.dtd.vd.li.dto.proxy.comun.ErrorProxyComun;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.GestionCreditoConsumoRQ;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.GestionCreditoConsumoRS;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.OfertaDigital;
import bancolombia.dtd.vd.li.dto.utils.EnvioCorreoDto;
import bancolombia.dtd.vd.li.dto.utils.InformacionTransaccionDto;
import bancolombia.dtd.vd.li.dto.utils.LibreInversion;
import bancolombia.dtd.vd.li.dto.utils.ProductoCreditoDto;
import bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.util.CallRestServiceBack;
import bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.util.ConstantesLI;
import bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.util.PlantillasUtil;
import bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.util.Utilities;
import suc.lib.autenticarClienteOTP.ServicioAutenticarClienteOTP;
import suc.lib.autenticarClienteOTP.util.AutenticarClienteOTPRQ;
import suc.lib.generacionToken.ServicioGeneracionToken;
import suc.lib.generacionToken.util.GeneracionTokenRQ;
import suc.lib.generarOTP.ServicioGenerarOTP;
import suc.lib.generarOTP.util.AutenticacionOTPRQ;
import suc.lib.generarOTP.util.Destinatario;
import suc.lib.generarOTP.util.InformacionMensajeType;

public class ServicioDatosCliente {
	private static final Logger logger = LogManager.getLogger(ServicioDatosCliente.class);

	private String mensaje = "";

	protected static CargadorPropiedades propiedades = CargadorPropiedades.getInstance();
	private Map<Object, Object> param;
	private static final Gson gson = new Gson();

	@Inject
	CallRestServiceBack callRestServiceBack;

	@Inject
	private ServicioAutenticacion autenticacion;

	private LibreInversion libreInversion = new LibreInversion();
	private ErrorProxyComun er = new ErrorProxyComun();
	private Utilities util = new Utilities();
	UtilsJwt utilsJwt = new UtilsJwt();

	/**
	 * Método que consume el servicio de gestionCreditoConsumo operacion
	 * crearSolicitudCreditoConsumo (Crear Caso).
	 * 
	 * @param authorization, Informacion del token actual.
	 * @param encrypter,     Informacion del objeto libreInversion.
	 * @param ipCliente      ipCliente
	 * @return
	 * @throws ExceptionLI
	 */
	public GestionCreditoConsumoRS crearCreditoConsumo(String authorization, String encrypter, String ipCliente)
			throws ExceptionLI {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRQ gestionCreditoConsumoRQ = new GestionCreditoConsumoRQ();
		param = new HashMap<>();
		try {
			this.libreInversion = new LibreInversion();
			this.libreInversion = obtenerGestionCredito(encrypter);
			ThreadContext.put(ConstantesLI.TXT_SESION_ID,
					this.libreInversion.getInformacionTransaccion().getIdSesion());
			ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);

			gestionCreditoConsumoRQ = obtenerGestionCreditoConsumoRQTDC(this.libreInversion);

			respuestaService = callProxyGestionCreditoConsumo(authorization, respuestaService, gestionCreditoConsumoRQ);

			if (StringUtils.isNotEmpty(respuestaService.getIdProceso())) {
				OfertaDigital resp = consultaApiGestionCreditoConsumo(gestionCreditoConsumoRQ.getIdSesion(),
						respuestaService.getNumeroSolicitud(), gestionCreditoConsumoRQ.getPaso(), true);

				if (resp == null) {
					mapearMensajeError(respuestaService, ConstantesLI.ERROR_RECUPERANDO_RESPUESTA1);
					param.remove(ConstantesLI.DB_PASO);
					param.put(ConstantesLI.DB_PASO,
							gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
					this.guardarDatosDB(param, gson.toJson(respuestaService));
				} else {
					if (resp.getConfirmacionTransaccion() == null) {
						respuestaService.setCodSolicitante(resp.getOferta().getInformacionCliente().getIdSolicitante());
						respuestaService.setIdProducto(this.libreInversion.getInformacionCredito().getIdProducto());

						this.libreInversion.getInformacionTransaccion()
								.setTokenActual(respuestaService.getTokenActual());
						authorization = "Bearer " + respuestaService.getTokenActual();
						gestionCreditoConsumoRQ.setPasoFuncional("paso_li_107_A");
						mapearRespuestaTDC(gestionCreditoConsumoRQ, respuestaService);
						AutenticacionRS sesionTDC = this.validarSesion(authorization,
								gestionCreditoConsumoRQ.getPasoFuncional(), gestionCreditoConsumoRQ.getIdSesion());
						respuestaService.setTokenActual(sesionTDC.getTokenNuevo());
						gestionCreditoConsumoRQ
								.setPasoFuncional(this.libreInversion.getInformacionTransaccion().getPasoFuncional());
						param.remove(ConstantesLI.DB_PASO);
						param.put(ConstantesLI.DB_PASO,
								gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_R);
						this.guardarDatosDB(param, gson.toJson(respuestaService));
					} else {
						mapearMensajeError(respuestaService, "GCC-" + resp.getConfirmacionTransaccion().getCodigo());
						param.remove(ConstantesLI.DB_PASO);
						param.put(ConstantesLI.DB_PASO,
								gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
						this.guardarDatosDB(param, gson.toJson(respuestaService));
					}
					mensaje = "Fin respuesta crearCreditoConsumo: " + respuestaService.toString()
							+ gestionCreditoConsumoRQ.getIdSesion() + " - " + ipCliente;
					logger.info(mensaje);
				}
			} else {
				if (StringUtils.isNotBlank(respuestaService.getCodError())) {
					mapearMensajeError(respuestaService, "GCC-" + respuestaService.getCodError());
					param.remove(ConstantesLI.DB_PASO);
					param.put(ConstantesLI.DB_PASO,
							gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
					this.guardarDatosDB(param, gson.toJson(respuestaService));
				}
			}
		} catch (ExceptionLI e) {
			logger.error(String.format("Error crearCreditoConsumo. %s ", gestionCreditoConsumoRQ.getIdSesion()), e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuestaService.setCodError(e.getCodigo());
			respuestaService.setDescError(e.getMensaje());
			if (Arrays.asList(ConstantesLI.getExcepcionesSession()).contains(e.getCodigo())) {
				mapearMensajeError(respuestaService, e.getCodigo());
			}else{
				mapearMensajeError(respuestaService, "GCC-" + e.getCodigo());
			}
			this.guardarDatosDB(param, gson.toJson(respuestaService));
		}
		return respuestaService;
	}

	/**
	 * Método que consume el servicio de gestionCreditoConsumo operacion
	 * avanzarSolicitudCreditoConsumo (Avanzar Momento 1).
	 * 
	 * @param authorization, Informacion del token actual.
	 * @param encrypter,     Informacion del request que se envia del front.
	 * @param ipCliente      ipCliente
	 * @return
	 * @throws ExceptionLI
	 */
	public GestionCreditoConsumoRS confirmarCreditoConsumo(String authorization, String encrypter, String ipCliente)
			throws ExceptionLI {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRQ gestionCreditoConsumoRQ = new GestionCreditoConsumoRQ();
		param = new HashMap<>();
		try {
			gestionCreditoConsumoRQ = obtenerGestionCreditoConsumoRQ(encrypter);
			ThreadContext.put(ConstantesLI.TXT_SESION_ID, gestionCreditoConsumoRQ.getIdSesion());
			ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);

			logger.info("Inicio confirmarCreditoConsumo ");
			this.libreInversion = new LibreInversion();
			this.libreInversion.setInformacionTransaccion(new InformacionTransaccionDto());
			this.libreInversion.getInformacionTransaccion()
					.setPasoFuncional(gestionCreditoConsumoRQ.getPasoFuncional());
			this.libreInversion.getInformacionTransaccion().setIdSesion(gestionCreditoConsumoRQ.getIdSesion());

			respuestaService = callProxyGestionCreditoConsumo(authorization, respuestaService, gestionCreditoConsumoRQ);

			if (StringUtils.isNotEmpty(respuestaService.getIdProceso())) {
				OfertaDigital resp = consultaApiGestionCreditoConsumo(gestionCreditoConsumoRQ.getIdSesion(),
						respuestaService.getNumeroSolicitud(), gestionCreditoConsumoRQ.getPaso(), true);
				if (resp == null) {
					mapearMensajeError(respuestaService, ConstantesLI.ERROR_RECUPERANDO_RESPUESTA2);
				} else {
					if (resp.getConfirmacionTransaccion() == null
							|| "".equalsIgnoreCase(resp.getConfirmacionTransaccion().getCodigo())) {
						if (resp.getDocumentos() != null && !resp.getDocumentos().get(0).getDocumento().isEmpty()) {
							respuestaService.setDocumento(resp.getDocumentos().get(0).getDocumento());
						}
						mapearRespuestaOk(param, gestionCreditoConsumoRQ, respuestaService);
					} else {
						mapearMensajeError(respuestaService, "GCC-" + resp.getConfirmacionTransaccion().getCodigo());
					}
					mensaje = "Fin respuesta confirmarCreditoConsumo: " + respuestaService.toString();
					logger.info(mensaje);
				}
			} else {
				if (StringUtils.isNotBlank(respuestaService.getCodError())) {
					mapearMensajeError(respuestaService, "GCC-" + respuestaService.getCodError());
				}
			}
		} catch (ExceptionLI e) {
			logger.error("Error confirmarCreditoConsumo.", e);
			respuestaService.setCodError(e.getCodigo());
			respuestaService.setDescError(e.getMensaje());
			if (Arrays.asList(ConstantesLI.getExcepcionesSession()).contains(e.getCodigo())) {
				mapearMensajeError(respuestaService, e.getCodigo());
			}else{
				mapearMensajeError(respuestaService, "GCC-" + e.getCodigo());
			}
		}
		return respuestaService;
	}

	/**
	 * Método que consume el servicio de gestionCreditoConsumo operacion
	 * avanzarSolicitudCreditoConsumo (Avanzar Momento 2).
	 * 
	 * @param authorization, Informacion del token actual.
	 * @param encrypter,     Informacion del request que se envia del front.
	 * @param ipCliente      ipCliente
	 * @return
	 * @throws ExceptionLI
	 */
	public GestionCreditoConsumoRS firmarDocumentosCreditoConsumo(String authorization, String encrypter,
			String ipCliente) throws ExceptionLI {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRQ gestionCreditoConsumoRQ = new GestionCreditoConsumoRQ();
		param = new HashMap<>();
		try {
			gestionCreditoConsumoRQ = obtenerGestionCreditoConsumoRQ(encrypter);
			ThreadContext.put(ConstantesLI.TXT_SESION_ID, gestionCreditoConsumoRQ.getIdSesion());
			ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);

			logger.info("Inicio firmarDocumentosCreditoConsumo");
			this.libreInversion = new LibreInversion();
			this.libreInversion.setInformacionTransaccion(new InformacionTransaccionDto());
			this.libreInversion.getInformacionTransaccion()
					.setPasoFuncional(gestionCreditoConsumoRQ.getPasoFuncional());
			this.libreInversion.getInformacionTransaccion().setIdSesion(gestionCreditoConsumoRQ.getIdSesion());

			respuestaService = callProxyGestionCreditoConsumo(authorization, respuestaService, gestionCreditoConsumoRQ);

			if (StringUtils.isNotEmpty(respuestaService.getIdProceso())) {
				OfertaDigital resp = consultaApiGestionCreditoConsumo(gestionCreditoConsumoRQ.getIdSesion(),
						respuestaService.getNumeroSolicitud(), gestionCreditoConsumoRQ.getPaso(), true);

				if (resp == null) {
					mapearMensajeError(respuestaService, ConstantesLI.ERROR_RECUPERANDO_RESPUESTA3);
					param.remove(ConstantesLI.DB_PASO);
					param.put(ConstantesLI.DB_PASO,
							gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
					this.guardarDatosDB(param, gson.toJson(respuestaService));
				} else {
					String[] lista = propiedades.getValue("CODIGOS_FINALIZACION_TARJETAS").split(",");
					if ("ok".equalsIgnoreCase(resp.getConfirmacionTransaccion().getCodigo())
							&& (Arrays.asList(lista).contains(resp.getConfirmacionTransaccion().getDescripcion()))) {
						if (resp.getConfirmacionTransaccion().getDescripcion().equalsIgnoreCase(ConstantesLI.EXITOSO)) {
							respuestaService.setNumeroSolicitud(resp.getCabecera().getNumeroSolicitud());
							respuestaService.setDescFuncional(resp.getConfirmacionTransaccion().getDescripcion());
							mapearRespuestaOk(param, gestionCreditoConsumoRQ, respuestaService);
						} else {
							respuestaService.setNumeroSolicitud(resp.getCabecera().getNumeroSolicitud());
							respuestaService.setDescFuncional(resp.getConfirmacionTransaccion().getDescripcion());
							mapearMensajeErrorEmisionTarjeta(gestionCreditoConsumoRQ.getPasoFuncional(),
									respuestaService, "GCC-" + resp.getConfirmacionTransaccion().getDescripcion());
							respuestaService.setCodError(resp.getConfirmacionTransaccion().getDescripcion());
						}
					} else {
						mapearMensajeError(respuestaService, "GCC-" + resp.getConfirmacionTransaccion().getCodigo());
						param.remove(ConstantesLI.DB_PASO);
						param.put(ConstantesLI.DB_PASO,
								gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
						this.guardarDatosDB(param, gson.toJson(respuestaService));
					}
				}
				mensaje = "Fin respuesta firmarDocumentosCreditoConsumo: " + respuestaService.toString();
				logger.info(mensaje);
			} else {
				if (StringUtils.isNotBlank(respuestaService.getCodError())) {
					mapearMensajeError(respuestaService, "GCC-" + respuestaService.getCodError());
					param.remove(ConstantesLI.DB_PASO);
					param.put(ConstantesLI.DB_PASO,
							gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
					this.guardarDatosDB(param, gson.toJson(respuestaService));
				}
			}
		} catch (ExceptionLI e) {
			logger.error("Error firmarDocumentosCreditoConsumo.", e);
			respuestaService.setCodError(e.getCodigo());
			respuestaService.setDescError(e.getMensaje());
			if (Arrays.asList(ConstantesLI.getExcepcionesSession()).contains(e.getCodigo())) {
				mapearMensajeError(respuestaService, e.getCodigo());
			}else{
				mapearMensajeError(respuestaService, "GCC-" + e.getCodigo());
			}
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			this.guardarDatosDB(param, gson.toJson(respuestaService));
		}
		return respuestaService;
	}

	/**
	 * Método que mapea el request que llega del front al objeto
	 * gestionCreditoConsumoRQ
	 * 
	 * @param libreInversiontdc objeto Libre Inversion
	 * @return
	 * @throws ExceptionLI
	 */
	private GestionCreditoConsumoRQ obtenerGestionCreditoConsumoRQTDC(LibreInversion libreInversiontdc) {
		GestionCreditoConsumoRQ gestionCreditoConsumoRQ = new GestionCreditoConsumoRQ();
		gestionCreditoConsumoRQ.setIdSesion(libreInversiontdc.getInformacionTransaccion().getIdSesion());
		gestionCreditoConsumoRQ.setTipodocumento(libreInversiontdc.getDatosCliente().getTipoIdentificacion());
		if (libreInversiontdc.getDatosCliente().getTipoIdentificacion().equalsIgnoreCase(ConstantesLI.TIPO_CEDULA_CE)) {
			gestionCreditoConsumoRQ.setNumeroDocumento(libreInversiontdc.getDatosCliente().getNumeroExtranjeria());
		} else {
			gestionCreditoConsumoRQ.setNumeroDocumento(libreInversiontdc.getDatosCliente().getNumeroIdentificacion());
		}
		gestionCreditoConsumoRQ.setSucursalRadicacion(libreInversiontdc.getInformacionSucursal().getCodigoSucursal());
		gestionCreditoConsumoRQ
				.setCodigoAsesorComercial(libreInversiontdc.getInformacionSucursal().getCodigoCIFAsesor());
		gestionCreditoConsumoRQ.setCodigoReferido(libreInversiontdc.getInformacionSucursal().getCodigoReferido());
		gestionCreditoConsumoRQ.setIdProducto(libreInversiontdc.getInformacionCredito().getIdProducto());
		gestionCreditoConsumoRQ.setProducto(libreInversiontdc.getInformacionCredito().getProductoCredito());
		gestionCreditoConsumoRQ.setPasoFuncional(libreInversiontdc.getInformacionTransaccion().getPasoFuncional());
		gestionCreditoConsumoRQ.setPaso("paso1");
		return gestionCreditoConsumoRQ;
	}

	/**
	 * Método que mapea el request que llega del front al objeto LibreInversion
	 * 
	 * @param encrypter, objeto Libre Inversion
	 * @return
	 */
	private LibreInversion obtenerGestionCredito(String encrypter) {
		LibreInversion libreinvers = null;
		libreinvers = gson.fromJson(encrypter, LibreInversion.class);

		param.put(ConstantesLI.DB_ID_SESION, libreinvers.getInformacionTransaccion().getIdSesion());
		param.put(ConstantesLI.DB_PASO, libreinvers.getInformacionTransaccion().getPasoFuncional());
		this.guardarDatosDB(param, gson.toJson(libreinvers));
		return libreinvers;

	}

	private GestionCreditoConsumoRQ obtenerGestionCreditoConsumoRQ(String encrypter) {
		GestionCreditoConsumoRQ gestionCreditoConsumoRQ = null;
		gestionCreditoConsumoRQ = gson.fromJson(encrypter, GestionCreditoConsumoRQ.class);

		param.put(ConstantesLI.DB_ID_SESION, gestionCreditoConsumoRQ.getIdSesion());
		param.put(ConstantesLI.DB_PASO, gestionCreditoConsumoRQ.getPasoFuncional());
		this.guardarDatosDB(param, gson.toJson(gestionCreditoConsumoRQ));
		return gestionCreditoConsumoRQ;
	}

	private GestionCreditoConsumoRS callProxyGestionCreditoConsumo(String authorization,
			GestionCreditoConsumoRS respuestaService, GestionCreditoConsumoRQ gestionCreditoConsumoRQ)
			throws ExceptionLI {
		AutenticacionRS sesion = this.validarSesion(authorization, gestionCreditoConsumoRQ.getPasoFuncional(),
				gestionCreditoConsumoRQ.getIdSesion());

		if (gestionCreditoConsumoRQ.getPaso().equalsIgnoreCase(ConstantesLI.PASO1)) {
			gestionCreditoConsumoRQ.setTipoVenta("P");

			respuestaService = callRestServiceBack.proxyGestionCreditoConsumo(gestionCreditoConsumoRQ,
					propiedades.getPropiedades(), ConstantesLI.SERVICIO_OFERTA_DIGITAL_CREAR_SOL);
		} else if (gestionCreditoConsumoRQ.getPaso().equalsIgnoreCase(ConstantesLI.PASO2)) {
			for (int i = 0; i < gestionCreditoConsumoRQ.getProducto().size(); i++) {
				if (gestionCreditoConsumoRQ.getProducto().get(i).getAceptaDebitoAutomatico()) {
					String tipoCuenta = gestionCreditoConsumoRQ.getProducto().get(i).getCuentaDebitoAutomatico()
							.split("-")[ConstantesLI.CERO].trim();
					String cuenta = gestionCreditoConsumoRQ.getProducto().get(i).getCuentaDebitoAutomatico().split("-")[ConstantesLI.UNO]
							.trim();
					gestionCreditoConsumoRQ.getProducto().get(i).setTipoCuentaDebitoAutomatico(tipoCuenta);
					gestionCreditoConsumoRQ.getProducto().get(i).setCuentaDebitoAutomatico(cuenta);
				}
			}
			
			

			gestionCreditoConsumoRQ
					.setNombreTarea(propiedades.getValue("PROXY_GESTION_SOLICITUD_CREDITO_CONSUMO_NOMBRE_TAREA_M1"));
			respuestaService = callRestServiceBack.proxyGestionCreditoConsumo(gestionCreditoConsumoRQ,
					propiedades.getPropiedades(), ConstantesLI.SERVICIO_OFERTA_DIGITAL_AVANZAR_SOL);
		} else if (gestionCreditoConsumoRQ.getPaso().equalsIgnoreCase(ConstantesLI.PASO3)) {
			gestionCreditoConsumoRQ
					.setNombreTarea(propiedades.getValue("PROXY_GESTION_SOLICITUD_CREDITO_CONSUMO_NOMBRE_TAREA_M2"));
			gestionCreditoConsumoRQ.setNombreArchivoTraza(
					propiedades.getValue("PROXY_GESTION_SOLICITUD_CREDITO_CONSUMO_NOMBRE_ARCHIVO_TRAZA"));

			respuestaService = callRestServiceBack.proxyGestionCreditoConsumo(gestionCreditoConsumoRQ,
					propiedades.getPropiedades(), ConstantesLI.SERVICIO_OFERTA_DIGITAL_AVANZAR_SOL);
		}
		respuestaService.setTokenActual(sesion.getTokenNuevo());

		param.remove(ConstantesLI.DB_PASO);
		param.put(ConstantesLI.DB_PASO, gestionCreditoConsumoRQ.getPasoFuncional() + "_Bizagi");
		this.guardarDatosDB(param, gson.toJson(respuestaService));

		return respuestaService;
	}

	/**
	 * Metodo para ver las tarjetas de acuerdo al monto solicitado.
	 * 
	 * @param encrypter
	 * @param ipCliente
	 * @return
	 * @throws ExceptionLI
	 */
	public GestionCreditoConsumoRS verTarjetas(String encrypter, String ipCliente) throws ExceptionLI {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRQ gestionCreditoConsumoRQ = new GestionCreditoConsumoRQ();
		param = new HashMap<>();

		try {
			// carga util
			gestionCreditoConsumoRQ = gson.fromJson(encrypter, GestionCreditoConsumoRQ.class);
			ThreadContext.put(ConstantesLI.TXT_SESION_ID, gestionCreditoConsumoRQ.getIdSesion());
			ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);

			param.put(ConstantesLI.DB_ID_SESION, gestionCreditoConsumoRQ.getIdSesion());
			param.put(ConstantesLI.DB_PASO, gestionCreditoConsumoRQ.getPasoFuncional());
			this.guardarDatosDB(param, encrypter);

			OfertaDigital ofertaDigital = consultaApiGestionCreditoConsumo(gestionCreditoConsumoRQ.getIdSesion(),
					gestionCreditoConsumoRQ.getNumeroSolicitud(), gestionCreditoConsumoRQ.getPaso(), false);
			respuestaService.setNumeroSolicitud(gestionCreditoConsumoRQ.getNumeroSolicitud());

			if (ofertaDigital != null) {
				if (gestionCreditoConsumoRQ.getCupoElegido() == null) {
					respuestaService.setProductosTarjeta(util.obtenerTarjetas(ofertaDigital,
							gestionCreditoConsumoRQ.getProducto().get(0).getCupoElegido().toString(), propiedades,
							gestionCreditoConsumoRQ.getProducto().get(0).getIdProducto()));

				} else {
					respuestaService.setProductosTarjeta(
							util.obtenerTarjetas(ofertaDigital, gestionCreditoConsumoRQ.getCupoElegido().toString(),
									propiedades, gestionCreditoConsumoRQ.getIdProducto()));
				}

				respuestaService.setProductosTarjeta(
						util.filtrarCompatibilidadTDC(respuestaService, propiedades, gestionCreditoConsumoRQ));
				util.mapeoFechasPago(respuestaService);

				param.remove(ConstantesLI.DB_PASO);
				param.put(ConstantesLI.DB_PASO, gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_R);
				this.guardarDatosDB(param, gson.toJson(respuestaService));
			} else {
				this.mapearMensajeError(respuestaService, "GCC-100");
				param.remove(ConstantesLI.DB_PASO);
				param.put(ConstantesLI.DB_PASO, gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
				this.guardarDatosDB(param, gson.toJson(respuestaService));
			}
		} catch (ExceptionLI e) {
			logger.error("Error solicitudCreditoConsumo.", e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuestaService.setCodError(e.getCodigo());
			respuestaService.setDescError(e.getMensaje());
			this.mapearMensajeError(respuestaService, e.getCodigo());
			this.guardarDatosDB(param, gson.toJson(respuestaService));
		} catch (Exception e) {
			logger.error("Error SolicitudCreditoConsumo", e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, gestionCreditoConsumoRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuestaService.setDescError(e.getMessage());
			this.mapearMensajeError(respuestaService, ConstantesLI.ERROR_GCC_500);
			this.guardarDatosDB(param, gson.toJson(respuestaService));
		}
		return respuestaService;
	}

	/**
	 * Metodo para guardar datos en DB segun lo requerido desde el front, aplica
	 * solo para algunos pasos funcionales
	 * 
	 * @param encrypter Carga util que se va a guardar
	 * @param ipCliente Ip desde donde se realiza el consumo
	 * @return Confirmacion de ejecucion del servicio, siempre Finalizado OK
	 * @throws ExceptionLI control de alguna excepcion
	 */
	public String finalizarExp(String encrypter, String ipCliente) throws ExceptionLI {
		libreInversion = new LibreInversion();
		param = new HashMap<>();
		try {
			ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
			libreInversion = gson.fromJson(encrypter, LibreInversion.class);

			param.put(ConstantesLI.DB_ID_SESION, libreInversion.getInformacionTransaccion().getIdSesion());
			param.put(ConstantesLI.DB_PASO, libreInversion.getInformacionTransaccion().getPasoFuncional());
			if (libreInversion.getInformacionCredito() != null) {
				libreInversion.getInformacionCredito().setDocumento(null);
			}
			this.guardarDatosDB(param, gson.toJson(libreInversion));
		} catch (JsonSyntaxException e) {
			logger.error("Error solicitudCreditoConsumo ", e);
			throw new ExceptionLI("500", e.getMessage(), e);
		}
		return "Finalizado OK";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void guardarDatosDB(Map conf, String cargaUtil) {
		try {
            conf.put(ConstantesLI.DB_ESQUEMA, ConstantesLI.ESQUEMA_DB_NAME);
            conf.put(ConstantesLI.DB_TABLA, "JSONLI");
			ThreadContext.put("sessionId", conf.get(ConstantesLI.DB_ID_SESION).toString());
			String resp = callRestServiceBack.guardarDatosDB(conf, cargaUtil, propiedades.getValue(ConstantesLI.ENDPOINT_PERSISTENCE));

			mensaje = "Respuesta Guardar " + resp + " : Paso: " + conf.get(ConstantesLI.DB_PASO).toString()
					+ " idSession: " + conf.get(ConstantesLI.DB_ID_SESION).toString();
			logger.info(mensaje);
		} catch (Exception e) {
			logger.error("Error Guardar ", e);
		}
	}

	/**
	 * Metodo encargado de preparar los datos necesarios para enviar al correo
	 * electronico del cliente la informacion de su credito.
	 * 
	 * @param encrypter String con la informacion para enviar el correo.
	 * @return int con el codigo de la respuesta del servicio de envio de correos
	 * @throws ExceptionLI ExceptionLI
	 */
	public String[] enviarCorreo(String authorization, String encrypter, String ipCliente) throws ExceptionLI {
		Map<String, Object> datosCorreo = new java.util.HashMap<>();
		Response res = null;
		EnvioCorreoDto datosEnvioCorreo = new EnvioCorreoDto();
		String[] respuesta = new String[ConstantesLI.DOS];
		param = new HashMap<>();
		AutenticacionRS sesion = new AutenticacionRS();
		try {
			datosEnvioCorreo = gson.fromJson(encrypter, EnvioCorreoDto.class);
			ThreadContext.put(ConstantesLI.TXT_SESION_ID, datosEnvioCorreo.getIdSesion());
			ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);

			param.put(ConstantesLI.DB_ID_SESION, datosEnvioCorreo.getIdSesion());
			param.put(ConstantesLI.DB_PASO, datosEnvioCorreo.getPasoFuncional());

			this.guardarDatosCorreoDB(datosEnvioCorreo);

			this.libreInversion = new LibreInversion();
			this.libreInversion.setInformacionTransaccion(new InformacionTransaccionDto());
			this.libreInversion.getInformacionTransaccion().setPasoFuncional(datosEnvioCorreo.getPasoFuncional());
			this.libreInversion.getInformacionTransaccion().setIdSesion(datosEnvioCorreo.getIdSesion());

			sesion = this.validarSesion(authorization, datosEnvioCorreo.getPasoFuncional(),
					datosEnvioCorreo.getIdSesion());

			logger.info("Respuesta Envio correo Session %s - %s - %s", sesion.getTokenNuevo(),
					datosEnvioCorreo.getIdSesion(), ipCliente);

			prepararDatosEnvioCorreo(datosCorreo, datosEnvioCorreo);
			res = callRestServiceBack.enviarCorreo(datosCorreo,
					propiedades.getValue(ConstantesLI.API_ENDPOINT_ENVIO_CORREO));

			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, datosEnvioCorreo.getPasoFuncional() + ConstantesLI.RESPUESTA_R);
			respuesta[0] = String.valueOf(res.getStatus());
			respuesta[1] = sesion.getTokenNuevo();
			this.guardarDatosDB(param, gson.toJson(res.getEntity()));
		} catch (ExceptionLI e) {
			logger.error(String.format("Error enviando correo %s", datosEnvioCorreo.getIdSesion()), e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO,
					libreInversion.getInformacionTransaccion().getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			er.setCodError(e.getCodigo());
			er.setDescError(e.getMensaje());
			er.setCodFuncional(e.getCodigoFuncional());
			er.setDescFuncional(e.getMensajeFuncional());
			respuesta[0] = e.getCodigo();
			respuesta[1] = sesion.getTokenNuevo();
			if (Arrays.asList(ConstantesLI.getExcepcionesSession()).contains(e.getCodigo())) {
				mapearMensajeError(er, e.getCodigo());
			}
			this.guardarDatosDB(param, gson.toJson(er));
		} catch (Exception e) {
			logger.error(String.format("Error enviando correo %s", datosEnvioCorreo.getIdSesion()), e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO,
					libreInversion.getInformacionTransaccion().getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			er.setCodError("500");
			er.setDescError(e.getMessage());
			respuesta[0] = er.getCodError();
			respuesta[1] = sesion.getTokenNuevo();
			this.guardarDatosDB(param, gson.toJson(er));
		}
		return respuesta;
	}

	/**
	 * Método para guardar en base de datos la información de los documentos de
	 * bienvenida del cliente.
	 *
	 * @param datosEnvioCorreo EnvioCorreoDto información del cliente
	 */
	private void guardarDatosCorreoDB(EnvioCorreoDto datosEnvioCorreo) {
		EnvioCorreoDto envioCorreoDto = new EnvioCorreoDto();
		envioCorreoDto.setIdSesion(datosEnvioCorreo.getIdSesion());
		envioCorreoDto.setNombreCliente(datosEnvioCorreo.getNombreCliente());
		envioCorreoDto.setDocumentoCliente(datosEnvioCorreo.getDocumentoCliente());
		envioCorreoDto.setCorreo(datosEnvioCorreo.getCorreo());
		envioCorreoDto.setDocumentoPdf(null);
		envioCorreoDto.setCupoSolicitado(datosEnvioCorreo.getCupoSolicitado());
		envioCorreoDto.setNombreProducto(datosEnvioCorreo.getNombreProducto());
		envioCorreoDto.setPasoFuncional(datosEnvioCorreo.getPasoFuncional());
		envioCorreoDto.setCuentaDebito(datosEnvioCorreo.getCuentaDebito());
		envioCorreoDto.setNumeroCredito(datosEnvioCorreo.getNumeroCredito());
		envioCorreoDto.setProductoTarjeta(datosEnvioCorreo.getProductoTarjeta());
		envioCorreoDto.setCreaTarjeta(datosEnvioCorreo.getCreaTarjeta());
		this.guardarDatosDB(param, gson.toJson(envioCorreoDto));
	}

	/**
	 * Método que construye los datos necesarios para la plantilla de bienvenida.
	 * 
	 * @param datosCorreo      Map<String, Object>
	 * @param datosEnvioCorreo EnvioCorreoDto
	 */
	private void prepararDatosEnvioCorreo(Map<String, Object> datosCorreo, EnvioCorreoDto datosEnvioCorreo) {
		DecimalFormat formatNum = new DecimalFormat("#,###");
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(ConstantesLI.DOS);

		List<List<String>> listBeneficios = new ArrayList<>();
		List<String> codigosImagen = new ArrayList<>();
		List<String> nombresTarjetas = new ArrayList<>();
		List<String> cuposTarjetas = new ArrayList<>();

		datosCorreo.put(ConstantesGeneracionPDF.DOCUMENTO_PDF, datosEnvioCorreo.getDocumentoPdf());
		datosCorreo.put(ConstantesGeneracionPDF.EMAIL, datosEnvioCorreo.getCorreo());
		datosCorreo.put(ConstantesGeneracionPDF.NOMBRE_CLIENTE, datosEnvioCorreo.getNombreCliente());
		datosCorreo.put(ConstantesGeneracionPDF.NO_DOCUMENTO, datosEnvioCorreo.getDocumentoCliente());
		datosCorreo.put(ConstantesGeneracionPDF.RUTA_PLANTILLA_CORREO,
				ConstantesGeneracionPDF.RUTA_PLANTILLA_TDC_SUCURSALES);
		datosCorreo.put(ConstantesGeneracionPDF.ASUNTO_CORREO, ConstantesGeneracionPDF.ASUNTO_TDC);
		datosCorreo.put(ConstantesGeneracionPDF.IMAGENES, ConstantesGeneracionPDF.IMAGENES_CORREO_TDC);
		datosCorreo.put(ConstantesGeneracionPDF.CREA_TARJETA, datosEnvioCorreo.getCreaTarjeta());

		// recorre informacion de tarjetas seleccionadas
		List<ProductoCreditoDto> productList = datosEnvioCorreo.getProductoTarjeta();
		for (ProductoCreditoDto productoCreditoDto : productList) {
			listBeneficios.add(productoCreditoDto.getListaCondiciones());
			codigosImagen.add(productoCreditoDto.getCodigoImagen());
			nombresTarjetas.add(productoCreditoDto.getNombreSubProducto());
			cuposTarjetas.add(formatNum.format(productoCreditoDto.getCupoElegido()));
		}
		datosCorreo.put(ConstantesGeneracionPDF.CUPO_SOLICITADO, cuposTarjetas);
		datosCorreo.put(ConstantesGeneracionPDF.NOMBRE_PRODUCTO, nombresTarjetas);
		datosCorreo.put(ConstantesGeneracionPDF.BENEFICIOS, listBeneficios);
		datosCorreo.put(ConstantesGeneracionPDF.CODIGO_IMAGEN, codigosImagen);
	}

	public ErrorResponseDTO consultarMensajeError(String codError) {
		ErrorResponseDTO respuesta = new ErrorResponseDTO();

		if (propiedades.getValue("VAL_API_ERRORES_ACTIVO").equalsIgnoreCase(ConstantesLI.TRUE)) {
			ErrorRequestDTO errorRequestDTO = new ErrorRequestDTO();
			errorRequestDTO.setIdAplicacion(propiedades.getValue("VAL_API_ERRORES_COD_APLICACION"));
			errorRequestDTO.setCodigoInterno(codError);
			respuesta = callRestServiceBack.errorService(errorRequestDTO,
					propiedades.getValue(ConstantesLI.API_ENDPOINT_ERROR_BYCODE),
					propiedades.getValue(ConstantesLI.MSG_DEFAULT_API));
		} else {
			respuesta.setCodigoError(codError);
			respuesta.setDescripcionTecnica("No se tiene activo el consumo al API Errores");
			MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
			mensajeFuncional.setCodigoFuncional(codError);
			mensajeFuncional.setDescripcionFuncional(propiedades.getValue(ConstantesLI.MSG_DEFAULT_API));
			List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
			listaMensajes.add(mensajeFuncional);
			respuesta.setMensajeFuncional(listaMensajes);
		}
		return respuesta;
	}

	/**
	 * Método que mapea los mensajes de error para ser mostrados en pantalla y en
	 * el reporte funcional.
	 * 
	 * @param respuesta   ErrorProxyComun
	 * @param codigoError String
	 */
	private void mapearMensajeError(ErrorProxyComun respuesta, String codigoError) {
		ErrorResponseDTO res = this.consultarMensajeError(codigoError);
		respuesta.setCodError(res.getCodigoError());
		String descErrorExc = respuesta.getDescError() == null ? "" : " (" + respuesta.getDescError() + ")";
		respuesta
				.setDescError(res.getDescripcionTecnica() + " (" + res.getMensajeFuncional().get(0).getCodigoFuncional()
						+ "-" + res.getMensajeFuncional().get(0).getDescripcionFuncional() + ")" + " (" + codigoError
						+ ")" + descErrorExc);
		respuesta.setCodFuncional(res.getMensajeFuncional().get(0).getCodigoFuncional());
		respuesta.setDescFuncional(res.getMensajeFuncional().get(0).getDescripcionFuncional());
		respuesta.setOperacion(res.getOperacion());
		respuesta.setServicio(res.getServicio());
	}

	/**
	 * Método que mapea los mensajes que viene de la base de datos para Tarjeta de
	 * credito.
	 * 
	 * @param pasoFun     paso funcional
	 * @param respuesta   objeto ErrorProxyComun
	 * @param codigoError codigoError
	 */
	private void mapearMensajeErrorEmisionTarjeta(String pasoFun, ErrorProxyComun respuesta, String codigoError)
			throws ExceptionLI {
		ErrorResponseDTO res = this.consultarMensajeError(codigoError);
		respuesta.setCodError(res.getCodigoError());
		respuesta.setDescError(ConstantesLI.MSJ_ERROR_CONTROLADO_EMISION);
		respuesta.setCodFuncional(res.getMensajeFuncional().get(0).getCodigoFuncional());
		respuesta.setDescFuncional(res.getMensajeFuncional().get(0).getDescripcionFuncional());
		respuesta.setOperacion(res.getOperacion());
		respuesta.setServicio(res.getServicio());

		param.remove(ConstantesLI.DB_PASO);
		param.put(ConstantesLI.DB_PASO, pasoFun + ConstantesLI.RESPUESTA_R);
		this.guardarDatosDB(param, gson.toJson(respuesta));
	}

	/**
	 * Método para descargar los documentos de bienvenida.
	 * 
	 * @param authorization String
	 * @param encrypter     String con la información del crédito y del cliente.
	 * @return String codificado en base 64 con los documentos de bienvenida.
	 */
	public String[] descargarDocumentosBienvenida(String authorization, String encrypter, String ipCliente)
			throws ExceptionLI {
		Map<String, Object> datosCorreo = new java.util.HashMap<>();
		String[] respuesta = new String[2];
		EnvioCorreoDto datosEnvioCorreo = new EnvioCorreoDto();
		param = new HashMap<>();

		try {
			datosEnvioCorreo = gson.fromJson(encrypter, EnvioCorreoDto.class);
			ThreadContext.put(ConstantesLI.TXT_SESION_ID, datosEnvioCorreo.getIdSesion());
			ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
			param.put(ConstantesLI.DB_ID_SESION, datosEnvioCorreo.getIdSesion());
			param.put(ConstantesLI.DB_PASO, datosEnvioCorreo.getPasoFuncional());
			this.guardarDatosCorreoDB(datosEnvioCorreo);

			this.libreInversion = new LibreInversion();
			this.libreInversion.setInformacionTransaccion(new InformacionTransaccionDto());
			this.libreInversion.getInformacionTransaccion().setPasoFuncional(datosEnvioCorreo.getPasoFuncional());
			this.libreInversion.getInformacionTransaccion().setIdSesion(datosEnvioCorreo.getIdSesion());

			AutenticacionRS sesion = this.validarSesion(authorization, datosEnvioCorreo.getPasoFuncional(),
					datosEnvioCorreo.getIdSesion());

			logger.info(String.format("Respuesta desscarga documento %s.", sesion.getTokenNuevo()));

			this.prepararDatosEnvioCorreo(datosCorreo, datosEnvioCorreo);
			respuesta[0] = this.crearDocumentos(datosCorreo, propiedades);
			respuesta[1] = sesion.getTokenNuevo();
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, datosEnvioCorreo.getPasoFuncional() + ConstantesLI.RESPUESTA_R);
			this.guardarDatosCorreoDB(datosEnvioCorreo);
		} catch (ExceptionLI e) {
			logger.error("ExceptionLI descargarDocumentosBienvenida", e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO,
					libreInversion.getInformacionTransaccion().getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			er.setCodError(e.getCodigo());
			er.setDescError(e.getMensaje());
			if (Arrays.asList(ConstantesLI.getExcepcionesSession()).contains(e.getCodigo())) {
				mapearMensajeError(er, e.getCodigo());
			}
			this.guardarDatosCorreoDB(datosEnvioCorreo);
		}
		return respuesta;
	}

	/**
	 * Método que crea el documento completo con la carta de bienvenida y el
	 * pagaré.
	 * 
	 * @param datos       Map<String, Object>, con los datos necesarios para crear
	 *                    la plantilla de bienvenida y el pagaré.
	 * @param propiedades CargadorPropiedades.
	 * @return String codificado en base 64 con los documentos de bienvenida.
	 */
	public String crearDocumentos(Map<String, Object> datos, CargadorPropiedades propiedades) {
		File archivoFinal = null;
		String fileExtention = "pdf";
		String documento = "";

		List<Parametro> listaParametros = new ArrayList<>();
		listaParametros.add(new Parametro(ConstantesGeneracionPDF.PARA, datos.get(ConstantesGeneracionPDF.EMAIL)));
		listaParametros
				.add(new Parametro(ConstantesGeneracionPDF.NOMBRE, datos.get(ConstantesGeneracionPDF.NOMBRE_CLIENTE)));

		// Se ingresan los parametros que vienen del map
		for (Entry<String, Object> dato : datos.entrySet()) {
			if (ConstantesGeneracionPDF.RUTA_PLANTILLA_CORREO.equals(dato.getKey())) {
				listaParametros.add(new Parametro(dato.getKey(), dato.getValue().toString()));
			} else if (ConstantesGeneracionPDF.ASUNTO_CORREO.equals(dato.getKey())) {
				listaParametros.add(new Parametro(dato.getKey(), dato.getValue().toString()));
			} else if (ConstantesGeneracionPDF.IMAGENES.equals(dato.getKey())) {
				listaParametros.add(new Parametro(dato.getKey(), dato.getValue().toString()));
			} else if (ConstantesGeneracionPDF.DOCUMENTO_PDF.equals(dato.getKey())) {
				documento = dato.getValue().toString();
			} else {
				listaParametros.add(new Parametro(dato.getKey(), dato.getValue()));
			}
		}

		File cartaBienvenida = null;
		File transforDocs = null;
		List<File> listFiles = new ArrayList<>();
		if ((null != datos && !datos.isEmpty()) || null != propiedades) {
			PlantillasUtil plantillasUtil = new PlantillasUtil(listaParametros, propiedades);
			plantillasUtil.createFile();
			cartaBienvenida = plantillasUtil.getFilePdf();
			listFiles.add(cartaBienvenida);
		}

		if (null != documento && !"".equals(documento)) {
			transforDocs = util.transformStringB64ToFile(documento, fileExtention);
			listFiles.add(transforDocs);
		}

		archivoFinal = util.mergePDFFiles(listFiles, fileExtention);

		byte[] fileContent = null;
		try {
			fileContent = FileUtils.readFileToByteArray(archivoFinal);
			Files.delete(transforDocs.toPath());
			if (null != cartaBienvenida) {
				Files.delete(cartaBienvenida.toPath());
			}
			if (null != archivoFinal) {
				Files.delete(archivoFinal.toPath());
			}
		} catch (IOException e) {
			logger.error("Error creando documentos de bienvenida", e);
		}
		return java.util.Base64.getEncoder().encodeToString(fileContent);
	}

	public ValidacionClaveRS validacionClave(String authorization, String encrypter, String ipCliente)
			throws ExceptionLI {
		ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
		mensaje = "";
		ValidacionClaveRS respuesta = new ValidacionClaveRS();
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		param = new HashMap<>();

		try {

			validacionClaveRQ = gson.fromJson(encrypter, ValidacionClaveRQ.class);
			ThreadContext.put(ConstantesLI.TXT_SESION_ID, validacionClaveRQ.getIdSesion());

			param.put(ConstantesLI.DB_ID_SESION, validacionClaveRQ.getIdSesion());
			param.put(ConstantesLI.DB_PASO, validacionClaveRQ.getPasoFuncional());
			this.guardarDatosDB(param, encrypter);
			mensaje = "Inicio validacionClave " + validacionClaveRQ.getIdSesion();
			logger.log(Level.INFO, mensaje);

			this.libreInversion = new LibreInversion();
			this.libreInversion.setInformacionTransaccion(new InformacionTransaccionDto());
			this.libreInversion.getInformacionTransaccion().setPasoFuncional(validacionClaveRQ.getPasoFuncional());
			this.libreInversion.getInformacionTransaccion().setIdSesion(validacionClaveRQ.getIdSesion());

			AutenticacionRS sesion = this.validarSesion(authorization, validacionClaveRQ.getPasoFuncional(),
					validacionClaveRQ.getIdSesion());
			respuesta.setTokenActual(sesion.getTokenNuevo());

			// validacion de token con autenticarClienteOTP o autenticarClienteOTPSoftoken
			if (ConstantesLI.VAR_CLAVE_OTPODA.equalsIgnoreCase(validacionClaveRQ.getMecanismo())) {
				llamadoValidacionODA(validacionClaveRQ, respuesta, ipCliente);
			} else if (ConstantesLI.OTP_ATALLA.equalsIgnoreCase(validacionClaveRQ.getMecanismo())) {
				llamadoValidacionToken(validacionClaveRQ, respuesta, ipCliente);
			} else { // ConstantesLI.VAR_CLAVE_SOFTOKEN
				llamadoValidacionSoftoken(validacionClaveRQ, respuesta, ipCliente);
			}

			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, validacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_R);
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (ExceptionLI e) {
			mensaje = "Error ExceptionLI validacionClave ::" + e.getMensaje();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, validacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError(e.getCodigo());
			respuesta.setDescError(e.getMensaje());
			if (Arrays.asList(ConstantesLI.getExcepcionesSession()).contains(e.getCodigo())) {
				mapearMensajeError(respuesta, e.getCodigo());
			}else{
				mapearMensajeError(respuesta, "CVT-" + e.getCodigo());
			}
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (Exception e) {
			// se usa exepcion generica ya que se realiza llamado a servicios banco y pueden
			// generarse algunas otras excepciones no contempladas en el proceso
			mensaje = "Error Exception validacion clave" + validacionClaveRQ.getIdSesion();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, validacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("CLA-500");
			respuesta.setDescError(e.getMessage());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		}
		return respuesta;
	}

	private void llamadoGeneracionODA(GeneracionClaveRQ generacionClaveRQ, GeneracionClaveRS respuesta,
			String ipCliente) {
		try {
			ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
			ServicioGenerarOTP servicioGenerarOTP = new ServicioGenerarOTP(generacionClaveRQ.getIdSesion(),
					propiedades.getPropiedades(), ipCliente, null);
			AutenticacionOTPRQ autenticacionOTPRQ = new AutenticacionOTPRQ();
			autenticacionOTPRQ.setIdentificacionCanal(propiedades.getValue(ConstantesLI.CLAVE_IDENTIFICACION_CANAL));
			autenticacionOTPRQ.setIdSesion(generacionClaveRQ.getIdSesion());
			autenticacionOTPRQ.setSharedKey(util.generarSharedKey(generacionClaveRQ.getTipoDocumento(),
					generacionClaveRQ.getNumeroDocumento()));
			autenticacionOTPRQ.setInformacionMensaje(new InformacionMensajeType());
			autenticacionOTPRQ.getInformacionMensaje().setDestinatario(new Destinatario());
			autenticacionOTPRQ.getInformacionMensaje().setTipoMensaje(util.buscarTipoMensaje(generacionClaveRQ));
			autenticacionOTPRQ.getInformacionMensaje().getDestinatario()
					.setTipoDocumento(generacionClaveRQ.getTipoDocumento());
			autenticacionOTPRQ.getInformacionMensaje().getDestinatario()
					.setNumeroDocumento(generacionClaveRQ.getNumeroDocumento());
			autenticacionOTPRQ.getInformacionMensaje().getDestinatario()
					.setCelular(autenticacionOTPRQ.getInformacionMensaje().getTipoMensaje()
							.equalsIgnoreCase(ConstantesLI.TIPO_MENSAJE_M) ? generacionClaveRQ.getCelular() : null);
			autenticacionOTPRQ.getInformacionMensaje().getDestinatario()
					.setCorreoElectronico(autenticacionOTPRQ.getInformacionMensaje().getTipoMensaje().equalsIgnoreCase(
							ConstantesLI.TIPO_MENSAJE_C) ? generacionClaveRQ.getCorreoElectronico() : null);
			autenticacionOTPRQ.getInformacionMensaje().getDestinatario().setNombres(generacionClaveRQ.getNombres());

			autenticacionOTPRQ.getInformacionMensaje()
					.setCodigoOperacion(propiedades.getValue(ConstantesLI.CLAVE_COD_OPERACION));
			autenticacionOTPRQ.getInformacionMensaje().setAsunto(propiedades.getValue(ConstantesLI.CLAVE_ASUNTO));
			autenticacionOTPRQ.getInformacionMensaje()
					.setContenidoMensaje(propiedades.getValue(ConstantesLI.CLAVE_CONTENIDO_MENSAJE));
			autenticacionOTPRQ.getInformacionMensaje()
					.setCodigoAplicacion(propiedades.getValue(ConstantesLI.CLAVE_COD_APLICACION));
			mensaje = "Datos para enviarOTP: " + gson.toJson(autenticacionOTPRQ);
			logger.debug(mensaje);

			GenerarEnviarOTPODAResponse respuestaGenEnviar = servicioGenerarOTP.enviarOTP(autenticacionOTPRQ);
			respuesta.setOtp(respuestaGenEnviar.getOtp());
		} catch (com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.enlace.v2.BusinessExceptionMsg e) {
			mensaje = "Error BusinessExceptionMsg" + generacionClaveRQ.getIdSesion();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, generacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("CGOO-" + e.getFaultInfo().getGenericException().getCode());
			respuesta.setDescError(e.getFaultInfo().getGenericException().getDescription());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.enlace.v2.SystemExceptionMsg e) {
			mensaje = "Error SystemExceptionMsg::" + generacionClaveRQ.getIdSesion();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, generacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("CGOO-" + e.getFaultInfo().getGenericException().getCode());
			respuesta.setDescError(e.getMessage());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (Exception e) {
			// se usa exepcion generica ya que se realiza llamado a servicios banco y pueden
			// generarse algunas otras excepciones no contempladas en el proceso
			mensaje = "Error Exception GenerarEnviarOTPODA" + generacionClaveRQ.getIdSesion();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, generacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("CGOO-500");
			respuesta.setDescError(e.getMessage());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		}
	}

	private void llamadoGeneracionToken(GeneracionClaveRQ generacionClaveRQ, GeneracionClaveRS respuesta,
			String ipCliente) throws ExceptionLI {
		try {
			ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);

			GeneracionTokenRQ generacionTokenRQ = new GeneracionTokenRQ();
			generacionTokenRQ.setIdServidor(propiedades.getValue(ConstantesLI.TOKEN_ID_SERVIDOR));
			generacionTokenRQ.setIdSistemaFuente(propiedades.getValue(ConstantesLI.TOKEN_SISTEMA_FUENTE));
			generacionTokenRQ.setTipoDocumento(generacionClaveRQ.getTipoDocumento());
			generacionTokenRQ.setNumeroDocumento(generacionClaveRQ.getNumeroDocumento());
			if (StringUtils.isNotBlank(generacionClaveRQ.getCelular())) {
				generacionTokenRQ.setCelular(new BigInteger(generacionClaveRQ.getCelular()));
			} else {
				throw new ExceptionLI("901", "Los datos de cliente no son válidos para el firmado por Token(ATALLA)");
			}
			generacionTokenRQ.setCorreoElectronicoCliente(generacionClaveRQ.getCorreoElectronico());
			generacionTokenRQ.setGeneraTokenCifrado(propiedades.getValue(ConstantesLI.TOKEN_GENERACION_TOKEN_CIFRADO));
			generacionTokenRQ.setEnviarToken(propiedades.getValue(ConstantesLI.TOKEN_ENVIAR_TOKEN));
			generacionTokenRQ.setIdSesion(generacionClaveRQ.getIdSesion());

			// Cambiar el proxy
			ServicioGeneracionToken servicioGeneracionToken = new ServicioGeneracionToken(
					generacionClaveRQ.getIdSesion(), propiedades.getPropiedades(), ipCliente, null);
			SolicitarTokenResponse respGenToken = servicioGeneracionToken.solicitarToken(generacionTokenRQ);
			respuesta.setToken(respGenToken.getTokenGenerado());
			respuesta.setEstadoSolicitud(respGenToken.getRespuesta().getCodigoRespuesta() + " - "
					+ respGenToken.getRespuesta().getDescripcionRespuesta());

		} catch (com.grupobancolombia.intf.canal.movil.generaciontoken.enlace.v1.SystemExceptionMsg e) {
			mensaje = "Error SystemExceptionMsg: " + generacionClaveRQ.getIdSesion();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, generacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("CGT-" + e.getFaultInfo().getGenericException().getCode());
			respuesta.setDescError(e.getFaultInfo().getGenericException().getDescription());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (com.grupobancolombia.intf.canal.movil.generaciontoken.enlace.v1.BusinessExceptionMsg e) {
			mensaje = "Error BusinessExceptionMsg: " + generacionClaveRQ.getIdSesion();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, generacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("CGT-" + e.getFaultInfo().getGenericException().getCode());
			respuesta.setDescError(e.getFaultInfo().getGenericException().getDescription());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (ExceptionLI e) {
			mensaje = "Error ExceptionLI llamadoGeneracionToken ::" + e.getMensaje();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, generacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError(e.getCodigo());
			respuesta.setDescError(e.getMensaje());
			mapearMensajeError(respuesta, "CGT-" + e.getCodigo());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (Exception e) {
			// se usa exepcion generica ya que se realiza llamado a servicios banco y pueden
			// generarse algunas otras excepciones no contempladas en el proceso
			mensaje = "Error Exception consultarAuthFuerte" + e.getMessage();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, generacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("CGT-500");
			respuesta.setDescError(e.getMessage());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		}

	}

	private void llamadoValidacionODA(ValidacionClaveRQ validacionClaveRQ, ValidacionClaveRS respuesta,
			String ipCliente) throws ExceptionLI {
		try {
			ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
			ServicioAutenticarClienteOTP service = new ServicioAutenticarClienteOTP(validacionClaveRQ.getIdSesion(),
					propiedades.getPropiedades(), ipCliente, null);
			AutenticarClienteOTPRQ autRQ = new AutenticarClienteOTPRQ();
			autRQ.setSharedKey(util.generarSharedKey(validacionClaveRQ.getTipoDocumento(),
					validacionClaveRQ.getNumeroDocumento()));
			autRQ.setIdentificacionCanal(propiedades.getValue(ConstantesLI.CLAVE_IDENTIFICACION_CANAL));
			autRQ.setIdSesion(validacionClaveRQ.getIdSesion());
			autRQ.setOtp(validacionClaveRQ.getOtp());
			AutenticarClienteOTPODAResponse resAuthODA = service.autenticarClienteOTP(autRQ);

			respuesta.setResultCodeODA(resAuthODA.getResultCode());
			respuesta.setResultDescriptionODA(resAuthODA.getResultDescription());
			respuesta.setIntentosFallidosRestantesODA(resAuthODA.getIntentosFallidosRestantes());

			if (StringUtils.isNotBlank(resAuthODA.getResultCode())) {
				switch (resAuthODA.getResultCode()) {
				case "901": // ok
					break;
				case "902":
					if (!ConstantesLI.UNO_TXT.equalsIgnoreCase(resAuthODA.getIntentosFallidosRestantes())) {
						throw new ExceptionLI("902", "Clave Dinámica incorrecta.");
					} else {
						throw new ExceptionLI("903",
								"903 Clave Dinámica incorrecta. Si fallas el próximo intento será bloqueada");
					}
				case "1021":
					throw new ExceptionLI("1021", "1021 Por tu seguridad, la Clave Dinámica fue bloqueada. "
							+ "Debes cambiar la clave de tu tarjeta en Cajeros Automáticos, PAC o Sucursales Físicas.");
				case "1022":
					throw new ExceptionLI("1022",
							"1022 Superaste el número de intentos permitidos y la Clave dinámica está bloqueada. "
									+ "Debes cambiar la clave de tu tarjeta en Cajeros Automáticos, PAC o Sucursales Físicas.");
				case "803":
					throw new ExceptionLI("803", "Tu clave dinámica ha expirado.");
				default:
					throw new ExceptionLI(resAuthODA.getResultCode(),
							"Codigo de error no conocido llamadoValidacionODA");
				}
			} else {
				throw new ExceptionLI("900", "Sin respuesta de ServicioAutenticarClienteOTP ");
			}

		} catch (com.grupobancolombia.intf.seguridadcorporativa.canales.autenticarclienteotp.enlace.v2.SystemExceptionMsg e) {
			mensaje = "Error SystemExceptionMsg" + validacionClaveRQ.getIdSesion();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, validacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("VCO-" + e.getFaultInfo().getGenericException().getCode());
			respuesta.setDescError(e.getMessage());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (ExceptionLI e) {
			mensaje = "Error ExceptionLI validacionClave ::" + e.getMensaje();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, validacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError(e.getCodigo());
			respuesta.setDescError(e.getMensaje());
			mapearMensajeError(respuesta, "VCO-" + e.getCodigo());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (Exception e) {
			// se usa excepcion generica ya que se realiza llamado a servicios banco y
			// pueden
			// generarse algunas otras excepciones no contempladas en el proceso
			mensaje = "Error Exception validacionClave" + validacionClaveRQ.getIdSesion();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, validacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("VCO-500");
			respuesta.setDescError(e.getMessage());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		}

	}

	private void llamadoValidacionSoftoken(ValidacionClaveRQ validacionClaveRQ, ValidacionClaveRS respuesta,
			String ipCliente) throws ExceptionLI {
		try {
			ThreadContext.put(ConstantesLI.TXT_IP_CLIENTE, ipCliente);
			ServicioAutenticarClienteOTP service = new ServicioAutenticarClienteOTP(validacionClaveRQ.getIdSesion(),
					propiedades.getPropiedades(), ipCliente, null);
			AutenticarClienteOTPRQ autRQ = new AutenticarClienteOTPRQ();
			autRQ.setSharedKey(util.generarSharedKey(validacionClaveRQ.getTipoDocumento(),
					validacionClaveRQ.getNumeroDocumento()));
			autRQ.setIdentificacionCanal(propiedades.getValue(ConstantesLI.CLAVE_IDENTIFICACION_CANAL));
			autRQ.setIdSesion(validacionClaveRQ.getIdSesion());
			autRQ.setOtp(validacionClaveRQ.getOtp());
			AutenticarClienteOTPSoftokenResponse resAuthSFTNK = service.autenticarClienteOTPSoftoken(autRQ);

			respuesta.setResultCodeODA(resAuthSFTNK.getResultCode());
			respuesta.setResultDescriptionODA(resAuthSFTNK.getResultDescription());
			respuesta.setIntentosFallidosRestantesODA(resAuthSFTNK.getIntentosFallidosRestantes());

			if (StringUtils.isNotBlank(resAuthSFTNK.getResultCode())) {
				switch (resAuthSFTNK.getResultCode()) {
				case "801": // ok
					break;
				case "802":
					if (!ConstantesLI.UNO_TXT.equalsIgnoreCase(resAuthSFTNK.getIntentosFallidosRestantes())) {
						throw new ExceptionLI("802", "Clave Dinámica incorrecta.");
					} else {
						throw new ExceptionLI("803",
								"903 Clave Dinámica incorrecta. Si fallas el próximo intento será bloqueada");
					}
				case "1021":
					throw new ExceptionLI("1021", "1021 Por tu seguridad, la Clave Dinámica fue bloqueada. "
							+ "Debes cambiar la clave de tu tarjeta en Cajeros Automáticos, PAC o Sucursales Físicas.");
				case "1022":
					throw new ExceptionLI("1022",
							"1022 Superaste el número de intentos permitidos y la Clave dinámica está bloqueada. "
									+ "Debes cambiar la clave de tu tarjeta en Cajeros Automáticos, PAC o Sucursales Físicas.");
				default:
					throw new ExceptionLI(resAuthSFTNK.getResultCode(),
							"Codigo de error no conocido llamadoValidacionSoftoken");
				}
			} else {
				throw new ExceptionLI("900", "Sin respuesta de ServicioAutenticarClienteOTP ");
			}

		} catch (com.grupobancolombia.intf.seguridadcorporativa.canales.autenticarclienteotp.enlace.v2.SystemExceptionMsg e) {
			mensaje = "Error SystemExceptionMsg llamadoValidacionSoftoken: " + e.getMessage();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, validacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("VCS-" + e.getFaultInfo().getGenericException().getCode());
			respuesta.setDescError(e.getMessage());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (ExceptionLI e) {
			mensaje = "Error ExceptionLI llamadoValidacionSoftoken ::" + e.getMensaje();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, validacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError(e.getCodigo());
			respuesta.setDescError(e.getMensaje());
			mapearMensajeError(respuesta, "VCS-" + e.getCodigo());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		} catch (Exception e) {
			// se usa excepcion generica ya que se realiza llamado a servicios banco y
			// pueden
			// generarse algunas otras excepciones no contempladas en el proceso
			mensaje = "Error Exception llamadoValidacionSoftoken" + validacionClaveRQ.getIdSesion();
			logger.log(Level.ERROR, mensaje, e);
			param.remove(ConstantesLI.DB_PASO);
			param.put(ConstantesLI.DB_PASO, validacionClaveRQ.getPasoFuncional() + ConstantesLI.RESPUESTA_E);
			respuesta.setCodError("VCS-500");
			respuesta.setDescError(e.getMessage());
			mapearMensajeError(respuesta, respuesta.getCodError());
			this.guardarDatosDB(param, gson.toJson(respuesta));
		}

	}

	/**
	 * Metodo para validar la sesion de todos los pasos de la experiencia.
	 * 
	 * @param authorization
	 * @param pasoFuncional
	 * @param idSesion
	 * @return
	 * @throws ExceptionLI
	 */
	private AutenticacionRS validarSesion(String authorization, String pasoFuncional, String idSesion)
			throws ExceptionLI {

		if (this.libreInversion == null) {
			this.libreInversion = new LibreInversion();
		}

		this.libreInversion.setInformacionTransaccion(new InformacionTransaccionDto());
		this.libreInversion.getInformacionTransaccion().setPasoFuncional(pasoFuncional);
		this.libreInversion.getInformacionTransaccion().setIdSesion(idSesion);

		String cargaUtil = gson.toJson(this.libreInversion);

		return autenticacion.validarSesion(authorization, cargaUtil);
	}

	/**
	 * @param autenticacion the autenticacion to set
	 */
	public void setAutenticacion(ServicioAutenticacion autenticacion) {
		this.autenticacion = autenticacion;
	}

}