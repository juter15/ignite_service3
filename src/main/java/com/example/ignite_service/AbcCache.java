package com.example.ignite_service;

import lombok.Data;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;

@Data
public class AbcCache implements Serializable {
    @QuerySqlField(index = true)
    private String abcId;
    @QuerySqlField(index = true)
    private String abcName;
}
