package com.zlzy.sbv.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class SpringRedisCache {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 保存缓存的数据
     * @param key 缓存的键
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value){
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 保存缓存的数据
     * @param key 缓存的键
     * @param value 缓存的值
     * @param times 过期时间
     * @param timeUnit 时间单位
     */
    public <T> void setCacheObject(final String key, final T value, final long times, final TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key, value, times, timeUnit);
    }

    /**
     * 设置有效时间
     * @param key 键
     * @param timeOut 时间(秒)
     * @return true 设置成功, false 设置失败
     */
    public boolean expire(final String key, final long timeOut){
        return expire(key, timeOut, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     * @param key 键
     * @param timeOut 时间
     * @param timeUnit 单位
     * @return true设置成功, false设置失败
     */
    public boolean expire(String key, long timeOut, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeOut, timeUnit);
    }

    /**
     * 获得缓存的值
     * @param key 键
     * @param <T> 类型
     * @return 值
     */
    public <T> T getCacheObject(final String key){
        ValueOperations<String, T> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    /**
     * 删除单个对象
     * @param key 键
     * @return true成功,false失败
     */
    public boolean deleteObject(final String key){
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     * @param keys 键的集合
     * @return 删除成功的数量
     */
    public long deleteCacheObject(final Collection keys){
        return redisTemplate.delete(keys);
    }

    /**
     * 换成List数据
     * @param key 键
     * @param datas
     * @return 缓存成功的数量
     */
    public <T> long setCacheObject(final String key, final List<T> datas){
        Long count = redisTemplate.opsForList().rightPushAll(key, datas);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list
     * @param key
     * @return
     */
    public <T> List<T> getCacheList(final String key){
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存set
     * @param key 键
     * @param dataSet 值
     * @return 缓存的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet){
        BoundSetOperations<String, T> operations = redisTemplate.boundSetOps(key);
        Iterator<T> iterator = dataSet.iterator();
        while (iterator.hasNext()){
            operations.add(iterator.next());
        }
        return operations;
    }

    /**
     * 获得缓存的set
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(final String key){
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存map
     * @param key
     * @param dataMap
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap){
        if (Objects.nonNull(dataMap)){
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的map
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(final String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往hash中存入数据
     * @param key redis键
     * @param mKey hashmap键
     * @param value hashmap值
     */
    public <T> void setCacheMapValue(final String key, final String mKey, final T value){
        redisTemplate.opsForHash().put(key, mKey, value);
    }

    /**
     * 获得hash中的值
     * @param key redis键
     * @param mKey hash键
     */
    public <T> T getCacheMapValue(final String key, final String mKey){
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(key, mKey);
    }

    /**
     * 删除hash中的数据
     * @param key redis键
     * @param mKey hash键
     */
    public void deleteCacheMapValue(final String key,final String mKey){
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, mKey);
    }

    /**
     * 获得hash中的部分数据
     * @param key redis键
     * @param mkeys hash键集合
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<String> mkeys){
        return redisTemplate.opsForHash().multiGet(key, mkeys);
    }

    /**
     * 获得缓存的基本对象列表
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern){
        return redisTemplate.keys(pattern);
    }
}
