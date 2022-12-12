package top.strelitzia.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @author strelitzia
 * @Date 2022/11/20 17:01
 **/
@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource.angelinadatasource")
public class AngelinaDataSourceProperties {

    private String url;
    private String username;
    private String password;
    private String driverClassName;
}