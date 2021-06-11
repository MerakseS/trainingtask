package controller.utils;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletUtils {
    private static final Gson gson = new Gson();

    public static void sendJson(HttpServletResponse response, Object payload) throws IOException {
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.print(gson.toJson(payload));
        writer.flush();
    }

    public static Long parseRouteParameter(String pathInfo) {
        String[] splits = pathInfo.split("/");
        return Long.parseLong(splits[1]);
    }
}
