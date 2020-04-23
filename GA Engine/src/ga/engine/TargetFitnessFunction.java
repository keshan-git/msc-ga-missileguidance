package ga.engine;


import ga.balistic.calculator.DragTrajectoryCalculator;
import ga.balistic.primitive.Position2D;
import ga.balistic.primitive.SingleParam;
import ga.balistic.primitive.Vector2D;
import org.jenetics.DoubleGene;
import org.jenetics.Genotype;
import org.jenetics.util.Function;

/**
 *
 *
 */
public class TargetFitnessFunction implements Function<Genotype<DoubleGene>, Double>
{
    private DragTrajectoryCalculator dragTrajectoryCalculator;
    private Position2D targetPosition;
    private Position2D currentPosition;

    public TargetFitnessFunction(Position2D currentPosition, DragTrajectoryCalculator dragTrajectoryCalculator, Position2D targetPosition)
    {
        this.dragTrajectoryCalculator = dragTrajectoryCalculator;
        this.targetPosition = targetPosition;
        this.currentPosition = currentPosition;
        dragTrajectoryCalculator.setInitialPosition(currentPosition);
    }

    @Override
    public Double apply(Genotype<DoubleGene> genotype)
    {
        double speedX = genotype.getChromosome(0).getGene(0).doubleValue();
        double speedY = genotype.getChromosome(1).getGene(0).doubleValue();
        Vector2D speed = new Vector2D(speedX, speedY);
        
        SingleParam errorValue = dragTrajectoryCalculator.calculateError(speed, new Position2D(targetPosition));
        
        return errorValue.getValue();
    }   
}
