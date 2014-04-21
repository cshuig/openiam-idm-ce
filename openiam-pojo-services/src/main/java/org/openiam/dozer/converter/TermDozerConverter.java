package org.openiam.dozer.converter;

import org.openiam.idm.srvc.edu.course.domain.TermEntity;
import org.openiam.idm.srvc.edu.course.dto.term.Term;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("termDozerMapper")
public class TermDozerConverter extends AbstractDozerEntityConverter<Term, TermEntity> {

    @Override
	public TermEntity convertEntity(TermEntity entity, boolean isDeep) {
		return convert(entity, isDeep, TermEntity.class);
	}

	@Override
	public Term convertDTO(Term entity, boolean isDeep) {
		return convert(entity, isDeep, Term.class);
	}

	@Override
	public TermEntity convertToEntity(Term entity, boolean isDeep) {
		return convertToCrossEntity(entity, isDeep, TermEntity.class);
	}

	@Override
	public Term convertToDTO(TermEntity entity, boolean isDeep) {
		return convertToCrossEntity(entity, isDeep, Term.class);
	}

	@Override
	public List<TermEntity> convertToEntityList(List<Term> list, boolean isDeep) {
		return convertListToCrossEntity(list, isDeep, TermEntity.class);
	}

	@Override
	public List<Term> convertToDTOList(List<TermEntity> list, boolean isDeep) {
		return convertListToCrossEntity(list, isDeep, Term.class);
	}
}
