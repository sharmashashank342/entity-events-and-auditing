package work.shashank.service;

import work.shashank.entity.EntityEvent;

public interface EntityService {

    void processEntityCallback(EntityEvent entityEvent);
}
