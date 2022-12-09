/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mikrokosmos.Enum;

/**
 *
 * @author Admin
 */
public enum ImageFileEnum {
    JPG("image/jpg"),
    JPEG("image/jpeg"),
    PNG("image/png"),
    PNEG("image/pneg");

    private String fileType;

    ImageFileEnum(String fileType) {
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }

    public static boolean checkContain(String fileType) {
        for (ImageFileEnum file : ImageFileEnum.values()) {
            if (file.fileType.equalsIgnoreCase(fileType)) {
                return true;
            }
        }
        return false;
    }

}
