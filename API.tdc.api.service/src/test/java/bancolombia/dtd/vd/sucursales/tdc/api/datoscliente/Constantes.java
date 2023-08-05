package bancolombia.dtd.vd.sucursales.tdc.api.datoscliente;

public class Constantes {
	
	public static String cargaUtilInfoAsesorCedula = "{'datoConsulta': '1018418391','tipoConsulta': '3','idSesion': '2werwd32rewr233de324d3f','pasoFuncional': 'paso_li_101'}";
	
	public static String cargaUtilInfoAsesorSucursal = "{'datoConsulta': '00005','tipoConsulta': '2','idSesion': '2werwd32rewr233de324d3f','pasoFuncional': 'paso_li_101'}";
	
	public static String respuestaCatalogo = "{\"esSatisfactorio\":\"true\",\"lista\":[{\"descripcion\":\"CAROL MELISSA JARAMILLO RODRIG|31105|00006|00000066|43583347|1020452408|CAJARAMI\",\"id\":\"1018418391\"},{\"descripcion\":\"SANTIAGO ANDRES LOPEZ ROJAS|9911|00005|00000066|43583347|71261558|SANTLOPE\",\"id\":\"1037644654\"}]}";
	
	public static String respuestaDatosCliente = "{\"informacionUbicacionCliente\": [\r\n" + "      {\r\n"
			+ "      \"codigoDireccionFuente\": \"0069421916\",\r\n"
			+ "      \"codigoTipoDireccionFuente\": \"Z001\",\r\n" + "      \"direccionPrincipal\": \"LABORAL 1\",\r\n"
			+ "      \"codigoCiudad\": \"8001000\",\r\n" + "      \"codigoDepartamento\": \"08\",\r\n"
			+ "      \"codigoPais\": {\"codigoISO3166Alfa2\": \"CO\"},\r\n" + "      \"telefonoFijo\": \"4556677\",\r\n"
			+ "      \"celular\": \"3015659286\",\r\n" + "      \"correoElectronico\": \"cgallego@co.ibm.com\",\r\n"
			+ "      \"codigoPostal\": \"0008001000\",\r\n" + "      \"barrioDireccionPrincipal\": \"Pescaito\"\r\n"
			+ "   },\r\n" + "      {\r\n" + "      \"codigoDireccionFuente\": \"0069421919\",\r\n"
			+ "      \"codigoTipoDireccionFuente\": \"Z002\",\r\n" + "      \"direccionPrincipal\": \"LABORAL 2\",\r\n"
			+ "      \"codigoCiudad\": \"17001000\",\r\n" + "      \"codigoDepartamento\": \"17\",\r\n"
			+ "      \"codigoPais\": {\"codigoISO3166Alfa2\": \"CO\"},\r\n"
			+ "      \"codigoPostal\": \"0017001000\"\r\n" + "   },\r\n" + "      {\r\n"
			+ "      \"codigoDireccionFuente\": \"0069421900\",\r\n"
			+ "      \"codigoTipoDireccionFuente\": \"Z001\",\r\n"
			+ "      \"direccionPrincipal\": \"Bulevar 12 # 212 Unidad Montañas Verdes Apto 234\",\r\n"
			+ "      \"codigoCiudad\": \"5001000\",\r\n" + "      \"codigoDepartamento\": \"05\",\r\n"
			+ "      \"codigoPais\": {\"codigoISO3166Alfa2\": \"CO\"},\r\n"
			+ "      \"codigoPostal\": \"0005001000\",\r\n" + "      \"codigoCorrespondencia\": \"X\"\r\n" + "   }\r\n"
			+ "]}";
	
	public static String cargaUtilSolicitudCreditoConsumoPaso1 = "{	\"idSesion\": \"sadsadsadsa\",\r\n" + 
			" 	\"pasoFuncional\": \"Paso1\",\r\n" + 
			"   	\"paso\": \"paso1\",\r\n" + 
			"   	\"tipoVenta\": \"P\",\r\n" + 
			" 	\"tipodocumento\": \"FS001\",\r\n" + 
			"   	\"numeroDocumento\": \"1111111111\",\r\n" + 
			"   	\"idProducto\": \"14\",\r\n" + 
			" 	\"sucursalRadicacion\": \"\",\r\n" + 
			"   	\"codigoAsesorComercial\": \"\",\r\n" + 
			"   	\"codigoReferido\": \"\",\r\n" + 
			"   	\"producto\":[\r\n" + 
			"   		{\r\n" + 
			"   		\"idProducto\": \"14\"\r\n" + 
			"   		}\r\n" + 
			"   	]\r\n" + 
			"}";
	
	public static String respuestaOfertaSolicitudCreditoConsumoPaso1 = "{  \r\n" + "   \"cabecera\":{  \r\n"
			+ "      \"estadoTransaccion\":\"\",\r\n" + "      \"pasoFuncional\":\"paso1\",\r\n"
			+ "      \"numeroSolicitud\":\"VDI_2018_3795\",\r\n" + "      \"extensionFuncional\":[  \r\n"
			+ "         {  \r\n" + "            \"llave\":\"\",\r\n" + "            \"valor\":\"\"\r\n"
			+ "         }\r\n" + "      ]\r\n" + "   },\r\n" + "   \"oferta\":{  \r\n"
			+ "      \"informacionCliente\":{  \r\n" + "         \"idSolicitante\":\"53924\",\r\n"
			+ "         \"tipoDocumento\":\"FS001\",\r\n" + "         \"numeroDocumento\":\"1059786192\",\r\n"
			+ "         \"nombreCompletoCliente\":\" Juan Andres Bedoya Rios\",\r\n"
			+ "         \"direccionPrincipal\":\"PRINCIPAL\",\r\n" + "         \"codigoCiudad\":\"5001000\",\r\n"
			+ "         \"codigoDepartamento\":\"05\",\r\n" + "         \"codigoPais\":\"CO\"\r\n" + "      },\r\n"
			+ "      \"producto\":[  \r\n" + "         {  \r\n" + "            \"idProducto\":\"14\",\r\n"
			+ "            \"nombreProducto\":\"libre inversion\",\r\n"
			+ "            \"montoOtorgado\":\"500000000.00\",\r\n" + "            \"categoria\":[  \r\n"
			+ "               {  \r\n" + "                  \"codigoCategoria\":\"Credito de Consumo\",\r\n"
			+ "                  \"subproducto\":[  \r\n" + "                     {  \r\n"
			+ "                        \"idSubproducto\":\"174991\",\r\n"
			+ "                        \"nombreSubproducto\":\"LIBRE INVERSION\",\r\n"
			+ "                        \"descripcionSubproducto\":\"LIBRE INVERSION\",\r\n"
			+ "                        \"cupoMinimo\":\"0.00\",\r\n"
			+ "                        \"cupoMaximo\":\"500000000.00\",\r\n"
			+ "                        \"codigoImagen\":\"\",\r\n"
			+ "                        \"listaCondiciones\":[  \r\n" + "\r\n" + "                        ],\r\n"
			+ "                        \"tasas\":[  \r\n" + "                           {  \r\n"
			+ "                              \"grupoRiesgo\":\"G2\",\r\n"
			+ "                              \"montoInferiorProducto\":1000000.0,\r\n"
			+ "                              \"montoSuperiorProducto\":11999999.0,\r\n"
			+ "                              \"plazoInferior\":6,\r\n"
			+ "                              \"plazoSuperior\":36,\r\n"
			+ "                              \"tasaMesVencida\":1.775412,\r\n"
			+ "                              \"tasaNominalAnualMesVencido\":0.213049,\r\n"
			+ "                              \"tasaEfectivaAnual\":0.2351,\r\n"
			+ "                              \"tasaMora\":27.39\r\n" + "                           },\r\n"
			+ "                           {\r\n" + "					\"grupoRiesgo\": \"G2\",\r\n"
			+ "					\"montoInferiorProducto\": 1000000.00,\r\n"
			+ "					\"montoSuperiorProducto\": 11999999.00,\r\n"
			+ "					\"plazoInferior\": 6,\r\n" + "					\"plazoSuperior\": 59,\r\n"
			+ "					\"tasaMesVencida\": 2.099917,\r\n"
			+ "					\"tasaNominalAnualMesVencido\": 0.25199004,\r\n"
			+ "					\"tasaEfectivaAnual\": 0.2832,\r\n" + "					\"tasaMora\": 20.42\r\n"
			+ "				}\r\n" + "                        ],\r\n"
			+ "                        \"seguroProducto\":[  \r\n" + "                           {  \r\n"
			+ "                              \"montoMinimoSeguro\":1000000,\r\n"
			+ "                              \"montoMaximoSeguro\":20000000,\r\n"
			+ "                              \"factor\":0.00145\r\n" + "                           },\r\n"
			+ "                           {  \r\n" + "                              \"montoMinimoSeguro\":20000001,\r\n"
			+ "                              \"montoMaximoSeguro\":50000000,\r\n"
			+ "                              \"factor\":0.00149\r\n" + "                           },\r\n"
			+ "                           {  \r\n" + "                              \"montoMinimoSeguro\":50000001,\r\n"
			+ "                              \"montoMaximoSeguro\":100000000,\r\n"
			+ "                              \"factor\":0.00151\r\n" + "                           },\r\n"
			+ "                           {  \r\n"
			+ "                              \"montoMinimoSeguro\":100000001,\r\n"
			+ "                              \"montoMaximoSeguro\":200000000,\r\n"
			+ "                              \"factor\":0.00155\r\n" + "                           },\r\n"
			+ "                           {  \r\n"
			+ "                              \"montoMinimoSeguro\":200000001,\r\n"
			+ "                              \"montoMaximoSeguro\":500000001,\r\n"
			+ "                              \"factor\":0.00156\r\n" + "                           }\r\n"
			+ "                        ]\r\n" + "                     }\r\n" + "                  ]\r\n"
			+ "               }\r\n" + "            ]\r\n" + "         }\r\n" + "      ]\r\n" + "   }\r\n" + "}\r\n";

	public static String cargaUtilSolicitudCreditoConsumoPaso2 = "{  	\"idSesion\":\"3123123123213\",\r\n" + 
			"	\"paso\":\"paso2\",\r\n" + 
			"	\"tipoVenta\":\"Preaprobado\",\r\n" + 
			"	\"tipodocumento\":\"FS001\",\r\n" + 
			"	\"numeroDocumento\":\"98768001\",\r\n" + 
			"	\"idSesion\":\"sadsadsadsa\",\r\n" + 
			"	\"idProducto\":\"14\",\r\n" + 
			"	\"sucursalRadicacion\":\"245\",\r\n" + 
			"	\"codigoAsesorComercial\":\"423\",\r\n" + 
			"	\"codigoReferido\":\"1112\",\r\n" + 
			"	\"numeroSolicitud\":\"1111111111-9153-14\",\r\n" + 
			"	\"nombreTarea\":\"\",\r\n" + 
			"	\"codigoSolicitante\":\"\",\r\n" + 
			"	\"firmaCliente\":\"\",\r\n" + 
			"	\"direccionEntrega\":\"\",\r\n" + 
			"	\"municipioEntrega\":\"\",\r\n" + 
			"	 \"producto\": [{\"idProducto\":\"14\",\r\n" + 
			"			       \"cupoElegido\":\"4000000\",\r\n" + 
			"			               \"plazo\":\"36\",\r\n" + 
			"\r\n" + 
			"			       \"cuota\":\"430000\",\r\n" + 
			"			               \"tasaMV\":\"0.6\",\r\n" + 
			"			               \"tasaNAMV\":\"0.7\",\r\n" + 
			"\r\n" + 
			"			       \"tasaEA\":\"1.9\",\r\n" + 
			"			               \"tasaMora\":\"3.9\",\r\n" + 
			"\r\n" + 
			"			       \"factorSeguro\":\"3212\",\r\n" + 
			"			               \"costoTotalSeguro\":\"300000\",\r\n" + 
			"\r\n" + 
			"			       \"tipoCuentaDesembolso\":\"2\",\r\n" + 
			"			               \"cuentaDesembolso\":\"D-00003424234\",\r\n" + 
			"\r\n" + 
			"			       \"aceptaDebitoAutomatico\":\"true\",\r\n" + 
			"			               \"tipoCuentaDebitoAutomatico\":\"\",\r\n" + 
			"\r\n" + 
			"			       \"cuentaDebitoAutomatico\":\"\",\r\n" + 
			"			               \"beneficiario\":[{\r\n" + 
			"								\"tipoDocumento\":\"FS001\",\r\n" + 
			"								\"numeroDocumento\":\"12344354\",\r\n" + 
			"\r\n" + 
			"								\"nombreCompleto\":\"FFFFFFFFFFFFF\",\r\n" + 
			"								\"asignacion\":\"50\"\r\n" + 
			"								},\r\n" + 
			"								{\"tipoDocumento\":\"FS001\",\r\n" + 
			"\r\n" + 
			"								\"numeroDocumento\":\"77777\",\r\n" + 
			"								\"nombreCompleto\":\"AAAAAAAAAAA\",\r\n" + 
			"\r\n" + 
			"								\"asignacion\":\"50\"       \r\n" + 
			"								}]\r\n" + 
			"			         }]\r\n" + 
			"}";

