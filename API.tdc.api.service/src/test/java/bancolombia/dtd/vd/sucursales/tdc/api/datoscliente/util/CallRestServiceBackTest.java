package bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;

import org.apache.commons.collections.map.HashedMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.bancolombia.vtd.api.persistence.business.DynamicQueriesBusiness;
import com.bancolombia.vtd.api.persistence.business.OfertaDigitalBusiness;
import com.bcol.vtd.lib.comunes.exception.ConectorClientException;
import com.grupobancolombia.ents.common.genericexception.v2.GenericException;

import b1c.li.proxy.gestionCreditoConsumo.ServicioGestionCreditoConsumo;
import bancolombia.dtd.vd.li.commons.exception.ExceptionLI;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.GestionCreditoConsumoRQ;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.GestionCreditoConsumoRS;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.OfertaDigital;

import java.util.HashMap;
import java.util.Map;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ CallRestServiceBack.class })
public class CallRestServiceBackTest {

	CallRestServiceBack service;

	@Mock
	ServicioGestionCreditoConsumo servicioGestionCreditoConsumo;

	@Mock
	private OfertaDigitalBusiness ofertaDigiPersis;

	@Mock
	private DynamicQueriesBusiness dquery;

	private  Map<String, String> propiedades;

	@Before
	public void setUpBefore() {
		service = new CallRestServiceBack();
	}

