package com.javatechnics;

import java.util.concurrent.ForkJoinPool;


public class ForkJoin {
    private static final int HEIGHT = 100;
    private static final int WIDTH = 100;
    private static final int[] source = new int[WIDTH * HEIGHT];
    private static final int[] dest = new int[HEIGHT * WIDTH];

    public static void main(String[] args) {

        for (int pixel = 0; pixel < WIDTH * HEIGHT; pixel++)
        {
            source[pixel] = (int) (Math.random() * Integer.MAX_VALUE);
        }

        System.out.println("Stop watch starting");
        long startTime = System.nanoTime();
        ForkJoinPool pool = new ForkJoinPool();
        ForkBlur forkBlur = new ForkBlur(source, dest, 0, source.length);
        pool.invoke(forkBlur);

        long stopTime = System.nanoTime();

        System.out.println("ForkJoin time is : " + (stopTime - startTime) / 1000 + "ms");

        ForkBlur singleThreadFB = new ForkBlur(source, dest, 0, source.length);

        startTime = System.nanoTime();
        singleThreadFB.computeDirectly();
        stopTime = System.nanoTime();

        System.out.println("Single thread time is : " + (stopTime - startTime) / 1000 + "ms");


    }
}
