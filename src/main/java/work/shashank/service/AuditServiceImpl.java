package work.shashank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import work.shashank.entity.EntityEvent;
import work.shashank.entity.Audit;
import work.shashank.repository.AuditRepository;

import javax.persistence.Table;
import java.util.Optional;

import static work.shashank.util.EntityUtils.toJson;

@Service
public class AuditServiceImpl implements AuditService {

    private AuditRepository auditRepository;

    @Autowired
    public AuditServiceImpl(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    @Async
    public void createAudit(EntityEvent entityEvent) {

        Audit audit = buildAudit(entityEvent);
        auditRepository.save(audit);
    }

    private Audit buildAudit(EntityEvent entityEvent) {

        Audit audit = new Audit();
        audit.setTableName(Optional.ofNullable(entityEvent.getEntityClass().getAnnotation(Table.class))
                .map(Table::name).orElse(null));
        audit.setTableData(toJson(entityEvent.getProperties()));
        audit.setObjectId(entityEvent.getIdField());
        audit.setEntityClass(entityEvent.getEntityClass().getName());

        return audit;
    }
}
