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
    
    @Override
    public List<Answer> getAllAnswers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Answer> getAnswersByQuestion(Question question) {
        return session.createCriteria(Answer.class).add(Restrictions.eq("questionId", question)).list();
    }
    
    @Override
    public int allActiveSizeAnswerForQuestion(Question q) {
        List<Question> questions = session.createCriteria(Answer.class).add(Restrictions.eq("questionId", q)).list();
        return questions.size();
    }

    @Override
    public void addAnswer(Answer answer) {
        session.merge(answer);
    }

    @Override
    public void removeAnswer(Integer id) {
        Answer answer = (Answer)session.createCriteria(Answer.class).add(Restrictions.eq("answerId", id)).uniqueResult();
        session.delete(answer);
    }

    @Override
    public Answer getAnswerById(Integer id) {
        return (Answer) session.createCriteria(Answer.class).add(Restrictions.eq("answerId", id)).uniqueResult();
    }
}
