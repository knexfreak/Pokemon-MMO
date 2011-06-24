package com.pokemon.mmo.gui;

import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;

public class Sprite extends Texture2D {
	public Sprite(String fname) { super(fname); }
	public Sprite(BufferedImage image) { super(image); }
}
