/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tapestry.stackoverflowclone.dao;

import com.tapestry.stackoverflowclone.entities.User;
import java.util.List;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author filip
 */
public class UserDaoImpl implements UserDao{

    @Inject
    private Session session;
    
    @Override
    public List<User> getAllUsers() {
        return session.createCriteria(User.class).list();
    }

    @Override
    public User getUserById(Integer id) {
        return (User) session.createCriteria(User.class).add(Restrictions.eq("userId",id)).uniqueResult();
    }
    
    @Override
    public User checkUser(String email, String password) {
        try {
            User u = (User) session.createCriteria(User.class).add(Restrictions.eq("userEmail",
                    email)).add(Restrictions.eq("userPassword", password)).uniqueResult();
            if (u != null) {
                return u;
            }
            return null;
        } catch (NullPointerException e) {
            return null;
        }
    }
    
    @Override
    public boolean checkIfEmailExists(String email) {
        Long rows = (Long) session.createCriteria(User.class).add(Restrictions.eq("userEmail",
                email)).setProjection(Projections.rowCount()).uniqueResult();
        return (rows == 0) ? false : true;
    }
 
    @Override
    public User registerUser(User user) {
        return (User) session.merge(user);
    }
}