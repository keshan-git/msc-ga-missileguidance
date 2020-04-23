package ga.balistic.primitive;

/**
 *
 *
 */
public class SingleParam implements Comparable<SingleParam>
{
    private double value;

    public SingleParam(double value)
    {
        this.value = value;
    }
    
    public SingleParam(SingleParam singleParam)
    {
        this.value = singleParam.getValue();
    }

    public double getValue()
    {
        return value;
    }

    public void setValue(double value)
    {
        this.value = value;
    }

    public SingleParam getDifference(SingleParam distance)
    {
        return new SingleParam(Math.abs(distance.getValue() - this.getValue()));
    }
    
    public SingleParam add(SingleParam distance)
    {
        return new SingleParam(distance.getValue() + this.getValue());
    }
    
    public SingleParam add(double distance)
    {
        return new SingleParam(distance + this.getValue());
    }
        
    public boolean isLessThan(SingleParam value)
    {
        return (this.compareTo(value) > 0);
    }
    
    public boolean isGreaterThan(SingleParam value)
    {
        return (this.compareTo(value) < 0);
    }
        
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof SingleParam)
        {
            SingleParam distanceParam = (SingleParam)o;
            if (distanceParam.getValue()== getValue())
            {
                return true;
            }
        }
        
        return false;
    }   

    @Override
    public int compareTo(SingleParam distance)
    {
        if (distance != null)
        {
            return (int)Math.round(this.getValue() - distance.getValue());
        }
        
        return 0;
    }

    @Override
    public String toString()
    {
        return "SingleParam[ " + value + " ]";
    }
    
    
}
