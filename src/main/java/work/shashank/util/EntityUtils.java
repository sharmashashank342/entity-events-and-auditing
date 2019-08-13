package work.shashank.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility Class
 * @author Shashank Sharma
 */
public class EntityUtils {

    private static final Logger log = LoggerFactory.getLogger(EntityUtils.class);

    private EntityUtils() {
        // No Instance for Utils
    }

    // Thread Safe class thus static
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.addMixIn(Object.class, IgnoreHibernatePropertiesInJackson.class);
    }

    /**
     * @param object Object provided
     * @param annotationClass {@link java.lang.Class} class Object
     * @return String value of property annotated by provided class
     */
    public static String getAnnotatedFieldValue(Object object, Class<? extends Annotation> annotationClass) {

        try {
            Map<String, String> fieldMap = new HashMap<>();
            for (Field field: object.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(annotationClass)) {
                    field.setAccessible(true);
                    fieldMap.put(field.getName(), field.get(object).toString());
                }
            }

            int size = fieldMap.size();

            switch (size) {
                case 0: return null;
                case 1: return fieldMap.entrySet().iterator().next().getValue();
                default: return toJson(fieldMap);
            }
        }catch (Exception e) {
            // Ignored
        }

        return null;
    }

    /**
     * @param object Object provided
     * @see com.fasterxml.jackson.databind.ObjectMapper#writeValueAsString(Object)
     * @return Json String of specified object
     */
    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("Exception occured while converting Object toJson", e);
        }
        return null;
    }

    /**
     * @param object Object provided
     * @see com.fasterxml.jackson.databind.ObjectMapper#convertValue(Object, Class)
     * @return {@link java.util.Map} representation of specified object
     */
    public static Map<String, Object> convertToMap(Object object) {
        Assert.notNull(object, "object cannot be null");
        return mapper.convertValue(object, new TypeReference<Map<String, Object>>() {});
    }

    /**
     * @param properties Properties as {@link java.util.Map}
     * @param tClass {@link java.lang.Class} class Object
     * @param <T> This is the type parameter
     * @see com.fasterxml.jackson.databind.ObjectMapper#convertValue(Object, Class)
     * @return DTO class object of provided {@link java.lang.Class} class Object
     */
    public static <T> T convertValue(Map<String, Object> properties, Class<T> tClass) {
        Assert.notNull(properties, "properties cannot be null");
        Assert.notNull(tClass, "class cannot be null");
        return convertValue((Object) properties, tClass);
    }

    /**
     * @param object Object provided
     * @param tClass {@link java.lang.Class} class Object
     * @param <T> This is the type parameter
     * @see com.fasterxml.jackson.databind.ObjectMapper#convertValue(Object, Class)
     * @return DTO class object of provided {@link java.lang.Class} class Object
     */
    public static <T> T convertValue(Object object, Class<T> tClass) {
        Assert.notNull(object, "object cannot be null");
        Assert.notNull(tClass, "class cannot be null");
        return mapper.convertValue(object, tClass);
    }

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private abstract class IgnoreHibernatePropertiesInJackson {

    }
}
