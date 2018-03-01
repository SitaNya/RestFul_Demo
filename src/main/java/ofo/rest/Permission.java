package ofo.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * restful测试类
 *
 * @author marscheng
 * @create 2017-07-26 下午3:19
 */
@Path("/permission")
public class Permission {

    /**
     * @param group_name    组名
     * @param group_id      组缩写
     * @param manager       负责人
     * @param database      数据库名
     * @param table         表名
     * @param readonly      读写
     * @param text          备注
     * @return result       String类型，成功返回成功后状态，失败返回错误类型
     */
    @GET
    @Path("/table")
    public String Table(@QueryParam("group_name") String group_name,
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
    @Path("/database")
    public String DataBase(@QueryParam("group_name") String group_name,
                           @QueryParam("group_id") String group_id,
                           @QueryParam("manager") String manager,
                           @QueryParam("database") String database,
                           @QueryParam("readonly") Boolean readonly,
                           @QueryParam("text") String text) {
        System.out.println(group_name + " " + group_id + " " + manager + " " + " " + database + " " + readonly.toString() + " " + text);
        return readonly.toString();
    }

}
