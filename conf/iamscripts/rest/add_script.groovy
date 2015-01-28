
def String identity = req.getNotificationParam("IDENTITY").valueObj
def String password = req.getNotificationParam("PSWD").valueObj


def msg = """\
			<User xmlns=\"urn:scim:schemas:core:1.0\" 
			xmlns:enterprise=\"urn:scim:schemas:extension:enterprise:1.0\">
			<userName>
			 ${identity}
			</userName>
			<password>
			 ${password}
			</password>
			<preferredLanguage>en_US</preferredLanguage>
			<emails>
			<email>
			<value>
			 ${identity}
			@test.com</value>
			<primary>true</primary>
			</email>
			</emails>
			<addresses><address><country>FI</country></address></addresses>
			<enterprise:gender>male</enterprise:gender>
			</User>
"""

						

output=msg.toString()							
