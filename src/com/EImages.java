package com;

import java.awt.event.WindowFocusListener;

import processing.*;
import processing.core.PApplet;
import processing.core.PImage;

import java.awt.Image;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

public class EImages{

	private String urls;
	private String name;
	public float x;
	public float y;
	public float width;
	public float height;
	public PImage mimg;
	private PApplet applet;
	public boolean move = false;
	public int color = 0;
	public int destination = 0;


	public EImages(PApplet p){
		
		applet = p;

	}
	public void actionPerformed(ActionEvent e) { 
	      //code that reacts to the action... 
	
	}
	
	public void init(String _url, String _name, int _position, int _color ){
		
		urls = _url;
		name = _name;
		color = _color;
		mimg = applet.loadImage(urls);
		
		
		try{
				
			width = mimg.width;
			height = mimg.height;
			if (_position == 0) x = (applet.displayWidth/2)-(mimg.width/2);
			else x = applet.displayWidth;
			y = 0;//(applet.displayHeight/2)-(mimg.height/2);
			applet.image(mimg,x,y);
			

			
		} catch ( Exception e){
	
		};
	
	}
	

	public void draw(){
	
		applet.image(mimg,x,y);

	}
	
	public float getHeight(){
			
		return width;
	
	}
	
	public float getWidth(){
			
		return height;
		
	}
	

	
}
