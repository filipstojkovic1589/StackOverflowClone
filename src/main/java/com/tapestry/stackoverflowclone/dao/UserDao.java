/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tapestry.stackoverflowclone.dao;

import com.tapestry.stackoverflowclone.entities.User;
import java.util.List;

/**
 *
 * @author filip
 */
public interface UserDao {
    public List<User> getAllUsers();
    public User getUserById(Integer id);
    public User checkUser(String email, String password);
    public User registerUser(User user);
    public boolean checkIfEmailExists(String email);
}