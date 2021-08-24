/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.listeners;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import khangnh.principles.Constant;
//import javax.servlet.ServletContext;

/**
 * Web application lifecycle listener.
 *
 * @author khang nguyen
 */
public class ContextConfigureListener implements ServletContextListener {
    private void loadResourceToAuthenticate(ServletContext context, String path, String attributeName) throws FileNotFoundException, IOException {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            String fileName = context.getRealPath("/" + context.getInitParameter(path));
            File file = new File(fileName);
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line = "";
            StringTokenizer tokenizer = null;
            Map<String, String> map = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (map == null) {
                    map = new HashMap<>();
                }
                tokenizer = new StringTokenizer(line, Constant.DELIMETER);
                map.put(tokenizer.nextToken(), tokenizer.nextToken());
            }
            context.setAttribute(attributeName, map);
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (fileReader != null) {
                fileReader.close();
            }
        }
    }

    private void loadResourceToAuthorize(ServletContext context, String path, String attributeName) throws FileNotFoundException, IOException {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            String fileName = context.getRealPath("/" + context.getInitParameter(path));
            File file = new File(fileName);
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line = "";
            List<String> list = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(line);
            }
            context.setAttribute(attributeName, list);
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (fileReader != null) {
                fileReader.close();
            }
        }
    }

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        try {
            loadResourceToAuthenticate(context, "authentication_resources", "AUTHENTICATION");
            loadResourceToAuthorize(context, "customer_resources", "CUSTOMER");
            loadResourceToAuthorize(context, "admin_resources", "ADMIN");
        } catch (IOException ex) {
            context.log(ex.getMessage());
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }
}
