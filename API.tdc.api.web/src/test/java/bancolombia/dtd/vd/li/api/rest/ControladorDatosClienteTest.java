/**
 *
 */
package bancolombia.dtd.vd.li.api.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jose4j.jwt.MalformedClaimException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gson.Gson;

import bancolombia.dtd.vd.li.api.autenticacion.ServicioAutenticacion;
import bancolombia.dtd.vd.li.commons.exception.ExceptionLI;
import bancolombia.dtd.vd.li.dto.autenticacion.AutenticacionRS;
import bancolombia.dtd.vd.li.dto.proxy.authFuerte.AutenticacionFuerteRS;
import bancolombia.dtd.vd.li.dto.proxy.clave.GeneracionClaveRS;
import bancolombia.dtd.vd.li.dto.proxy.clave.ValidacionClaveRS;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.GestionCreditoConsumoRQ;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.GestionCreditoConsumoRS;
import bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.ServicioDatosCliente;
import bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.util.ConstantesLI;
import bancolombia.dtd.vd.tdc.api.rest.ControladorDatosCliente;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ServicioDatosCliente.class })
public class ControladorDatosClienteTest {

	private static final Logger logger = LogManager.getLogger(ControladorDatosClienteTest.class);

	@Mock
	ServicioDatosCliente servicioDatosCliente;

	@Mock
	ServicioAutenticacion servicioAutenticacion;

	ControladorDatosCliente service;

	HttpServletRequest requestContext;

	@Before
	public void init() {
		requestContext = Mockito.mock(HttpServletRequest.class);
		service = new ControladorDatosCliente();
		service.setControladorDatosCliente(servicioAutenticacion, servicioDatosCliente);
	}

	@Test
	public void pruebasLogs() {
		logger.trace("the built-in TRACE level");
		logger.debug("the built-in DEBUG level");
		logger.info("the built-in INFO level");
		logger.warn("the built-in WARN level");
		logger.error("the built-in ERROR level");
		logger.fatal("the built-in FATAL level");
	}

	@Test
	public void formatCreacion() {
        String entrada = "{\r\n" + "    \"codigoAsesorComercial\": \"9911\",\r\n"
                + "    \"hash\": \"8fad657e1689576326e0e24d25a56eda\",\r\n"
                + "    \"idSesion\": \"Hw42dzqzKm0xQKtU_hMutg\",\r\n" + "    \"numeroDocumento\": \"1111111\",\r\n"
                + "    \"paso\": \"paso1\",\r\n" + "    \"pasoFuncional\": \"paso_li_106\",\r\n"
                + "    \"producto\": [\r\n" + "        {\r\n" + "            \"beneficiario\": [],\r\n"
                + "            \"idProducto\": \"\"\r\n" + "        },\r\n" + "        {\r\n"
                + "            \"beneficiario\": [],\r\n" + "            \"idProducto\": \"12\"\r\n"
                + "        }\r\n" + "    ],\r\n" + "    \"sucursalRadicacion\": \"005\",\r\n"
                + "    \"tipodocumento\": \"FS001\"\r\n" + "}";

        GestionCreditoConsumoRQ gestionCreditoConsumoRQ = new Gson().fromJson(entrada,
                GestionCreditoConsumoRQ.class);
        assertEquals("1111111", gestionCreditoConsumoRQ.getNumeroDocumento());
	}

