package com.ptt.boundary;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

@Path("/multipart")
public class MultipartEndpoint {
    
    private static final Logger LOG = Logger.getLogger(MultipartEndpoint.class);
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public String testMultipart(HttpHeaders headers, String form) {
        LOG.info("content-type: " + headers.getHeaderString("content-type"));
        LOG.info("received multipart: " + form);
        return form;
    }
}
