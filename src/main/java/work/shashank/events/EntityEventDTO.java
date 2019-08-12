package work.shashank.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.util.Assert;
import work.shashank.entity.EntityEvent;
import work.shashank.enums.OperationType;
import work.shashank.util.EntityUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class EntityEventDTO extends ApplicationEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private EntityEvent entityEvent;

    public EntityEventDTO(Object source, EntityEvent entityEvent) {
        super(source);
        this.entityEvent = entityEvent;
    }

    public Class<?> getEntityClass() {
        return entityEvent.getEntityClass();
    }

    public String getIdField() {
        return entityEvent.getIdField();
    }

    public OperationType getOperationType() {
        return entityEvent.getOperationType();
    }

    public Map<String, Object> getProperties() {
        return entityEvent.getProperties();
    }

    public <T> T getProperties(Class<T> tClass) {
        Assert.notNull(tClass, "classType is required");
        return EntityUtils.convertValue(entityEvent.getProperties(), tClass);
    }

    @Override
    public String toString() {
        return entityEvent.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof EntityEventDTO)) {
            return false;
        }
        EntityEventDTO that = (EntityEventDTO) object;
        return Objects.equals(entityEvent, that.entityEvent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityEvent);
    }
}
