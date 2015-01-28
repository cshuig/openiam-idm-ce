package org.openiam.ui.webconsole.web.mvc;

import org.openiam.ui.webconsole.web.model.SysBean;
import org.openiam.util.SystemInfoWebService;
import org.openiam.util.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SystemInfoController extends AbstractWebconsoleController {

    @Autowired
    SystemInfoWebService systemInfoWebService;

    @Value("${org.openiam.ui.about.root.id}")
    private String rootMenuName;

    @RequestMapping(value="/about", method=RequestMethod.GET)
    public String info(final HttpServletRequest request, @ModelAttribute("sys") SysBean sys) {
        buildSysBean(sys, request);
        setMenuTree(request, rootMenuName);
        return "system/about";
    }

    private void buildSysBean(SysBean sys, HttpServletRequest request) {

        sys.setDevelopmentMode(systemInfoWebService.isDevelopmentMode());

        sys.setEsbBuildInfo(String.format("Version: %s, Build: %s, Last commit %s",
                systemInfoWebService.getWarManifestInfo("Project-Version"),
                systemInfoWebService.getWarManifestInfo("Built-Timestamp"),
                systemInfoWebService.getWarManifestInfo("Last-Commit-Timestamp")));
        sys.setUiBuildInfo(String.format("Version: %s, Build: %s, Last commit %s",
                SystemUtils.getWarManifestInfo(request.getSession().getServletContext(), "Project-Version"),
                SystemUtils.getWarManifestInfo(request.getSession().getServletContext(), "Built-Timestamp"),
                SystemUtils.getWarManifestInfo(request.getSession().getServletContext(), "Last-Commit-Timestamp")));

        sys.setEsbJavaInfo(String.format("%s, %s, Uptime: %s",
                systemInfoWebService.getJavaInfo("name"),
                systemInfoWebService.getJavaInfo("version"),
                systemInfoWebService.getJavaInfo("upTime")));
        sys.setUiJavaInfo(String.format("%s, %s, Uptime: %s",
                SystemUtils.getJavaInfo("name"),
                SystemUtils.getJavaInfo("version"),
                SystemUtils.getJavaInfo("upTime")));

        sys.setEsbJavaMemInfo(String.format("Total: %s, Free: %s, Used: %s",
                systemInfoWebService.getMemInfo("total"),
                systemInfoWebService.getMemInfo("free"),
                systemInfoWebService.getMemInfo("used")));
        sys.setUiJavaMemInfo(String.format("Total: %s, Free: %s, Used: %s",
                SystemUtils.getMemInfo("total"),
                SystemUtils.getMemInfo("free"),
                SystemUtils.getMemInfo("used")));

        sys.setEsbOsInfo(String.format("%s, v.%s, [%s]",
                systemInfoWebService.getOsInfo("name"),
                systemInfoWebService.getOsInfo("version"),
                systemInfoWebService.getOsInfo("arch")));
        sys.setUiOsInfo(String.format("%s, v.%s, [%s]",
                SystemUtils.getOsInfo("name"),
                SystemUtils.getOsInfo("version"),
                SystemUtils.getOsInfo("arch")));

    }
}
