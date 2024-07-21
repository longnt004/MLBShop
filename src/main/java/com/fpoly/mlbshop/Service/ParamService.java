package com.fpoly.mlbshop.Service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

@Service
public class ParamService {
    @Autowired
    HttpServletRequest request;

    public String getParam(String name, String defaultValue){
        return request.getParameter(name) == null ? defaultValue : request.getParameter(name);
    }


    public int getIntParam(String name, int defaultValue){
        try {
            return Integer.parseInt(request.getParameter(name));
        } catch (Exception e){
            return defaultValue;
        }
    }

    public boolean getBooleanParam(String name, boolean defaultValue){
        try {
            return Boolean.parseBoolean(request.getParameter(name));
        } catch (Exception e){
            return defaultValue;
        }
    }

    public double getDoubleParam(String name, double defaultValue){
        try {
            return Double.parseDouble(request.getParameter(name));
        } catch (Exception e){
            return defaultValue;
        }
    }

    public Date getDateParam(String name, Date defaultValue){
        try {
            return new Date(request.getParameter(name));
        } catch (Exception e){
            return defaultValue;
        }
    }

    public File getFileParam(String name, File defaultValue){
        try {
            return new File(request.getParameter(name));
        } catch (Exception e){
            return defaultValue;
        }
    }

    public String[] getListStringParam(String name, String[] defaultValue){
        try {
            return request.getParameterValues(name);
        } catch (Exception e){
            return defaultValue;
        }
    }

    public MultipartFile[] getListMultipartFile(String slideImg, MultipartFile[] multipartFiles) {
        try {
            return (MultipartFile[]) request.getAttribute(slideImg);
        } catch (Exception e){
            return multipartFiles;
        }
    }
}
