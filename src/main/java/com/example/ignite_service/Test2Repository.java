package com.example.ignite_service;

import org.apache.ignite.springdata22.repository.IgniteRepository;
import org.apache.ignite.springdata22.repository.config.RepositoryConfig;

@RepositoryConfig(cacheName = "test2Cache")
public interface Test2Repository extends IgniteRepository<Test2Cache, Long> {
}
