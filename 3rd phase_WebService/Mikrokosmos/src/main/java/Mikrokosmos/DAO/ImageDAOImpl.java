/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mikrokosmos.DAO;

import Mikrokosmos.Enum.ImageFileEnum;
import Mikrokosmos.Model.FileDTO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Admin
 */
@Repository
public class ImageDAOImpl implements ImageDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Value("${sp_Image_save}")
    String sp_Image_save;
    @Value("${sp_Image_download}")
    String sp_Image_download;

    @Override
    public boolean saveImage(FileDTO fileDTO) {
        System.out.println("File type"+ fileDTO.getFileType());
        System.out.println("checkkkk " +ImageFileEnum.checkContain(fileDTO.getFileType()));
        try {
            if (ImageFileEnum.checkContain(fileDTO.getFileType())) {
                StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Image_save, FileDTO.class);
                query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
                query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
                query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
                query.registerStoredProcedureParameter(4, byte[].class, ParameterMode.IN);
                query.setParameter(1, fileDTO.getRefID());
                query.setParameter(2, fileDTO.getFileName());
                query.setParameter(3, fileDTO.getFileType());
                query.setParameter(4, fileDTO.getData());
                query.execute();
                return true;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return false;
    }

    @Override
    public FileDTO getImage(String refID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @SuppressWarnings("unchecked")
    @Override
    public FileDTO downloadImage(String refID) {
        try {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Image_download, FileDTO.class);
            query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
            query.setParameter(1, refID);
            List<FileDTO> list = query.getResultList();
            return list.get(0);
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

}
