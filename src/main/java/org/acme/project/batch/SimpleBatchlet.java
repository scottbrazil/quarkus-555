package org.acme.project.batch;

import io.quarkus.logging.Log;
import jakarta.batch.api.BatchProperty;
import jakarta.batch.runtime.BatchStatus;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("simpleBatchlet")
@Dependent
public class SimpleBatchlet implements jakarta.batch.api.Batchlet {

    @Inject
    @BatchProperty(name = "message")
    String message;

    @Override
    public String process() throws Exception {
        System.out.println(">>> Batchlet started running! <<<");


        String greeting = (message != null) ? message : "Hello World from Batchlet!";
        Log.info("Processing payload: " + greeting);

        Thread.sleep(1000);

        Log.info(">>> Batchlet completed successfully! <<<");

        return BatchStatus.COMPLETED.toString();
    }

    @Override
    public void stop() throws Exception {
        // Handle graceful cancellation if needed
    }
}
