package com.pokemon.mmo.gui;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import javax.imageio.*;
import java.io.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Hashtable;

public class GUI {

	private int width = 0;
	private int height = 0;
	private boolean finished = false;
	private TexturePack2D map_pack = null;
	
	public GUI(int w, int h) {
		width = w;
		height = h;
	}

	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		initGL();						// init OpenGL
		map_pack = new TexturePack2D("assets/tileset.png", 16, 16);	// load map texture pack--cant be done in constructor since display is not initialized
		
//		font = new Font("assets/text.png", "assets/text.txt", 6, 13);
//		font.buildFont(2);	// build the textures for text
//		BufferedImage image = null;
//		try {
//			image = ImageIO.read(new File("assets/pokemon_sprites/front/644.png"));
//			TexturePack2D pack = new TexturePack2D("assets/tileset2.png", 16, 16);
//		} catch (IOException e) {
//			e.printStackTrace();
//			System.exit(0);
//		}
//		image = image.getSubimage(0, 0, 40, 40);
//		r = new Sprite("assets/pokemon_sprites/front/644.png");
//		s = new Sprite("assets/pokemon_sprites/front/643.png");
		
		while (!Display.isCloseRequested() && !finished) {
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) finished = true;
			renderGL();
			Display.update();
			Display.sync(60); // cap fps to 60fps

		}
		
		Display.destroy();
	}

	public void initGL() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	public void renderGL() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	// Clear The Screen And The Depth Buffer
		
		map_pack.getTile(17, 11).draw(100, 100, 3);

//		font.draw_str("HELLO", 1000, 700);
//		
//		s.draw(100, 400, 2f);
//		s.draw(300, 400, 2.23f);
//		r.draw(200, 200, 3f);
	}
}

