//*********************************************** 3.3.2 *********************************//
//*********************** dml/1.language_mapping_for_pt_zh_es_ja.sql ***********************//
UPDATE LANGUAGE_MAPPING SET ID='EN_ESORG_LOCATION' WHERE ID='ESORG_LOCATION' AND REFERENCE_ID='ORG_LOCATION' AND LANGUAGE_ID='1';

DELETE FROM LANGUAGE_MAPPING WHERE REFERENCE_ID IN ('a','AD_GROUP','AR_ADD_GRP_BTN','AR_ADD_RES_BTN','AR_ADD_ROLE_BTN','AR_BTN_ROOT','AR_GRP_DEL_BTN','AR_RES_DEL_BTN','AR_ROLE_DEL_BTN','AR_VIEW_RES_BTN','AR_VIEW_ROLE_BTN','AR_VIEW_ROOT','DISTRIBUTION_GROUP','Domain_Local','ENT_USR_ORG_BTN_ROOT','g','GENERAL_GROUP','Global','HIGH_RISK','LOW_RISK','m','ORGANIZATIONS_ADD_BTN','ORGANIZATIONS_DEL_BTN','ORGANIZATIONS_EDT_BTN','ORG_LOCATION','PRIMARY_LOCATION','SECURITY_GROUP','SS_GRP_BULK_OP','SS_SAVE_USER_BTN','SS_USR_ORG','Universal','a','AD_GROUP','AR_ADD_GRP_BTN','AR_ADD_RES_BTN','AR_ADD_ROLE_BTN','AR_BTN_ROOT','AR_GRP_DEL_BTN','AR_RES_DEL_BTN','AR_ROLE_DEL_BTN','AR_VIEW_RES_BTN','AR_VIEW_ROLE_BTN','AR_VIEW_ROOT','DISTRIBUTION_GROUP','Domain_Local','g','GENERAL_GROUP','Global','HIGH_RISK','LOW_RISK','m','ORG_LOCATION','SECURITY_GROUP','SS_GRP_BULK_OP','SS_SAVE_USER_BTN','SS_USR_ORG','Universal','a','AD_GROUP','AR_ADD_GRP_BTN','AR_ADD_RES_BTN','AR_ADD_ROLE_BTN','AR_BTN_ROOT','AR_GRP_DEL_BTN','AR_RES_DEL_BTN','AR_ROLE_DEL_BTN','AR_VIEW_RES_BTN','AR_VIEW_ROLE_BTN','AR_VIEW_ROOT','DISTRIBUTION_GROUP','Domain_Local','g','GENERAL_GROUP','Global','HIGH_RISK','LOW_RISK','m','ORG_LOCATION','SECURITY_GROUP','SS_GRP_BULK_OP','SS_SAVE_USER_BTN','SS_USR_ORG','SYNCUSER_REVIEW','Universal','a','AD_GROUP','AR_ADD_GRP_BTN','AR_ADD_RES_BTN','AR_ADD_ROLE_BTN','AR_BTN_ROOT','AR_GRP_DEL_BTN','AR_RES_DEL_BTN','AR_ROLE_DEL_BTN','AR_VIEW_RES_BTN','AR_VIEW_ROLE_BTN','AR_VIEW_ROOT','DISTRIBUTION_GROUP','Domain_Local','g','GENERAL_GROUP','Global','GROUP_IDENTITY','HIGH_RISK','LOW_RISK','m','ORG_LOCATION','SECURITY_GROUP','SS_GRP_BULK_OP','SS_SAVE_USER_BTN','SS_USR_ORG','SYNCUSER_REVIEW','Universal') AND LANGUAGE_ID IN ('11','10','4','9');

INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTa','11','a','MetadataTypeEntity.displayNameMap','Grupo de Aplicação');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTAD_GROUP','11','AD_GROUP','MetadataTypeEntity.displayNameMap','Tipo de Grupo AD');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTAR_ADD_GRP_BTN','11','AR_ADD_GRP_BTN','ResourceEntity.displayNameMap','Adicionar Grupo');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTAR_ADD_RES_BTN','11','AR_ADD_RES_BTN','ResourceEntity.displayNameMap','Adicionar Recurso');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTAR_ADD_ROLE_BTN','11','AR_ADD_ROLE_BTN','ResourceEntity.displayNameMap','Adicionar Papel');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTAR_BTN_ROOT','11','AR_BTN_ROOT','ResourceEntity.displayNameMap','Botões de raiz para Revisão de Acesso');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTAR_GRP_DEL_BTN','11','AR_GRP_DEL_BTN','ResourceEntity.displayNameMap','Eliminar Grupo');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTAR_RES_DEL_BTN','11','AR_RES_DEL_BTN','ResourceEntity.displayNameMap','Eliminar Recurso');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTAR_ROLE_DEL_BTN','11','AR_ROLE_DEL_BTN','ResourceEntity.displayNameMap','Eliminar Papel');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTAR_VIEW_RES_BTN','11','AR_VIEW_RES_BTN','ResourceEntity.displayNameMap','Recurso');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTAR_VIEW_ROLE_BTN','11','AR_VIEW_ROLE_BTN','ResourceEntity.displayNameMap','Papel');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTAR_VIEW_ROOT','11','AR_VIEW_ROOT','ResourceEntity.displayNameMap','Menu de raiz para Separadores de Revisão de Acesso');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTDISTRIBUTION_GROUP','11','DISTRIBUTION_GROUP','MetadataTypeEntity.displayNameMap','Grupo de Distribuição');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTDomain_Local','11','Domain_Local','MetadataTypeEntity.displayNameMap','Local do Domínio');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTENT_USR_ORG_BTN_ROOT','11','ENT_USR_ORG_BTN_ROOT','ResourceEntity.displayNameMap','Botões de Filiação da Organização do Utilizador');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTg','11','g','MetadataTypeEntity.displayNameMap','Outro Grupo');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTGENERAL_GROUP','11','GENERAL_GROUP','MetadataTypeEntity.displayNameMap','Tipo Geral do Grupo');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTGlobal','11','Global','MetadataTypeEntity.displayNameMap','Global');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTHIGH_RISK','11','HIGH_RISK','MetadataTypeEntity.displayNameMap','Alto');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTLOW_RISK','11','LOW_RISK','MetadataTypeEntity.displayNameMap','Baixo');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTm','11','m','MetadataTypeEntity.displayNameMap','Grupo Admin');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTORGANIZATIONS_ADD_BTN','11','ORGANIZATIONS_ADD_BTN','ResourceEntity.displayNameMap','Adicionar Organização ao Utilizador');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTORGANIZATIONS_DEL_BTN','11','ORGANIZATIONS_DEL_BTN','ResourceEntity.displayNameMap','Remover Organização do Utilizador');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTORGANIZATIONS_EDT_BTN','11','ORGANIZATIONS_EDT_BTN','ResourceEntity.displayNameMap','Editar Organização');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTORG_LOCATION','11','ORG_LOCATION','ResourceEntity.displayNameMap','Localização da Organização');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTPRIMARY_LOCATION','11','PRIMARY_LOCATION','MetadataTypeEntity.displayNameMap','Localização Principal');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTSECURITY_GROUP','11','SECURITY_GROUP','ResourceEntity.displayNameMap','Grupo');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTSS_GRP_BULK_OP','11','SS_GRP_BULK_OP','ResourceEntity.displayNameMap','Operações em Série');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTSS_SAVE_USER_BTN','11','SS_SAVE_USER_BTN','ResourceEntity.displayNameMap','Guardar Botão do Utilizador');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTSS_USR_ORG','11','SS_USR_ORG','ResourceEntity.displayNameMap','Organizações do Utilizador');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('PTUniversal','11','Universal','MetadataTypeEntity.displayNameMap','Universal');

INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHa','10','a','MetadataTypeEntity.displayNameMap','应用程序组');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHAD_GROUP','10','AD_GROUP','MetadataTypeEntity.displayNameMap','AD群组类型');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHAR_ADD_GRP_BTN','10','AR_ADD_GRP_BTN','ResourceEntity.displayNameMap','添加群组');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHAR_ADD_RES_BTN','10','AR_ADD_RES_BTN','ResourceEntity.displayNameMap','添加资源');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHAR_ADD_ROLE_BTN','10','AR_ADD_ROLE_BTN','ResourceEntity.displayNameMap','添加角色');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHAR_BTN_ROOT','10','AR_BTN_ROOT','ResourceEntity.displayNameMap','权限总览的根按钮');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHAR_GRP_DEL_BTN','10','AR_GRP_DEL_BTN','ResourceEntity.displayNameMap','删除群组');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHAR_RES_DEL_BTN','10','AR_RES_DEL_BTN','ResourceEntity.displayNameMap','删除资源');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHAR_ROLE_DEL_BTN','10','AR_ROLE_DEL_BTN','ResourceEntity.displayNameMap','删除角色');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHAR_VIEW_RES_BTN','10','AR_VIEW_RES_BTN','ResourceEntity.displayNameMap','资源');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHAR_VIEW_ROLE_BTN','10','AR_VIEW_ROLE_BTN','ResourceEntity.displayNameMap','角色');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHAR_VIEW_ROOT','10','AR_VIEW_ROOT','ResourceEntity.displayNameMap','权限总览标签页的根菜单');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHDISTRIBUTION_GROUP','10','DISTRIBUTION_GROUP','MetadataTypeEntity.displayNameMap','分布群组');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHDomain_Local','10','Domain_Local','MetadataTypeEntity.displayNameMap','本地域');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHg','10','g','MetadataTypeEntity.displayNameMap','其他群组');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHGENERAL_GROUP','10','GENERAL_GROUP','MetadataTypeEntity.displayNameMap','通用群组类型');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHGlobal','10','Global','MetadataTypeEntity.displayNameMap','全局');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHHIGH_RISK','10','HIGH_RISK','MetadataTypeEntity.displayNameMap','高');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHLOW_RISK','10','LOW_RISK','MetadataTypeEntity.displayNameMap','低');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHm','10','m','MetadataTypeEntity.displayNameMap','管理员群组');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHORG_LOCATION','10','ORG_LOCATION','ResourceEntity.displayNameMap','组织位置');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHSECURITY_GROUP','10','SECURITY_GROUP','ResourceEntity.displayNameMap','群组');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHSS_GRP_BULK_OP','10','SS_GRP_BULK_OP','ResourceEntity.displayNameMap','批量操作');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHSS_SAVE_USER_BTN','10','SS_SAVE_USER_BTN','ResourceEntity.displayNameMap','保存用户按钮');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHSS_USR_ORG','10','SS_USR_ORG','ResourceEntity.displayNameMap','用户组织');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ZHUniversal','10','Universal','MetadataTypeEntity.displayNameMap','通用');

INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESa','4','a','MetadataTypeEntity.displayNameMap','Grupo de Aplicación');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESAD_GROUP','4','AD_GROUP','MetadataTypeEntity.displayNameMap','Grupo de Tipo DA');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESAR_ADD_GRP_BTN','4','AR_ADD_GRP_BTN','ResourceEntity.displayNameMap','Añadir Grupo');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESAR_ADD_RES_BTN','4','AR_ADD_RES_BTN','ResourceEntity.displayNameMap','Añadir Recurso');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESAR_ADD_ROLE_BTN','4','AR_ADD_ROLE_BTN','ResourceEntity.displayNameMap','Añadir Rol');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESAR_BTN_ROOT','4','AR_BTN_ROOT','ResourceEntity.displayNameMap','Botones raíz para Revisión de Acceso');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESAR_GRP_DEL_BTN','4','AR_GRP_DEL_BTN','ResourceEntity.displayNameMap','Borrar Grupo');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESAR_RES_DEL_BTN','4','AR_RES_DEL_BTN','ResourceEntity.displayNameMap','Borrar Recurso');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESAR_ROLE_DEL_BTN','4','AR_ROLE_DEL_BTN','ResourceEntity.displayNameMap','Borrar Rol');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESAR_VIEW_RES_BTN','4','AR_VIEW_RES_BTN','ResourceEntity.displayNameMap','Recurso');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESAR_VIEW_ROLE_BTN','4','AR_VIEW_ROLE_BTN','ResourceEntity.displayNameMap','Rol');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESAR_VIEW_ROOT','4','AR_VIEW_ROOT','ResourceEntity.displayNameMap','Menú raíz para Solapas de Revisión de Acceso');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESDISTRIBUTION_GROUP','4','DISTRIBUTION_GROUP','MetadataTypeEntity.displayNameMap','Grupo de Distribución');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESDomain_Local','4','Domain_Local','MetadataTypeEntity.displayNameMap','Dominio Local');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESg','4','g','MetadataTypeEntity.displayNameMap','Otro Grupo');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESGENERAL_GROUP','4','GENERAL_GROUP','MetadataTypeEntity.displayNameMap','Grupo de Tipo General');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESGlobal','4','Global','MetadataTypeEntity.displayNameMap','Global');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESHIGH_RISK','4','HIGH_RISK','MetadataTypeEntity.displayNameMap','Alto');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESLOW_RISK','4','LOW_RISK','MetadataTypeEntity.displayNameMap','Bajo');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESm','4','m','MetadataTypeEntity.displayNameMap','Grupo Admin');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESORG_LOCATION','4','ORG_LOCATION','ResourceEntity.displayNameMap','Localización de la Organización');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESSECURITY_GROUP','4','SECURITY_GROUP','ResourceEntity.displayNameMap','Grupo');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESSS_GRP_BULK_OP','4','SS_GRP_BULK_OP','ResourceEntity.displayNameMap','Operaciones Masivas');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESSS_SAVE_USER_BTN','4','SS_SAVE_USER_BTN','ResourceEntity.displayNameMap','Botón Guardar Usuario');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESSS_USR_ORG','4','SS_USR_ORG','ResourceEntity.displayNameMap','Organizaciones de Usuario');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESSYNCUSER_REVIEW','4','SYNCUSER_REVIEW','ResourceEntity.displayNameMap','Revisión de Sincronización');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('ESUniversal','4','Universal','MetadataTypeEntity.displayNameMap','Universal');

INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JAa','9','a','MetadataTypeEntity.displayNameMap','アプリケーショングループ');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JAAD_GROUP','9','AD_GROUP','MetadataTypeEntity.displayNameMap','ADグループタイプ');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JAAR_ADD_GRP_BTN','9','AR_ADD_GRP_BTN','ResourceEntity.displayNameMap','グループタイプを追加');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JAAR_ADD_RES_BTN','9','AR_ADD_RES_BTN','ResourceEntity.displayNameMap','リソースを追加');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JAAR_ADD_ROLE_BTN','9','AR_ADD_ROLE_BTN','ResourceEntity.displayNameMap','ロールを追加');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JAAR_BTN_ROOT','9','AR_BTN_ROOT','ResourceEntity.displayNameMap','アクセスレビューのルートボタン');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JAAR_GRP_DEL_BTN','9','AR_GRP_DEL_BTN','ResourceEntity.displayNameMap','グループを削除');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JAAR_RES_DEL_BTN','9','AR_RES_DEL_BTN','ResourceEntity.displayNameMap','リソースを削除');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JAAR_ROLE_DEL_BTN','9','AR_ROLE_DEL_BTN','ResourceEntity.displayNameMap','ロールを削除');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JAAR_VIEW_RES_BTN','9','AR_VIEW_RES_BTN','ResourceEntity.displayNameMap','リソース');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JAAR_VIEW_ROLE_BTN','9','AR_VIEW_ROLE_BTN','ResourceEntity.displayNameMap','ロール');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JAAR_VIEW_ROOT','9','AR_VIEW_ROOT','ResourceEntity.displayNameMap','アクセスレビュータブのルートメニュー');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JADISTRIBUTION_GROUP','9','DISTRIBUTION_GROUP','MetadataTypeEntity.displayNameMap','配布グループ');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JADomain_Local','9','Domain_Local','MetadataTypeEntity.displayNameMap','ドメインローカル');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JAg','9','g','MetadataTypeEntity.displayNameMap','その他のグループ');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JAGENERAL_GROUP','9','GENERAL_GROUP','MetadataTypeEntity.displayNameMap','一般的なグループタイプ');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JAGlobal','9','Global','MetadataTypeEntity.displayNameMap','グローバル');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JAGROUP_IDENTITY','9','GROUP_IDENTITY','ResourceEntity.displayNameMap','アイデンティティ');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JAHIGH_RISK','9','HIGH_RISK','MetadataTypeEntity.displayNameMap','高い');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JALOW_RISK','9','LOW_RISK','MetadataTypeEntity.displayNameMap','低い');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JAm','9','m','MetadataTypeEntity.displayNameMap','管理グループ');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JAORG_LOCATION','9','ORG_LOCATION','ResourceEntity.displayNameMap','組織のロケーション');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JASECURITY_GROUP','9','SECURITY_GROUP','MetadataTypeEntity.displayNameMap','セキュリティグループ');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JASS_GRP_BULK_OP','9','SS_GRP_BULK_OP','ResourceEntity.displayNameMap','一括操作');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JASS_SAVE_USER_BTN','9','SS_SAVE_USER_BTN','ResourceEntity.displayNameMap','ユーザー保存ボタン');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JASS_USR_ORG','9','SS_USR_ORG','ResourceEntity.displayNameMap','ユーザー組織');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JASYNCUSER_REVIEW','9','SYNCUSER_REVIEW','ResourceEntity.displayNameMap','同期のレビュー');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('JAUniversal','9','Universal','MetadataTypeEntity.displayNameMap','ユニバーサル');


//*********************** dml/2.language_mapping_for_ru.sql ***********************//
DELETE FROM LANGUAGE_MAPPING WHERE REFERENCE_ID='a' AND LANGUAGE_ID='8';
DELETE FROM LANGUAGE_MAPPING WHERE REFERENCE_ID='AD_GROUP' AND LANGUAGE_ID='8';
DELETE FROM LANGUAGE_MAPPING WHERE REFERENCE_ID='DISTRIBUTION_GROUP' AND LANGUAGE_ID='8';
DELETE FROM LANGUAGE_MAPPING WHERE REFERENCE_ID='Domain_Local' AND LANGUAGE_ID='8';
DELETE FROM LANGUAGE_MAPPING WHERE REFERENCE_ID='g' AND LANGUAGE_ID='8';
DELETE FROM LANGUAGE_MAPPING WHERE REFERENCE_ID='GENERAL_GROUP' AND LANGUAGE_ID='8';
DELETE FROM LANGUAGE_MAPPING WHERE REFERENCE_ID='Global' AND LANGUAGE_ID='8';
DELETE FROM LANGUAGE_MAPPING WHERE REFERENCE_ID='GROUP_IDENTITY' AND LANGUAGE_ID='8';
DELETE FROM LANGUAGE_MAPPING WHERE REFERENCE_ID='HIGH_RISK' AND LANGUAGE_ID='8';
DELETE FROM LANGUAGE_MAPPING WHERE REFERENCE_ID='LOW_RISK' AND LANGUAGE_ID='8';
DELETE FROM LANGUAGE_MAPPING WHERE REFERENCE_ID='m' AND LANGUAGE_ID='8';
DELETE FROM LANGUAGE_MAPPING WHERE REFERENCE_ID='SECURITY_GROUP' AND LANGUAGE_ID='8';
DELETE FROM LANGUAGE_MAPPING WHERE REFERENCE_ID='Universal' AND LANGUAGE_ID='8';

INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('RUa','8','a','MetadataTypeEntity.displayNameMap','Группа приложений');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('RUAD_GROUP','8','AD_GROUP','MetadataTypeEntity.displayNameMap','Тип группы AD');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('RUDISTRIBUTION_GROUP','8','DISTRIBUTION_GROUP','MetadataTypeEntity.displayNameMap','Группа распространения');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('RUDomain_Local','8','Domain_Local','MetadataTypeEntity.displayNameMap','Локальный домен');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('RUg','8','g','MetadataTypeEntity.displayNameMap','Другая группа');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('RUGENERAL_GROUP','8','GENERAL_GROUP','MetadataTypeEntity.displayNameMap','Тип главной группы');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('RUGlobal','8','Global','MetadataTypeEntity.displayNameMap','Глобальный');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('RUGROUP_IDENTITY','8','GROUP_IDENTITY','ResourceEntity.displayNameMap','Идентификатор');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('RUHIGH_RISK','8','HIGH_RISK','MetadataTypeEntity.displayNameMap','Высокий');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('RULOW_RISK','8','LOW_RISK','MetadataTypeEntity.displayNameMap','Низкий');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('RUm','8','m','MetadataTypeEntity.displayNameMap','Группа Администратора');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('RUSECURITY_GROUP','8','SECURITY_GROUP','MetadataTypeEntity.displayNameMap','Группа безопасности');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('RUUniversal','8','Universal','MetadataTypeEntity.displayNameMap','Универсальный');


//*********************** dml/3.add_report_param.sql ***********************//
DECLARE
 tCount NUMBER := 0;
BEGIN
  SELECT count(*) INTO tCount FROM RES WHERE RESOURCE_ID = 'USER_ACCESS_REPORT_109';
  IF (tCount > 0) THEN
    SELECT count(*) INTO tCount FROM REPORT_CRITERIA_PARAMETER WHERE RCP_ID = '1098';
    IF (tCount < 1) THEN
      UPDATE REPORT_CRITERIA_PARAMETER SET DISPLAY_ORDER = DISPLAY_ORDER * 10 WHERE REPORT_INFO_ID = '109' AND DISPLAY_ORDER < 10;
      INSERT INTO REPORT_CRITERIA_PARAMETER (RCP_ID, REPORT_INFO_ID, PARAM_NAME, PARAM_VALUE, RCPT_ID, PARAM_META_TYPE_ID, IS_MULTIPLE, IS_REQUIRED, CAPTION, DISPLAY_ORDER, REQUEST_PARAMS) 
             VALUES ('1098', '109', 'GROUP_ID', NULL, '1', 'GROUP', 'N', 'N', 'Group', 25, NULL);
    END IF;
  END IF;
END;
/


//*********************** dml/4.add_group_report.sql ***********************//
DECLARE
 tCount NUMBER := 0;
BEGIN
  SELECT count(*) INTO tCount FROM REPORT_PARAMETER_TYPE WHERE RCPT_ID = 4;
  IF (tCount < 1) THEN
    INSERT INTO REPORT_PARAMETER_TYPE (RCPT_ID,TYPE_NAME,TYPE_DESCRIPTION) VALUES (4,'BOOLEAN','Boolean');
  END IF;
  
  SELECT count(*) INTO tCount FROM RES WHERE RESOURCE_ID = 'GROUP_REPORT_111';
  IF (tCount < 1) THEN
    INSERT INTO RES (RESOURCE_ID, RESOURCE_TYPE_ID, DESCRIPTION, NAME, DISPLAY_ORDER, URL, MIN_AUTH_LEVEL, IS_PUBLIC,
            ADMIN_RESOURCE_ID, RISK, TYPE_ID, COORELATED_NAME) VALUES
        ('GROUP_REPORT_111', 'REPORT', NULL, 'GROUP_REPORT_111', NULL, NULL, NULL, 'Y', NULL, NULL, NULL, 'GROUP_REPORT');
  END IF;
  
  SELECT count(*) INTO tCount FROM REPORT_INFO WHERE REPORT_INFO_ID = '111';
  IF (tCount < 1) THEN
    INSERT INTO REPORT_INFO (REPORT_INFO_ID, REPORT_NAME, DATASOURCE_FILE_PATH, REPORT_FILE_PATH, BUILT_IN, RESOURCE_ID) VALUES
        ('111', 'GROUP_REPORT', 'GroupReport.groovy', 'GroupReport.rptdesign', 'Y', 'GROUP_REPORT_111');
  END IF;
  
  DELETE FROM REPORT_CRITERIA_PARAMETER WHERE REPORT_INFO_ID = '111';
  INSERT INTO REPORT_CRITERIA_PARAMETER (RCP_ID, REPORT_INFO_ID, PARAM_NAME, PARAM_VALUE, RCPT_ID, PARAM_META_TYPE_ID, IS_MULTIPLE, IS_REQUIRED, CAPTION, DISPLAY_ORDER, REQUEST_PARAMS) VALUES
      ('1111', '111', 'GROUP_ID', NULL, '1', 'GROUP', 'Y', 'N', 'Group', 1, NULL);
  INSERT INTO REPORT_CRITERIA_PARAMETER (RCP_ID, REPORT_INFO_ID, PARAM_NAME, PARAM_VALUE, RCPT_ID, PARAM_META_TYPE_ID, IS_MULTIPLE, IS_REQUIRED, CAPTION, DISPLAY_ORDER, REQUEST_PARAMS) VALUES
      ('1112', '111', 'ORG_ID', NULL, '1', 'ORGANIZATION', 'N', 'N', 'Organization', 2, NULL);
  INSERT INTO REPORT_CRITERIA_PARAMETER (RCP_ID, REPORT_INFO_ID, PARAM_NAME, PARAM_VALUE, RCPT_ID, PARAM_META_TYPE_ID, IS_MULTIPLE, IS_REQUIRED, CAPTION, DISPLAY_ORDER, REQUEST_PARAMS) VALUES
      ('1113', '111', 'RISK', NULL, '1', 'RISK', 'N', 'N', 'Risk', 3, NULL);
  INSERT INTO REPORT_CRITERIA_PARAMETER (RCP_ID, REPORT_INFO_ID, PARAM_NAME, PARAM_VALUE, RCPT_ID, PARAM_META_TYPE_ID, IS_MULTIPLE, IS_REQUIRED, CAPTION, DISPLAY_ORDER, REQUEST_PARAMS) VALUES      
      ('1114', '111', 'MANAGED_SYS_ID', NULL, '1', 'MANAGED_SYSTEM', 'N', 'N', 'Managed System', 4, NULL);
  INSERT INTO REPORT_CRITERIA_PARAMETER (RCP_ID, REPORT_INFO_ID, PARAM_NAME, PARAM_VALUE, RCPT_ID, PARAM_META_TYPE_ID, IS_MULTIPLE, IS_REQUIRED, CAPTION, DISPLAY_ORDER, REQUEST_PARAMS) VALUES
      ('1115', '111', 'USER_ID', NULL, '1', 'USER', 'N', 'N', 'Memeber', 5, NULL);
  INSERT INTO REPORT_CRITERIA_PARAMETER (RCP_ID, REPORT_INFO_ID, PARAM_NAME, PARAM_VALUE, RCPT_ID, PARAM_META_TYPE_ID, IS_MULTIPLE, IS_REQUIRED, CAPTION, DISPLAY_ORDER, REQUEST_PARAMS) VALUES
      ('1116', '111', 'DETAILS', NULL, '4', NULL, 'N', 'N', 'Show details', 6, NULL);
  INSERT INTO REPORT_CRITERIA_PARAMETER (RCP_ID, REPORT_INFO_ID, PARAM_NAME, PARAM_VALUE, RCPT_ID, PARAM_META_TYPE_ID, IS_MULTIPLE, IS_REQUIRED, CAPTION, DISPLAY_ORDER, REQUEST_PARAMS) VALUES
      ('1117', '111', 'MEMBERS', NULL, '4', NULL, 'N', 'N', 'Show member users', 7, NULL);

