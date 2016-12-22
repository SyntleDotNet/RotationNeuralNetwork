package game;

import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

public class Block
{
	private Random rand = new Random();
	
	private double gapX;
	private final double gapWidth = 150;
	private static final double height = 50;
	private double y = Game.height + height;
	
	public Block()
	{
		gapX = rand.nextDouble() * (Game.width - gapWidth) + gapWidth * 0.5;
	}
	
	public void render()
	{
		double width1 = getLeft();
		double width2 = getRight();
		
		glTranslated(6, -6, 0);
		glBegin(GL_QUADS);
		glColor4d(0, 0, 0, .7);
		
		glVertex2d(-6, y);
		glVertex2d(width1, y);
		glVertex2d(width1, y + height);
		glVertex2d(-6, y + height);
		
		glVertex2d(gapX + gapWidth * 0.5, y);
		glVertex2d(gapX + gapWidth * 0.5 + width2, y);
		glVertex2d(gapX + gapWidth * 0.5 + width2, y + height);
		glVertex2d(gapX + gapWidth * 0.5, y + height);
		glEnd();
		glTranslated(-6, 6, 0);
		
		glBegin(GL_QUADS);
		glColor3d(98.0/255, 0.0/255, 234.0/255);

		glVertex2d(0, y);
		glVertex2d(width1, y);
		glVertex2d(width1, y + height);
		glVertex2d(0, y + height);
		
		glVertex2d(gapX + gapWidth * 0.5, y);
		glVertex2d(gapX + gapWidth * 0.5 + width2, y);
		glVertex2d(gapX + gapWidth * 0.5 + width2, y + height);
		glVertex2d(gapX + gapWidth * 0.5, y + height);
		glEnd();
	}
	
	public void update(double speed)
	{
		y -= speed;
	}
	
	public double getY()
	{
		return y;
	}
	
	public double gapX()
	{
		return gapX;
	}
	
	public static double getHeight()
	{
		return height;
	}
	
	public double getLeft()
	{
		return gapX - gapWidth * 0.5;
	}
	
	public double getRight()
	{
		return Game.width - gapWidth - getLeft();
	}
}
