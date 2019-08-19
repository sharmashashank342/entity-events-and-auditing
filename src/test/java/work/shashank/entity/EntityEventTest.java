package work.shashank.entity;

import org.junit.Test;
import org.springframework.beans.BeanUtils;
import work.shashank.enums.OperationType;
import work.shashank.util.EntityUtils;

import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class EntityEventTest {

    @Test
    public void test_EntityEvent() {

        Audit audit = new Audit();
        Map<String, Object> properties = EntityUtils.convertToMap(audit);

        EntityEvent entityEvent = new EntityEvent();
        entityEvent.setIdField(audit.getId());
        entityEvent.setEntityClass(audit.getClass());
        entityEvent.setOperationType(OperationType.CREATE);
        entityEvent.setProperties(properties);

        assertThat(entityEvent.getIdField()).isEqualTo(audit.getId());
        assertThat(entityEvent.getOperationType()).isEqualTo(OperationType.CREATE);
        assertThat(entityEvent.getEntityClass()).isEqualTo(Audit.class);
        assertThat(entityEvent.getProperties()).isEqualTo(properties);
        assertThat(entityEvent).isNotEqualTo(new EntityEvent());
        assertThat(entityEvent).isNotEqualTo(new Object());
        EntityEvent copy = new EntityEvent();
        BeanUtils.copyProperties(entityEvent, copy);
        assertThat(entityEvent).isEqualTo(copy);
        assertThat(copy).isEqualTo(copy);
        assertThat(entityEvent.toString()).isEqualTo(EntityUtils.toJson(entityEvent));
        assertThat(entityEvent.hashCode())
                .isEqualTo(Objects.hash(OperationType.CREATE, Audit.class, audit.getId(), properties));

        assertThat(audit).isNotEqualTo(new Object());
        assertThat(audit).isNotEqualTo(new Audit());
        assertThat(audit.toString()).isEqualTo(EntityUtils.toJson(audit));
        assertThat(audit).isEqualTo(audit);
        assertThat(audit.hashCode()).isEqualTo(
                Objects.hash(audit.getId(), audit.getObjectId(), audit.getTableName(), audit.getEntityClass(),
                        audit.getTableData(), audit.getCreatedOn()));
    }
}
