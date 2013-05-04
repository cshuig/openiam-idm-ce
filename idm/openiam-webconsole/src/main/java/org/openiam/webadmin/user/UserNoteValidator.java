package org.openiam.webadmin.user;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class UserNoteValidator implements Validator {


	public boolean supports(Class cls) {
		 return UserNotesCommand.class.equals(cls);
	}

	public void validate(Object cmd, Errors err) {


        UserNotesCommand attrCmd =  (UserNotesCommand) cmd;

			
		
		
	}



	
}