	public static String respuestaOfertaSolicitudCreditoConsumoPaso2 = "{\r\n" + "	\"cabecera\":{\r\n"
			+ "		\"estadoTransaccion\": \"success\",\r\n" + "		\"pasoFuncional\": \"paso2\",\r\n"
			+ "		\"numeroSolicitud\": \"XXXXXXX\"\r\n" + "	},\r\n" + "	\"documentos\": [{\r\n"
			+ "		\"tipoDocumento\": \"1\",\r\n"
			+ "		\"documento\": \"ASDASD\",\r\n"
			+ "		\"descripcion\": \"sin descripcion1\"\r\n" + "	},\r\n" + "	{\r\n"
			+ "		\"tipoDocumento\": \"2\",\r\n"
			+ "		\"documento\": \"ASDASD\",\r\n"
			+ "		\"descripcion\": \"sin descripcion2\"\r\n" + "	}]		\r\n" + "}";

	public static String cargaUtilSolicitudCreditoConsumoPaso3 = "{\r\n" + 
			"    \"codigoAsesorComercial\": \"423\",\r\n" + 
			"    \"codigoReferido\": \"1112\",\r\n" + 
			"    \"codigoSolicitante\": \"\",\r\n" + 
			"    \"direccionEntrega\": \"\",\r\n" + 
			"    \"firmaCliente\": \"\",\r\n" + 
			"    \"idProducto\": \"14\",\r\n" + 
			"    \"idSesion\": \"sadsadsadsa\",\r\n" + 
			"    \"municipioEntrega\": \"\",\r\n" + 
			"    \"nombreTarea\": \"\",\r\n" + 
			"    \"numeroDocumento\": \"98768001\",\r\n" + 
			"    \"numeroSolicitud\": \"1111111111-9153-14\",\r\n" + 
			"    \"paso\": \"paso3\",\r\n" + 
			"    \"producto\": [{\r\n" + 
			"        \"aceptaDebitoAutomatico\": \"0\",\r\n" + 
			"        \"beneficiario\": [\r\n" + 
			"            {\r\n" + 
			"                \"asignacion\": \"50\",\r\n" + 
			"                \"nombreCompleto\": \"FFFFFFFFFFFFF\",\r\n" + 
			"                \"numeroDocumento\": \"12344354\",\r\n" + 
			"                \"tipoDocumento\": \"FS001\"\r\n" + 
			"            },\r\n" + 
			"            {\r\n" + 
			"                \"asignacion\": \"50\",\r\n" + 
			"                \"nombreCompleto\": \"AAAAAAAAAAA\",\r\n" + 
			"                \"numeroDocumento\": \"77777\",\r\n" + 
			"                \"tipoDocumento\": \"FS001\"\r\n" + 
			"            }\r\n" + 
			"        ],\r\n" + 
			"        \"costoTotalSeguro\": \"300000\",\r\n" + 
			"        \"cuentaDebitoAutomatico\": \"\",\r\n" + 
			"        \"cuentaDesembolso\": \"D-00003424234\",\r\n" + 
			"        \"cuota\": \"430000\",\r\n" + 
			"        \"cupoElegido\": \"4000000\",\r\n" + 
			"        \"factorSeguro\": \"3212\",\r\n" + 
			"        \"idProducto\": \"14\",\r\n" + 
			"        \"plazo\": \"36\",\r\n" + 
			"        \"tasaEA\": \"1.9\",\r\n" + 
			"        \"tasaMV\": \"0.6\",\r\n" + 
			"        \"tasaMora\": \"3.9\",\r\n" + 
			"        \"tasaNAMV\": \"0.7\",\r\n" + 
			"        \"tipoCuentaDebitoAutomatico\": \"\",\r\n" + 
			"        \"tipoCuentaDesembolso\": \"2\"\r\n" + 
			"    }],\r\n" + 
			"    \"sucursalRadicacion\": \"245\",\r\n" + 
			"    \"tipoVenta\": \"Preaprobado\",\r\n" + 
			"    \"tipodocumento\": \"FS001\"\r\n" + 
			"}";

	public static String respuestaOfertaSolicitudCreditoConsumoPaso3 = "{\r\n" + "	\"cabecera\":{\r\n"
			+ "		\"estadoTransaccion\": \"success\",\r\n" + "		\"pasoFuncional\": \"paso3\",\r\n"
			+ "		\"numeroSolicitud\": \"33254-TDCP\"\r\n" + "	}, \r\n" + "     \"confirmacionTransaccion\":{\r\n"
			+ "           \"codigo\":\"ok\",\r\n" + "           \"descripcion\":\"34323233\"\r\n" + "     }\r\n" + "}";

	public static String cargaUtilRecalculo = "{\r\n" + 
			" \"idSesion\":\"3123123123213\",\r\n" + 
			" \"paso\":\"paso3\",\r\n" + 
			" \"tipoVenta\":\"Preaprobado\",\r\n" + 
			" \"tipodocumento\":\"FS001\",\r\n" + 
			" \"numeroDocumento\":\"98768001\",\r\n" + 
			" \"idSesion\":\"sadsadsadsa\",\r\n" + 
			" \"idProducto\":\"14\",\r\n" + 
			" \"sucursalRadicacion\":\"245\",\r\n" + 
			" \"codigoAsesorComercial\":\"423\",\r\n" + 
			" \"codigoReferido\":\"1112\",\r\n" + 
			" \"numeroSolicitud\":\"1111111111-9153-14\",\r\n" + 
			" \"nombreTarea\":\"\",\r\n" + 
			" \"codigoSolicitante\":\"\",\r\n" + 
			" \"firmaCliente\":\"\",\r\n" + 
			" \"direccionEntrega\":\"\",\r\n" + 
			" \"municipioEntrega\":\"\",\r\n" + 
			" \"producto\":[{\r\n" + 
			"       \"idProducto\":\"14\",\r\n" + 
			"       \"cupoElegido\":\"4000000\",\r\n" + 
			"       \"plazo\":\"36\",\r\n" + 
			"       \"cuota\":\"430000\",\r\n" + 
			"       \"tasaMV\":\"0.6\",\r\n" + 
			"       \"tasaNAMV\":\"0.7\",\r\n" + 
			"       \"tasaEA\":\"1.9\",\r\n" + 
			"       \"tasaMora\":\"3.9\",\r\n" + 
			"       \"factorSeguro\":\"3212\",\r\n" + 
			"       \"costoTotalSeguro\":\"300000\",\r\n" + 
			"       \"tipoCuentaDesembolso\":\"2\",\r\n" + 
			"       \"cuentaDesembolso\":\"D-00003424234\",\r\n" + 
			"       \"aceptaDebitoAutomatico\":\"0\",\r\n" + 
			"       \"tipoCuentaDebitoAutomatico\":\"\",\r\n" + 
			"       \"cuentaDebitoAutomatico\":\"\",\r\n" + 
			"       \"beneficiario\":[{\r\n" + 
			"         \"tipoDocumento\":\"FS001\",\r\n" + 
			"         \"numeroDocumento\":\"12344354\",\r\n" + 
			"         \"nombreCompleto\":\"FFFFFFFFFFFFF\",\r\n" + 
			"         \"asignacion\":\"50\"\r\n" + 
			"       },{\r\n" + 
			"         \"tipoDocumento\":\"FS001\",\r\n" + 
			"         \"numeroDocumento\":\"77777\",\r\n" + 
			"         \"nombreCompleto\":\"AAAAAAAAAAA\",\r\n" + 
			"         \"asignacion\":\"50\"\r\n" + 
			"       }]\r\n" + 
			"  }]\r\n" + 
			"}";

	public static String respuestaOfertaRecalculo = "{  \r\n" + "   \"cabecera\":{  \r\n"
			+ "      \"estadoTransaccion\":\"\",\r\n" + "      \"pasoFuncional\":\"paso1\",\r\n"
			+ "      \"numeroSolicitud\":\"VDI_2018_3795\",\r\n" + "      \"extensionFuncional\":[  \r\n"
			+ "         {  \r\n" + "            \"llave\":\"\",\r\n" + "            \"valor\":\"\"\r\n"
			+ "         }\r\n" + "      ]\r\n" + "   },\r\n" + "   \"oferta\":{  \r\n"
			+ "      \"informacionCliente\":{  \r\n" + "         \"idSolicitante\":\"53924\",\r\n"
			+ "         \"tipoDocumento\":\"FS001\",\r\n" + "         \"numeroDocumento\":\"1059786192\",\r\n"
			+ "         \"nombreCompletoCliente\":\" Juan Andres Bedoya Rios\",\r\n"
			+ "         \"direccionPrincipal\":\"PRINCIPAL\",\r\n" + "         \"codigoCiudad\":\"5001000\",\r\n"
			+ "         \"codigoDepartamento\":\"05\",\r\n" + "         \"codigoPais\":\"CO\"\r\n" + "      },\r\n"
			+ "      \"producto\":[  \r\n" + "         {  \r\n" + "            \"idProducto\":\"14\",\r\n"
			+ "            \"nombreProducto\":\"libre inversion\",\r\n"
			+ "            \"montoOtorgado\":\"500000000.00\",\r\n" + "            \"categoria\":[  \r\n"
			+ "               {  \r\n" + "                  \"codigoCategoria\":\"Credito de Consumo\",\r\n"
			+ "                  \"subproducto\":[  \r\n" + "                     {  \r\n"
			+ "                        \"idSubproducto\":\"174991\",\r\n"
			+ "                        \"nombreSubproducto\":\"LIBRE INVERSION\",\r\n"
			+ "                        \"descripcionSubproducto\":\"LIBRE INVERSION\",\r\n"
			+ "                        \"cupoMinimo\":\"0.00\",\r\n"
			+ "                        \"cupoMaximo\":\"500000000.00\",\r\n"
			+ "                        \"codigoImagen\":\"\",\r\n"
			+ "                        \"listaCondiciones\":[  \r\n" + "\r\n" + "                        ],\r\n"
			+ "                        \"tasas\":[  \r\n" + "                           {  \r\n"
			+ "                              \"grupoRiesgo\":\"G2\",\r\n"
			+ "                              \"montoInferiorProducto\":1000000.0,\r\n"
			+ "                              \"montoSuperiorProducto\":11999999.0,\r\n"
			+ "                              \"plazoInferior\":6,\r\n"
			+ "                              \"plazoSuperior\":36,\r\n"
			+ "                              \"tasaMesVencida\":1.775412,\r\n"
			+ "                              \"tasaNominalAnualMesVencido\":0.213049,\r\n"
			+ "                              \"tasaEfectivaAnual\":0.2351,\r\n"
			+ "                              \"tasaMora\":27.39\r\n" + "                           },\r\n"
			+ "                           {\r\n" + "					\"grupoRiesgo\": \"G2\",\r\n"
			+ "					\"montoInferiorProducto\": 1000000.00,\r\n"
			+ "					\"montoSuperiorProducto\": 11999999.00,\r\n"
			+ "					\"plazoInferior\": 6,\r\n" + "					\"plazoSuperior\": 59,\r\n"
			+ "					\"tasaMesVencida\": 2.099917,\r\n"
			+ "					\"tasaNominalAnualMesVencido\": 0.25199004,\r\n"
			+ "					\"tasaEfectivaAnual\": 0.2832,\r\n" + "					\"tasaMora\": 20.42\r\n"
			+ "				}\r\n" + "                        ],\r\n"
			+ "                        \"seguroProducto\":[  \r\n" + "                           {  \r\n"
			+ "                              \"montoMinimoSeguro\":1000000,\r\n"
			+ "                              \"montoMaximoSeguro\":20000000,\r\n"
			+ "                              \"factor\":0.00145\r\n" + "                           },\r\n"
			+ "                           {  \r\n" + "                              \"montoMinimoSeguro\":20000001,\r\n"
			+ "                              \"montoMaximoSeguro\":50000000,\r\n"
			+ "                              \"factor\":0.00149\r\n" + "                           },\r\n"
			+ "                           {  \r\n" + "                              \"montoMinimoSeguro\":50000001,\r\n"
			+ "                              \"montoMaximoSeguro\":100000000,\r\n"
			+ "                              \"factor\":0.00151\r\n" + "                           },\r\n"
			+ "                           {  \r\n"
			+ "                              \"montoMinimoSeguro\":100000001,\r\n"
			+ "                              \"montoMaximoSeguro\":200000000,\r\n"
			+ "                              \"factor\":0.00155\r\n" + "                           },\r\n"
			+ "                           {  \r\n"
			+ "                              \"montoMinimoSeguro\":200000001,\r\n"
			+ "                              \"montoMaximoSeguro\":500000001,\r\n"
			+ "                              \"factor\":0.00156\r\n" + "                           }\r\n"
			+ "                        ]\r\n" + "                     }\r\n" + "                  ]\r\n"
			+ "               }\r\n" + "            ]\r\n" + "         }\r\n" + "      ]\r\n" + "   }\r\n" + "}\r\n";
	
