package work.shashank.service;

import work.shashank.entity.EntityEvent;

public interface AuditService {

    void createAudit(EntityEvent entityEvent);
}
