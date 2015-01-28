def sf = binding.hasVariable("searchFilter")? searchFilter : "*"
def us = binding.hasVariable("updatedSince")? updatedSince : ""

output = "(&(objectClass=user)(sAMAccountName=${sf}))" as String