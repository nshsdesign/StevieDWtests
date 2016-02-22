package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class Entity {

	private TexturedModel model;
	private TexturedModel storedModel;
	public Vector3f position;
	private float rotX, rotY, rotZ;
	private float scale;
	private boolean isHovered,prevHovered,usingStored = false;
	private float scalePercentage; //ST
	private int timeShrunken; // ST

	private int textureIndex = 0;

	public Entity(TexturedModel model, Vector3f position, float rotX,
			float rotY, float rotZ, float scale) {
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		scalePercentage = 1; // ST
		timeShrunken = 0; // ST
	}

	public Entity(TexturedModel model, int index, Vector3f position, float rotX,
			float rotY, float rotZ, float scale) {
		this.textureIndex = index;
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		scalePercentage = 1; // ST
		timeShrunken = 0; // ST
	}

	public float getTextureXOffset(){
		int column = textureIndex%model.getTexture().getNumberOfRows();
		return (float)column/(float)model.getTexture().getNumberOfRows();
	}

	public float getTextureYOffset(){
		int row = textureIndex/model.getTexture().getNumberOfRows();
		return (float)row/(float)model.getTexture().getNumberOfRows();
	}

	public void increasePosition(float dx, float dy, float dz){
		this.position.x+=dx;
		this.position.y+=dy;
		this.position.z+=dz;
	}

	public void increaseRotation(float dx, float dy, float dz){
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}

	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public float getScale() {
		return scale;
	}

	public void setScalePercentage(float newScalePercentage) { //ST
		this.scalePercentage = newScalePercentage;
	}

	public void setScale(float scale) {
		this.scale = scale*scalePercentage; //ST
	}
	
	public void timeShrunk(){ // ST
		if(timeShrunken < 2000){ //2000 is just for test
			this.timeShrunken++;
		}else{
			scalePercentage = 1;
			timeShrunken = 0;
		}
		
	}

	public void update() {
	}

	public BoundingBox getBoundingBox(){
		return new BoundingBox(new Vector3f(position.x-(scale), position.y-(scale), position.z-(scale)), 2*scale, 2*scale, 2*scale);
	}

//	public void createAndStoreNewLightEmmisionModel(float amount){
//		ModelTexture newTex = new ModelTexture(model.getTexture());
//		newTex.setLightEmmision(amount);
//		TexturedModel newModel = new TexturedModel(model.getRawModel(), newTex);
//		storedModel = newModel;
//	}
	public void switchToStoredModel(){
		TexturedModel temp = model;
		this.model = this.storedModel;
		this.storedModel = temp;
		usingStored = !usingStored;
	}

	public boolean isUsingStored(){
		return usingStored;
	}

	public void setHovered(boolean b){
		prevHovered = isHovered;
		isHovered = b;
	}

	public boolean isHovered(){
		return isHovered;
	}

	public boolean prevHovered(){
		return prevHovered;
	}

	public boolean hasStoredModel() {
		return storedModel != null;
	}
	
	public boolean isShrunken(){ //ST
		if(scalePercentage == 1){
			return false;
		}else{
			return true;
		}
	}
}