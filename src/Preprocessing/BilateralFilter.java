/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Preprocessing;

public class BilateralFilter {

    private final double sigmaD;
    private final double sigmaR;

    private final int kernelRadius;

    private final double[][] kernelD;
    private final double kernelR[];

    public BilateralFilter(double sigmaD, double sigmaR) {
        this.sigmaD = sigmaD;
        this.sigmaR = sigmaR;

        double sigmaMax = Math.max(sigmaD, sigmaR);
        this.kernelRadius = (int) Math.ceil(2 * sigmaMax);

        // this will always be an odd number, i.e. {1,3,5,7,9,...}
        int kernelSize = kernelRadius * 2 + 1;
        int center = (kernelSize - 1) / 2;

        this.kernelD = new double[kernelSize][kernelSize];

        for (int x = -center; x < -center + kernelSize; x++) {
            for (int y = -center; y < -center + kernelSize; y++) {
                kernelD[x + center][y + center] = gauss2(sigmaD, x, y);
            }
        }

        kernelR = new double[256];
        for (int i = 0; i < 256; i++) {
            kernelR[i] = gauss(sigmaR, i);
        }
    }

    public GrayImage bruteForce(GrayImage source) {
        long in = System.currentTimeMillis();
        System.out.println("Applying Bilateral Filter with sigmaD = " + sigmaD + ", sigmaR = " + sigmaR + " and kernelRadius " + kernelRadius);

        GrayImage result = new GrayImage(source.w, source.h);
        
        int[][] ra = source.getRPixels();
        int[][] ga = source.getGPixels();
        int[][] ba = source.getBPixels();
        
        for (int i = 0; i < source.w; i++) {
            for (int j = 0; j < source.h; j++) {
                int r = apply(i, j, ra);
                int g = apply(i, j, ga);
                int b = apply(i, j, ba);

                
                int val = r | g << 8 | b << 16;
                result.setPixel(i, j, val);
            }
        }
        long out = System.currentTimeMillis();
        int time = (int) (out - in) / 1000;
        System.out.println("Applying Bilateral Filter finished in " + time + " sec.");
        return result;
    }

    private int apply(int i, int j, int[][] pixels) {

        double sum = 0;
        double totalWeight = 0;

        int mMax = i + kernelRadius;
        int nMax = j + kernelRadius;
        double weight;

        for (int m = i - kernelRadius; m < mMax; m++) {
            for (int n = j - kernelRadius; n < nMax; n++) {
                if (isInsideBoundaries(m, n, pixels)) {
                    weight = kernelD[i - m + kernelRadius][j - n + kernelRadius] * similarity(pixels[m][n], pixels[i][j]);
                    totalWeight += weight;
                    sum += (weight * pixels[m][n]);
                }
            }
        }
        return (int) Math.floor(sum / totalWeight);
    }

    // basicaly b filter
    private double similarity(int p, int s) {
        return this.kernelR[Math.abs(p - s)];
    }

    private double gauss(double sigma, int x) {
        return Math.exp(-((x) / (2 * sigma * sigma)));
    }

    private double gauss2(double sigma, int x, int y) {
        return Math.exp(-((x * x + y * y) / (2 * sigma * sigma)));
    }

    private boolean isInsideBoundaries(int m, int n, int[][] pixels) {
        return m >= 0 && n >= 0 && pixels.length > m && pixels[0].length > n;
    }

    public double getSigmaD() {
        return sigmaD;
    }

    public double getSigmaR() {
        return sigmaR;
    }

}