	public static String cargaUtilEnvioCorreo = "{\"idSesion\":\"3123123123213\",\r\n" + 
			"\"nombreCliente\":\"Pepito Perez\", \"documentoCliente\":\"123456789\" ,\r\n" + 
			"\"correo\":\"leidy.roman@ibm.com\", \"documentoPdf\":\"hkhkghjkgkfjhbjklhop\",\r\n" + 
			"\"cupoSolicitado\":\"5000000\", \"nombreProducto\":\"Libre inversion\",\r\n" + 
			"\"pasoFuncional\":\"Paso5\",\r\n" + 
			"\"tasaNominal\":\"0.22\",\r\n" + 
			"\"plazoCredito\":\"36\",\r\n" + 
			"\"valorCuota\":\"34453\",\r\n" + 
			"\"cuentaDebito\":\"D-123456789087\",\r\n" + 
			"\"numeroCredito\":\"21312321\"}";
	
	public static String cargaUtilGestionProspectoConsumoRQ = "{\r\n" + 
			"  \"idSesion\": \"987987uytyut765765ytrerte\",\r\n" + 
			"  \"propiedades\": [\r\n" + 
			"    {\r\n" + 
			"      \"llave\": \"PROXY_ENDPOINT_GESTIONAR_PROSPECTO\",\r\n" + 
			"      \"valor\": \"http://localhost:9081/GestionarProspectoLI/GestionarProspectoLI\"\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"      \"llave\": \"PROXY_SYSTEM_ID_GESTIONAR_PROSPECTO\",\r\n" + 
			"      \"valor\": \"AW1177\"\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"      \"llave\": \"PROXY_CLASSIFICATION_GESTIONAR_PROSPECTO\",\r\n" + 
			"      \"valor\": \"http://grupobancolombia.com/clas/AplicacionesActuales#\"\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"      \"llave\": \"PROXY_GESTIONAR_PROSPECTO_NAMESPACE\",\r\n" + 
			"      \"valor\": \"http://grupobancolombia.com/intf/Clientes/GestionClientes/GestionarProspecto/V2.0\"\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"      \"llave\": \"PROXY_REQUEST_TIMEOUT_GESTIONAR_PROSPECTO\",\r\n" + 
			"      \"valor\": \"600\"\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"      \"llave\": \"PROXY_CONNECTION_TIMEOUT_GESTIONAR_PROSPECTO\",\r\n" + 
			"      \"valor\": \"600\"\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"      \"llave\": \"PROXY_CODIGO_RESPUESTA_EXITOSA_GESTIONAR_PROSPECTO\",\r\n" + 
			"      \"valor\": \"000\"\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"      \"llave\": \"PROXY_CODIGO_RESPUESTA_EXITOSA_GESTIONAR_PROSPECTO\",\r\n" + 
			"      \"valor\": \"000\"\r\n" + 
			"    }\r\n" + 
			"  ],\r\n" + 
			"  \"tipoDocumento\": \"FS001\",\r\n" + 
			"  \"numeroDocumento\": \"220818104\",\r\n" + 
			"  \"userName\": \"user\",\r\n" + 
			"  \"infoVeridica\": \"1\",\r\n" + 
			"  \"mdActualizacion\": \"10\",\r\n" + 
			"  \"llaveDireccion\": \"0071011808\",\r\n" + 
			"  \"correspondencia\": \"1\",\r\n" + 
			"  \"tipoDireccion\": \"Z001\",\r\n" + 
			"  \"direccion\": \"Manzana 10 # 2\",\r\n" + 
			"  \"pais\": \"CO\",\r\n" + 
			"  \"departamento\": \"05\",\r\n" + 
			"  \"ciudad\": \"5045000\",\r\n" + 
			"  \"email\": \"prueba.pru.co\",\r\n" + 
			"  \"celular\": \"3122222222\"\r\n" + 
			"}";
	
	public static String cargaUtilVerTarjetas = "{  \r\n" + 
			"   \"producto\":[  \r\n" + 
			"      {  \r\n" + 
			"         \"beneficiario\":[  \r\n" + 
			"\r\n" + 
			"         ],\r\n" + 
			"      }\r\n" + 
			"   ],\r\n" + 
			"   \"numeroSolicitud\":\"1111111-3554-12\",\r\n" + 
			"   \"paso\":\"paso1\",\r\n" + 
			"   \"cupoElegido\":\"8000000\",\r\n" + 
			"   \"idProducto\":\"12\"\r\n" +
			"   \"idSesion\":\"YbtMMy0D8-i4vdHfwTJTsw\",\r\n" + 
			"   \"pasoFuncional\":\"paso_li_201\",\r\n" + 
			"   \"hash\":\"619ff3bb9e9e824e615f7eb88deb7d7e\"\r\n" + 
			"}";
	
	public static String respuestaOfertaVerTarjetasSinCiclo = "{\"cabecera\":{\"estadoTransaccion\":\"\",\"pasoFuncional\":\"paso1\",\"numeroSolicitud\":\"SUC_2020_113270\",\"extensionFuncional\":[{\"llave\":\"\",\"valor\":\"\"}]},\"oferta\":{\"informacionCliente\":{\"idSolicitante\":\"159567\",\"tipoDocumento\":\"FS001\",\"numeroDocumento\":\"1989672702\",\"nombreCompletoCliente\":\" Humberto Adolfo Vargas Jimenez\",\"direccionPrincipal\":\"CL 81A D 52DD 11 LO 202\",\"codigoCiudad\":\"5001011\",\"codigoDepartamento\":\"5\",\"codigoPais\":\"CO\",\"telefono\":\"6424591\",\"celular\":\"3090100588\",\"correoElectronicoCliente\":\"dabran@bancolombia.com.co\",\"codigoDireccionPrincipalFuente\":\"0075291493\"},\"producto\":[{\"idProducto\":\"21\",\"nombreProducto\":\"MASTER NEGOCIOS MICROPYME\",\"montoOtorgado\":\"700000.00\",\"categoria\":[{\"codigoCategoria\":\"Master\",\"subproducto\":[{\"idSubproducto\":\"457659\",\"nombreSubproducto\":\"Master Negocios Micropyme\",\"descripcionSubproducto\":\"Master Negocios Micropyme\",\"cupoMinimo\":500000,\"cupoMaximo\":700000,\"codigoImagen\":\"326\",\"listaCondiciones\":[\"Cubre tus viajes empresariales, visita clientes y asiste a eventos. \u00a1Ampl\u00eda tu red de contactos!\",\"11,400 COP = 6 Puntos\",\"Cuota de Manejo Mensual: $23,300. \n Exoneraci\u00f3n de cuota de manejo por 6 meses. La exoneraci\u00f3n se inica desde la fecha de activaci\u00f3n.\"],\"comisionDisponibilidad\":0,\"isProductOwner\":false,\"planes\":[]},{\"idSubproducto\":\"457661\",\"nombreSubproducto\":\"MASTERCARD AGRO\",\"descripcionSubproducto\":\"MASTERCARD AGRO\",\"cupoMinimo\":500000,\"cupoMaximo\":700000,\"codigoImagen\":\"326\",\"listaCondiciones\":[],\"comisionDisponibilidad\":0,\"isProductOwner\":false,\"planes\":[]}]}]}],\"tarjetasCredito\":{\"cicloFactura\":\"\",\"nombreRealce\":\"\"}}}";
	
	public static String respuestaOfertaVerTarjetas = "{\r\n" + 
			"    \"cabecera\":\r\n" + 
			"    {\r\n" + 
			"        \"estadoTransaccion\": \"\",\r\n" + 
			"        \"pasoFuncional\": \"paso1\",\r\n" + 
			"        \"numeroSolicitud\": \"SUC_2020_113270\",\r\n" + 
			"        \"extensionFuncional\": [\r\n" + 
			"        {\r\n" + 
			"            \"llave\": \"\",\r\n" + 
			"            \"valor\": \"\"\r\n" + 
			"        }]\r\n" + 
			"    },\r\n" + 
			"    \"oferta\":\r\n" + 
			"    {\r\n" + 
			"        \"informacionCliente\":\r\n" + 
			"        {\r\n" + 
			"            \"idSolicitante\": \"159567\",\r\n" + 
			"            \"tipoDocumento\": \"FS001\",\r\n" + 
			"            \"numeroDocumento\": \"1989672702\",\r\n" + 
			"            \"nombreCompletoCliente\": \" Humberto Adolfo Vargas Jimenez\",\r\n" + 
			"            \"direccionPrincipal\": \"CL 81A D 52DD 11 LO 202\",\r\n" + 
			"            \"codigoCiudad\": \"5001011\",\r\n" + 
			"            \"codigoDepartamento\": \"5\",\r\n" + 
			"            \"codigoPais\": \"CO\",\r\n" + 
			"            \"telefono\": \"6424591\",\r\n" + 
			"            \"celular\": \"3090100588\",\r\n" + 
			"            \"correoElectronicoCliente\": \"dabran@bancolombia.com.co\",\r\n" + 
			"            \"codigoDireccionPrincipalFuente\": \"0075291493\"\r\n" + 
			"        },\r\n" + 
			"        \"producto\": [\r\n" + 
			"        {\r\n" + 
			"            \"idProducto\": \"21\",\r\n" + 
			"            \"nombreProducto\": \"MASTER NEGOCIOS MICROPYME\",\r\n" + 
			"            \"montoOtorgado\": \"700000.00\",\r\n" + 
			"            \"categoria\": [\r\n" + 
			"            {\r\n" + 
			"                \"codigoCategoria\": \"Master\",\r\n" + 
			"                \"subproducto\": [\r\n" + 
			"                {\r\n" + 
			"                    \"idSubproducto\": \"457659\",\r\n" + 
			"                    \"nombreSubproducto\": \"Master Negocios Micropyme\",\r\n" + 
			"                    \"descripcionSubproducto\": \"Master Negocios Micropyme\",\r\n" + 
			"                    \"cupoMinimo\": 500000,\r\n" + 
			"                    \"cupoMaximo\": 700000,\r\n" + 
			"                    \"codigoImagen\": \"326\",\r\n" + 
			"                    \"listaCondiciones\": [\"Cubre tus viajes empresariales, visita clientes y asiste a eventos. \\u00a1Ampl\\u00eda tu red de contactos!\", \"11,400 COP = 6 Puntos\", \"Cuota de Manejo Mensual: $23,300. \\n Exoneraci\\u00f3n de cuota de manejo por 6 meses. La exoneraci\\u00f3n se inica desde la fecha de activaci\\u00f3n.\"],\r\n" + 
			"                    \"comisionDisponibilidad\": 0,\r\n" + 
			"                    \"isProductOwner\": false,\r\n" + 
			"                    \"planes\": [],                    \r\n" + 
			"			        \"tarjetasCredito\":[\r\n" + 
			"						{\r\n" + 
			"				            \"cicloFactura\": [\"15\", \"30\"],\r\n" + 
			"				            \"nombreRealce\": \"HAROLD PINTO\"\r\n" + 
			"				        }\r\n" + 
			"			        ]\r\n" + 
			"                },\r\n" + 
			"                {\r\n" + 
			"                    \"idSubproducto\": \"457661\",\r\n" + 
			"                    \"nombreSubproducto\": \"MASTERCARD AGRO\",\r\n" + 
			"                    \"descripcionSubproducto\": \"MASTERCARD AGRO\",\r\n" + 
			"                    \"cupoMinimo\": 500000,\r\n" + 
			"                    \"cupoMaximo\": 700000,\r\n" + 
			"                    \"codigoImagen\": \"326\",\r\n" + 
			"                    \"listaCondiciones\": [],\r\n" + 
			"                    \"comisionDisponibilidad\": 0,\r\n" + 
			"                    \"isProductOwner\": false,\r\n" + 
			"                    \"planes\": [],                    \r\n" + 
			"			        \"tarjetasCredito\":[\r\n" + 
			"						{\r\n" + 
			"				            \"cicloFactura\": [\"15\", \"30\"],\r\n" + 
			"				            \"nombreRealce\": \"HAROLD PINTO\"\r\n" + 
			"				        }\r\n" + 
			"			        ]	        \r\n" + 
			"                }]\r\n" + 
			"            }]\r\n" + 
			"        }]\r\n" + 
			"    }\r\n" + 
			"}";
	
