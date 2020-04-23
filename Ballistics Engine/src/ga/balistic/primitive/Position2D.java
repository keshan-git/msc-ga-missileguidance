package ga.balistic.primitive;

/**
 *
 *
 */
public class Position2D extends Vector2D 
{

    public Position2D(Position2D position2D)
    {
        this(position2D.getX(), position2D.getY());
    }
        
    public Position2D(double x, double y)
    {
        super(x, y);
    }

    public SingleParam getDistance(Position2D position2D)
    {
        return new SingleParam(Math.sqrt(Math.pow(position2D.getX() - this.getX(), 2) +
                            Math.pow(position2D.getY() - this.getY(), 2)));
    }
    

   
}
