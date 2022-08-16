package com.ptt.entity;

import java.io.InputStream;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

public class MultipartFileForm {
    @RestForm("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public InputStream file;
    
    @RestForm("name")
    public String name;
}
