package top.strelitzia.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public CachingConnectionFactory getConnectionFactory() {
        ConnectionFactory rabbitConnectionFactory = new ConnectionFactory();
        rabbitConnectionFactory.setHost("175.24.31.205");
        rabbitConnectionFactory.setPort(5672);
        rabbitConnectionFactory.setUsername("admin");
        rabbitConnectionFactory.setPassword("123456");
        rabbitConnectionFactory.setAutomaticRecoveryEnabled(true);
        rabbitConnectionFactory.setNetworkRecoveryInterval(5000);
        return new CachingConnectionFactory(rabbitConnectionFactory);
    }

    @Bean
    public Queue queue1() {
        return new Queue("queue1",true);
    }

    @Bean
    public FanoutExchange DataVersion() {
        return new FanoutExchange("DataVersion",true, false);
    }

    @Bean
    public FanoutExchange PoolData() {
        return new FanoutExchange("PoolData",true, false);
    }

    @Bean
    public FanoutExchange NickName() {
        return new FanoutExchange("NickName",true, false);
    }

    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(queue1()).to(DataVersion());
    }

    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(queue1()).to(PoolData());
    }

    @Bean
    public Binding binding3() {
        return BindingBuilder.bind(queue1()).to(NickName());
    }
}
