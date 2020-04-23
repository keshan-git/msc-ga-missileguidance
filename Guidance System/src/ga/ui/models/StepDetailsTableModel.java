package ga.ui.models;

import ga.listeners.StepDetails;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 *
 */
public class StepDetailsTableModel extends AbstractTableModel
{
    private final String[] columnNames = new String[]
        {"Generation", "VelocityX", "VelocityY", "Best Fitness", "Worst Fitness", "Mean", "Variance"};
    private final ArrayList<StepDetails> dataSet = new ArrayList<>();
    
    public void addStepDetails(StepDetails stepDetails)
    {
        dataSet.add(stepDetails);
        fireTableDataChanged();
    }
    
    public void clearStepDetails()
    {
        dataSet.clear();
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount()
    {
        return dataSet.size();
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        StepDetails stepDetails = dataSet.get(rowIndex);
        
        switch (columnIndex)
        {
            case 0 : return stepDetails.getGeneration();
            case 1 : return stepDetails.getVelocityX();
            case 2 : return stepDetails.getVelocityY();
            case 3 : return stepDetails.getBestFitness();
            case 4 : return stepDetails.getWorstFitness();
            case 5 : return stepDetails.getMean();
            case 6 : return stepDetails.getVariance();
        }
        
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columIndex)
    {
        switch (columIndex)
        {
            case 0 : return Integer.class;
            default : return Double.class;      
        }
    }

    @Override
    public String getColumnName(int columIndex)
    {
        return columnNames[columIndex];
    }
    
    
    
}
