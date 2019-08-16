package work.shashank.configuration;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import work.shashank.service.EntityService;

import static org.assertj.core.api.Assertions.assertThat;

public class SpringContextHolderTest {

    @Mock
    private EntityService entityService;

    @InjectMocks
    private SpringContextHolder springContextHolder = new SpringContextHolder(entityService);

    @Test
    public void test_getEntityService() {

        assertThat(springContextHolder.getEntityService()).isEqualTo(entityService);
    }
}
