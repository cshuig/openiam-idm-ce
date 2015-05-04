OPENIAM = window.OPENIAM || {};

OPENIAM.EditProfileBootstrap = {
	onReady : function(readyArgs) {
		var $this = this;
		
		var count = 0;
		var tr = null;
		$("#uiTemplate").openiamUITemplate({
			templateObject : OPENIAM.ENV.UITEMPLATE,
			onTemplateObjectEmpty : function() {
				$("#uiTemplateTable").hide();
			},
			onAppend : function(label, element) {
				if(count == 0 || (count % 3) == 0) {
					tr = document.createElement("tr");
					$(this).append(tr);
				}
				var td = document.createElement("td");
				$(td).attr("valign", "top"); //html4 (i.e. IE :( ) 
				$(td).append(label, element);
				$(tr).append(td);
				count++;
			},
			onFinishedDrawing : function() {
				if(count > 0 && (count % 3) > 0) {
					for(var i = 0; i < (3 - (count % 3)); i++) {
						$(tr).append(document.createElement("td"));
					}
				}
			}
		});
		
		var emailModalFields = [
			{fieldName: "metadataTypeId", type:"select",label:localeManager["openiam.ui.common.email.address.type"], items : OPENIAM.ENV.EmailTypes, required : true},
		    {fieldName: "emailAddress", type:"text",label:localeManager["openiam.ui.common.email.address"], required:true},
		    {fieldName: "description", type:"text",label:localeManager["openiam.ui.common.description"]},
		    {fieldName: "isActive", type:"checkbox", label:localeManager["openiam.ui.common.is.active"], readonly:false, required:false},
		    {fieldName: "isDefault", type:"checkbox", label:localeManager["openiam.ui.common.is.default"], readonly:false, required:false}
		];
		
		if($("#emailAddresses").length > 0) {
			$("#emailAddresses").persistentTable({
				required : OPENIAM.ENV.EmailRequired,
				emptyMessage : localeManager["openiam.ui.selfservice.ui.template.no.emails"],
				createEnabled : OPENIAM.ENV.supportsEmailCreation,
				objectArray : OPENIAM.ENV.emailList,
				createBtnId : "createEmail",
				headerFields : [
					localeManager["openiam.ui.common.email.address.type"], 
					localeManager["openiam.ui.common.email.address"], 
					localeManager["openiam.ui.common.is.default"], 
					localeManager["openiam.ui.common.is.active"]
				],
				fieldNames : ["typeDescription", "emailAddress", "isDefault", "isActive"],
				deleteEnabledField : "editable",
				editEnabledField : "editable",
				createText : localeManager["openiam.ui.selfservice.ui.template.add.email"],
				actionsColumnName : localeManager["openiam.ui.common.actions"],
				tableTitle : localeManager["openiam.ui.selfservice.ui.template.email"],
				onDeleteClick : function(obj) {
					var objArray = OPENIAM.ENV.emailList;
					var hasDefault = false;
					$.each(objArray, function(idx, objAtIdx) {
						if(objAtIdx.isDefault) {
							hasDefault = true;
						}
					});
					
					if(!hasDefault && objArray.length > 0) {
						objArray[0].isDefault = true;
					}
				},
				equals : function(obj1, obj2) {
					if(obj1.emailId == null || obj2.emailId == null) {
						return obj1.emailAddress == obj2.emailAddress;
					} else {
						return obj1.emailId == obj2.emailId;
					}
				},
				onEditClick : function(obj) {
					var $this = this;
					$("#editDialog").modalEdit({
						fields: emailModalFields,
			            dialogTitle: localeManager["openiam.ui.selfservice.ui.template.edit.email"],
			            onSubmit: function(bean){
			            	obj.name = bean.name;
			            	obj.description = bean.description;
			            	obj.emailAddress = bean.emailAddress;
			            	obj.isActive = (bean.isActive == true || OPENIAM.ENV.emailList.length == 1);
			            	obj.isDefault = (bean.isDefault == true || OPENIAM.ENV.emailList.length == 1);
			            	obj.metadataTypeId = bean.metadataTypeId;
			            	try {
			            		obj.typeDescription = OPENIAM.ENV.EmailTypeMap[bean.metadataTypeId].name
			            	} catch(e) {
			            		obj.typeDescription = "";
			            	}
			            	$("#editDialog").modalEdit("hide");
			            	if(obj.isDefault === true) {
			            		$this.persistentTable("setPropertyOnAll", "isDefault", false);
			            		obj.isDefault = true;
			            	}
			            	$this.persistentTable("draw");
			            }
	                });
					$("#editDialog").modalEdit("show", obj);
				},
				onCreateClick : function() {
					var $this = this;
					var obj = {};
					$("#editDialog").modalEdit({
						fields: emailModalFields,
			            dialogTitle: localeManager["openiam.ui.selfservice.ui.template.edit.email"],
			            onSubmit: function(bean){
			            	obj.description = bean.description;
			            	obj.emailAddress = bean.emailAddress;
			            	obj.metadataTypeId = bean.metadataTypeId;
			            	try {
			            		obj.typeDescription = OPENIAM.ENV.EmailTypeMap[bean.metadataTypeId].name
			            	} catch(e) {
			            		obj.typeDescription = "";
			            	}
			            	obj.isActive = (bean.isActive == true  || OPENIAM.ENV.emailList.length == 0);
			            	obj.isDefault = (bean.isDefault == true || OPENIAM.ENV.emailList.length == 0);
			            	obj.name = bean.name;
			            	obj.editable = true;
			            	obj.emailId = null;
			            	if(obj.isDefault === true) {
			            		$this.persistentTable("setPropertyOnAll", "isDefault", false);
			            		obj.isDefault = true;
			            	}
			            	$("#editDialog").modalEdit("hide");
			            	$this.persistentTable("addObject", obj);
			            	$this.persistentTable("draw");
			            }
	                });
					$("#editDialog").modalEdit("show", obj);
				}
			});
		}
		
		var phoneModalFields = [
			{fieldName: "metadataTypeId", type:"select",label:localeManager["openiam.ui.common.phone.type"], items : OPENIAM.ENV.PhoneTypes, required: true},
	        {fieldName: "countryCd", type:"text",label:localeManager["openiam.ui.common.phone.country.code"]},
		    {fieldName: "areaCd", type:"text",label:localeManager["openiam.ui.common.phone.area.code"], required:true},
		    {fieldName: "phoneNbr", type:"text",label:localeManager["openiam.ui.common.phone.number"], required:true},
		    {fieldName: "phoneExt", type:"text",label:localeManager["openiam.ui.common.phone.extension"]},
		    {fieldName: "isDefault", type:"checkbox", label:localeManager["openiam.ui.common.is.default"], readonly:false, required:false},
		    {fieldName: "isActive", type:"checkbox", label:localeManager["openiam.ui.common.is.active"], readonly:false, required:false}
		];
		
		if($("#phoneNumbers").length > 0) {
			$("#phoneNumbers").persistentTable({
				required : OPENIAM.ENV.PhoneRequired,
				emptyMessage : localeManager["openiam.ui.selfservice.ui.template.no.phones"],
				createEnabled : OPENIAM.ENV.supportsPhoneCreation,
				createBtnId : "createPhone",
				objectArray : OPENIAM.ENV.phoneList,
				headerFields : [
					localeManager["openiam.ui.common.phone.type"], 
					localeManager["openiam.ui.common.phone.country.code"], 
					localeManager["openiam.ui.common.phone.area.code"], 
					localeManager["openiam.ui.common.phone.number"], 
					localeManager["openiam.ui.common.phone.extension"], 
					localeManager["openiam.ui.common.is.default"], 
					localeManager["openiam.ui.common.is.active"]
				],
				fieldNames : ["typeDescription", "countryCd", "areaCd", "phoneNbr", "phoneExt", "isDefault", "isActive"],
				deleteEnabledField : "editable",
				editEnabledField : "editable",
				createText : localeManager["openiam.ui.selfservice.ui.template.add.phone"],
				actionsColumnName : localeManager["openiam.ui.common.actions"],
				tableTitle : localeManager["openiam.ui.selfservice.ui.template.phones"],
				onDeleteClick : function(obj) {
					var objArray = OPENIAM.ENV.phoneList;
					var hasDefault = false;
					$.each(objArray, function(idx, objAtIdx) {
						if(objAtIdx.isDefault) {
							hasDefault = true;
						}
					});
					
					if(!hasDefault && objArray.length > 0) {
						objArray[0].isDefault = true;
					}
				},
				equals : function(obj1, obj2) {
					if(obj1.phoneId == null || obj2.phoneId == null) {
						return obj1.name == obj2.name &&
							   obj1.countryCd == obj2.countryCd &&
							   obj1.areaCd == obj2.areaCd &&
							   obj1.phoneNbr == obj2.phoneNbr &&
							   obj1.phoneExt == obj2.phoneExt;
					} else {
						return obj1.phoneId == obj2.phoneId;
					}
				},
				onEditClick : function(obj) {
					var $this = this;
					$("#editDialog").modalEdit({
						fields: phoneModalFields,
			            dialogTitle: localeManager["openiam.ui.selfservice.ui.template.edit.phone"],
			            onSubmit: function(bean){
			            	obj.name = bean.name;
			            	obj.countryCd = bean.countryCd;
			            	obj.areaCd = bean.areaCd;
			            	obj.phoneNbr = bean.phoneNbr;
			            	obj.phoneExt = bean.phoneExt;
			            	obj.metadataTypeId = bean.metadataTypeId;
			            	try {
			            		obj.typeDescription = OPENIAM.ENV.PhoneTypeMap[bean.metadataTypeId].name;
			            	} catch(e) {
			            		obj.typeDescription = "";
			            	}
			            	obj.isDefault = (bean.isDefault == true || OPENIAM.ENV.phoneList.length == 1);
			            	obj.isActive = (bean.isActive == true || OPENIAM.ENV.phoneList.length == 1);
			            	$("#editDialog").modalEdit("hide");
			            	if(obj.isDefault === true) {
			            		$this.persistentTable("setPropertyOnAll", "isDefault", false);
			            		obj.isDefault = true;
			            	}
			            	$this.persistentTable("draw");
			            }
	                });
					$("#editDialog").modalEdit("show", obj);
				},
				onCreateClick : function() {
					var $this = this;
					var obj = {};
					$("#editDialog").modalEdit({
						fields: phoneModalFields,
			            dialogTitle: localeManager["openiam.ui.selfservice.ui.template.edit.phone"],
			            onSubmit: function(bean){
			            	obj.name = bean.name;
			            	obj.countryCd = bean.countryCd;
			            	obj.areaCd = bean.areaCd;
			            	obj.phoneNbr = bean.phoneNbr;
			            	obj.phoneExt = bean.phoneExt;
			            	obj.metadataTypeId = bean.metadataTypeId;
			            	try {
			            		obj.typeDescription = OPENIAM.ENV.PhoneTypeMap[bean.metadataTypeId].name
			            	} catch(e) {
			            		obj.typeDescription = "";
			            	}
			            	obj.isDefault = (bean.isDefault == true || OPENIAM.ENV.phoneList.length == 0);
			            	obj.isActive = (bean.isActive || OPENIAM.ENV.phoneList.length == 0);
			            	obj.editable = true;
			            	if(obj.isDefault === true) {
			            		$this.persistentTable("setPropertyOnAll", "isDefault", false);
			            		obj.isDefault = true;
			            	}
			            	$("#editDialog").modalEdit("hide");
			            	$this.persistentTable("addObject", obj)
			            	$this.persistentTable("draw");
			            }
	                });
					$("#editDialog").modalEdit("show", obj);
				}
			});
		}
		
		var addressModalFields = [
								  {fieldName: "metadataTypeId", type:"select",label:localeManager["openiam.ui.common.address.type"], items : OPENIAM.ENV.AddressTypes, required: true},
		                          {fieldName: "bldgNumber", type:"text",label:localeManager["openiam.ui.common.address.building"]},
		                          {fieldName: "address1", type:"text",label:localeManager["openiam.ui.common.address.1"]},
		                          {fieldName: "address2", type:"text",label:localeManager["openiam.ui.common.address.2"]},
		                          {fieldName: "city", type:"text",label:localeManager["openiam.ui.common.address.city"]},
		                          {fieldName: "state", type:"text",label:localeManager["openiam.ui.common.address.state"]},
		                          {fieldName: "country", type:"text",label:localeManager["openiam.ui.common.address.country"], readonly:false, required:true},
		                          {fieldName: "postalCd", type:"text",label:localeManager["openiam.ui.common.address.postal.code"]},
		                          {fieldName: "isDefault", type:"checkbox", label:localeManager["openiam.ui.common.is.default"], readonly:false, required:false},
		                          {fieldName: "isActive", type:"checkbox", label:localeManager["openiam.ui.common.is.active"], readonly:false, required:false}
		            		];
	
		if($("#addresses").length > 0) {

			$("#addresses").persistentTable({
				required : OPENIAM.ENV.AddressRequired,
				emptyMessage : localeManager["openiam.ui.selfservice.ui.template.no.addresses"],
				createEnabled : OPENIAM.ENV.supportsAddressCreation,
				createBtnId : "createAddress",
				objectArray : OPENIAM.ENV.addressList,
				headerFields : [
					localeManager["openiam.ui.common.address.type"],
					localeManager["openiam.ui.common.address.building"],
					localeManager["openiam.ui.common.address.1"], 
					localeManager["openiam.ui.common.address.2"], 
					localeManager["openiam.ui.common.address.city"], 
					localeManager["openiam.ui.common.address.state"], 
					localeManager["openiam.ui.common.address.country"], 
					localeManager["openiam.ui.common.address.postal.code"], 
					localeManager["openiam.ui.common.is.default"], 
					localeManager["openiam.ui.common.is.active"]
				],
				fieldNames : ["typeDescription","bldgNumber", "address1", "address2", "city", "state", "country", "postalCd", "isDefault", "isActive"],
				deleteEnabledField : "editable",
				editEnabledField : "editable",
				createText : localeManager["openiam.ui.selfservice.ui.template.add.address"],
				actionsColumnName : localeManager["openiam.ui.common.actions"],
				tableTitle : localeManager["openiam.ui.selfservice.ui.template.addresses"],
				additionalBtnText : localeManager["openiam.ui.button.add.location.to.address"],
				additionalBtnId : "addLocationBtn",
				onDeleteClick : function(obj) {
					var objArray = OPENIAM.ENV.addressList;
					var hasDefault = false;
					$.each(objArray, function(idx, objAtIdx) {
						if(objAtIdx.isDefault) {
							hasDefault = true;
						}
					});
					
					if(!hasDefault && objArray.length > 0) {
						objArray[0].isDefault = true;
					}
				},
				equals : function(obj1, obj2) {
					if(obj1.addressId == null || obj2.addressId == null) {
						return obj1.bldgNumber == obj2.bldgNumber &&
							   obj1.address1 == obj2.address1 &&
							   obj1.address2 == obj2.address2 &&
							   obj1.city == obj2.city &&
							   obj1.state == obj2.state &&
							   obj1.country == obj2.country &&
							   obj1.postalCd == obj2.postalCd;
					} else {
						return obj1.addressId == obj2.addressId;
					}
				},
				onEditClick : function(obj) {
					var $this = this;
					$("#editDialog").modalEdit({
						fields: addressModalFields,
			            dialogTitle: localeManager["openiam.ui.selfservice.ui.template.edit.address"],
			            onSubmit: function(bean){
			            	obj.address1 = bean.address1;
			            	obj.address2 = bean.address2;
			            	obj.city = bean.city;
			            	obj.state = bean.state;
			            	obj.bldgNumber = bean.bldgNumber;
			            	obj.country = bean.country;
			            	obj.postalCd = bean.postalCd;
			            	obj.metadataTypeId = bean.metadataTypeId;
			            	try {
			            		obj.typeDescription = OPENIAM.ENV.AddressTypeMap[bean.metadataTypeId].name
			            	} catch(e) {
			            		obj.typeDescription = "";
			            	}
			            	obj.isDefault = (bean.isDefault == true || OPENIAM.ENV.addressList.length == 1);
			            	obj.isActive = (bean.isActive == true || OPENIAM.ENV.addressList.length == 1);
			            	$("#editDialog").modalEdit("hide");
			            	if(obj.isDefault === true) {
			            		$this.persistentTable("setPropertyOnAll", "isDefault", false);
			            		obj.isDefault = true;
			            	}
			            	$this.persistentTable("draw");
			            }
	                });
					$("#editDialog").modalEdit("show", obj);
				},
				onCreateClick : function() {
					var $this = this;
					var obj = {};

					$("#editDialog").modalEdit({
						fields: addressModalFields,
			            dialogTitle: localeManager["openiam.ui.selfservice.ui.template.edit.address"],
			            onSubmit: function(bean){
			            	obj.address1 = bean.address1;
			            	obj.address2 = bean.address2;
			            	obj.city = bean.city;
			            	obj.state = bean.state;
			            	obj.country = bean.country;
			            	obj.postalCd = bean.postalCd;
			            	obj.metadataTypeId = bean.metadataTypeId;
			            	try {
			            		obj.typeDescription = OPENIAM.ENV.AddressTypeMap[bean.metadataTypeId].name
			            	} catch(e) {
			            		obj.typeDescription = "";
			            	}
			            	obj.bldgNumber = bean.bldgNumber;
			            	obj.isDefault = (bean.isDefault == true || OPENIAM.ENV.addressList.length == 0);
			            	obj.isActive = (bean.isActive == true || OPENIAM.ENV.addressList.length == 0);
			            	obj.addressId = null;
			            	obj.editable = true;
			            	if(obj.isDefault === true) {
			            		$this.persistentTable("setPropertyOnAll", "isDefault", false);
			            		obj.isDefault = true;
			            	}
			            	$("#editDialog").modalEdit("hide");
			            	$this.persistentTable("addObject", obj);
			            	$this.persistentTable("draw");
			            }
	                });
					$("#editDialog").modalEdit("show", obj);
				},
				onAdditionalBtnClick : function() {
					var orgsId = [];
					var hierarchy = OPENIAM.ENV.OrganizationHierarchy;
					var currentOrgId = OPENIAM.ENV.CurrentOrgId;
					if (hierarchy != null && hierarchy != undefined && hierarchy.length > 0 && $("#organizationsTable").length > 0) {
						orgsId = $("#organizationsTable").organizationHierarchyWrapper("getValues");
					};
					if ((orgsId == null) || (orgsId.length == 0) && (currentOrgId == null)) {
						OPENIAM.Modal.Error(localeManager["openiam.ui.idm.synch.synch_edit.field.organization"]);
					} else {
						var $this = this;
						var obj = {};
						var orgId = orgsId.pop();
						if (orgId == null) {
							orgId = currentOrgId;
						}
						$("#searchLocationContainer").entitlemetnsTable({
							columnHeaders: [
								localeManager["openiam.ui.common.location.address"],
								localeManager["openiam.ui.common.actions"]
							],
							columnsMap: ["displayDescription"],
							ajaxURL: "getLocationsForOrg.html",
							entityUrl: "",
							entityURLIdentifierParamName: "id",
							requestParamIdName: "id",
							requestParamIdValue: orgId,
							pageSize: 10,
							hasEditButton: false,
							emptyResultsText: localeManager["openiam.ui.user.contact.address.empty"],
							onAdd: function (bean) {
								obj.address1 = bean.address1;
								obj.address2 = bean.address2;
								obj.city = bean.city;
								obj.state = bean.state;
								obj.country = bean.country;
								obj.postalCd = bean.postalCd;
								obj.metadataTypeId = "OFFICE_ADDRESS";
								try {
									obj.typeDescription = OPENIAM.ENV.AddressTypeMap[obj.metadataTypeId].name
								} catch(e) {
									obj.typeDescription = "";
								}
								obj.bldgNumber = bean.bldgNum;
								obj.isDefault = (bean.isDefault == true || OPENIAM.ENV.addressList.length == 0);
								obj.isActive = (bean.isActive == true || OPENIAM.ENV.addressList.length == 0);
								obj.addressId = null;
								obj.editable = true;
								if(obj.isDefault === true) {
									$this.persistentTable("setPropertyOnAll", "isDefault", false);
									obj.isDefault = true;
								}
								$this.persistentTable("addObject", obj);
								$this.persistentTable("draw");
								$("#searchLocationContainer").empty();
							}
						})
					}
				}
			});
		}
		
		$("#editProfileForm").submit(function() {
			$this.onSubmit(readyArgs);
			return false;
		});
	},
	onSubmit : function(readyArgs) {
		$.ajax({
			url : readyArgs.postURL,
			data : JSON.stringify(readyArgs.toJSON()),
			type: "POST",
			dataType : "json",
			contentType: "application/json",
			success : function(data, textStatus, jqXHR) {
				if(data.status == 200) {
					OPENIAM.Modal.Success({message : data.successMessage, showInterval : 5000, onIntervalClose : function() {
						if(data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
							window.location.href = data.redirectURL;
						} else {
							window.location.reload(true);
						}
					}});
				} else {
					OPENIAM.Modal.Error({errorList : data.errorList});
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
			}
		});
	}
};