package org.performance;

import org.junit.jupiter.api.Test;
import org.performance.MultiThreadSearchUtil;
import org.performance.SimpleSearchUtil;


import java.io.UnsupportedEncodingException;
import java.util.Collections;

import static org.junit.Assert.*;



public class JunitTest {


    String pathname = "F:\\Download";
    String regex = "^(\\s?\\w?)*.xlsx";

    public void benchmarkSimpleAverage(int times) throws UnsupportedEncodingException {
        long summaryTimeOfSingle = 0;
        for (int i = 0; i < times; i++) {
            SimpleSearchUtil ssu = new SimpleSearchUtil();
            ssu.launch(pathname, regex);
            summaryTimeOfSingle = +summaryTimeOfSingle + ssu.summaryTime;
        }
        System.out.println("Simple average time : " + summaryTimeOfSingle / times + " ms");

    }

    @Test
    public void benchmarkSimple() throws UnsupportedEncodingException {
        long summaryTimeOfSingle = 0;
        SimpleSearchUtil ssu = new SimpleSearchUtil();
        ssu.launch(pathname, regex);
        summaryTimeOfSingle = +summaryTimeOfSingle + ssu.summaryTime;
        System.out.println("Average time : " + summaryTimeOfSingle + " ms");
    }


    public void benchmarkMultiAverage(int times, int sizeOfPool) {
        long summaryTimeOfMulti = 0;

        for (int i = 0; i < times; i++) {
            MultiThreadSearchUtil mtsu = new MultiThreadSearchUtil();
            mtsu.launch(pathname, regex, sizeOfPool);
            summaryTimeOfMulti =+ summaryTimeOfMulti + mtsu.summaryTime;
        }
        System.out.println("Multi average time  : " + summaryTimeOfMulti / times + " ms");
    }

    @Test
    public void benchmarkMulti() {
        long summaryTimeOfMulti = 0;

        MultiThreadSearchUtil mtsu = new MultiThreadSearchUtil();
        mtsu.launch(pathname, regex, 10);
        summaryTimeOfMulti =+ summaryTimeOfMulti + mtsu.summaryTime;
        System.out.println("Average time : " + summaryTimeOfMulti + " ms");
    }

    @Test
    public void compareResults() throws UnsupportedEncodingException {
        SimpleSearchUtil ssu = new SimpleSearchUtil();
        ssu.launch(pathname, regex);

        MultiThreadSearchUtil mtsu = new MultiThreadSearchUtil();
        mtsu.launch(pathname, regex, 10);

        Collections.sort(ssu.fileList);
        Collections.sort(mtsu.fileList);

        assertEquals(ssu.fileList, mtsu.fileList);
    }

    @Test
    public void compareAverageWithDifferentSize() throws UnsupportedEncodingException {

        int numberOfExp = 200;

        int sizeOfPool = 10;

        regex = "^(\\s?\\w?)*.docx";

        pathname = "F:\\Ucheba";

        System.out.println("Algorithm without thread pool: ");
        benchmarkSimpleAverage(numberOfExp);


        System.out.println("Size of pool: " + sizeOfPool);
        benchmarkMultiAverage(numberOfExp, sizeOfPool);

        sizeOfPool = 5;
        System.out.println("Size of pool: " + sizeOfPool);
        benchmarkMultiAverage(numberOfExp, sizeOfPool);

        sizeOfPool = 2;
        System.out.println("Size of pool: " + sizeOfPool);
        benchmarkMultiAverage(numberOfExp, sizeOfPool);

    }

}
