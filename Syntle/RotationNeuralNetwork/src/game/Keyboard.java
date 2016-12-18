package game;

public class Keyboard
{
	private static boolean keys[] = new boolean[1024];
	
	public static void pressed(int key)
	{
		keys[key] = true;
	}
	
	public static void released(int key)
	{
		keys[key] = false;
	}
	
	public static boolean isKeyPressed(int key)
	{
		return keys[key];
	}
}
