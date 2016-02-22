package engineTester;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import de.matthiasmann.twl.utils.PNGDecoder;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import objConverter.OBJFileLoader;
import objInfoSaver.objInfoSave;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;

import menu.*;

import java.util.Random;
import renderEngine.OBJLoader;
import music.audioPlayer;

import java.awt.event.WindowAdapter;

public class MainGameLoop {

	public static void main(String[] args) throws FileNotFoundException {
		String[] randomInfo = new String[3];
		randomInfo[0] = "RandomStuffs";
	    randomInfo[1] = "12414241441";
	    randomInfo[2] = "fasbhf978t234f9h4n";
		objInfoSave saver = new objInfoSave();
		saver.objInfoAddInfo(randomInfo);

		//Frame Below -------------------------------------------------------------------------------------------------------------------------------------------------------
		JFrame menuFrame = new JFrame("Menu");
	    menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    menuFrame.setSize(1280,720);
	    menuFrame.setLocation(330,178);
	    menuFrame.setVisible(true);
	    menuPanel menuPanel = new menuPanel();
	    menuPanel.setLayout(null);
	    menuFrame.add(menuPanel);

	    JFrame helpFrame = new JFrame("Help");
	    helpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    helpFrame.setSize(1280,720);
	    helpFrame.setLocation(330,178);
	    helpFrame.setVisible(false);
	    menuPanel helpPanel = new menuPanel();
	    helpPanel.setLayout(null);
	    helpFrame.add(helpPanel);

	    JFrame settingsFrame = new JFrame("Settings");
	    settingsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    settingsFrame.setSize(1280,720);
	    settingsFrame.setLocation(330,178);
	    settingsFrame.setVisible(false);
	    menuPanel settingsPanel = new menuPanel();
	    settingsPanel.setLayout(null);
	    settingsFrame.add(settingsPanel);



	    JButton newButton = new JButton("New");
	    newButton.setVerticalTextPosition(AbstractButton.CENTER);
	    newButton.setHorizontalTextPosition(AbstractButton.LEADING);
	    newButton.setMnemonic(KeyEvent.VK_D);
	    newButton.setActionCommand("disable");
	    buttonActionListener newListener = new buttonActionListener();
	    newButton.addActionListener(newListener);
	    newButton.setBounds(425 ,50, 450, 100);
	    newButton.setIcon(new ImageIcon("res/new.png"));
	    newButton.setOpaque(false);
	    newButton.setContentAreaFilled(false);
	    newButton.setBorderPainted(false);
	    JButton continueButton = new JButton("Continue");
	    continueButton.setVerticalTextPosition(AbstractButton.CENTER);
	    continueButton.setHorizontalTextPosition(AbstractButton.LEADING);
	    continueButton.setMnemonic(KeyEvent.VK_D);
	    continueButton.setActionCommand("disable");
	    buttonActionListener continueListener = new buttonActionListener();
	    continueButton.addActionListener(continueListener);
	    continueButton.setBounds(425 ,200, 450, 100);
	    continueButton.setIcon(new ImageIcon("res/continue.png"));
	    continueButton.setOpaque(false);
	    continueButton.setContentAreaFilled(false);
	    continueButton.setBorderPainted(false);
	    JButton settingsButton = new JButton("Settings");
	    settingsButton.setVerticalTextPosition(AbstractButton.CENTER);
	    settingsButton.setHorizontalTextPosition(AbstractButton.LEADING);
	    settingsButton.setMnemonic(KeyEvent.VK_D);
	    settingsButton.setActionCommand("disable");
	    settingsButton.setOpaque(false);
	    settingsButton.setContentAreaFilled(false);
	    settingsButton.setBorderPainted(false);
	    buttonActionListener settingsListener = new buttonActionListener();
	    settingsButton.addActionListener(settingsListener);
	    settingsButton.setBounds(425 ,350, 450, 100);
	    settingsButton.setIcon(new ImageIcon("res/settings.png"));
	    JButton helpButton = new JButton("Help");
	    helpButton.setVerticalTextPosition(AbstractButton.CENTER);
	    helpButton.setHorizontalTextPosition(AbstractButton.LEADING);
	    helpButton.setMnemonic(KeyEvent.VK_D);
	    helpButton.setActionCommand("disable");
	    helpButton.setOpaque(false);
	    helpButton.setContentAreaFilled(false);
	    helpButton.setBorderPainted(false);
	    buttonActionListener helpListener = new buttonActionListener();
	    helpButton.addActionListener(helpListener);
	    helpButton.setBounds(425 ,500, 450, 100);
	    helpButton.setIcon(new ImageIcon("res/help.png"));

	    JButton returnFromHelpButton = new JButton("Return");
	    returnFromHelpButton.setVerticalTextPosition(AbstractButton.CENTER);
	    returnFromHelpButton.setHorizontalTextPosition(AbstractButton.LEADING);
	    returnFromHelpButton.setMnemonic(KeyEvent.VK_D);
	    returnFromHelpButton.setActionCommand("disable");
	    returnFromHelpButton.setOpaque(false);
	    returnFromHelpButton.setContentAreaFilled(false);
	    returnFromHelpButton.setBorderPainted(false);
	    buttonActionListener returnFromHelpListener = new buttonActionListener();
	    returnFromHelpButton.addActionListener(returnFromHelpListener);
	    returnFromHelpButton.setBounds(425 ,500, 450, 100);
	    returnFromHelpButton.setIcon(new ImageIcon("res/help.png"));

	    JButton returnFromSettingsButton = new JButton("Settings");
	    returnFromSettingsButton.setVerticalTextPosition(AbstractButton.CENTER);
	    returnFromSettingsButton.setHorizontalTextPosition(AbstractButton.LEADING);
	    returnFromSettingsButton.setMnemonic(KeyEvent.VK_D);
	    returnFromSettingsButton.setActionCommand("disable");
	    returnFromSettingsButton.setOpaque(false);
	    returnFromSettingsButton.setContentAreaFilled(false);
	    returnFromSettingsButton.setBorderPainted(false);
	    buttonActionListener returnFromSettingsListener = new buttonActionListener();
	    returnFromSettingsButton.addActionListener(returnFromSettingsListener);
	    returnFromSettingsButton.setBounds(425 ,500, 450, 100);
	    returnFromSettingsButton.setIcon(new ImageIcon("res/settings.png"));



	    menuPanel.add(newButton);
	    menuPanel.add(continueButton);
	    menuPanel.add(settingsButton);
	    menuPanel.add(helpButton);

	    helpPanel.add(returnFromHelpButton);

	    settingsPanel.add(returnFromSettingsButton);

	    JSlider volume = null;
	    JSlider[] settingsSliders =  {volume};
	    String[] settingNames = {"volume"};
	    int STAT_MIN = -100;
		int STAT_MAX = 100;
		int STAT_INIT = 0;
	    NewChangeListener[] newChangeListeners = new NewChangeListener[settingNames.length];
	    NewChangeListener tempChangeListener = new NewChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				//changeValue((JSlider)e.getSource());
			}
		};

	    for(int i=0; i<settingsSliders.length; i++){
			JLabel sliderLabel = new JLabel(settingNames[i]+":");
			sliderLabel.setBounds(490 + 10, 18 + (i*100), 300, 50);
			settingsPanel.add(sliderLabel);

			settingsSliders[i] = new JSlider(JSlider.HORIZONTAL,
	                STAT_MIN, STAT_MAX, STAT_INIT);
			settingsSliders[i].addChangeListener(new ChangeListener(){
				@Override
				public void stateChanged(ChangeEvent e) {
					//changeValue((JSlider)e.getSource());
				}
			});
			settingsSliders[i].setMajorTickSpacing((STAT_MAX-STAT_MIN)/10);
			settingsSliders[i].setMinorTickSpacing(2);
			settingsSliders[i].setPaintTicks(true);
			settingsSliders[i].setPaintLabels(true);
			settingsSliders[i].setPaintTrack(false);
			settingsSliders[i].setBounds(490, 50 + (i*100), 300, 50);
			settingsPanel.add(settingsSliders[i]);
		}

	    menuFrame.validate();
	    menuPanel.repaint();
	    menuPanel.validate();
	    boolean onMenu = true;
	    boolean onHelp = false;
	    NewWindowListener menuWindowListener = new NewWindowListener();
	    menuFrame.addWindowListener(menuWindowListener);
	    audioPlayer audio = new audioPlayer();
		audio.play("res/menuSongWav.wav");
	    while(onMenu == true){
	    	if(newListener.accButtonInfo() == true){
	    		onMenu = false;
	    	}
	    	if(continueListener.accButtonInfo() == true){
	    		onMenu = false;
	    	}
	    	if(settingsListener.accButtonInfo() == true){
	    		//onMenu = false;
	    		onHelp = true;
	    		menuFrame.setVisible(false);
	    		settingsFrame.setVisible(true);
	    		while(onHelp == true){
	    			if(returnFromSettingsListener.accButtonInfo() == true){
	    				onHelp = false;
	    			}
	    			try{
	                    Thread.sleep(500);
	                }catch(Exception ex){
	                    System.exit(1);
	                }
	    		}
	    		settingsListener.modButton();
	    		returnFromSettingsListener.modButton();
	    		onHelp = false;
	    		settingsFrame.setVisible(false);
	    		menuFrame.setVisible(true);


	    	}
	    	if(helpListener.accButtonInfo() == true){
	    		//onMenu = false;
	    		onHelp = true;
	    		menuFrame.setVisible(false);
	    		helpFrame.setVisible(true);
	    		while(onHelp == true){
	    			if(returnFromHelpListener.accButtonInfo() == true){
	    				onHelp = false;
	    			}
	    			try{
	                    Thread.sleep(500);
	                }catch(Exception ex){
	                    System.exit(1);
	                }
	    		}
	    		helpListener.modButton();
	    		returnFromHelpListener.modButton();
	    		onHelp = false;
	    		helpFrame.setVisible(false);
	    		menuFrame.setVisible(true);
	    	}
	    	try{
                Thread.sleep(500);
            }catch(Exception ex){
                System.exit(1);
            }
	    	if(menuWindowListener.accIsClosed() == true){
	    		audio.close();
	    	}
	    }
	    audio.close();

	    //Frame Above --------------------------------------------------------------------------------------------------------------------------------------------------------
		//initializing stuff
		DisplayManager.createDisplay();
		menuFrame.setVisible(false); //ST
		Loader loader = new Loader();
