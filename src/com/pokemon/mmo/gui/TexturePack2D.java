package com.pokemon.mmo.gui;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.ArrayList;

public class TexturePack2D {

	private Tile pack[][] = null;
	private int tile_width = 0;
	private int tile_height = 0;
	private int width = 0;
	private int height = 0;

	/************************/
	/***   Constructors   ***/
	/************************/

	public TexturePack2D(String image_fname, int twidth, int theight) {
		BufferedImage image = loadImage(image_fname);
		tile_width = twidth;
		tile_height = theight;
		generateTiles(image);
	}

	public TexturePack2D(BufferedImage image, int twidth, int theight) {
		tile_width = twidth;
		tile_height = theight;
		generateTiles(image);
	}



	/***************************/
	/***   Utility Methods   ***/
	/***************************/

	private BufferedImage loadImage(String fname) {
		BufferedImage image = null;
		try { image = ImageIO.read(new File(fname)); }			// Read in new image
		catch(IOException e) { e.printStackTrace(); System.exit(0); }
		return image;
	}

	private void generateTiles(BufferedImage image) {
		width = image.getWidth() / tile_width;
		height = image.getHeight() / tile_height;

		pack = new Tile[width][height];
		for(int j = 0; j < height; j++) {
			for(int i = 0; i < width; i++) {
				pack[i][j] = new Tile(image.getSubimage(16*i, 16*j, 16, 16));
			}
		}
	}
	
	public Tile getTile(int u, int v) { return pack[u][v]; }
}
