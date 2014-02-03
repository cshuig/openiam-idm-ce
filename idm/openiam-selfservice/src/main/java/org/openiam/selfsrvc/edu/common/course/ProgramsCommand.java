package org.openiam.selfsrvc.edu.common.course;


import org.openiam.idm.srvc.edu.course.dto.Program;

import java.io.Serializable;
import java.util.List;

/**
 * Command object for the SelectCourses
 * @author suneet
 *
 */
public class ProgramsCommand implements Serializable {
	 

	private static final long serialVersionUID = -667408382835178231L;

    List<Program> programList;

    public List<Program> getProgramList() {
        return programList;
    }

    public void setProgramList(List<Program> programList) {
        this.programList = programList;
    }
}
