package work.shashank.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import javax.persistence.Id;
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
        assertThat(EntityUtils.getAnnotatedFieldValue(new EntityTestChild(), Id.class)).isEqualTo("{\"id2\":\"id2\",\"id\":\"id\"}");
    }

    @Test
    public void test_toJson() throws Exception {

        EntityTest entityTest = new EntityTest();

        String json = toJson(entityTest);

        EntityTest entityTestNew = new ObjectMapper().readValue(json, EntityTest.class);

        assertThat(entityTest).isEqualTo(entityTestNew);
        assertNotSame(entityTest, entityTestNew);
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

    public static class EntityTestChild extends EntityTest {

        @Id
        public String id2 = "id2";
    }

    private static class EntityTestDTO {

        public String id = "id";

        public Integer intId = 1;
    }
}
