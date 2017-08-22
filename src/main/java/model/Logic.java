package model;

import com.google.gson.JsonObject;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sergeybp on 20.07.17.
 */

@Path("")
public class Logic {

    private Utils utils = new Utils();

    @POST
    @Path("/start_job_user")
    public Response userGet(@QueryParam("username") String username) {

        String jobId = utils.generateNewId();
        Utils.workingMap.put(jobId, Utils.EMPTY);
        JsonObject result = new JsonObject();
        result.addProperty("job_id", jobId);

        SingleRun run = new SingleRun(username, jobId);
        run.run();

        return Response.ok(result.toString(), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/get_result")
    public Response get(@QueryParam("job_id") String jobId) {

        if (Utils.workingMap.get(jobId).equals(Utils.EMPTY)) {
            return Response.status(449).build();
        }
        return Response.ok(Utils.workingMap.get(jobId).toString(), MediaType.APPLICATION_JSON).build();
    }


}
