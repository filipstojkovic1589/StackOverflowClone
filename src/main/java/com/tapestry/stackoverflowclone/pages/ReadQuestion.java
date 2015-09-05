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
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.Response;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.got5.tapestry5.jquery.components.InPlaceEditor;

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
    
    public boolean getMyAnswer(){
        if(getLoggedIn()){
            if(oneanswer.getAnswerAuthorId() == loggedInUser.getUserId()){
                return true;
            }
        }
        return false;
    }
    
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
  
    //@CommitAfter
    //@OnEvent(component = "imeDrzave", value = InPlaceEditor.SAVE_EVENT)
    //void setImeDrzave(Long id, String value) {
    //    Answer answer = (Answer) answerDao.getDrzavaById(id.intValue());
    //    drzava.setImeDrzave(value);
    //    System.out.println("cuvam drzavu");
    //    gradDrzaveDao.dodajIliUpdatujDrzava(drzava);
    //}
}