	@Test
	public void testCrearSolicitudCreditoConsumo() throws ExceptionLI {
		GestionCreditoConsumoRS respu = new GestionCreditoConsumoRS();
		respu.setCodSolicitante("123456");
		respu.setNombreConvenio("CONVENIO");
		Mockito.when(servicioDatosCliente.crearCreditoConsumo(anyString(), anyString(), anyString())).thenReturn(respu);
		Response resp = service.crearSolicitudCreditoConsumo(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testCrearSolicitudCreditoConsumoExceptionLI401() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.crearCreditoConsumo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.crearSolicitudCreditoConsumo(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testCrearSolicitudCreditoConsumoExceptionLI500() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.crearCreditoConsumo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.crearSolicitudCreditoConsumo(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testCrearSolicitudCreditoConsumoException() throws ExceptionLI {
		Mockito.when(servicioDatosCliente.crearCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.crearSolicitudCreditoConsumo(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testConfirmarCreditoConsumo() throws ExceptionLI {
		GestionCreditoConsumoRS respu = new GestionCreditoConsumoRS();
		respu.setCodSolicitante("123456");
		respu.setNombreConvenio("CONVENIO");
		Mockito.when(servicioDatosCliente.confirmarCreditoConsumo(anyString(), anyString(), anyString()))
				.thenReturn(respu);
		Response resp = service.confirmarCreditoConsumo(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testConfirmarCreditoConsumoExceptionLI401() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.confirmarCreditoConsumo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.confirmarCreditoConsumo(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testConfirmarCreditoConsumoExceptionLI500() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.confirmarCreditoConsumo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.confirmarCreditoConsumo(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testConfirmarCreditoConsumoException() throws ExceptionLI {
		Mockito.when(servicioDatosCliente.confirmarCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.confirmarCreditoConsumo(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testfirmarDocumentosCreditoConsumo() throws ExceptionLI {
		GestionCreditoConsumoRS respu = new GestionCreditoConsumoRS();
		respu.setCodSolicitante("123456");
		respu.setNombreConvenio("CONVENIO");
		Mockito.when(servicioDatosCliente.firmarDocumentosCreditoConsumo(anyString(), anyString(), anyString()))
				.thenReturn(respu);
		Response resp = service.firmarDocumentosCreditoConsumo(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testfirmarDocumentosCreditoConsumoExceptionLI401() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.firmarDocumentosCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(ex);
		Response resp = service.firmarDocumentosCreditoConsumo(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testfirmarDocumentosCreditoConsumoExceptionLI500() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.firmarDocumentosCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(ex);
		Response resp = service.firmarDocumentosCreditoConsumo(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testfirmarDocumentosCreditoConsumoException() throws ExceptionLI {
		Mockito.when(servicioDatosCliente.firmarDocumentosCreditoConsumo(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.firmarDocumentosCreditoConsumo(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testverTarjetas() throws ExceptionLI {
		GestionCreditoConsumoRS respu = new GestionCreditoConsumoRS();
		respu.setCodSolicitante("123456");
		respu.setNombreConvenio("CONVENIO");
		Mockito.when(servicioDatosCliente.verTarjetas(anyString(), anyString())).thenReturn(respu);
		Response resp = service.verTarjetas(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testverTarjetasExceptionLI401() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.verTarjetas(anyString(), anyString())).thenThrow(ex);
		Response resp = service.verTarjetas(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testverTarjetasExceptionLI500() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.verTarjetas(anyString(), anyString())).thenThrow(ex);
		Response resp = service.verTarjetas(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testverTarjetasException() throws ExceptionLI {
		Mockito.when(servicioDatosCliente.verTarjetas(anyString(), anyString())).thenThrow(new NullPointerException());
		Response resp = service.verTarjetas(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testfinalizarExp() throws ExceptionLI {
		String respu = "OK";
		Mockito.when(servicioDatosCliente.finalizarExp(anyString(), anyString())).thenReturn(respu);
		Response resp = service.finalizarExp(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testfinalizarExpExceptionLI401() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.finalizarExp(anyString(), anyString())).thenThrow(ex);
		Response resp = service.finalizarExp(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testfinalizarExpExceptionLI500() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.finalizarExp(anyString(), anyString())).thenThrow(ex);
		Response resp = service.finalizarExp(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testfinalizarExpException() throws ExceptionLI {
		Mockito.when(servicioDatosCliente.finalizarExp(anyString(), anyString())).thenThrow(new NullPointerException());
		Response resp = service.finalizarExp(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testenviarCorreo() throws ExceptionLI {
		String[] respu = { "OK", "OK2" };
		Mockito.when(servicioDatosCliente.enviarCorreo(anyString(), anyString(), anyString())).thenReturn(respu);
		Response resp = service.enviarCorreo(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testenviarCorreoExpExceptionLI401() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.enviarCorreo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.enviarCorreo(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testenviarCorreoExpExceptionLI500() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.enviarCorreo(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.enviarCorreo(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testenviarCorreoExpException() throws ExceptionLI {
		Mockito.when(servicioDatosCliente.enviarCorreo(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.enviarCorreo(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testhealth() {
		Response resp = service.health();
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testdescargarDocumentosBienvenida() throws ExceptionLI {
		String[] respu = { "OK", "OK2" };
		Mockito.when(servicioDatosCliente.descargarDocumentosBienvenida(anyString(), anyString(), anyString()))
				.thenReturn(respu);
		Response resp = service.descargarDocumentosBienvenida(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testdescargarDocumentosBienvenidaExceptionLI() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.descargarDocumentosBienvenida(anyString(), anyString(), anyString()))
				.thenThrow(ex);
		Response resp = service.descargarDocumentosBienvenida(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testconsultarAuthFuerte() throws ExceptionLI {
		AutenticacionFuerteRS respu = new AutenticacionFuerteRS();
		Mockito.when(servicioDatosCliente.consultarAuthFuerte(anyString(), anyString(), anyString())).thenReturn(respu);
		Response resp = service.consultarAuthFuerte(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testconsultarAuthFuerteException() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.consultarAuthFuerte(anyString(), anyString(), anyString())).thenThrow(ex);
		Response resp = service.consultarAuthFuerte(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testgeneracionClave() throws ExceptionLI {
		GeneracionClaveRS respu = new GeneracionClaveRS();
		Mockito.when(servicioDatosCliente.generacionClave(anyString(), anyString(), anyString())).thenReturn(respu);
		Response resp = service.generacionClave(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testgeneracionClaveException() throws ExceptionLI {

		Mockito.when(servicioDatosCliente.generacionClave(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.generacionClave(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}


	@Test
	public void testvalidacionClave() throws ExceptionLI {
		ValidacionClaveRS respu = new ValidacionClaveRS();
		Mockito.when(servicioDatosCliente.validacionClave(anyString(), anyString(), anyString())).thenReturn(respu);
		Response resp = service.validacionClave(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testvalidacionClaveException() throws ExceptionLI {

		Mockito.when(servicioDatosCliente.validacionClave(anyString(), anyString(), anyString()))
				.thenThrow(new NullPointerException());
		Response resp = service.validacionClave(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}


	@Test
	public void testguardarPaso() throws ExceptionLI {
		String respu = "OK";
		Mockito.when(servicioDatosCliente.finalizarExp(anyString(), anyString())).thenReturn(respu);
		Response resp = service.guardarPaso(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testguardarPasoExpExceptionLI401() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioDatosCliente.finalizarExp(anyString(), anyString())).thenThrow(ex);
		Response resp = service.guardarPaso(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testguardarPasoExpExceptionLI500() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.QUINIENTOS, "error");
		Mockito.when(servicioDatosCliente.finalizarExp(anyString(), anyString())).thenThrow(ex);
		Response resp = service.guardarPaso(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testguardarPasoException500() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "error");
		Mockito.when(servicioDatosCliente.finalizarExp(anyString(), anyString())).thenThrow(ex);
		Response resp = service.guardarPaso(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testguardarPasoException() throws ExceptionLI {
		Mockito.when(servicioDatosCliente.finalizarExp(anyString(), anyString())).thenThrow(new NullPointerException());
		Response resp = service.guardarPaso(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_QUINIENTOS);
	}

	@Test
	public void testvalidarSesion() throws ExceptionLI {
		AutenticacionRS  respu = new AutenticacionRS();
		Mockito.when(servicioAutenticacion.validarSesion(anyString(), anyString())).thenReturn(respu);
		Response resp = service.validarSesion(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testvalidarSesionExceptionLI401() throws ExceptionLI {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioAutenticacion.validarSesion(anyString(), anyString())).thenThrow(ex);
		Response resp = service.validarSesion(requestContext, "", "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}

	@Test
	public void testterminarSesion() throws ExceptionLI, GeneralSecurityException, MalformedClaimException, IOException {
		AutenticacionRS  respu = new AutenticacionRS();
		Mockito.when(servicioAutenticacion.terminarSesion(anyString())).thenReturn(respu);
		Response resp = service.terminarSesion(requestContext, "");
		assertEquals(resp.getStatus(), ConstantesLI.DOCIENTOS);
	}

	@Test
	public void testterminarSesionExceptionLI401() throws ExceptionLI, GeneralSecurityException, MalformedClaimException, IOException {
		ExceptionLI ex = new ExceptionLI(ConstantesLI.ERROR_401, "error");
		Mockito.when(servicioAutenticacion.terminarSesion(anyString())).thenThrow(ex);
		Response resp = service.terminarSesion(requestContext, "");
		assertEquals(resp.getStatus(), ConstantesLI.ERRORNN_401);
	}
}
