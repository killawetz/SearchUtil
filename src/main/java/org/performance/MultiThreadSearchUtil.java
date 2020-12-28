package org.performance;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadSearchUtil {

    ExecutorService executor;

    ArrayList<File> fileList = new ArrayList<>();
    long summaryTime;

    public static void main(String[] args) throws UnsupportedEncodingException {
        MultiThreadSearchUtil mtsu = new MultiThreadSearchUtil();
        mtsu.launch("F:\\Ucheba", "task2_timetable.txt",
                10);
        for (File file : mtsu.fileList) {
            System.out.println("Найденный файл: " +
                    new String(file.getAbsolutePath().getBytes("UTF-8")));
        }
    }

    public boolean launch(String pathname, String regex, int sizeOfPool) {
        executor = Executors.newFixedThreadPool(sizeOfPool);
        long timeBefore = System.currentTimeMillis();
        searchFilesWithThread(new File(pathname), fileList, regex);
        executor.shutdown();

        while (true) {
            if (executor.isTerminated()) {
                summaryTime = System.currentTimeMillis() - timeBefore;
                break;
            }
        }
        return true;
    }


    private void searchFilesWithThread(File root, ArrayList<File> fileList, String regex) {
        if (root.isDirectory()) {
            File[] currentDirectoryFiles = root.listFiles();
            if (currentDirectoryFiles != null) {
                for (File file : currentDirectoryFiles) {
                    if (file.isDirectory()) {
                        executor.execute(new SearchThread(file, regex));
                    } else {
                        if (file.getName().toLowerCase().matches(regex)) {
                            addFileToList(file);
                        }
                    }
                }
            }
        }

    }

    private synchronized void addFileToList(File file) {
        fileList.add(file);
    }


    class SearchThread implements Runnable {

        File currentRoot;
        String regex;

        SearchThread(File currentRoot, String regex) {
            this.currentRoot = currentRoot;
            this.regex = regex;
        }

        @Override
        public void run() {
            searchFiles(currentRoot, regex);
        }

        private void searchFiles(File root, String regex) {
            if (root.isDirectory()) {
                File[] currentDirectoryFiles = root.listFiles(); // return Files[] in cur dir, list() return String[]
                if (currentDirectoryFiles != null) {
                    for (File file : currentDirectoryFiles) {
                        if (file.isDirectory()) {
                            searchFiles(file, regex);
                        } else {
                            if (file.getName().toLowerCase().matches(regex)) {
                                addFileToList(file);
                            }
                        }
                    }
                }
            }
        }
    }
}

