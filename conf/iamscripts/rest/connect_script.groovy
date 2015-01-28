import org.openiam.connector.common.scim.S;
import org.openiam.connector.common.scim.TestRSA;

 println("Inside Connect Script..");
		S token = new S();
	    token.setTimestamp(System.currentTimeMillis());
	    //String encrypted =token.getPassword();
		String encrypted = TestRSA.encrypt(token);
 
								
output=encrypted	