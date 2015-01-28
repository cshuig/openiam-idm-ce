
def String password = req.getNotificationParam("PSWD").valueObj


def String msg =		"""\
			                  <User xmlns=\"urn:scim:schemas:core:1.0\" 
			                  xmlns:enterprise=\"urn:scim:schemas:extension:enterprise:1.0\">
                              <password>
			                  ${password}
			                  </password>
							  </User>
							  
							  """
								
output=msg	