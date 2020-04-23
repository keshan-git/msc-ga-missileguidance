package ga.balistic.primitive;

/**
 *
 *
 */
public class Vector3D extends Vector2D
{
    private double z;

    public Vector3D(double x, double y, double z)
    {
        super(x, y);
        this.z = z;
    }
    
    public Vector2D getVector2D()
    {
        return new Vector2D(getX(), getY());
    }
    
    public double getZ()
    {
        return z;
    }

    public void setZ(double z)
    {
        this.z = z;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Vector3D)
        {
            Vector3D vector3D = (Vector3D)o;
            
            if ((vector3D.getVector2D().equals(this.getVector2D())) 
                    && (vector3D.getZ() == this.getZ()))
            {
                return true;
            }
        }
        
        return false;
    }  
}
