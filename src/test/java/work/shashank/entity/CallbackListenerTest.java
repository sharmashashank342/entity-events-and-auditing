package work.shashank.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import work.shashank.annotations.Callbacks;
import work.shashank.configuration.SpringContextHolder;
import work.shashank.service.EntityService;

import javax.persistence.Entity;
import javax.persistence.Id;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CallbackListenerTest {

    @Mock
    private SpringContextHolder springContextHolder;

    @Mock
    private EntityService entityService;

    @Captor
    private ArgumentCaptor<EntityEvent> entityEventArgumentCaptor;

    private CallbackListener callbackListener = new CallbackListener();

    @Before
    public void init() {
        ReflectionTestUtils.setField(springContextHolder, "springContextHolder", springContextHolder);
        ReflectionTestUtils.setField(springContextHolder, "entityService", entityService);
    }

    @Test
    public void test_afterInsert_With_NoEntityAnnotationEntity() {

        when(springContextHolder.getEntityService()).thenReturn(entityService);
        doNothing().when(entityService).processEntityCallback(any(EntityEvent.class));

        callbackListener.afterInsert(new NoEntityAnnotationEntity());

        verify(springContextHolder, never()).getEntityService();
        verify(entityService, never()).processEntityCallback(any());
    }

    @Test
    public void test_afterInsert_With_NoCallbackAnnotationEntity() {

        when(springContextHolder.getEntityService()).thenReturn(entityService);
        doNothing().when(entityService).processEntityCallback(any(EntityEvent.class));

        callbackListener.afterInsert(new NoCallbackAnnotationEntity());

        verify(springContextHolder, never()).getEntityService();
        verify(entityService, never()).processEntityCallback(any());
    }

    @Test
    public void test_afterInsert_With_NoCallbackAndAuditAnnotationEntity() {

        when(springContextHolder.getEntityService()).thenReturn(entityService);
        doNothing().when(entityService).processEntityCallback(any(EntityEvent.class));

        callbackListener.afterInsert(new NoCallbackAndAuditAnnotationEntity());

        verify(springContextHolder, never()).getEntityService();
        verify(entityService, never()).processEntityCallback(any());
    }

    @Test
    public void test_afterInsert() {

        when(springContextHolder.getEntityService()).thenReturn(entityService);
        doNothing().when(entityService).processEntityCallback(any(EntityEvent.class));

        callbackListener.afterInsert(new Audit());

        verify(springContextHolder, times(2)).getEntityService();
        verify(entityService).processEntityCallback(entityEventArgumentCaptor.capture());
    }

    private class NoEntityAnnotationEntity {

        @Id
        public Integer id = 1;
    }

    @Entity
    private class NoCallbackAnnotationEntity {

        @Id
        public Integer id = 1;
    }

    @Entity
    @Callbacks(raiseApplicationEvent = false)
    private class NoCallbackAndAuditAnnotationEntity {

        @Id
        public Integer id = 1;
    }
}
