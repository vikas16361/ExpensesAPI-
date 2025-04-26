package com.vikas.TrackerAPI.Exception;

import lombok.Data;

import java.sql.Date;

@Data
public class ErrorObject {
    private Integer statusCode;
    private String statusMessage;
    private Date timestamp;




}
