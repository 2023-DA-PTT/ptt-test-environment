package com.ptt.boundary;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.MultipartForm;

import com.ptt.entity.MultipartFileForm;

public class MultipartEndpoint {
    
    @Path("multipart")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public String testMultipart(@MultipartForm MultipartFileForm form) {
        return form.name;
    }
}
