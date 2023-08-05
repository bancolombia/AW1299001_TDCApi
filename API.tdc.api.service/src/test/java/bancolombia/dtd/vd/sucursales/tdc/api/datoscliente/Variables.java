package bancolombia.dtd.vd.sucursales.tdc.api.datoscliente;

import java.util.HashMap;
import java.util.Map;

public class Variables {
	private static Map<String, String> propiedades;
	private static Map<String, String> propiedades2;
	private static Map<String, String> propiedades3;
	private static Map<String, String> propiedades4;
	private static Map<String, String> propiedadesProspecto;

	private static void InicializarPropiedades() {
		propiedades = new HashMap<String, String>() {
			{
				put("API_DATOS_COMERCIALES_SUCURSAL_COD_ASESOR", "true");
				put("API_GESTION_PRECALCULADO_CLIENTE_TIPO_PRODUCTO", "4,15,14");
				put("VAL_TIEMPO_ESPERA_INICIAL_GESTION_CRED", "5");
				put("VAL_TIEMPO_REINTENTO_GESTION_CRED", "5");
				put("VAL_TIEMPO_TOTAL_GESTION_CRED", "20");
				put("VAL_TIEMPO_TOTAL_GESTION_CRED_MOM1","30");
				put("VAL_TIEMPO_TOTAL_GESTION_CRED_MOM2","50");
				put("PROXY_GESTION_SOLICITUD_CREDITO_CONSUMO_NOMBRE_TAREA_M1", "EsperarInformacionFinal");
				put("PROXY_GESTION_SOLICITUD_CREDITO_CONSUMO_NOMBRE_TAREA_M2", "EsperarAceptacionCliente");
				put("PROXY_GESTION_SOLICITUD_CREDITO_CONSUMO_NOMBRE_ARCHIVO_TRAZA", "TrazaLibreInversionSucursales.txt");
				put("VAL_API_ERRORES_ACTIVO", "true");
				put("VAL_API_ERRORES_COD_APLICACION", "99");
				put("MSG_DEFAULT_API", "En este momento el sistema no está disponible. Por favor intenta más tarde.");
				put("API_VAR_CALL_PROXY_PRECALCULADO_CLIENTE", "API_VAR_CALL_PROXY_PRECALCULADO_CLIENTE");
				put("VAL_API_COD_PRODUCTO_CREDIAGIL", "13,47");
				put("VAL_API_COD_PRODUCTO_LIBRANZA", "4,48");
				put("VAL_API_COD_PRODUCTO_LIBRANZA", "4");
				put("COD_PRD_SIN_TASAS", "13,47,12");
				put("VAL_API_COD_PRODUCTO_TDC", "12,49");
				put("CODIGOS_FINALIZACION_TARJETAS", "Exitoso,1111,1112,1113");
				put("API_ENDPOINT_ERROR_BYCODE" , "/com.bancolombia.commons.api.errores.v2/getErrorByCode");
				put("VAL_API_COD_PRODUCTO_TDC_JOVEN", "10");
				put("VAL_API_COD_PRODUCTO_TDC_IDEAL", "11");
				put("COD_IMAGEN_TDC_JOVEN", "320");
				put("COD_IMAGEN_TDC_IDEAL", "TI1");
				put("FWK_ENVIAR_CORREO_RUTA_PLANTILLA_TDC_SUCURSALES","/b1c/wrkdirr/ventasDigitales/plantillas/plantillaTDCSuc");
				put("FWK_ENVIAR_CORREO_NOMBRE_PLANTILLA","pdf_plantillacorreopdf.vm");
				put("VALIDACION_CICLO_DEFAULT","true");
				put("CANTIDAD_COD_IMAGEN_TDC","20");
				put("INCOMPATIBILIDAD_TDC_1","315,319,325,316");
				put("INCOMPATIBILIDAD_TDC_2","324");
				put("INCOMPATIBILIDAD_TDC_3","319,325,316,315");
				put("INCOMPATIBILIDAD_TDC_4","325,316,319,315");
				put("INCOMPATIBILIDAD_TDC_5","316,315,319,325");
				put("INCOMPATIBILIDAD_TDC_6","328");
				put("INCOMPATIBILIDAD_TDC_7","310,311,TI1,312,313,320");
				put("INCOMPATIBILIDAD_TDC_8","311,TI1,312,313,320,310");
				put("INCOMPATIBILIDAD_TDC_9","006");
				put("INCOMPATIBILIDAD_TDC_10","TI1,312,313,320,310,311");
				put("INCOMPATIBILIDAD_TDC_11","313,320,310,311,TI1,312");
				put("INCOMPATIBILIDAD_TDC_12","320,310,311,TI1,312,313");
				put("INCOMPATIBILIDAD_TDC_13","TI1,312,313,320");
				put("INCOMPATIBILIDAD_TDC_14","304,309,306");
				put("INCOMPATIBILIDAD_TDC_15","309,306,304");
				put("INCOMPATIBILIDAD_TDC_16","305");
				put("INCOMPATIBILIDAD_TDC_17","306,304,309");
				put("INCOMPATIBILIDAD_TDC_18","330,326");
				put("INCOMPATIBILIDAD_TDC_19","326,330");
				put("INCOMPATIBILIDAD_TDC_20","331");
				put("COD_IMAGEN_TDC_1","315");
				put("COD_IMAGEN_TDC_2","324");
				put("COD_IMAGEN_TDC_3","319");
				put("COD_IMAGEN_TDC_4","325");
				put("COD_IMAGEN_TDC_5","316");
				put("COD_IMAGEN_TDC_6","328");
				put("COD_IMAGEN_TDC_7","310");
				put("COD_IMAGEN_TDC_8","311");
				put("COD_IMAGEN_TDC_9","006");
				put("COD_IMAGEN_TDC_10","312");
				put("COD_IMAGEN_TDC_11","313");
				put("COD_IMAGEN_TDC_12","320");
				put("COD_IMAGEN_TDC_13","TI1");
				put("COD_IMAGEN_TDC_14","304");
				put("COD_IMAGEN_TDC_15","309");
				put("COD_IMAGEN_TDC_16","305");
				put("COD_IMAGEN_TDC_17","306");
				put("COD_IMAGEN_TDC_18","330");
				put("COD_IMAGEN_TDC_19","326");
				put("COD_IMAGEN_TDC_20","331");
				put("COD_CICLO_FACT_DEFAULT","21,26");
				put("COD_IMG_TDC_NO_MOSTRAR","310,405");
			}
		};
	}
	
