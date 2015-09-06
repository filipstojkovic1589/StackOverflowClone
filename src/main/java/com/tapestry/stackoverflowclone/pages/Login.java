package com.tapestry.stackoverflowclone.pages;

import com.tapestry.stackoverflowclone.dao.UserDao;
import com.tapestry.stackoverflowclone.entities.User;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author filip
 */
public class Login {

    @Inject
    private UserDao userDao;
    @Property
    private User userLogin;
    @SessionState
    private User loggedInUser;
    @Component
    private BeanEditForm form;

    Object onActivate() {
        if (loggedInUser.getUserEmail() != null) {
            return Index.class;
        }
        return null;
    }

    /**
     * Returns hash value for the passed string.
     * @param yourString
     * @return
     */
    public String getMD5Hash(String yourString) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(yourString.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    Object onSuccess(){
        String password = getMD5Hash(userLogin.getUserPassword());
        User u = userDao.checkUser(userLogin.getUserEmail(), password);
        if(u != null){
            loggedInUser = u;
            return Index.class;
        }else{
            form.recordError("Uneli ste pogresne parametre.");
            return null;
        }
    }
}
