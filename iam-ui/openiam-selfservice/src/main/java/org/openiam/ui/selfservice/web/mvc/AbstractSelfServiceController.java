package org.openiam.ui.selfservice.web.mvc;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.openiam.idm.searchbeans.MetadataTypeSearchBean;
import org.openiam.idm.srvc.meta.dto.MetadataType;
import org.openiam.idm.srvc.meta.ws.MetadataWebService;
import org.openiam.idm.srvc.user.dto.ProfilePicture;
import org.openiam.idm.srvc.user.ws.UserDataWebService;
import org.openiam.ui.dozer.DozerBeanMapper;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.openiam.ui.security.OpenIAMCookieProvider;
import org.openiam.ui.web.mvc.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class AbstractSelfServiceController extends AbstractController {

    @Resource(name = "userServiceClient")
    protected UserDataWebService userDataWebService;

    protected String getProfilePicture(String userId) {
        String result = null;
        try {
            ProfilePicture profilePic = userDataWebService.getProfilePictureByUserId(userId, userId);
            if (profilePic != null && profilePic.getPicture() != null) {
                ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(profilePic.getPicture()));
                Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
                if (iter.hasNext()) {
                    ImageReader ir = iter.next();
                    String format = ir.getFormatName();
                    String imgSrc = profilePic.getUser().getId() + "." + format.toLowerCase().replace("jpeg", "jpg");
                    result = imgSrc;
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
        return result;
    }


}
