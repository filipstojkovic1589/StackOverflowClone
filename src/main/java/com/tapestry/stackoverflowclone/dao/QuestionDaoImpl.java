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
    
    @Override
    public List<Question> getAllQuestions() {
        return session.createCriteria(Question.class).list();
    }

    @Override
    public Question getQuestionById(Integer id) {
        return (Question) session.createCriteria(Question.class).add(Restrictions.eq("questionId", id)).uniqueResult();
    }

    @Override
    public void addQuestion(Question question) {
        session.persist(question);
    }

    @Override
    public void removeQuestion(Integer id) {
        Question question = (Question) session.createCriteria(Question.class).add(Restrictions.eq("questionId", id)).uniqueResult();
        session.delete(question);
    }

    @Override
    public List<Question> getQuestionByQuery(String query) {
        Criteria criteria = session.createCriteria(Question.class); 
        Criterion rest1= Restrictions.and(Restrictions.like("questionText", "%" + query + "%"));
        Criterion rest2= Restrictions.and(Restrictions.like("questionCaption", "%" + query + "%"));
        criteria.add(Restrictions.or(rest1, rest2));
        return criteria.list();
        //return session.createCriteria(Question.class).add(Restrictions.like("questionText", "%" + caption + "%")).list();
    }

    @Override
    public int allActiveSizeQuestions() {
        Long l = (Long) session.createCriteria(Question.class).setProjection(Projections.rowCount()).uniqueResult();
        return l.intValue();
    }

    @Override
    public List<Question> loadActiveFromTo(int from) {
        int page = (from - 1) * 20;
        List<Question> lista = session.createCriteria(Question.class).setFirstResult(page).setMaxResults(20).addOrder(Order.asc("questionId")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return lista;
    }
}
