package bancolombia.dtd.vd.tdc.api.rest;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import bancolombia.dtd.vd.li.api.autenticacion.ServicioAutenticacion;
import bancolombia.dtd.vd.li.commons.exception.ExceptionLI;
import bancolombia.dtd.vd.li.dto.autenticacion.AutenticacionRS;
import bancolombia.dtd.vd.li.dto.proxy.authFuerte.AutenticacionFuerteRS;
import bancolombia.dtd.vd.li.dto.proxy.clave.GeneracionClaveRS;
import bancolombia.dtd.vd.li.dto.proxy.clave.ValidacionClaveRS;
import bancolombia.dtd.vd.li.dto.proxy.gestionCreditoConsumo.GestionCreditoConsumoRS;
import bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.ServicioDatosCliente;
import bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.util.ConstantesLI;
import bancolombia.dtd.vd.sucursales.tdc.api.datoscliente.util.Utilities;
import org.jose4j.jwt.MalformedClaimException;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Path("/servicio")
public class ControladorDatosCliente {
	private static final Logger logger = LogManager.getLogger(ControladorDatosCliente.class);

	private static final String ENCONDING = "UTF-8";
	private static Gson json = new Gson();
	private static Utilities util = new Utilities();
	private String mensaje = "";

	@Inject
	private ServicioAutenticacion servicioAutenticacion;

	@Inject
	private ServicioDatosCliente servicioDatosCliente;
	
	

	/**
	 * @param servicioAutenticacion
	 * @param servicioDatosCliente
	 */
	public void setControladorDatosCliente(ServicioAutenticacion servicioAutenticacion,
			ServicioDatosCliente servicioDatosCliente) {
		this.servicioAutenticacion = servicioAutenticacion;
		this.servicioDatosCliente = servicioDatosCliente;
	}