END;
/

//*********************** ddl/4.4096_to_4000.sql  ***********************//
//*********************** FIX CLOB to VARCHAR2(4000) ***********************//
//*********************** SYNCH_REVIEW_RECORD_VALUE ************************//
ALTER TABLE SYNCH_REVIEW_RECORD_VALUE ADD VALUE_TMP VARCHAR2(4000);

create or replace function BLOOB_TO_VARCHAR(B BLOB)
return clob is
  c clob;
  n number;
begin
  if (b is null) then
    return null;
  end if;
  if (length(b)=0) then
    return empty_clob();
  end if;
  dbms_lob.createtemporary(c,true);
  n:=1;
  while (n+32767<=length(b)) loop
    dbms_lob.writeappend(c,32767,utl_raw.cast_to_varchar2(dbms_lob.substr(b,32767,n)));
    n:=n+32767;
  end loop;
  dbms_lob.writeappend(c,length(b)-n+1,utl_raw.cast_to_varchar2(dbms_lob.substr(b,length(b)-n+1,n)));
  return c;
end;
/

DECLARE
  v_v varchar2(4000);
  c clob;
  n number;
  CURSOR curl is SELECT SYNCH_REVIEW_RECORD_VALUE_ID, VALUE FROM SYNCH_REVIEW_RECORD_VALUE WHERE VALUE IS NOT NULL;
  BEGIN
  FOR get_cur IN curl LOOP
    v_v := BLOOB_TO_VARCHAR(get_cur.VALUE);
    UPDATE SYNCH_REVIEW_RECORD_VALUE SET VALUE_TMP=v_v WHERE SYNCH_REVIEW_RECORD_VALUE_ID=get_cur.SYNCH_REVIEW_RECORD_VALUE_ID;
END LOOP;
END;
/

DROP FUNCTION BLOOB_TO_VARCHAR;
ALTER TABLE SYNCH_REVIEW_RECORD_VALUE DROP COLUMN VALUE;
ALTER TABLE SYNCH_REVIEW_RECORD_VALUE RENAME COLUMN VALUE_TMP TO VALUE; 


//*********************** REQUEST_ATTRIBUTE ************************//
ALTER TABLE REQUEST_ATTRIBUTE ADD VALUE_TMP VARCHAR2(4000);

DECLARE
 v_v varchar2(4000);
 CURSOR curl is SELECT REQUEST_ATTR_ID, VALUE FROM REQUEST_ATTRIBUTE WHERE VALUE IS NOT NULL;
BEGIN
  FOR get_cur IN curl LOOP
    v_v := get_cur.VALUE;
    UPDATE REQUEST_ATTRIBUTE SET VALUE_TMP=v_v WHERE REQUEST_ATTR_ID=get_cur.REQUEST_ATTR_ID;
