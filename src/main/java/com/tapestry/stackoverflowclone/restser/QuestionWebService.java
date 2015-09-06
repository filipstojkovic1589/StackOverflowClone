/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tapestry.stackoverflowclone.restser;

import java.net.URISyntaxException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author filip
 */
@Path("/readquestion")
public class QuestionWebService implements QuestionWebServiceInterface{

    /**
     * Web service function.
     * Takes passed id, and redirects to the new page and sends id again.
     * @param id
     * @return
     */
    @Override
    public Object getQuestion(@PathParam("id") Integer id) {   
        java.net.URI location = null;
        try {
            location = new java.net.URI("../readquestion/" + id);
        } catch (URISyntaxException ex) {}
        return Response.temporaryRedirect(location).build();
    }
}
