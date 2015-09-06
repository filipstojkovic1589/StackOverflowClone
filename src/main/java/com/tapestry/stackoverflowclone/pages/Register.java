/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tapestry.stackoverflowclone.pages;

import com.tapestry.stackoverflowclone.dao.UserDao;
import com.tapestry.stackoverflowclone.data.Role;
import com.tapestry.stackoverflowclone.entities.User;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author filip
 */
public class Register {

    @Inject
    private UserDao userDao;
    @Property
    private User user;
    @SessionState
    private User loggedInUser;

    @Component
    private BeanEditForm form;

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

    @CommitAfter
    Object onSuccess() {
        if (!userDao.checkIfEmailExists(user.getUserEmail())) {
            String unhashPassword = user.getUserPassword();
            user.setUserPassword(getMD5Hash(unhashPassword));
            user.setUserRole(Role.User);
            User u = userDao.registerUser(user);
            loggedInUser = u;
            return Index.class;
        } else {
            form.recordError("Provided E-mail is already in use.");
            return null;
        }
    }
}
