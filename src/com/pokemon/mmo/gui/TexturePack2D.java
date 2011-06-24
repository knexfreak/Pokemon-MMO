package com.pokemon.mmo.gui;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.ArrayList;

public class TexturePack2D {

	private class TileRectangle {
		public int x = -1, y = -1, w = 0, h = 0;
		public TileRectangle(int a, int b, int c, int d) { x = a; y = b; w = c; h = d; }
		public boolean contains(int u, int v) { if(x<=u && y<=v && u<x+w && v<y+h) return true; return false; }
	}
	
	private class TileSet {
		public int x = -1, y = -1;
		public ArrayList<TileCoord> set = null;
		public TileSet(int a, int b, int size) { x = a; y = b; set = new ArrayList<TileCoord>(size); }
		public TileSet(int a, int b, ArrayList<TileCoord> c) { x = a; y = b; set = c; }
		public void add(int u, int v) { set.add(new TileCoord(u, v)); }
		public void add(TileCoord tc) { set.add(tc); }
		public boolean contains(int u, int v) { if(x<=u && y<=v && u<x+w && v<y+h) return true; return false; }
	}

	private class TileCoord {
		public int x = -1, y = -1;
		
		public TileCoord(int a, int b) { x = a; y = b; }
		public boolean equals(Object o) {
			if (o instanceof TileCoord) {
				TileCoord tt = (TileCoord) o;
				if (tt.x == x && tt.y == y) return true;
			}
			return false;
		}
	}

	private Texture2D pack[][] = null;
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
		generateTiles(image, null);
	}

	public TexturePack2D(String image_fname, String block_fname, int twidth, int theight) {
		BufferedImage image = loadImage(image_fname);
		tile_width = twidth;
		tile_height = theight;
		LinkedList<ArrayList<TileCoord>> list = loadBlocks(block_fname);
		generateTiles(image, list);
	}

	public TexturePack2D(BufferedImage image, int twidth, int theight) {
		tile_width = twidth;
		tile_height = theight;
		generateTiles(image, null);
	}

	public TexturePack2D(BufferedImage image, String block_fname, int twidth, int theight) {
		tile_width = twidth;
		tile_height = theight;
		LinkedList<ArrayList<TileCoord>> list = loadBlocks(block_fname);
		generateTiles(image, list);
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

	private LinkedList<ArrayList<TileCoord>> loadBlocks(String fname) {
		byte inbyte[] = null;
		int length = 0;
		LinkedList<ArrayList<TileCoord>> list = new LinkedList<ArrayList<TileCoord>>();
		try {
			File infile = new File(fname);
			FileInputStream instream = new FileInputStream(infile);
			length = (int) infile.length();
			inbyte = new byte[(int) length];
			instream.read(inbyte);
			instream.close();
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		int i = 0;
		while (i < length) {
			if(inbyte[i] == 0) {
				// pass
			} else if (inbyte[i] == 1) {
				list.add(createRectangleSet(inbyte[i+1], inbyte[i+2], inbyte[i+3], inbyte[i+4]));
				i += 5;
			}
		}
		return list;
	}

	private ArrayList<TileCoord> createRectangleSet(byte bx0, byte by0, byte bx1, byte by1) {
		int ix0 = (int) bx0 & 0xFF;
		int iy0 = (int) by0 & 0xFF;
		int ix1 = (int) bx1 & 0xFF;
		int iy1 = (int) by1 & 0xFF;
		ArrayList<TileCoord> list = new ArrayList<TileCoord>((ix1-ix0)*(iy1-iy0));

		for(int j = iy0; j < iy1; j++) {
			for(int i = ix0; i < ix1; i++) {
				list.add(new TileCoord(i, j));
			}
		}
		return list;
	}

	private void generateTiles(BufferedImage image, LinkedList<ArrayList<TileCoord>> block_list) {
		width = image.getWidth() / tile_width;
		height = image.getHeight() / tile_height;
		if(block_list == null) block_list = new LinkedList<ArrayList<TileCoord>>();

		pack = new Texture2D[width][height];
		for(int j = 0; j < height; j++) {
			for(int i = 0; i < width; i++) {
				pack[i][j] = new Texture2D(image.getSubimage(16*i, 16*j, 16, 16));
			}
		}
	}
}
