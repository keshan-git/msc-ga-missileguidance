package ga.listeners;

/**
 *
 *
 */
public class StepDetails
{
    private int generation;
    private double velocityX;
    private double velocityY;
    private double bestFitness;
    private double worstFitness;
    private double mean;
    private double variance;

    public StepDetails(int generation, double velocityX, double velocityY, double bestFitness, double worstFitness, double mean, double variance)
    {
        this.generation = generation;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.bestFitness = bestFitness;
        this.worstFitness = worstFitness;
        this.mean = mean;
        this.variance = variance;
    }

    public int getGeneration()
    {
        return generation;
    }

    public void setGeneration(int generation)
    {
        this.generation = generation;
    }

    public double getVelocityX()
    {
        return velocityX;
    }

    public void setVelocityX(double velocityX)
    {
        this.velocityX = velocityX;
    }

    public double getVelocityY()
    {
        return velocityY;
    }

    public void setVelocityY(double velocityY)
    {
        this.velocityY = velocityY;
    }

    public double getBestFitness()
    {
        return bestFitness;
    }

    public void setBestFitness(double bestFitness)
    {
        this.bestFitness = bestFitness;
    }

    public double getWorstFitness()
    {
        return worstFitness;
    }

    public void setWorstFitness(double worstFitness)
    {
        this.worstFitness = worstFitness;
    }

    public double getMean()
    {
        return mean;
    }

    public void setMean(double mean)
    {
        this.mean = mean;
    }

    public double getVariance()
    {
        return variance;
    }

    public void setVariance(double variance)
    {
        this.variance = variance;
    }
}
