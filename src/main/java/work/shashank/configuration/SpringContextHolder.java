package work.shashank.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.shashank.service.EntityService;

import javax.annotation.PostConstruct;

/**
 * @author Shashank Sharma
 */
@Component
public class SpringContextHolder {

    private static SpringContextHolder springContextHolder;

    private EntityService entityService;

    @Autowired
    public SpringContextHolder(EntityService entityService) {
        springContextHolder = this;
        this.entityService = entityService;
    }

    public static SpringContextHolder getInstance() {
        return springContextHolder;
    }

    public EntityService getEntityService() {
        return entityService;
    }
}
