package bancolombia.dtd.vd.sucursales.tdc.api.datoscliente;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.model.EnrollmentRS;
import org.apache.http.client.HttpResponseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.bcol.vtd.lib.comunes.exception.ConectorClientException;
import com.google.gson.Gson;
import com.grupobancolombia.ents.common.genericexception.v2.GenericException;
import com.grupobancolombia.intf.canal.movil.generaciontoken.v1.Respuesta;
import com.grupobancolombia.intf.canal.movil.generaciontoken.v1.SolicitarTokenResponse;
import com.grupobancolombia.intf.canal.movil.generaciontoken.v1.ValidarTokenResponse;
import com.grupobancolombia.intf.seguridadcorporativa.canales.autenticarclienteotp.v2.AutenticarClienteOTPODAResponse;
import com.grupobancolombia.intf.seguridadcorporativa.canales.autenticarclienteotp.v2.AutenticarClienteOTPSoftokenResponse;
import com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.v2.GenerarEnviarOTPODAResponse;
import com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.v2.GenerarOTPODAResponse;

import bancolombia.dtd.vd.li.api.autenticacion.ServicioAutenticacion;
import bancolombia.dtd.vd.li.commons.exception.ExceptionLI;
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
import bancolombia.dtd.vd.li.dto.proxy.comun.RespuestaServicio;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.GestionCreditoConsumoRQ;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.GestionCreditoConsumoRS;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.OfertaDigital;
import bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.util.CallRestServiceBack;
import bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.util.ConstantesLI;
import suc.lib.autenticarClienteOTP.ServicioAutenticarClienteOTP;
import suc.lib.autenticarClienteOTP.util.AutenticarClienteOTPRQ;
import suc.lib.generacionToken.ServicioGeneracionToken;
import suc.lib.generacionToken.util.GeneracionTokenRQ;
import suc.lib.generarOTP.ServicioGenerarOTP;
import suc.lib.generarOTP.util.AutenticacionOTPRQ;

