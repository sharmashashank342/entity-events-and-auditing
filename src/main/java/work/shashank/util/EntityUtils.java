package work.shashank.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

public class EntityUtils {

    private static final Logger log = LoggerFactory.getLogger(EntityUtils.class);

    private EntityUtils() {
        // No Instance for Utils
    }

    // Thread Safe class thus static
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String getAnnotatedFieldValue(Object object, Class<? extends Annotation> annotationClass) {

        try {
            for (Field field: object.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(annotationClass)) {
                    field.setAccessible(true);
                    return field.get(object).toString();
                }
            }
        }catch (Exception e) {
            // Ignored
        }

        return null;
    }

    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("Exception occured while converting Object toJson", e);
        }
        return null;
    }

    public static Map<String, Object> convertToMap(Object object) {
        return mapper.convertValue(object, new TypeReference<Map<String, Object>>() {});
    }

    public static <T> T convertValue(Map<String, Object> properties, Class<T> tClass) {
        return mapper.convertValue(properties, tClass);
    }
}
