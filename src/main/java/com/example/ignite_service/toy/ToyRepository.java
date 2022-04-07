package com.example.ignite_service.toy;

import org.apache.ignite.springdata22.repository.IgniteRepository;
import org.apache.ignite.springdata22.repository.config.RepositoryConfig;

@Deprecated
@RepositoryConfig(cacheName = "Toy")
public interface ToyRepository extends IgniteRepository<ToyModel, String> {
}
