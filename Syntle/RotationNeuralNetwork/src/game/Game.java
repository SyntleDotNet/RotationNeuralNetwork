package game;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import neuralnet.AI;

public class Game
{
	private long window;

	private final double widthToHeightRatio = 9D / 16;
	private double width;
	private double height = 1000;

	private Player player = new Player();
	private UpdateThread updateThread;
	private Object updateLock = new Object();
	private boolean resetPending = false;
	private Object resetLock = new Object();

	private ArrayList<Block> blocks = new ArrayList<>();

	private AI ai;

	public static void main(String[] args)
	{
		new Game().run();
	}

	public void run()
	{
		try
		{
			init();
			loop();

			glfwFreeCallbacks(window);
			glfwDestroyWindow(window);
		}
		finally
		{
			glfwTerminate();
			glfwSetErrorCallback(null).free();
		}
	}

	private double rotation;
	private int score;
	private int oldScore;
	private double threshold;
	private double speed;

	public class UpdateThread extends Thread
	{
		public UpdateThread()
		{
			reset();
		}

		public void reset()
		{
			rotation = 0.0;
			score = 1;
			oldScore = 0;
			threshold = 150;
			speed = 2.0;

			blocks.clear();
			blocks.add(new Block());

			player.reset();
		}

		@Override
		public void run()
		{
			while (!glfwWindowShouldClose(window))
			{
				double greatestBlockY = 0;
				ArrayList<Integer> toRemove = new ArrayList<Integer>();
				for (int i = 0; i < blocks.size(); i += 1)
				{
					Block block = blocks.get(i);
					block.update(speed);
					if (block.getY() > greatestBlockY)
						greatestBlockY = block.getY();
					if (block.getY() < -Block.getHeight())
					{
						toRemove.add(i);
						score += 1;
					}
				}
				
				synchronized (blocks)
				{
					for (Integer i : toRemove)
						blocks.remove((int) i);
					
					if (greatestBlockY < threshold)
					{
						blocks.add(new Block());
						threshold += 15;
						if (threshold > 750)
							threshold = 750;
					}
				}

				rotation -= player.getSpeed();
				player.update();
				speed += 0.001;
				oldScore += 1;

				double leastY = 1000;
				Block lowestBlock = null;
				for (Block block : blocks)
				{
					if (block.getY() < leastY)
					{
						leastY = block.getY();
						lowestBlock = block;
					}
				}

				if (lowestBlock != null)
				{
					if (!(player.getX() - player.getSize() * 0.5 > lowestBlock.getLeft() && player.getX() + player.getSize() * 0.5 < 500 - lowestBlock.getRight()))
					{
						if (player.getY() + player.getSize() * 0.5 > lowestBlock.getY() && player.getY() - player.getSize() * 0.5 < lowestBlock.getY())
						{
							ai.Death(score, oldScore);
							synchronized (blocks)
							{
								reset();
							}
						}
					}
				}

				if (lowestBlock != null)
					ai.Update(lowestBlock.gapX(), leastY);

				if (Keyboard.isKeyPressed(GLFW_KEY_SPACE))
				{
					synchronized (updateLock)
					{
						try
						{
							updateLock.wait();
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}	
					}
				}
				
				if (resetPending)
				{
					reset();
					synchronized (resetLock)
					{
						resetLock.notifyAll();
					}
					resetPending = false;
				}
			}
		}
	}

	private void init()
	{
		if (height != 0)
			width = height * widthToHeightRatio;
		else if (width != 0)
			height = width / widthToHeightRatio;
		
		GLFWErrorCallback.createPrint(System.err).set();
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

		window = glfwCreateWindow((int) width, (int) height, "Rotation", NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		glfwSetKeyCallback(window, (window, key, scancode, action, mods) ->
		{
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
				glfwSetWindowShouldClose(window, true);
			
			if (key == GLFW_KEY_S && action == GLFW_PRESS)
				ai.saveBestSpeciesSoFar();
			if (key == GLFW_KEY_L && action == GLFW_PRESS)
			{
				resetPending = true;
				synchronized (resetLock)
				{
					try
					{
						resetLock.wait();
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					ai.loadSpecies();
				}
			}

			if (action == GLFW_PRESS)
				Keyboard.pressed(key);
			if (action == GLFW_RELEASE)
				Keyboard.released(key);
		});
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - (int) width) / 2, (vidmode.height() - (int) height) / 2);
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);

		ai = new AI();
		ai.Init(player);
	}

	private void loop()
	{
		GL.createCapabilities();
		glClearColor(0, 0, 0, 0);
		glViewport(0, 0, (int) width, (int) height);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, 0, height, -1, 1);
		glMatrixMode(GL_MODELVIEW);

		updateThread = new UpdateThread();
		updateThread.start();

		while (!glfwWindowShouldClose(window))
		{
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			glBegin(GL_QUADS);
			glColor3d(179.0 / 255, 157.0 / 255, 219.0 / 255);
			glVertex2d(0, 0);
			glVertex2d(width, 0);
			glColor3d(38.0 / 255, 198.0 / 255, 218.0 / 255);
			glVertex2d(width, height);
			glVertex2d(0, height);
			glEnd();

			double size = player.getSize();
			double halfSize = size * 0.5;
			double x = player.getX();
			double y = player.getY();

			// Shadow
			glTranslated(6, -6, 0);
			glTranslated(x, y, 0);
			glRotated(rotation, 0, 0, 1);
			glTranslated(-x, -y, 0);
			glBegin(GL_QUADS);
			glColor4d(0, 0, 0, 0.5);
			glVertex2d(x - halfSize, y - halfSize);
			glVertex2d(x + halfSize, y - halfSize);
			glVertex2d(x + halfSize, y + halfSize);
			glVertex2d(x - halfSize, y + halfSize);
			glEnd();
			glTranslated(-6, 6, 0);
			glLoadIdentity();

			// Player
			glTranslated(x, y, 0);
			glRotated(rotation, 0, 0, 1);
			glTranslated(-x, -y, 0);
			glBegin(GL_QUADS);
			glColor4d(216.0 / 255, 27.0 / 255, 96.0 / 255, 1.0);
			glVertex2d(x - halfSize, y - halfSize);
			glVertex2d(x + halfSize, y - halfSize);
			glVertex2d(x + halfSize, y + halfSize);
			glVertex2d(x - halfSize, y + halfSize);
			glEnd();
			glLoadIdentity();

			synchronized (blocks)
			{
				for (Block block : blocks)
					block.render();
			}
			
			glfwSwapBuffers(window);
			glfwPollEvents();
			
			synchronized (updateLock)
			{
				updateLock.notifyAll();
			}
		}
		try
		{
			updateThread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
