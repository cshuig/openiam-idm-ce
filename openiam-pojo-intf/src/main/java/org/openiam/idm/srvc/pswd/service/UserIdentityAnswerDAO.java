/*
 * Copyright 2009, OpenIAM LLC 
 * This file is part of the OpenIAM Identity and Access Management Suite
 *
 *   OpenIAM Identity and Access Management Suite is free software: 
 *   you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License 
 *   version 3 as published by the Free Software Foundation.
 *
 *   OpenIAM is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   Lesser GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with OpenIAM.  If not, see <http://www.gnu.org/licenses/>. *
 */

/**
 *
 */
package org.openiam.idm.srvc.pswd.service;

import org.openiam.idm.srvc.continfo.domain.UserIdentityAnswerEntity;

import java.util.List;

/**
 * @author suneet
 */
public interface UserIdentityAnswerDAO {

    public abstract UserIdentityAnswerEntity add(UserIdentityAnswerEntity transientInstance);

    public abstract void delete(UserIdentityAnswerEntity persistentInstance);

    public UserIdentityAnswerEntity update(
            UserIdentityAnswerEntity detachedInstance);

    UserIdentityAnswerEntity findById(java.lang.String id);

    List<UserIdentityAnswerEntity> findAnswersByUser(String userId);

}