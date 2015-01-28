package org.openiam.ui.web.util;

import org.apache.commons.lang.time.DateUtils;
import org.openiam.idm.srvc.policy.dto.ITPolicy;
import org.openiam.idm.srvc.policy.dto.ITPolicyApproveType;
import org.openiam.idm.srvc.user.dto.User;

import java.util.Date;

public class UsePolicyHelper {

    public static Boolean getUsePolicyStatus(final ITPolicy itPolicy, final User user) {
        Boolean status = null;
        if (itPolicy != null && itPolicy.isActive()) {
            status = false;
            Date date = user.getDateITPolicyApproved();
            if (date != null && date.after(itPolicy.getCreateDate())) {
                if (itPolicy.getApproveType() == ITPolicyApproveType.ANNUALLY) {
                    Date compareDate = user.getDateITPolicyApproved();
//                    Date compareDate = itPolicy.getCreateDate();
                    status = new Date().before(DateUtils.addYears(compareDate, 1));
                } else if (itPolicy.getApproveType() == ITPolicyApproveType.ONCE) {
                    status = true;
                }
            }
        }
        return status;
    }

}
