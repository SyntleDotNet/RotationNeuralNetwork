package game;

import static org.lwjgl.glfw.GLFW.*;

public class Player
{
	private double x;
	private final double y = 50;
	private final double size = 50;
	private double speed;
	private final double acceleration = 0.8;
	private final double friction = 0.05;
	
	public Player()
	{
		reset();
	}
	
	public void reset()
	{
		x = 250;
		speed = 0;
	}
	
	public void update()
	{
		if (Keyboard.isKeyPressed(GLFW_KEY_A))
			moveLeft();
		if (Keyboard.isKeyPressed(GLFW_KEY_D))
			moveRight();
		
		x += speed;
		speed *= 1 - friction;
		
		if (x > 500 - size)
			x = 500 - size;
		if (x < size)
			x = size;
	}
	
	public void moveLeft()
	{
		speed -= acceleration;
	}
	
	public void moveRight()
	{
		speed += acceleration;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public double getSize()
	{
		return size;
	}
	
	public double getSpeed()
	{
		return speed;
	}
}
