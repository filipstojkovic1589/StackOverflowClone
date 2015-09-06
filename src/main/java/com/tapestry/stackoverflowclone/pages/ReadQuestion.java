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
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

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
    
    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    void onActivate() {
        newanswer = new Answer();
        answers = answerDao.getAnswersByQuestion(question);
    }
    
    void onActivate(String id) {
        newanswer = new Answer();
        question = (Question) questionDao.getQuestionById(Integer.parseInt(id));
        answers = answerDao.getAnswersByQuestion(question);
    }
    
    /**
     * Serves as temporary object used in TML.
     * Returns the userName of Answer author.
     * @return
     */
    public String getOneUser(){
        return userDao.getUserById(oneanswer.getAnswerAuthorId()).getUserName();
    }
    
    /**
     * Returns number of Answers for the question.
     * @return
     */
    public String getAnswerCount(){
        return answerDao.allActiveSizeAnswerForQuestion(question) + "";
    }
    
    /**
     * Checks if use is logged in.
     * @return
     */
    public boolean getLoggedIn() {
        if (loggedInUser.getUserEmail() != null) {
            return true;
        }
        return false;
    }
    
    @CommitAfter
    Object onActionFromDelete(int id)
    {
        answerDao.removeAnswer(id);
        return this;
    }
    
    @CommitAfter
    Object onActionFromDeleteQuestion(int id)
    {
        for (Answer a : answerDao.getAnswersByQuestion(question)) {
            answerDao.removeAnswer(a.getAnswerId());
        }
        questionDao.removeQuestion(id);
        
        
        return SuccessfulRemove.class;
    }    
    
    /**
     * Checks if the logged user is the author of the current answer.
     * @return
     */
    public boolean getMyAnswer(){
        if(getLoggedIn()){
            if(oneanswer.getAnswerAuthorId() == loggedInUser.getUserId()){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if the logged user is the author of the current question.
     * @return
     */
    public boolean getMyQuestion(){
        if(getLoggedIn()){
            if(question.getUserId().getUserId() == loggedInUser.getUserId()){
                return true;
            }
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
