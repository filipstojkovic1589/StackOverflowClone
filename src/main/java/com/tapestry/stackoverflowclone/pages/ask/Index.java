/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tapestry.stackoverflowclone.pages.ask;

import com.tapestry.stackoverflowclone.dao.QuestionDao;
import com.tapestry.stackoverflowclone.entities.Question;
import com.tapestry.stackoverflowclone.entities.User;
import com.tapestry.stackoverflowclone.services.ProtectedPage;
import java.util.Date;
import javax.annotation.security.RolesAllowed;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author filip
 */
@ProtectedPage
@RolesAllowed(value={"User","Admin"})
public class Index {
    
    @SessionState
    private User loggedInUser;
    
    @Inject
    private QuestionDao questionDao;
    @Property
    private Question question;

    void onActivate() {
        question = new Question();
    }

    @CommitAfter
    Object onSuccess() {
        question.setUserId(loggedInUser);
        question.setQuestionDate(new Date());
        questionDao.addQuestion(question);
        return this;
    }
}
