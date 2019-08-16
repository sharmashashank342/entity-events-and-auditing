package work.shashank.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static work.shashank.util.EntityUtils.toJson;

public class EntityUtilsTest {

    @Test
    public void test_getAnnotatedFieldValue() {

        assertThat(EntityUtils.getAnnotatedFieldValue(new EntityTest(), Deprecated.class)).isNull();
        assertThat(EntityUtils.getAnnotatedFieldValue(new EntityTest(), Id.class)).isEqualTo("id");
        assertThat(EntityUtils.getAnnotatedFieldValue(new EntityTest(), org.springframework.data.annotation.Id.class)).isEqualTo("1");
        assertThat(EntityUtils.getAnnotatedFieldValue(new EntityTestChild(), Id.class)).isEqualTo("{\"id\":\"id\",\"id2\":\"id2\"}");
        assertThat(EntityUtils.getAnnotatedFieldValue(new Object(), Id.class)).isNull();
        assertThat(EntityUtils.getAnnotatedFieldValue(null, Id.class)).isNull();
    }

    @Test
    public void test_toJson() throws Exception {

        EntityTest entityTest = new EntityTest();

        String json = toJson(entityTest);

        EntityTest entityTestNew = new ObjectMapper().readValue(json, EntityTest.class);

        assertThat(entityTest).isEqualTo(entityTestNew);
        assertNotSame(entityTest, entityTestNew);

        setFinalStaticField(EntityUtils.class, "mapper", new ObjectMapper());
        assertThat(toJson(new ClassThatJacksonCannotSerialize())).isNull();
    }

    @Test
    public void test_convertToMap() throws Exception {

        Map<String, Object> map = EntityUtils.convertToMap(new EntityTest());

        assertEquals(map.get("id"), "id");
        assertEquals(map.get("intId"), 1);
        assertThat(map.get("unknown")).isNull();
    }

    @Test
    public void test_convertValue() {

        EntityTest entityTest = new EntityTest();
        Map<String, Object> map = EntityUtils.convertToMap(new EntityTest());
        EntityTestDTO entityTestDTO = EntityUtils.convertValue(map, EntityTestDTO.class);

        assertThat(entityTest.id).isEqualTo(entityTestDTO.id);
        assertThat(entityTest.intId).isEqualTo(entityTestDTO.intId);
    }

    private static class EntityTest {

        @Id
        public String id = "id";

        @org.springframework.data.annotation.Id
        public Integer intId = 1;

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof EntityTest)) {
                return false;
            }
            EntityTest that = (EntityTest) object;
            return Objects.equals(id, that.id) &&
                    Objects.equals(intId, that.intId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, intId);
        }
    }

    public static class EntityTestChild {

        @Id
        public String id = "id";

        @org.springframework.data.annotation.Id
        public Integer intId = 1;

        @Id
        public String id2 = "id2";

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof EntityTestChild)) {
                return false;
            }
            EntityTestChild that = (EntityTestChild) object;
            return Objects.equals(id, that.id) &&
                    Objects.equals(intId, that.intId) &&
                    Objects.equals(id2, that.id2);
        }

        @Override public int hashCode() {
            return Objects.hash(id, intId, id2);
        }
    }

    private static class EntityTestDTO {

        public String id = "id";

        public Integer intId = 1;
    }

    private static class ClassThatJacksonCannotSerialize {
        private final ClassThatJacksonCannotSerialize self = this;

        @Override
        public String toString() {
            return self.getClass().getName();
        }
    }

    private static void setFinalStaticField(Class<?> clazz, String fieldName, Object value)
            throws ReflectiveOperationException {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, value);
    }
}
