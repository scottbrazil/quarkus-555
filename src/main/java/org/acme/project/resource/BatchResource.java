package org.acme.project.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.batch.operations.JobOperator;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.Properties;

@Path("/batch")
public class BatchResource {
    @Inject
    JobOperator jobOperator;

    @POST
    @Path("/start")
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public String startBatchJob(@QueryParam("msg") String msg) {
        Properties jobParameters = new Properties();
        if (msg != null) {
            jobParameters.setProperty("customMessage", msg);
        }

        // "simple-job" maps directly to the name of your simple-job.xml file
        long executionId = jobOperator.start("simple-job", jobParameters);

        return "Job started! Execution ID: " + executionId;
    }

}
