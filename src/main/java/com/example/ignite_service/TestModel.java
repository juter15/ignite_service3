package com.example.ignite_service;

import lombok.Data;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

@Data
public class TestModel {
    @QuerySqlField(index = true)
    private Long id;
    @QuerySqlField(index = true)
    private String name;

}