	public static String respuestaOfertaVerTarjetas21 = "{\r\n" + 
			"    \"cabecera\":\r\n" + 
			"    {\r\n" + 
			"        \"estadoTransaccion\": \"\",\r\n" + 
			"        \"extensionFuncional\": [\r\n" + 
			"        {\r\n" + 
			"            \"llave\": \"\",\r\n" + 
			"            \"valor\": \"\"\r\n" + 
			"        }],\r\n" + 
			"        \"numeroSolicitud\": \"SUC_2020_162711\",\r\n" + 
			"        \"pasoFuncional\": \"paso1\"\r\n" + 
			"    },\r\n" + 
			"    \"oferta\":\r\n" + 
			"    {\r\n" + 
			"        \"informacionCliente\":\r\n" + 
			"        {\r\n" + 
			"            \"celular\": \"3190568837\",\r\n" + 
			"            \"codigoCiudad\": \"5001002\",\r\n" + 
			"            \"codigoDepartamento\": \"68\",\r\n" + 
			"            \"codigoDireccionPrincipalFuente\": \"0075291512\",\r\n" + 
			"            \"codigoPais\": \"CO\",\r\n" + 
			"            \"correoElectronicoCliente\": \"dabran@bancolombia.com.co\",\r\n" + 
			"            \"direccionPrincipal\": \"CL REAL 28 72\",\r\n" + 
			"            \"idSolicitante\": \"203728\",\r\n" + 
			"            \"nombreCompletoCliente\": \" Miguel Vargas Parra\",\r\n" + 
			"            \"numeroDocumento\": \"1989672707\",\r\n" + 
			"            \"telefono\": \"6711636\",\r\n" + 
			"            \"tipoDocumento\": \"FS001\"\r\n" + 
			"        },\r\n" + 
			"        \"producto\": [\r\n" + 
			"        {\r\n" + 
			"            \"categoria\": [\r\n" + 
			"            {\r\n" + 
			"                \"codigoCategoria\": \"Master\",\r\n" + 
			"                \"subproducto\": [\r\n" + 
			"                {\r\n" + 
			"                    \"codigoImagen\": \"326\",\r\n" + 
			"                    \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                    \"cupoMaximo\": 850000000.0,\r\n" + 
			"                    \"cupoMinimo\": 500000.0,\r\n" + 
			"                    \"descripcionSubproducto\": \"Master Negocios Micropyme\",\r\n" + 
			"                    \"idSubproducto\": \"568191\",\r\n" + 
			"                    \"isProductOwner\": false,\r\n" + 
			"                    \"listaCondiciones\": [\r\n" + 
			"                        \"Cubre tus viajes empresariales, visita clientes y asiste a eventos. ¡Amplía tu red de contactos!\",\r\n" + 
			"                        \"Con tus compras acumula Puntos Colombia que podrás redimir en tecnología, implementos para oficina, entre otros para ti y tu empresa.\",\r\n" + 
			"                        \"Acumulación: 6 Puntos por cada $11,400 en compras.\",\r\n" + 
			"                        \"Cuota de Manejo Mensual: 24.300.      \\n Exoneración de cuota de manejo por 6 meses. La exoneración se inica desde la fecha de activación.\"\r\n" + 
			"                    ],\r\n" + 
			"                    \"nombreSubproducto\": \"Master Negocios Micropyme\",\r\n" + 
			"                    \"planes\": []\r\n" + 
			"                },\r\n" + 
			"                {\r\n" + 
			"                    \"codigoImagen\": \"330\",\r\n" + 
			"                    \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                    \"cupoMaximo\": 300000000.0,\r\n" + 
			"                    \"cupoMinimo\": 500000.0,\r\n" + 
			"                    \"descripcionSubproducto\": \"MASTERCARD AGRO\",\r\n" + 
			"                    \"idSubproducto\": \"568193\",\r\n" + 
			"                    \"isProductOwner\": false,\r\n" + 
			"                    \"listaCondiciones\": [\r\n" + 
			"                        \"Asistencias presenciales y telefónicas con agrónomos, veterinarios y zootecnistas.\\n\",\r\n" + 
			"                        \"Protección en compras agrícolas.\\n\\n\",\r\n" + 
			"                        \"Beneficios y descuentos en comercios aliados.\",\r\n" + 
			"                        \"Acumulación: 6 Puntos por cada $11,400 en compras.\",\r\n" + 
			"                        \"Cuota de Manejo Mensual: 15.000.  \\n Exoneración de cuota de manejo por 6 meses. La exoneración se inica desde la fecha de activación.\"\r\n" + 
			"                    ],\r\n" + 
			"                    \"nombreSubproducto\": \"MASTERCARD AGRO\",\r\n" + 
			"                    \"planes\": []\r\n" + 
			"                }]\r\n" + 
			"            },\r\n" + 
			"            {\r\n" + 
			"                \"codigoCategoria\": \"Visa\",\r\n" + 
			"                \"subproducto\": [\r\n" + 
			"                {\r\n" + 
			"                    \"codigoImagen\": \"331\",\r\n" + 
			"                    \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                    \"cupoMaximo\": 300000000.0,\r\n" + 
			"                    \"cupoMinimo\": 500000.0,\r\n" + 
			"                    \"descripcionSubproducto\": \"VISA LIFEMILES MICROPYME\",\r\n" + 
			"                    \"idSubproducto\": \"568195\",\r\n" + 
			"                    \"isProductOwner\": false,\r\n" + 
			"                    \"listaCondiciones\": [\r\n" + 
			"                        \" Bono de 5.000 LifeMiles  de bienvenida por facturar $3,000,000 los primeros 3 meses a partir del momento de la activación de la tarjeta.\",\r\n" + 
			"                        \"Acumulas 1 LifeMile por cada 2 dólares en compras nacionales e internacionales y 1 LifeMile adicional por cada 2 dólares por compras en canales, directos de Avianca.\\n\\n\",\r\n" + 
			"                        \"Las asistencias son coberturas a nivel nacional e internacional en materia de viajes, y a nivel nacional para asistencia vehicular.\\n\\n\\n\",\r\n" + 
			"                        \"Acumulación: 1 LifeMile por cada 2 dólares en compras.\",\r\n" + 
			"                        \"Cuota de Manejo Mensual: 21.400. \\n Exoneración de cuota de manejo por 0 meses. La exoneración se inica desde la fecha de activación.\"\r\n" + 
			"                    ],\r\n" + 
			"                    \"nombreSubproducto\": \"VISA LIFEMILES MICROPYME\",\r\n" + 
			"                    \"planes\": []\r\n" + 
			"                },\r\n" + 
			"                {\r\n" + 
			"                    \"codigoImagen\": \"328\",\r\n" + 
			"                    \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                    \"cupoMaximo\": 50000000.0,\r\n" + 
			"                    \"cupoMinimo\": 5000000.0,\r\n" + 
			"                    \"descripcionSubproducto\": \"Visa Lifemiles Personas\",\r\n" + 
			"                    \"idSubproducto\": \"568197\",\r\n" + 
			"                    \"isProductOwner\": false,\r\n" + 
			"                    \"listaCondiciones\": [],\r\n" + 
			"                    \"nombreSubproducto\": \"Visa Lifemiles Personas\",\r\n" + 
			"                    \"planes\": []\r\n" + 
			"                }]\r\n" + 
			"            }],\r\n" + 
			"            \"coberturaFng\": 0,\r\n" + 
			"            \"comisionFNG\": [],\r\n" + 
			"            \"idProducto\": \"21\",\r\n" + 
			"            \"ivaFng\": 0,\r\n" + 
			"            \"montoOtorgado\": \"850000000.00\",\r\n" + 
			"            \"nombreProducto\": \"MASTER NEGOCIOS MICROPYME\",\r\n" + 
			"            \"periodoGraciaFNG\": []\r\n" + 
			"        }],\r\n" + 
			"        \"tarjetasCredito\":\r\n" + 
			"        {\r\n" + 
			"            \"cicloFactura\": \"\",\r\n" + 
			"            \"nombreRealce\": \"\"\r\n" + 
			"        }\r\n" + 
			"    }\r\n" + 
			"}";
	
	public static String cargaUtilVerTarjetaJoven = "{\r\n" + 
			"    \"idSesion\": \"09a31d82-1908-48be-bc96-7e78d005996b\",\r\n" + 
			"    \"pasoFuncional\": \"paso_li_202_B\",\r\n" + 
			"    \"paso\": \"paso2\",\r\n" + 
			"    \"tipodocumento\": \"FS001\",\r\n" + 
			"    \"numeroDocumento\": \"1989652244\",\r\n" + 
			"    \"numeroSolicitud\": \"SUC_2020_162611\",\r\n" + 
			"    \"codigoSolicitante\": \"203714\",\r\n" + 
			"    \"cupoElegido\":\"1500000\",\r\n" + 
			"    \"idProducto\":\"10\",\r\n" + 
			"    \"producto\": [\r\n" + 
			"    {\r\n" + 
			"        \"idProducto\": \"567972\",\r\n" + 
			"        \"cupoElegido\": 1000000,\r\n" + 
			"        \"idSeguro\": 0,\r\n" + 
			"        \"aceptaDebitoAutomatico\": false,\r\n" + 
			"        \"cuentaDebitoAutomatico\": \"\",\r\n" + 
			"        \"tarjeta\":\r\n" + 
			"        {\r\n" + 
			"            \"cicloFactura\": \"\",\r\n" + 
			"            \"tipoDebitoAutomatico\": \"\",\r\n" + 
			"            \"nombreRealce\": \"\"\r\n" + 
			"        },\r\n" + 
			"        \"codigoImagen\": \"320\",\r\n" + 
			"        \"nombreSubProducto\": \"MASTER JOVEN\",\r\n" + 
			"        \"listaCondiciones\": [\"¡Invita a tus amigos a cine! 2x1, todos los días, en boletas de Cine Colombia.\", \"Combo gratis los jueves de cine Colombia. *TyC \", \"Asesoría Básica en temas financieros, tributarios, y legales para la creación de empresas *TyC\", \"Por cada 6.600 COP = 6 puntos Bancolombia.\", \"Cuota de Manejo Mensual: $12500.\"],\r\n" + 
			"        \"codigoCategoria\": \"MASTER\"\r\n" + 
			"    }],\r\n" + 
			"    \"tipoCreacionTarjeta\": \"\"\r\n" + 
			"}";
	
	public static String cargaUtilVerTarjetasError = "  \r\n" + 
			"   \"productoTarjeta\":[  \r\n" + 
			"      {  \r\n" + 
			"         \"beneficiario\":[  \r\n" + 
			"\r\n" + 
			"         ],\r\n" + 
			"      }\r\n" + 
			"   ],\r\n" + 
			"   \"numeroSolicitud\":\"1111111-3554-12\",\r\n" + 
			"   \"paso\":\"paso1\",\r\n" + 
			"   \"cupoElegido\":\"1000000\",\r\n" + 
			"   \"idProducto\":\"12\"\r\n" + 
			"   \"idSesion\":\"YbtMMy0D8-i4vdHfwTJTsw\",\r\n" + 
			"   \"pasoFuncional\":\"paso_li_201\",\r\n" + 
			"   \"hash\":\"619ff3bb9e9e824e615f7eb88deb7d7e\"\r\n" + 
			"}";
	
