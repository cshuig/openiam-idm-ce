import org.openiam.idm.srvc.user.service.UserDataService
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.TransactionCallback
import org.springframework.transaction.support.TransactionTemplate

println "Executing groovy script..."

def manager = context.getBean("transactionManager") as PlatformTransactionManager
def _binding = binding

def template = new TransactionTemplate(manager)
template.propagationBehavior = TransactionTemplate.PROPAGATION_REQUIRED

def result = template.execute(new TransactionCallback<Boolean>() {
    @Override
    Boolean doInTransaction(TransactionStatus status) {

        // this part of the script is wrapped in a transaction
        def userService = _binding.context.getBean(UserDataService.class) as UserDataService
        def u = userService.getUserByPrincipal("helpdesk", "0", false)

        println "User roles: " + u.roles.each { it.name }
        return true

    }
})

println "Result of the script execution: " + result
output = result
