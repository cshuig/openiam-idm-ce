
import org.openiam.idm.srvc.user.dto.UserStatusEnum;

int UF_ACCOUNTDISABLE = 0x0002;
int UF_PASSWD_NOTREQD = 0x0020;
int UF_PASSWD_CANT_CHANGE = 0x0040;
int UF_NORMAL_ACCOUNT = 0x0200;
int UF_DONT_EXPIRE_PASSWD = 0x10000;
int UF_PASSWORD_EXPIRED = 0x800000;




if (UserStatusEnum.DISABLED.equals(user.getSecondaryStatus())) {
    output = Integer.toString(UF_ACCOUNTDISABLE + UF_PASSWD_CANT_CHANGE);
}else {
    if (UserStatusEnum.ACTIVE.equals(user.getStatus())) {
        output = Integer.toString(UF_NORMAL_ACCOUNT + UF_PASSWD_CANT_CHANGE);
    }else {
        output = Integer.toString(UF_ACCOUNTDISABLE + UF_PASSWD_CANT_CHANGE);
    }
}




