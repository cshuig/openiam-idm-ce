package org.openiam.ui.webconsole.idm.web.mvc.provisioning.synchronization;

import org.openiam.idm.srvc.synch.dto.SynchReview;
import org.openiam.idm.srvc.synch.dto.SynchReviewRecord;
import org.openiam.idm.srvc.synch.dto.SynchReviewRequest;
import org.openiam.ui.web.model.PaginationCommand;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

public class SynchReviewCommand extends PaginationCommand {

    private String synchConfigId;
    private String synchReviewId;
    private Date createTime;
    private Date modifyTime;
    private Date execTime;
    private boolean sourceRejected;
    private boolean skipSourceValid;
    private boolean skipRecordValid;
    private List<SynchReviewRecord> reviewRecords;

    SynchReviewCommand() {}

    SynchReviewCommand(SynchReview synchReview) {
        BeanUtils.copyProperties(synchReview, this);
    }

    public SynchReviewRequest generateRequest() {
        SynchReviewRequest req = new SynchReviewRequest();
        BeanUtils.copyProperties(this, req);
        return req;
    }

    public String getSynchConfigId() {
        return synchConfigId;
    }

    public void setSynchConfigId(String synchConfigId) {
        this.synchConfigId = synchConfigId;
    }

    public String getSynchReviewId() {
        return synchReviewId;
    }

    public void setSynchReviewId(String synchReviewId) {
        this.synchReviewId = synchReviewId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getExecTime() {
        return execTime;
    }

    public void setExecTime(Date execTime) {
        this.execTime = execTime;
    }

    public boolean isSourceRejected() {
        return sourceRejected;
    }

    public void setSourceRejected(boolean sourceRejected) {
        this.sourceRejected = sourceRejected;
    }

    public boolean isSkipSourceValid() {
        return skipSourceValid;
    }

    public void setSkipSourceValid(boolean skipSourceValid) {
        this.skipSourceValid = skipSourceValid;
    }

    public boolean isSkipRecordValid() {
        return skipRecordValid;
    }

    public void setSkipRecordValid(boolean skipRecordValid) {
        this.skipRecordValid = skipRecordValid;
    }

    public List<SynchReviewRecord> getReviewRecords() {
        return reviewRecords;
    }

    public void setReviewRecords(List<SynchReviewRecord> reviewRecords) {
        this.reviewRecords = reviewRecords;
    }
}
