/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tapestry.stackoverflowclone.dao;

import com.tapestry.stackoverflowclone.entities.Answer;
import com.tapestry.stackoverflowclone.entities.Question;
import java.util.List;

/**
 *
 * @author filip
 */
public interface AnswerDao {
    public List<Answer> getAllAnswers();
    public List<Answer> getAnswersByQuestion(Question question);
    public int allActiveSizeAnswerForQuestion(Question q);
    public void addAnswer(Answer answer);
    public void removeAnswer(Integer id);
    public Answer getAnswerById(Integer id);
}
