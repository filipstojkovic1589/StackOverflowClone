/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tapestry.stackoverflowclone.dao;

import com.tapestry.stackoverflowclone.entities.Answer;
import com.tapestry.stackoverflowclone.entities.Question;
import java.util.List;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author filip
 */
public class AnswerDaoImpl implements AnswerDao {

    @Inject
    private Session session;

    /**
     * Returns List of all Answer, for the passed Question.
     * @param question
     * @return
     */
    @Override
    public List<Answer> getAnswersByQuestion(Question question) {
        return session.createCriteria(Answer.class).add(Restrictions.eq("questionId", question)).list();
    }
    
    /**
     * Returns size of the List of all Questions, for the passed Question.
     * @param q
     * @return
     */
    @Override
    public int allActiveSizeAnswerForQuestion(Question q) {
        List<Question> questions = session.createCriteria(Answer.class).add(Restrictions.eq("questionId", q)).list();
        return questions.size();
    }

    /**
     * Merges new Answer.
     * @param answer
     */
    @Override
    public void addAnswer(Answer answer) {
        session.merge(answer);
    }

    /**
     * Removes Answer that has passed id.
     * @param id
     */
    @Override
    public void removeAnswer(Integer id) {
        Answer answer = (Answer)session.createCriteria(Answer.class).add(Restrictions.eq("answerId", id)).uniqueResult();
        session.delete(answer);
    }

    /**
     * Returns Answer that has passed id.
     * @param id
     * @return
     */
    @Override
    public Answer getAnswerById(Integer id) {
        return (Answer) session.createCriteria(Answer.class).add(Restrictions.eq("answerId", id)).uniqueResult();
    }
}
