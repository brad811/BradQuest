package main;

import java.util.Random;

public class PerlinNoise
{
	public static long seed = 10;
    Random random = new Random(seed);
    public static int pointsComplete = 0;
    public static int pointsTotal = 1;
    
    public float[][] GenerateWhiteNoise(int width, int height)
    {            
        float[][] noise = new float[width][height];

        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                noise[i][j] = (float)random.nextDouble() % 1;
            }
            pointsComplete++;
        }

        return noise;
    }

    public float Interpolate(float x0, float x1, float alpha)
    {
        return x0 * (1 - alpha) + alpha * x1;
    }

    public int GetColor(int gradientStart, int gradientEnd, float t)
    {        
        float u = 1 - t;
        
        return (int)(gradientStart * u + gradientEnd * t);
    }

    public int[][] MapGradient(int gradientStart, int gradientEnd, float[][] perlinNoise)
    {
        int width = perlinNoise.length;
        int height = perlinNoise[0].length;

        int[][] image = new int[width][height];

        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                image[i][j] = GetColor(gradientStart, gradientEnd, perlinNoise[i][j]);
            }
            pointsComplete++;
        }

        return image;
    }

    public float[][] GenerateSmoothNoise(float[][] baseNoise, int octave)
    {
        int width = baseNoise.length;
        int height = baseNoise[0].length;

        float[][] smoothNoise = new float[width][height];

        int samplePeriod = 1 << octave; // calculates 2 ^ k
        float sampleFrequency = 1.0f / samplePeriod;

        for (int i = 0; i < width; i++)
        {
            //calculate the horizontal sampling indices
            int sample_i0 = (i / samplePeriod) * samplePeriod;
            int sample_i1 = (sample_i0 + samplePeriod) % width; //wrap around
            float horizontal_blend = (i - sample_i0) * sampleFrequency;

            for (int j = 0; j < height; j++)
            {
                //calculate the vertical sampling indices
                int sample_j0 = (j / samplePeriod) * samplePeriod;
                int sample_j1 = (sample_j0 + samplePeriod) % height; //wrap around
                float vertical_blend = (j - sample_j0) * sampleFrequency;

                //blend the top two corners
                float top = Interpolate(baseNoise[sample_i0][sample_j0],
                    baseNoise[sample_i1][sample_j0], horizontal_blend);

                //blend the bottom two corners
                float bottom = Interpolate(baseNoise[sample_i0][sample_j1],
                    baseNoise[sample_i1][sample_j1], horizontal_blend);

                //final blend
                smoothNoise[i][j] = Interpolate(top, bottom, vertical_blend);                    
            }
            pointsComplete++;
        }
        
        return smoothNoise;
    }

    public float[][] GeneratePerlinNoise(float[][] baseNoise, int octaveCount)
    {
        int width = baseNoise.length;
        int height = baseNoise[0].length;

        float[][][] smoothNoise = new float[octaveCount][][]; //an array of 2D arrays containing

        float persistance = 0.7f;
        
        //generate smooth noise
        for (int i = 0; i < octaveCount; i++)
        {
            smoothNoise[i] = GenerateSmoothNoise(baseNoise, i);
        }

        float[][] perlinNoise = new float[width][height]; //an array of floats initialised to 0

        float amplitude = 1.0f;
        float totalAmplitude = 0.0f;
        
        //blend noise together
        for (int octave = octaveCount - 1; octave >= 0; octave--)
        {
            amplitude *= persistance;
            totalAmplitude += amplitude;
            
            for (int i = 0; i < width; i++)
            {
                for (int j = 0; j < height; j++)
                {
                    perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
                }
            }
        }
        
        //normalisation
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                perlinNoise[i][j] /= totalAmplitude;
            }
        }

        return perlinNoise;
    }

    public float[][] GeneratePerlinNoise(int width, int height, int octaveCount)
    {
        float[][] baseNoise = GenerateWhiteNoise(width, height);
        return GeneratePerlinNoise(baseNoise, octaveCount);
    }
    
    public int[][] GenerateGradientMap()
    {
        int width = Game.mapSize;
        int height = Game.mapSize;
        int octaveCount = 6;

        int gradientStart = 0;
        int gradientEnd = 100;
        
        pointsTotal = Game.mapSize*octaveCount + 2*Game.mapSize;
        
        float[][] perlinNoise = GeneratePerlinNoise(width, height, octaveCount);
        int[][] perlinImage = MapGradient(gradientStart, gradientEnd, perlinNoise);
        
        return perlinImage;
    }
    
}
