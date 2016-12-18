package game;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

import java.util.Random;

public class Block
{
	private Random rand = new Random();
	
	private double gapX;
	private final double gapWidth = 120;
	private final double height = 40;
	private double y = 1000 + height;
	
	public Block()
	{
		gapX = rand.nextDouble() * (500 - gapWidth) + gapWidth * 0.5;
	}
	
	public void render()
	{
		y -= 2;
		
		glBegin(GL_QUADS);
		glColor3d(98.0/255, 0.0/255, 234.0/255);
		
		double width1 = gapX - gapWidth * 0.5;
		glVertex2d(0, y);
		glVertex2d(width1, y);
		glVertex2d(width1, y + height);
		glVertex2d(0, y + height);
		
		double width2 = 500 - gapWidth - width1;
		glVertex2d(gapX + gapWidth * 0.5, y);
		glVertex2d(gapX + gapWidth * 0.5 + width2, y);
		glVertex2d(gapX + gapWidth * 0.5 + width2, y + height);
		glVertex2d(gapX + gapWidth * 0.5, y + height);
		glEnd();
	}
}
