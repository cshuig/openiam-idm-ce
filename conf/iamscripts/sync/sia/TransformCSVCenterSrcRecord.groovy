package sync

/**
 * TransformCSVCenterSrcRecord.groovy
 * Aprovisionamiento de unidades organizativas tipo Centros (CSV) de MCMUTUAL
 * Codigos de retorno:
 * 		NO_DELETE = 0;
 * 		DELETE = 1;
 * 		DISABLE = 2;
 * 		ENABLE = 3;
 * Date   : Mar 2014
 * Author : Sistemas Informaticos Abiertos (S.I.A)
 * Version: 1.0
 */

import groovy.sql.Sql

import java.sql.SQLException
import java.text.SimpleDateFormat

import org.openiam.idm.srvc.grp.domain.GroupAttributeEntity
import org.openiam.idm.srvc.grp.domain.GroupEntity
import org.openiam.idm.srvc.grp.service.GroupDataService
import org.openiam.idm.srvc.meta.dto.MetadataElement
import org.openiam.idm.srvc.meta.service.MetadataService
import org.openiam.idm.srvc.meta.ws.*
import org.openiam.idm.srvc.org.dto.Organization
import org.openiam.idm.srvc.org.dto.OrganizationAttribute
import org.openiam.idm.srvc.org.service.OrganizationDataService
import org.openiam.idm.srvc.meta.ws.MetadataWebService
import org.openiam.idm.srvc.synch.dto.Attribute
import org.openiam.idm.srvc.synch.dto.LineObject
import org.openiam.idm.srvc.synch.service.AbstractTransformScript
import org.openiam.provision.dto.ProvisionUser
import org.openiam.idm.searchbeans.OrganizationSearchBean;
import org.springframework.context.ApplicationContext

import es.sia.idm.groovy.helper.Constants;
import es.sia.idm.groovy.tools.Logger;
import es.sia.idm.groovy.helper.SIAServiceHelper;
import es.sia.idm.groovy.helper.SIADbHelper;
import org.openiam.idm.groovy.helper.ServiceHelper;

class TransformCSVCenterSrcRecord extends AbstractTransformScript {

	ApplicationContext context;

	final String className = "TransformCSVCenterSrcRecord";

	ServiceHelper    sHelper  = new ServiceHelper();
	SIAServiceHelper iHelper  = new SIAServiceHelper();
	SIADbHelper      dbHelper = new SIADbHelper();
	Logger           logger   = new Logger();

	// Inicialize log
	String log_type  = iHelper.getBundleProperty(Constants.BUNDLE_INT, Constants.LOG_TYPE);
	String log_path  = iHelper.getBundleProperty(Constants.BUNDLE_INT, Constants.LOG_PATH);
	String log_file  = iHelper.getBundleProperty(Constants.BUNDLE_INT, Constants.LOG_FILE_CSV_CENTERS);
	String log_level = iHelper.getBundleProperty(Constants.BUNDLE_INT, Constants.LOG_LEVEL_CSV);
	def log          = logger.initLogFile(log_path,log_file,log_level);

	def SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

	// Servicios
	def OrganizationDataService orgService = sHelper.orgService();
	def MetadataWebService metadataService = sHelper.metaService();

	// Variables
	Organization centersOrg   = null;

	String operacion     = null;
	String idMeta4       = null;
	String nombre        = null;
	String direccion     = null;
	String codPostal     = null;
	String codLocalidad  = null;
	String numTelefono   = null;
	String numFax        = null;
	String descripcion   = null;
	String idResponsable = null;
	String responsable   = null;
	String tipoCentro    = null;
	String idMCXXI       = null;
	String fechaEfecto   = null;
	String servidorFich  = null;
	String actualizacion = null;

	// Atributos - Centros
	String attrIdMeta4       = null;	//ID_META4
	String attrIdMCXXI       = null;	//ID_MCXXI
	String attrDireccion     = null;	//DIRECCION
	String attrCodPostal     = null;	//CODIGO_POSTAL
	String attrCodLocalidad  = null;	//CODIGO_LOCAL
	String attrNumTelefono   = null;	//TELEFONO
	String attrNumFax        = null;	//FAX
	String attrResponsable   = null;	//RESPONSABLE
	String attrTipoCentro    = null;	//TIPO_CENTRO
	String attrFechaEfecto   = null;	//FECHA_EFECTO
	String attrServidorFich  = null;	//SERVIDOR_FICHEROS
	String attrActualizacion = null;	//ACTUALIZACION

	// Grupos
	String managedSystem = null;
	String organization  = null;

	// Atributos - Grupos
	String attrIDMCXXIGrp = null;	//ID_MXXI
	String attrTipoGrp  = null;	//TIPO

	boolean initialized = false;

