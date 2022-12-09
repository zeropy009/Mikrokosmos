/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mikrokosmos.DAO;

import Mikrokosmos.Model.FileDTO;

/**
 *
 * @author Admin
 */

public interface ImageDAO {
    
    public FileDTO getImage(String refID);
    
    public boolean saveImage(FileDTO fileDTO);
    
    public FileDTO downloadImage(String refID);
}
