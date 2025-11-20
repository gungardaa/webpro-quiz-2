package controller;

import jakarta.servlet.http.Part;

public class PathUtil {
    public static String getFilename(Part part) {
        String header = part.getHeader("content-disposition");
        if (header == null) return "file";
        for (String cd : header.split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1);
            }
        }
        return null;
    }
}
