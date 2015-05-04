use openiam;

UPDATE MANAGED_SYS SET ADD_HNDLR='groovy/example/AddScriptConnector.groovy', MODIFY_HNDLR='groovy/example/ModifyScriptConnector.groovy', DELETE_HNDLR='groovy/example/DeleteScriptConnector.groovy', SEARCH_HNDLR='groovy/example/SearchScriptConnector.groovy', LOOKUP_HNDLR='groovy/example/LookupScriptConnector.groovy', TEST_CONNECTION_HNDLR='groovy/example/TestScriptConnector.groovy' WHERE MANAGED_SYS_ID='104';

