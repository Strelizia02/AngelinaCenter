package top.strelitzia.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import top.strelitzia.config.properties.RedisPoolProperties;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class RedisPoolConfig {
    @Autowired
    private RedisPoolProperties redisPoolProperties;

    /**
     * 连接池的基本配置
     *
     * @return JedisPoolConfig
     */
    public JedisPoolConfig initPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 设置最大连接数，默认值为8.如果赋值为-1，则表示不限制；
        poolConfig.setMaxTotal(redisPoolProperties.getMaxTotal());
        // 最大空闲连接数
        poolConfig.setMaxIdle(redisPoolProperties.getMaxIdle());
        // 最小空闲连接数
        poolConfig.setMinIdle(redisPoolProperties.getMinIdle());
        // 获取Jedis连接的最大等待时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
        poolConfig.setMaxWaitMillis(redisPoolProperties.getMaxWaitMillis());
        // 每次释放连接的最大数目
        poolConfig.setNumTestsPerEvictionRun(redisPoolProperties.getNumTestsPerEvictionRun());
        // 释放连接的扫描间隔（毫秒）,如果为负数,则不运行逐出线程, 默认-1
        poolConfig.setTimeBetweenEvictionRunsMillis(redisPoolProperties.getTimeBetweenEvictionRunsMillis());
        // 连接最小空闲时间
        poolConfig.setMinEvictableIdleTimeMillis(redisPoolProperties.getMinEvictableIdleTimeMillis());
        // 连接空闲多久后释放, 当空闲时间&gt;该值 且 空闲连接&gt;最大空闲连接数 时直接释放
        poolConfig.setSoftMinEvictableIdleTimeMillis(redisPoolProperties.getSoftMinEvictableIdleTimeMillis());
        // 在获取Jedis连接时，自动检验连接是否可用
        poolConfig.setTestOnBorrow(redisPoolProperties.isTestOnBorrow());
        // 在将连接放回池中前，自动检验连接是否有效
        poolConfig.setTestOnReturn(redisPoolProperties.isTestOnReturn());
        // 自动测试池中的空闲连接是否都是可用连接
        poolConfig.setTestWhileIdle(redisPoolProperties.isTestWhileIdle());
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        poolConfig.setBlockWhenExhausted(redisPoolProperties.isBlockWhenExhausted());
        // 是否启用pool的jmx管理功能, 默认true
        poolConfig.setJmxEnabled(redisPoolProperties.isJmxEnabled());
        // 是否启用后进先出, 默认true
        poolConfig.setLifo(redisPoolProperties.isLifo());
        // 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        poolConfig.setNumTestsPerEvictionRun(redisPoolProperties.getNumTestsPerEvictionRun());
        return poolConfig;
    }

    /**
     * 创建JedisCluster客户端， 将redis客户端放到容器中； 连接redis集群
     * Jedis 连接集群
     *
     * @return JedisCluster  使用完成后不需要手动释放连接，返回客户端， 使用完成后不需要手动释放连接， 客户端会自动释放连接
     */
    @Bean
    @Qualifier("jedisCluster")
    public JedisCluster getJedisCluster() {
        return new JedisCluster(getSet(), initPoolConfig());
    }


    /**
     * 获取集群对象集合
     * @return Set
     */
    public Set<HostAndPort> getSet() {
        String hostAndPortStr = redisPoolProperties.getHostAndPort();
        String[] hostAndPortArrays = hostAndPortStr.trim().split(",");
        Set<HostAndPort> hostAndPorts = new HashSet<>();
        for (String hostAndPort : hostAndPortArrays) {
            hostAndPorts.add(new HostAndPort(hostAndPort.split(":")[0], Integer.parseInt(hostAndPort.split(":")[1])));
        }
        return hostAndPorts;
    }
}
