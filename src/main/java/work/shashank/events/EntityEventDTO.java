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
     * @return Entity {@link java.lang.Class} Object
     */
    public Class<?> getEntityClass() {
        return entityEvent.getEntityClass();
    }

    /**
     * @return Primary key field of Entity as {@link java.lang.String}
     */
    public String getIdField() {
        return entityEvent.getIdField();
    }

    /**
     * @return Return current {@link work.shashank.enums.OperationType} on entity
     */
    public OperationType getOperationType() {
        return entityEvent.getOperationType();
    }

    /**
     * @return Properties as {@link java.util.Map}
     */
    public Map<String, Object> getProperties() {
        return entityEvent.getProperties();
    }

    /**
     * Get Entity properties as DTO class Object
     * @param dtoClass DTO {@link java.lang.Class} class object
     * @param <T> This is the type parameter
     * @return Properties as DTO class Object
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
