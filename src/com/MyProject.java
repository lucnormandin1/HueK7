package com;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;


import nl.q42.jue.FullLight;
import nl.q42.jue.HueBridge;
import nl.q42.jue.Light;
import nl.q42.jue.StateUpdate;
import nl.q42.jue.exceptions.ApiException;
import processing.net.*;
import processing.core.*;
import processing.core.PApplet;
import de.voidplus.leapmotion.*;
import com.EImages;


public class MyProject extends PApplet implements WindowFocusListener {

	// Configuration
	private static final long serialVersionUID = 1;
	public final static int WIDTH = 500;
	public final static int HEIGHT = 250;
	public boolean focused = true;
	public boolean bHue = true;
	public boolean bLeap = true;
	public EImages test;
	
	// Main variables
	public Client myClient;
	public LeapMotion leap;
	public Environment envStage;
	public int iNextImages = 0;
	public int iCurrentImageOnStage = 0;

	public void setup() {

		
		
		// Applet setup
		size(displayWidth, displayHeight);
		//size(500,500);
		hint(ENABLE_OPENGL_ERRORS);
		hint(DISABLE_TEXTURE_MIPMAPS);
		frameRate(60);

		// Focus listener
		frame.addWindowFocusListener(this);
		
		// Setting up leapmotion
		setupLeapmotion();		

		// Create stage
		envStage = new Environment(this);
		
		// Load all picture
		envStage.loadPicture();

		
	}
	
	public void draw() {
		
		// Clear background
		background(0, 0, 0);
		
		if(bLeap)activateLeapmotion();
		
		// Draw stage
		envStage.draw();


	}
	

	
	public void setupLeapmotion(){
		
		// Create a new Leapmotion including Gesture
		leap = new LeapMotion(this).withGestures();
		
	}

	
	public void activateLeapmotion(){
		

		 // HANDS
	    for(Hand hand : leap.getHands()){

	    	// Draw hand
	        hand.draw();

	    }
	    
	}

	// SWIPE GESTURE
	public void leapOnSwipeGesture(SwipeGesture g, int state){
		  int       id               = g.getId();
		  Finger    finger           = g.getFinger();
		  PVector   position         = g.getPosition();
		  PVector   position_start   = g.getStartPosition();
		  PVector   direction        = g.getDirection();
		  float     speed            = g.getSpeed();
		  long      duration         = g.getDuration();
		  float     duration_seconds = g.getDurationInSeconds();

		  switch(state){
		    case 1: // Start
		      break;
		    case 2: // Update
		      break;
		    case 3: if(!envStage.move) envStage.switchImage(); break;
		  }
	}

	
	public void windowGainedFocus(WindowEvent e) {
		focused = true;
	}

	public void windowLostFocus(WindowEvent e) {
		focused = false;
	}

	public static void main(String[] args) {
		PApplet.main(new String[] { "--bgcolor=#000000", com.MyProject.class.getName() });		
	}

}
