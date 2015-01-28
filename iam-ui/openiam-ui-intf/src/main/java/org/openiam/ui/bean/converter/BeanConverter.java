package org.openiam.ui.bean.converter;

public interface BeanConverter<Entity, Bean> {

	public Entity convert(final Bean bean);
}
