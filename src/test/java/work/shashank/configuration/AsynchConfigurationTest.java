package work.shashank.configuration;

import org.junit.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

import static org.assertj.core.api.Assertions.assertThat;

public class AsynchConfigurationTest {

    private AsynchConfiguration asynchConfiguration = new AsynchConfiguration();

    @Test
    public void test_AsynchConfiguration() {

        Executor executor = asynchConfiguration.asyncExecutor();
        ThreadPoolTaskExecutor threadPoolTaskExecutor = (ThreadPoolTaskExecutor) executor;

        assertThat(asynchConfiguration.asyncExecutor()).isInstanceOf(ThreadPoolTaskExecutor.class);

        assertThat(threadPoolTaskExecutor.getCorePoolSize()).isEqualTo(threadPoolTaskExecutor.getMaxPoolSize())
                .isEqualTo(5);
        assertThat(threadPoolTaskExecutor.getThreadNamePrefix()).isEqualTo("EntityEvent-");
    }
}
