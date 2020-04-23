package ga.ui.panels;


import ga.canvas.Canvas;
import ga.balistic.primitive.Position2D;
import ga.balistic.primitive.Vector2D;

/**
 *
 *
 */
public class CanvasPanel extends javax.swing.JPanel
{

    public CanvasPanel()
    {
        initComponents();
    }

    public void setWorldSize(double worldWidth, double worldHeight)
    {
        canvas.setWorldSize(worldWidth, worldHeight);
    }

    public void resetCanvs()
    {
        canvas.resetCanvs();
    }
    
    public void resetPath()
    {
        canvas.resetPath();
    }
    
    public void addPosition2D(Position2D position)
    {
        canvas.addPosition2D(position);
    }
    
    public void setSpeed(Vector2D currentSpeed)
    {
        canvas.setSpeed(currentSpeed);
    }
    
    public void setInitialSpeed(Vector2D initSpeed)
    {
        canvas.setInitialSpeed(initSpeed);
    }
    
    public void setTargetPosition2D(Position2D position)
    {
        canvas.setTargetPosition2D(position);
    }
    
    public void setLaserTargetPosition2D(Position2D position)
    {
        canvas.setLaserTargetPosition2D(position);
    }
    
    public void setInitialPosition(Position2D initialPosition)
    {
        canvas.setInitialPosition(initialPosition);
    }
    
    public void setGuidencePoint(Position2D guidencePoint)
    {
        canvas.setGuidencePoint(guidencePoint);
    }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        canvas = new Canvas();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Trajectory Simulation"));

        javax.swing.GroupLayout canvasLayout = new javax.swing.GroupLayout(canvas);
        canvas.setLayout(canvasLayout);
        canvasLayout.setHorizontalGroup(
            canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 388, Short.MAX_VALUE)
        );
        canvasLayout.setVerticalGroup(
            canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 277, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(canvas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(canvas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Canvas canvas;
    // End of variables declaration//GEN-END:variables

}