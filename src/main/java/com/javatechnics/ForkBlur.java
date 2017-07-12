package com.javatechnics;

import java.util.concurrent.RecursiveAction;

public class ForkBlur extends RecursiveAction {

    private int [] source;
    private int [] destination;
    private int start;
    private int length;

    private int blurWidth = 15;

    public static final int threshold = 1000;

    public ForkBlur(final int[] source, final int[] destination, final int start, final int length) {
        this.source = source;
        this.destination = destination;
        this.start = start;
        this.length = length;
    }

    protected void compute() {
        if (length < threshold)
        {
            computeDirectly();
        }
        else
        {
            int split = length / 2;

            invokeAll(new ForkBlur(source, destination, start, split),
                    new ForkBlur(source, destination, split, length - split));
        }
    }

    public void computeDirectly()
    {
        int sidePixel = (blurWidth - 1) / 2;
        for (int index = start; index < start + length; index++)
        {
            float rt = 0, gt = 0, bt = 0;
            for (int mi = -sidePixel; mi <= sidePixel; mi++)
            {
                int mindex = Math.min(Math.max(mi + index, 0), source.length - 1);
                int pixel = source[mindex];
                rt += (float)((pixel & 0x00ff0000) >> 16) / blurWidth;
                gt += (float)((pixel & 0x0000ff00) >> 8) / blurWidth;
                bt += (float)((pixel & 0x000000ff)) / blurWidth;
            }

            destination[index] = (0xff000000) |
                    (((int)rt) << 16) |
                    (((int)gt) << 8) |
                    (int)bt;
        }
    }


}
