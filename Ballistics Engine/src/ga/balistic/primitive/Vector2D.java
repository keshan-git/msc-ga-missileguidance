package ga.balistic.primitive;

/**
 *
 *
 */
public class Vector2D
{
    private double x;
    private double y;

    public Vector2D(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }
    
    public SingleParam getTangentAngle()
    {
        double angle = y / x;
        return new SingleParam(angle);
    }
    
    public SingleParam getAngle()
    {
        double angle = (Math.atan(y / x) * 180 ) / Math.PI;
        return new SingleParam(angle);
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Vector2D)
        {
            Vector2D vector2D = (Vector2D)o;
            
            if ((vector2D.getX() == getX()) && (vector2D.getY() == getY()))
            {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public String toString()
    {
        return "Vector2D [ " + x + ", " + y + " ]";
    }
    
    
}
