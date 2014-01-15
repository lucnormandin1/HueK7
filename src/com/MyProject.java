package com;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;
import java.util.*;

import nl.q42.jue.FullLight;
import nl.q42.jue.HueBridge;
import nl.q42.jue.Light;
import nl.q42.jue.StateUpdate;
import nl.q42.jue.exceptions.ApiException;
import processing.net.*;
import processing.core.*;
import processing.*;
import de.voidplus.leapmotion.*;
//import com.leapmotion.*;




public class MyProject extends PApplet implements WindowFocusListener {

	// Configuration
	private static final long serialVersionUID = 1;
	public final static int WIDTH = 500;
	public final static int HEIGHT = 250;
	public boolean focused = true;
	public boolean bHue = true;
	public boolean bLeap = true;
	
	// Main variables
	public int iBrightness;
	public Client myClient;
	private HueBridge bridge; 
	public LeapMotion leap;
	public ArrayList<Light> lAllLights;
	
	public void setup() {

		// Setup
		size(WIDTH, HEIGHT, P3D);
		hint(ENABLE_OPENGL_ERRORS);
		hint(DISABLE_TEXTURE_MIPMAPS);
		frameRate(1);
		
		// Focus listener
		frame.addWindowFocusListener(this);
		
		
		// Setting up leapmotion
		if(bLeap)setupLeapmotion();		
		
		// Setting up Hue
		if(bHue)setupHueBridge();	
	
	}

	public void draw() {

		// Clear background
		background(0, 0, 250);
		
		if(bLeap)activateLeapmotion();
		if(bHue)activateHue();
		    
	}
	
	public void setupLeapmotion(){
		
		// Create a new Leapmotion including Gesture
		leap = new LeapMotion(this).withGestures();
		
	}
	
	public void activateHue(){
		

		try {
				
			// Get all lights to be set the iBrightness
			for (Light light : bridge.getLights()) {

				// Light Object
				FullLight fullLight = bridge.getLight(light);
			   
				// Set brightness
				if(iBrightness<=255) {
				   bridge.setGroupState(bridge.getAllGroup(), new StateUpdate().turnOn().setBrightness(iBrightness));
				   println("My birghtness : " + iBrightness);
			   
			   }
				   
				   
				   
				  
			}
		
		
		} catch (IOException e) {
			// Ton erreur va s'imprimer dans la console
			e.printStackTrace();	
		} catch (ApiException e) {
			// Ton erreur va s'imprimer dans la console
			e.printStackTrace();
		}
		
		
	}

	public void setupHueBridge(){
		
		//Create instance with IP
		bridge = new HueBridge("192.168.0.103");
		
		// Set first brightness;
		iBrightness = 0;
		
		//Controller.connect(timeoutInterval = 0);
		
		try {
			
			// Authentification with HUB
			bridge.authenticate("newdeveloper");

			
			//get 3 lights depending on configuration
			for (Light light : bridge.getLights()) {
			    // light
				FullLight fullLight = bridge.getLight(light);
			   // System.out.println(fullLight.getName() + "FIRST BRIGHTNESS (" + fullLight.getState().getBrightness() + ")");
				bridge.setGroupState(bridge.getAllGroup(), new StateUpdate().setBrightness(iBrightness));
			}
			println("Ok Authenticate");
		
		} catch (IOException e) {
			// Ton erreur va s'imprimer dans la console
			e.printStackTrace();	
		} catch (ApiException e) {
			// Ton erreur va s'imprimer dans la console
			e.printStackTrace();
		}
		
		
		
	};
	
	public void activateLeapmotion(){
		

		 // HANDS
	    for(Hand hand : leap.getHands()){

	    	// Draw hand
	        hand.draw();
	       
	        // Set brightness as the Y position
	        iBrightness = (int)hand.getRawPosition().y;

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
		    case 3: // Stop
		      println("SwipeGesture: "+id);
		      changeEnvironment();
		      
		      break;
		  }
	}
	
	public void changeEnvironment(){
		
		// Projection
		
		
		
		// Hue color
		
		
	
		
	}

	public void setHueColor(){
		
		
		
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