END LOOP;
END;
/

ALTER TABLE REQUEST_ATTRIBUTE DROP COLUMN VALUE;
ALTER TABLE REQUEST_ATTRIBUTE RENAME COLUMN VALUE_TMP TO VALUE; 


//*********************** METADATA_ELEMENT ************************//
ALTER TABLE METADATA_ELEMENT ADD VALUE_TMP VARCHAR2(4000);

DECLARE
 v_v varchar2(4000);
 CURSOR curl is SELECT METADATA_ID, STATIC_DEFAULT_VALUE FROM METADATA_ELEMENT WHERE STATIC_DEFAULT_VALUE IS NOT NULL;
BEGIN
  FOR get_cur IN curl LOOP
    v_v := get_cur.STATIC_DEFAULT_VALUE;
    UPDATE METADATA_ELEMENT SET VALUE_TMP=v_v WHERE METADATA_ID=get_cur.METADATA_ID;
END LOOP;
END;
/

ALTER TABLE METADATA_ELEMENT DROP COLUMN STATIC_DEFAULT_VALUE;
ALTER TABLE METADATA_ELEMENT RENAME COLUMN VALUE_TMP TO STATIC_DEFAULT_VALUE; 

//*********************** LOGIN_ATTRIBUTE ************************//
ALTER TABLE LOGIN_ATTRIBUTE ADD VALUE_TMP VARCHAR2(4000);

DECLARE
 v_v varchar2(4000);
 CURSOR curl is SELECT LOGIN_ATTR_ID, VALUE FROM LOGIN_ATTRIBUTE WHERE VALUE IS NOT NULL;
BEGIN
  FOR get_cur IN curl LOOP
    v_v := get_cur.VALUE;
    UPDATE LOGIN_ATTRIBUTE SET VALUE_TMP=v_v WHERE LOGIN_ATTR_ID=get_cur.LOGIN_ATTR_ID;
END LOOP;
END;
/

ALTER TABLE LOGIN_ATTRIBUTE DROP COLUMN VALUE;
ALTER TABLE LOGIN_ATTRIBUTE RENAME COLUMN VALUE_TMP TO VALUE; 

//*********************** URI_PATTERN_META_VALUE ************************//
ALTER TABLE URI_PATTERN_META_VALUE ADD VALUE_TMP VARCHAR2(4000);

DECLARE
 v_v varchar2(4000);
 CURSOR curl is SELECT URI_PATTERN_META_VALUE_ID, STATIC_VALUE FROM URI_PATTERN_META_VALUE WHERE STATIC_VALUE IS NOT NULL;
BEGIN
  FOR get_cur IN curl LOOP
    v_v := get_cur.STATIC_VALUE;
    UPDATE URI_PATTERN_META_VALUE SET VALUE_TMP=v_v WHERE URI_PATTERN_META_VALUE_ID=get_cur.URI_PATTERN_META_VALUE_ID;
END LOOP;
END;
/

ALTER TABLE URI_PATTERN_META_VALUE DROP COLUMN STATIC_VALUE;
ALTER TABLE URI_PATTERN_META_VALUE RENAME COLUMN VALUE_TMP TO STATIC_VALUE; 


//*********************** AUTH_PROVIDER_ATTRIBUTE ************************//
ALTER TABLE AUTH_PROVIDER_ATTRIBUTE ADD VALUE_TMP VARCHAR2(4000);

DECLARE
 v_v varchar2(4000);
 CURSOR curl is SELECT PROVIDER_ATTRIBUTE_ID, VALUE FROM AUTH_PROVIDER_ATTRIBUTE WHERE VALUE IS NOT NULL;
BEGIN
  FOR get_cur IN curl LOOP
    v_v := get_cur.VALUE;
    UPDATE AUTH_PROVIDER_ATTRIBUTE SET VALUE_TMP=v_v WHERE PROVIDER_ATTRIBUTE_ID=get_cur.PROVIDER_ATTRIBUTE_ID;
END LOOP;
END;
/

ALTER TABLE AUTH_PROVIDER_ATTRIBUTE DROP COLUMN VALUE;
ALTER TABLE AUTH_PROVIDER_ATTRIBUTE RENAME COLUMN VALUE_TMP TO VALUE; 


//*********************** AUTH_LEVEL_ATTRIBUTE ************************//
ALTER TABLE AUTH_LEVEL_ATTRIBUTE ADD VALUE_TMP VARCHAR2(4000);

DECLARE
 v_v varchar2(4000);
 CURSOR curl is SELECT AUTH_LEVEL_ATTRIBUTE_ID, VALUE_AS_STRING FROM AUTH_LEVEL_ATTRIBUTE WHERE VALUE_AS_STRING IS NOT NULL;
BEGIN
  FOR get_cur IN curl LOOP
    v_v := get_cur.VALUE_AS_STRING;
    UPDATE AUTH_LEVEL_ATTRIBUTE SET VALUE_TMP=v_v WHERE AUTH_LEVEL_ATTRIBUTE_ID=get_cur.AUTH_LEVEL_ATTRIBUTE_ID;
END LOOP;
END;
/

