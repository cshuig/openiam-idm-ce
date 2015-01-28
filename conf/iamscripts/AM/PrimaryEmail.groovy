import org.openiam.am.srvc.service.AbstractAuthResourceAttributeMapper

public class PrimaryEmailMapper extends AbstractAuthResourceAttributeMapper {

    @Override
    public String mapAttribute(){
        System.out.println("Called email");
		System.out.println(getUser());
		System.out.println(getUser().getEmail());
        return getUser().getEmail();
    }
	
	@Override
	public String mapValue(String value) {
	
	}

	@Override
    public String getAttributeName() {
	
	}
}