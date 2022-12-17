package top.strelitzia.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import top.strelitzia.config.properties.AngelinaDataSourceProperties;

import javax.sql.DataSource;


/**
 * @author strelitzia
 * @Date 2022/11/20 17:01
 **/
@Configuration
@MapperScan(basePackages = "top.strelitzia.dao",
        sqlSessionTemplateRef = "angelinaSqlSessionTemplate")
@Slf4j
public class AngelinaDataSourceConfig {

    @Autowired
    private AngelinaDataSourceProperties prop;

    /**
     * 创建数据源
     */
    @Bean(name = "angelinaDataSource")
    public DataSource getFirstDataSource() {
        return DataSourceBuilder.create()
                .driverClassName(prop.getDriverClassName())
                .url(prop.getUrl())
                .username(prop.getUsername())
                .password(prop.getPassword())
                .build();
    }

    @Bean(name = "angelinaSqlSessionFactory")
    public SqlSessionFactory angelinaSqlSessionFactory(
            @Qualifier("angelinaDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapping/*.xml"));
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean("angelinaTransactionManger")
    public DataSourceTransactionManager angelinaTransactionManger(
            @Qualifier("angelinaDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    // 创建SqlSessionTemplate
    @Bean(name = "angelinaSqlSessionTemplate")
    public SqlSessionTemplate angelinaSqlSessionTemplate(
            @Qualifier("angelinaSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
