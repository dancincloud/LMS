package model;

import java.util.ArrayList;
import java.util.List;

public class FileDirectory {
    private List<File> fileList;

    public FileDirectory(List<File> fileList) {
        this.fileList = fileList;
    }
    
    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }
}