	void init() {
		final String methodName = "init";
		logger.toLog(log_type,className,methodName,"Init.....");

		if (!initialized) {
			initialized = true;

			logger.toLog(log_type,className,methodName,"Load Initial Conext...");

			iHelper.setLogger(logger, context);

			Map<String,String> deptMeta = iHelper.getMetadataElementByType("departmentType");

			attrIdMeta4 = deptMeta.get("ID_META4");
			attrDireccion = deptMeta.get("DIRECCION");
			attrCodPostal = deptMeta.get("CODIGO_POSTAL");
			attrCodLocalidad = deptMeta.get("CODIGO_LOCAL");
			attrNumTelefono = deptMeta.get("TELEFONO");
			attrNumFax = deptMeta.get("FAX");
			attrResponsable = deptMeta.get("RESPONSABLE");
			attrTipoCentro = deptMeta.get("TIPO_CENTRO");
			attrFechaEfecto = deptMeta.get("FECHA_EFECTO");
			attrIdMCXXI = deptMeta.get("ID_MCXXI");
			attrServidorFich = deptMeta.get("SERVIDOR_FICHEROS");
			attrActualizacion = deptMeta.get("ACTUALIZACION");


			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Loaded attribute Id Meta4        :"+attrIdMeta4);
				logger.toLog(log_type,className,methodName,"Loaded attribute Direccion       :"+attrDireccion);
				logger.toLog(log_type,className,methodName,"Loaded attribute Codigo Postal   :"+attrCodPostal);
				logger.toLog(log_type,className,methodName,"Loaded attribute Codigo Localidad:"+attrCodLocalidad);
				logger.toLog(log_type,className,methodName,"Loaded attribute Telefono        :"+attrNumTelefono);
				logger.toLog(log_type,className,methodName,"Loaded attribute Fax             :"+attrNumFax);
				logger.toLog(log_type,className,methodName,"Loaded attribute Responsable     :"+attrResponsable);
				logger.toLog(log_type,className,methodName,"Loaded attribute Tipo Centro     :"+attrTipoCentro);
				logger.toLog(log_type,className,methodName,"Loaded attribute Id MCXXI        :"+attrIdMCXXI);
				logger.toLog(log_type,className,methodName,"Loaded attribute Fecha Efecto    :"+attrFechaEfecto);
				logger.toLog(log_type,className,methodName,"Loaded attribute Servidor Fichero:"+attrServidorFich);
				logger.toLog(log_type,className,methodName,"Loaded attribute ACTUALIZACION   :"+attrActualizacion);
			}

			Map<String,String> mcmGroupMeta = iHelper.getMetadataElementByType("MC XXI - Oficina");

			attrIDMCXXIGrp = mcmGroupMeta.get("ID_MCXXI");
			attrTipoGrp = mcmGroupMeta.get("TIPO");


			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Loaded attribute ID_MCXXI (Grupo)  :"+attrIDMCXXIGrp);
				logger.toLog(log_type,className,methodName,"Loaded attribute TIPO (Grupo)      :"+attrTipoGrp);
			}

		}
		logger.toLog(log_type,className,methodName,"End.....");
	}

	@Override
	public int execute(LineObject rowObj, ProvisionUser pUser) {
		final String methodName = "execute";

		logger.toLog(log_type,className,methodName,"Init.....");

		pUser.setSkipPostProcessor(true);
		pUser.setSkipPreprocessor(true);

		Organization newCenter = new Organization();
		populateObject(rowObj, newCenter);

		logger.toLog(log_type,className,methodName,"End.....Return code: -1");
		return -1;
	}

	private void populateObject(LineObject rowObj, Organization center)
	{
		final String methodName = "populateObject";

		logger.toLog(log_type,className,methodName,"Init.....");

		Map<String,Attribute> columnMap =  rowObj.getColumnMap();

		//actualizacion = "Automatico"; // Valor por defecto
		loadAttributes(columnMap);

		Organization org = iHelper.getOrganizationByTypeAndInternalOrgId("DEPARTMENT",idMeta4);

		if (operacion.equals("A"))
		{
			logger.toLog(log_type,className,methodName,"ADD Operation");

			if (org!=null){
				logger.toLog(log_type,className,methodName,"Centro "+idMeta4+"  not added, it is already registered");
				return;
			}
			createOrganizationCenter();
		}
		else if (operacion.equals("M") && !isUpdateManual(org))
		{

			logger.toLog(log_type,className,methodName,"MODIFY Operation");
			if (org==null){
				logger.toLog(log_type,className,methodName,"Centro "+idMeta4+"  not modificable, it is not registered");
				return;
			}
			updateOrganizationCenter(org);


		}
		else if (operacion.equals("B"))
		{
			logger.toLog(log_type,className,methodName,"DELETE Operation");
			if (org==null){
				logger.toLog(log_type,className,methodName,"Centro "+idMeta4+"  not deleted, it is not registered");
				return;
			}
			deleteOrganizationCenter(org);
		}

		logger.toLog(log_type,className,methodName,"End.....");
	}

	private void loadAttributes(Map<String,Attribute> columnMap)
	{
		final String methodName = "loadAttributes";

		logger.toLog(log_type,className,methodName,"Init.....");

		Attribute attrVal = null;

		attrVal = columnMap.get("OPERACION");
		if (!iHelper.isAttributeNull(attrVal,logger)) {
			operacion = attrVal.getValue();
		}
		attrVal = columnMap.get("ID_META4");
		if (!iHelper.isAttributeNull(attrVal,logger)) {
			idMeta4 = attrVal.getValue();
		}
		attrVal = columnMap.get("NOMBRE");
		if (!iHelper.isAttributeNull(attrVal,logger)) {
			nombre = attrVal.getValue().replace("<coma>",",");
		}
		attrVal = columnMap.get("DIRECCION");
		if (!iHelper.isAttributeNull(attrVal,logger)) {
			direccion = attrVal.getValue().replace("<coma>",",");
		}
		attrVal = columnMap.get("CODIGO_POSTAL");
		if (!iHelper.isAttributeNull(attrVal,logger)) {
			codPostal = attrVal.getValue();
		}
		attrVal = columnMap.get("CODIGO_LOCALIDAD");
		if (!iHelper.isAttributeNull(attrVal,logger)) {
			codLocalidad = attrVal.getValue();
		}
		attrVal = columnMap.get("TELEFONO");
		if (!iHelper.isAttributeNull(attrVal,logger)) {
			numTelefono = attrVal.getValue();
		}
		attrVal = columnMap.get("FAX");
		if (!iHelper.isAttributeNull(attrVal,logger)) {
			numFax = attrVal.getValue();
		}
		attrVal = columnMap.get("DESCRIPCION");
		if (!iHelper.isAttributeNull(attrVal,logger)) {
			descripcion = attrVal.getValue().replace("<coma>",",");
		}
		attrVal = columnMap.get("RESPONSABLE");
		if (!iHelper.isAttributeNull(attrVal,logger)) {
			responsable = attrVal.getValue();
		}
		attrVal = columnMap.get("TIPO_CENTRO");
		if (!iHelper.isAttributeNull(attrVal,logger)) {
			tipoCentro = attrVal.getValue();
		}
		attrVal = columnMap.get("ID_MCXXI");
		if (!iHelper.isAttributeNull(attrVal,logger)) {
			idMCXXI = attrVal.getValue();
		}
		attrVal = columnMap.get("FECHA_EFECTO");
		if (!iHelper.isAttributeNull(attrVal,logger)) {
			fechaEfecto = attrVal.getValue();
		}
		attrVal = columnMap.get("SERVIDOR_FICHEROS");
		if (!iHelper.isAttributeNull(attrVal,logger)) {
			servidorFich = attrVal.getValue();
		}

		logger.toLog(log_type,className,methodName,"End.....");

	}

	/**
	 * Crea un centro de trabajo (tipo DEPARTMENT).
	 */
	private void createOrganizationCenter() {

		final String methodName = "createOrganizationCenter";

		logger.toLog(log_type,className,methodName,"Init.....");

		Organization newCenter = new Organization();

		newCenter.setInternalOrgId(idMeta4);
		newCenter.setAlias(idMeta4);
		newCenter.setName(nombre);
		newCenter.setDescription(descripcion);
		//classificaction - No se puede gestionar
		newCenter.setMdTypeId("departmentType");
		newCenter.setOrganizationTypeId("DEPARTMENT");
		newCenter.setOrganizationTypeName("Centro Trabajo")
		newCenter.setStatus("ACTIVE");
		Date fechaActual = new Date();
		newCenter.setCreateDate(fechaActual);
		newCenter.setLstUpdate(fechaActual);

		if ("DEBUG".equals(log_level)) {
			logger.toLog(log_type,className,methodName,"Guardando el centro de trabajo");
		}

		// Pendiente Validacion respuesta
		orgService.saveOrganization(newCenter, '3000');

		//Recuperamos el centro creado
		//Organization center = getOrganizationCenterByInternalOrgId("DEPARTMENT",idMeta4);
		Organization center = iHelper.getOrganizationByTypeAndInternalOrgId("DEPARTMENT",idMeta4);
		if (center!=null) {

			String orgId = center.getId();
			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Centro de trabajo guardado satisfactoriamente, identificador :"+orgId);
				logger.toLog(log_type,className,methodName,"Recuperando atributos extendidos");
			}

			// A�adimos el resto de atributos del centro
			Set <OrganizationAttribute> extendedAttribs  = new HashSet();

			OrganizationAttribute orgAttrIdMeta4 = new OrganizationAttribute();
			orgAttrIdMeta4.metadataId = attrIdMeta4;
			orgAttrIdMeta4.name = "ID_META4";
			orgAttrIdMeta4.organizationId =  orgId;
			orgAttrIdMeta4.value = idMeta4;
			extendedAttribs.add(orgAttrIdMeta4);

			OrganizationAttribute orgAttrIdMCXXI = new OrganizationAttribute();
			orgAttrIdMCXXI.metadataId=attrIdMCXXI;
			orgAttrIdMCXXI.name="ID_MCXXI";
			orgAttrIdMCXXI.organizationId=orgId;
			orgAttrIdMCXXI.value=idMCXXI;
			extendedAttribs.add(orgAttrIdMCXXI);

			OrganizationAttribute orgAttrDireccion = new OrganizationAttribute();
			orgAttrDireccion.metadataId=attrDireccion;
			orgAttrDireccion.name="DIRECCION";
			orgAttrDireccion.organizationId=orgId;
			orgAttrDireccion.value=direccion;
			extendedAttribs.add(orgAttrDireccion);

			OrganizationAttribute orgAttrCodPostal = new OrganizationAttribute();
			orgAttrCodPostal.metadataId=attrCodPostal;
			orgAttrCodPostal.name="CODIGO_POSTAL";
			orgAttrCodPostal.organizationId=orgId;
			orgAttrCodPostal.value=codPostal;
			extendedAttribs.add(orgAttrCodPostal);

			OrganizationAttribute orgAttrCodLocalidad = new OrganizationAttribute();
			orgAttrCodLocalidad.metadataId=attrCodLocalidad;
			orgAttrCodLocalidad.name="CODIGO_LOCAL";
			orgAttrCodLocalidad.organizationId=orgId;
			orgAttrCodLocalidad.value=codLocalidad;
			extendedAttribs.add(orgAttrCodLocalidad);

			OrganizationAttribute orgAttrNumTelefono = new OrganizationAttribute();
			orgAttrNumTelefono.metadataId=attrNumTelefono;
			orgAttrNumTelefono.name="TELEFONO";
			orgAttrNumTelefono.organizationId=orgId;
			orgAttrNumTelefono.value=numTelefono;
			extendedAttribs.add(orgAttrNumTelefono);

			OrganizationAttribute orgAttrNumFax = new OrganizationAttribute();
			orgAttrNumFax.metadataId=attrNumFax;
			orgAttrNumFax.name="FAX";
			orgAttrNumFax.organizationId=orgId;
			orgAttrNumFax.value=numFax;
			extendedAttribs.add(orgAttrNumFax);

			OrganizationAttribute orgAttrResponsable = new OrganizationAttribute();
			orgAttrResponsable.metadataId=attrResponsable;
			orgAttrResponsable.name="RESPONSABLE";
			orgAttrResponsable.organizationId=orgId;
			orgAttrResponsable.value=responsable;
			extendedAttribs.add(orgAttrResponsable);

			OrganizationAttribute orgAttrTipoCentro = new OrganizationAttribute();
			orgAttrTipoCentro.metadataId=attrTipoCentro;
			orgAttrTipoCentro.name="TIPO_CENTRO";
			orgAttrTipoCentro.organizationId=orgId;
			orgAttrTipoCentro.value=tipoCentro;
			extendedAttribs.add(orgAttrTipoCentro);

			OrganizationAttribute orgAttrServidorFich = new OrganizationAttribute();
			orgAttrServidorFich.metadataId=attrServidorFich;
			orgAttrServidorFich.name="SERVIDOR_FICHEROS";
			orgAttrServidorFich.organizationId=orgId;
			orgAttrServidorFich.value=servidorFich;
			extendedAttribs.add(orgAttrServidorFich);

			OrganizationAttribute orgAttrFechaEfecto = new OrganizationAttribute();
			orgAttrFechaEfecto.metadataId=attrFechaEfecto;
			orgAttrFechaEfecto.name="FECHA_EFECTO";
			orgAttrFechaEfecto.organizationId=orgId;
			orgAttrFechaEfecto.value=fechaEfecto;
			extendedAttribs.add(orgAttrFechaEfecto);
			
			//Por defecto se ponen en Automatico
			OrganizationAttribute orgAttrActualizacion = new OrganizationAttribute();
			orgAttrActualizacion.metadataId=attrActualizacion;
			orgAttrActualizacion.name="ACTUALIZACION";
			orgAttrActualizacion.organizationId=orgId;
			orgAttrActualizacion.value="Automatico";
			extendedAttribs.add(orgAttrActualizacion);

			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Actualizado el centro con los atributos extendidos.");
			}

			center.setAttributes(extendedAttribs);
			orgService.saveOrganization(center,'3000');

			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Centro de trabajo actualizado satisfactoriamente.");
				logger.toLog(log_type,className,methodName,"Generando grupo asociado.");
			}

			createGroupDB(idMeta4,nombre,orgId, idMCXXI);

		} else {
			logger.toLog("ERROR",className,methodName,"Centro de trabajo guardado no se ha podido recuperar para a�adir atributos");
		}

		logger.toLog(log_type,className,methodName,"End.....");
	}

	private void updateOrganizationCenter(Organization orgCenter) {

		final String methodName = "updateOrganizationCenter";

		boolean modifyUsers = false;

		logger.toLog(log_type,className,methodName,"Init.....");

		Date fechaActual = new Date();
		orgCenter.setLstUpdate(fechaActual);
		orgCenter.setStatus("ACTIVE");


		if (nombre!=null && !orgCenter.getName().equals(nombre)) {
			modifyUsers = true;
			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Actualizando attributo NOMBRE, valor antiguo:"+orgCenter.getName()+", valor nuevo:"+nombre);
			}
			orgCenter.setName(nombre);

			// Actualiazamos el nombre del grupo
			updateGroupDB (orgCenter.getInternalOrgId(), nombre);

		} else {
			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Actualizando attributo NOMBRE, no cambia");
			}
		}

		if (descripcion!=null && !orgCenter.getDescription().equals(descripcion)) {
			orgCenter.setDescription(descripcion);
			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Actualizando attributo DESCRIPTION, valor antiguo:"+orgCenter.getDescription()+", valor nuevo:"+descripcion);
			}
		} else {
			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Actualizando attributo DESCRIPTION, no cambia");
			}
		}

		// Recuperamos los atributos del centro
		logger.toLog(log_type,className,methodName,"Recuperando Atributos Extendidos");
		Set <OrganizationAttribute> extendedAttribs  = orgCenter.getAttributes();

		for (OrganizationAttribute orgAtt : extendedAttribs){
			String orgAttName = orgAtt.getName();
			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Evaluando attributo "+orgAttName);
			}
			switch (orgAttName){
				case "ID_META4": // No debe cambiar el identificador
					if ("DEBUG".equals(log_level)) {
						logger.toLog(log_type,className,methodName,"Actualizando attributo ID_META4, no cambia");
					}
					orgAtt.setValue(idMeta4);
					orgAtt.setMetadataElementId(attrIdMeta4);
					break;
				case "ID_MCXXI":
					if (!"--".equals(idMCXXI) ) {
						if ("DEBUG".equals(log_level)) {
							logger.toLog(log_type,className,methodName,"Actualizando attributo ID_MCXXI, valor antiguo:"+orgAtt.getValue()+", valor nuevo:"+idMCXXI);
						}
						orgAtt.setValue(idMCXXI);
						orgAtt.setMetadataElementId(attrIdMCXXI);
					} else {
						if ("DEBUG".equals(log_level)) {
							logger.toLog(log_type,className,methodName,"Actualizando attributo ID_MCXXI, no cambia");
						}
					}
					break;
				case "DIRECCION":
					if (!"--".equals(direccion) ) {
						modifyUsers = true;
						if ("DEBUG".equals(log_level)) {
							logger.toLog(log_type,className,methodName,"Actualizando attributo DIRECCION, valor antiguo:"+orgAtt.getValue()+", valor nuevo:"+direccion);
						}
						orgAtt.setValue(direccion);
						orgAtt.setMetadataElementId(attrDireccion);
					} else {
						if ("DEBUG".equals(log_level)) {
							logger.toLog(log_type,className,methodName,"Actualizando attributo DIRECCION, no cambia");
						}
					}
					break;
				case "CODIGO_POSTAL":
					if (!"--".equals(codPostal) ) {
						modifyUsers = true;
						if ("DEBUG".equals(log_level)) {
							logger.toLog(log_type,className,methodName,"Actualizando attributo CODIGO_POSTAL, valor antiguo:"+orgAtt.getValue()+", valor nuevo:"+codPostal);
						}
						orgAtt.setValue(codPostal);
						orgAtt.setMetadataElementId(attrCodPostal);
					} else {
						if ("DEBUG".equals(log_level)) {
							logger.toLog(log_type,className,methodName,"Actualizando attributo CODIGO_POSTAL, no cambia");
						}
					}
					break;
				case "CODIGO_LOCAL":
					if (!"--".equals(codLocalidad) ) {
						modifyUsers = true;
						if ("DEBUG".equals(log_level)) {
							logger.toLog(log_type,className,methodName,"Actualizando attributo CODIGO_LOCAL, valor antiguo:"+orgAtt.getValue()+", valor nuevo:"+codLocalidad);
						}
						orgAtt.setValue(codLocalidad);
						orgAtt.setMetadataElementId(attrCodLocalidad);
					} else {
						if ("DEBUG".equals(log_level)) {
							logger.toLog(log_type,className,methodName,"Actualizando attributo CODIGO_LOCAL, no cambia");
						}
					}
					break;
				case "TELEFONO":
					if (!"--".equals(numTelefono) ) {
						modifyUsers = true;
						if ("DEBUG".equals(log_level)) {
							logger.toLog(log_type,className,methodName,"Actualizando attributo TELEFONO, valor antiguo:"+orgAtt.getValue()+", valor nuevo:"+numTelefono);
						}
						orgAtt.setValue(numTelefono);
						orgAtt.setMetadataElementId(attrNumTelefono);
					} else {
						if ("DEBUG".equals(log_level)) {
							logger.toLog(log_type,className,methodName,"Actualizando attributo TELEFONO, no cambia");
						}
					}
					break;
				case "FAX":
					if (!"--".equals(numFax) ) {
						modifyUsers = true;
						if ("DEBUG".equals(log_level)) {
							logger.toLog(log_type,className,methodName,"Actualizando attributo FAX, valor antiguo:"+orgAtt.getValue()+", valor nuevo:"+numFax);
						}
						orgAtt.setValue(numFax);
						orgAtt.setMetadataElementId(attrNumFax);
					} else {
						if ("DEBUG".equals(log_level)) {
							logger.toLog(log_type,className,methodName,"Actualizando attributo FAX, no cambia");
						}
					}
					break;
				case "RESPONSABLE":
					if (!"--".equals(responsable) ) {
						modifyUsers = true;
						if ("DEBUG".equals(log_level)) {
							logger.toLog(log_type,className,methodName,"Actualizando attributo RESPONSABLE, valor antiguo:"+orgAtt.getValue()+", valor nuevo:"+responsable);
						}
						orgAtt.setValue(responsable);
						orgAtt.setMetadataElementId(attrResponsable);
					}
					break;
				case "TIPO_CENTRO":
					if (!"--".equals(tipoCentro) ) {
						modifyUsers = true;
						if ("DEBUG".equals(log_level)) {
							logger.toLog(log_type,className,methodName,"Actualizando attributo TIPO_CENTRO, valor antiguo:"+orgAtt.getValue()+", valor nuevo:"+tipoCentro);
						}
						orgAtt.setValue(tipoCentro);
						orgAtt.setMetadataElementId(attrTipoCentro);
					} else {
						if ("DEBUG".equals(log_level)) {
							logger.toLog(log_type,className,methodName,"Actualizando attributo TIPO_CENTRO no cambia");
						}
					}
					break;
				case "SERVIDOR_FICHEROS":
					if (!"--".equals(servidorFich) ) {
						modifyUsers = true;
						if ("DEBUG".equals(log_level)) {
							logger.toLog(log_type,className,methodName,"Actualizando attributo SERVIDOR_FICHEROS, valor antiguo:"+orgAtt.getValue()+", valor nuevo:"+servidorFich);
						}
						orgAtt.setValue(servidorFich);
						orgAtt.setMetadataElementId(attrServidorFich);
					} else {
						if ("DEBUG".equals(log_level)) {
							logger.toLog(log_type,className,methodName,"Actualizando attributo SERVIDOR_FICHEROS no cambia");
						}
					}
					break;
				case "FECHA_EFECTO":
					if (!"--".equals(fechaEfecto) ) {
						modifyUsers = true;
						if ("DEBUG".equals(log_level)) {
							logger.toLog(log_type,className,methodName,"Actualizando attributo FECHA_EFECTO, valor antiguo:"+orgAtt.getValue()+", valor nuevo:"+fechaEfecto);
						}
						orgAtt.setValue(fechaEfecto);
						orgAtt.setMetadataElementId(attrFechaEfecto);
					} else {
						if ("DEBUG".equals(log_level)) {
							logger.toLog(log_type,className,methodName,"Actualizando attributo FECHA_EFECTO no cambia");
						}
					}
					break;
				default:
					logger.toLog(log_type,className,methodName,"WARNING","Atributo "+orgAtt.getName()+" no encontrado.");
					break;
			}
			//orgService.saveAttribute(orgAtt);
		}

		// Actualizamos el centro
		if ("DEBUG".equals(log_level)) {
			logger.toLog(log_type,className,methodName,"Actualizando el centro.");
		}
		orgService.saveOrganization(orgCenter, '3000');

		if ("DEBUG".equals(log_level)) {
			logger.toLog(log_type,className,methodName,"Centro de trabajo actualizado satisfactoriamente.");
		}

		logger.toLog(log_type,className,methodName,"End.....");
	}


	/**
	 * Borra el centro de trabajo. Borrado l�gico, pone su estado como "DELETED"
	 * Se verifica si el nodo tiene usuarios, en cuyo caso se genera un ticket en CA Service Desk
	 * @param orgCenter Objeto Organization con el centro a borrar
	 */
	private void deleteOrganizationCenter(Organization orgCenter) {

		final String methodName = "deleteOrganizationCenter";

		logger.toLog(log_type,className,methodName,"Init.....");

		// Verificacion de la existencia de usuarios. Si tiene usuario habr� que generar un ticket
		if ("DEBUG".equals(log_level)) {
			logger.toLog(log_type,className,methodName,"Verificando la existencia de usuarios en el centro de trabajo.");
		}
		if (iHelper.hasUsersOrganization (orgCenter.id)) {
			logger.toLog(log_type,className,methodName,"WARNING","El centro de trabajo "+orgCenter.name+" a borrar tiene usuarios");
			StringBuffer sb = new StringBuffer();
			sb.append ("El centro de trabajo "+orgCenter.name+" ha sido eliminado en Meta4 pero en OpenIAM todav�a tiene usuarios asignados, ");
			sb.append (" se deben reubicar los usuarios en otros centros y eliminar definitivamente el centro de trabajo");
			String ticket = iHelper.createCAIssue(sb.toString(),"I","Centro de trabajo eliminado con usuarios asignados (Carga Centros Meta4)");
		} else {
		if ("DEBUG".equals(log_level)) {
			logger.toLog(log_type,className,methodName,"La unidad funcional "+orgCenter.name+" se puede borrar, no tiene usuarios");
		}
			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Borrando centro de trabajo.");
			}
			deletedGroupDB(orgCenter.getInternalOrgId());
			orgService.deleteOrganization(orgCenter.id);
			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Centro de trabajo borrado satisfactoriamente.");
			}
			
		}
		/**
		orgCenter.setStatus("DELETED");
		orgCenter.setSelectable(false);
		Date fechaActual = new Date();
		orgCenter.setLstUpdate(fechaActual);
		if ("DEBUG".equals(log_level)) {
			logger.toLog(log_type,className,methodName,"Actualizando el centro a estado DELETED.");
		}

		//orgService.deleteOrganization(orgCenter.id);
		orgService.saveOrganization(orgCenter, '3000');

		if ("DEBUG".equals(log_level)) {
			logger.toLog(log_type,className,methodName,"Centro de trabajo actualizado satisfactoriamente.");
			logger.toLog(log_type,className,methodName,"Actualizando grupo correspondiente a estado DELETED.");
		}
		deletedGroupDB(orgCenter.getInternalOrgId());
		*/
		logger.toLog(log_type,className,methodName,"End.....");

	}

	private void createGroupDB (String idMeta4, String groupName, String centerId, String idMCXXI) {
		final String methodName = "createGroupDB(String idMeta4, String groupName, String centerId, String idMCXXI)";

		logger.toLog(log_type,className,methodName,"Init.....");

		String idgroup     = idMeta4;
		String grpName     = groupName + " (Oficina MC XXI)";
		String description = groupName;
		Date fechaActual = new Date();
		String status      = "ACTIVE";
		String companyId   = centerId;
		String managedSys  = "200";  //MCXXI

		def paramList = [
			idgroup,
			grpName,
			description,
			fechaActual,
			fechaActual,
			status,
			companyId,
			managedSys,
			'MC XXI - Oficina'
		]
		def query    = "INSERT INTO GRP ( GRP_ID, GRP_NAME, GROUP_DESC, CREATE_DATE, LAST_UPDATE, STATUS, COMPANY_ID, MANAGED_SYS_ID, TYPE_ID) VALUES (?,?,?,?,?,?,?,?,?)";

		//def paramListRes = ["200",idMeta4]
		//def queryRes = "INSERT INTO RESOURCE_GROUP ( RESOURCE_ID, GRP_ID) VALUES (?,?)";

		def paramListRes = [iHelper.uuid(),idMeta4,attrIDMCXXIGrp,idMCXXI]
		def queryRes = "INSERT INTO GRP_ATTRIBUTES (ID,GRP_ID,METADATA_ID,NAME,ATTR_VALUE,IS_MULTIVALUED) VALUES (?,?,?,'ID_MCXXI',?,'N');";

                def paramListRes2 = [iHelper.uuid(),idMeta4,attrTipoGrp,'Oficina MC XXI']
                def queryRes2 = "INSERT INTO GRP_ATTRIBUTES (ID,GRP_ID,METADATA_ID,NAME,ATTR_VALUE,IS_MULTIVALUED) VALUES (?,?,?,'TIPO',?,'N');";

		if ("DEBUG".equals(log_level)) {
			logger.toLog(log_type,className,methodName,"Adding group :"+idMeta4+"-"+groupName);
		}

		def connDB = null;
		try {
			logger.toLog(log_type,className,methodName,"Init.....");
			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Estableciendo conexion con la base de datos");
			}
			connDB = connectDB();
			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Conexion Ok");
			}

			connDB.execute (query,paramList);

			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Group added successfully");
				logger.toLog(log_type,className,methodName,"Associating Attribute ID MC XXI");
			}
			connDB.execute (queryRes,paramListRes);
			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Atribute ID MC XXI associated successfully");
			}

                        connDB.execute (queryRes2,paramListRes2);
                        if ("DEBUG".equals(log_level)) {
                                logger.toLog(log_type,className,methodName,"Atribute TIPO associated successfully");
                        }


		} catch (SQLException sq) {
			logger.toLog(log_type,className,methodName,"ERROR","SQLException :"+ sq.getMessage());
		} finally {
			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Cerrando la conexion con la base de datos");
			}
			connDB.close();
			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Conexion cerrada Ok");
			}

		}
		logger.toLog(log_type,className,methodName,"End.....");

	}

	private void updateGroupDB (String idMeta4, String groupName) {
		final String methodName = "updateGroupDB(String idMeta4, String groupName)";

		logger.toLog(log_type,className,methodName,"Init.....");
		String idgroup     = idMeta4;
		String grpName     = groupName;
		String description = groupName;
		Date fechaActual = new Date();
		String status      = "ACTIVE";

		def paramList = [
			grpName,
			description,
			fechaActual,
			status,
			idgroup
		]
		def query = "UPDATE GRP SET GRP_NAME = ?, GROUP_DESC = ?, LAST_UPDATE = ?, STATUS = ? WHERE GRP_ID = ?";
		def connDB = null;
		try {
			logger.toLog(log_type,className,methodName,"Init.....");
			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Estableciendo conexion con la base de datos");
			}
			connDB = connectDB();
			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Conexion Ok");
			}

			connDB.execute (query,paramList);

			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Group updated successfully");
			}
		} catch (SQLException sq) {
			logger.toLog(log_type,className,methodName,"ERROR","SQLException :"+ sq.getMessage());
		} finally {
			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Cerrando la conexion con la base de datos");
			}
			connDB.close();
			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Conexion cerrada Ok");
			}

		}
		logger.toLog(log_type,className,methodName,"End.....");
	}

	private void deletedGroupDB (String idMeta4) {
		final String methodName = "deletedGroupDB(String idMeta4)";

		logger.toLog(log_type,className,methodName,"Init.....");
		String idgroup   = idMeta4;
		Date fechaActual = new Date();
		String status    = "DELETED";

		def paramList = [
			idgroup
		]

		def query = "DELETE FROM GRP WHERE GRP_ID = ?";

		def queryAttr = "DELETE FROM GRP_ATTRIBUTES WHERE GRP_ID = ?";
		def connDB = null;

		try {
			logger.toLog(log_type,className,methodName,"Init.....");
			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Estableciendo conexion con la base de datos");
			}
			connDB = connectDB();
			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Conexion Ok");
			}

			connDB.execute (queryAttr,paramList);

			connDB.execute (query,paramList);

			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Group deleted successfully");
			}
		} catch (SQLException sq) {
			logger.toLog(log_type,className,methodName,"ERROR","SQLException :"+ sq.getMessage());
		} finally {
			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Cerrando la conexion con la base de datos");
			}
			connDB.close();
			if ("DEBUG".equals(log_level)) {
				logger.toLog(log_type,className,methodName,"Conexion cerrada Ok");
			}

		}
		logger.toLog(log_type,className,methodName,"End.....");
	}


	
	private boolean isUpdateManual(Organization org) {
		final String methodName = "isUpdateManual(Organization org)";
		boolean isManual = true;
		
		if ("DEBUG".equals(log_level)) {
			logger.toLog(log_type,className,methodName,"Init...");
		}
		
		// Verificamos el tipo de actualizacion: Automatico/Manual
		Set <OrganizationAttribute> extendedAttribs  = org.getAttributes();
		
		for (OrganizationAttribute orgAtt : extendedAttribs) {
			String orgAttName = orgAtt.getName();
			if ("ACTUALIZACION".equals(orgAttName)) {
				if(orgAtt.getValue()!=null && "Automatico".equals(orgAtt.getValue())) {
					isManual = false;
				}
			}
		}
			
		if ("DEBUG".equals(log_level)) {
			logger.toLog(log_type,className,methodName,"Centro de Trabajo "+org.getName()+" en modo manual:"+isManual);
			logger.toLog(log_type,className,methodName,"End...");
		}
		return isManual;
	}


	/**
	 * Funcion de conexion a la base de datos
	 * Pendiente formalizar en el SIADBHelper
	 * @return Sql conexion a la base de datos
	 */
	def Sql connectDB() {
		return dbHelper.connectDb();
	}


}