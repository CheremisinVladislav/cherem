package com.javarush.task.task31.task3101;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;



/*
Проход по дереву файлов
*/
public class Solution {
   private static  ArrayList<File> filesList = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        File path = new File(args[0]);
        File resultFIleAbsolutePath = new File(args[1]);
        File allFilesContent = new File(resultFIleAbsolutePath.getParent() + "/allFilesContent.txt");
        FileUtils.renameFile(resultFIleAbsolutePath,allFilesContent);

        try(FileOutputStream fileOutputStream = new FileOutputStream(allFilesContent)) {
            fillFilesList(path.getAbsolutePath());
            Collections.sort(filesList,new FileComparator());
            for (File file:filesList) {
                FileInputStream inputStream = new FileInputStream(file);
                while(inputStream.available()>0){
                    fileOutputStream.write(inputStream.read());
                }
                fileOutputStream.write("\n".getBytes());

                fileOutputStream.flush();

            }

        }


    }
    public static void fillFilesList(String path){
        File[] files = new File(path).listFiles();
        for (File f:files) {
            if(f.isDirectory()){
                fillFilesList(f.getAbsolutePath());
                continue;
            }
            if(f.length()>50)
                FileUtils.deleteFile(f);
            else
                filesList.add(f);
        }

    }

    public static class FileComparator implements Comparator<File>{

        public int compare(File first,File second){
            return first.getName().compareTo(second.getName());
        }
    }

    public static void deleteFile(File file) {
        if (!file.delete()) System.out.println("Can not delete file with name " + file.getName());
    }
}
