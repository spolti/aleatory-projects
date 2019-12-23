package org.kie.kogito;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.kie.kogito.process.Process;
import org.kie.kogito.process.ProcessInstance;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class ReproducerTest {

    @Inject
    @Named("testProcess")
    Process<? extends Model> process;

    @Test
    public void testProcess()  {

        assertNotNull(process);
        User user = new User("name", "surename", Arrays.asList("test", "test1"), Arrays.asList(true, false), Arrays.asList(1, 2), Arrays.asList(1L, 2L), Arrays.asList(new Message()));


        Model model = process.createModel();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user", user);
        model.fromMap(parameters);

        ProcessInstance<?> processInstance = process.createInstance(model);
        processInstance.start();

    }


}
