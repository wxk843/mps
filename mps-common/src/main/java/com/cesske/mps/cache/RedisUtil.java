package com.cesske.mps.cache;

import com.cesske.mps.utils.TimeUtil;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis操作
 *
 * @author util
 */
@Slf4j
@Order(1)
@Component
public class RedisUtil<T> {
    public static final String ORDER_NO = "order_no";
    public static final String TRADE_NO = "trade_no";
    static final int DEFAULT_LENGTH = 7;
    static final int ORIGIN = 10000;
    static final int BOUND = 99999;
//    private static final ThreadLocalRandom random = ThreadLocalRandom.current();

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void delete(final String... keys) {
        for (String key : keys) {
            delete(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void deletePattern(final String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        if (!keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void delete(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 通过正则获取key
     *
     * @param pattern
     * @return
     */
    public Set keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public T get(final String key) {
        T result;
        ValueOperations<String, T> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, T value) {
        boolean result = false;
        try {
            ValueOperations<String, T> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            log.error("redis set is error", e);
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, T value, long expireTime) {
        boolean result = false;
        try {
            ValueOperations<String, T> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            log.error("redis set is error", e);
        }
        return result;
    }

    /**
     * 计数并设置失效时间
     *
     * @param key
     * @param expireTime
     * @return
     */
    public long incr(String key, long expireTime) {
        long seq = redisTemplate.boundValueOps(key).increment(1);
        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        return seq;
    }

    /**
     * 返回时效时间
     *
     * @param key
     * @return
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 订单号生成
     *
     * @return
     */
    public String getOrderNo() {
        String date = TimeUtil.formatDate(new Date(), TimeUtil.YYMMDDHHMMSS);
        return Joiner.on("").join(date, getSequence(getSeq(date, ORDER_NO), DEFAULT_LENGTH), String.format("%04d", getIntVal()));
    }

    /**
     * 特殊订单号生成
     *
     * @return
     */
    public String getSpeOrderNo(String prefix) {
        String date = TimeUtil.formatDate(new Date(), TimeUtil.YYMMDDHHMMSS);
        String orderno =  Joiner.on("").join(date, getSequence(getSeq(date, ORDER_NO), DEFAULT_LENGTH), String.format("%04d", getIntVal()));
        return prefix + orderno;
    }

    /**
     * 交易流水号生成
     *
     * @return
     */
    public String getTradeNo() {
        String date = TimeUtil.formatDate(new Date(), TimeUtil.YYMMDDHHMMSS);
        String tradeno = Joiner.on("").join(date, getSequence(getSeq(date, TRADE_NO), DEFAULT_LENGTH), String.format("%04d", getIntVal()));
        return "PE" + tradeno;
    }

    public long getSeq(String str, String type) {
        String orderNo = String.join(type, str);
        long seq = redisTemplate.boundValueOps(orderNo).increment(1);
        return seq;
    }

    /**
     * 得到10位的序列号,长度不足10位,前面补0
     *
     * @param seq
     * @return
     */
    public static String getSequence(long seq, int length) {
        String str = String.valueOf(seq);
        int len = str.length();
        if (len >= length) {// 取决于业务规模,应该不会到达10
            return str;
        }
        int rest = length - len;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rest; i++) {
            sb.append('0');
        }
        sb.append(str);
        return sb.toString();
    }

    /**
     * 随机5位数字
     *
     * @return
     */
    public static int getIntVal() {
        Random random = new Random();
        return random.nextInt(BOUND-ORIGIN+1)+ORIGIN;
    }

    /**
     * 队列入值，左进
     *
     * @param key
     * @param value
     * @return
     */
    public long leftPush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 队列出值，左出
     *
     * @param key
     * @return
     */
    public Object leftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 一次获取所有队列的值，并删除
     *
     * @param key
     * @return
     */
    public List<Object> popListAll(String key) {
        List<Object> list = new ArrayList<Object>();
        while (!redisTemplate.opsForList().range(key, 0, -1).isEmpty()) {
            list.add(leftPop(key));
        }
        return list;
    }

    /**
     * 一次获取所有队列的值，不删除
     *
     * @param key
     * @return
     */
    public List<Object> popListAllNoDel(String key) {
        List<Object> list = new ArrayList<Object>();
        while (!redisTemplate.opsForList().range(key, 0, -1).isEmpty()) {
            list.add(redisTemplate.opsForList().range(key, 0, -1));
        }
        return list;
    }

    /**
     * 存储hash
     *
     * @param key
     * @param hashKey
     * @param hashVal
     */
    public void addHash(String key, String hashKey, String hashVal) {
        redisTemplate.opsForHash().put(key, hashKey, hashVal);
    }

    /**
     * 获取hash
     * @param key
     * @return
     */
    public Map<Object, Object> getHash(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public Long delHash(String key, String hashKey){
        return redisTemplate.opsForHash().delete(key,hashKey);
    }
}
