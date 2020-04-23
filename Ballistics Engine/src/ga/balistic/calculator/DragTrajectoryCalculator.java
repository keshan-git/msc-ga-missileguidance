package ga.balistic.calculator;

import static ga.balistic.calculator.TrajectoryCalculator.G;
import ga.balistic.primitive.Position2D;
import ga.balistic.primitive.SingleParam;
import ga.balistic.primitive.Vector2D;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 *
 */
public class DragTrajectoryCalculator extends BasicTrajectoryCalculator
{
    public final static double X_ERROR = 100;
    public final static double Y_ERROR = 100;
        
    private final SingleParam mass;
    private final SingleParam massDensity;
    private final SingleParam dragCoefficient;
    private final SingleParam referenceArea;

    private Position2D initialPosition;

    public void setInitialPosition(Position2D initialPosition)
    {
        this.initialPosition = initialPosition;
    }

    public Position2D getInitialPosition()
    {
        return initialPosition;
    }

    // http://en.wikipedia.org/wiki/Density
    // https://sites.google.com/site/technicalarchery/technical-discussions-1/drag-coefficients-of-bullets-arrows-and-spears
    // http://www.imi-israel.com/vault/documents/m454.pdf
    public DragTrajectoryCalculator(SingleParam initialHeight, SingleParam mass,
                SingleParam massDensity, SingleParam dragCoefficient, SingleParam referenceArea)
    {
        super(initialHeight);
        this.mass = mass;
        this.massDensity = massDensity;
        this.dragCoefficient = dragCoefficient;
        this.referenceArea = referenceArea;
    }

    // http://en.wikipedia.org/wiki/Drag_equation
    /**
     * FD  : is the drag force, which is by definition the force component in the
     *       direction of the flow velocity
     * rho : is the mass density of the fluid
     * v   : is the velocity of the object relative to the fluid
     * A   : is the reference area
     * CD  : is the drag coefficient – a dimensionless coefficient related to the
     *       object's geometry and taking into account both skin friction and form drag
     * 
     * FD = 0.5 * rho * v * v * CD * A
     * FD = k * v * v
     * k = 0.5 * rho * CD * A
     */
    public SingleParam getAirResistance()
    {
        double airResistance = 0.5 * massDensity.getValue() * dragCoefficient.getValue() 
                                   * referenceArea.getValue();
        
        return new SingleParam(airResistance);
    }
    
    // http://en.wikipedia.org/wiki/Trajectory_of_a_projectile
    @Override
    public Vector2D getTragectorySpeed(Vector2D initialSpeed, SingleParam time)
    {
        SingleParam airResistance = getAirResistance();
                
        // Vx = Vx0 * e pow(-kt/m)
        double vx = initialSpeed.getX() * Math.pow(Math.E, ((-1 * airResistance.getValue() * time.getValue()) / mass.getValue()));
        
        // Vy = -mg/k + ((Vy0) + mg/k) * e pow (-kt/m)
        double vy = ((-1 * mass.getValue() * G) / airResistance.getValue()) +
                ((initialSpeed.getY() + (mass.getValue() * G) / airResistance.getValue()) *
                Math.pow(Math.E, (-1 * airResistance.getValue() * time.getValue()) / mass.getValue()));
        
        return new Vector2D(vx, vy);
    }
    
    // http://en.wikipedia.org/wiki/Trajectory_of_a_projectile
    @Override
    public Position2D getTrajectoryPosition(Vector2D initialSpeed, SingleParam time)
    {
        SingleParam airResistance = getAirResistance();
                
        // Sx = (m/k) * Vxo * (1 - epow (-kt/m))
        double distanceX = (mass.getValue() / airResistance.getValue()) * initialSpeed.getX() *
                (1 - (Math.pow(Math.E, ((-1 * airResistance.getValue() * time.getValue()) / mass.getValue()))));
        
        // Sy = (-mgt/k) + (m/k)(vyo + mg/k)(1 - epow (-kt/m))
        double distanceY = (-1 * mass.getValue() * G * time.getValue()) / airResistance.getValue() +
                ((mass.getValue() / airResistance.getValue()) *
                (initialSpeed.getY() + ((mass.getValue() * G) / airResistance.getValue())) *
                (1 - (Math.pow(Math.E, ((-1 * airResistance.getValue() * time.getValue()) / mass.getValue())))));
        
        return new Position2D(initialPosition.getX() + distanceX, initialPosition.getY() + distanceY);
    }
    
