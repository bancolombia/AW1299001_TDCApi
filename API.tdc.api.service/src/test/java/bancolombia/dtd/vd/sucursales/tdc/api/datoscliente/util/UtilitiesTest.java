/**
 * 
 */
package bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gson.Gson;

import bancolombia.dtd.vd.li.commons.exception.ExceptionLI;
import bancolombia.dtd.vd.li.commons.util.CargadorPropiedades;
import bancolombia.dtd.vd.li.dto.proxy.clave.GeneracionClaveRQ;
import bancolombia.dtd.vd.li.dto.proxy.comun.Propiedad;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.OfertaDigital;
import bancolombia.dtd.vd.li.dto.utils.ProductoCreditoDto;
import bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.ConstantesTest;
import bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.Variables;


@RunWith(PowerMockRunner.class)
@PrepareForTest({Utilities.class})
public class UtilitiesTest {

	private Utilities utilities;

	@Mock
	private Utilities utilitiesM;

	private CargadorPropiedades prop;

	
	@Before
	public void init() {
		utilities = new Utilities();
	}

	@Test
	public void cuandoSeEjecutaRetornaFechaActualConFormatoDefinido() {
		String fecha = utilities.getFechaActual();
		assertNotNull(fecha);
	}

	@Test
	public void testverTarjetas() throws ExceptionLI {
		OfertaDigital ofertaDigital = new Gson().fromJson(ConstantesTest.OFERTA_VER_TDC, OfertaDigital.class);
		prop = new CargadorPropiedades();
		prop.setPropiedades(Variables.getPropiedades());
		List<ProductoCreditoDto> res = utilities.obtenerTarjetas(ofertaDigital, "3000000", prop, "12");
		assertNotNull(res);
	}

	@Test
	public void testverTarjetasJoven() throws ExceptionLI {
		OfertaDigital ofertaDigital = new Gson().fromJson(ConstantesTest.OFERTA_VER_TDC_JOVEN, OfertaDigital.class);
		prop = new CargadorPropiedades();
		prop.setPropiedades(Variables.getPropiedades());
		List<ProductoCreditoDto> res = utilities.obtenerTarjetas(ofertaDigital, "3000000", prop, "10");
		assertNotNull(res);
	}
	
	@Test
	public void testverTarjetas21() throws ExceptionLI {
		OfertaDigital ofertaDigital = new Gson().fromJson(ConstantesTest.OFERTA_VER_TDC_21, OfertaDigital.class);
		prop = new CargadorPropiedades();
		prop.setPropiedades(Variables.getPropiedades());
		List<ProductoCreditoDto> res = utilities.obtenerTarjetas(ofertaDigital, "600000", prop, "21");
		assertNotNull(res);
	}

	@Test
	public void testverTarjetasIdeal() throws ExceptionLI {
		OfertaDigital ofertaDigital = new Gson().fromJson(ConstantesTest.OFERTA_VER_TDC_IDEAL, OfertaDigital.class);
		prop = new CargadorPropiedades();
		prop.setPropiedades(Variables.getPropiedades());
		List<ProductoCreditoDto> res = utilities.obtenerTarjetas(ofertaDigital, "1000000", prop, "11");
		assertNotNull(res);
	}

	@Test
	public void testverTarjetasExcep() throws ExceptionLI {
		OfertaDigital ofertaDigital = new Gson().fromJson(ConstantesTest.OFERTA_VER_SINTDC, OfertaDigital.class);
		prop = new CargadorPropiedades();
		prop.setPropiedades(Variables.getPropiedades());
		List<ProductoCreditoDto> res = utilities.obtenerTarjetas(ofertaDigital, "1000000", prop, "11");
		assertNotNull(res);
	}

	@Test
	public void getIpCliente() {
		HttpServletRequest http = Mockito.mock(HttpServletRequest.class);
		Mockito.when(http.getHeader(Mockito.anyString())).thenReturn("127.0.0.2");

		http.setAttribute("X-FORWARDED-FOR", "127.0.0.2");
		String rep = utilities.getIpCliente(http);
		assertEquals(rep, "127.0.0.2");
	}

