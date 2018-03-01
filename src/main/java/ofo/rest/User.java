package ofo.rest;

import ofo.Enity.UserEnity;
import ofo.dao.Cache.Cache;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/user")
public class User {
    @GET
    @Path("/add")
    public String add_User(@QueryParam("user") String user_name,
                            @QueryParam("password") String password,
                            @QueryParam("group") String group,
                            @QueryParam("level") String level,
                            @QueryParam("phone") int phone,
                            @QueryParam("email") String email) {
        UserEnity userEnity = new UserEnity(user_name, password, group, level, phone, email);
        String state = new Cache().insert(userEnity);
        return state;
    }

    @GET
    @Path("/select")
    public String select_User(@QueryParam("user") String user_name,
                              @QueryParam("password") String password,
                              @QueryParam("group") String group,
                              @QueryParam("level") String level,
                              @QueryParam("phone") int phone,
                              @QueryParam("email") String email) {

        return user_name + password + group + level + phone + email;
    }

    @GET
    @Path("/update")
    public String update_User(@QueryParam("user") String user_name,
                              @QueryParam("password") String password,
                              @QueryParam("group") String group,
                              @QueryParam("level") String level,
                              @QueryParam("phone") int phone,
                              @QueryParam("email") String email) {

        return user_name + password + group + level + phone + email;
    }
}
