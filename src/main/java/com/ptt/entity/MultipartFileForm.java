package com.ptt.entity;

import javax.ws.rs.FormParam;

public class MultipartFileForm {
    @FormParam("file")
    public byte[] file;
    
    @FormParam("name")
    public String name;
}
