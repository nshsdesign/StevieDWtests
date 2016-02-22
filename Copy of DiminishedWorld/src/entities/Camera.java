package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;

public class Camera {
	
	public static final int FIRST_PERSON = 1;
	public static final int THIRD_PERSON = 2;
	public static final int FREE_ROAM = 3;
	
	private static final float MIN_ZOOM = 50;
	private static final float MAX_ZOOM = 500;

	private Vector3f position;
	private float speed;
	private float angleSpeed;
	private float pitch;
	private float yaw;
	private float roll;
	private float mouseWheelSensitivity;
	private float angleAroundPlayerY;
	private float zoomDistance;
	private boolean prevEscDown;
	private boolean prevF5Down;
	private float dx;
	private float dz;
	private int type;
	
	//1P + 3P POV
	private Entity player;
	private float mouseSensitivity;
	
	//3P POV
	private static final float OFFSET_Y = 7;
	private float distanceFromPlayer = 50;
	
	//FreeRoam
	private static final float ANGLE_MOVE_SPEED = 50;
	private static final float MOVE_SPEED = ANGLE_MOVE_SPEED;
	
	public Camera(int type){
		this.type = type;
		position = new Vector3f(0,0,0);
		
		dx = 0;
		dz = 0;
		angleAroundPlayerY = 0;
		mouseSensitivity = 0.1f;
		mouseWheelSensitivity = 0.1f;
		Mouse.setGrabbed(true);
		
		if(type == FIRST_PERSON || type == THIRD_PERSON){
			pitch = 20;
		}
		else if(type == FREE_ROAM){
			zoomDistance = 50;
			pitch = 90;
			yaw = 0;
		}
	}
	
	public void setPlayer(Player p){
		player = p;
		position = new Vector3f(player.getPosition().x, player.getPosition().y, player.getPosition().z);
		yaw = 180-player.getRotY();
	}
	
	public void move(){
		if(Keyboard.isKeyDown(Keyboard.KEY_F5)){
			if(!prevF5Down){
				type++;
				if(type > FREE_ROAM) type = FIRST_PERSON;
			}
			prevF5Down = true;
		}else{
			prevF5Down = false;
		}
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
        	if(!prevEscDown){
	        	if(Mouse.isGrabbed()) Mouse.setGrabbed(false);
	        	else Mouse.setGrabbed(true);     
        	}
        	prevEscDown = true;        	
        }else{
        	prevEscDown = false;
        }
        
		if(type == FIRST_PERSON){
			calcYawAndPitch();
			player.setRotZ(pitch);
			player.setRotY(270-yaw);
			position = new Vector3f(player.getPosition());
		}else if(type == THIRD_PERSON){
			calculateZoom();
			calcYawAndPitch();
	        float horizontalDistance = calculateHorizontalDistance();
	        float verticleDistance = calculateVerticleDistance();
	        calculateCameraPosition(horizontalDistance, verticleDistance);
	        yaw = 180 - (player.getRotY() - 90 + angleAroundPlayerY);
		}else if(type == FREE_ROAM){
	        speed = MOVE_SPEED * DisplayManager.getFrameTimeSeconds();
	        angleSpeed = ANGLE_MOVE_SPEED * DisplayManager.getFrameTimeSeconds();
	        handleInputs();
	        calculateZoom();
	        position.y = zoomDistance;
        }
	}
	
	private void handleInputs(){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			dz = (float) (-speed * Math.cos(Math.toRadians(yaw)));
			dx = (float) (speed * Math.sin(Math.toRadians(yaw)));
			position.z+= dz;
			position.x+= dx;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			dz = (float) (speed * Math.cos(Math.toRadians(yaw)));
			dx = (float) (-speed * Math.sin(Math.toRadians(yaw)));
			position.z+= dz;
			position.x+= dx;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			dz = (float) (-speed * Math.cos(Math.toRadians(yaw+90)));
			dx = (float) (speed * Math.sin(Math.toRadians(yaw+90)));
			position.z+= dz;
			position.x+= dx;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			dz = (float) (speed * Math.cos(Math.toRadians(yaw+90)));
			dx = (float) (-speed * Math.sin(Math.toRadians(yaw+90)));
			position.z+= dz;
			position.x+= dx;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
			yaw -= angleSpeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_E)){
			yaw += angleSpeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_R)){
			pitch -= angleSpeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_F)){
			pitch += angleSpeed;
		}
	}
	
	private void calcYawAndPitch(){
		int dx;
		int dy;
        dx = Mouse.getDX();
        dy = Mouse.getDY();
        if(Mouse.isGrabbed()){
	        if(type == FIRST_PERSON) yaw += (dx * mouseSensitivity);    
	        angleAroundPlayerY -= (dx * mouseSensitivity);
	
	        if(pitch-(dy * mouseSensitivity) <=90 && pitch-(dy * mouseSensitivity) >=-90 ){
	        	pitch-=(dy * mouseSensitivity);
	        }
        }
	}
	
	public void invertPitch(){
		this.pitch = -pitch;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	private void calculateZoom(){
		float zoomLevel = Mouse.getDWheel() * mouseWheelSensitivity;
		if(zoomDistance-zoomLevel>=MIN_ZOOM && zoomDistance-zoomLevel<=MAX_ZOOM)zoomDistance -= zoomLevel;
	}
	
	private float calculateHorizontalDistance(){
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticleDistance(){
		float vD = (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
		if(vD<0){
			vD = 0;
			pitch = 0;
		}
		return vD;
	}
	
	private void calculateCameraPosition(float hDistance, float vDistance){
		float theta = player.getRotY() + angleAroundPlayerY - 90;
		float offsetX = (float) (hDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (hDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + vDistance + OFFSET_Y;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
