package ofo.hive;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * restful测试类
 *
 * @author marscheng
 * @create 2017-07-26 下午3:19
 */
@Path("set")
public class Hello {

    @GET
    @Path("table")
    public String sayXMLHello(@QueryParam("group_name") String group_name,
                              @QueryParam("group_id") String group_id,
                              @QueryParam("manager") String manager,
                              @QueryParam("database") String database,
                              @QueryParam("table") String table,
                              @QueryParam("readonly") Boolean readonly,
                              @QueryParam("text") String text) {
        System.out.println(group_name + " " + group_id + " " + manager + " " + table + " " + database + " " + readonly.toString() + " " + text);
        return readonly.toString();
    }

    @GET
    @Path("database")
    public String sayXMLHello(@QueryParam("group_name") String group_name,
                              @QueryParam("group_id") String group_id,
                              @QueryParam("manager") String manager,
                              @QueryParam("database") String database,
                              @QueryParam("readonly") Boolean readonly,
                              @QueryParam("text") String text) {
        System.out.println(group_name + " " + group_id + " " + manager + " " + " " + database + " " + readonly.toString() + " " + text);
        return readonly.toString();
    }

}
