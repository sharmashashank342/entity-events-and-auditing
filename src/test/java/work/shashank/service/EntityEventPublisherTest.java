package work.shashank.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import work.shashank.entity.EntityEvent;
import work.shashank.events.EntityEventDTO;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static work.shashank.factory.EntityEventFactory.getDummyEntityEvent;
import static work.shashank.factory.EntityEventFactory.getDummyEntityEventDTO;

@RunWith(MockitoJUnitRunner.class)
public class EntityEventPublisherTest {

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private EntityEventPublisher entityEventPublisher = new EntityEventPublisher(applicationEventPublisher);

    @Captor
    private ArgumentCaptor<EntityEventDTO> applicationEvent;

    @Test
    public void test_publishEntityEvent() {

        doNothing().when(applicationEventPublisher).publishEvent(any(ApplicationEvent.class));

        EntityEvent entityEvent = getDummyEntityEvent();

        entityEventPublisher.publishEntityEvent(entityEvent);

        verify(applicationEventPublisher).publishEvent(applicationEvent.capture());

        assertEquals(applicationEvent.getValue(), getDummyEntityEventDTO(entityEvent));
    }
}
