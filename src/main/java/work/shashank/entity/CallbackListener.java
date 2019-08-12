package work.shashank.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import work.shashank.annotations.Callbacks;
import work.shashank.configuration.SpringContextHolder;
import work.shashank.enums.OperationType;
import work.shashank.util.EntityUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Callback Listener for {@link javax.persistence.Entity} class
 * @author Shashank Sharma
 */
public class CallbackListener {

    private static final Logger log = LoggerFactory.getLogger(CallbackListener.class);

    /**
     * @param object
     * Handling After {@link javax.persistence.Entity} is Inserted in Database
     */
    @PostPersist
    public void afterInsert(Object object) {
        createEntityCallback(object, OperationType.CREATE);
    }

    /**
     * @param object
     * Handling After {@link javax.persistence.Entity} is Updated in Database
     */
    @PostUpdate
    public void afterUpdate(Object object) {
        createEntityCallback(object, OperationType.UPDATE);
    }


    /**
     * @param entity
     * @param operationType
     */
    private void createEntityCallback(final Object entity, final OperationType operationType) {

        try {
            log.info("Inside Callback for operation "+operationType+" and entity "+entity);
            final Predicate<Callbacks> callbacksPredicate = callbacks -> !callbacks.raiseApplicationEvent() && !callbacks.auditable();
            Callbacks callbacks = entity.getClass().getAnnotation(Callbacks.class);
            if (entity.getClass().getAnnotation(Entity.class) == null || callbacks == null || callbacksPredicate.test(callbacks)) {
                return;
            }

            EntityEvent entityEvent = new EntityEvent();
            entityEvent.setOperationType(operationType);
            entityEvent.setEntityClass(entity.getClass());
            entityEvent.setIdField(EntityUtils.getAnnotatedFieldValue(entity, Id.class));
            entityEvent.setProperties(EntityUtils.convertToMap(entity));

            Optional.ofNullable(SpringContextHolder.getInstance())
                    .map(SpringContextHolder::getEntityService)
                    .ifPresent(entityService -> entityService.processEntityCallback(entityEvent));
        } catch (Exception e) {
            // Handle Exception silently
            log.error("Exception occured while raising Entity Callback", e);
        }
    }
}
