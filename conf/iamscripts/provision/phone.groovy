if (user.phones != null && user.phones.size() > 0) {
    List<String> phones = new LinkedList<String>();
    for(org.openiam.idm.srvc.continfo.dto.Phone phone :  user.phones) {
        String phoneString =  phone.areaCd + "-" +phone.phoneNbr;
        phones.add(phoneString);
    }

    output=phones
}else {
    output=null
}
