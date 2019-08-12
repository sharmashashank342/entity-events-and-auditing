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
import java.util.Optional;
import java.util.function.Predicate;

public class CallbackListener<T> {

    private static final Logger log = LoggerFactory.getLogger(CallbackListener.class);

    /**
     * <p>Handling After {@link javax.persistence.Entity} is Inserted in Database
     * </p>
     * @author Shashank Sharma
     */
    @PostPersist
    public void afterInsert(T object) {
        log.info("Inside Callbacks insert");
        createEntityCallback(object, OperationType.CREATE);
    }

    /**
     * <p>Handling After {@link javax.persistence.Entity} is Updated in Database
     * </p>
     * @author Shashank Sharma
     */
//    @PostUpdate
    public void afterUpdate(T object) {
        createEntityCallback(object, OperationType.UPDATE);
    }


    private void createEntityCallback(final T entity, final OperationType operationType) {

        try {
            log.info("entity "+entity);
            log.info("operationType "+operationType);
            final Predicate<Callbacks> callbacksPredicate = callbacks -> !callbacks.raiseApplicationEvent() && !callbacks.auditable();
            Callbacks callbacks = entity.getClass().getAnnotation(Callbacks.class);
            if (entity.getClass().getAnnotation(Entity.class) == null || callbacks == null || callbacksPredicate.test(callbacks)) {
                log.info("In Return");
                return;
            }

            EntityEvent entityEvent = new EntityEvent();
            entityEvent.setOperationType(operationType);
            entityEvent.setEntityClass(entity.getClass());
            entityEvent.setIdField(EntityUtils.getAnnotatedFieldValue(entity, Id.class));
            entityEvent.setProperties(EntityUtils.convertToMap(entity));

            log.info("entityEvent "+entityEvent);
            log.info("instance "+SpringContextHolder.getInstance());
            log.info("entity instance "+SpringContextHolder.getInstance().getEntityService());

            Optional.ofNullable(SpringContextHolder.getInstance())
                    .map(SpringContextHolder::getEntityService)
                    .ifPresent(entityService -> entityService.processEntityCallback(entityEvent));
        } catch (Exception e) {
            // Handle Exception silently
            log.error("Exception occured while raising Entity Callback for entity "+entity+" on operation "+operationType, e);
        }
    }
}
