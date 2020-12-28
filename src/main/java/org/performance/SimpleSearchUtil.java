package org.performance;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

public class SimpleSearchUtil {

     long summaryTime;
     ArrayList<File> fileList = new ArrayList<>();

    public static void main(String[] args) throws UnsupportedEncodingException {
        SimpleSearchUtil ssu = new SimpleSearchUtil();
        ssu.launch("F:\\Downloads", "^(\\s?\\w?)*.docx");
        for (File file : ssu.fileList) {
            System.out.println("Найденный файл: " +
                    new String(file.getAbsolutePath().getBytes("UTF-8")));
        }
    }


    public Boolean launch(String pathname, String regex) throws UnsupportedEncodingException {
        long timeBefore = System.currentTimeMillis();
        searchFiles(new File(pathname), fileList, regex);
        Collections.sort(fileList);
        summaryTime = System.currentTimeMillis() - timeBefore;
        return true;
    }


    private void searchFiles(File root, ArrayList<File> fileList, String regex) {
        if (root.isDirectory()) {
            File[] currentDirectoryFiles = root.listFiles(); // return Files[] in cur dir, list() return String[]
            if (currentDirectoryFiles != null) {
                for (File file : currentDirectoryFiles) {
                    if (file.isDirectory()) {
                        searchFiles(file, fileList, regex);
                    } else {
                        if (file.getName().toLowerCase().matches(regex)) {
                            fileList.add(file);
                        }
                    }
                }
            }
        }
    }



}
