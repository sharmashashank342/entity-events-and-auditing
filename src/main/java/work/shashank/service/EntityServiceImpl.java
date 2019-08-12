package work.shashank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.shashank.annotations.Callbacks;
import work.shashank.entity.EntityEvent;
import work.shashank.entity.Audit;

import java.util.Optional;

@Service
public class EntityServiceImpl implements EntityService {

    private AuditService auditService;

    private EntityEventPublisher entityEventPublisher;

    @Autowired
    public EntityServiceImpl(AuditService auditService, EntityEventPublisher entityEventPublisher) {
        this.auditService = auditService;
        this.entityEventPublisher = entityEventPublisher;
    }

    @Override
    public void processEntityCallback(EntityEvent entityEvent) {

        Optional.ofNullable(entityEvent.getEntityClass().getAnnotation(Callbacks.class))
                .ifPresent(callbacks -> processCallback(callbacks, entityEvent));
    }

    private void processCallback(Callbacks callbacks, EntityEvent entityEvent) {

        if (callbacks.auditable() && entityEvent.getEntityClass() != Audit.class) {
            auditService.createAudit(entityEvent);
        }

        if (callbacks.raiseApplicationEvent()) {
            entityEventPublisher.publishEntityEvent(entityEvent);
        }
    }
}
