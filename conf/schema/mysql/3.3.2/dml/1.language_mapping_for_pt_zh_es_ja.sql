use openiam;

UPDATE LANGUAGE_MAPPING SET ID='EN_ESORG_LOCATION' WHERE ID='ESORG_LOCATION' AND REFERENCE_ID='ORG_LOCATION' AND LANGUAGE_ID='1';

DELETE FROM LANGUAGE_MAPPING WHERE REFERENCE_ID IN ('a','AD_GROUP','AR_ADD_GRP_BTN','AR_ADD_RES_BTN','AR_ADD_ROLE_BTN','AR_BTN_ROOT',
                                                    'AR_GRP_DEL_BTN','AR_RES_DEL_BTN','AR_ROLE_DEL_BTN','AR_VIEW_RES_BTN','AR_VIEW_ROLE_BTN',
                                                    'AR_VIEW_ROOT','DISTRIBUTION_GROUP','Domain_Local','ENT_USR_ORG_BTN_ROOT','g','GENERAL_GROUP',
                                                    'Global','HIGH_RISK','LOW_RISK','m','ORGANIZATIONS_ADD_BTN','ORGANIZATIONS_DEL_BTN','ORGANIZATIONS_EDT_BTN',
                                                    'ORG_LOCATION','PRIMARY_LOCATION','SECURITY_GROUP','SS_GRP_BULK_OP','SS_SAVE_USER_BTN','SS_USR_ORG',
                                                    'Universal','a','AD_GROUP','AR_ADD_GRP_BTN','AR_ADD_RES_BTN','AR_ADD_ROLE_BTN','AR_BTN_ROOT','AR_GRP_DEL_BTN',
                                                    'AR_RES_DEL_BTN','AR_ROLE_DEL_BTN','AR_VIEW_RES_BTN','AR_VIEW_ROLE_BTN','AR_VIEW_ROOT','DISTRIBUTION_GROUP',
                                                    'Domain_Local','g','GENERAL_GROUP','Global','HIGH_RISK','LOW_RISK','m','ORG_LOCATION','SECURITY_GROUP',
                                                    'SS_GRP_BULK_OP','SS_SAVE_USER_BTN','SS_USR_ORG','Universal','a','AD_GROUP','AR_ADD_GRP_BTN','AR_ADD_RES_BTN',
                                                    'AR_ADD_ROLE_BTN','AR_BTN_ROOT','AR_GRP_DEL_BTN','AR_RES_DEL_BTN','AR_ROLE_DEL_BTN','AR_VIEW_RES_BTN',
                                                    'AR_VIEW_ROLE_BTN','AR_VIEW_ROOT','DISTRIBUTION_GROUP','Domain_Local','g','GENERAL_GROUP','Global',
                                                    'HIGH_RISK','LOW_RISK','m','ORG_LOCATION','SECURITY_GROUP','SS_GRP_BULK_OP','SS_SAVE_USER_BTN',
                                                    'SS_USR_ORG','SYNCUSER_REVIEW','Universal','a','AD_GROUP','AR_ADD_GRP_BTN','AR_ADD_RES_BTN','AR_ADD_ROLE_BTN',
                                                    'AR_BTN_ROOT','AR_GRP_DEL_BTN','AR_RES_DEL_BTN','AR_ROLE_DEL_BTN','AR_VIEW_RES_BTN','AR_VIEW_ROLE_BTN','AR_VIEW_ROOT',
                                                    'DISTRIBUTION_GROUP','Domain_Local','g','GENERAL_GROUP','Global','GROUP_IDENTITY','HIGH_RISK','LOW_RISK','m',
                                                    'ORG_LOCATION','SECURITY_GROUP','SS_GRP_BULK_OP','SS_SAVE_USER_BTN','SS_USR_ORG','SYNCUSER_REVIEW','Universal')
                                                    AND LANGUAGE_ID IN ('11','10','4','9');

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