package com;
import processing.core.PApplet;

import com.EImages;

import processing.*;
import processing.core.PImage;

import java.awt.Image;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

import nl.q42.jue.FullLight;
import nl.q42.jue.HueBridge;
import nl.q42.jue.Light;
import nl.q42.jue.StateUpdate;
import nl.q42.jue.exceptions.ApiException;

public class Environment {

    
    private PApplet applet;
    public EImages[] imageArray = new EImages[3];
    public int currentImageToMove;
    public int nextImageToMove;
    public boolean thereIsOneImageMoving = false;
    public boolean move = false;
    public int destination = 0;
    private float easing = 0.15f;
	private boolean bActivateTimer = false;
	private int savedTime;
	private int totalTime = 150;
	private EImages imgCurrentToMove;
	private boolean nextAnim;
	private HueBridge bridge; 
	
    public Environment(PApplet p){
        
        applet = p;
  
        setupHueBridge();
    }
    
    public void loadPicture(){
    	
    	loadEImage("winter.jpg","winter",0,46920);
		loadEImage("fall.jpg","fall",1,65535);
		loadEImage("summer.jpg","summer",2,13000);
	
    	
    	
    }
    
    public void moveImages(){
        

        //Move images
        float targetX;
        float dx;
        EImages img = imgCurrentToMove;
       
        if(move){   

        	bActivateTimer = false;
        	
            switch(destination){
            
                // Left
                case 0:
                	
  
                    targetX = 0 - (img.width);
                    dx = targetX - img.x;
                       
                break;
                    
                // Middle 
                case 1:
                    
                	targetX = (applet.width/2) - (img.width/2);
                    dx = targetX - img.x;
                    
                break;
                
                default:
                	
                    targetX = 0;
                    dx = 0; 
                break;

            };
            

            if(applet.abs(dx) > 1) {

                img.x += dx * easing;
               
            }else{
            	
            	if(nextAnim)img.x = applet.width;
            	move=false;
            	
                bActivateTimer = true;
            };

        };
  
    }
    
    public void draw(){
        
        // Draw all images
        for (EImages img : imageArray) {
            img.draw();
        }
        
        // If we need to move a picture
        if(move) moveImages();

		if(bActivateTimer & nextAnim)switchImage();

    }
    
	public boolean timer(){
		
		// Calculate how much time has passed
		int passedTime = applet.millis() - savedTime;
		
		boolean r= false;
		
		if (passedTime > totalTime) {
					
			savedTime = applet.millis(); // Save the current time to restart the timer!
			r = true;
								
		}
		
		return r;
		
	}
    
    
    

    
    public void switchImage(){

    	// First image to be switch to the LEFT
    	if(imgCurrentToMove == null){
    		currentImageToMove = 0;	
    		destination = 0;
    		nextAnim = true;

    	// Second image to be switch
    	}else{

    		// Second image to be switch to the LEFT
    		if(!nextAnim){
    			
    			destination = 0;
    			nextAnim = true;
    			changeColorAmbiance();
    			
    		}
    		// Second image to be switch to the CENTER
    		else {
    			
    			destination = 1;
    			
    			// Second image to be switch to the CENTER
    			if(currentImageToMove>=imageArray.length-1){

    				currentImageToMove = 0;
    		    	nextAnim = false;
    		    	
    		    // Second image to be switch to the CENTER if max reset
    			 }else{
    				 
    				currentImageToMove++;
    	    		nextAnim = false;

    			 }
    			changeColorAmbiance();
	
    		}
    		
    		
    		
    	//Last time	
    	}
    	
    	// Set image to be moved
    	imgCurrentToMove = imageArray[currentImageToMove];
    	
    	// GO Move
    	move = true;
    	
    	// Change color ambiance
    	
    	
    }

    public void loadEImage(String u,String n,int p,int c){
        
        // Create a new images Object
        EImages img = new EImages(applet);
        
        // Init image by setting property
        img.init("resources/img/"+u,n,p,c);

        // Add image to an array
        imageArray[p] = img;


    }
    

	public void changeColorAmbiance(){
		
		applet.println("ChangeColor");
		int color = imgCurrentToMove.color;
		
		try {
				
			bridge.setGroupState(bridge.getAllGroup(), new StateUpdate().turnOn().setBrightness(255));
			bridge.setGroupState(bridge.getAllGroup(), new StateUpdate().turnOn().setHue(color));

		
		
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
		bridge = new HueBridge("192.168.1.10");

		try {
			
			// Authentification with HUB
			bridge.authenticate("newdeveloper");

			// Set first hue light
			bridge.setGroupState(bridge.getAllGroup(), new StateUpdate().setBrightness(255));
			bridge.setGroupState(bridge.getAllGroup(), new StateUpdate().setHue(16000));


		
		} catch (IOException e) {
			// Ton erreur va s'imprimer dans la console
			e.printStackTrace();	
		} catch (ApiException e) {
			// Ton erreur va s'imprimer dans la console
			e.printStackTrace();
		}
		
		
		
	};
    
}
