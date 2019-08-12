package work.shashank.factory;

import work.shashank.entity.EntityEvent;
import work.shashank.entity.Audit;
import work.shashank.enums.OperationType;
import work.shashank.events.EntityEventDTO;
import work.shashank.util.EntityUtils;

public class EntityEventFactory {

    public static Audit getDummyAudit() {

        Audit audit = new Audit();
        audit.setEntityClass(Audit.class.getName());
        audit.setObjectId(audit.getId());
        audit.setTableName("ss_audit");
        audit.setTableData(EntityUtils.toJson(audit));

        return audit;
    }

    public static EntityEvent getDummyEntityEvent() {

        return getDummyEntityEvent(getDummyAudit());
    }

    public static EntityEvent getDummyEntityEvent(Audit audit) {

        EntityEvent entityEvent = new EntityEvent();
        entityEvent.setIdField(audit.getId());
        entityEvent.setEntityClass(audit.getClass());
        entityEvent.setOperationType(OperationType.CREATE);
        entityEvent.setProperties(EntityUtils.convertToMap(audit));

        return entityEvent;
    }

    public static EntityEventDTO getDummyEntityEventDTO(EntityEvent entityEvent) {
        return new EntityEventDTO(entityEvent, entityEvent);
    }
}