	@Test
	public void getIpClienteNull() {
		HttpServletRequest http = Mockito.mock(HttpServletRequest.class);
		Mockito.when(http.getHeader(Mockito.anyString())).thenReturn(null);
		Mockito.when(http.getRemoteAddr()).thenReturn("127.0.0.2");
		http.setAttribute("X-FORWARDED-FOR", "127.0.0.2");
		String rep = utilities.getIpCliente(http);
		assertEquals(rep, "127.0.0.2");
	}

	@Test
	public void convertirFechaPago() throws Exception {
		GregorianCalendar cale = new GregorianCalendar(2020, 0, 31);
		PowerMockito.whenNew(GregorianCalendar.class).withAnyArguments().thenReturn(cale);
		Calendar cal = Calendar.getInstance();
		cal.set(2020, 0, 31);
		PowerMockito.mockStatic(Calendar.class);
		PowerMockito.when(Calendar.getInstance()).thenReturn(cal);
		utilitiesM.convertirFechaPago("30");
		Mockito.verify(utilitiesM, Mockito.times(1)).convertirFechaPago("30");
	}
	


	@Test
	public void convertirFechaPago531() {
		Calendar cal = Calendar.getInstance();
		cal.set(2020, 4, 30);
		PowerMockito.mockStatic(Calendar.class);
		PowerMockito.when(Calendar.getInstance()).thenReturn(cal);
		utilitiesM.convertirFechaPago("30");
		Mockito.verify(utilitiesM, Mockito.times(1)).convertirFechaPago("30");
	}
	
	@Test
	public void convertirFechaPago731() {
		Calendar cal = Calendar.getInstance();
		cal.set(2020, 6, 30);
		PowerMockito.mockStatic(Calendar.class);
		PowerMockito.when(Calendar.getInstance()).thenReturn(cal);
		utilitiesM.convertirFechaPago("30");
		Mockito.verify(utilitiesM, Mockito.times(1)).convertirFechaPago("30");
	}
	
	@Test
	public void convertirFechaPago831() {
		Calendar cal = Calendar.getInstance();
		cal.set(2020, 7, 30);
		PowerMockito.mockStatic(Calendar.class);
		PowerMockito.when(Calendar.getInstance()).thenReturn(cal);
		utilitiesM.convertirFechaPago("30");
		Mockito.verify(utilitiesM, Mockito.times(1)).convertirFechaPago("30");
	}
	
	@Test
	public void convertirFechaPago1031() {
		Calendar cal = Calendar.getInstance();
		cal.set(2020, 9, 30);
		PowerMockito.mockStatic(Calendar.class);
		PowerMockito.when(Calendar.getInstance()).thenReturn(cal);
		utilitiesM.convertirFechaPago("30");
		Mockito.verify(utilitiesM, Mockito.times(1)).convertirFechaPago("30");
	}
	
	@Test
	public void convertirFechaPago31() {
		Calendar cal = Calendar.getInstance();
		cal.set(2020, 0, 31);
		PowerMockito.mockStatic(Calendar.class);
		PowerMockito.when(Calendar.getInstance()).thenReturn(cal);
		utilitiesM.convertirFechaPago("30");
		Mockito.verify(utilitiesM, Mockito.times(1)).convertirFechaPago("30");
	}
	

	
	@Test
	public void convertirFechaPago1231() {
		Calendar cal = Calendar.getInstance();
		cal.set(2020, 11, 31);
		PowerMockito.mockStatic(Calendar.class);
		PowerMockito.when(Calendar.getInstance()).thenReturn(cal);
		utilitiesM.convertirFechaPago("30");
		Mockito.verify(utilitiesM, Mockito.times(1)).convertirFechaPago("30");
	}
	
	
	@Test
	public void convertirFechaPago228() {
		Calendar cal = Calendar.getInstance();
		cal.set(2020, 1, 28);
		PowerMockito.mockStatic(Calendar.class);
		PowerMockito.when(Calendar.getInstance()).thenReturn(cal);
		utilitiesM.convertirFechaPago("30");
		Mockito.verify(utilitiesM, Mockito.times(1)).convertirFechaPago("30");
	}

