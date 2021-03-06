package com.gaoshu.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Title: KaptchaConfig
 * @Description: 验证码注入
 * @author: gaoshu
 * @date: 2021/12/1 14:51
 */
@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha getDefaultKaptcha(){
        DefaultKaptcha captchaProducer = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "yes");// 图片边框
        properties.setProperty("kaptcha.border.color", "20,15,90");// 边框颜色
        properties.setProperty("kaptcha.textproducer.font.color", "blue"); // 字体颜色
        properties.setProperty("kaptcha.image.width", "110");// 图片宽
        properties.setProperty("kaptcha.image.height", "40");// 图片高
        properties.setProperty("kaptcha.textproducer.font.size", "30");// 字体大小
        properties.setProperty("kaptcha.session.key", "code");// session key
        properties.setProperty("kaptcha.textproducer.char.length", "5");// 验证码长度
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");// 字体
        Config config = new Config(properties);
        captchaProducer.setConfig(config);
        return captchaProducer;
    }

}
