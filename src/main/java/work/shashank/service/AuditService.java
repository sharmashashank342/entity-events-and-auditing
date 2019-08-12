package work.shashank.service;

import work.shashank.entity.EntityEvent;

/**
 * @author Shashank Sharma
 */
public interface AuditService {

    void createAudit(EntityEvent entityEvent);
}
