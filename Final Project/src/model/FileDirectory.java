package model;

import java.util.ArrayList;
import java.util.List;

public class FileDirectory extends Directory<File> {
    public FileDirectory(List<File> list){
        super(list);
    }
    
    public List<File> getFileList() {
        return this.getList();
    }
}
