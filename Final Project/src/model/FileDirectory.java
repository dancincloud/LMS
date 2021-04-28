package model;

import java.util.ArrayList;
import java.util.List;

public class FileDirectory extends Directory<File> {
    public FileDirectory(){
        super();
    }

    public FileDirectory(List<File> list){
        super(list);
    }
}