/**
 * @author cgallego
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ServicioDatosCliente.class })
public class ServicioDatosClienteTest {
	@Mock
	CallRestServiceBack callRestServiceBack;

	private ServicioDatosCliente service;


	@Mock
	ServicioGeneracionToken servicioGeneracionToken;

	@Mock
	ServicioAutenticarClienteOTP servicioAutenticarClienteOTP;

	@Mock
	ServicioGenerarOTP servicioGenerarOTP;

	@Mock
	ServicioAutenticacion servicioAutenticacion;

	@Mock
	private ServicioDatosCliente serviceMail;

	private static Map<String, String> propiedades;

	private AutenticacionRS auth;

	@Before
	public void init() throws ExceptionLI, IOException {
		service = new ServicioDatosCliente();
		service.callRestServiceBack = callRestServiceBack;
		propiedades = Variables.getPropiedades();
		service.propiedades.setPropiedades(propiedades);
		auth = new AutenticacionRS();
		auth.setTokenNuevo("RRRR");
		Mockito.when(callRestServiceBack.guardarDatosDB(Mockito.any(), Mockito.any(), Mockito.anyString())).thenReturn("OK");
		Mockito.when(servicioAutenticacion.validarSesion(Mockito.anyString(), Mockito.anyString())).thenReturn(auth);
		service.setAutenticacion(servicioAutenticacion);
	}

	@Test
	public void testSolicitudCreditoConsumoPaso1() throws Exception, IOException, ConectorClientException {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();

		respuestaService.setIdProceso("4463");
		respuestaService.setNumeroSolicitud("1111111111-4463-14");

		Mockito.when(callRestServiceBack.proxyGestionCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(respuestaService);

		String respuesta = ConstantesTest.RESPUESTAOFERTASOLICITUDCREDITOCONSUMOPASO1;
		OfertaDigital ofertaDigital = new Gson().fromJson(respuesta, OfertaDigital.class);
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);

		respuestaApi = service.crearCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO1,
				"127.0.0.1");
		assertEquals("4463", respuestaApi.getIdProceso());
	}

	@Test
	public void testSolicitudCreditoConsumoPaso2()
			throws ExceptionLI, IOException, ConectorClientException, Exception, RuntimeException {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuesta = new GestionCreditoConsumoRS();

		respuestaService.setIdProceso("4463");
		respuestaService.setNumeroSolicitud("1111111111-4463-14");
		Mockito.when(callRestServiceBack.proxyGestionCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(respuestaService);
		String respuestaJson = ConstantesTest.RESPUESTAOFERTASOLICITUDCREDITOCONSUMOPASO2;
		OfertaDigital ofertaDigital = new Gson().fromJson(respuestaJson, OfertaDigital.class);
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);
		respuesta = service.confirmarCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO2,
				"127.0.0.1");
		assertNotNull(respuesta);
	}

	@Test
	public void testSolicitudCreditoConsumoPaso3()
			throws Exception, IOException, ConectorClientException, RuntimeException {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuesta = new GestionCreditoConsumoRS();

		respuestaService.setIdProceso("4463");
		respuestaService.setNumeroSolicitud("1111111111-4463-14");

		Mockito.when(callRestServiceBack.proxyGestionCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(respuestaService);
		String respuestaJson3 = ConstantesTest.RESPUESTAOFERTASOLICITUDCREDITOCONSUMOPASO3;
		OfertaDigital ofertaDigital = new Gson().fromJson(respuestaJson3, OfertaDigital.class);
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);

		respuesta = service.firmarDocumentosCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO3,
				"127.0.0.1");
		assertNotNull(respuesta);
		assertThat(respuesta.getIdProceso(), is("4463"));
	}

	@Test
	public void testFinalizarExperiencia() throws ExceptionLI, IOException {
		String cargaUtil = "{\"cuentasDisponibles\":[],\"datosCliente\":{\"tipoIdentificacion\":\"FS001\"},\"informacionSucursal\":{\"nombreCompleto\":\"Usuario Prueba\",\"codigoAsesor\":\"1037644654\",\"usuarioRed\":\"IEUSER2\"},\"informacionDispositivo\":{\"deviceBrowser\":\"chrome\",\"deviceOS\":\"windows\",\"sistemaOperativo\":\"windows\",\"userAgent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36\",\"dispositivo\":\"unknown\",\"versionSistemaOperativo\":\"windows-10\",\"ip\":\"127.0.0.1\"},\"informacionCredito\":{\"isDebitoAutomatico\":true,\"isAceptaSeguro\":true,\"isBeneficiarios\":false,\"beneficiario\":[]},\"informacionDocumentos\":{\"aceptaTerminos\":false},\"informacionBiometria\":{\"reintentosValidacionNoexitosa\":\"0\"},\"informacionTransaccion\":{\"pasoFuncional\":\"paso_li_100\",\"idSesion\":\"gjopl9D7oz4Si3AC3xQdEw\",\"fechaHoraTransaccion\":\"\",\"ipCliente\":\"\",\"tokenActual\":\"miTooooooooooooken\"},\"esCrediagil\":false,\"esLibranza\":false,\"esLibreInversion\":false,\"hash\":\"fff22f8a1fc76a173329b8952707e772\"}";

		String respuesta = service.finalizarExp(cargaUtil, "127.0.0.1");
		assertNotNull(respuesta);
		assertThat(respuesta, is("Finalizado OK"));
	}

	@Test(expected = Exception.class)
	public void testFinalizarExperienciaException() throws Exception {
		String cargaUtil = "\"{\"informacionTransaccion\":{\"pasoFuncional\":\"paso4\",\"idSesion\":\"3123123123213\"}}\"";
		Mockito.when(service.finalizarExp(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(new ExceptionLI("500", "Error"));

		service.finalizarExp(cargaUtil, "127.0.0.1");
	}

	@Test
	public void testEnviarCorreoExito() throws ExceptionLI, IOException {
		Response res = Response.status(Status.OK).entity("Lista es null").build();
		Mockito.when(callRestServiceBack.enviarCorreo(Mockito.anyMap(), Mockito.any())).thenReturn(res);
		String[] respuesta = service.enviarCorreo("", ConstantesTest.CARGAUTILENVIOCORREO, "127.0.0.1");

		assertNotNull(respuesta);
		assertThat(respuesta[0], is("200"));
	}

	@Test
	public void testEnviarCorreoFalla() throws ExceptionLI, IOException {
		Response res = Response.status(500).entity("Lista es null").build();
		Mockito.when(callRestServiceBack.enviarCorreo(Mockito.anyMap(), Mockito.any())).thenReturn(res);
		String[] respuesta = service.enviarCorreo("", ConstantesTest.CARGAUTILENVIOCORREO, "127.0.0.1");

		assertNotNull(respuesta);
		assertThat(respuesta[0], is("500"));
	}

	@Test
	public void testEnviarExc() throws ExceptionLI, IOException {
		RespuestaServicio res = new RespuestaServicio();
		res.setCodigo("500");
		res.setDescripcion("Servicio no disponible");
		Mockito.when(callRestServiceBack.enviarCorreo(Mockito.anyMap(), Mockito.any()))
				.thenThrow(new ExceptionLI(res.getCodigo(), res.getDescripcion()));
		String[] respuesta = service.enviarCorreo("", ConstantesTest.CARGAUTILENVIOCORREO, "127.0.0.1");

		assertNotNull(respuesta);
		assertThat(respuesta[0], is("500"));
	}

	@Test
	public void testEnviarExcAuth() throws ExceptionLI, IOException {
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("AUTH-409");
		errorResponseDTO.setDescripcionTecnica("Session invalida");
		mensajeFuncional.setCodigoFuncional("AUTH-409");
		mensajeFuncional
				.setDescripcionFuncional("Inicia una session nuevamente.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);
		Mockito.when(servicioAutenticacion.validarSesion(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(new ExceptionLI("AUTH-409", "Session invalida"));
		String[] respuesta = service.enviarCorreo("", ConstantesTest.CARGAUTILENVIOCORREO, "127.0.0.1");

		assertNotNull(respuesta);
		assertThat(respuesta[0], is("AUTH-409"));
	}

	@Test
	public void testConsultarApiErrores() throws HttpResponseException, IOException, ExceptionLI {
		// Arrange
		ErrorRequestDTO errorRequestDTO = new ErrorRequestDTO();
		errorRequestDTO.setIdAplicacion("4");
		errorRequestDTO.setCodigoInterno("4-ACOACOO902");

		ErrorResponseDTO res = new ErrorResponseDTO();
		res.setCodigoError("902");
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		mensajeFuncional.setCodigoFuncional("4-ACOACOO902");
		mensajeFuncional.setDescripcionFuncional("El cliente no existe.");
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		listaMensajes.add(mensajeFuncional);
		res.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(ErrorRequestDTO.class), Mockito.anyString(),
				Mockito.anyString())).thenReturn(res);

		// Assert
		assertNotNull(res);
		assertEquals("4-ACOACOO902", res.getMensajeFuncional().get(0).getCodigoFuncional());
	}

	@Test
	public void testEnviarCorreoExcLI() throws ExceptionLI, IOException {
		Response res = Response.status(Status.OK).entity("Lista es null").build();
		Mockito.when(callRestServiceBack.enviarCorreo(Mockito.anyMap(), Mockito.anyString())).thenReturn(res);

		String respuestaCorreo[] = service.enviarCorreo("", ConstantesTest.CARGAUTILENVIOCORREO, "127.0.0.1");
		assertNotNull(respuestaCorreo);
	}

	@Test
	public void testEnviarCorreoExc() throws ExceptionLI, Exception {
		Response res = Response.status(Status.OK).entity("Lista es null").build();
		Mockito.when(callRestServiceBack.enviarCorreo(Mockito.anyMap(), Mockito.anyString())).thenReturn(res);
		String respuestaCorreo[] = service.enviarCorreo("", ConstantesTest.CARGAUTILENVIOCORREOEXCEP, "127.0.0.1");
		assertNotNull(respuestaCorreo);
	}

	@Test
	public void testSolicitudCreditoConsumoExcLI() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("GCC-500");
		errorResponseDTO.setDescripcionTecnica("No se encontr� codError en API Errores");
		mensajeFuncional.setCodigoFuncional("GCC-500");
		mensajeFuncional
				.setDescripcionFuncional("En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);
		Mockito.when(servicioAutenticacion.validarSesion(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(new ExceptionLI("GCC-500", "No se encontro codError en API Errores"));
		respuestaApi = service.crearCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO1,
				"127.0.0.1");
		assertThat(respuestaApi.getCodFuncional(), is("GCC-500"));
	}


	@Test
	public void testSolicitudCreditoConsumoExcAutenticacionLI() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("AUTH-409");
		errorResponseDTO.setDescripcionTecnica("Sesion invalida");
		mensajeFuncional.setCodigoFuncional("AUTH-409");
		mensajeFuncional
				.setDescripcionFuncional("Sesion invalida.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);
		Mockito.when(servicioAutenticacion.validarSesion(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(new ExceptionLI("AUTH-409", "Sesion invalida"));
		respuestaApi = service.crearCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO1,
				"127.0.0.1");
		assertThat(respuestaApi.getCodFuncional(), is("AUTH-409"));
	}

	@Test
	public void testVerTarjetasConCicloDefault() throws RuntimeException, IOException, ExceptionLI, Exception {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuesta = new GestionCreditoConsumoRS();

		respuestaService.setIdProceso("4463");
		respuestaService.setNumeroSolicitud("SUC_2020_162711");

		OfertaDigital ofertaDigital = new Gson().fromJson(Constantes.respuestaOfertaVerTarjetas21, OfertaDigital.class);
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);

		respuesta = service.verTarjetas(Constantes.cargaUtilVerTarjetasMasterNegocios, "127.0.0.1");
		assertNull(respuesta.getCodError());
 	}

	@Test
	public void testVerTarjetaJoven() throws ExceptionLI, IOException, ConectorClientException, Exception {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuesta = new GestionCreditoConsumoRS();

		respuestaService.setIdProceso("4463");
		respuestaService.setNumeroSolicitud("1111111111-4463-14");

		OfertaDigital ofertaDigital = new Gson().fromJson(Constantes.respuestaOfertaVerTarjetasJoven,
				OfertaDigital.class);
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);
		respuesta = service.verTarjetas(Constantes.cargaUtilVerTarjetaJoven, "127.0.0.1");
		assertNotNull(respuesta);
		assertThat(respuesta.getNumeroSolicitud(), is("SUC_2020_162611"));
	}

	@Test
	public void testConvenio() {
		try {
			OfertaDigital oferta = new Gson().fromJson(ConstantesTest.DATOS_CON, OfertaDigital.class);
			if (oferta.getOferta().getConvenio() != null) {
				assertThat(oferta.getOferta().getConvenio().getNombreConvenio().trim(), is("CONTACTAMOS SAS"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Test
	public void testSolicitudCreditoConsumoIdProcesEmpty() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();
		propiedades = Variables.getPropiedades();
		service.propiedades.setPropiedades(propiedades);

		respuestaService.setIdProceso("");
		respuestaService.setNumeroSolicitud("1111111111-4463-14");
		respuestaService.setCodError("06");

		Mockito.when(callRestServiceBack.proxyGestionCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(respuestaService);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("GCC-06");
		errorResponseDTO.setDescripcionTecnica("No se encontró codError en API Errores");
		mensajeFuncional.setCodigoFuncional("GCC-06");
		mensajeFuncional
				.setDescripcionFuncional("En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		respuestaApi = service.crearCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO1,
				"127.0.0.1");
		assertThat(respuestaApi.getCodError(), is("GCC-06"));
	}


	@Test
	public void testSolicitudCreditoConsumoTDCExcLI() throws ExceptionLI, IOException {
		ExceptionLI respuestaService = new ExceptionLI("500", "error prueba");
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("GCC-500");
		errorResponseDTO.setDescripcionTecnica("No se encontró codError en API Errores");
		mensajeFuncional.setCodigoFuncional("GCC-500");
		mensajeFuncional
				.setDescripcionFuncional("En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);
		Mockito.when(servicioAutenticacion.validarSesion(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(new ExceptionLI("GCC-500", "No se encontrÃ³ codError en API Errores"));
		respuestaApi = service.crearCreditoConsumo("", ConstantesTest.CARGAUTILOFERTANULL, "127.0.0.1");
		assertThat(respuestaApi.getCodFuncional(), is("GCC-500"));
	}

	@Test
	public void testSolicitudCreditoConsumoTDCOfertaNull()
			throws ExceptionLI, IOException, ConectorClientException, Exception {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();
		propiedades = Variables.getPropiedades();
		service.propiedades.setPropiedades(propiedades);

		respuestaService.setIdProceso("4463");
		respuestaService.setNumeroSolicitud("1111111111-4463-14");

		Mockito.when(callRestServiceBack.proxyGestionCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(respuestaService);

		OfertaDigital ofertaDigital = null;
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("GCC-500");
		errorResponseDTO.setDescripcionTecnica("Falló creando la oferta");
		mensajeFuncional.setCodigoFuncional("GCC-500");
		mensajeFuncional
				.setDescripcionFuncional("En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(ErrorRequestDTO.class), Mockito.anyString(),
				Mockito.anyString())).thenReturn(errorResponseDTO);

		respuestaApi = service.crearCreditoConsumo("", ConstantesTest.CARGAUTILOFERTANULL, "127.0.0.1");
		assertThat(respuestaApi.getCodFuncional(), is("GCC-500"));
	}

	@Test
	public void testSolicitudCreditoConsumoAvanzarOfertaNull()
			throws ExceptionLI, IOException, ConectorClientException, Exception {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();
		propiedades = Variables.getPropiedades();
		service.propiedades.setPropiedades(propiedades);
		respuestaService.setIdProceso("4463");
		respuestaService.setNumeroSolicitud("1111111111-4463-14");

		Mockito.when(callRestServiceBack.proxyGestionCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(respuestaService);

		OfertaDigital ofertaDigital = null;
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("GCC-500");
		errorResponseDTO.setDescripcionTecnica("Falló creando la oferta");
		mensajeFuncional.setCodigoFuncional("GCC-500");
		mensajeFuncional
				.setDescripcionFuncional("En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(ErrorRequestDTO.class), Mockito.anyString(),
				Mockito.anyString())).thenReturn(errorResponseDTO);

		respuestaApi = service.confirmarCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO2,
				"127.0.0.1");
		assertThat(respuestaApi.getCodFuncional(), is("GCC-500"));
	}


	@Test
	public void testSolicitudCreditoConsumoAvanzarOfertaExcepcionAuth()
			throws  Exception {
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("AUTH-409");
		errorResponseDTO.setDescripcionTecnica("Sesion invalida");
		mensajeFuncional.setCodigoFuncional("AUTH-409");
		mensajeFuncional
				.setDescripcionFuncional("Sesion invalida.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);
		Mockito.when(servicioAutenticacion.validarSesion(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(new ExceptionLI("AUTH-409", "Sesion invalida"));

		respuestaApi = service.confirmarCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO2,
				"127.0.0.1");
		assertThat(respuestaApi.getCodFuncional(), is("AUTH-409"));
	}

	@Test
	public void testSolicitudCreditoConsumoConfirmacionTransaccion()
			throws ExceptionLI, IOException, ConectorClientException, Exception {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();
		propiedades = Variables.getPropiedades();
		service.propiedades.setPropiedades(propiedades);

		respuestaService.setIdProceso("4463");
		respuestaService.setNumeroSolicitud("1111111111-4463-14");

		Mockito.when(callRestServiceBack.proxyGestionCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(respuestaService);

		String respuesta = ConstantesTest.RESPUESTAOFERTASOLICITUDCREDITOCONSUMOERROR;
		OfertaDigital ofertaDigital = new Gson().fromJson(respuesta, OfertaDigital.class);
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("GCC-06");
		errorResponseDTO.setDescripcionTecnica("Cerrado consultando cliente");
		mensajeFuncional.setCodigoFuncional("GCC-06");
		mensajeFuncional
				.setDescripcionFuncional("En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(ErrorRequestDTO.class), Mockito.anyString(),
				Mockito.anyString())).thenReturn(errorResponseDTO);

		respuestaApi = service.crearCreditoConsumo("", ConstantesTest.CARGAUTILOFERTANULL, "127.0.0.1");
		assertThat(respuestaApi.getCodFuncional(), is("GCC-06"));
	}

	@Test
	public void testSolicitudCreditoConsumoAvanzarConfirmacionTransaccion()
			throws  Exception {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();
		propiedades = Variables.getPropiedades();
		service.propiedades.setPropiedades(propiedades);

		respuestaService.setIdProceso("4463");
		respuestaService.setNumeroSolicitud("1111111111-4463-14");

		Mockito.when(callRestServiceBack.proxyGestionCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(respuestaService);

		String respuesta = ConstantesTest.RESPUESTAOFERTASOLICITUDAVANZARERROR;
		OfertaDigital ofertaDigital = new Gson().fromJson(respuesta, OfertaDigital.class);
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("CUP0445");
		errorResponseDTO
				.setDescripcionTecnica("No existe OPE preaprobada que cubra monto a desembolsar de este origen ");
		mensajeFuncional.setCodigoFuncional("CUP0445");
		mensajeFuncional
				.setDescripcionFuncional("En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(ErrorRequestDTO.class), Mockito.anyString(),
				Mockito.anyString())).thenReturn(errorResponseDTO);

		respuestaApi = service.confirmarCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO2,
				"127.0.0.1");
		assertThat(respuestaApi.getCodFuncional(), is("CUP0445"));
	}

	@Test
	public void testSolicitudCreditoConsumoAvanzarConfirmacionTransaccionPaso3()
			throws Exception {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();
		propiedades = Variables.getPropiedades();
		service.propiedades.setPropiedades(propiedades);

		respuestaService.setIdProceso("4463");
		respuestaService.setNumeroSolicitud("1111111111-4463-14");

		Mockito.when(callRestServiceBack.proxyGestionCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(respuestaService);

		String respuesta = ConstantesTest.RESPUESTAOFERTASOLICITUDAVANZARERROR;
		OfertaDigital ofertaDigital = new Gson().fromJson(respuesta, OfertaDigital.class);
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("CUP0445");
		errorResponseDTO
				.setDescripcionTecnica("No existe OPE preaprobada que cubra monto a desembolsar de este origen ");
		mensajeFuncional.setCodigoFuncional("CUP0445");
		mensajeFuncional
				.setDescripcionFuncional("En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(ErrorRequestDTO.class), Mockito.anyString(),
				Mockito.anyString())).thenReturn(errorResponseDTO);

		respuestaApi = service.firmarDocumentosCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO3,
				"127.0.0.1");
		assertThat(respuestaApi.getCodFuncional(), is("CUP0445"));
	}
	@Test
	public void testSolicitudCreditoFinalizacionAuthExcLI()
			throws Exception {
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("AUTH-409");
		errorResponseDTO.setDescripcionTecnica("Sesion invalida");
		mensajeFuncional.setCodigoFuncional("AUTH-409");
		mensajeFuncional
				.setDescripcionFuncional("Sesion invalida.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);
		Mockito.when(servicioAutenticacion.validarSesion(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(new ExceptionLI("AUTH-409", "Sesion invalida"));
		respuestaApi = service.firmarDocumentosCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO3,
				"127.0.0.1");
		assertThat(respuestaApi.getCodFuncional(), is("AUTH-409"));
	}


	@Test
	public void testSolicitudCreditoConsumoAvanzarIdProcesEmpty() throws ExceptionLI, IOException {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();
		propiedades = Variables.getPropiedades();
		service.propiedades.setPropiedades(propiedades);
		respuestaService.setIdProceso("");
		respuestaService.setNumeroSolicitud("1111111111-4463-14");
		respuestaService.setCodError("06");

		Mockito.when(callRestServiceBack.proxyGestionCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(respuestaService);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("GCC-06");
		errorResponseDTO.setDescripcionTecnica("No se encontró codError en API Errores");
		mensajeFuncional.setCodigoFuncional("GCC-06");
		mensajeFuncional
				.setDescripcionFuncional("En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(ErrorRequestDTO.class), Mockito.anyString(),
				Mockito.anyString())).thenReturn(errorResponseDTO);

		respuestaApi = service.confirmarCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO2,
				"127.0.0.1");
		assertThat(respuestaApi.getCodError(), is("GCC-06"));
	}

	@Test
	public void testVerTarjetasOfertaNull() throws ExceptionLI, IOException, ConectorClientException, Exception {
		GestionCreditoConsumoRS respuesta = new GestionCreditoConsumoRS();
		propiedades = Variables.getPropiedades();
		service.propiedades.setPropiedades(propiedades);

		OfertaDigital ofertaDigital = null;
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("GCC-100");
		errorResponseDTO.setDescripcionTecnica("No se recuperó oferta para calcular cuota");
		mensajeFuncional.setCodigoFuncional("GCC-100");
		mensajeFuncional
				.setDescripcionFuncional("En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(ErrorRequestDTO.class), Mockito.anyString(),
				Mockito.anyString())).thenReturn(errorResponseDTO);

		respuesta = service.verTarjetas(Constantes.cargaUtilVerTarjetas, "127.0.0.1");
		assertThat(respuesta.getCodError(), is("GCC-100"));
	}

	@Test
	public void testVerTarjetasExcLI() throws ExceptionLI, IOException, ConectorClientException, Exception {
		GestionCreditoConsumoRS respuesta = new GestionCreditoConsumoRS();
		Exception er = new Exception();
		ExceptionLI respuestaError = new ExceptionLI("500", er.getMessage(), er);
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("GCC-" + respuestaError.getCodigo());
		errorResponseDTO.setDescripcionTecnica("No se encontró codError en API Errores");
		mensajeFuncional.setCodigoFuncional("GCC-" + respuestaError.getCodigo());
		mensajeFuncional
				.setDescripcionFuncional("En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);

		Mockito.when(callRestServiceBack.errorService(Mockito.any(ErrorRequestDTO.class), Mockito.anyString(),
				Mockito.anyString())).thenReturn(errorResponseDTO);
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
		respuesta = service.verTarjetas(Constantes.cargaUtilVerTarjetas, "127.0.0.1");
		assertThat(respuesta.getCodFuncional(), is("GCC-" + "500"));
	}

	@Test
	public void testVerTarjetasExc() throws ExceptionLI, IOException {
		propiedades = Variables.getPropiedades();
		service.propiedades.setPropiedades(propiedades);
		GestionCreditoConsumoRS respuesta = new GestionCreditoConsumoRS();
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("GCC-500");
		errorResponseDTO.setDescripcionTecnica("No se encontró codError en API Errores");
		mensajeFuncional.setCodigoFuncional("GCC-500");
		mensajeFuncional
				.setDescripcionFuncional("En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);

		Mockito.when(callRestServiceBack.errorService(Mockito.any(ErrorRequestDTO.class), Mockito.anyString(),
				Mockito.anyString())).thenReturn(errorResponseDTO);
		respuesta = service.verTarjetas(Constantes.cargaUtilVerTarjetasError, "127.0.0.1");
		assertThat(respuesta.getCodFuncional(), is("GCC-500"));
	}

	@SuppressWarnings("static-access")
	@Test
	public void testconsultarAuthFuerte() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		AutenticacionFuerteRQ rqAuth = new AutenticacionFuerteRQ();
		rqAuth.setIdSesion("sadasd21321edsas");
		rqAuth.setNumeroDocumento("12312321");
		rqAuth.setPasoFuncional("paso2");
		rqAuth.setTipoDocumento("FS001");

		EnrollmentRS resAuth = new EnrollmentRS();
		resAuth.setDynamicKeyMechanism("ODA");
		resAuth.setCustomerEmail("correo@prueba.com");
		resAuth.setCustomerMobileNumber("3123123123");
		resAuth.setEnrollmentDate("2022-01-13");
		resAuth.setLastDataModificationDate(null);
		resAuth.setLastMechanismUpdateDate("2022-01-13");
		resAuth.setPushActive("OK");
		resAuth.setAlertType("SMS");
		doReturn(resAuth).when(callRestServiceBack).getEnrollment(any());

		AutenticacionFuerteRS resp = service.consultarAuthFuerte("", new Gson().toJson(rqAuth), "127.0.0.1");

		assertEquals(resAuth.getDynamicKeyMechanism(), resp.getMecanismo());

	}


	@SuppressWarnings("static-access")
	@Test
	public void testconsultarAuthFuerteExceptionLI() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		AutenticacionFuerteRQ rqAuth = new AutenticacionFuerteRQ();
		rqAuth.setIdSesion("sadasd21321edsas");
		rqAuth.setNumeroDocumento("12312321");
		rqAuth.setPasoFuncional("paso2");
		rqAuth.setTipoDocumento("FS001");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("CDCSA-100");
		errorResponseDTO.setDescripcionTecnica("100 Error al consultar el asesor");
		mensajeFuncional.setCodigoFuncional("CDCSA-100");
		mensajeFuncional.setDescripcionFuncional("Asesor no autorizado");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);


		Mockito.when(servicioAutenticacion.validarSesion(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(new ExceptionLI("CDCSA-100", "No autorizado"));

		AutenticacionFuerteRS resp = service.consultarAuthFuerte("", new Gson().toJson(rqAuth), "127.0.0.1");

		assertEquals(errorResponseDTO.getCodigoError(), resp.getCodError());
	}

	@SuppressWarnings("static-access")
	@Test
	public void testconsultarAuthFuerteException() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		AutenticacionFuerteRQ rqAuth = new AutenticacionFuerteRQ();
		rqAuth.setIdSesion("sadasd21321edsas");
		rqAuth.setNumeroDocumento("12312321");
		rqAuth.setPasoFuncional("paso2");
		rqAuth.setTipoDocumento("FS001");

		ErrorResponseDTO res = new ErrorResponseDTO();
		res.setCodigoError("CGP-401");
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		mensajeFuncional.setCodigoFuncional("CGP-401");
		mensajeFuncional.setDescripcionFuncional("No se puede continuar con la experiencia.");
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		listaMensajes.add(mensajeFuncional);
		res.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(ErrorRequestDTO.class), Mockito.anyString(),
				Mockito.anyString())).thenReturn(res);

		Mockito.when(servicioAutenticacion.validarSesion(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(new ExceptionLI("CGP-401", "No autorizado"));

		AutenticacionFuerteRS resp = service.consultarAuthFuerte("", new Gson().toJson(rqAuth), "127.0.0.1");

		assertEquals(res.getCodigoError(), resp.getCodError());
	}


	@SuppressWarnings("static-access")
	@Test
	public void testgeneracionClaveODA() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		genClave.setCorreoElectronico("prueba@pru.com");
		genClave.setMetodoEnvioOTPODA(ConstantesLI.TIPO_MENSAJE_TEXTO);

		GenerarEnviarOTPODAResponse otp = new GenerarEnviarOTPODAResponse();
		otp.setOtp("1234");

		PowerMockito.whenNew(ServicioGenerarOTP.class).withAnyArguments().thenReturn(servicioGenerarOTP);
		Mockito.when(servicioGenerarOTP.enviarOTP(Mockito.any(AutenticacionOTPRQ.class))).thenReturn(otp);
		GeneracionClaveRS resp = service.generacionClave("", new Gson().toJson(genClave), "127.0.0.1");
		assertEquals(ConstantesLI.VAR_CLAVE_OTPODA, resp.getOtp());
	}

	@SuppressWarnings("static-access")
	@Test
	public void testgeneracionClaveExceptionLI() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("500");
		errorResponseDTO.setDescripcionTecnica("500");
		mensajeFuncional.setCodigoFuncional("500");
		mensajeFuncional.setDescripcionFuncional("500");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		genClave.setCorreoElectronico("prueba@pru.com");

		GenerarOTPODAResponse otp = new GenerarOTPODAResponse();
		otp.setOtp("1234");
		ExceptionLI exc = new ExceptionLI("500", "Error prueba");

		Mockito.when(servicioAutenticacion.validarSesion(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(new ExceptionLI("500", "Error prueba"));

		PowerMockito.whenNew(ServicioGenerarOTP.class).withAnyArguments().thenReturn(servicioGenerarOTP);
		Mockito.when(servicioGenerarOTP.generarOTP(Mockito.any(AutenticacionOTPRQ.class))).thenReturn(otp);
		GeneracionClaveRS resp = service.generacionClave("", new Gson().toJson(genClave), "127.0.0.1");
		assertEquals(exc.getCodigo(), resp.getCodError());
	}

	@SuppressWarnings("static-access")
	@Test
	public void testgeneracionClaveODASystemExceptionMsg() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		genClave.setCorreoElectronico("prueba@pru.com");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("111");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("111");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.v2.SystemException faultInfo = new com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.v2.SystemException();
		faultInfo.setGenericException(new GenericException());
		faultInfo.getGenericException().setCode("111");
		com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.enlace.v2.SystemExceptionMsg exc = new com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.enlace.v2.SystemExceptionMsg(
				"", faultInfo);

		PowerMockito.whenNew(ServicioGenerarOTP.class).withAnyArguments().thenReturn(servicioGenerarOTP);

		Mockito.when(servicioGenerarOTP.generarOTP(Mockito.any(AutenticacionOTPRQ.class))).thenThrow(exc);
		GeneracionClaveRS resp = service.generacionClave("", new Gson().toJson(genClave), "127.0.0.1");
		assertEquals(errorResponseDTO.getCodigoError(), resp.getCodError());
	}

	@SuppressWarnings("static-access")
	@Test
	public void testgeneracionClaveODABusinessExceptionMsg() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		genClave.setCorreoElectronico("prueba@pru.com");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("111");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("111");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.v2.BusinessException faultInfo = new com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.v2.BusinessException();
		faultInfo.setGenericException(new GenericException());
		faultInfo.getGenericException().setCode("111");
		com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.enlace.v2.BusinessExceptionMsg exc = new com.grupobancolombia.intf.seguridadcorporativa.canales.generarotp.enlace.v2.BusinessExceptionMsg(
				"", faultInfo);

		PowerMockito.whenNew(ServicioGenerarOTP.class).withAnyArguments().thenReturn(servicioGenerarOTP);

		Mockito.when(servicioGenerarOTP.generarOTP(Mockito.any(AutenticacionOTPRQ.class))).thenThrow(exc);
		GeneracionClaveRS resp = service.generacionClave("", new Gson().toJson(genClave), "127.0.0.1");
		assertEquals(errorResponseDTO.getCodigoError(), resp.getCodError());
	}

	@SuppressWarnings("static-access")
	@Test
	public void testgeneracionClaveATALLA() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.OTP_ATALLA);
		genClave.setCelular("1312312312");
		genClave.setCorreoElectronico("prueba@pru.com");

		SolicitarTokenResponse otp = new SolicitarTokenResponse();
		otp.setRespuesta(new Respuesta());
		otp.getRespuesta().setCodigoRespuesta("000");

		PowerMockito.whenNew(ServicioGeneracionToken.class).withAnyArguments().thenReturn(servicioGeneracionToken);
		Mockito.when(servicioGeneracionToken.solicitarToken(Mockito.any(GeneracionTokenRQ.class))).thenReturn(otp);
		GeneracionClaveRS resp = service.generacionClave("", new Gson().toJson(genClave), "127.0.0.1");
		assertEquals(ConstantesLI.OTP_ATALLA, resp.getToken());
	}

	@SuppressWarnings("static-access")
	@Test
	public void testgeneracionClaveATALLASystemExceptionMsg() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.OTP_ATALLA);
		genClave.setCorreoElectronico("prueba@pru.com");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("111");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("111");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		com.grupobancolombia.intf.canal.movil.generaciontoken.v1.SystemException faultInfo = new com.grupobancolombia.intf.canal.movil.generaciontoken.v1.SystemException();
		faultInfo.setGenericException(new GenericException());
		faultInfo.getGenericException().setCode("111");
		com.grupobancolombia.intf.canal.movil.generaciontoken.enlace.v1.SystemExceptionMsg exc = new com.grupobancolombia.intf.canal.movil.generaciontoken.enlace.v1.SystemExceptionMsg(
				"", faultInfo);

		PowerMockito.whenNew(ServicioGeneracionToken.class).withAnyArguments().thenReturn(servicioGeneracionToken);
		Mockito.when(servicioGeneracionToken.solicitarToken(Mockito.any(GeneracionTokenRQ.class))).thenThrow(exc);
		GeneracionClaveRS resp = service.generacionClave("", new Gson().toJson(genClave), "127.0.0.1");
		assertEquals(errorResponseDTO.getCodigoError(), resp.getCodError());
	}

	@Test
	public void testgeneracionClaveATALLABusinessExceptionMsg() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.OTP_ATALLA);
		genClave.setCorreoElectronico("prueba@pru.com");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("111");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("111");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		com.grupobancolombia.intf.canal.movil.generaciontoken.v1.BusinessException faultInfo = new com.grupobancolombia.intf.canal.movil.generaciontoken.v1.BusinessException();
		faultInfo.setGenericException(new GenericException());
		faultInfo.getGenericException().setCode("111");
		com.grupobancolombia.intf.canal.movil.generaciontoken.enlace.v1.BusinessExceptionMsg exc = new com.grupobancolombia.intf.canal.movil.generaciontoken.enlace.v1.BusinessExceptionMsg(
				"", faultInfo);

		PowerMockito.whenNew(ServicioGeneracionToken.class).withAnyArguments().thenReturn(servicioGeneracionToken);
		Mockito.when(servicioGeneracionToken.solicitarToken(Mockito.any(GeneracionTokenRQ.class))).thenThrow(exc);
		GeneracionClaveRS resp = service.generacionClave("", new Gson().toJson(genClave), "127.0.0.1");
		assertEquals(errorResponseDTO.getCodigoError(), resp.getCodError());
	}

	@Test
	public void testgeneracionClaveATALLAExceptionLI() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.OTP_ATALLA);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("901");
		errorResponseDTO.setDescripcionTecnica("Sin email");
		mensajeFuncional.setCodigoFuncional("901");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioGeneracionToken.class).withAnyArguments().thenReturn(servicioGeneracionToken);
		GeneracionClaveRS resp = service.generacionClave("", new Gson().toJson(genClave), "127.0.0.1");
		assertEquals(errorResponseDTO.getCodigoError(), resp.getCodError());
	}

	@Test
	public void testValidacionClave() throws ExceptionLI {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("111");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("111");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		Mockito.when(servicioAutenticacion.validarSesion(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(new ExceptionLI("500", "Error prueba"));

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(errorResponseDTO.getCodigoError(), resp.getCodError());
	}
	
	


	@Test
	public void testValidacionClaveODA() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("124345");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("1234561");

		AutenticarClienteOTPODAResponse resAuthODA = new AutenticarClienteOTPODAResponse();
		resAuthODA.setResultCode("901");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(servicioAutenticarClienteOTP.autenticarClienteOTP(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resAuthODA.getResultCode(), resp.getResultCodeODA());
	}

	@Test
	public void testValidacionClaveODA902() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPODAResponse resAuthODA = new AutenticarClienteOTPODAResponse();
		resAuthODA.setResultCode("902");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("2");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("VCO-902");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("VCO-902");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(servicioAutenticarClienteOTP.autenticarClienteOTP(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}
	
	
	
	@Test
	public void testValidacionClaveODADefault() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPODAResponse resAuthODA = new AutenticarClienteOTPODAResponse();
		resAuthODA.setResultCode("905");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("2");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("DEFAULT-500");
		errorResponseDTO.setDescripcionTecnica("905");
		mensajeFuncional.setCodigoFuncional("DEFAULT-500");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(servicioAutenticarClienteOTP.autenticarClienteOTP(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}
		
	

	@Test
	public void testValidacionClaveODA9021() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPODAResponse resAuthODA = new AutenticarClienteOTPODAResponse();
		resAuthODA.setResultCode("902");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("VCO-903");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("VCO-903");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(servicioAutenticarClienteOTP.autenticarClienteOTP(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}

	@Test
	public void testValidacionClaveODA1021() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPODAResponse resAuthODA = new AutenticarClienteOTPODAResponse();
		resAuthODA.setResultCode("1021");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("VCO-1021");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("VCO-1021");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(servicioAutenticarClienteOTP.autenticarClienteOTP(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}

	@Test
	public void testValidacionClaveODA1022() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPODAResponse resAuthODA = new AutenticarClienteOTPODAResponse();
		resAuthODA.setResultCode("1022");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("VCO-1022");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("VCO-1022");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(servicioAutenticarClienteOTP.autenticarClienteOTP(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}

	@Test
	public void testValidacionClaveODA803() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPODAResponse resAuthODA = new AutenticarClienteOTPODAResponse();
		resAuthODA.setResultCode("803");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("VCO-803");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("VCO-803");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(servicioAutenticarClienteOTP.autenticarClienteOTP(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}

	@Test
	public void testValidacionClaveODASystemExceptionMsg() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPODAResponse resAuthODA = new AutenticarClienteOTPODAResponse();
		resAuthODA.setResultCode("000");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("111");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("111");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		com.grupobancolombia.intf.seguridadcorporativa.canales.autenticarclienteotp.v2.SystemException faultInfo = new com.grupobancolombia.intf.seguridadcorporativa.canales.autenticarclienteotp.v2.SystemException();
		faultInfo.setGenericException(new GenericException());
		faultInfo.getGenericException().setCode("111");
		com.grupobancolombia.intf.seguridadcorporativa.canales.autenticarclienteotp.enlace.v2.SystemExceptionMsg exc = new com.grupobancolombia.intf.seguridadcorporativa.canales.autenticarclienteotp.enlace.v2.SystemExceptionMsg(
				"", faultInfo);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(servicioAutenticarClienteOTP.autenticarClienteOTP(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenThrow(exc);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(errorResponseDTO.getCodigoError(), resp.getCodError());
	}

	@Test
	public void testValidacionClaveODAException() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPODAResponse resAuthODA = new AutenticarClienteOTPODAResponse();
		resAuthODA.setResultCode("000");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("111");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("111");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(servicioAutenticarClienteOTP.autenticarClienteOTP(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenThrow(new Exception());

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(errorResponseDTO.getCodigoError(), resp.getCodError());
	}

	@Test
	public void testValidacionClaveSFTKN() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_SOFTOKEN);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPSoftokenResponse resAuthODA = new AutenticarClienteOTPSoftokenResponse();
		resAuthODA.setResultCode("801");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(
				servicioAutenticarClienteOTP.autenticarClienteOTPSoftoken(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resAuthODA.getResultCode(), resp.getResultCodeODA());
	}

	@Test
	public void testValidacionClaveSFTKN802() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_SOFTOKEN);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPSoftokenResponse resAuthODA = new AutenticarClienteOTPSoftokenResponse();
		resAuthODA.setResultCode("802");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("2");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("VCS-802");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("VCS-802");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(
				servicioAutenticarClienteOTP.autenticarClienteOTPSoftoken(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}

	
	
	@Test
	public void testValidacionClaveSFTKNDefault() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_SOFTOKEN);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPSoftokenResponse resAuthODA = new AutenticarClienteOTPSoftokenResponse();
		resAuthODA.setResultCode("905");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("2");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("DEFAULT-500");
		errorResponseDTO.setDescripcionTecnica("500");
		mensajeFuncional.setCodigoFuncional("DEFAULT-500");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(
				servicioAutenticarClienteOTP.autenticarClienteOTPSoftoken(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}

	
	
	@Test
	public void testValidacionClaveSFTKN8021() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_SOFTOKEN);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPSoftokenResponse resAuthODA = new AutenticarClienteOTPSoftokenResponse();
		resAuthODA.setResultCode("802");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("VCS-803");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("VCS-803");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(
				servicioAutenticarClienteOTP.autenticarClienteOTPSoftoken(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}

	@Test
	public void testValidacionClaveSFTKN1021() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_SOFTOKEN);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPSoftokenResponse resAuthODA = new AutenticarClienteOTPSoftokenResponse();
		resAuthODA.setResultCode("1021");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("VCS-1021");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("VCS-1021");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(
				servicioAutenticarClienteOTP.autenticarClienteOTPSoftoken(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}

	@Test
	public void testValidacionClaveSFTKN1022() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_SOFTOKEN);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPSoftokenResponse resAuthODA = new AutenticarClienteOTPSoftokenResponse();
		resAuthODA.setResultCode("1022");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("VCS-1022");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("VCS-1022");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(
				servicioAutenticarClienteOTP.autenticarClienteOTPSoftoken(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}

	@Test
	public void testValidacionClaveSFTKN900() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_SOFTOKEN);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPSoftokenResponse resAuthODA = new AutenticarClienteOTPSoftokenResponse();
		resAuthODA.setResultCode("");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("VCS-900");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("VCS-900");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(
				servicioAutenticarClienteOTP.autenticarClienteOTPSoftoken(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}

	@Test
	public void testValidacionClaveSFTKNExc() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.VAR_CLAVE_SOFTOKEN);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setOtp("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("123456");

		AutenticarClienteOTPSoftokenResponse resAuthODA = new AutenticarClienteOTPSoftokenResponse();
		resAuthODA.setResultCode("");
		resAuthODA.setResultDescription("Success");
		resAuthODA.setIntentosFallidosRestantes("1");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("CLASTKN-500");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("CLASTKN-500");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		PowerMockito.whenNew(ServicioAutenticarClienteOTP.class).withAnyArguments()
				.thenReturn(servicioAutenticarClienteOTP);
		Mockito.when(
				servicioAutenticarClienteOTP.autenticarClienteOTPSoftoken(Mockito.any(AutenticarClienteOTPRQ.class)))
				.thenThrow(new Exception());

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resp.getCodError(), errorResponseDTO.getCodigoError());
	}

	@Test
	public void testValidacionClaveATALLA() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.OTP_ATALLA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setTokenEntrada("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("2312312312");
		validacionClaveRQ.setCelular("123123121");

		ValidarTokenResponse resAuthODA = new ValidarTokenResponse();
		resAuthODA.setRespuesta(new Respuesta());
		resAuthODA.getRespuesta().setCodigoRespuesta("000");
		resAuthODA.getRespuesta().setDescripcionRespuesta("Success");

		PowerMockito.whenNew(ServicioGeneracionToken.class).withAnyArguments().thenReturn(servicioGeneracionToken);
		Mockito.when(servicioGeneracionToken.validarToken(Mockito.any(GeneracionTokenRQ.class))).thenReturn(resAuthODA);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(resAuthODA.getRespuesta().getCodigoRespuesta(), resp.getCodigoRespuestaTOKEN());
	}

	@Test
	public void testValidacionClaveATALLASystemExceptionMsg() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.OTP_ATALLA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setTokenEntrada("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("2312312312");
		validacionClaveRQ.setCelular("123123121");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("111");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("111");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		com.grupobancolombia.intf.canal.movil.generaciontoken.v1.SystemException faultInfo = new com.grupobancolombia.intf.canal.movil.generaciontoken.v1.SystemException();
		faultInfo.setGenericException(new GenericException());
		faultInfo.getGenericException().setCode("111");
		com.grupobancolombia.intf.canal.movil.generaciontoken.enlace.v1.SystemExceptionMsg exc = new com.grupobancolombia.intf.canal.movil.generaciontoken.enlace.v1.SystemExceptionMsg(
				"", faultInfo);

		PowerMockito.whenNew(ServicioGeneracionToken.class).withAnyArguments().thenReturn(servicioGeneracionToken);
		Mockito.when(servicioGeneracionToken.validarToken(Mockito.any(GeneracionTokenRQ.class))).thenThrow(exc);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(errorResponseDTO.getCodigoError(), resp.getCodError());
	}

	@Test
	public void testValidacionClaveATALLABusinessExceptionMsg() throws Exception {
		ValidacionClaveRQ validacionClaveRQ = new ValidacionClaveRQ();
		validacionClaveRQ.setIdSesion("312312lkasjdklas");
		validacionClaveRQ.setMecanismo(ConstantesLI.OTP_ATALLA);
		validacionClaveRQ.setPasoFuncional("pasoX");
		validacionClaveRQ.setTokenEntrada("123456");
		validacionClaveRQ.setTipoDocumento("FS001");
		validacionClaveRQ.setNumeroDocumento("2312312312");
		validacionClaveRQ.setCelular("123123121");

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("VCA-802");
		errorResponseDTO.setDescripcionTecnica("100");
		mensajeFuncional.setCodigoFuncional("VCA-802");
		mensajeFuncional.setDescripcionFuncional("Error");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);

		com.grupobancolombia.intf.canal.movil.generaciontoken.v1.BusinessException faultInfo = new com.grupobancolombia.intf.canal.movil.generaciontoken.v1.BusinessException();
		faultInfo.setGenericException(new GenericException());
		faultInfo.getGenericException().setCode("111");
		com.grupobancolombia.intf.canal.movil.generaciontoken.enlace.v1.BusinessExceptionMsg exc = new com.grupobancolombia.intf.canal.movil.generaciontoken.enlace.v1.BusinessExceptionMsg(
				"", faultInfo);

		PowerMockito.whenNew(ServicioGeneracionToken.class).withAnyArguments().thenReturn(servicioGeneracionToken);
		Mockito.when(servicioGeneracionToken.validarToken(Mockito.any(GeneracionTokenRQ.class))).thenThrow(exc);

		ValidacionClaveRS resp = service.validacionClave("", new Gson().toJson(validacionClaveRQ), "127.0.0.1");

		assertEquals(errorResponseDTO.getCodigoError(), resp.getCodError());
	}
	
	@Test
	public void testVerTarjetasFiltradas() throws ExceptionLI, IOException, ConectorClientException, Exception {
//		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
//		GestionCreditoConsumoRS respuesta = new GestionCreditoConsumoRS();
//
//		respuestaService.setIdProceso("4463");
//		respuestaService.setNumeroSolicitud("SUC_2020_161592");
//
//		OfertaDigital ofertaDigital = new Gson().fromJson(Constantes.respuestaOfertaTarjetas, OfertaDigital.class);
//		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
//		.thenReturn(ofertaDigital);
//		respuesta = service.verTarjetas(Constantes.tarjetaSeleccionadas, "127.0.0.1");
//		assertNotNull(respuesta);
//		System.out.println("Respuesta Tarjetas de credito: " + new Gson().toJson(respuesta));
//		assertThat(respuesta.getProductosTarjeta().get(0).getNombreSubProducto(), is("AMEX VERDE"));
	}
		

	@Test
	public void testConfirmarCreditoConsumoExcLI() throws ExceptionLI, IOException, ConectorClientException, Exception {
		ExceptionLI respuestaService = new ExceptionLI("500", "error prueba");
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("GCC-500");
		errorResponseDTO.setDescripcionTecnica("No se encontr� codError en API Errores");
		mensajeFuncional.setCodigoFuncional("GCC-500");
		mensajeFuncional
				.setDescripcionFuncional("En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);
		Mockito.when(servicioAutenticacion.validarSesion(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(new ExceptionLI("GCC-500", "No se encontrÃ³ codError en API Errores"));
		respuestaApi = service.confirmarCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO2,
				"127.0.0.1");
		assertThat(respuestaApi.getCodFuncional(), is("GCC-500"));
	}

	@Test
	public void testFirmarCreditoConsumoExcLI() throws ExceptionLI, IOException, ConectorClientException, Exception {
		ExceptionLI respuestaService = new ExceptionLI("500", "error prueba");
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("GCC-500");
		errorResponseDTO.setDescripcionTecnica("No se encontr� codError en API Errores");
		mensajeFuncional.setCodigoFuncional("GCC-500");
		mensajeFuncional
				.setDescripcionFuncional("En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(errorResponseDTO);
		Mockito.when(servicioAutenticacion.validarSesion(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(new ExceptionLI("GCC-500", "No se encontrÃ³ codError en API Errores"));
		respuestaApi = service.firmarDocumentosCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO3,
				"127.0.0.1");
		assertThat(respuestaApi.getCodFuncional(), is("GCC-500"));
	}

	@Test
	public void testFirmarCreditoConsumonPaso3() throws ExceptionLI, IOException, ConectorClientException, Exception {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();
		propiedades = Variables.getPropiedades();
		service.propiedades.setPropiedades(propiedades);

		respuestaService.setCodError("500");
		Mockito.when(callRestServiceBack.proxyGestionCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(respuestaService);

		String respuesta = ConstantesTest.RESPUESTAOFERTASOLICITUDAVANZARERROR;
		OfertaDigital ofertaDigital = new Gson().fromJson(respuesta, OfertaDigital.class);
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("500");
		errorResponseDTO
				.setDescripcionTecnica("En este momento el sistema no está disponible. Por favor intenta más tarde.");
		mensajeFuncional.setCodigoFuncional("500");
		mensajeFuncional
				.setDescripcionFuncional("En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(ErrorRequestDTO.class), Mockito.anyString(),
				Mockito.anyString())).thenReturn(errorResponseDTO);

		respuestaApi = service.firmarDocumentosCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO3,
				"127.0.0.1");
		assertThat(respuestaApi.getCodFuncional(), is("500"));
	}

	@Test
	public void testFirmarCreditoConsumonEmisionPaso3()
			throws ExceptionLI, IOException, ConectorClientException, Exception {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuestaApi = new GestionCreditoConsumoRS();
		propiedades = Variables.getPropiedades();
		service.propiedades.setPropiedades(propiedades);

		respuestaService.setIdProceso("5555");
		respuestaService.setNumeroSolicitud("SUC_2020_168814");
		Mockito.when(callRestServiceBack.proxyGestionCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(respuestaService);

		String respuesta = ConstantesTest.RESPUESTAOFERTASOLICITUDAVANZAREMISIONERROR;
		OfertaDigital ofertaDigital = new Gson().fromJson(respuesta, OfertaDigital.class);
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("500");
		errorResponseDTO
				.setDescripcionTecnica("En este momento el sistema no está disponible. Por favor intenta más tarde.");
		mensajeFuncional.setCodigoFuncional("500");
		mensajeFuncional
				.setDescripcionFuncional("En este momento el sistema no está disponible. Por favor intenta más tarde.");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(ErrorRequestDTO.class), Mockito.anyString(),
				Mockito.anyString())).thenReturn(errorResponseDTO);

		respuestaApi = service.firmarDocumentosCreditoConsumo("", ConstantesTest.CARGAUTILFIRMARCREDITOCONSUMOPASO3,
				"127.0.0.1");
		assertThat(respuestaApi.getCodFuncional(), is("500"));
	}

	@Test
	public void testFirmarSolicitudCreditoConsumoPaso3()
			throws Exception, IOException, ConectorClientException, RuntimeException {
		GestionCreditoConsumoRS respuestaService = new GestionCreditoConsumoRS();
		GestionCreditoConsumoRS respuesta = new GestionCreditoConsumoRS();

		respuestaService.setIdProceso("4463");
		respuestaService.setNumeroSolicitud("1111111111-4463-14");

		Mockito.when(callRestServiceBack.proxyGestionCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class),
				Mockito.any(), Mockito.anyString())).thenReturn(respuestaService);
		OfertaDigital ofertaDigital = null;
		Mockito.when(callRestServiceBack.apiGestionCreditos(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(ofertaDigital);
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		errorResponseDTO.setCodigoError("500");
		errorResponseDTO.setDescripcionTecnica("No existe oferta digital");
		mensajeFuncional.setCodigoFuncional("500");
		mensajeFuncional.setDescripcionFuncional("No existe oferta digital");
		listaMensajes.add(mensajeFuncional);
		errorResponseDTO.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(ErrorRequestDTO.class), Mockito.anyString(),
				Mockito.anyString())).thenReturn(errorResponseDTO);

		respuesta = service.firmarDocumentosCreditoConsumo("", ConstantesTest.CARGAUTILSOLICITUDCREDITOCONSUMOPASO3,
				"127.0.0.1");
		assertNotNull(respuesta);
		assertThat(respuesta.getCodError(), is("500"));
	}

	@Test
	public void testconsultarAuthFuerteExceptionError() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		AutenticacionFuerteRQ rqAuth = new AutenticacionFuerteRQ();
		rqAuth.setNumeroDocumento("12312321");
		rqAuth.setPasoFuncional("paso2");
		rqAuth.setTipoDocumento("FS001");

		ErrorResponseDTO res = new ErrorResponseDTO();
		res.setCodigoError("500");
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		mensajeFuncional.setCodigoFuncional("500");
		mensajeFuncional.setDescripcionFuncional("Dato vacio.");
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		listaMensajes.add(mensajeFuncional);
		res.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(ErrorRequestDTO.class), Mockito.anyString(),
				Mockito.anyString())).thenReturn(res);

		Mockito.when(servicioAutenticacion.validarSesion(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(Exception.class);

		AutenticacionFuerteRS resp = service.consultarAuthFuerte("", new Gson().toJson(rqAuth), "127.0.0.1");

		assertEquals(res.getCodigoError(), resp.getCodError());
	}

	@Test
	public void testgeneracionClaveExceptionError() throws Exception {
		propiedades = Variables.getPropiedades2();
		service.propiedades.setPropiedades(propiedades);

		AutenticacionFuerteRQ rqAuth = new AutenticacionFuerteRQ();
		rqAuth.setNumeroDocumento("12312321");
		rqAuth.setPasoFuncional("paso2");
		rqAuth.setTipoDocumento("FS001");

		ErrorResponseDTO res = new ErrorResponseDTO();
		res.setCodigoError("500");
		MensajeFuncionalDTO mensajeFuncional = new MensajeFuncionalDTO();
		mensajeFuncional.setCodigoFuncional("500");
		mensajeFuncional.setDescripcionFuncional("Dato vacio.");
		List<MensajeFuncionalDTO> listaMensajes = new ArrayList<>();
		listaMensajes.add(mensajeFuncional);
		res.setMensajeFuncional(listaMensajes);
		Mockito.when(callRestServiceBack.errorService(Mockito.any(ErrorRequestDTO.class), Mockito.anyString(),
				Mockito.anyString())).thenReturn(res);

		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		genClave.setCorreoElectronico("prueba@pru.com");

		GenerarOTPODAResponse otp = new GenerarOTPODAResponse();
		otp.setOtp("1234");

		Mockito.when(servicioAutenticacion.validarSesion(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(Exception.class);

		PowerMockito.whenNew(ServicioGenerarOTP.class).withAnyArguments().thenReturn(servicioGenerarOTP);
		Mockito.when(servicioGenerarOTP.generarOTP(Mockito.any(AutenticacionOTPRQ.class))).thenReturn(otp);

		GeneracionClaveRS resp = service.generacionClave("", new Gson().toJson(genClave), "127.0.0.1");

		assertEquals(res.getCodigoError(), resp.getCodError());
	}
}
