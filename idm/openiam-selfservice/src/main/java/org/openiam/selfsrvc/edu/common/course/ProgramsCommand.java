package org.openiam.selfsrvc.edu.common.course;

import org.openiam.idm.srvc.edu.course.dto.CourseSearch;

import java.io.Serializable;

/**
 * Command object for the SelectCourses
 * @author suneet
 *
 */
public class ProgramsCommand implements Serializable {
	 

	private static final long serialVersionUID = -667408382835178231L;

    CourseSearch search = new CourseSearch();

    public CourseSearch getSearch() {
        return search;
    }

    public void setSearch(CourseSearch search) {
        this.search = search;
    }
}
