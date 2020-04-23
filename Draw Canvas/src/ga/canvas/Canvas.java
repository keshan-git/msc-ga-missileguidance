package ga.canvas;


import ga.balistic.primitive.Position2D;
import ga.balistic.primitive.Vector2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

/**
 *
 *
 */
public class Canvas extends javax.swing.JPanel
{
    private double worldWidth;
    private double worldHeight;
    private ArrayList<Position2D> positionList = new ArrayList<>();
    private Vector2D currentSpeed;
    private Position2D targetPosition;
    private Position2D laserTargetPosition;
    private Vector2D initialSpeed;
    private Position2D initialPosition;
    private Position2D guidencePoint;
    
    public Canvas()
    {
        initComponents();
    }

    public void setWorldSize(double worldWidth, double worldHeight)
    {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    public void resetCanvs()
    {
        positionList.clear();
        currentSpeed = null;
        currentSpeed = null;
        targetPosition = null;
        laserTargetPosition = null;
        initialSpeed = null;
        initialPosition = null;
        guidencePoint = null;
        repaint();
    }
    
    public void resetPath()
    {
        positionList.clear();
        repaint();
    }
    
    public void addPosition2D(Position2D position)
    {
        positionList.add(position);
        repaint();
    }
     
    public void setTargetPosition2D(Position2D targetPosition)
    {
        this.targetPosition = targetPosition;
        repaint();
    }
    
    public void setLaserTargetPosition2D(Position2D laserTargetPosition)
    {
        this.laserTargetPosition = laserTargetPosition;
        repaint();
    }
    
    public void setInitialPosition(Position2D initialPosition)
    {
        this.initialPosition = initialPosition;
        repaint();
    }

    public void setGuidencePoint(Position2D guidencePoint)
    {
        this.guidencePoint = guidencePoint;
        repaint();
    }

    public void setSpeed(Vector2D currentSpeed)
    {
        this.currentSpeed = currentSpeed;
        repaint();
    }
    
    public void setInitialSpeed(Vector2D initialSpeed)
    {
        this.initialSpeed = initialSpeed;
        repaint();
    }
        
    @Override
    protected void paintComponent(Graphics g)
    { 
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
        graphics2D.setStroke(new BasicStroke(1));   
        graphics2D.setFont(new Font("TimesRoman", Font.PLAIN, 12));
            
        // Draw line
        graphics2D.setColor(Color.GREEN);
        for (int i = 0; i < positionList.size(); i++)
        {
            Position2D position = getWindowPosition(positionList.get(i));
                    
            int dotSize = (i == positionList.size() - 1) ? 5 : 2;
            graphics2D.fillOval((int)Math.round(position.getX()) - dotSize / 2, (int)Math.round(position.getY()) - dotSize / 2, dotSize, dotSize);
            
            if (i != positionList.size() - 1)
            {
                Position2D nextPosition = getWindowPosition(positionList.get(i + 1));
                graphics2D.drawLine((int)Math.round(position.getX()), (int)Math.round(position.getY()), 
                                        (int)Math.round(nextPosition.getX()), (int)Math.round(nextPosition.getY()));
            }
        }
        
        //Draw speed
        if (currentSpeed != null)
        {
            Position2D lastPosition = getWindowPosition(positionList.get(positionList.size() - 1));
            
            graphics2D.drawLine((int)Math.round(lastPosition.getX()), (int)Math.round(lastPosition.getY()), 
                                        (int)Math.round(lastPosition.getX()) + 50, (int)Math.round(lastPosition.getY()));
            
            int yAxisLength = (currentSpeed.getY() > 0) ? 50 : -50;
            graphics2D.drawLine((int)Math.round(lastPosition.getX()), (int)Math.round(lastPosition.getY()), 
                                        (int)Math.round(lastPosition.getX()), (int)Math.round(lastPosition.getY() - yAxisLength));
            
            graphics2D.drawString(String.format("Vx=%.2f", currentSpeed.getX()), (int)Math.round(lastPosition.getX()) + 55, (int)Math.round(lastPosition.getY()));
            graphics2D.drawString(String.format("Vy=%.2f", currentSpeed.getY()), (int)Math.round(lastPosition.getX()) + 5, (int)Math.round(lastPosition.getY() - yAxisLength));
                
        }
        
        // Target
        if (targetPosition != null)
        {
            graphics2D.setColor(Color.RED);
            Position2D position = getWindowPosition(targetPosition);
            Position2D radius = getWindowPosition(new Position2D(100, 100));
            
            int centerX = (int)Math.round(position.getX());
            int centerY = (int)Math.round(position.getY());
            
            graphics2D.drawLine(centerX - 5, centerY, centerX + 5, centerY);
            graphics2D.drawLine(centerX, centerY - 5, centerX, centerY + 5);
            graphics2D.drawOval(centerX - (int)radius.getX(), centerY - (int)radius.getX(), (int)radius.getX() * 2, (int)radius.getX() * 2);
            graphics2D.drawString("Target", centerX + 10, centerY + 10);
        }
        
        // Laser Target
        if (laserTargetPosition != null)
        {
            graphics2D.setColor(Color.MAGENTA);
            Position2D position = getWindowPosition(laserTargetPosition);
            Position2D radius = getWindowPosition(new Position2D(100, 100));
            
            int centerX = (int)Math.round(position.getX());
            int centerY = (int)Math.round(position.getY());
            
            graphics2D.drawLine(centerX - 5, centerY, centerX + 5, centerY);
            graphics2D.drawLine(centerX, centerY - 5, centerX, centerY + 5);
            graphics2D.drawOval(centerX - (int)radius.getX(), centerY - (int)radius.getX(), (int)radius.getX() * 2, (int)radius.getX() * 2);
            graphics2D.drawString("Laser Target", centerX + 10, centerY + 10);
        }
        
        graphics2D.setColor(Color.GREEN);
        // information area
        if (initialSpeed != null)
        {
            graphics2D.drawString(String.format("Initial Velosity X = %.2f", initialSpeed.getX()), 10, 15);
            graphics2D.drawString(String.format("Initial Velosity Y = %.2f", initialSpeed.getY()), 10, 30);
            graphics2D.drawString(String.format("Initial Angle = %.2f", initialSpeed.getAngle().getValue()), 10, 45);
        }
        
        if (currentSpeed != null)
        {
            graphics2D.drawString(String.format("Velosity X=%.2f", currentSpeed.getX()), 10, 70);
            graphics2D.drawString(String.format("Velosity Y=%.2f", currentSpeed.getY()), 10, 85);
        }
        
        if (initialPosition != null)
        {
            graphics2D.setColor(Color.BLUE);
            Position2D position = getWindowPosition(initialPosition);

            int centerX = (int)Math.round(position.getX());
            int centerY = (int)Math.round(position.getY());
            
            graphics2D.drawLine(centerX - 5, centerY, centerX + 5, centerY);
            graphics2D.drawLine(centerX, centerY - 5, centerX, centerY + 5);
            graphics2D.drawString("Gun Line", centerX + 10, centerY + 10);
        }
        
        if (guidencePoint != null)
        {
            graphics2D.setColor(Color.CYAN);
            Position2D position = getWindowPosition(guidencePoint);

            int centerX = (int)Math.round(position.getX());
            int centerY = (int)Math.round(position.getY());
            
            graphics2D.drawLine(centerX - 5, centerY, centerX + 5, centerY);
            graphics2D.drawLine(centerX, centerY - 5, centerX, centerY + 5);
            graphics2D.drawString("Guidance Activated", centerX + 10, centerY + 10);
        }
    }
    
    private Position2D getWindowPosition(Position2D worldPosition)
    {
        double windowXPosition = getWidth() * (worldPosition.getX() / worldWidth);
        double windowYPosition = getHeight() - (getHeight() * (worldPosition.getY() / worldHeight));
        Position2D pos = new Position2D(windowXPosition, windowYPosition);
        
        return pos;
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

        setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