	public static String respuestaOfertaVerTarjetasJoven = "{\r\n" + 
			"    \"cabecera\":\r\n" + 
			"    {\r\n" + 
			"        \"estadoTransaccion\": \"\",\r\n" + 
			"        \"pasoFuncional\": \"paso1\",\r\n" + 
			"        \"numeroSolicitud\": \"SUC_2020_162611\",\r\n" + 
			"        \"extensionFuncional\": [\r\n" + 
			"        {\r\n" + 
			"            \"llave\": \"\",\r\n" + 
			"            \"valor\": \"\"\r\n" + 
			"        }]\r\n" + 
			"    },\r\n" + 
			"    \"oferta\":\r\n" + 
			"    {\r\n" + 
			"        \"informacionCliente\":\r\n" + 
			"        {\r\n" + 
			"            \"idSolicitante\": \"203714\",\r\n" + 
			"            \"tipoDocumento\": \"FS001\",\r\n" + 
			"            \"numeroDocumento\": \"1989652244\",\r\n" + 
			"            \"nombreCompletoCliente\": \" Jorge Enrique Molinares Ruiz\",\r\n" + 
			"            \"direccionPrincipal\": \"CL 12 6 52\",\r\n" + 
			"            \"codigoCiudad\": \"5001003\",\r\n" + 
			"            \"codigoDepartamento\": \"5\",\r\n" + 
			"            \"codigoPais\": \"CO\",\r\n" + 
			"            \"telefono\": \"3541927\",\r\n" + 
			"            \"celular\": \"3196867788\",\r\n" + 
			"            \"correoElectronicoCliente\": \"mebusta@bancolombia.com.co\",\r\n" + 
			"            \"codigoDireccionPrincipalFuente\": \"0074928417\"\r\n" + 
			"        },\r\n" + 
			"        \"producto\": [\r\n" + 
			"        {\r\n" + 
			"            \"idProducto\": \"10\",\r\n" + 
			"            \"nombreProducto\": \"MASTERCARD JOVEN\",\r\n" + 
			"            \"montoOtorgado\": \"15000000.00\",\r\n" + 
			"            \"categoria\": [\r\n" + 
			"            {\r\n" + 
			"                \"codigoCategoria\": \"AMEX\",\r\n" + 
			"                \"subproducto\": [\r\n" + 
			"                {\r\n" + 
			"                    \"idSubproducto\": \"567952\",\r\n" + 
			"                    \"nombreSubproducto\": \"AMEX VERDE\",\r\n" + 
			"                    \"descripcionSubproducto\": \"AMEX VERDE\",\r\n" + 
			"                    \"cupoMinimo\": 3000000.0,\r\n" + 
			"                    \"cupoMaximo\": 15000000.0,\r\n" + 
			"                    \"codigoImagen\": \"305\",\r\n" + 
			"                    \"listaCondiciones\": [\"¡Bienvenido! Recibe 10.720 Puntos Colombia por facturación de mínimo 2 millones durante los primeros 6 meses.\", \"¡Más puntos! Recibe doble puntaje por compras en Spa’s, Gimnasios, Droguerías, Salones de belleza, tiendas deportivas, entre otros.\", \"2x1 en boletas y cupón de descuento para crispetas, todos los días, en Cinemark.\", \"Por cada 4.800 COP = 6 puntos Bancolombia.\", \"Exoneración de 50.0% en la cuota de manejo por tiempo indefinido. La exoneración se inicia desde la fecha de activación. Cuota de Manejo Mensual: $14500.\"],\r\n" + 
			"                    \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                    \"isProductOwner\": false,\r\n" + 
			"                    \"planes\": []\r\n" + 
			"                }]\r\n" + 
			"            },\r\n" + 
			"            {\r\n" + 
			"                \"codigoCategoria\": \"VISA\",\r\n" + 
			"                \"subproducto\": [\r\n" + 
			"                {\r\n" + 
			"                    \"idSubproducto\": \"567954\",\r\n" + 
			"                    \"nombreSubproducto\": \"VISA SELECCION\",\r\n" + 
			"                    \"descripcionSubproducto\": \"VISA SELECCION\",\r\n" + 
			"                    \"cupoMinimo\": 400000.0,\r\n" + 
			"                    \"cupoMaximo\": 10000000.0,\r\n" + 
			"                    \"codigoImagen\": \"324\",\r\n" + 
			"                    \"listaCondiciones\": [\"Tienes 20% DCTO en la boletería para Nacional, Medellín y Santa Fe con ubicación privilegiada, alimentación, bebidas y obsequio. (100 boletas disponibles por partido).\", \"Adquiera la tarjeta entre el 15/04/2019 y el 31/12/2019 y podrás disfrutar de $0 cuota de manejo durante 12 meses. Aplican TyC.\", \"Experiencias y beneficios únicos con la Selección como: preventa de boletas, visitas a los entrenamientos y entrada a camerinos. Aplican TyC \", \"Por cada 6.600 COP = 6 puntos Bancolombia.\", \"Exoneración de 100.0% en la cuota de manejo por 12 meses. La exoneración se inicia desde la fecha de activación. Cuota de Manejo Mensual: $0.\"],\r\n" + 
			"                    \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                    \"isProductOwner\": false,\r\n" + 
			"                    \"planes\": []\r\n" + 
			"                },\r\n" + 
			"                {\r\n" + 
			"                    \"idSubproducto\": \"567956\",\r\n" + 
			"                    \"nombreSubproducto\": \"VISA CLASICA\",\r\n" + 
			"                    \"descripcionSubproducto\": \"VISA CLASICA\",\r\n" + 
			"                    \"cupoMinimo\": 400000.0,\r\n" + 
			"                    \"cupoMaximo\": 4900000.0,\r\n" + 
			"                    \"codigoImagen\": \"315\",\r\n" + 
			"                    \"listaCondiciones\": [\"Con Martes Visa tienes 30% de descuento en más de 140 restaurantes aliados.\", \"Seguro de Viajes hasta por $75.000 dólares y  seguro de protección de precios hasta por $200 dólares al año.\", \"Tarifa preferencial para compras en USA por medio de tu casillero internacional Aeropost.\", \"Por cada 6.600 COP = 6 puntos Bancolombia.\", \"Cuota de Manejo Mensual: $22300.\"],\r\n" + 
			"                    \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                    \"isProductOwner\": false,\r\n" + 
			"                    \"planes\": []\r\n" + 
			"                },\r\n" + 
			"                {\r\n" + 
			"                    \"idSubproducto\": \"567958\",\r\n" + 
			"                    \"nombreSubproducto\": \"VISA LIFEMILES PERSONAS\",\r\n" + 
			"                    \"descripcionSubproducto\": \"VISA LIFEMILES PERSONAS\",\r\n" + 
			"                    \"cupoMinimo\": 5000000.0,\r\n" + 
			"                    \"cupoMaximo\": 50000000.0,\r\n" + 
			"                    \"codigoImagen\": \"328\",\r\n" + 
			"                    \"listaCondiciones\": [\"Recibe un bono de bienvenida de 7.500 millas LifeMiles si facturas 7.500.000 COP durante los primeros 6 meses de uso.\", \"Recibe 6.000 millas LifeMiles de descuento anual si facturas 24.000.000 durante cada año.\", \"Acumula 2 millas LifeMiles por cada dólar en canales directos de Avianca y el los Aliados LifeMiles y 1.5 millas Lifemiles por cada dólar en compras internacionales.\", \"Acumula hasta 2 millas LifeMiles por cada dólar en compras.\", \"Exoneración de 100.0% en la cuota de manejo por -11 meses. La exoneración se inicia desde la fecha de activación. Cuota de Manejo Mensual: $0.\"],\r\n" + 
			"                    \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                    \"isProductOwner\": false,\r\n" + 
			"                    \"planes\": []\r\n" + 
			"                },\r\n" + 
			"                {\r\n" + 
			"                    \"idSubproducto\": \"567960\",\r\n" + 
			"                    \"nombreSubproducto\": \"VISA ORO\",\r\n" + 
			"                    \"descripcionSubproducto\": \"VISA ORO\",\r\n" + 
			"                    \"cupoMinimo\": 5000000.0,\r\n" + 
			"                    \"cupoMaximo\": 50000000.0,\r\n" + 
			"                    \"codigoImagen\": \"316\",\r\n" + 
			"                    \"listaCondiciones\": [\"Con Martes Visa tienes 30% de descuento en más de 140 restaurantes aliados.\", \"Viajas tranquilo con un Seguro de Viajes con cobertura de hasta $45.000 dólares.\", \"Protege las compras contra daños accidentales y robos hasta por $1.000 dólares anuales.\", \"Por cada 4.800 COP = 6 puntos Bancolombia.\", \"Cuota de Manejo Mensual: $26000.\"],\r\n" + 
			"                    \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                    \"isProductOwner\": false,\r\n" + 
			"                    \"planes\": []\r\n" + 
			"                },\r\n" + 
			"                {\r\n" + 
			"                    \"idSubproducto\": \"567962\",\r\n" + 
			"                    \"nombreSubproducto\": \"VISA PLATINUM\",\r\n" + 
			"                    \"descripcionSubproducto\": \"VISA PLATINUM\",\r\n" + 
			"                    \"cupoMinimo\": 10000000.0,\r\n" + 
			"                    \"cupoMaximo\": 200000000.0,\r\n" + 
			"                    \"codigoImagen\": \"319\",\r\n" + 
			"                    \"listaCondiciones\": [\"Protege tus compras y agrégale garantía extendida para duplicar la cobertura sin costos adicionales.\", \"Disfruta de beneficios en hoteles de ensueño a través de Visa Luxury Hotel Collection.\", \"Cuentas con un asistente personal para reserva en restaurantes, viajes, entradas a eventos y más\", \"Por cada 3.300 COP = 6 puntos Bancolombia.\", \"Cuota de Manejo Mensual: $29500.\"],\r\n" + 
			"                    \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                    \"isProductOwner\": false,\r\n" + 
			"                    \"planes\": []\r\n" + 
			"                },\r\n" + 
			"                {\r\n" + 
			"                    \"idSubproducto\": \"567964\",\r\n" + 
			"                    \"nombreSubproducto\": \"VISA INFINITE\",\r\n" + 
			"                    \"descripcionSubproducto\": \"VISA INFINITE\",\r\n" + 
			"                    \"cupoMinimo\": 15000000.0,\r\n" + 
			"                    \"cupoMaximo\": 300000000.0,\r\n" + 
			"                    \"codigoImagen\": \"325\",\r\n" + 
			"                    \"listaCondiciones\": [\"¡Bienvenido! Recibe 53.600 Puntos Colombia por facturación de mínimo 5 millones durante los primeros 6 meses.\", \"Cuentas con un asistente personal para reserva en restaurantes, viajes, entradas a eventos y más\", \"Viaja siempre cómodo con acceso a más de 1,200 salas VIP con Priority Pass en diferentes aeropuertos del mundo.\", \"Por cada 3.300 COP = 6 puntos Bancolombia.\", \"Cuota de Manejo Mensual: $39500.\"],\r\n" + 
			"                    \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                    \"isProductOwner\": false,\r\n" + 
			"                    \"planes\": []\r\n" + 
			"                }]\r\n" + 
			"            },\r\n" + 
			"            {\r\n" + 
			"                \"codigoCategoria\": \"MASTER\",\r\n" + 
			"                \"subproducto\": [\r\n" + 
			"                {\r\n" + 
			"                    \"idSubproducto\": \"567966\",\r\n" + 
			"                    \"nombreSubproducto\": \"MASTER PLATINUM\",\r\n" + 
			"                    \"descripcionSubproducto\": \"MASTER PLATINUM\",\r\n" + 
			"                    \"cupoMinimo\": 10000000.0,\r\n" + 
			"                    \"cupoMaximo\": 200000000.0,\r\n" + 
			"                    \"codigoImagen\": \"312\",\r\n" + 
			"                    \"listaCondiciones\": [\"Cuentas con coberturas de seguros como: Protección en compras, protección de precios y garantía extendida.\", \"Cobertura de hasta $65.000 dólares por gastos médicos en el exterior, por enfermedad o accidente.\", \"Reserva en restaurantes, viajes, entradas a eventos y más.\", \"Por cada 3.300 COP = 6 puntos Bancolombia.\", \"Cuota de Manejo Mensual: $29500.\"],\r\n" + 
			"                    \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                    \"isProductOwner\": false,\r\n" + 
			"                    \"planes\": []\r\n" + 
			"                },\r\n" + 
			"                {\r\n" + 
			"                    \"idSubproducto\": \"567968\",\r\n" + 
			"                    \"nombreSubproducto\": \"MASTER BLACK\",\r\n" + 
			"                    \"descripcionSubproducto\": \"MASTER BLACK\",\r\n" + 
			"                    \"cupoMinimo\": 10000000.0,\r\n" + 
			"                    \"cupoMaximo\": 30000000.0,\r\n" + 
			"                    \"codigoImagen\": \"313\",\r\n" + 
			"                    \"listaCondiciones\": [\"Entra ilimitadamente a más de 1.000 salas VIP en aeropuertos del mundo con MasterCard Airport Experience (MCAE).\", \"Disfruta de internet gratuito alrededor del mundo, en lugares como: aeropuertos, restaurantes, hoteles, estadios, medios de transporte, entre otros. \", \"Recibe el apoyo de un concierge, o asistente personal, las 24 horas del día.\", \"Por cada 3.300 COP = 6 puntos Bancolombia.\", \"Cuota de Manejo Mensual: $38000.\"],\r\n" + 
			"                    \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                    \"isProductOwner\": false,\r\n" + 
			"                    \"planes\": []\r\n" + 
			"                },\r\n" + 
			"                {\r\n" + 
			"                    \"idSubproducto\": \"567970\",\r\n" + 
			"                    \"nombreSubproducto\": \"MASTER IDEAL INTERNACIONAL\",\r\n" + 
			"                    \"descripcionSubproducto\": \"MASTER IDEAL INTERNACIONAL\",\r\n" + 
			"                    \"cupoMinimo\": 200000.0,\r\n" + 
			"                    \"cupoMaximo\": 1750000.0,\r\n" + 
			"                    \"codigoImagen\": \"TI1\",\r\n" + 
			"                    \"listaCondiciones\": [\"Avances del 100% del cupo disponible. Además podrás transferirlas de manera inmediata a tus cuentas de ahorro y corriente Bancolombia.\", \"Obtén liquidez y  financia lo que quieres con una cuota de manejo mensual baja.\", \"Las compras que realices con la tarjeta estarán aseguradas en caso de daño accidental o robo. Cobertura de hasta $100 dólares por incidente y hasta $200 dólares al año.\", \"Por cada 0.000 COP = 0 puntos Bancolombia.\", \"Cuota de Manejo Mensual: $15100.\"],\r\n" + 
			"                    \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                    \"isProductOwner\": false,\r\n" + 
			"                    \"planes\": []\r\n" + 
			"                },\r\n" + 
			"                {\r\n" + 
			"                    \"idSubproducto\": \"567972\",\r\n" + 
			"                    \"nombreSubproducto\": \"MASTER JOVEN\",\r\n" + 
			"                    \"descripcionSubproducto\": \"MASTER JOVEN\",\r\n" + 
			"                    \"cupoMinimo\": 200000.0,\r\n" + 
			"                    \"cupoMaximo\": 5000000.0,\r\n" + 
			"                    \"codigoImagen\": \"320\",\r\n" + 
			"                    \"listaCondiciones\": [\"¡Invita a tus amigos a cine! 2x1, todos los días, en boletas de Cine Colombia.\", \"Combo gratis los jueves de cine Colombia. *TyC \", \"Asesoría Básica en temas financieros, tributarios, y legales para la creación de empresas *TyC\", \"Por cada 6.600 COP = 6 puntos Bancolombia.\", \"Cuota de Manejo Mensual: $12500.\"],\r\n" + 
			"                    \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                    \"isProductOwner\": false,\r\n" + 
			"                    \"planes\": []\r\n" + 
			"                },\r\n" + 
			"                {\r\n" + 
			"                    \"idSubproducto\": \"567974\",\r\n" + 
			"                    \"nombreSubproducto\": \"MASTER CLASICA\",\r\n" + 
			"                    \"descripcionSubproducto\": \"MASTER CLASICA\",\r\n" + 
			"                    \"cupoMinimo\": 400000.0,\r\n" + 
			"                    \"cupoMaximo\": 4900000.0,\r\n" + 
			"                    \"codigoImagen\": \"310\",\r\n" + 
			"                    \"listaCondiciones\": [\"Cobertura de hasta 35,000 dólares por gastos médicos en el exterior, por enfermedad o accidente. *TyC.\", \"Cuentas con un seguro de protección en compras con una cobertura de hasta 100 dólares por incidente y hasta 200 dólares al año.\", \"Tarifa preferencial para compras en USA por medio de tu casillero internacional Aeropost.\", \"Por cada 6.600 COP = 6 puntos Bancolombia.\", \"Cuota de Manejo Mensual: $22300.\"],\r\n" + 
			"                    \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                    \"isProductOwner\": false,\r\n" + 
			"                    \"planes\": []\r\n" + 
			"                },\r\n" + 
			"                {\r\n" + 
			"                    \"idSubproducto\": \"567976\",\r\n" + 
			"                    \"nombreSubproducto\": \"MASTER ORO\",\r\n" + 
			"                    \"descripcionSubproducto\": \"MASTER ORO\",\r\n" + 
			"                    \"cupoMinimo\": 5000000.0,\r\n" + 
			"                    \"cupoMaximo\": 50000000.0,\r\n" + 
			"                    \"codigoImagen\": \"311\",\r\n" + 
			"                    \"listaCondiciones\": [\"Cobertura de hasta 50,000 dólares por gastos médicos en el exterior, por enfermedad o accidente. *TyC\", \"Priceless Specials, un pasaporte de beneficios en más de 50 ciudades del mundo.\", \"Cuentas con un seguro de protección en compras con una cobertura de hasta $200 dólares por incidente y hasta $400 dólares al año.\", \"Por cada 4.800 COP = 6 puntos Bancolombia.\", \"Cuota de Manejo Mensual: $26000.\"],\r\n" + 
			"                    \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                    \"isProductOwner\": false,\r\n" + 
			"                    \"planes\": []\r\n" + 
			"                }]\r\n" + 
			"            }],\r\n" + 
			"            \"comisionFNG\": [],\r\n" + 
			"            \"periodoGraciaFNG\": [],\r\n" + 
			"            \"coberturaFng\": 0,\r\n" + 
			"            \"ivaFng\": 0\r\n" + 
			"        }],\r\n" + 
			"        \"convenio\":\r\n" + 
			"        {\r\n" + 
			"            \"nitEmpresa\": \"\",\r\n" + 
			"            \"nombreConvenio\": \"\",\r\n" + 
			"            \"diaPago\": 0,\r\n" + 
			"            \"codigoConvenio\": \"0\",\r\n" + 
			"            \"ciudadConvenio\": \"\",\r\n" + 
			"            \"correoElectronico\": \"\"\r\n" + 
			"        },\r\n" + 
			"        \"tarjetasCredito\":\r\n" + 
			"        {\r\n" + 
			"            \"cicloFactura\": \"15\",\r\n" + 
			"            \"nombreRealce\": \"\"\r\n" + 
			"        }\r\n" + 
			"    }\r\n" + 
			"}";
	
