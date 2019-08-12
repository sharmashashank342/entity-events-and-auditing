package work.shashank.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import work.shashank.annotations.Callbacks;
import work.shashank.entity.EntityEvent;
import work.shashank.enums.OperationType;
import work.shashank.util.EntityUtils;

import javax.persistence.Entity;
import javax.persistence.Id;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static work.shashank.factory.EntityEventFactory.getDummyEntityEvent;

@RunWith(MockitoJUnitRunner.class)
public class EntityServiceTest {

    @Mock
    private AuditService auditService;

    @Mock
    private EntityEventPublisher entityEventPublisher;

    @InjectMocks
    private EntityService entityService = new EntityServiceImpl(auditService, entityEventPublisher);

    @Test
    public void test_processEntityCallback_Without_Auditable() {

        // Entity Used is Audit, which is not auditable
        EntityEvent entityEvent = getDummyEntityEvent();

        doNothing().when(auditService).createAudit(any(EntityEvent.class));
        doNothing().when(entityEventPublisher).publishEntityEvent(any(EntityEvent.class));

        entityService.processEntityCallback(entityEvent);

        verify(auditService, never()).createAudit(any());
        verify(entityEventPublisher).publishEntityEvent(entityEvent);
    }

    @Test
    public void test_processEntityCallback_Without_RaiseCallbackEvent() {

        EntityAuditableWithoutCallback entity = new EntityAuditableWithoutCallback();

        EntityEvent entityEvent = new EntityEvent();
        entityEvent.setIdField(entity.id.toString());
        entityEvent.setEntityClass(entity.getClass());
        entityEvent.setOperationType(OperationType.CREATE);
        entityEvent.setProperties(EntityUtils.convertToMap(entity));

        doNothing().when(auditService).createAudit(any(EntityEvent.class));
        doNothing().when(entityEventPublisher).publishEntityEvent(any(EntityEvent.class));

        entityService.processEntityCallback(entityEvent);

        verify(auditService).createAudit(entityEvent);
        verify(entityEventPublisher, never()).publishEntityEvent(any());
    }

    @Test
    public void test_processEntityCallback_With_Auditable_And_Callbacks() {

        EntityAuditableAndCallbacks entity = new EntityAuditableAndCallbacks();

        EntityEvent entityEvent = new EntityEvent();
        entityEvent.setIdField(entity.id.toString());
        entityEvent.setEntityClass(entity.getClass());
        entityEvent.setOperationType(OperationType.CREATE);
        entityEvent.setProperties(EntityUtils.convertToMap(entity));

        doNothing().when(auditService).createAudit(any(EntityEvent.class));
        doNothing().when(entityEventPublisher).publishEntityEvent(any(EntityEvent.class));

        entityService.processEntityCallback(entityEvent);

        verify(auditService).createAudit(entityEvent);
        verify(entityEventPublisher).publishEntityEvent(entityEvent);
    }

    @Test
    public void test_processEntityCallback_Without_Auditable_And_Callbacks() {

        EntityClass entity = new EntityClass();

        EntityEvent entityEvent = new EntityEvent();
        entityEvent.setIdField(entity.id.toString());
        entityEvent.setEntityClass(entity.getClass());
        entityEvent.setOperationType(OperationType.CREATE);
        entityEvent.setProperties(EntityUtils.convertToMap(entity));

        doNothing().when(auditService).createAudit(any(EntityEvent.class));
        doNothing().when(entityEventPublisher).publishEntityEvent(any(EntityEvent.class));

        entityService.processEntityCallback(entityEvent);

        verify(auditService, never()).createAudit(any());
        verify(entityEventPublisher, never()).publishEntityEvent(any());
    }

    @Entity
    @Callbacks(auditable = true, raiseApplicationEvent = false)
    private class EntityAuditableWithoutCallback {

        @Id
        public Integer id = 1;
    }

    @Entity
    @Callbacks(auditable = true)
    private class EntityAuditableAndCallbacks {

        @Id
        public Integer id = 1;
    }

    @Entity
    private class EntityClass {

        @Id
        public Integer id = 1;
    }
}