	@Test
	public void testProxyGestionCreditoConsumoCrear() throws Exception {

		PowerMockito.whenNew(ServicioGestionCreditoConsumo.class).withAnyArguments()
				.thenReturn(servicioGestionCreditoConsumo);

		GestionCreditoConsumoRS resCrear = new GestionCreditoConsumoRS();
		resCrear.setIdProceso("12345");
		GestionCreditoConsumoRQ gestionCreditoConsumoRQ = new GestionCreditoConsumoRQ();
		gestionCreditoConsumoRQ.setIdSesion("123123");
		Mockito.when(
				servicioGestionCreditoConsumo.crearSolicitudCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class)))
				.thenReturn(resCrear);

		GestionCreditoConsumoRS consumo = service.proxyGestionCreditoConsumo(gestionCreditoConsumoRQ, null,
				"crearSolicitudCreditoConsumo");
		assertEquals(consumo.getIdProceso(), resCrear.getIdProceso());
	}

	@Test
	public void testProxyGestionCreditoConsumoAvanzar() throws Exception {

		PowerMockito.whenNew(ServicioGestionCreditoConsumo.class).withAnyArguments()
				.thenReturn(servicioGestionCreditoConsumo);

		GestionCreditoConsumoRS resCrear = new GestionCreditoConsumoRS();
		resCrear.setIdProceso("12345");
		GestionCreditoConsumoRQ gestionCreditoConsumoRQ = new GestionCreditoConsumoRQ();
		gestionCreditoConsumoRQ.setIdSesion("123123");
		Mockito.when(servicioGestionCreditoConsumo
				.avanzarSolicitudCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class))).thenReturn(resCrear);

		GestionCreditoConsumoRS consumo = service.proxyGestionCreditoConsumo(gestionCreditoConsumoRQ, null,
				"avanzarSolicitudCreditoConsumo");
		assertEquals(consumo.getIdProceso(), resCrear.getIdProceso());
	}

	@Test(expected = ExceptionLI.class)
	public void testProxyGestionCreditoConsumoAvanzarBusinessExceptionMsg() throws Exception {

		PowerMockito.whenNew(ServicioGestionCreditoConsumo.class).withAnyArguments()
				.thenReturn(servicioGestionCreditoConsumo);

		com.grupobancolombia.intf.producto.comun.gestionsolicitudcreditoconsumo.v2.BusinessException faultInfo = new com.grupobancolombia.intf.producto.comun.gestionsolicitudcreditoconsumo.v2.BusinessException();
		faultInfo.setGenericException(new GenericException());
		com.grupobancolombia.intf.producto.comun.gestionsolicitudcreditoconsumo.enlace.v2.BusinessExceptionMsg msg = new com.grupobancolombia.intf.producto.comun.gestionsolicitudcreditoconsumo.enlace.v2.BusinessExceptionMsg(
				"", faultInfo);
		GestionCreditoConsumoRQ gestionCreditoConsumoRQ = new GestionCreditoConsumoRQ();
		gestionCreditoConsumoRQ.setIdSesion("123123");
		Mockito.when(servicioGestionCreditoConsumo
				.avanzarSolicitudCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class))).thenThrow(msg);
		service.proxyGestionCreditoConsumo(gestionCreditoConsumoRQ, null, "avanzarSolicitudCreditoConsumo");
	}

	@Test(expected = ExceptionLI.class)
	public void testProxyGestionCreditoConsumoAvanzarSystemExceptionMsg() throws Exception {

		PowerMockito.whenNew(ServicioGestionCreditoConsumo.class).withAnyArguments()
				.thenReturn(servicioGestionCreditoConsumo);

		com.grupobancolombia.intf.producto.comun.gestionsolicitudcreditoconsumo.v2.SystemException faultInfo = new com.grupobancolombia.intf.producto.comun.gestionsolicitudcreditoconsumo.v2.SystemException();
		faultInfo.setGenericException(new GenericException());
		com.grupobancolombia.intf.producto.comun.gestionsolicitudcreditoconsumo.enlace.v2.SystemExceptionMsg msg = new com.grupobancolombia.intf.producto.comun.gestionsolicitudcreditoconsumo.enlace.v2.SystemExceptionMsg(
				"", faultInfo);
		GestionCreditoConsumoRQ gestionCreditoConsumoRQ = new GestionCreditoConsumoRQ();
		gestionCreditoConsumoRQ.setIdSesion("123123");
		Mockito.when(servicioGestionCreditoConsumo
				.avanzarSolicitudCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class))).thenThrow(msg);
		service.proxyGestionCreditoConsumo(gestionCreditoConsumoRQ, null, "avanzarSolicitudCreditoConsumo");
	}

	@Test(expected = ExceptionLI.class)
	public void testProxyGestionCreditoConsumoAvanzarException() throws Exception {

		PowerMockito.whenNew(ServicioGestionCreditoConsumo.class).withAnyArguments()
				.thenReturn(servicioGestionCreditoConsumo);

		GestionCreditoConsumoRQ gestionCreditoConsumoRQ = new GestionCreditoConsumoRQ();
		gestionCreditoConsumoRQ.setIdSesion("123123");
		Mockito.when(servicioGestionCreditoConsumo
				.avanzarSolicitudCreditoConsumo(Mockito.any(GestionCreditoConsumoRQ.class)))
				.thenThrow(new NullPointerException());
		service.proxyGestionCreditoConsumo(gestionCreditoConsumoRQ, null, "avanzarSolicitudCreditoConsumo");
	}

	@Test
	public void testApiGestionCreditos() throws ConectorClientException {

		Mockito.when(ofertaDigiPersis.getOfertaDigitaByNumeroSolicitud(anyString(), anyString()))
				.thenReturn("{\r\n" + "    \"cabecera\":\r\n" + "    {\r\n" + "        \"estadoTransaccion\": \"\",\r\n"
						+ "        \"pasoFuncional\": \"paso1\",\r\n"
						+ "        \"numeroSolicitud\": \"SUC_2020_108030\",\r\n"
						+ "        \"extensionFuncional\": [\r\n" + "        {\r\n" + "            \"llave\": \"\",\r\n"
						+ "            \"valor\": \"\"\r\n" + "        }]\r\n" + "    }\r\n" + "}");
		service = new CallRestServiceBack(ofertaDigiPersis, null);
		OfertaDigital ofertaDigital = service.apiGestionCreditos("SUC", "paso1");
		assertEquals(ofertaDigital.getCabecera().getNumeroSolicitud(), "SUC_2020_108030");
	}

	@Test
	public void testApiGestionCreditosConectorClientException() throws ConectorClientException {

		Mockito.when(ofertaDigiPersis.getOfertaDigitaByNumeroSolicitud(anyString(), anyString()))
				.thenThrow(new ConectorClientException("", null));
		service = new CallRestServiceBack(ofertaDigiPersis, null);
		OfertaDigital ofertaDigital = service.apiGestionCreditos("SUC", "paso1");
		assertNull(ofertaDigital);
	}

	@Test
	public void testApiGestionCreditosException() throws ConectorClientException {

		Mockito.when(ofertaDigiPersis.getOfertaDigitaByNumeroSolicitud(anyString(), anyString()))
				.thenThrow(new NullPointerException());
		service = new CallRestServiceBack(ofertaDigiPersis, null);
		OfertaDigital ofertaDigital = service.apiGestionCreditos("SUC", "paso1");
		assertNull(ofertaDigital);
	}

	


}
