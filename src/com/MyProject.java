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
import processing.core.PApplet;

public class MyProject extends PApplet implements WindowFocusListener {

	// Configuration
	private static final long serialVersionUID = 1;
	public final static int WIDTH = 1920/4;
	public final static int HEIGHT = 1080/4;
	public boolean focused = true;

	public int mbrightness;
	public Client myClient;
	private HueBridge bridge; 

	public void setup() {

		// Setup
		size(WIDTH, HEIGHT, OPENGL);
		hint(ENABLE_OPENGL_ERRORS);
		hint(DISABLE_TEXTURE_MIPMAPS);

		// Focus listener
		frame.addWindowFocusListener(this);
		
		bridge = new HueBridge("192.168.1.10");
		mbrightness = 0;
		frameRate(40);
		
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
		
		
		
		 
		
	
	}

	public void draw() {

		// Clear background
		background(255, 0, 250);

	}
	
	public void mousePressed(){
		
		mbrightness = mouseY;
		println(mouseY);
		
		try {


			for (Light light : bridge.getLights()) {
			    FullLight fullLight = bridge.getLight(light);
			   // System.out.println(fullLight.getName() + "SECOND BRIGHTNESS (" + fullLight.getState().getBrightness() + ")");
			   if(mbrightness<255) bridge.setGroupState(bridge.getAllGroup(), new StateUpdate().turnOn().setBrightness(mbrightness));
			}
		
		
		} catch (IOException e) {
			// Ton erreur va s'imprimer dans la console
			//e.printStackTrace();	
		} catch (ApiException e) {
			// Ton erreur va s'imprimer dans la console
			//e.printStackTrace();
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
