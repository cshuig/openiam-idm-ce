package org.openiam.idm.srvc.pswd.generator;


/**
 * Password generator builder.
 */
public class PasswordGeneratorBuilder {
    public static PasswordGenerator createSimplePasswordGenerator() {
        return new SimplePasswordGenerator();
    }

    public static PasswordGenerator createComplexPasswordGenerator() {
        return new ComplexPasswordGenerator();
    }
}