/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tapestry.stackoverflowclone.pages.services;

import com.tapestry.stackoverflowclone.dao.AnswerDao;
import com.tapestry.stackoverflowclone.dao.QuestionDao;
import com.tapestry.stackoverflowclone.entities.Question;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.util.TextStreamResponse;

/**
 *
 * @author filip
 */
public class FindState {

    @Inject
    private Request request;
    @Property
    private List<Question> questions;
    @Property
    private Question question;
    @Inject
    private QuestionDao questionDao;
    
    @Inject
    private AnswerDao answerDao;

    Object onActivate(@RequestParameter("questionCaption") String query) {
        if (questions == null) {
            questions = new ArrayList<Question>();
        }
        questions = questionDao.getQuestionByQuery(query);
        String response = 
                "<div class=\"row\">"
                + "<div class=\"col-sm-12\">"
                + "<h4>"
                + questions.size()
                + " results"
                + "</h4>"
                + "</div>"
                + "</div>";
        for (Question q : questions) {
            response += (
                    "<div class=\"row\" style=\"border-top: 1px solid #ccc;\">"
                    + "<div class=\"col-sm-1 answer-count\">"
                    + answerDao.allActiveSizeAnswerForQuestion(q) + " answers"
                    + "</div>"
                    + "<div class=\"col-sm-11\">"
                    + "<div>"
                    + "<h4 style=\"color:#49B7FD\">" 
                    
                    + "<div class=\"question-list-caption\">"
                    + "<a href=\"rest/readquestion/" + q.getQuestionId() +"\">"
                    
                    + "Q: " + q.getQuestionCaption() 
                    
                    + "</a>"
                    + "</div>"
                    
                    + "</h4>"
                    + "</div>"
                    + "<div>"
                    + q.getQuestionText()
                    + "</div>"
                    + "<div class=\"question-list-author\">"
                    + "asked " + q.getQuestionDate() + " by " + q.getUserId().getUserName()
                    + "</div>"
                    + "</div>"
                    + "</div>"
                    );
        }
        return new TextStreamResponse("text/plain", response);
    }
}
