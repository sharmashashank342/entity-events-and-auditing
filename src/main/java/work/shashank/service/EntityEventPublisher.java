package work.shashank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import work.shashank.entity.EntityEvent;
import work.shashank.events.EntityEventDTO;

/**
 * @author Shashank Sharma
 */
@Component
public class EntityEventPublisher {

    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public EntityEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Async("asyncExecutor")
    public void publishEntityEvent(final EntityEvent entityEvent) {

        EntityEventDTO entityEventDTO = new EntityEventDTO(this, entityEvent);
        applicationEventPublisher.publishEvent(entityEventDTO);
    }
}
