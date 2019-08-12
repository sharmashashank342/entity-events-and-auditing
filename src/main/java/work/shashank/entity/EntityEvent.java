package work.shashank.entity;

import org.springframework.util.Assert;
import work.shashank.enums.OperationType;
import work.shashank.util.EntityUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * @author Shashank Sharma
 */
public class EntityEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private OperationType operationType;

    private Class<?> entityClass;

    private String idField;

    /**
     * Entity Class Properties as {@link java.util.Map}
     * variable names of entity class properties as key
     * and variable values as value
     */
    private Map<String, Object> properties;

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        Assert.notNull(operationType, "operationType is required");
        this.operationType = operationType;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        Assert.notNull(entityClass, "entityClass is required");
        this.entityClass = entityClass;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        Assert.notNull(properties, "properties are required");
        Assert.isTrue(!properties.isEmpty(), "properties are required");
        this.properties = properties;
    }

    public String getIdField() {
        return idField;
    }

    public void setIdField(String idField) {
        this.idField = idField;
    }

    @Override
    public String toString() {
        return EntityUtils.toJson(this);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof EntityEvent)) {
            return false;
        }
        EntityEvent that = (EntityEvent) object;
        return operationType == that.operationType &&
                Objects.equals(entityClass, that.entityClass) &&
                Objects.equals(idField, that.idField) &&
                Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, entityClass, idField, properties);
    }
}
