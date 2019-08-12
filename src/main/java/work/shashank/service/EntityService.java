package work.shashank.service;

import work.shashank.entity.EntityEvent;

/**
 * @author Shashank Sharma
 */
public interface EntityService {

    void processEntityCallback(EntityEvent entityEvent);
}
