package xorrr.github.io.rest.routes;

import spark.Request;
import spark.Response;
import spark.Route;
import xorrr.github.io.db.DatastoreFacade;
import xorrr.github.io.model.Media;
import xorrr.github.io.model.Range;
import xorrr.github.io.rest.MappedRoutes;
import xorrr.github.io.rest.MappedRoutesParams;
import xorrr.github.io.rest.QueryParams;
import xorrr.github.io.rest.transformation.Transformator;

public class GetMediaByIdRoute extends Route {

    private DatastoreFacade facade;
    private Transformator transformator;

    public GetMediaByIdRoute(DatastoreFacade f, Transformator t) {
        super(MappedRoutes.MEDIA_BY_ID);
        this.facade = f;
        this.transformator = t;
    }

    @Override
    public String handle(Request request, Response response) {
        String id = request.params(MappedRoutesParams.ID);
        String json = "";

        if (request.queryParams().size() > 0) {
            if (avgRangeQryParamIsTrue(request)) {
                json = returnAvgRangeJson(id);
            }
        } else {
            json = returnMediaJson(id);
        }
        return json;
    }

    private String returnAvgRangeJson(String id) {
        String json;
        Range avg= facade.getAverageRangeFor(id);
        json = transformator.toRangeJson(avg);
        return json;
    }

    private String returnMediaJson(String id) {
        String json;
        Media m = facade.getMediaById(id);
        json = transformator.toMediaJson(m);
        return json;
    }

    private boolean avgRangeQryParamIsTrue(Request request) {
        return request.queryParams(QueryParams.AVG_RANGE).equals("true");
    }

}
