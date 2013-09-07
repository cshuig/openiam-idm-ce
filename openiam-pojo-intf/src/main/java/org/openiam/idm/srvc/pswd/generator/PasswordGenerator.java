package org.openiam.idm.srvc.pswd.generator;

/**
 * Created with IntelliJ IDEA.
 * User: suneetshah
 * Date: 9/6/13
 * Time: 5:12 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PasswordGenerator {
    String generatePassword(int size);
    String generatePassword(PasswordGenParameters param);

}