//		Random random = new Random();
		TextMaster.init(loader);
		MasterRenderer renderer = new MasterRenderer(loader);
		GuiRenderer guiRenderer = new GuiRenderer(loader);

		//**********Font Setup************************
		FontType font = new FontType(loader.loadTexture("verdana"), new File("res/verdana.fnt"));

		//**********Textured Model Setup************************
		RawModel playerModel = OBJFileLoader.loadOBJ("testPlanet", loader);
		ModelTexture playerTex = new ModelTexture(loader.loadTexture("sunTex"));
		 TexturedModel playerTexModel = new TexturedModel(playerModel, playerTex);

		//**********Normal Map Setup************************
		TexturedModel barrelModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("barrel", loader),
				new ModelTexture(loader.loadTexture("barrel")));
		barrelModel.getTexture().setNormalMap(loader.loadTexture("barrelNormal"));
		barrelModel.getTexture().setShineDamper(10);
		barrelModel.getTexture().setReflectivity(0.5f);

		//**********Terrain Setup************************
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture,
				gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));


		//lists for keeping track of world items
		List<Entity> entities = new ArrayList<Entity>();
		List<Entity> normalMapEntities = new ArrayList<Entity>();
		List<Light> lights = new ArrayList<Light>();
		List<Terrain> terrains = new ArrayList<Terrain>();
		List<GuiTexture> guiTextures = new ArrayList<GuiTexture>();


		//**********Text Setup************************
		//					 (String text, float fontSize, FontType font, vec2D(posX, posY), maxLineLength, boolean centered);
		//GUIText text = new GUIText("Sanity", 2, font, new Vector2f(0.5f,0.5f), 0.5f, true);
		GUIText textSan = new GUIText("Sanity", 1.3f, font, new Vector2f(-0.22f,0.7f), 0.5f, true); //ST
		textSan.setColor(1, 1, 1);
		GUIText textSpr = new GUIText("Sprint", 1.3f, font, new Vector2f(-0.22f,0.78f), 0.5f, true); //ST
		textSpr.setColor(1, 1, 1);
		GUIText textCool = new GUIText("Cooldown", 1.3f, font, new Vector2f(0.7f,0.74f), 0.5f, true); //ST
		textCool.setColor(1, 1, 1);

		//**********GUI Picture************************
		//guiTextures.add(new GuiTexture(guiTextures.size(), new Vector2f(0.5f,0.5f), new Vector2f(0.5f,0.5f)));
		//guiTextures.add(new GuiTexture(1, new Vector2f(0.5f,0.5f), new Vector2f(0.5f,0.5f)));
		//guiTextures.add(new GuiTexture(3, new Vector2f(0.5f,0.5f), new Vector2f(0.5f,0.5f)));
		//guiTextures.add(new GuiTexture(5, new Vector2f(0.5f,0.5f), new Vector2f(0.5f,0.5f)));
		//guiTextures.add(new GuiTexture(loader.loadTexture("/sanity/sanityBar100"), new Vector2f(-.65f,-.54f), new Vector2f(0.45f,0.05f))); //ST
		guiTextures.add(new GuiTexture(loader.loadTexture("health"), new Vector2f(-0.9f,-0.67f), new Vector2f(0.2f,0.2f))); //ST
		guiTextures.add(new GuiTexture(loader.loadTexture("health"), new Vector2f(0.82f,-0.67f), new Vector2f(0.2f,0.2f))); //ST
		guiTextures.add(new GuiTexture(loader.loadTexture("fern"), new Vector2f(0.82f,0.75f), new Vector2f(0.2f,0.2f))); //ST
		guiTextures.add(new GuiTexture(loader.loadTexture("aim3lineYellowMidV2"), new Vector2f(0f,0f), new Vector2f(0.05f,0.05f))); //ST

		/**

		for(int x=0; x<90; x++){
			guiTextures.add(new GuiTexture(x, new Vector2f(0.5f,0.5f), new Vector2f(0.5f,0.5f)));
		}
		*/

		//**********Light Setup************************
		Light sun = new Light(new Vector3f(10000, 10000, -10000), new Vector3f(1.3f, 1.3f, 1.3f));
		lights.add(sun);

		//**********Terrain Setup************************
		Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, ("heightmap"));
		terrains.add(terrain);

		//**********Camera and Player Setup************************
		Camera camera = new Camera(Camera.FIRST_PERSON);
		Player player = new Player(camera, playerTexModel, new Vector3f(100, 0, 0), 0, 0, 0, 1);
		camera.setPlayer(player);
		entities.add(player);


		//**********Mouse Picker Setup************************
		//lets you get the coords of where the mouse is on the terrain
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);

		//**********Water Renderer Setup************************
		WaterFrameBuffers buffers = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), buffers);
		List<WaterTile> waters = new ArrayList<WaterTile>();
		WaterTile water = new WaterTile(75, -75, 0);
		waters.add(water);

		//**********Extra items************************
		Light l = new Light(new Vector3f(0, 0, 0), new Vector3f(.5f, .5f, .5f));
		lights.add(l);
		int testSanity = player.accSanity(); //ST
		GuiTexture sanityTexture = new GuiTexture(loader.loadTexture("/sanity/sanityBar"+testSanity), new Vector2f(-.65f,-.54f), new Vector2f(0.45f,0.05f));
		guiTextures.add(sanityTexture);
		//guiTextures.add(new GuiTexture(loader.loadTexture("/sanity/sanityBar"+testSanity), new Vector2f(-5f,-5f), new Vector2f(0.45f,0.05f)));
		int guiSanityTexturePosition = guiTextures.size()-1;
		//guiTextures.remove(guiTextures.get(guiSanityTexturePosition));
		//guiTextures.remove(guiTextures.get(guiSanityTexturePosition-1));
		//guiTextures.remove(guiTextures.get(guiSanityTexturePosition-2));

		while (!Display.isCloseRequested()) {


			/*testSanity = player.accSanity(); //ST Below
			if(player.accSanity() < 100){
				player.modSanity(player.accSanity()+1);
			}else{
				player.modSanity(1);
			}
			guiTextures.remove(guiTextures.get(guiSanityTexturePosition));
			guiRenderer.render(guiTextures);
			sanityTexture = new GuiTexture(loader.loadTexture("/sanity/sanityBar"+player.accSanity()), new Vector2f(-.65f,-.54f), new Vector2f(0.45f,0.05f));
			guiRenderer.render(guiTextures);
			guiTextures.add(sanityTexture); //ST Above
			//terrain = new Terrain(0, -1, loader, texturePack, blendMap, ("/sanity/sanityBar"+player.accSanity()));
*/

			if(camera.getType() != Camera.FREE_ROAM)player.move();
			camera.move();
			picker.update();
			for (Entity e : entities) {
				e.update();
			}
			for (Entity e : normalMapEntities) {
				e.update();
			}
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);

			//render reflection teture
			buffers.bindReflectionFrameBuffer();
			float distance = 2 * (camera.getPosition().y - water.getHeight());
			camera.getPosition().y -= distance;
			camera.invertPitch();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, 1, 0, -water.getHeight()+1));
			camera.getPosition().y += distance;
			camera.invertPitch();

			//render refraction texture
			buffers.bindRefractionFrameBuffer();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, water.getHeight()));

			//render to screen
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			buffers.unbindCurrentFrameBuffer();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, 100000));
			waterRenderer.render(waters, camera, sun);

			/**
			if(waitFor>3000){
				waitFor=0;
			}
			guiRenderer.render(guiTextures);
			if(waitFor/100 != theX){
				System.out.println(theX);
				guiTextures.remove(new GuiTexture(theX, new Vector2f(0.5f,0.5f), new Vector2f(0.5f,0.5f)));
				guiTextures.add(new GuiTexture(waitFor/100, new Vector2f(0.5f,0.5f), new Vector2f(0.5f,0.5f)));
			}
			*/
			//USE LOADTEXTURE IN LOAD
			guiRenderer.render(guiTextures);

			//guiRenderer.render(guiTextures);
			//guiRenderer
			TextMaster.render();


			DisplayManager.updateDisplay();

		}

		TextMaster.cleanUp();
		buffers.cleanUp();
		waterShader.cleanUp();
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();




	}
}
