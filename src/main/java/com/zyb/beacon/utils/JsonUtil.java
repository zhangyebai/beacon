package com.zyb.beacon.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * javadoc JsonUtil
 * 为干掉fast json做准备
 *
 * @author zhang yebai
 * @version 1.0.0
 * @date 2020/6/12 17:08
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class JsonUtil {

    /**
     * thread safe instance( by thread-local )
     **/
    private static final ObjectMapper JSON = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    /**
      *  javadoc toJsonString
      *  @apiNote obj to json
     *            without exception
     *            null returns on exception
      *
      *  @param  obj instance of any object
      *  @return  java.lang.String
      *  @author zhang yebai
      *  @date 2020/6/12 17:24
      */
    public static String toJsonString(Object obj) {
        try {
            return toJsonStringOnEx(obj);
        } catch (JsonProcessingException ex) {
            log.error("jackson toJson({}) error: ", obj, ex);
        }
        return null;
    }

    /**
      *  javadoc toJsonStringOnEx
      *  @apiNote obj to json
     *            fail fast on exception
      *
      *  @param  obj any instance of object
      *  @return  java.lang.String
      *  @author zhang yebai
      *  @date 2020/6/12 17:26
      */
    public static String toJsonStringOnEx(Object obj) throws JsonProcessingException {
        return JSON.writeValueAsString(obj);
    }

    /**
      *  javadoc parseObject
      *  @apiNote json to obj
      *
      *  @param  json text of json
      *  @param  clazz class type
      *  @return  T simple type T
      *  @author zhang yebai
      *  @date 2020/6/12 17:27
      */
    public static <T> T parseObject(String json, Class<T> clazz){
        try {
            return parseObjectOnEx(json, clazz);
        } catch (JsonProcessingException ex) {
            log.error("jackson parseObject({}, {}) error: ", json, clazz, ex);
        }
        return null;
    }

    /**
      *  javadoc parseObjectOnEx
      *  @apiNote json to obj
      *           fail fast on exception
      *
      *  @param  json text of json
      *  @param  clazz class type
      *  @return  T
      *  @author zhang yebai
      *  @date 2020/6/12 17:27
      */
    public static <T> T parseObjectOnEx(String json, Class<T> clazz) throws JsonProcessingException {
        return JSON.readValue(json, clazz);
    }

    /**
      *  javadoc parseObjectOn
      *  @apiNote json to obj with type feature
      *
      *  @param  json text json
      *  @param  reference type feature
      *  @return  T
      *  @author zhang yebai
      *  @date 2020/6/12 17:29
      */
    public static <T> T parseObject(String json, TypeReference<T> reference) {
        try {
            return parseObjectOnEx(json, reference);
        } catch (JsonProcessingException ex) {
            log.error("jackson parseObjectWithFeature({}, {}) error: ", json, reference, ex);
        }
        return null;
    }

    /**
     *  javadoc parseObjectOnEx
     *  @apiNote json to obj with type feature
     *           fail fast on exception
     *
     *  @param  json text json
     *  @param  reference type feature
     *  @return  T
     *  @author zhang yebai
     *  @date 2020/6/12 17:29
     */
    public static <T> T parseObjectOnEx(String json, TypeReference<T> reference) throws JsonProcessingException {
        return JSON.readValue(json, reference);
    }

}