ALTER TABLE AUTH_LEVEL_ATTRIBUTE DROP COLUMN VALUE_AS_STRING;
ALTER TABLE AUTH_LEVEL_ATTRIBUTE RENAME COLUMN VALUE_TMP TO VALUE_AS_STRING; 


//*********************** AUTH_ATTRIBUTE ************************//
ALTER TABLE AUTH_ATTRIBUTE ADD VALUE_TMP VARCHAR2(4000);

DECLARE
 v_v varchar2(4000);
 CURSOR curl is SELECT AUTH_ATTRIBUTE_ID, DEFAULT_VALUE FROM AUTH_ATTRIBUTE WHERE DEFAULT_VALUE IS NOT NULL;
BEGIN
  FOR get_cur IN curl LOOP
    v_v := get_cur.DEFAULT_VALUE;
    UPDATE AUTH_ATTRIBUTE SET VALUE_TMP=v_v WHERE AUTH_ATTRIBUTE_ID=get_cur.AUTH_ATTRIBUTE_ID;
END LOOP;
END;
/

ALTER TABLE AUTH_ATTRIBUTE DROP COLUMN DEFAULT_VALUE;
ALTER TABLE AUTH_ATTRIBUTE RENAME COLUMN VALUE_TMP TO DEFAULT_VALUE; 



//*********************** ROLE_ATTRIBUTE ************************//
  ALTER TABLE ROLE_ATTRIBUTE ADD VALUE_TMP VARCHAR2(4000);
  
  DECLARE
   v_v varchar2(4000);
   CURSOR curl is SELECT ROLE_ATTR_ID, ATTR_VALUE FROM ROLE_ATTRIBUTE WHERE ATTR_VALUE IS NOT NULL;
  BEGIN
    FOR get_cur IN curl LOOP
      v_v := get_cur.ATTR_VALUE;
      UPDATE ROLE_ATTRIBUTE SET VALUE_TMP=v_v WHERE ROLE_ATTR_ID=get_cur.ROLE_ATTR_ID;
  END LOOP;
  END;
  /
  
  ALTER TABLE ROLE_ATTRIBUTE DROP COLUMN ATTR_VALUE;
  ALTER TABLE ROLE_ATTRIBUTE RENAME COLUMN VALUE_TMP TO ATTR_VALUE; 


//*********************** RESOURCE_PROP ************************//
  ALTER TABLE RESOURCE_PROP ADD VALUE_TMP VARCHAR2(4000);
  
  DECLARE
   v_v varchar2(4000);
   CURSOR curl is SELECT RESOURCE_PROP_ID, ATTR_VALUE FROM RESOURCE_PROP WHERE ATTR_VALUE IS NOT NULL;
  BEGIN
    FOR get_cur IN curl LOOP
      v_v := get_cur.ATTR_VALUE;
      UPDATE RESOURCE_PROP SET VALUE_TMP=v_v WHERE RESOURCE_PROP_ID=get_cur.RESOURCE_PROP_ID;
  END LOOP;
  END;
  /
  
  ALTER TABLE RESOURCE_PROP DROP COLUMN ATTR_VALUE;
  ALTER TABLE RESOURCE_PROP RENAME COLUMN VALUE_TMP TO ATTR_VALUE; 
  
  
//*********************** COMPANY_ATTRIBUTE ************************//
  ALTER TABLE COMPANY_ATTRIBUTE ADD VALUE_TMP VARCHAR2(4000);
  
  DECLARE
   v_v varchar2(4000);
   CURSOR curl is SELECT COMPANY_ATTR_ID, ATTR_VALUE FROM COMPANY_ATTRIBUTE WHERE ATTR_VALUE IS NOT NULL;
  BEGIN
    FOR get_cur IN curl LOOP
      v_v := get_cur.ATTR_VALUE;
      UPDATE COMPANY_ATTRIBUTE SET VALUE_TMP=v_v WHERE COMPANY_ATTR_ID=get_cur.COMPANY_ATTR_ID;
  END LOOP;
  END;
  /
  
  ALTER TABLE COMPANY_ATTRIBUTE DROP COLUMN ATTR_VALUE;
  ALTER TABLE COMPANY_ATTRIBUTE RENAME COLUMN VALUE_TMP TO ATTR_VALUE; 
  
  
//*********************** GRP_ATTRIBUTES ************************//
 ALTER TABLE GRP_ATTRIBUTES ADD VALUE_TMP VARCHAR2(4000);
  
  DECLARE
   v_v varchar2(4000);
   CURSOR curl is SELECT ID, ATTR_VALUE FROM GRP_ATTRIBUTES WHERE ATTR_VALUE IS NOT NULL;
  BEGIN
    FOR get_cur IN curl LOOP
      v_v := get_cur.ATTR_VALUE;
      UPDATE GRP_ATTRIBUTES SET VALUE_TMP=v_v WHERE ID=get_cur.ID;
  END LOOP;
  END;
  /
  
  ALTER TABLE GRP_ATTRIBUTES DROP COLUMN ATTR_VALUE;
  ALTER TABLE GRP_ATTRIBUTES RENAME COLUMN VALUE_TMP TO ATTR_VALUE; 
  