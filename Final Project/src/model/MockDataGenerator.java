package model;

import java.util.UUID;

/**
 * a tool to generate mutiple kinds of mock data
 *
 * @author Joseph Yuanhao Li
 * @date 4/27/21 23:18
 */
public class MockDataGenerator {
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
}