	private static void InicializarPropiedades2() {
		propiedades2 = new HashMap<String, String>() {
			{
				put("API_DATOS_COMERCIALES_SUCURSAL_COD_ASESOR", "false");
				put("API_GESTION_PRECALCULADO_CLIENTE_TIPO_PRODUCTO", "14");
				put("VAL_TIEMPO_ESPERA_INICIAL_GESTION_CRED", "5");
				put("VAL_TIEMPO_REINTENTO_GESTION_CRED", "5");
				put("VAL_TIEMPO_TOTAL_GESTION_CRED", "20");
				put("VAL_TIEMPO_TOTAL_GESTION_CRED_MOM1","30");
				put("VAL_TIEMPO_TOTAL_GESTION_CRED_MOM2","50");
				put("PROXY_GESTION_SOLICITUD_CREDITO_CONSUMO_NOMBRE_TAREA_M1", "EsperarInformacionFinal");
				put("PROXY_GESTION_SOLICITUD_CREDITO_CONSUMO_NOMBRE_TAREA_M2", "EsperarAceptacionCliente");
				put("PROXY_GESTION_SOLICITUD_CREDITO_CONSUMO_NOMBRE_ARCHIVO_TRAZA", "TrazaLibreInversionSucursales.txt");
				put("VAL_API_ERRORES_ACTIVO", "true");
				put("VAL_API_ERRORES_COD_APLICACION", "99");
				put("MSG_DEFAULT_API", "En este momento el sistema no est� disponible. Por favor intenta m�s tarde.");
				put("API_VAR_CALL_PROXY_PRECALCULADO_CLIENTE", "API_VAR_CALL_PROXY_PRECALCULADO_CLIENTE");
				put("VAL_API_COD_PRODUCTO_CREDIAGIL", "13,47");
				put("COD_PRD_SIN_TASAS", "13,47,12");
				put("VAL_API_ERRORES_ACTIVO", "true");
				put("TIEMPO_GENERACION_TOKEN_NUEVO", "34");
				put("COD_IMG_TDC_NO_MOSTRAR","405");
			}
		};
	}

