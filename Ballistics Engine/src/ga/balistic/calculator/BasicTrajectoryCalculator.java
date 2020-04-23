package ga.balistic.calculator;

import ga.balistic.primitive.Position2D;
import ga.balistic.primitive.SingleParam;
import ga.balistic.primitive.Vector2D;

/**
 *
 *
 */
public class BasicTrajectoryCalculator implements TrajectoryCalculator
{
    private SingleParam initialHeight = null;

    public BasicTrajectoryCalculator(SingleParam initialHeight)
    {
        this.initialHeight = initialHeight;
    }

    public BasicTrajectoryCalculator()
    {
  
    }

    public SingleParam getInitialHeight()
    {
        return initialHeight;
    }

    public Vector2D getSpeedVector(SingleParam speed, SingleParam angle)
    {
        return new Vector2D(speed.getValue() * Math.cos(Math.toRadians(angle.getValue())),
                            speed.getValue() * Math.sin(Math.toRadians(angle.getValue())));
    }
    
    public SingleParam getTimeAtMaxOrdinate(Vector2D initialSpeed)
    {
        // ^ v = u + at
        double time = initialSpeed.getY() / G;
        return new SingleParam(time);
    }
        
    public Position2D getMaxOrdinate(Vector2D initialSpeed)
    {
        return getMaxOrdinate(initialSpeed, initialHeight);
    }
    
    public Position2D getMaxOrdinate(Vector2D initialSpeed, SingleParam initialHeight)
    {
        return getTrajectoryPosition(initialSpeed, initialHeight, getTimeAtMaxOrdinate(initialSpeed));
    }
    
    public Position2D getTrajectoryPosition(Vector2D initialSpeed, SingleParam time)
    {
        return getTrajectoryPosition(initialSpeed, initialHeight, time);
    }
    
    public Position2D getTrajectoryPosition(Vector2D initialSpeed, SingleParam initialHeight,
                                            SingleParam time)
    {
        // --> S = ut + 0.5*a*t*t
        double distanceX = initialSpeed.getX() * time.getValue();
        
        // ^ S = ut + 0.5*a*t*t
        double distanceY = initialSpeed.getY() * time.getValue() - 0.5 * G * Math.pow(time.getValue(), 2);
        
        return new Position2D(distanceX, initialHeight.add(distanceY).getValue());
    }
    
    //http://en.wikipedia.org/wiki/Trajectory_of_a_projectile
    public SingleParam getTotalHorizontalDistance(Vector2D initialSpeed) 
    {
        return getTotalHorizontalDistance(initialSpeed, initialHeight);
    }
        
    public SingleParam getTotalHorizontalDistance(Vector2D initialSpeed, SingleParam initialHeight) 
    {
        double distance = (initialSpeed.getX() / G) * (initialSpeed.getY() 
                + Math.sqrt(Math.pow(initialSpeed.getY() , 2) + 2 * G * initialHeight.getValue()));
        return new SingleParam(distance);
    }
    
    public SingleParam getTimeOfFlight(Vector2D initialSpeed)
    {
        return getTimeOfFlight(initialSpeed, initialHeight);
    }
    
    public SingleParam getTimeOfFlight(Vector2D initialSpeed, SingleParam initialHeight)
    {
        double tof = (initialSpeed.getY() + (Math.sqrt(Math.pow(initialSpeed.getY(), 2)) 
                + 2 * G * initialHeight.getValue()) ) / G;
        return new SingleParam(tof);    
    }
    
    public SingleParam getHeightAt(Vector2D initialSpeed, SingleParam distanceX)
    {
        return getHeightAt(initialSpeed, initialHeight, distanceX);
    }
        
    public SingleParam getHeightAt(Vector2D initialSpeed, SingleParam initialHeight, SingleParam distanceX)
    {
        double height = initialHeight.getValue() + (distanceX.getValue() * (initialSpeed.getTangentAngle().getValue()))
                             - ((G * Math.pow(distanceX.getValue(), 2)) / (2 * Math.pow(initialSpeed.getX(), 2)));
        return new SingleParam(height);
    }
    
    public Vector2D getTragectorySpeed(Vector2D initialSpeed, SingleParam time)
    {
        return getTragectorySpeed(initialSpeed, initialHeight, time);
    }
                
    public Vector2D getTragectorySpeed(Vector2D initialSpeed, SingleParam initialHeight,
                                            SingleParam time)
    {
        double vx = initialSpeed.getX();
        double vy = initialSpeed.getY()  - (G * time.getValue());
        
        return new Vector2D(vx, vy);
    }
}

