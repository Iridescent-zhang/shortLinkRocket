/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nageoffer.shortlink.admin.config;

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

//@Configuration(value = "adminRedisConfig")
public class RedisConfig {

    private static final String KEY_PREFIX = "strumcode:";

    @Primary
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
