package work.shashank.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import javax.persistence.Id;

import java.util.Map;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class EntityUtilsTest {

    @Test
    public void test_getAnnotatedFieldValue() {

        assertThat(EntityUtils.getAnnotatedFieldValue(new EntityTest(), Deprecated.class), is(nullValue()));
        assertThat(EntityUtils.getAnnotatedFieldValue(new EntityTest(), Id.class), is("id"));
        assertThat(EntityUtils.getAnnotatedFieldValue(new EntityTest(), org.springframework.data.annotation.Id.class), is("1"));
    }

    @Test
    public void test_toJson() throws Exception {

        EntityTest entityTest = new EntityTest();

        String json = EntityUtils.toJson(entityTest);

        EntityTest entityTestNew = new ObjectMapper().readValue(json, EntityTest.class);

        assertEquals(entityTest, entityTestNew);
        assertNotSame(entityTest, entityTestNew);
    }

    @Test
    public void test_convertToMap() throws Exception {

        Map<String, Object> map = EntityUtils.convertToMap(new EntityTest());

        assertEquals(map.get("id"), "id");
        assertEquals(map.get("intId"), 1);
        assertNull(map.get("unknown"));
    }

    @Test
    public void test_convertValue() throws Exception {

        EntityTest entityTest = new EntityTest();
        Map<String, Object> map = EntityUtils.convertToMap(new EntityTest());
        EntityTestDTO entityTestDTO = EntityUtils.convertValue(map, EntityTestDTO.class);

        assertEquals(entityTest.id, entityTestDTO.id);
        assertEquals(entityTest.intId, entityTestDTO.intId);
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

    private static class EntityTestDTO {

        public String id = "id";

        public Integer intId = 1;
    }
}
