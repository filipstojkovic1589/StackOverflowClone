/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tapestry.stackoverflowclone.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author filip
 */
@Entity
@Table(name = "answer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Answer.findAll", query = "SELECT a FROM Answer a"),
    @NamedQuery(name = "Answer.findByAnswerId", query = "SELECT a FROM Answer a WHERE a.answerId = :answerId"),
    @NamedQuery(name = "Answer.findByAnswerAuthorId", query = "SELECT a FROM Answer a WHERE a.answerAuthorId = :answerAuthorId"),
    @NamedQuery(name = "Answer.findByAnswerText", query = "SELECT a FROM Answer a WHERE a.answerText = :answerText"),
    @NamedQuery(name = "Answer.findByAnswerDate", query = "SELECT a FROM Answer a WHERE a.answerDate = :answerDate")})
public class Answer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ANSWER_ID")
    private Integer answerId;
    @Column(name = "ANSWER_AUTHOR_ID")
    private Integer answerAuthorId;
    @Column(name = "ANSWER_TEXT")
    private String answerText;
    @Column(name = "ANSWER_DATE")
    @Temporal(TemporalType.DATE)
    private Date answerDate;
    @JoinColumn(name = "QUESTION_ID", referencedColumnName = "QUESTION_ID")
    @ManyToOne
    private Question questionId;

    @Inject
    public Answer() {
    }

    public Answer(Integer answerId) {
        this.answerId = answerId;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public Integer getAnswerAuthorId() {
        return answerAuthorId;
    }

    public void setAnswerAuthorId(Integer answerAuthorId) {
        this.answerAuthorId = answerAuthorId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Date getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(Date answerDate) {
        this.answerDate = answerDate;
    }

    public Question getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Question questionId) {
        this.questionId = questionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (answerId != null ? answerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Answer)) {
            return false;
        }
        Answer other = (Answer) object;
        if ((this.answerId == null && other.answerId != null) || (this.answerId != null && !this.answerId.equals(other.answerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tapestry.stackoverflowclone.entities.Answer[ answerId=" + answerId + " ]";
    }
    
}
