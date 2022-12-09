/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mikrokosmos.Controller;

import Mikrokosmos.DAO.ImageDAO;
import Mikrokosmos.Model.FileDTO;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Yukinon
 */
@Controller
public class LoginController {

    @Autowired
    ImageDAO imageDAO;
    
    
    public String getAuthenticatedUserName() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return "";
    }

    @RequestMapping("/mikrokosmos/**")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        System.out.println(getAuthenticatedUserName());
        System.out.println(StringUtils.isEmpty(getAuthenticatedUserName()));
        if(!StringUtils.isEmpty(getAuthenticatedUserName()) && !"anonymousUser".equalsIgnoreCase(getAuthenticatedUserName())){
            return "redirect:/mikrokosmos/";
        }
        return "login";
    }

    @RequestMapping("/download/{refID}")
    public void downloadDocument(@PathVariable(value="refID") String refID, HttpServletResponse response) throws IOException {
        FileDTO fileDTO = imageDAO.downloadImage(refID);
        Objects.requireNonNull(fileDTO, "Don't have file with uuid " + refID + "");
        response.setContentType(fileDTO.getFileType());
        response.setContentLength(fileDTO.getData().length);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileDTO.getFileName() + "\"");
        FileCopyUtils.copy(fileDTO.getData(), response.getOutputStream());
    }
}
