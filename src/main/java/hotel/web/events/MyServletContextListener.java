/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.web.events;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author mdeboer1
 */
public class MyServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext sc = event.getServletContext();
        String driverClass = sc.getInitParameter("driver.class");
        sc.setAttribute("driver.class", driverClass);
        String url = sc.getInitParameter("db.url");
        sc.setAttribute("db.url", url);
        String username = sc.getInitParameter("db.username");
        sc.setAttribute("db.username", username);
        String password = sc.getInitParameter("db.password");
        sc.setAttribute("db.password", password);
        String hotelDao = sc.getInitParameter("hotel.dao.strategy");
        sc.setAttribute("hotel.dao.strategy", hotelDao);
        String dbAccessor = sc.getInitParameter("db.accessor.strategy");
        sc.setAttribute("db.accessor.strategy", dbAccessor);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
}
