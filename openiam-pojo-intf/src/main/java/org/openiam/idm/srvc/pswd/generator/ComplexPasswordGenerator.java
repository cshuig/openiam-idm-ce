package org.openiam.idm.srvc.pswd.generator;

import java.util.Random;

/**
 * Configurable password generator
 */
public class ComplexPasswordGenerator implements PasswordGenerator {

    private static Random rand = new Random(System.currentTimeMillis());

    private static final char[] lowerChars = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private static final char[] upperChars = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private static final char[] numericChars = {'0','1','2','3','4','5','6','7','8','9'};
    private static final char[] specialChars = {'!','$','@','%','&','{','}','*','#','%','+','-','_','/','?'};

    private static final String charset = "!$@%&{}*#%+-_/?0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static final String specialCharset = "!$@%&{}*#%+-_/?";
    private static final String numericCharset = "0123456789";
    private static final String upperCharset = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String lowerCharset = "abcdefghijklmnopqrstuvwxyz";

    @Override
    public String generatePassword(int size) {
        PasswordGenParameters params = new PasswordGenParameters();
        params.setSize(size);
        params.setMinNumeric(1);
        params.setMaxNumeric(2);
        params.setMinCapital(1);
        params.setMaxCapital(2);
        params.setMaxSpecialChar(1);
        params.setMinSpecialChar(1);

        return generatePassword(params);

    }

    @Override
    public String generatePassword(PasswordGenParameters param) {

        boolean foundRequiredChar = false;

        boolean lcase = false, ucase = false, numchar = false, specialchar = false;

        int lcaseCount = 0;
        int ucaseCount = 0;
        int numCount = 0;
        int specialCount = 0;

        int length = param.getSize();

        // update the flags based on what we are not supposed to have
        if (param.getMaxSpecialChar() == 0) {
            specialchar = true;
        }
        if (param.getMaxLowerCase() == 0) {
            lcase = true;
        }
        if (param.getMaxCapital() == 0)  {
            ucase = true;
        }

        if (param.getMaxNumeric() == 0) {
            numchar = true;

        }


        StringBuilder sb = new StringBuilder();

        while (sb.length() < length) {

            if (sb.length() >= (length-4) && !foundRequiredChar ) {
                // we have 4 chars to fill and need to make sure that we meet the password policy

                if (!specialchar) {
                    getMissingChar( rand, specialCharset, sb );
                    specialchar = true;
                    specialCount++;
                    continue;
                }
                if (!numchar) {
                    getMissingChar( rand, numericCharset, sb );
                    numchar = true;
                    numCount++;
                    continue;
                }
                if (!ucase) {
                    getMissingChar( rand, upperCharset, sb );
                    ucase = true;
                    ucaseCount++;
                    continue;
                }
                if (!lcase) {
                    getMissingChar( rand, lowerCharset, sb );
                    lcase = true;
                    lcaseCount++;
                    continue;
                }

            }


            int pos = rand.nextInt(charset.length());

            char c = charset.charAt(pos);

            if (contain(lowerChars, c) && lcaseCount < param.getMaxLowerCase()  ) {

                int lastCharIndex = sb.length() - 1;
                if ( lastCharIndex >= 0 && sb.charAt(lastCharIndex) != c) {

                    lcaseCount++;

                    if (lcaseCount >= param.getMinLowerCase()) {
                        lcase=true;
                    }
                    sb.append(c);
                }
            }
            if (contain(upperChars, c) && ucaseCount < param.getMaxCapital()) {
                ucaseCount++;

                if (ucaseCount >= param.getMinCapital()) {
                    ucase=true;
                }
                sb.append(c);

            }
            if (contain(numericChars, c) && numCount < param.getMaxNumeric()) {
                numCount++;

                if (numCount >= param.getMinNumeric()) {
                    numchar=true;
                }
                sb.append(c);
            }
            if (contain(specialChars, c) && specialCount < param.getMaxSpecialChar()) {

                specialCount++;

                if (specialCount >= param.getMinSpecialChar()) {
                    specialchar=true;
                }
                sb.append(c);

            }


            if (lcase && ucase && numchar && specialchar) {
                foundRequiredChar = true;

            }
        }


            return sb.toString();
    }

    private boolean contain(char[] referenceString, char c ) {
        for (char ch : referenceString) {
            if (ch == c) {
                return true;

            }

        }
        return false;

    }
    private void getMissingChar( Random rand, String charset, StringBuilder sb ) {
        int p = rand.nextInt(charset.length());
        sb.append(charset.charAt(p));
    }


   /* static public void main(String[] args) {

        // 1 number, 1 Capital, 1 alpa - no special characters
        PasswordGenParameters params = new PasswordGenParameters();
        params.setSize(8);
        params.setMinNumeric(1);
        params.setMaxNumeric(1);
        params.setMinCapital(1);
        params.setMaxCapital(1);
        params.setMaxSpecialChar(0);
        params.setMinSpecialChar(0);

        ComplexPasswordGenerator gen = new ComplexPasswordGenerator();

        for (int i=0; i< 10; i++ ) {

            System.out.println( gen.generatePassword(params) );
        }


        params = new PasswordGenParameters();
        params.setSize(8);
        params.setMinNumeric(1);
        params.setMaxNumeric(2);
        params.setMinCapital(1);
        params.setMaxCapital(2);
        params.setMaxSpecialChar(1);
        params.setMinSpecialChar(1);

        gen = new ComplexPasswordGenerator();

        System.out.println("-------------------");

        for (int i=0; i< 10; i++ ) {

            System.out.println( gen.generatePassword(params) );
        }
    }
    */

}
