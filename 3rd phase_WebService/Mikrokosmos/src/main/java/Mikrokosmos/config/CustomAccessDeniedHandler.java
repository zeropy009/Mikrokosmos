/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mikrokosmos.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 *
 * @author Admin
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

//    public static final Logger logger = Logger.getLogger(CustomAccessDeniedHandler.class.getName());

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException exc) throws IOException, ServletException {
        System.out.println("NO permisssionnnnn");
        response.sendRedirect(request.getContextPath() + "/error-401?isNoPermission=true");
    }

}
