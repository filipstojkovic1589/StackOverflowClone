/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tapestry.stackoverflowclone.pages;

import com.tapestry.stackoverflowclone.dao.AnswerDao;
import com.tapestry.stackoverflowclone.dao.QuestionDao;
import com.tapestry.stackoverflowclone.dao.UserDao;
import com.tapestry.stackoverflowclone.entities.Answer;
import com.tapestry.stackoverflowclone.entities.Question;
import com.tapestry.stackoverflowclone.entities.User;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author filip
 */
public class ReadQuestion {

    @SessionState
    private User loggedInUser;
    
    @Inject
    private QuestionDao questionDao;
    @Property
    @Persist
    private Question question;

    @Inject
    private AnswerDao answerDao;
    @Property
    @Persist
    private List<Answer> answers;
    @Property
    private Answer oneanswer;
    
    @Property
    private Answer newanswer;
    
    @Inject 
    private UserDao userDao;

    void onActivate() {
        newanswer = new Answer();
        answers = answerDao.getAnswersByQuestion(question);
    }
    
    void onActivate(String id) {
        newanswer = new Answer();
        question = (Question) questionDao.getQuestionById(Integer.parseInt(id));
        answers = answerDao.getAnswersByQuestion(question);
    }
    
    public String getOneUser(){
        return userDao.getUserById(oneanswer.getAnswerAuthorId()).getUserName();
    }
    
    public String getAnswerCount(){
        return answerDao.allActiveSizeAnswerForQuestion(question) + "";
    }
    
    public boolean getLoggedIn() {
        if (loggedInUser.getUserEmail() != null) {
            return true;
        }
        return false;
    }
    
    @CommitAfter
    Object onSuccess() {
        newanswer.setAnswerAuthorId(loggedInUser.getUserId());
        newanswer.setAnswerDate(new Date());
        newanswer.setQuestionId(question);
        answerDao.addAnswer(newanswer);      
        return this;
    }
}
