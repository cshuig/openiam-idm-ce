package org.openiam.ui.rest.api.model;

import java.util.List;

import org.openiam.idm.srvc.meta.dto.MetadataElement;
import org.openiam.idm.srvc.meta.dto.MetadataType;

public class FieldSearchResponse {

	private List<MetadataElement> resultList;
    private List<MetadataType> typeList;
    private Integer count;

    public FieldSearchResponse(List<MetadataElement> resultList,List<MetadataType> typeList, Integer count) {
        this.resultList = resultList;
        this.typeList = typeList;
        this.count = count;
    }

    public List<MetadataElement> getResultList() {
        return resultList;
    }

    public void setResultList(List<MetadataElement> resultList) {
        this.resultList = resultList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<MetadataType> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<MetadataType> typeList) {
        this.typeList = typeList;
    }
}
