package com.example.ignite_service.config;

import com.example.ignite_service.TestModel;
import com.example.ignite_service.UserModel;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.DeploymentMode;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.apache.ignite.springdata22.repository.config.EnableIgniteRepositories;
import org.apache.ignite.springdata22.repository.config.RepositoryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import java.util.Arrays;

@Configuration
@EnableIgniteRepositories(basePackages = "com.example.ignite_service", includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = RepositoryConfig.class))
public class IgniteConfig {

    @Bean
    public Ignite igniteInstance() {
        org.apache.ignite.configuration.IgniteConfiguration cfg = new org.apache.ignite.configuration.IgniteConfiguration()
                // Enabling peer-class loading feature.
                .setPeerClassLoadingEnabled(true)
                .setDeploymentMode(DeploymentMode.CONTINUOUS)
                /*클라이언트 노드 설정*/
                .setClientMode(true);

        // Discovery SPI

        TcpDiscoverySpi spi = new TcpDiscoverySpi();
        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();

        // Set initial IP addresses.
        // Note that you can optionally specify a port or a port range.
        // Default port to listen (value is <tt>47500</tt>).
        ipFinder.setAddresses(Arrays.asList("localhost", "localhost:47500..47509"));
        spi.setIpFinder(ipFinder);

        // Override default discovery SPI.
        cfg.setDiscoverySpi(spi);


        Ignite ignite = Ignition.start(cfg);

        // Cache configuration
        CacheConfiguration<Long, UserModel> userCache = new CacheConfiguration<Long, UserModel>("User")
                .setCacheMode(CacheMode.PARTITIONED)
                //.setBackups(1)
                .setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL)
                .setWriteSynchronizationMode(CacheWriteSynchronizationMode.PRIMARY_SYNC)
                .setIndexedTypes(Long.class, UserModel.class);

        // Cache configuration
        CacheConfiguration<Long, TestModel> testCache = new CacheConfiguration<Long, TestModel>("test1")
                .setCacheMode(CacheMode.REPLICATED)
                //.setBackups(1)
                .setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL)
                .setWriteSynchronizationMode(CacheWriteSynchronizationMode.PRIMARY_SYNC)
                .setIndexedTypes(Long.class, TestModel.class);

        ignite.getOrCreateCaches(Arrays.asList(userCache,testCache)); //캐시 생성. 여러개 추가 가능성
        return ignite;
    }

}