	public static String respuestaOfertaTarjetas = "{\r\n" + 
			"    \"cabecera\": {\r\n" + 
			"        \"estadoTransaccion\": \"\",\r\n" + 
			"        \"extensionFuncional\": [\r\n" + 
			"            {\r\n" + 
			"                \"llave\": \"\",\r\n" + 
			"                \"valor\": \"\"\r\n" + 
			"            }\r\n" + 
			"        ],\r\n" + 
			"        \"numeroSolicitud\": \"SUC_2020_161592\",\r\n" + 
			"        \"pasoFuncional\": \"paso1\"\r\n" + 
			"    },\r\n" + 
			"    \"oferta\": {\r\n" + 
			"        \"convenio\": {\r\n" + 
			"            \"ciudadConvenio\": \"\",\r\n" + 
			"            \"codigoConvenio\": \"0\",\r\n" + 
			"            \"correoElectronico\": \"\",\r\n" + 
			"            \"diaPago\": 0,\r\n" + 
			"            \"nitEmpresa\": \"\",\r\n" + 
			"            \"nombreConvenio\": \"\"\r\n" + 
			"        },\r\n" + 
			"        \"informacionCliente\": {\r\n" + 
			"            \"celular\": \"3124116150\",\r\n" + 
			"            \"codigoCiudad\": \"11001005\",\r\n" + 
			"            \"codigoDepartamento\": \"11\",\r\n" + 
			"            \"codigoDireccionPrincipalFuente\": \"0074930791\",\r\n" + 
			"            \"codigoPais\": \"CO\",\r\n" + 
			"            \"correoElectronicoCliente\": \"mebusta@bancolombia.com.co\",\r\n" + 
			"            \"direccionPrincipal\": \"CR 74 49 43 PS 2\",\r\n" + 
			"            \"idSolicitante\": \"201829\",\r\n" + 
			"            \"nombreCompletoCliente\": \" Eliecer De Jesus Giraldo Giraldo\",\r\n" + 
			"            \"numeroDocumento\": \"1989653241\",\r\n" + 
			"            \"telefono\": \"7910575\",\r\n" + 
			"            \"tipoDocumento\": \"FS001\"\r\n" + 
			"        },\r\n" + 
			"        \"producto\": [\r\n" + 
			"            {\r\n" + 
			"                \"categoria\": [\r\n" + 
			"                    {\r\n" + 
			"                        \"codigoCategoria\": \"AMEX\",\r\n" + 
			"                        \"subproducto\": [\r\n" + 
			"                            {\r\n" + 
			"                                \"codigoImagen\": \"306\",\r\n" + 
			"                                \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                                \"cupoMaximo\": 50000000.0,\r\n" + 
			"                                \"cupoMinimo\": 5000000.0,\r\n" + 
			"                                \"descripcionSubproducto\": \"AMEX ORO\",\r\n" + 
			"                                \"idSubproducto\": \"565158\",\r\n" + 
			"                                \"isProductOwner\": false,\r\n" + 
			"                                \"listaCondiciones\": [\r\n" + 
			"                                    \"Espera cómodamente los vuelos en la sala VIP del aeropuerto internacional El Dorado, en Bogotá, donde podrás ingresar dos veces al año. \",\r\n" + 
			"                                    \"2 meses gratis de Rappi Prime, con envío sin costo para pedidos superiores a $10.000 a través de Rappi.\",\r\n" + 
			"                                    \"2x1 en boletas todos los días en todos los teatros Cinemark en Colombia *TyC.\",\r\n" + 
			"                                    \"Por cada 3.300 COP = 6 puntos Bancolombia.\",\r\n" + 
			"                                    \"Cuota de Manejo Mensual: $30000.\"\r\n" + 
			"                                ],\r\n" + 
			"                                \"nombreSubproducto\": \"AMEX ORO\",\r\n" + 
			"                                \"planes\": []\r\n" + 
			"                            },\r\n" + 
			"                            {\r\n" + 
			"                                \"codigoImagen\": \"304\",\r\n" + 
			"                                \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                                \"cupoMaximo\": 4900000.0,\r\n" + 
			"                                \"cupoMinimo\": 700000.0,\r\n" + 
			"                                \"descripcionSubproducto\": \"AMEX AZUL\",\r\n" + 
			"                                \"idSubproducto\": \"565160\",\r\n" + 
			"                                \"isProductOwner\": false,\r\n" + 
			"                                \"listaCondiciones\": [\r\n" + 
			"                                    \"2x1 en boletas y cupón de descuento para crispetas, todos los días, en Cinemark.\",\r\n" + 
			"                                    \"2 meses gratis de Rappi Prime, con envío sin costo para pedidos superiores a $10.000 a través de Rappi.\",\r\n" + 
			"                                    \"Protege tus compras con un seguro hasta por $1.000 dólares por daño accidental o hurto.\",\r\n" + 
			"                                    \"Por cada 6.600 COP = 6 puntos Bancolombia.\",\r\n" + 
			"                                    \"Cuota de Manejo Mensual: $23700.\"\r\n" + 
			"                                ],\r\n" + 
			"                                \"nombreSubproducto\": \"AMEX AZUL\",\r\n" + 
			"                                \"planes\": []\r\n" + 
			"                            },\r\n" + 
			"                            {\r\n" + 
			"                                \"codigoImagen\": \"305\",\r\n" + 
			"                                \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                                \"cupoMaximo\": 15000000.0,\r\n" + 
			"                                \"cupoMinimo\": 3000000.0,\r\n" + 
			"                                \"descripcionSubproducto\": \"AMEX VERDE\",\r\n" + 
			"                                \"idSubproducto\": \"565162\",\r\n" + 
			"                                \"isProductOwner\": false,\r\n" + 
			"                                \"listaCondiciones\": [\r\n" + 
			"                                    \"¡Bienvenido! Recibe 10.720 Puntos Colombia por facturación de mínimo 2 millones durante los primeros 6 meses.\",\r\n" + 
			"                                    \"¡Más puntos! Recibe doble puntaje por compras en Spa’s, Gimnasios, Droguerías, Salones de belleza, tiendas deportivas, entre otros.\",\r\n" + 
			"                                    \"2x1 en boletas y cupón de descuento para crispetas, todos los días, en Cinemark.\",\r\n" + 
			"                                    \"Por cada 4.800 COP = 6 puntos Bancolombia.\",\r\n" + 
			"                                    \"Exoneración de 50.0% en la cuota de manejo por tiempo indefinido. La exoneración se inicia desde la fecha de activación. Cuota de Manejo Mensual: $14500.\"\r\n" + 
			"                                ],\r\n" + 
			"                                \"nombreSubproducto\": \"AMEX VERDE\",\r\n" + 
			"                                \"planes\": []\r\n" + 
			"                            },\r\n" + 
			"                            {\r\n" + 
			"                                \"codigoImagen\": \"309\",\r\n" + 
			"                                \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                                \"cupoMaximo\": 300000000.0,\r\n" + 
			"                                \"cupoMinimo\": 10000000.0,\r\n" + 
			"                                \"descripcionSubproducto\": \"AMEX PLATINUM\",\r\n" + 
			"                                \"idSubproducto\": \"565164\",\r\n" + 
			"                                \"isProductOwner\": false,\r\n" + 
			"                                \"listaCondiciones\": [\r\n" + 
			"                                    \"Disfruta del lujo con acceso a más de 1.000 salas VIP en aeropuertos de 120 países. \",\r\n" + 
			"                                    \"Obtén tarifas especiales en reservas de hotel, restaurantes, viajes, restaurantes, almacenes, entre otros.\",\r\n" + 
			"                                    \"2x1 en boletas y cupón de descuento para crispetas, todos los días, en Cinemark.\",\r\n" + 
			"                                    \"Por cada 3.300 COP = 6 puntos Bancolombia.\",\r\n" + 
			"                                    \"Cuota de Manejo Mensual: $39000.\"\r\n" + 
			"                                ],\r\n" + 
			"                                \"nombreSubproducto\": \"AMEX PLATINUM\",\r\n" + 
			"                                \"planes\": []\r\n" + 
			"                            }\r\n" + 
			"                        ]\r\n" + 
			"                    },\r\n" + 
			"                    {\r\n" + 
			"                        \"codigoCategoria\": \"VISA\",\r\n" + 
			"                        \"subproducto\": [\r\n" + 
			"                            {\r\n" + 
			"                                \"codigoImagen\": \"324\",\r\n" + 
			"                                \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                                \"cupoMaximo\": 10000000.0,\r\n" + 
			"                                \"cupoMinimo\": 400000.0,\r\n" + 
			"                                \"descripcionSubproducto\": \"VISA SELECCION\",\r\n" + 
			"                                \"idSubproducto\": \"565166\",\r\n" + 
			"                                \"isProductOwner\": false,\r\n" + 
			"                                \"listaCondiciones\": [\r\n" + 
			"                                    \"Tienes 20% DCTO en la boletería para Nacional, Medellín y Santa Fe con ubicación privilegiada, alimentación, bebidas y obsequio. (100 boletas disponibles por partido).\",\r\n" + 
			"                                    \"Adquiera la tarjeta entre el 15/04/2019 y el 31/12/2019 y podrás disfrutar de $0 cuota de manejo durante 12 meses. Aplican TyC.\",\r\n" + 
			"                                    \"Experiencias y beneficios únicos con la Selección como: preventa de boletas, visitas a los entrenamientos y entrada a camerinos. Aplican TyC \",\r\n" + 
			"                                    \"Por cada 6.600 COP = 6 puntos Bancolombia.\",\r\n" + 
			"                                    \"Exoneración de 100.0% en la cuota de manejo por 12 meses. La exoneración se inicia desde la fecha de activación. Cuota de Manejo Mensual: $0.\"\r\n" + 
			"                                ],\r\n" + 
			"                                \"nombreSubproducto\": \"VISA SELECCION\",\r\n" + 
			"                                \"planes\": []\r\n" + 
			"                            },\r\n" + 
			"                            {\r\n" + 
			"                                \"codigoImagen\": \"315\",\r\n" + 
			"                                \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                                \"cupoMaximo\": 4900000.0,\r\n" + 
			"                                \"cupoMinimo\": 400000.0,\r\n" + 
			"                                \"descripcionSubproducto\": \"VISA CLASICA\",\r\n" + 
			"                                \"idSubproducto\": \"565168\",\r\n" + 
			"                                \"isProductOwner\": false,\r\n" + 
			"                                \"listaCondiciones\": [\r\n" + 
			"                                    \"Con Martes Visa tienes 30% de descuento en más de 140 restaurantes aliados.\",\r\n" + 
			"                                    \"Seguro de Viajes hasta por $75.000 dólares y  seguro de protección de precios hasta por $200 dólares al año.\",\r\n" + 
			"                                    \"Tarifa preferencial para compras en USA por medio de tu casillero internacional Aeropost.\",\r\n" + 
			"                                    \"Por cada 6.600 COP = 6 puntos Bancolombia.\",\r\n" + 
			"                                    \"Cuota de Manejo Mensual: $22300.\"\r\n" + 
			"                                ],\r\n" + 
			"                                \"nombreSubproducto\": \"VISA CLASICA\",\r\n" + 
			"                                \"planes\": []\r\n" + 
			"                            },\r\n" + 
			"                            {\r\n" + 
			"                                \"codigoImagen\": \"328\",\r\n" + 
			"                                \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                                \"cupoMaximo\": 50000000.0,\r\n" + 
			"                                \"cupoMinimo\": 5000000.0,\r\n" + 
			"                                \"descripcionSubproducto\": \"VISA LIFEMILES PERSONAS\",\r\n" + 
			"                                \"idSubproducto\": \"565170\",\r\n" + 
			"                                \"isProductOwner\": false,\r\n" + 
			"                                \"listaCondiciones\": [\r\n" + 
			"                                    \"Recibe un bono de bienvenida de 7.500 millas LifeMiles si facturas 7.500.000 COP durante los primeros 6 meses de uso.\",\r\n" + 
			"                                    \"Recibe 6.000 millas LifeMiles de descuento anual si facturas 24.000.000 durante cada año.\",\r\n" + 
			"                                    \"Acumula 2 millas LifeMiles por cada dólar en canales directos de Avianca y el los Aliados LifeMiles y 1.5 millas Lifemiles por cada dólar en compras internacionales.\",\r\n" + 
			"                                    \"Acumula hasta 2 millas LifeMiles por cada dólar en compras.\",\r\n" + 
			"                                    \"Exoneración de 100.0% en la cuota de manejo por -11 meses. La exoneración se inicia desde la fecha de activación. Cuota de Manejo Mensual: $0.\"\r\n" + 
			"                                ],\r\n" + 
			"                                \"nombreSubproducto\": \"VISA LIFEMILES PERSONAS\",\r\n" + 
			"                                \"planes\": []\r\n" + 
			"                            },\r\n" + 
			"                            {\r\n" + 
			"                                \"codigoImagen\": \"316\",\r\n" + 
			"                                \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                                \"cupoMaximo\": 50000000.0,\r\n" + 
			"                                \"cupoMinimo\": 5000000.0,\r\n" + 
			"                                \"descripcionSubproducto\": \"VISA ORO\",\r\n" + 
			"                                \"idSubproducto\": \"565172\",\r\n" + 
			"                                \"isProductOwner\": false,\r\n" + 
			"                                \"listaCondiciones\": [\r\n" + 
			"                                    \"Con Martes Visa tienes 30% de descuento en más de 140 restaurantes aliados.\",\r\n" + 
			"                                    \"Viajas tranquilo con un Seguro de Viajes con cobertura de hasta $45.000 dólares.\",\r\n" + 
			"                                    \"Protege las compras contra daños accidentales y robos hasta por $1.000 dólares anuales.\",\r\n" + 
			"                                    \"Por cada 4.800 COP = 6 puntos Bancolombia.\",\r\n" + 
			"                                    \"Cuota de Manejo Mensual: $26000.\"\r\n" + 
			"                                ],\r\n" + 
			"                                \"nombreSubproducto\": \"VISA ORO\",\r\n" + 
			"                                \"planes\": []\r\n" + 
			"                            },\r\n" + 
			"                            {\r\n" + 
			"                                \"codigoImagen\": \"319\",\r\n" + 
			"                                \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                                \"cupoMaximo\": 200000000.0,\r\n" + 
			"                                \"cupoMinimo\": 10000000.0,\r\n" + 
			"                                \"descripcionSubproducto\": \"VISA PLATINUM\",\r\n" + 
			"                                \"idSubproducto\": \"565174\",\r\n" + 
			"                                \"isProductOwner\": false,\r\n" + 
			"                                \"listaCondiciones\": [\r\n" + 
			"                                    \"Protege tus compras y agrégale garantía extendida para duplicar la cobertura sin costos adicionales.\",\r\n" + 
			"                                    \"Disfruta de beneficios en hoteles de ensueño a través de Visa Luxury Hotel Collection.\",\r\n" + 
			"                                    \"Cuentas con un asistente personal para reserva en restaurantes, viajes, entradas a eventos y más\",\r\n" + 
			"                                    \"Por cada 3.300 COP = 6 puntos Bancolombia.\",\r\n" + 
			"                                    \"Cuota de Manejo Mensual: $29500.\"\r\n" + 
			"                                ],\r\n" + 
			"                                \"nombreSubproducto\": \"VISA PLATINUM\",\r\n" + 
			"                                \"planes\": []\r\n" + 
			"                            },\r\n" + 
			"                            {\r\n" + 
			"                                \"codigoImagen\": \"325\",\r\n" + 
			"                                \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                                \"cupoMaximo\": 300000000.0,\r\n" + 
			"                                \"cupoMinimo\": 15000000.0,\r\n" + 
			"                                \"descripcionSubproducto\": \"VISA INFINITE\",\r\n" + 
			"                                \"idSubproducto\": \"565176\",\r\n" + 
			"                                \"isProductOwner\": false,\r\n" + 
			"                                \"listaCondiciones\": [\r\n" + 
			"                                    \"¡Bienvenido! Recibe 53.600 Puntos Colombia por facturación de mínimo 5 millones durante los primeros 6 meses.\",\r\n" + 
			"                                    \"Cuentas con un asistente personal para reserva en restaurantes, viajes, entradas a eventos y más\",\r\n" + 
			"                                    \"Viaja siempre cómodo con acceso a más de 1,200 salas VIP con Priority Pass en diferentes aeropuertos del mundo.\",\r\n" + 
			"                                    \"Por cada 3.300 COP = 6 puntos Bancolombia.\",\r\n" + 
			"                                    \"Cuota de Manejo Mensual: $39500.\"\r\n" + 
			"                                ],\r\n" + 
			"                                \"nombreSubproducto\": \"VISA INFINITE\",\r\n" + 
			"                                \"planes\": []\r\n" + 
			"                            }\r\n" + 
			"                        ]\r\n" + 
			"                    },\r\n" + 
			"                    {\r\n" + 
			"                        \"codigoCategoria\": \"MASTER\",\r\n" + 
			"                        \"subproducto\": [\r\n" + 
			"                            {\r\n" + 
			"                                \"codigoImagen\": \"312\",\r\n" + 
			"                                \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                                \"cupoMaximo\": 200000000.0,\r\n" + 
			"                                \"cupoMinimo\": 10000000.0,\r\n" + 
			"                                \"descripcionSubproducto\": \"MASTER PLATINUM\",\r\n" + 
			"                                \"idSubproducto\": \"565178\",\r\n" + 
			"                                \"isProductOwner\": false,\r\n" + 
			"                                \"listaCondiciones\": [\r\n" + 
			"                                    \"Cuentas con coberturas de seguros como: Protección en compras, protección de precios y garantía extendida.\",\r\n" + 
			"                                    \"Cobertura de hasta $65.000 dólares por gastos médicos en el exterior, por enfermedad o accidente.\",\r\n" + 
			"                                    \"Reserva en restaurantes, viajes, entradas a eventos y más.\",\r\n" + 
			"                                    \"Por cada 3.300 COP = 6 puntos Bancolombia.\",\r\n" + 
			"                                    \"Cuota de Manejo Mensual: $29500.\"\r\n" + 
			"                                ],\r\n" + 
			"                                \"nombreSubproducto\": \"MASTER PLATINUM\",\r\n" + 
			"                                \"planes\": []\r\n" + 
			"                            },\r\n" + 
			"                            {\r\n" + 
			"                                \"codigoImagen\": \"313\",\r\n" + 
			"                                \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                                \"cupoMaximo\": 30000000.0,\r\n" + 
			"                                \"cupoMinimo\": 10000000.0,\r\n" + 
			"                                \"descripcionSubproducto\": \"MASTER BLACK\",\r\n" + 
			"                                \"idSubproducto\": \"565180\",\r\n" + 
			"                                \"isProductOwner\": false,\r\n" + 
			"                                \"listaCondiciones\": [\r\n" + 
			"                                    \"Entra ilimitadamente a más de 1.000 salas VIP en aeropuertos del mundo con MasterCard Airport Experience (MCAE).\",\r\n" + 
			"                                    \"Disfruta de internet gratuito alrededor del mundo, en lugares como: aeropuertos, restaurantes, hoteles, estadios, medios de transporte, entre otros. \",\r\n" + 
			"                                    \"Recibe el apoyo de un concierge, o asistente personal, las 24 horas del día.\",\r\n" + 
			"                                    \"Por cada 3.300 COP = 6 puntos Bancolombia.\",\r\n" + 
			"                                    \"Cuota de Manejo Mensual: $38000.\"\r\n" + 
			"                                ],\r\n" + 
			"                                \"nombreSubproducto\": \"MASTER BLACK\",\r\n" + 
			"                                \"planes\": []\r\n" + 
			"                            },\r\n" + 
			"                            {\r\n" + 
			"                                \"codigoImagen\": \"TI1\",\r\n" + 
			"                                \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                                \"cupoMaximo\": 1750000.0,\r\n" + 
			"                                \"cupoMinimo\": 200000.0,\r\n" + 
			"                                \"descripcionSubproducto\": \"MASTER IDEAL INTERNACIONAL\",\r\n" + 
			"                                \"idSubproducto\": \"565182\",\r\n" + 
			"                                \"isProductOwner\": false,\r\n" + 
			"                                \"listaCondiciones\": [\r\n" + 
			"                                    \"Avances del 100% del cupo disponible. Además podrás transferirlas de manera inmediata a tus cuentas de ahorro y corriente Bancolombia.\",\r\n" + 
			"                                    \"Obtén liquidez y  financia lo que quieres con una cuota de manejo mensual baja.\",\r\n" + 
			"                                    \"Las compras que realices con la tarjeta estarán aseguradas en caso de daño accidental o robo. Cobertura de hasta $100 dólares por incidente y hasta $200 dólares al año.\",\r\n" + 
			"                                    \"Por cada 0.000 COP = 0 puntos Bancolombia.\",\r\n" + 
			"                                    \"Cuota de Manejo Mensual: $15100.\"\r\n" + 
			"                                ],\r\n" + 
			"                                \"nombreSubproducto\": \"MASTER IDEAL INTERNACIONAL\",\r\n" + 
			"                                \"planes\": []\r\n" + 
			"                            },\r\n" + 
			"                            {\r\n" + 
			"                                \"codigoImagen\": \"320\",\r\n" + 
			"                                \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                                \"cupoMaximo\": 5000000.0,\r\n" + 
			"                                \"cupoMinimo\": 200000.0,\r\n" + 
			"                                \"descripcionSubproducto\": \"MASTER JOVEN\",\r\n" + 
			"                                \"idSubproducto\": \"565184\",\r\n" + 
			"                                \"isProductOwner\": false,\r\n" + 
			"                                \"listaCondiciones\": [\r\n" + 
			"                                    \"¡Invita a tus amigos a cine! 2x1, todos los días, en boletas de Cine Colombia.\",\r\n" + 
			"                                    \"Combo gratis los jueves de cine Colombia. *TyC \",\r\n" + 
			"                                    \"Asesoría Básica en temas financieros, tributarios, y legales para la creación de empresas *TyC\",\r\n" + 
			"                                    \"Por cada 6.600 COP = 6 puntos Bancolombia.\",\r\n" + 
			"                                    \"Cuota de Manejo Mensual: $12500.\"\r\n" + 
			"                                ],\r\n" + 
			"                                \"nombreSubproducto\": \"MASTER JOVEN\",\r\n" + 
			"                                \"planes\": []\r\n" + 
			"                            },\r\n" + 
			"                            {\r\n" + 
			"                                \"codigoImagen\": \"310\",\r\n" + 
			"                                \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                                \"cupoMaximo\": 4900000.0,\r\n" + 
			"                                \"cupoMinimo\": 400000.0,\r\n" + 
			"                                \"descripcionSubproducto\": \"MASTER CLASICA\",\r\n" + 
			"                                \"idSubproducto\": \"565186\",\r\n" + 
			"                                \"isProductOwner\": false,\r\n" + 
			"                                \"listaCondiciones\": [\r\n" + 
			"                                    \"Cobertura de hasta 35,000 dólares por gastos médicos en el exterior, por enfermedad o accidente. *TyC.\",\r\n" + 
			"                                    \"Cuentas con un seguro de protección en compras con una cobertura de hasta 100 dólares por incidente y hasta 200 dólares al año.\",\r\n" + 
			"                                    \"Tarifa preferencial para compras en USA por medio de tu casillero internacional Aeropost.\",\r\n" + 
			"                                    \"Por cada 6.600 COP = 6 puntos Bancolombia.\",\r\n" + 
			"                                    \"Cuota de Manejo Mensual: $22300.\"\r\n" + 
			"                                ],\r\n" + 
			"                                \"nombreSubproducto\": \"MASTER CLASICA\",\r\n" + 
			"                                \"planes\": []\r\n" + 
			"                            },\r\n" + 
			"                            {\r\n" + 
			"                                \"codigoImagen\": \"311\",\r\n" + 
			"                                \"comisionDisponibilidad\": 0.0,\r\n" + 
			"                                \"cupoMaximo\": 50000000.0,\r\n" + 
			"                                \"cupoMinimo\": 5000000.0,\r\n" + 
			"                                \"descripcionSubproducto\": \"MASTER ORO\",\r\n" + 
			"                                \"idSubproducto\": \"565188\",\r\n" + 
			"                                \"isProductOwner\": false,\r\n" + 
			"                                \"listaCondiciones\": [\r\n" + 
			"                                    \"Cobertura de hasta 50,000 dólares por gastos médicos en el exterior, por enfermedad o accidente. *TyC\",\r\n" + 
			"                                    \"Priceless Specials, un pasaporte de beneficios en más de 50 ciudades del mundo.\",\r\n" + 
			"                                    \"Cuentas con un seguro de protección en compras con una cobertura de hasta $200 dólares por incidente y hasta $400 dólares al año.\",\r\n" + 
			"                                    \"Por cada 4.800 COP = 6 puntos Bancolombia.\",\r\n" + 
			"                                    \"Cuota de Manejo Mensual: $26000.\"\r\n" + 
			"                                ],\r\n" + 
			"                                \"nombreSubproducto\": \"MASTER ORO\",\r\n" + 
			"                                \"planes\": []\r\n" + 
			"                            }\r\n" + 
			"                        ]\r\n" + 
			"                    }\r\n" + 
			"                ],\r\n" + 
			"                \"coberturaFng\": 0,\r\n" + 
			"                \"comisionFNG\": [],\r\n" + 
			"                \"idProducto\": \"12\",\r\n" + 
			"                \"ivaFng\": 0,\r\n" + 
			"                \"montoOtorgado\": \"15000000.00\",\r\n" + 
			"                \"nombreProducto\": \"TARJETA DE CREDITO\",\r\n" + 
			"                \"periodoGraciaFNG\": []\r\n" + 
			"            }\r\n" + 
			"        ],\r\n" + 
			"        \"tarjetasCredito\": {\r\n" + 
			"            \"cicloFactura\": \"15\",\r\n" + 
			"            \"nombreRealce\": \"\"\r\n" + 
			"        }\r\n" + 
			"    }\r\n" + 
			"}";

