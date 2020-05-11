package com.cesske.mps.utils;

//import cn.dsmc.ecp.config.ScrmConfig;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

/**
 * 业务唯一标识，格式：
 * [platformId]-[activity]-[消息产生timestamp(13位)+五位随机数]
 */
@Data
@Component
public class TraceId {

    //@Autowired
    //private ScrmConfig scrmConfig;

    private final SecureRandom RANDOM = new SecureRandom();

    public String get(String activity) {
        return String.format("%s-%s-%d%05d","mps", activity, System.currentTimeMillis(), RANDOM.nextInt(99999));
    }

}