	private static void InicializarPropiedades4() {
		propiedades4 = new HashMap<String, String>() {
			{
				put("API_DATOS_COMERCIALES_SUCURSAL_COD_ASESOR", "true");
				put("API_GESTION_PRECALCULADO_CLIENTE_TIPO_PRODUCTO", "4,15,14");
				put("VAL_TIEMPO_ESPERA_INICIAL_GESTION_CRED", "5");
				put("VAL_TIEMPO_REINTENTO_GESTION_CRED", "5");
				put("VAL_TIEMPO_TOTAL_GESTION_CRED", "20");
				put("VAL_TIEMPO_TOTAL_GESTION_CRED_MOM1","30");
				put("VAL_TIEMPO_TOTAL_GESTION_CRED_MOM2","50");
				put("PROXY_GESTION_SOLICITUD_CREDITO_CONSUMO_NOMBRE_TAREA_M1", "EsperarInformacionFinal");
				put("PROXY_GESTION_SOLICITUD_CREDITO_CONSUMO_NOMBRE_TAREA_M2", "EsperarAceptacionCliente");
				put("PROXY_GESTION_SOLICITUD_CREDITO_CONSUMO_NOMBRE_ARCHIVO_TRAZA", "TrazaLibreInversionSucursales.txt");
				put("VAL_API_ERRORES_ACTIVO", "false");
				put("VAL_API_ERRORES_COD_APLICACION", "99");
				put("MSG_DEFAULT_API", "En este momento el sistema no está disponible. Por favor intenta más tarde.");
				put("API_VAR_CALL_PROXY_PRECALCULADO_CLIENTE", "API_VAR_CALL_PROXY_PRECALCULADO_CLIENTE");
				put("VAL_API_COD_PRODUCTO_CREDIAGIL", "13,47");
				put("COD_PRD_SIN_TASAS", "13,47,12");
				put("VAL_API_ERRORES_ACTIVO", "true");
				put("TIEMPO_GENERACION_TOKEN_NUEVO", "34");
				put("COD_IMG_TDC_NO_MOSTRAR","405");
			}
		};
	}

	private static void InicializarPropiedades3() {
		propiedades3 = new HashMap<String, String>() {
			{
				put("API_VAR_CALL_PROXY_PRECALCULADO_CLIENTE", "API_VAR_CALL_PROXY_PRECALCULADO_CLIENTE");
				put("VAL_API_COD_PRODUCTO_CREDIAGIL", "13,47");
				put("VAL_API_COD_PRODUCTO_LIBRANZA", "4,48");
				put("VAL_API_COD_PRODUCTO_LIBRANZA", "4");
				put("COD_PRD_SIN_TASAS", "13,47,12");
				put("VAL_API_COD_PRODUCTO_TDC", "12,49");
				put("CODIGOS_FINALIZACION_TARJETAS", "Exitoso,1111,1112,1113");
				put("API_ENDPOINT_ERROR_BYCODE" , "/com.bancolombia.commons.api.errores.v2/getErrorByCode");
				put("VAL_API_COD_PRODUCTO_TDC_JOVEN", "10");
				put("VAL_API_COD_PRODUCTO_TDC_IDEAL", "11");
				put("COD_IMAGEN_TDC_JOVEN", "320");
				put("COD_IMAGEN_TDC_IDEAL", "TI1");
				put("VALIDACION_CICLO_DEFAULT","false");
				put("VAL_TIEMPO_ESPERA_INICIAL_GESTION_CRED", "5");
				put("VAL_TIEMPO_REINTENTO_GESTION_CRED", "5");
				put("VAL_TIEMPO_TOTAL_GESTION_CRED", "20");
				put("VAL_TIEMPO_TOTAL_GESTION_CRED_MOM1","30");
				put("VAL_TIEMPO_TOTAL_GESTION_CRED_MOM2","50");
				put("COD_IMG_TDC_NO_MOSTRAR","405");
			}
		};
	}

	public static Map<String, String> getPropiedades() {
		if (propiedades == null) {
			InicializarPropiedades();
		}
		return propiedades;
	}
	
	public static Map<String, String> getPropiedades2() {
		if (propiedades2 == null) {
			InicializarPropiedades2();
		}
		return propiedades2;
	}
	
	public static Map<String, String> getPropiedades3() {
		if (propiedades == null) {
			InicializarPropiedades3();
		}
		return propiedades3;
	}

	public static Map<String, String> getPropiedades4() {
		if (propiedades4 == null) {
			InicializarPropiedades4();
		}
		return propiedades4;
	}

}
