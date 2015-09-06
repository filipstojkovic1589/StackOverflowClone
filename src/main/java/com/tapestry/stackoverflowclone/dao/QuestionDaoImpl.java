/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tapestry.stackoverflowclone.dao;

import com.tapestry.stackoverflowclone.entities.Question;
import java.util.List;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author filip
 */
public class QuestionDaoImpl implements QuestionDao{

    @Inject
    private Session session;
    
    /**
     * Returns List of all Questions.
     * @return
     */
    @Override
    public List<Question> getAllQuestions() {
        return session.createCriteria(Question.class).list();
    }

    /**
     * Returns Questions that has passed id.
     * @param id
     * @return
     */
    @Override
    public Question getQuestionById(Integer id) {
        return (Question) session.createCriteria(Question.class).add(Restrictions.eq("questionId", id)).uniqueResult();
    }

    /**
     * Adds new Question.
     * @param question
     */
    @Override
    public void addQuestion(Question question) {
        session.persist(question);
    }

    /**
     * Removes Questions that has passed id.
     * @param id
     */
    @Override
    public void removeQuestion(Integer id) {
        Question question = (Question) session.createCriteria(Question.class).add(Restrictions.eq("questionId", id)).uniqueResult();
        session.delete(question);
    }

    /**
     * Searches all Questions, by checking if caption or text contains passed text.
     * Then returns the result.
     * @param query
     * @return
     */
    @Override
    public List<Question> getQuestionByQuery(String query) {
        Criteria criteria = session.createCriteria(Question.class); 
        Criterion rest1= Restrictions.and(Restrictions.like("questionText", "%" + query + "%"));
        Criterion rest2= Restrictions.and(Restrictions.like("questionCaption", "%" + query + "%"));
        criteria.add(Restrictions.or(rest1, rest2));
        return criteria.list();
        //return session.createCriteria(Question.class).add(Restrictions.like("questionText", "%" + caption + "%")).list();
    }

    /**
     * Returns the number of Questions in the database.
     * @return
     */
    @Override
    public int allActiveSizeQuestions() {
        Long l = (Long) session.createCriteria(Question.class).setProjection(Projections.rowCount()).uniqueResult();
        return l.intValue();
    }

    /**
     * Returns 20 Questions starting from the passed position.
     * @param from
     * @return
     */
    @Override
    public List<Question> loadActiveFromTo(int from) {
        int page = (from - 1) * 20;
        List<Question> lista = session.createCriteria(Question.class).setFirstResult(page).setMaxResults(20).addOrder(Order.asc("questionId")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return lista;
    }
}
