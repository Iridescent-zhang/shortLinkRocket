package com.nageoffer.shortlink.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : lczhang
 * @version : 1.0
 * @Project : shortLinkRocket
 * @Package : com.nageoffer.shortlink.project.config
 * @ClassName : .java
 * @createTime : 2025/1/9 12:49
 * @Email : lczhang93@gmail.com
 * @Website : https://iridescent-zhang.github.io
 * @Description :
 */

@Configuration(value = "gatewayRedisConfig")
public class RedisConfig {

    private static final String KEY_PREFIX = "strumcode:";

//    @Primary
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate(connectionFactory);

        // 设置Key的序列化器以添加前缀
        template.setKeySerializer(new RedisSerializer<String>() {
            private final StringRedisSerializer stringSerializer = new StringRedisSerializer();

            @Override
            public byte[] serialize(String s) {
                return stringSerializer.serialize(KEY_PREFIX + s);
            }

            @Override
            public String deserialize(byte[] bytes) {
                String result = stringSerializer.deserialize(bytes);
                return result != null && result.startsWith(KEY_PREFIX) ? result.substring(KEY_PREFIX.length()) : result;
            }
        });

        // 其他默认序列化器已在StringRedisTemplate中设置

        template.afterPropertiesSet();
        return template;
    }
}
