package com.example.eHospital.utils;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

public class ResFormat {

    public static <T> void res(HttpServletResponse res, ResponseEntity<T> response, int status) throws IOException {
        res.setStatus(status);
        res.setContentType("application/json");
        OutputStream out = res.getOutputStream();
        out.write(new JSONUtil().toJson(response).getBytes());
        out.flush();
    }

}
