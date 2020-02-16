package com.leekwok.gateway.config;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * <b>Author</b>: Hsiang Leekwok<br/>
 * <b>Date</b>: 2020/02/16 15:03<br/>
 * <b>Version</b>: v1.0<br/>
 * <b>Subject</b>: <br/>
 * <b>Description</b>: 路由谓词工厂必须以 RoutePredicateFactory 结尾
 * 1. 读取配置文件里面的配置，并注入到 config 参数里面
 * 2. 判断当前时间是否满足要求
 */
@Component
public class TimeBetweenRoutePredicateFactory extends AbstractRoutePredicateFactory<TimeConfig> {

    public TimeBetweenRoutePredicateFactory() {
        super(TimeConfig.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(TimeConfig config) {
        LocalTime startTime = config.getStartTime();
        LocalTime endTime = config.getEndTime();
        LocalTime now=LocalTime.now();
        return serverWebExchange -> now.isAfter(startTime) && now.isBefore(endTime);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        // 有顺序
        return Arrays.asList("startTime", "endTime");
    }

    public static void main(String[] args) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        String format = dateTimeFormatter.format(LocalTime.now());
        System.out.println(format);
    }
}