    private ArrayList<SingleParam> getDefaultCharge(Position2D targetPosition)
    {
        //54.90, 225.34, 880.57, 1259.52, 4990.60, 21455.28
        ArrayList<SingleParam> distances = new ArrayList<>();
        distances.add(new SingleParam(54.90));
        distances.add(new SingleParam(225.34));
        distances.add(new SingleParam(880.57));
        distances.add(new SingleParam(1259.52));
        distances.add(new SingleParam(4990.60));
        distances.add(new SingleParam(21455.28));
        
        //22.2, 46.25, 92.5, 111.0, 222.0, 462.5
        ArrayList<SingleParam> charges = new ArrayList<>();
        charges.add(new SingleParam(22.20));
        charges.add(new SingleParam(46.25));
        charges.add(new SingleParam(92.50));
        charges.add(new SingleParam(111.0));
        charges.add(new SingleParam(222.0));
        charges.add(new SingleParam(462.5));

        ArrayList<SingleParam> selectedCharges = new ArrayList<>();
        
        for (int i = 0; i < distances.size(); i++)
        {
            if ((targetPosition.getX() - initialPosition.getX()) < distances.get(i).getValue())
            {
                selectedCharges.add(charges.get(i));
            }
        }

        return selectedCharges;           
    }
    
    public Vector2D calculateInitialSpeed(Position2D initialPosition, Position2D targetPosition)
    {
        // for each charge level
        for (SingleParam charge : getDefaultCharge(targetPosition))
        {
            // for each angle
            for (int i = 45; i < 89; i++)
            {
                SingleParam realTime = new SingleParam(0.1);
                Vector2D speed = new Vector2D(charge.getValue() * Math.cos((i / 180.0 ) * Math.PI),
                        charge.getValue() * Math.sin((i / 180.0 ) * Math.PI));
                
                while (true)
                {
                    Position2D position = getTrajectoryPosition(speed, realTime);
                    realTime = realTime.add(0.1);
                    
                    if (position.getY() < 0)
                    {
                        if (Math.abs(position.getX() - targetPosition.getX()) < X_ERROR)
                        {
                            Vector2D selectedInitialSpeed = new Vector2D(charge.getValue() * Math.cos((i / 180.0 ) * Math.PI),
                                                                charge.getValue() * Math.sin((i / 180.0 ) * Math.PI));
                            return selectedInitialSpeed;
                        }
                        
                        break;
                    }
                    
                    if (Math.abs(position.getX() - targetPosition.getX()) < X_ERROR)
                    {
                        if (Math.abs(position.getY() - targetPosition.getY()) < Y_ERROR)
                        {
                            Vector2D selectedInitialSpeed = new Vector2D(charge.getValue() * Math.cos((i / 180.0 ) * Math.PI),
                                                                charge.getValue() * Math.sin((i / 180.0 ) * Math.PI));
                            return selectedInitialSpeed;
                        }
                        
                        break;
                    }
                }
            }
        }
        
        return null;
    }
    
    public SingleParam calculateError(Vector2D speed, Position2D targetPosition)
    {
        SingleParam realTime = new SingleParam(0.1);
        ArrayList<SingleParam> distanceList = new ArrayList<>();
        
        while (true)
        {
            Position2D position = getTrajectoryPosition(speed, realTime);
            realTime = realTime.add(0.1);
            distanceList.add(position.getDistance(targetPosition));
            
            if (position.getY() < 0)
            {
                Collections.sort(distanceList);
                return distanceList.get(0);
            }
        }
    }
}
