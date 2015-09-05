/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tapestry.stackoverflowclone.dao;

import com.tapestry.stackoverflowclone.entities.Question;
import java.util.List;

/**
 *
 * @author filip
 */
public interface QuestionDao {
    public List<Question> getAllQuestions();
    public Question getQuestionById(Integer id);
    public List<Question> getQuestionByQuery(String query);
    public void addQuestion(Question question);
    public void removeQuestion(Integer id);
    public abstract int allActiveSizeQuestions();
    public abstract List<Question> loadActiveFromTo(int from);
}