	public static String tarjetaSeleccionadas = "{\r\n" + 
			"    \"idSesion\": \"cd1ff782\",\r\n" + 
			"    \"pasoFuncional\": \"paso_li_202_B\",\r\n" + 
			"    \"paso\": \"paso2\",\r\n" + 
			"    \"tipodocumento\": \"FS001\",\r\n" + 
			"    \"numeroDocumento\": \"1989652250\",\r\n" + 
			"    \"numeroSolicitud\": \"SUC_2020_161592\",\r\n" + 
			"    \"codigoSolicitante\": \"203726\",\r\n" + 
			"    \"idProducto\": \"12\",\r\n" + 
			"    \"cupoElegido\": \"8000000\",\r\n" + 
			"    \"producto\": [\r\n" + 
			"    {\r\n" + 
			"        \"idProducto\": \"568178\",\r\n" + 
			"        \"cupoElegido\": 5000000,\r\n" + 
			"        \"idSeguro\": 0,\r\n" + 
			"        \"aceptaDebitoAutomatico\": false,\r\n" + 
			"        \"cuentaDebitoAutomatico\": \"\",\r\n" + 
			"        \"tarjeta\":\r\n" + 
			"        {\r\n" + 
			"            \"cicloFactura\": \"\",\r\n" + 
			"            \"tipoDebitoAutomatico\": \"\",\r\n" + 
			"            \"nombreRealce\": \"\"\r\n" + 
			"        },\r\n" + 
			"        \"codigoImagen\": \"316\",\r\n" + 
			"        \"nombreSubProducto\": \"VISA ORO\",\r\n" + 
			"        \"listaCondiciones\": [\"Con Martes Visa tienes 30 de descuento en más de 140 restaurantes aliados.\", \"Viajas tranquilo con un Seguro de Viajes con cobertura de hasta 45000 dólares.\", \"Protege las compras contra daños accidentales y robos hasta por 1000 dólares anuales.\", \"Por cada 4800 COP  6 puntos Bancolombia.\", \"Cuota de Manejo Mensual: 26000.\"],\r\n" + 
			"        \"codigoCategoria\": \"VISA\"\r\n" + 
			"    }]\r\n" + 
			"}";
	
