package com.tests.oom;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.Configuration;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by fspolti on 5/18/17.
 */
@WebServlet(urlPatterns = {"/oom-cache"})
public class TestServletJDGCache extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String host = System.getenv("DATAGRID_APP_HOTROD_SERVICE_HOST");
        int port = Integer.parseInt(System.getenv("DATAGRID_APP_HOTROD_SERVICE_PORT"));

        Configuration conf = new ConfigurationBuilder().addServer().host(host).port(port).build();
        RemoteCacheManager manager = new RemoteCacheManager(conf);
        RemoteCache cache = manager.getCache();

        char[] data = new char[1000000];
        int index = 0;
        while (true) {
            System.out.println("populating the cache index " + index);
            cache.put(index++, data,1, TimeUnit.DAYS);
        }
    }
}