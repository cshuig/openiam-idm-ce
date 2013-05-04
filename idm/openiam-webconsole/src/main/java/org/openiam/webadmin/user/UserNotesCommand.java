package org.openiam.webadmin.user;

import org.openiam.idm.srvc.user.dto.UserNote;

import java.io.Serializable;
import java.util.List;

/**
 * Command object for the UserNoteController
 * @author suneet
 *
 */
public class UserNotesCommand implements Serializable {
	 

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1193834815491224478L;
	protected List<UserNote> noteList;
	protected String perId; // personId

    public List<UserNote> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<UserNote> noteList) {
        this.noteList = noteList;
    }

    public String getPerId() {
		return perId;
	}
	public void setPerId(String perId) {
		this.perId = perId;
	}


	



}
