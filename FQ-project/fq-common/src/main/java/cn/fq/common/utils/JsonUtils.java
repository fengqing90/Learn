package cn.fq.common.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.fq.common.bean.entity.SysUser;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/5/11 16:29
 */
@Slf4j
public class JsonUtils {

    public static final ObjectMapper mapper = new ObjectMapper();

    //json化
    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return (String) obj;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("json序列化出错：" + obj, e);
            return null;
        }
    }

    //json解析
    public static <T> T toBean(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

    public static void main(String[] args) {
        try {
            String str = "{\"id\":1,\"username\":\"a\",\"password\":null,\"status\":1,\"roles\":[{\"id\":1,\"roleName\":\"ROLE_ADMIN\",\"roleDesc\":\"ROLE_ADMIN\"},{\"id\":2,\"roleName\":\"ROLE_FQ\",\"roleDesc\":\"ROLE_FQ\"},{\"id\":3,\"roleName\":\"ROLE_USER\",\"roleDesc\":\"ROLE_USER\"}],\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"accountNonExpired\":true,\"enabled\":true,\"authorities\":[{\"id\":1,\"roleName\":\"ROLE_ADMIN\",\"roleDesc\":\"ROLE_ADMIN\"},{\"id\":2,\"roleName\":\"ROLE_FQ\",\"roleDesc\":\"ROLE_FQ\"},{\"id\":3,\"roleName\":\"ROLE_USER\",\"roleDesc\":\"ROLE_USER\"}]}";
            System.out.println(str);
            SysUser sysUser = toBean(str, SysUser.class);
            System.out.println(sysUser);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //解析list的json数据
    public static <E> List<E> toList(String json, Class<E> eClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory()
                .constructCollectionType(List.class, eClass));
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

    //json转map
    public static <K, V> Map<K, V> toMap(String json, Class<K> kClass,
            Class<V> vClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory()
                .constructMapType(Map.class, kClass, vClass));
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

    //json解析自定义类型
    public static <T> T nativeRead(String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }
}
