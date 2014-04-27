package org.openiam.dozer.converter;

import org.openiam.idm.srvc.edu.course.domain.CourseTermUserEntity;
import org.openiam.idm.srvc.edu.course.dto.CourseTermUser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("courseTermUserDozerMapper")
public class CourseTermUserDozerConverter extends AbstractDozerEntityConverter<CourseTermUser, CourseTermUserEntity> {
    @Override
	public CourseTermUserEntity convertEntity(CourseTermUserEntity entity, boolean isDeep) {
		return convert(entity, isDeep, CourseTermUserEntity.class);
	}

	@Override
	public CourseTermUser convertDTO(CourseTermUser entity, boolean isDeep) {
		return convert(entity, isDeep, CourseTermUser.class);
	}

	@Override
	public CourseTermUserEntity convertToEntity(CourseTermUser entity, boolean isDeep) {
		return convertToCrossEntity(entity, isDeep, CourseTermUserEntity.class);
	}

	@Override
	public CourseTermUser convertToDTO(CourseTermUserEntity entity, boolean isDeep) {
		return convertToCrossEntity(entity, isDeep, CourseTermUser.class);
	}

	@Override
	public List<CourseTermUserEntity> convertToEntityList(List<CourseTermUser> list, boolean isDeep) {
		return convertListToCrossEntity(list, isDeep, CourseTermUserEntity.class);
	}

	@Override
	public List<CourseTermUser> convertToDTOList(List<CourseTermUserEntity> list, boolean isDeep) {
		return convertListToCrossEntity(list, isDeep, CourseTermUser.class);
	}
}
