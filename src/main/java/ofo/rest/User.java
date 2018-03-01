package ofo.rest;

import net.sf.json.JSONObject;
import ofo.Enity.UserEnity;
import ofo.dao.Cache.Cache;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
        return new Cache().insert(userEnity);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/select")
    public String select_User(@QueryParam("user") String user_name,
                              @QueryParam("password") String password) {
        JSONObject jsonObject = new JSONObject();
        UserEnity userEnity = new Cache().select(user_name, password);
        if (userEnity == null) {
            jsonObject.put("Faild", "Login Faild");
            return jsonObject.toString();
        } else {
            jsonObject.put("user", user_name);
            jsonObject.put("password", password);
            jsonObject.put("group", userEnity.getGroup());
            jsonObject.put("level", userEnity.getLevel());
            jsonObject.put("phone", userEnity.getPhone());
            jsonObject.put("email", userEnity.getEmail());
            return jsonObject.toString();
        }
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
