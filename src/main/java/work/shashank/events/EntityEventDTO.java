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

    /**
     * Entity Class class Object
     * @return
     */
    public Class<?> getEntityClass() {
        return entityEvent.getEntityClass();
    }

    /**
     * Primary key field of Entity as String
     * @return
     */
    public String getIdField() {
        return entityEvent.getIdField();
    }

    /**
     * Get Operation performed on this entity
     * @return
     */
    public OperationType getOperationType() {
        return entityEvent.getOperationType();
    }

    /**
     * Entity properties as <code>Map<String, Object></code>
     * @return
     */
    public Map<String, Object> getProperties() {
        return entityEvent.getProperties();
    }

    /**
     * Get Entity properties as DTO class Object
     * @return
     */
    public <T> T getProperties(Class<T> dtoClass) {
        Assert.notNull(dtoClass, "dtoClass is required");
        return EntityUtils.convertValue(entityEvent.getProperties(), dtoClass);
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
