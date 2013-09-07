package org.openiam.idm.srvc.pswd.generator;

/**
 * Created with IntelliJ IDEA.
 * User: suneetshah
 * Date: 9/6/13
 * Time: 5:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class PasswordGenParameters {
    protected int size = 8; // default size of the password

    protected int minNumeric = -1;
    protected int maxNumeric = 99;


    protected int minCapital = -1;
    protected int maxCapital = 99;

    protected int minLowerCase = -1;
    protected int maxLowerCase = 99;

    protected int minSpecialChar = -1;
    protected int maxSpecialChar = 99;

    protected boolean consecutiveCharsAllowed = false; // default

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getMinNumeric() {
        return minNumeric;
    }

    public void setMinNumeric(int minNumeric) {
        this.minNumeric = minNumeric;
    }

    public int getMaxNumeric() {
        return maxNumeric;
    }

    public void setMaxNumeric(int maxNumeric) {
        this.maxNumeric = maxNumeric;
    }


    public int getMinCapital() {
        return minCapital;
    }

    public void setMinCapital(int minCapital) {
        this.minCapital = minCapital;
    }

    public int getMaxCapital() {
        return maxCapital;
    }

    public void setMaxCapital(int maxCapital) {
        this.maxCapital = maxCapital;
    }

    public int getMinLowerCase() {
        return minLowerCase;
    }

    public void setMinLowerCase(int minLowerCase) {
        this.minLowerCase = minLowerCase;
    }

    public int getMaxLowerCase() {
        return maxLowerCase;
    }

    public void setMaxLowerCase(int maxLowerCase) {
        this.maxLowerCase = maxLowerCase;
    }

    public int getMinSpecialChar() {
        return minSpecialChar;
    }

    public void setMinSpecialChar(int minSpecialChar) {
        this.minSpecialChar = minSpecialChar;
    }

    public int getMaxSpecialChar() {
        return maxSpecialChar;
    }

    public void setMaxSpecialChar(int maxSpecialChar) {
        this.maxSpecialChar = maxSpecialChar;
    }

    public boolean isConsecutiveCharsAllowed() {
        return consecutiveCharsAllowed;
    }

    public void setConsecutiveCharsAllowed(boolean consecutiveCharsAllowed) {
        this.consecutiveCharsAllowed = consecutiveCharsAllowed;
    }
}
