package org.openiam.dozer.converter;

import org.openiam.idm.srvc.edu.course.domain.ProgramEntity;
import org.openiam.idm.srvc.edu.course.dto.Program;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("programDozerMapper")
public class ProgramDozerConverter extends AbstractDozerEntityConverter<Program, ProgramEntity> {

    @Override
	public ProgramEntity convertEntity(ProgramEntity entity, boolean isDeep) {
		return convert(entity, isDeep, ProgramEntity.class);
	}

	@Override
	public Program convertDTO(Program entity, boolean isDeep) {
		return convert(entity, isDeep, Program.class);
	}

	@Override
	public ProgramEntity convertToEntity(Program entity, boolean isDeep) {
		return convertToCrossEntity(entity, isDeep, ProgramEntity.class);
	}

	@Override
	public Program convertToDTO(ProgramEntity entity, boolean isDeep) {
		return convertToCrossEntity(entity, isDeep, Program.class);
	}

	@Override
	public List<ProgramEntity> convertToEntityList(List<Program> list, boolean isDeep) {
		return convertListToCrossEntity(list, isDeep, ProgramEntity.class);
	}

	@Override
	public List<Program> convertToDTOList(List<ProgramEntity> list, boolean isDeep) {
		return convertListToCrossEntity(list, isDeep, Program.class);
	}
}
