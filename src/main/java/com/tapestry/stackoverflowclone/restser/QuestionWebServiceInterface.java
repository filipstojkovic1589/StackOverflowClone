/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tapestry.stackoverflowclone.restser;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author filip
 */
public interface QuestionWebServiceInterface {
  
    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Object getQuestion(@PathParam("id") Integer id);
}
