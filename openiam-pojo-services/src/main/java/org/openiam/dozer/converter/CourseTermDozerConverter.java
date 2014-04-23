package org.openiam.dozer.converter;

import org.openiam.idm.srvc.edu.course.domain.CourseTermEntity;
import org.openiam.idm.srvc.edu.course.dto.CourseTerm;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("courseTermDozerMapper")
public class CourseTermDozerConverter extends AbstractDozerEntityConverter<CourseTerm, CourseTermEntity> {
    @Override
	public CourseTermEntity convertEntity(CourseTermEntity entity, boolean isDeep) {
		return convert(entity, isDeep, CourseTermEntity.class);
	}

	@Override
	public CourseTerm convertDTO(CourseTerm entity, boolean isDeep) {
		return convert(entity, isDeep, CourseTerm.class);
	}

	@Override
	public CourseTermEntity convertToEntity(CourseTerm entity, boolean isDeep) {
		return convertToCrossEntity(entity, isDeep, CourseTermEntity.class);
	}

	@Override
	public CourseTerm convertToDTO(CourseTermEntity entity, boolean isDeep) {
		return convertToCrossEntity(entity, isDeep, CourseTerm.class);
	}

	@Override
	public List<CourseTermEntity> convertToEntityList(List<CourseTerm> list, boolean isDeep) {
		return convertListToCrossEntity(list, isDeep, CourseTermEntity.class);
	}

	@Override
	public List<CourseTerm> convertToDTOList(List<CourseTermEntity> list, boolean isDeep) {
		return convertListToCrossEntity(list, isDeep, CourseTerm.class);
	}
}
