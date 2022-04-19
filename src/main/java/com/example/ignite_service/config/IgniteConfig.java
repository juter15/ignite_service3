package com.example.ignite_service.config;

import com.example.ignite_service.AbcCache;
import com.example.ignite_service.Test2Cache;
import com.example.ignite_service.TestCache;
import com.example.ignite_service.UserCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteClientDisconnectedException;
import org.apache.ignite.IgniteEvents;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.DeploymentMode;
import org.apache.ignite.events.Event;
import org.apache.ignite.lang.IgnitePredicate;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.apache.ignite.springdata22.repository.config.EnableIgniteRepositories;
import org.apache.ignite.springdata22.repository.config.RepositoryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import java.util.Arrays;

@Configuration
@Slf4j
@EnableIgniteRepositories(basePackages = "com.example.ignite_service", includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = RepositoryConfig.class))
public class IgniteConfig {

    @Bean
    public Ignite igniteInstance() {
        //System.setProperty("IGNITE_JETTY_PORT","8888");
        org.apache.ignite.configuration.IgniteConfiguration cfg = new org.apache.ignite.configuration.IgniteConfiguration()
                // Enabling peer-class loading feature.
                .setPeerClassLoadingEnabled(true)
                .setDeploymentMode(DeploymentMode.CONTINUOUS)
                /*클라이언트 노드 설정*/
                .setClientMode(true)
                ;

        // Discovery SPI

        TcpDiscoverySpi spi = new TcpDiscoverySpi();

        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        // Set initial IP addresses.
        // Note that you can optionally specify a port or a port range.
        //ipFinder.setAddresses(Arrays.asList("localhost", "localhost:47500..47509"));
        //spi.setIpFinder(ipFinder);

        TcpDiscoveryMulticastIpFinder tcpDiscoveryMulticastIpFinder = new TcpDiscoveryMulticastIpFinder();
        tcpDiscoveryMulticastIpFinder.setAddresses(Arrays.asList("localhost", "localhost:47500..47509","192.168.0.109:47500..47509","192.168.0.111:47500..47509","192.168.0.112:47500..47509"));
        //tcpDiscoveryMulticastIpFinder.set
        //tcpDiscoveryMulticastIpFinder.setMulticastGroup(Arrays.asList("192.168.0.109:47500..47509","192.168.0.111:47500..47509","192.168.0.112:47500..47509"));
        spi.setIpFinder(tcpDiscoveryMulticastIpFinder);
        //spi.setReconnectDelay(500);


        // Override default discovery SPI.
        cfg.setDiscoverySpi(spi);

        Ignite ignite = Ignition.start(cfg);

        IgniteEvents events = ignite.events();

        //events.
        //Client Node 재연결
//        try {
//            cache.put(1, "value");
//        } catch (IgniteClientDisconnectedException e) {
//            if (e.getCause() instanceof IgniteClientDisconnectedException) {
//                IgniteClientDisconnectedException cause = (IgniteClientDisconnectedException) e.getCause();
//
//                cause.reconnectFuture().get();
//            }
//        }
        // Cache configuration
        CacheConfiguration<Long, UserCache> userCache = new CacheConfiguration<Long, UserCache>("userCache")
                .setCacheMode(CacheMode.PARTITIONED)
                .setBackups(1)

                .setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL)
                .setWriteSynchronizationMode(CacheWriteSynchronizationMode.PRIMARY_SYNC)
                .setIndexedTypes(Long.class, UserCache.class);
        //파티션 인식 확성화
        //        .setPartitionAwarenessEnabled

        // Cache configuration
        CacheConfiguration<Long, TestCache> testCache = new CacheConfiguration<Long, TestCache>("testCache")
                .setCacheMode(CacheMode.PARTITIONED)
                .setBackups(1)
                .setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL)
                .setWriteSynchronizationMode(CacheWriteSynchronizationMode.PRIMARY_SYNC)
                .setIndexedTypes(Long.class, TestCache.class);

        CacheConfiguration<Long, Test2Cache> test2Cache = new CacheConfiguration<Long, Test2Cache>("test2Cache")
                .setCacheMode(CacheMode.PARTITIONED)
                .setBackups(1)
                .setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL)
                .setWriteSynchronizationMode(CacheWriteSynchronizationMode.PRIMARY_SYNC)
                .setIndexedTypes(Long.class, Test2Cache.class);

        CacheConfiguration<String, AbcCache> abcCache = new CacheConfiguration<String, AbcCache>("PUBLIC")
                .setCacheMode(CacheMode.PARTITIONED)
                .setBackups(1)
                .setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL)
                .setWriteSynchronizationMode(CacheWriteSynchronizationMode.PRIMARY_SYNC)
                .setIndexedTypes(String.class, AbcCache.class);
        //ignite.getOrCreateCaches(Arrays.asList(userCache,testCache,test2Cache)); //캐시 생성. 여러개 추가 가능성

        CacheConfiguration<String, AbcCache> abcCache2 = new CacheConfiguration<String, AbcCache>("ABC")
                .setCacheMode(CacheMode.PARTITIONED)
                .setBackups(1)
                .setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL)
                .setWriteSynchronizationMode(CacheWriteSynchronizationMode.PRIMARY_SYNC)
                .setIndexedTypes(String.class, AbcCache.class);
        //ignite.getOrCreateCaches(Arrays.asList(userCache,testCache,test2Cache)); //캐시 생성. 여러개 추가 가능성
        ignite.getOrCreateCaches(Arrays.asList(userCache,testCache,test2Cache,abcCache,abcCache2)); //캐시 생성. 여러개 추가 가능성

        return ignite;
    }

}