	public static String cargaUtilVerTarjetasMasterNegocios = "{\r\n" + 
			"    \"idSesion\": \"f5db71d1-8d01-4bc4-8b57-0f340e707b61\",\r\n" + 
			"    \"pasoFuncional\": \"paso_li_202_B\",\r\n" + 
			"    \"paso\": \"paso2\",\r\n" + 
			"    \"tipodocumento\": \"FS001\",\r\n" + 
			"    \"numeroDocumento\": \"1989672701\",\r\n" + 
			"    \"numeroSolicitud\": \"SUC_2020_162646\",\r\n" + 
			"    \"codigoSolicitante\": \"203718\",\r\n" + 
			"    \"cupoElegido\":\"10000000\",\r\n" + 
			"    \"idProducto\":\"21\",\r\n" + 
			"    \"producto\": [\r\n" + 
			"    {\r\n" + 
			"        \"idProducto\": \"568062\",\r\n" + 
			"        \"cupoElegido\": 500000,\r\n" + 
			"        \"idSeguro\": 0,\r\n" + 
			"        \"aceptaDebitoAutomatico\": false,\r\n" + 
			"        \"tarjeta\":\r\n" + 
			"        {\r\n" + 
			"            \"cicloFactura\": \"\",\r\n" + 
			"            \"tipoDebitoAutomatico\": \"\",\r\n" + 
			"            \"nombreRealce\": \"\"\r\n" + 
			"        },\r\n" + 
			"        \"codigoImagen\": \"326\",\r\n" + 
			"        \"nombreSubProducto\": \"Master Negocios Micropyme\",\r\n" + 
			"        \"listaCondiciones\": [\"Cubre tus viajes empresariales, visita clientes y asiste a eventos. Amplía tu red de contactos\", \"Con tus compras acumula Puntos Colombia que podrás redimir en tecnología, implementos para oficina, entre otros para ti y tu empresa.\", \"Acumulación: 6 Puntos por cada 11400 en compras.\", \"Cuota de Manejo Mensual: 24300.  Exoneración de cuota de manejo por 6 meses. La exoneración se inica desde la fecha de activación.\"],\r\n" + 
			"        \"codigoCategoria\": \"Master\"\r\n" + 
			"    }]\r\n" + 
			"}";
}
