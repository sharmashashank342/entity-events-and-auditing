package work.shashank.events;

import org.junit.Test;
import work.shashank.entity.Audit;
import work.shashank.entity.EntityEvent;
import work.shashank.enums.OperationType;
import work.shashank.util.EntityUtils;

import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EntityEventDTOTest {

    @Test
    public void test_EntityEventDTO_Throws_IllegalArgumentException() {


        assertThatThrownBy(() -> new EntityEventDTO(null, new EntityEvent()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("null source");
    }

    @Test
    public void test_EntityEventDTO() {

        Audit audit = new Audit();
        Map<String, Object> properties = EntityUtils.convertToMap(audit);

        EntityEvent entityEvent = new EntityEvent();
        entityEvent.setIdField(audit.getId());
        entityEvent.setEntityClass(audit.getClass());
        entityEvent.setOperationType(OperationType.CREATE);
        entityEvent.setProperties(properties);

        EntityEventDTO entityEventDTO = new EntityEventDTO(new Object(), entityEvent);

        assertThat(entityEventDTO.getIdField()).isEqualTo(entityEvent.getIdField()).isEqualTo(audit.getId());
        assertThat(entityEventDTO.getOperationType()).isEqualTo(entityEvent.getOperationType()).isEqualTo(OperationType.CREATE);
        assertThat(entityEventDTO.getEntityClass()).isEqualTo(entityEvent.getEntityClass()).isEqualTo(Audit.class);
        assertThat(entityEventDTO.getProperties()).isEqualTo(entityEvent.getProperties()).isEqualTo(properties);
        assertThat(entityEventDTO).isEqualTo(entityEventDTO);
        assertThat(entityEventDTO).isNotEqualTo(new Object());
        assertThat(entityEventDTO.hashCode()).isEqualTo(Objects.hash(entityEvent));
        assertThat(entityEventDTO.toString()).isEqualTo(entityEvent.toString());
        assertThat(entityEventDTO.getProperties(Audit.class)).isEqualTo(audit);
    }
}