	@POST
	@Path("crearCasoTDC")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response crearSolicitudCreditoConsumo(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) throws ExceptionLI {
		Response response = null;
		try {
			GestionCreditoConsumoRS gestionCreditoConsumoRS = servicioDatosCliente.crearCreditoConsumo(authorization,
					encrypter, util.getIpCliente(requestContext));
			response = Response.status(Status.OK).entity(json.toJson(gestionCreditoConsumoRS)).build();
		} catch (ExceptionLI e) {
			logger.error("Error crearSolicitudCreditoConsumo INTERNAL_SERVER_ERROR", e);
			return util.validacionRespuestaError(e);
		} catch (Exception e) {
			logger.error("Error crearSolicitudCreditoConsumo.", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	@POST
	@Path("confirmarCasoTDC")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response confirmarCreditoConsumo(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) throws ExceptionLI {
		Response response = null;
		try {
			GestionCreditoConsumoRS gestionCreditoConsumoRS = servicioDatosCliente
					.confirmarCreditoConsumo(authorization, encrypter, util.getIpCliente(requestContext));
			response = Response.status(Status.OK).entity(json.toJson(gestionCreditoConsumoRS)).build();
		} catch (ExceptionLI e) {
			logger.error("Error confirmarCreditoConsumo INTERNAL_SERVER_ERROR", e);
			return util.validacionRespuestaError(e);
		} catch (Exception e) {
			logger.error("Error confirmarCreditoConsumo.", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	@POST
	@Path("firmarDocumentosTDC")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response firmarDocumentosCreditoConsumo(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) throws ExceptionLI {
		Response response = null;
		try {
			GestionCreditoConsumoRS gestionCreditoConsumoRS = servicioDatosCliente
					.firmarDocumentosCreditoConsumo(authorization, encrypter, util.getIpCliente(requestContext));
			response = Response.status(Status.OK).entity(json.toJson(gestionCreditoConsumoRS)).build();
		} catch (ExceptionLI e) {
			logger.error("Error firmarDocumentosCreditoConsumo INTERNAL_SERVER_ERROR", e);
			return util.validacionRespuestaError(e);
		} catch (Exception e) {
			logger.error("Error firmarDocumentosCreditoConsumo.", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	@POST
	@Path("finalizarExpTDC")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response finalizarExp(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) throws ExceptionLI {
		Response response = null;
		try {

			String resp = servicioDatosCliente.finalizarExp(encrypter, util.getIpCliente(requestContext));
			response = Response.status(Status.OK).entity(json.toJson(resp)).build();

		} catch (ExceptionLI e) {
			logger.error("Error finalizarExp INTERNAL_SERVER_ERROR", e);
			return util.validacionRespuestaError(e);
		} catch (Exception e) {
			logger.error("Error finalizarExp.", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	@POST
	@Path("enviarCorreoTDC")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response enviarCorreo(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) throws ExceptionLI {
		Response response = null;
		try {
			String[] respuesta = servicioDatosCliente.enviarCorreo(authorization, encrypter,
					util.getIpCliente(requestContext));
			response = Response.status(Status.OK).entity(json.toJson(respuesta)).build();
		} catch (ExceptionLI e) {
			logger.error("Error enviarCorreo INTERNAL_SERVER_ERROR", e);
			return util.validacionRespuestaError(e);
		} catch (Exception e) {
			logger.error("Error enviarCorreo.", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	@GET
	@Path("health")
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response health() {
		return Response.status(Status.OK).entity("Health OK").build();
	}

	@POST
	@Path("descargarDocumentosTDC")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response descargarDocumentosBienvenida(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) {
		Response response = null;
		try {
			String[] resp = servicioDatosCliente.descargarDocumentosBienvenida(authorization, encrypter,
					util.getIpCliente(requestContext));
			response = Response.status(Status.OK).entity(json.toJson(resp)).build();
		} catch (ExceptionLI e) {
			logger.error("Error descargarDocumentos", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	/**
	 * Metodo para generar clave de clientes
	 * 
	 * @param requestContext contexto de la solicitud
	 * @param authorization  JWT de autorizacion
	 * @param encrypter      CargaUtil para el llamado de servicios y control del
	 *                       flujo
	 * @return Respuesta al proceso
	 * @throws ExceptionLI excepciones del proceso
	 */
	@POST
	@Path("generacionClaveTDC")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response generacionClave(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) throws ExceptionLI {
		Response response = null;
		try {
			GeneracionClaveRS resp = servicioDatosCliente.generacionClave(authorization, encrypter,
					util.getIpCliente(requestContext));
			response = Response.status(Status.OK).entity(json.toJson(resp)).build();
		} catch (Exception e) {
			logger.error("Error generacionClave", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}
	
	@POST
	@Path("guardarPasoTDC")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response guardarPaso(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) throws ExceptionLI {
		Response response = null;
		try {

			String resp = servicioDatosCliente.finalizarExp(encrypter, util.getIpCliente(requestContext));
			response = Response.status(Status.OK).entity(json.toJson(resp)).build();

		} catch (ExceptionLI e) {

			logger.error("Error guardarPaso INTERNAL_SERVER_ERROR", e);
			return util.validacionRespuestaError(e);
		} catch (Exception e) {
			logger.error("Error guardarPaso.", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	@POST
	@Path("validarSesionTDC")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response validarSesion(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization, String encrypter) throws ExceptionLI {
		AutenticacionRS respuesta = null;
		try {
			respuesta = servicioAutenticacion.validarSesion(authorization, encrypter);
			respuesta.setIpCliente(util.getIpCliente(requestContext));
			return Response.status(Status.OK).entity(json.toJson(respuesta)).build();
		} catch (ExceptionLI e) {
			mensaje = ConstantesLI.MSG_VAL_SESSION + e.getMensaje();
			logger.error(mensaje, e);
			return Response.status(Status.UNAUTHORIZED).entity(e.getMensaje()).build();
		}
	}



	@POST
	@Path("terminarSesionTDC")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=" + ENCONDING)
	public Response terminarSesion(@Context HttpServletRequest requestContext,
			@HeaderParam("Authorization") String authorization) throws ExceptionLI, GeneralSecurityException, MalformedClaimException, IOException {
		AutenticacionRS respuesta = null;
		try {
			respuesta = servicioAutenticacion.terminarSesion(authorization);
			respuesta.setIpCliente(util.getIpCliente(requestContext));
			return Response.status(Status.OK).entity(json.toJson(respuesta)).build();
		} catch (ExceptionLI e) {
			mensaje = ConstantesLI.MSG_VAL_SESSION + e.getMensaje();
			logger.error(mensaje, e);
			return Response.status(Status.UNAUTHORIZED).entity(e.getMensaje()).build();
		}
	}

}
