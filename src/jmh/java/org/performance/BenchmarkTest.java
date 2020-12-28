package org.performance;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.UnsupportedEncodingException;

@BenchmarkMode(Mode.All) // тестируем во всех режимах
@Warmup(iterations = 2) // число итераций для прогрева нашей функции
@Measurement(iterations = 3, batchSize = 2)
public class BenchmarkTest {

    @State(Scope.Benchmark)
    public static class BenchMarkState {
        String pathname = "F:\\Download";
        String regex = "^(\\s?\\w?)*.xlsx";
        MultiThreadSearchUtil mtsu = new MultiThreadSearchUtil();
        SimpleSearchUtil ssu = new SimpleSearchUtil();
    }

    @Benchmark
    public void twoThreadSearch(Blackhole blackhole, BenchMarkState state) {
        Boolean res = state.mtsu.launch(state.pathname, state.regex,
                2);
        blackhole.consume(res);
    }

    @Benchmark
    public void fiveThreadSearch(Blackhole blackhole, BenchMarkState state) {
        Boolean res = state.mtsu.launch(state.pathname, state.regex,
                5);
        blackhole.consume(res);
    }

    @Benchmark
    public void tenThreadSearch(Blackhole blackhole, BenchMarkState state) {
        Boolean res = state.mtsu.launch(state.pathname, state.regex,
                10);
        blackhole.consume(res);
    }

    @Benchmark
    public void simpleSearch(Blackhole blackhole, BenchMarkState state) throws UnsupportedEncodingException {
        Boolean res = state.ssu.launch(state.pathname, state.regex);
        blackhole.consume(res);
    }



    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BenchmarkTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
