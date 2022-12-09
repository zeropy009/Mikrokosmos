package Mikrokosmos.Controller;

import Mikrokosmos.DAO.ImageDAO;
import Mikrokosmos.Model.FileDTO;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Yukinon
 */
@RestController
@RequestMapping("/mik/image")
public class ImageController {

    @Autowired
    ImageDAO imageDAO;

    @CrossOrigin
    @RequestMapping("/save-image")
    public boolean insertAttachmentsPurchase(@RequestParam(value = "file") MultipartFile document,@RequestParam(value = "refID")String refID) throws IOException, InterruptedException, ExecutionException {
        FileDTO documentUpload = new FileDTO();
        documentUpload.setRefID(refID);
        documentUpload.setData(document.getBytes());
        documentUpload.setFileName(document.getOriginalFilename());
        documentUpload.setFileType(document.getContentType());
        return imageDAO.saveImage(documentUpload);
    }
}
