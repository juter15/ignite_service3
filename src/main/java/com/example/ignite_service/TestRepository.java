package com.example.ignite_service;

import org.apache.ignite.springdata22.repository.IgniteRepository;
import org.apache.ignite.springdata22.repository.config.RepositoryConfig;

@RepositoryConfig(cacheName = "test1")
public interface TestRepository extends IgniteRepository<TestModel, Long> {
}
