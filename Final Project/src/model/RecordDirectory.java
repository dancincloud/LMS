package model;


import java.util.List;

public class RecordDirectory extends Directory<Record> {
    public RecordDirectory() {
        super();
    }

    public RecordDirectory(List<Record> list) {
        super(list);
    }
}
