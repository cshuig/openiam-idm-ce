def sf = (binding.hasVariable("searchFilter")? searchFilter : null)?: "(&(objectclass=person)(sAMAccountName=*))"
output= sf as String