	@Test
	public void generarSharedKey() {
		assertEquals(utilities.generarSharedKey("FS001", "1223423"), "011223423");
	}

	@Test
	public void buscarTipoMensaje() {
		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		genClave.setCorreoElectronico("prueba@pru.com");
		genClave.setMetodoEnvioOTPODA(ConstantesLI.TIPO_MENSAJE_CORREO);

		assertEquals(utilities.buscarTipoMensaje(genClave), ConstantesLI.TIPO_MENSAJE_C);
	}

	@Test
	public void buscarTipoMensajeSMS() {
		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		genClave.setCorreoElectronico("prueba@pru.com");
		genClave.setMetodoEnvioOTPODA(ConstantesLI.TIPO_MENSAJE_TEXTO);

		assertEquals(utilities.buscarTipoMensaje(genClave), ConstantesLI.TIPO_MENSAJE_M);
	}

	@Test
	public void buscarTipoMensajeMIXTO() {
		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.VAR_CLAVE_OTPODA);
		genClave.setCorreoElectronico("prueba@pru.com");
		genClave.setMetodoEnvioOTPODA(ConstantesLI.TIPO_MENSAJE_MIXTO);

		assertEquals(utilities.buscarTipoMensaje(genClave), ConstantesLI.TIPO_MENSAJE_C);
	}

	@Test
	public void buscarTipoMensajeSTK() {
		GeneracionClaveRQ genClave = new GeneracionClaveRQ();
		genClave.setIdSesion("sadasd21321edsas");
		genClave.setNumeroDocumento("12312321");
		genClave.setPasoFuncional("paso2");
		genClave.setTipoDocumento("FS001");
		genClave.setMecanismo(ConstantesLI.VAR_CLAVE_SOFTOKEN);
		genClave.setCorreoElectronico("prueba@pru.com");
		genClave.setMetodoEnvioOTPODA("");

		assertEquals(utilities.buscarTipoMensaje(genClave), ConstantesLI.TIPO_MENSAJE_C);
	}

	@Test
	public void testconvertirFechaPago15() {
		String rep = utilities.convertirFechaPago("15");
		assertNotNull(rep);
	}

	@Test
	public void testconvertirFechaPago30() {
		String rep = utilities.convertirFechaPago("30");
		assertNotNull(rep);
	}

	@Test
	public void testcrearListaConfiguracion() {
		prop = new CargadorPropiedades();
		prop.setPropiedades(Variables.getPropiedades());
		List<Propiedad> resp = utilities.crearListaConfiguracion(prop,
				"PROXY_GESTION_SOLICITUD_CREDITO_CONSUMO_NOMBRE_TAREA_M1,VAL_API_COD_PRODUCTO_LIBRANZA");
		assertNotNull(resp);
		assertEquals("EsperarInformacionFinal", resp.get(0).getValor());
	}

	@Test
	public void testvalidacionRespuestaError() {
		ExceptionLI e = new ExceptionLI(ConstantesLI.ERROR_401, "Error");
		javax.ws.rs.core.Response resp = utilities.validacionRespuestaError(e);
		assertEquals(ConstantesTest.CUATRO_CERO_UNO, resp.getStatus());
	}

	@Test
	public void testvalidacionRespuestaError500() {
		ExceptionLI e = new ExceptionLI(ConstantesLI.ERROR_GCC_500, "Error");
		javax.ws.rs.core.Response resp = utilities.validacionRespuestaError(e);
		assertEquals(ConstantesTest.QUINIENTOS, resp.getStatus());
	}
}
