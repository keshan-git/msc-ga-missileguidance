package ga.balistic.primitive;

/**
 *
 *
 */
public class Position3D extends Vector3D
{

    public Position3D(double x, double y, double z)
    {
        super(x, y, z);
    }

    public Position2D getPosition2D()
    {
        return new Position2D(getX(), getY());
    }

    public SingleParam getDistance(Position3D position3D)
    {
        return new SingleParam(Math.sqrt(Math.pow(position3D.getX() - this.getX(), 2) +
                            Math.pow(position3D.getY() - this.getY(), 2) +
                            Math.pow(position3D.getZ() - this.getZ(), 2)));
    }


}
