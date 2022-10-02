import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.DockerHealthcheckWaitStrategy;

import java.io.IOException;
import java.nio.file.Paths;

@Slf4j
public class ContainerStartup {
   @Test
   public void containerStartupTest() throws IOException {
      DockerComposeContainer container = new DockerComposeContainer<>(Paths.get(System.getProperty("user.dir"), "../docker-compose.yaml").toFile())
            .withLogConsumer("cassandra", new Slf4jLogConsumer(log))
            .withLogConsumer("keycloak", new Slf4jLogConsumer(log))
            .waitingFor("keycloak", new DockerHealthcheckWaitStrategy());

      container.start();

      log.info("Keycloak is up and running on: http://localhost:8080");
      log.info("Press any key to abort...");
      System.in.read();
   }
}
