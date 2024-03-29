package work.shashank.entity;

import org.hibernate.annotations.CreationTimestamp;
import work.shashank.annotations.Callbacks;
import work.shashank.util.EntityUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

/**
 * Table for auditing
 * @author Shashank Sharma
 */
@Table(name = "ss_audit")
@Entity
@Callbacks
@EntityListeners(CallbackListener.class)
public class Audit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();

    // Id of Entity that is being saved
    // Default varchar with length 255
    private String objectId;

    @Column(length = 100)
    private String tableName;

    private String entityClass;

    // Json Table Data
    @Column(columnDefinition = "TEXT")
    private String tableData;

    @CreationTimestamp
    private Timestamp createdOn;

    public String getId() {
        return id;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }

    public String getTableData() {
        return tableData;
    }

    public void setTableData(String tableData) {
        this.tableData = tableData;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
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
        if (!(object instanceof Audit)) {
            return false;
        }
        Audit audit = (Audit) object;
        return Objects.equals(id, audit.id) &&
                Objects.equals(objectId, audit.objectId) &&
                Objects.equals(tableName, audit.tableName) &&
                Objects.equals(entityClass, audit.entityClass) &&
                Objects.equals(tableData, audit.tableData) &&
                Objects.equals(createdOn, audit.createdOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, objectId, tableName, entityClass, tableData, createdOn);
    }
}
