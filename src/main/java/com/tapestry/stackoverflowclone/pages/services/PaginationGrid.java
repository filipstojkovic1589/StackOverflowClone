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
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.TextStreamResponse;

/**
 *
 * @author filip
 */
public class PaginationGrid {

    @Inject
    private QuestionDao questionDao;
    private int size = 20;
    
    @Inject
    private AnswerDao answerDao;

    Object onActivate(@RequestParameter("page") int page) {
        Class<?> act = null;
        int sizeOfAll = questionDao.allActiveSizeQuestions();
        List<Question> lista = new ArrayList<Question>();
        lista = questionDao.loadActiveFromTo(page);
        String response = 
                "<h4>"
                + lista.size()
                + " results"
                + "</h3>";
        for (Question q : lista) {
            response += (
                    "<div class=\"row\" style=\"border-top: 1px solid #ccc;\">"
                    + "<div class=\"col-sm-1 answer-count\">"
                    + answerDao.allActiveSizeAnswerForQuestion(q) + " answers"
                    + "</div>"
                    + "<div class=\"col-sm-11\">"
                    + "<div>"
                    + "<h4 style=\"color:#49B7FD\">" 
                    
                    + "<a href=\"rest/readquestion/" + q.getQuestionId() +"\">"
                    
                    + "Q: " + q.getQuestionCaption() 
                    
                    + "</a>"
                    
                    + "</h4>"
                    + "</div>"
                    + "<div>"
                    + q.getQuestionText()
                    + "</div>"
                    + "<div style=\"color: #858D93; text-align:right\">"
                    + "asked " + q.getQuestionDate() + " by " + q.getUserId().getUserName()
                    + "</div>"
                    + "</div>"
                    + "</div>"
                    );
        }
        float ceil = (float) sizeOfAll / (float) 20;
        int totalPageSizes = (int) Math.ceil(ceil);
        response += "<ul class=\"pagination\">";
        for (int i = 1; i <= totalPageSizes; i++) {
            if (page == i) {
                response += ("<li class=\"callservice active\"><a href=\"#\">" + i + "</a></li>\n");
            } else {
                response += ("<li class=\"callservice\"><a href=\"#\">" + i + "</a></li>\n");
            }
        }
        response += "</ul>";
        return new TextStreamResponse("text/plain", response);
    }
   
}

