package org.openiam.provision.cmd

import org.openiam.idm.srvc.policy.dto.Policy
import org.openiam.idm.srvc.policy.dto.PolicyAttribute
import org.openiam.idm.srvc.policy.service.PolicyDataService
import org.openiam.idm.srvc.pswd.service.PasswordGenerator;


def policyDataService = context.getBean("policyDataService") as PolicyDataService

//Here may be neeeded to determine current policy ID
Policy policy = policyDataService.getPolicy("4000");
// static by defauls
String pswd = "Password\$51";
PolicyAttribute pa = policy.getAttribute("INITIAL_PASSWORD");
if (pa && pa.isRequired() && "RANDOM".equals(pa.value1)) {
// random password generator
    pswd = PasswordGenerator.generatePassword(policy);
}
output = pswd
