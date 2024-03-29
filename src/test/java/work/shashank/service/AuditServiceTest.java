package work.shashank.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import work.shashank.entity.EntityEvent;
import work.shashank.entity.Audit;
import work.shashank.repository.AuditRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static work.shashank.factory.EntityEventFactory.getDummyAudit;
import static work.shashank.factory.EntityEventFactory.getDummyEntityEvent;
import static work.shashank.util.EntityUtils.toJson;

@RunWith(MockitoJUnitRunner.class)
public class AuditServiceTest {

    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private AuditService auditService = new AuditServiceImpl(auditRepository);

    @Captor
    private ArgumentCaptor<Audit> auditArgumentCaptor;

    @Test
    public void test_createAudit() {

        Audit audit = getDummyAudit();
        EntityEvent entityEvent = getDummyEntityEvent(audit);

        when(auditRepository.save(any(Audit.class))).thenReturn(audit);

        auditService.createAudit(entityEvent);

        verify(auditRepository).save(auditArgumentCaptor.capture());

        assertThat(isUUID(auditArgumentCaptor.getValue().getId())).isTrue();
        assertThat(auditArgumentCaptor.getValue().getObjectId()).isEqualTo(entityEvent.getIdField());
        assertThat(auditArgumentCaptor.getValue().getEntityClass()).isEqualTo(audit.getClass().getName());
        assertThat(auditArgumentCaptor.getValue().getTableName()).isEqualTo(audit.getTableName());
        assertThat(auditArgumentCaptor.getValue().getTableData()).isEqualTo(toJson(entityEvent.getProperties()));
    }

    private boolean isUUID(String string) {
        try {
            UUID.fromString(string);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
