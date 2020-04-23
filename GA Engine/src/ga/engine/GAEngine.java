package ga.engine;


import ga.balistic.calculator.DragTrajectoryCalculator;
import ga.balistic.primitive.Position2D;
import ga.balistic.primitive.Vector2D;
import ga.listeners.StepDetails;
import ga.listeners.StepListener;
import java.util.ArrayList;
import org.jenetics.Chromosome;
import org.jenetics.DoubleChromosome;
import org.jenetics.DoubleGene;
import org.jenetics.GeneticAlgorithm;
import org.jenetics.Genotype;
import org.jenetics.MeanAlterer;
import org.jenetics.Mutator;
import org.jenetics.NumberStatistics;
import org.jenetics.Optimize;
import org.jenetics.RouletteWheelSelector;
import org.jenetics.TournamentSelector;
import org.jenetics.termination;
import org.jenetics.util.Factory;
import org.jenetics.util.Function;



/**
 *
 *
 */
public class GAEngine
{
    private static final double SPEED_VARIANCE = 0.3;
    private static final int POPULATION_SIZE = 50;
    private static final int NUM_OF_ITERATIONS = 100;
    
    private GeneticAlgorithm<DoubleGene, Double> geneticAlgorithm;
    private DragTrajectoryCalculator dragTrajectoryCalculator;
    private Position2D targetPosition;
    private Position2D currentPosition;
    
    private ArrayList<StepListener> listeners = new ArrayList<>();
    
    public GAEngine(Position2D currentPosition, DragTrajectoryCalculator dragTrajectoryCalculator, Position2D targetPosition)
    {
        this.dragTrajectoryCalculator = dragTrajectoryCalculator;
        this.targetPosition = targetPosition;
        this.currentPosition = currentPosition;
    }
    
    public void initGA(Vector2D currentSpeed)
    {
        DoubleGene[] doubleGenesX = new DoubleGene[]
        {
            DoubleGene.of(currentSpeed.getX(), currentSpeed.getX() - (currentSpeed.getX() * SPEED_VARIANCE), currentSpeed.getX() + (currentSpeed.getX() * SPEED_VARIANCE)),
        };
        
        DoubleGene[] doubleGenesY = new DoubleGene[]
        {
            DoubleGene.of(currentSpeed.getY(), currentSpeed.getY() - (currentSpeed.getY() * SPEED_VARIANCE), currentSpeed.getY() + (currentSpeed.getY() * SPEED_VARIANCE)),
        };
        
        Factory<Genotype<DoubleGene>> genotype = Genotype.of(DoubleChromosome.of(doubleGenesX), DoubleChromosome.of(doubleGenesY));
        Function<Genotype<DoubleGene>, Double> fitnessFunction = new TargetFitnessFunction(currentPosition, dragTrajectoryCalculator, targetPosition);
        
        geneticAlgorithm = new GeneticAlgorithm<>(genotype, fitnessFunction, Optimize.MINIMUM);
        geneticAlgorithm.setPopulationSize(POPULATION_SIZE);
        geneticAlgorithm.setStatisticsCalculator(new NumberStatistics.Calculator<>());
        geneticAlgorithm.setOffspringFraction(0.8);
        geneticAlgorithm.setAlterers(new Mutator<>(0.3), new MeanAlterer<>(0.9));
        geneticAlgorithm.setSurvivorSelector(new RouletteWheelSelector<>());
        geneticAlgorithm.setOffspringSelector(new TournamentSelector<>(3));
        geneticAlgorithm.setup();  
    }

    public Vector2D getBestFittedSpeed()
    {
        geneticAlgorithm.evolve(termination.SteadyFitness(5));
        Chromosome<DoubleGene> bestGenoTypeX = geneticAlgorithm.getBestPhenotype().getGenotype().getChromosome(0);
        Chromosome<DoubleGene> bestGenoTypeY = geneticAlgorithm.getBestPhenotype().getGenotype().getChromosome(1);
        Vector2D velocity = new Vector2D(bestGenoTypeX.getGene(0).doubleValue(),
                bestGenoTypeY.getGene(0).doubleValue());
        return velocity;
    }
    
    public Vector2D getBestFittedSpeedWithSteps()
    {
        for (int i = 0; i < NUM_OF_ITERATIONS; i++)
        {
            geneticAlgorithm.evolve();

            int generation = i;
            Chromosome<DoubleGene> bestGenoTypeX = geneticAlgorithm.getBestPhenotype().getGenotype().getChromosome(0);
            Chromosome<DoubleGene> bestGenoTypeY = geneticAlgorithm.getBestPhenotype().getGenotype().getChromosome(1);
            double velocityX = bestGenoTypeX.getGene(0).doubleValue();
            double velocityY = bestGenoTypeY.getGene(0).doubleValue();
            double bestFitness = geneticAlgorithm.getStatistics().getBestFitness();
            double worstFitness = geneticAlgorithm.getStatistics().getWorstFitness();
            double mean = geneticAlgorithm.getStatistics().getAgeMean();
            double variance = geneticAlgorithm.getStatistics().getAgeVariance();

            StepDetails stepDetails = new StepDetails(generation, velocityX, velocityY, bestFitness, worstFitness, mean, variance);
            for (StepListener stepListener : listeners)
            {
                stepListener.onStepChanged(stepDetails);
            }
        }

        Chromosome<DoubleGene> bestGenoTypeX = geneticAlgorithm.getBestPhenotype().getGenotype().getChromosome(0);
        Chromosome<DoubleGene> bestGenoTypeY = geneticAlgorithm.getBestPhenotype().getGenotype().getChromosome(1);
        Vector2D velocity = new Vector2D(bestGenoTypeX.getGene(0).doubleValue(),
                bestGenoTypeY.getGene(0).doubleValue());
        System.out.println(geneticAlgorithm.getBestPhenotype().getFitness());
        return velocity;
    }
        
    public void addStepListener(StepListener stepListener)
    {
        listeners.add(stepListener);
    }
    
    public void clearStepListeners()
    {
        listeners.clear();
    }
}
