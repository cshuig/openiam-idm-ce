package org.openiam.dozer.converter;

import org.openiam.idm.srvc.edu.course.domain.CourseEntity;
import org.openiam.idm.srvc.edu.course.dto.Course;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("courseDozerMapper")
public class CourseDozerConverter extends AbstractDozerEntityConverter<Course, CourseEntity> {

    @Override
	public CourseEntity convertEntity(CourseEntity entity, boolean isDeep) {
		return convert(entity, isDeep, CourseEntity.class);
	}

	@Override
	public Course convertDTO(Course entity, boolean isDeep) {
		return convert(entity, isDeep, Course.class);
	}

	@Override
	public CourseEntity convertToEntity(Course entity, boolean isDeep) {
		return convertToCrossEntity(entity, isDeep, CourseEntity.class);
	}

	@Override
	public Course convertToDTO(CourseEntity entity, boolean isDeep) {
		return convertToCrossEntity(entity, isDeep, Course.class);
	}

	@Override
	public List<CourseEntity> convertToEntityList(List<Course> list, boolean isDeep) {
		return convertListToCrossEntity(list, isDeep, CourseEntity.class);
	}

	@Override
	public List<Course> convertToDTOList(List<CourseEntity> list, boolean isDeep) {
		return convertListToCrossEntity(list, isDeep, Course.class);
	}
}
