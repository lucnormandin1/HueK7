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
	public final static int HEIGHT = 500;
	public boolean focused = true;
	public boolean bHue = true;
	public boolean bLeap = false;
	
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
		frameRate(60);
		
		// Focus listener
		frame.addWindowFocusListener(this);
		
		// Setting up leapmotion
		if(bLeap)setupLeapmotion();		
		
		// Setting up Hue
		if(bHue)setupHueBridge();	
	
	}

	public void draw() {

		// Clear background
		background(255, 0, 250);
		
		if(bLeap)activateLeapmotion();
		if(bHue)activateHue();
		    
	}
	
	public void setupLeapmotion(){
		
		leap = new LeapMotion(this);
		
	}
	
	public void activateHue(){
		
		lAllLights = ArrayList() bridge.getLights();
		
		try {
				
			for (Light light : bridge.getLights()) {

				FullLight fullLight = bridge.getLight(light);
			   // System.out.println(fullLight.getName() + "SECOND BRIGHTNESS (" + fullLight.getState().getBrightness() + ")");
			   if(iBrightness<255) bridge.setGroupState(bridge.getAllGroup(), new StateUpdate().turnOn().setBrightness(iBrightness));
			}
		
		
		} catch (IOException e) {
			// Ton erreur va s'imprimer dans la console
			//e.printStackTrace();	
		} catch (ApiException e) {
			// Ton erreur va s'imprimer dans la console
			//e.printStackTrace();
		}
		
		
	}

	public void setupHueBridge(){
		
		//Create instance with IP
		bridge = new HueBridge("192.168.1.10");
		
		lAllLights = new ArrayList<Light>();
		
		// Set first brightness;
		iBrightness = 0;
		
		try {
			
			// Autication with HUB
			bridge.authenticate("newdeveloper");

			
			//get 3 lights depending on configuration
			for (Light light : bridge.getLights()) {
			    // light
				FullLight fullLight = bridge.getLight(light);
			    System.out.println(fullLight.getName() + "FIRST BRIGHTNESS (" + fullLight.getState().getBrightness() + ")");
			    bridge.setGroupState(bridge.getAllGroup(), new StateUpdate().turnOff());
			
			}

		
		} catch (IOException e) {
			// Ton erreur va s'imprimer dans la console
			e.printStackTrace();	
		} catch (ApiException e) {
			// Ton erreur va s'imprimer dans la console
			e.printStackTrace();
		}
		
		
		
	};
	
	public void activateLeapmotion(){
		
		int fps = leap.getFrameRate();
		
		 // HANDS
	    for(Hand hand : leap.getHands()){

	        hand.draw();
	        
	        int incr = 0;

	      //  balls = new ArrayList<Ball>();
	        
	        // FINGERS
	        for(Finger finger : hand.getFingers()){
	        	
	        	if (incr == 0) {
		        	
		            // Basics
		            finger.draw();
		            int     finger_id         = finger.getId();
		            PVector finger_position   = finger.getPosition();
		            PVector finger_stabilized = finger.getStabilizedPosition();
		            PVector finger_velocity   = finger.getVelocity();
		            PVector finger_direction  = finger.getDirection();
	
		            println("Pos X : " + finger_position.x);
		            iBrightness = (int)finger_position.x;
		            
		            
	        	}
	        	
	        	incr ++ ;
	            
	            
	        }

	      

	    }

	    // DEVICES
	    for(Device device : leap.getDevices()){
	        float device_horizontal_view_angle = device.getHorizontalViewAngle();
	        float device_verical_view_angle = device.getVerticalViewAngle();
	        float device_range = device.getRange();
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
