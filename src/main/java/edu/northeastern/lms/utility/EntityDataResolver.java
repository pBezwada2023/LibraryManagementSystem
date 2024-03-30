package edu.northeastern.lms.utility;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EntityDataResolver {

/*
    Deserialize object  from file
    @Param File
    @Return Object
 */
    public static Object readObjectFromFile(File file) throws IOException, ClassNotFoundException {
        Object result = null;
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            result = ois.readObject();
        }
        return result;
    }

    /*
      Creates a new file by serializing the object
      @param Object  obj to be serialized
      @param String objectId that would be the name of the file
      @param String path
     */
    public static void writeToFile(Object obj, String id, String path) throws IOException {
        if(obj == null || id.isBlank() || path.isBlank())
            throw new IllegalArgumentException("Invalid arguments while serializing an object");
        Path p = Paths.get(path+id+".txt");
        File file = new File(p.toString());
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
        os.writeObject(obj);
        os.close();
    }

    public static void deleteFile(String path, String id) {
        if(path.isBlank() || id.isBlank())
            return;
        Path p = Paths.get(path + id + ".txt");
        File fileToDelete = new File(p.toString());
        fileToDelete.delete();
    }

    public static void deleteAllDataRecordsFromRepository(String path) {
        if(path.isBlank())
            return;
        File folder = new File(path);
        if (!folder.isDirectory())
            return;
        for (File file: folder.listFiles()) {
            if (!file.isDirectory())
                file.delete();
        }
    }
}
