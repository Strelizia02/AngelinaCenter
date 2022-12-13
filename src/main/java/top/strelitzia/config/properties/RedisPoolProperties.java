package top.strelitzia.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "redis-config.pool")
public class RedisPoolProperties {
    private String hostAndPort;

    private int maxTotal;

    private int maxIdle;

    private int minIdle;

    private Long maxWaitMillis;

    private Long timeBetweenEvictionRunsMillis;

    private Long minEvictableIdleTimeMillis;

    private Long softMinEvictableIdleTimeMillis;

    private boolean testOnBorrow;

    private boolean testOnReturn;

    private boolean testWhileIdle;

    private boolean blockWhenExhausted;

    private boolean jmxEnabled;

    private boolean lifo;

    private int numTestsPerEvictionRun;
}
