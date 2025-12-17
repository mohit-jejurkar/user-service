package com.example.user_service.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MapUtils {

    static final  Logger logger = LoggerFactory.getLogger(MapUtils.class);

    /**
     * Restrict to initialize
     */
    private MapUtils(){
    }

    /**
     * Used For core map operation
     * @param innerMap
     * @param key
     * @return
     */
    public static String optString(Map<?,?> innerMap, String key){
        try{
            if(innerMap != null && innerMap.containsKey(key)){
                return innerMap.get(key) != null ? String.valueOf(innerMap.get(key)): null;
            }
        } catch(Exception e){
            logger.info("ERROR   "+e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return "";
    }

    /**
     * @param innerMap
     * @param key
     * @return
     */
    public static long optLong(Map<?,?> innerMap, String key){
        try{
            if(innerMap.containsKey(key)){
                return Long.parseLong(String.valueOf(innerMap.get(key)));
            }
        }catch(Exception e){
            logger.info("ERROR   "+e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return 0l;
    }


    /**
     * @param innerMap
     * @param key
     * @return
     */
    public static Integer optInteger(Map<?,?> innerMap, String key){
        try{
            if(innerMap.containsKey(key)){
                return Integer.parseInt(String.valueOf(innerMap.get(key)));
            }
        }catch(Exception e){
            logger.info("ERROR   "+e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return 0;
    }


    /**
     * @param innerMap
     * @param key
     * @return
     */
    public static Date optDate(Map<?,?> innerMap, String key){
        try{
            if(innerMap.containsKey(key) && innerMap.get(key) instanceof Date){
                return (Date) innerMap.get(key);
            }
        }catch(Exception e){
            logger.info("ERROR   "+e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * Used For core map operation
     * @param innerMap
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Object> optList(Map<?,?> innerMap, String key){
        try{
            if(innerMap.containsKey(key)){
                return (List<Object>) innerMap.get(key);
            }
        }catch(Exception e){
            logger.info("ERROR   "+e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    /**
     * Used For core map operation
     * @param innerMap
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String,Object>> optListMap(Map<?,?> innerMap, String key){
        try{
            if(innerMap.containsKey(key)){
                if(innerMap.get(key) instanceof Map) {
                    List<Map<String, Object>> list = new ArrayList<>();
                    list.add((Map<String,Object>) innerMap.get(key));
                    return list;
                } else {
                    return (List<Map<String,Object>>) innerMap.get(key);
                }
            }
        }catch(Exception e){
            logger.info("ERROR   "+e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    /**
     * Used For core map operation
     * @param innerMap
     * @param key
     * @return
     */
    public static Object optObject(Map<?,?> innerMap, String key){
        try{
            if(null!= innerMap && innerMap.containsKey(key)){
                return innerMap.get(key);
            }
        }catch(Exception e){
            logger.info("ERROR   "+e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Used For core map operation
     * @param innerMap
     * @param key
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map<String,Object> optMap(Map<?,?> innerMap, String key){
        try{
            if(innerMap != null && innerMap.containsKey(key) && innerMap.get(key) instanceof Map){
                return (Map<String,Object>) innerMap.get(key);
            } else if(innerMap != null && innerMap.containsKey(key) && innerMap.get(key) instanceof ObjectNode){
                return new ObjectMapper().convertValue(innerMap.get(key), HashMap.class);
            }
        }catch(Exception e){
            logger.info("ERROR   "+e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return new HashMap();
    }


    public static Boolean optBoolean(Map<String, Object> innerMap, String key) {
        try {
            if(innerMap.containsKey(key) && innerMap.get(key) instanceof Boolean){
                return (Boolean) innerMap.get(key);
            }
        }catch (Exception e) {
            logger.info("ERROR   "+e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return false;
    }

}
