package org.openiam.idm.srvc.csv;

import java.util.List;
import java.util.Map;

import org.openiam.idm.srvc.csv.constant.CSVSource;
import org.openiam.idm.srvc.mngsys.dto.AttributeMap;
import org.openiam.idm.srvc.mngsys.dto.ManagedSys;

public interface CSVParser<T> {
	ReconciliationObject<T> toReconciliationObject(T pu,
			List<AttributeMap> attrMap);

	List<ReconciliationObject<T>> getObjects(ManagedSys managedSys,
			List<AttributeMap> attrMapList, CSVSource source) throws Exception;

	void update(ReconciliationObject<T> newUser,
			ManagedSys managedSys, List<AttributeMap> attrMapList,
			CSVSource source) throws Exception;

	void delete(String principal, ManagedSys managedSys,
			List<AttributeMap> attrMapList, CSVSource source) throws Exception;

	void add(ReconciliationObject<T> newObject,
			ManagedSys managedSys, List<AttributeMap> attrMapList,
			CSVSource source) throws Exception;

	Map<String, String> convertToMap(List<AttributeMap> attrMap,
			ReconciliationObject<T> obj);

	public String getFileName(ManagedSys mngSys, CSVSource source);

	String objectToString(List<String> head, Map<String, String> obj);

	String objectToString(List<String> head, List<AttributeMap> attrMapList,
			ReconciliationObject<T> u);

	Map<String, String> matchFields(List<AttributeMap> attrMap,
			ReconciliationObject<T> u,
			ReconciliationObject<T> o);
}
