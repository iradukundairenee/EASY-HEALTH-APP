package com.example.eHospital;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import com.example.eHospital.utils.ResFormat;
import com.example.eHospital.utils.ResponseEntity;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public class Main extends HttpServlet {
    public static void main(String[] args) {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        tomcat.addWebapp("/", new File("src/main/webapp/").getAbsolutePath());

        try {
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ResFormat.res(res, new ResponseEntity<>("Hello,  world!", null), HttpServletResponse.SC_OK);
    }
}
