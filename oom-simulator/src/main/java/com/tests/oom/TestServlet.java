package com.tests.oom;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by fspolti on 5/17/17.
 */
@WebServlet(urlPatterns = { "/oom-jvm" })
public class TestServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        while(true) {

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        System.out.println("hello new tread");
                    }
                }
            });
            thread.start();
        }
    }
}
