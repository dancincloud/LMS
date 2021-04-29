package model;

import java.util.Random;
import java.util.UUID;

/**
 * a tool to generate mutiple kinds of mock data
 *
 * @author Joseph Yuanhao Li
 * @date 4/27/21 23:18
 */
public class DataGenerator {
    public static String generateName(int min, int max) {
        char[] chars;
        int nameLength = (int) (Math.random() * (max - min + 1)) + min;
        chars = new char[nameLength];
        chars[0] = (char) (Math.random() * 26 + 65);
        for (int i = 1; i < nameLength; i++) {
            chars[i] = (char) (Math.random() * 26 + 97);
        }
        return new String(chars);
    }

    public static String generateEmailByName(String name){
        return name + "@northeastern.edu";
    }

    public static String generateID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr=str.replace("-", "");
        return uuidStr;
    }

    private static Random random = new Random();

    private static String[] coursePrefix = new String[]{"INFO", "CSYE", "ENPC"};

    public static String generateCourseID(){
        return coursePrefix[random.nextInt(coursePrefix.length)] + (1000 + random.nextInt(8999));
    }

    public static double generateGPA(){
        double gpa = 1.0 + random.nextDouble() * 3.0;
        return (double) Math.round(gpa * 1000) / 1000.0 ;
    }

    public static String generateFilePath(String fileName){
        return fileName + ".ppt";
    }

    public static String generateZoom(){
        return "https://northeastern.zoom.us/j/96811799191?pwd=UVZ5aWpGb2VwYWkrdTM4RGRoQmhoZz09&uname=Yuanhao+Li#success";
    }
}
