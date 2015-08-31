package com.iteye.weimingtom.guichan.demo.fps;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Key;
import com.iteye.weimingtom.guichan.basic.KeyInput;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.event.KeyEvent;
import com.iteye.weimingtom.guichan.font.ImageFont;
import com.iteye.weimingtom.guichan.gui.Globals;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.listener.ApplicationListener;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.platform.Image;
import com.iteye.weimingtom.guichan.widget.Container;
import com.iteye.weimingtom.guichan.widget.DropDown;
import com.iteye.weimingtom.guichan.widget.Icon;
import com.iteye.weimingtom.guichan.widget.Label;
import com.iteye.weimingtom.guichan.widget.ListBox;
import com.iteye.weimingtom.guichan.widget.ScrollArea;
import com.iteye.weimingtom.guichan.widget.Slider;
import com.iteye.weimingtom.guichan.widget.TextBox;

public class FPSDemo implements ApplicationListener {
//	private boolean mRunning;
	private boolean mInit;
	private boolean mResolutionChange;
	private boolean mHaveFullscreen;
	
	private int mWidth;
	private int mHeight;

	private float mRotation;
	private double mGlow;
	private long/*float*/ mDeltaTime;
	private long/*float*/ mTime;
	
//	SDL_Surface* mScreen;
//	SDL_Event mEvent;

//	Mix_Chunk* mChooseSound;
//	Mix_Chunk* mEscapeSound;
//	Mix_Chunk* mOptionsSound;
//	Mix_Chunk* mMusic;
	
//	private AWTGraphics mOpenGLGraphics;
//	private AWTInput mSDLInput;
//	private AWTImageLoader mOpenGLImageLoader;
//	private Gui mGui;
	private Container mTop;
	private ImageFont mFont;
	private ImageFont mSmallBlackFont;
	private ImageFont mWhiteFont;
	private ImageFont mHighLightFont;
	private Image mTitleImage;
	private Icon mTitle;
	private Image mCloudImage;
	private Image mPlanetImage;
	private Image mStarsImage;
	private Image mMoonImage;
	private Image mMoonRedImage;

	//FIXME:new added
	private Image _mFPSDemoBg;
	
	private Image mBoxImage;
	private Icon mSingleplayBoxIcon;
	private Icon mMultiplayBoxIcon;
	private Icon mOptionsBoxIcon;
	
	private Label mVersionLabel;
	private Label mSingleplayLabel;
	private Label mMultiplayLabel;
	private Label mOptionsLabel;
	private TextBox mSingleplayText;
	private TextBox mMultiplayText;
	
	private Container mMain;
	private Container mSingleplay;
	private Container mMultiplay;
	private Container mOptions;
	private TextBox mDemoInfo;
	
//	GLuint mPlanetTexture;
//	GLuint mCloudTexture;
//	GLuint mStarsTexture;
//	GLuint mMoonTexture;
//	GLuint mMoonRedTexture;
	
//	GLUquadricObj *mQuad1;
//	GLUquadricObj *mQuad2;
//	GLUquadricObj *mClouds2;
//	GLUquadricObj *mMoon;
//	GLUquadricObj *mMoonRed;
//	GLfloat LightAmbient[4];
//	GLfloat Light2Ambient[4];
//	GLfloat LightDiffuse[4];
//	GLfloat Light2Diffuse[4];
//	GLfloat LightPosition[4];
//	GLfloat Light2Position[4];
//	GLfloat LightSpotDirection[3];
//	GLfloat Light2SpotDirection[3];
	
	private FPSButton mSingleplayButton;
	private FPSButton mMultiplayButton;
	private FPSButton mOptionsButton;
	private FPSButton mQuitButton;
	private FPSButton mSingleplayBackButton;
	private FPSButton mMultiplayBackButton;
	private FPSButton mOptionsBackButton;
	
	private FPSCheckBox mFullScreen;
	private DropDown mResolution;	
	private ResolutionListModel mResolutionListModel;
	private ScrollArea mResolutionScrollArea;
	private ListBox mResolutionListBox;

	private Slider mVolume;
	private Label mVolumePercent;
	private Label mVolumeLabel;
	private Label mResolutionLabel;
	private Image mSplashImage;
	private Icon mSplashIcon;
    
	
    private final class ButtonActionListener implements ActionListener {
    	public void action(ActionEvent actionEvent) throws GuichanException {
    		if (actionEvent.getId().equals("quit")) {
//    			Mix_PlayChannel(-1, mEscapeSound, 0);
//    			mRunning = false;
            	if (Globals.app != null) {
            		Globals.app.dispose();
            	}
    		} else if (actionEvent.getId().equals("singleplay")) {
//    			Mix_PlayChannel(-1, mChooseSound, 0);
    	 		mMain.setVisible(false);
    	 		mSingleplay.setVisible(true);
    	 	} else if (actionEvent.getId().equals("multiplay")) {
//    	 		Mix_PlayChannel(-1, mChooseSound, 0);
    	 		mMain.setVisible(false);
    	 		mMultiplay.setVisible(true);
    	 	} else if (actionEvent.getId().equals("options")) {
//    			Mix_PlayChannel(-1, mChooseSound, 0);
    	 		mMain.setVisible(false);
    	 		mOptions.setVisible(true);
    	 	} else if (actionEvent.getId().equals("back")) {
//    	 		Mix_PlayChannel(-1, mEscapeSound, 0);
    	 		mMain.setVisible(true);
    	 		mSingleplay.setVisible(false);
    	 		mMultiplay.setVisible(false);
    	 		mOptions.setVisible(false);
    	 	} else if (actionEvent.getId().equals("fullscreen")) {
//    			Mix_PlayChannel(-1, mOptionsSound, 0);
    			initVideo();
    		} else if (actionEvent.getId().equals("resolution")) {
//    			Mix_PlayChannel(-1, mOptionsSound, 0);
    			initVideo();
    		} else if (actionEvent.getId().equals("volume")) {
    			String str = "" + (int)(mVolume.getValue() * 100) + "%";
    			mVolumePercent.setCaption(str);
    			mVolumePercent.adjustSize();
//    	 		double m = MIX_MAX_VOLUME;
//    	 		double p = mVolume.getValue();
//    	 		Mix_Volume(-1,(int)(m*(p/100)));
    		}
        }
    };

	public void init() throws GuichanException {
		mRotation = 0; 
//		mRunning = true;
		mGlow = 0;
		
		mWidth = 800;
		mHeight = 600;
		mTime = -1; 
		mDeltaTime = 0;
		mInit = true;
		mResolutionChange = false;
		mHaveFullscreen = false;

		
		
		
		
		
		
		
		// Init SDL
//		SDL_Init(SDL_INIT_VIDEO | SDL_INIT_AUDIO);
//		SDL_GL_SetAttribute(SDL_GL_DOUBLEBUFFER, 1);
//		mScreen = SDL_SetVideoMode(800, 600, 32, SDL_HWSURFACE | SDL_OPENGL | SDL_HWACCEL);	

//		SDL_EnableUNICODE(1);
//		SDL_EnableKeyRepeat(SDL_DEFAULT_REPEAT_DELAY, SDL_DEFAULT_REPEAT_INTERVAL);
//		SDL_WM_SetCaption("Guichan FPS demo", NULL);
		
		// Init SDL_Mixer
//		Mix_OpenAudio(22050, MIX_DEFAULT_FORMAT, 2, 1024);
		
		// Load sounds and music
//	 	mChooseSound = Mix_LoadWAV("sound/sound4.wav");
//	 	mEscapeSound = Mix_LoadWAV("sound/sound3.wav");
//	 	mOptionsSound = Mix_LoadWAV("sound/sound2.wav");
//	 	mMusic = Mix_LoadWAV("sound/space.ogg");
		
	 	// Set the mixer volume
//	 	double m = MIX_MAX_VOLUME;
//	 	double p = 70;
//	 	Mix_Volume(-1,(int)(m*(p/100)));
		
		// Create some GLU quadrics	
//		mQuad1 = gluNewQuadric();
//		mQuad2 = gluNewQuadric();
//		mMoon = gluNewQuadric();
//		mMoonRed = gluNewQuadric();
//		mClouds2 = gluNewQuadric();
//		gluQuadricNormals(mQuad1, GLU_SMOOTH);
//		gluQuadricTexture(mQuad1,GL_TRUE);
//		gluQuadricNormals(mQuad2, GLU_SMOOTH);
//		gluQuadricTexture(mQuad2,GL_TRUE);
//		gluQuadricNormals(mMoon, GLU_SMOOTH);
//		gluQuadricTexture(mMoon,GL_TRUE);
//		gluQuadricNormals(mMoonRed, GLU_SMOOTH);
//		gluQuadricTexture(mMoonRed,GL_TRUE);
//		gluQuadricNormals(mClouds2, GLU_SMOOTH);
//		gluQuadricTexture(mClouds2,GL_TRUE);
	
		
		initOpenGL();
		initGui();
		resize();
		
		mInit = false;	
	}
	
	public void halt() throws GuichanException {
		cleanGui();
//	 	Mix_FreeChunk(mChooseSound);
//	 	Mix_FreeChunk(mEscapeSound);
//	 	Mix_FreeChunk(mOptionsSound);
//	 	Mix_FreeChunk(mMusic);
//	 	Mix_CloseAudio();

//		SDL_Quit();
	}
	
	//FIXME:
	private long _startTicks = System.currentTimeMillis();
	private int _status = 0;
	public void run() throws GuichanException {
		long curLastTicks = System.currentTimeMillis();
		long deltaTicks = curLastTicks - _startTicks;
		if (deltaTicks < 3000) {
			runIntro();
			_status = 0;
		} else {
			if (_status == 0) {
				//clear all before ?!
				Globals.gui.setTop(mTop);
//				if (mMusic > 0) {
//					Mix_PlayChannel(-1, mMusic, -1);		
//				}
			}
			runMain();
			_status = 1;
		}
	}
	
	
	//FIXME:
	private void runIntro() {
		//FIXME:		
		if (mTime < 0) {
			mTime = System.currentTimeMillis();
		}
		mDeltaTime = System.currentTimeMillis() - mTime;
		mTime = System.currentTimeMillis();	

		input();
		
//		glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//		mGui->draw();
//		SDL_GL_SwapBuffers();
//		SDL_Delay(10);
		
//		mGui->setTop(mTop);
//		if (mMusic > 0)
//		{
//	    Mix_PlayChannel(-1, mMusic, -1);		
//		}
	}
	
	//FIXME:
	private void runMain() throws GuichanException {
		//FIXME:
		
		if (mResolutionChange) {
			// Clear the screen before remaking the Gui
//			glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//			SDL_GL_SwapBuffers();
			
			cleanGui();
			initGui();		
			resize();
			mResolutionChange = false;
		}

		if (mTime < 0) {
			mTime = System.currentTimeMillis();
		}
		
		mDeltaTime = System.currentTimeMillis() - mTime;
		mTime = System.currentTimeMillis();	
		
		input();
//		mGui->logic();
		
//		glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
//		glLoadIdentity( );
		
		drawSpace();
//		gluLookAt(0.0,-1.8,-2.9,
//							0.0,-1.2,1.0,
//							0.0,-1.0,0.0);		
		
		drawBackground();
	}
	
	private void resize() throws GuichanException {
	 	mTitle.setPosition(mWidth / 2 - 330, mHeight / 2 - 290);
	 	mDemoInfo.setPosition(mWidth / 2 - 390, mHeight - 50);
	 	mVersionLabel.setPosition(mWidth - 100, mHeight - 80);

	 	mTop.setDimension(new Rectangle(0, 0, mWidth, mHeight));
	 	mMain.setDimension(new Rectangle(0, 0, mWidth, mHeight));
	 	mSingleplay.setDimension(new Rectangle(0, 0, mWidth, mHeight));
		mMultiplay.setDimension(new Rectangle(0, 0, mWidth, mHeight));
		mOptions.setDimension(new Rectangle(0, 0, mWidth, mHeight));	

	 	mSingleplayButton.setPosition(mWidth / 2 - 100, mHeight / 2 - 100);
	 	mMultiplayButton.setPosition(mWidth / 2 - 100, mHeight / 2 - 60);
	 	mOptionsButton.setPosition(mWidth / 2 - 100, mHeight / 2 - 20);
	 	mQuitButton.setPosition(mWidth / 2 - 100, mHeight / 2 +60);

	 	mSingleplayBackButton.setPosition(mWidth / 2 - 290, mHeight / 2 + 180);
		mMultiplayBackButton.setPosition(mWidth / 2 - 290, mHeight / 2 + 180);
	 	mOptionsBackButton.setPosition(mWidth / 2 - 290, mHeight / 2 + 180);
			
	 	mSingleplayBoxIcon.setPosition(mWidth / 2 - 300, mHeight / 2 - 150);
		mMultiplayBoxIcon.setPosition(mWidth / 2 - 300, mHeight / 2 - 150);
	 	mOptionsBoxIcon.setPosition(mWidth / 2 - 300, mHeight / 2 - 150);
		
	 	mSingleplayText.setPosition(mWidth / 2 - 285, mHeight / 2 - 120);
	 	mSingleplayLabel.setPosition(mWidth / 2 + 150, mHeight / 2 - 145);

	 	mMultiplayText.setPosition(mWidth / 2 - 285, mHeight / 2 - 120);
	 	mMultiplayLabel.setPosition(mWidth / 2 + 150, mHeight / 2 - 145);

	 	mOptionsLabel.setPosition(mWidth / 2 + 150, mHeight / 2 - 145);
	 	mFullScreen.setPosition(mWidth / 2 - 200, mHeight / 2 - 100);
	 	mResolution.setPosition(mWidth / 2 - 90, mHeight / 2 - 70);
	 	mResolutionLabel.setPosition(mWidth / 2 -200, mHeight / 2 - 70);
	 	mVolume.setPosition(mWidth / 2 - 90, mHeight / 2 - 40);
	 	mVolumePercent.setPosition(mWidth / 2 +120, mHeight / 2 - 40);
	 	mVolumeLabel.setPosition(mWidth / 2 - 200, mHeight / 2 - 40);
	}
	
	private void initOpenGL() {
//		// Init OpenGL
//		glViewport(0, 0, mWidth, mHeight);		
//	 	glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
//		
//		glShadeModel(GL_SMOOTH);
//		glClearDepth(1.0f);
//		glEnable(GL_DEPTH_TEST);
//		glDepthFunc(GL_LEQUAL);
//		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
//			
//	 	glMatrixMode(GL_PROJECTION);
//	 	glLoadIdentity();	
//	 	gluPerspective(50.0, mWidth/mHeight, 1.0, 10.0); 
//
//	 	glMatrixMode(GL_MODELVIEW);
//		glLoadIdentity();
//		
//		// Init Light
//		Light2Ambient[0] =  0.5f;
//		Light2Ambient[1]	= 0.4f;
//		Light2Ambient[2]	= 0.7f;
//		Light2Ambient[3]	= 1.0f;
//
//		Light2Diffuse[0] = 1.0f;
//		Light2Diffuse[1] = 1.0f;
//		Light2Diffuse[2] = 1.0f;
//		Light2Diffuse[3] =	1.0f;
//
//		Light2Position[0] =  10.0f;
//		Light2Position[1] =	 .5f;
//		Light2Position[2] =  0.0f;
//		Light2Position[3] =  1.0f;
//
//		Light2SpotDirection[0] = -1.0;
//		Light2SpotDirection[1] = 0.0;
//		Light2SpotDirection[2] = 0.0;
//
//		glLightfv(GL_LIGHT2, GL_POSITION, Light2Position);
//		glLightfv(GL_LIGHT2, GL_AMBIENT, Light2Ambient);
//		glLightfv(GL_LIGHT2, GL_DIFFUSE, Light2Diffuse); 
//		glLightfv(GL_LIGHT2, GL_SPOT_DIRECTION, Light2SpotDirection);
//		glLightf(GL_LIGHT2, GL_SPOT_CUTOFF, 45.0);
//		
//		glEnable(GL_LIGHT2);
	}
	
	private void initVideo() {
		if (mResolution.getSelected() == 0) {
	 		mWidth = 1024;
	 		mHeight = 768;
			mResolutionChange = true;
	 	} else if (mResolution.getSelected() == 1) {
	 		mWidth = 800;
	 		mHeight = 600;
			mResolutionChange = true;
	 	}
	 	if (mFullScreen.isSelected()/*.isMarked()*/) {
//	 		mScreen = SDL_SetVideoMode(mWidth, mHeight, 32, SDL_HWSURFACE | SDL_OPENGL | SDL_HWACCEL | SDL_FULLSCREEN);
			mHaveFullscreen = true;
	 	} else {
			mHaveFullscreen = false;
//	 		mScreen = SDL_SetVideoMode(mWidth, mHeight, 32, SDL_HWSURFACE | SDL_OPENGL | SDL_HWACCEL);
	 	}
//	 	mOpenGLGraphics->setTargetPlane(mWidth, mHeight);
		initOpenGL();
	}
	
	private void initGui() throws GuichanException {
//    	mOpenGLImageLoader = new AWTImageLoader();
//    	gcn::Image::setImageLoader(mOpenGLImageLoader); 
//    	mOpenGLGraphics = new AWTGraphics();
//    	mOpenGLGraphics->setTargetPlane(mWidth, mHeight);
//    	mSDLInput = new gcn::SDLInput();

        mTop = new Container();
//		mTop.setDimension(new Rectangle(0, 0, 640, 480));
		mTop.setOpaque(false);
        Globals.gui.setTop(mTop);
        Globals.gui.setTabbingEnabled(false);
//        Globals.gui.setGraphics(mOpenGLGraphics);
//        Globals.gui.setInput(mSDLInput);
    	
     	mFont = new ImageFont("fonts/techyfontbig2.png", " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,!?-+/():;%&`'*#=[]\"");
     	mHighLightFont = new ImageFont("fonts/techyfontbighighlight.png", " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,!?-+/():;%&`'*#=[]\"");
     	mSmallBlackFont = new ImageFont("fonts/techyfontblack.png", " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,!?-+/():;%&`'*#=[]\"");
     	mWhiteFont = new ImageFont("fonts/techyfontwhite.png", " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,!?-+/():;%&`'*#=[]\"");
     	Widget.setGlobalFont(mWhiteFont);
        
    	mTitleImage = Image.load("images/title2.png");
    	mTitle = new Icon(mTitleImage);
    	mTop.add(mTitle);
    	
    	mDemoInfo = new TextBox(
    		"     Copyright 2004, 2005, 2006 (c) Darkbits. This is a Demo demonstrating Guichan with SDL and OpenGL.\n" + 
    		"Guichan is licensed under BSD. For more information about Guichan and visit http://guichan.sourceforge.net\n" + 
    		"            Code Yakslem (Olof Nassen). Art Finalman (Per Larsson). Darkbits logo Haiko (Henrik Vahlgren).");
    	mDemoInfo.setFont(mSmallBlackFont);
    	mDemoInfo.setOpaque(false);
    	mDemoInfo.setEditable(false);
    	mDemoInfo.setFocusable(false);
//        mDemoInfo.setBorderSize(0);
        mDemoInfo.setFrameSize(0);
        mTop.add(mDemoInfo);

    	mVersionLabel = new Label("Version 1.00");
    	mVersionLabel.setFont(mSmallBlackFont);
    	mTop.add(mVersionLabel);
    	
    	mBoxImage = Image.load("images/box.png");
    	
    	mSplashImage = Image.load("images/splash.png");
    	mSplashIcon = new Icon(mSplashImage);

    	if (mInit) {
    		Globals.gui.setTop(mSplashIcon);		
    		mSplashIcon.setPosition(
    			mWidth / 2 -mSplashImage.getWidth() / 2,
                mHeight / 2 -mSplashImage.getHeight() / 2);	
    	} else {
    		Globals.gui.setTop(mTop);
    	}
    	
    	loadTextures();
    	initMain();
    	initSingleplay();
    	initMultiplay();
    	initOptions();
	}
	
	private void cleanGui() throws GuichanException {
		cleanTextures();
		cleanMain();	
		cleanSingleplay();
		cleanMultiplay();
		cleanOptions();
		
		mBoxImage.destroy();
		mBoxImage = null;
		
		mFont.destroy();
		mFont = null;
		
		mHighLightFont.destroy();
		mHighLightFont = null;
		
		mSmallBlackFont.destroy();
		mSmallBlackFont = null;
		
		mWhiteFont.destroy();
		mWhiteFont = null;
		
		mTop.destroy();
	 	mTop = null;
	 	
//	 	mGui = null;	
		
	 	mSplashIcon.destroy();
	 	mSplashIcon = null;
		
	 	mSplashImage.destroy();
	 	mSplashImage = null;
	 	
//	 	mSDLInput = null;
//	 	mOpenGLGraphics = null;
//	 	mOpenGLImageLoader = null;
	}
	
	private void initMain() throws GuichanException {
		mMain = new Container();
		mMain.setOpaque(false);
		mTop.add(mMain);
		
		mSingleplayButton = new FPSButton("Singleplayer");
		mSingleplayButton.setHighLightFont(mHighLightFont);
		mSingleplayButton.adjustSize();
		mSingleplayButton.setActionEventId("singleplay");
		mSingleplayButton.addActionListener(new ButtonActionListener());
		mSingleplayButton.setFont(mFont);
		mSingleplayButton.adjustSize();
		mMain.add(mSingleplayButton);
		
		mMultiplayButton = new FPSButton("Multiplayer");
		mMultiplayButton.setHighLightFont(mHighLightFont);
		mMultiplayButton.adjustSize();
		mMultiplayButton.setActionEventId("multiplay");
		mMultiplayButton.addActionListener(new ButtonActionListener());
		mMultiplayButton.setFont(mFont);
		mMultiplayButton.adjustSize();
		mMain.add(mMultiplayButton);

		mOptionsButton = new FPSButton("Options");
		mOptionsButton.setHighLightFont(mHighLightFont);
		mOptionsButton.adjustSize();
		mOptionsButton.setActionEventId("options");
		mOptionsButton.addActionListener(new ButtonActionListener());
		mOptionsButton.setFont(mFont);
		mOptionsButton.adjustSize();
		mMain.add(mOptionsButton);

		mQuitButton = new FPSButton("Quit");
		mQuitButton.setHighLightFont(mHighLightFont);
		mQuitButton.adjustSize();
		mQuitButton.setActionEventId("quit");
		mQuitButton.addActionListener(new ButtonActionListener());
		mQuitButton.setFont(mFont);
		mQuitButton.adjustSize();
		mMain.add(mQuitButton);
	}
	
	private void cleanMain() throws GuichanException {
		mSingleplayButton.destroy();
		mSingleplayButton = null;
		
		mMultiplayButton.destroy();
		mMultiplayButton = null;
		
		mOptionsButton.destroy();
		mOptionsButton = null;
		
		mQuitButton.destroy();
		mQuitButton = null;
	 	
		mVersionLabel.destroy();
		mVersionLabel = null;
		
		mTitle.destroy();
		mTitle = null;
		
	 	mTitleImage.destroy();
	 	mTitleImage = null;
	 	
	 	mDemoInfo.destroy();
	 	mDemoInfo = null;
	 	
	 	mMain.destroy();
	 	mMain = null;
	}
	
	private void initSingleplay() throws GuichanException {
		mSingleplay = new Container();
		mSingleplay.setVisible(false);
		mSingleplay.setOpaque(false);		
		mTop.add(mSingleplay);

		mSingleplayBoxIcon = new Icon(mBoxImage);
		mSingleplay.add(mSingleplayBoxIcon);
		
		mSingleplayLabel = new Label("Singleplayer");
		mSingleplayLabel.setFont(mWhiteFont);
		mSingleplayLabel.adjustSize();
		mSingleplay.add(mSingleplayLabel);

		mSingleplayText = new TextBox(
			"I'm verry sorry but this is not an actual game.\n" + 
			"It's a demonstration of the GUI library Guichan.\n" + 
			"But who knows...\n" + 
            "Maybe it will be a game here someday.\n");
		mSingleplayText.setFont(mWhiteFont);
		mSingleplayText.setOpaque(false);
	    mSingleplayText.setEditable(false);
//	    mSingleplayText.setBorderSize(0);
	    mSingleplayText.setFrameSize(0);
		mSingleplay.add(mSingleplayText);

		mSingleplayBackButton = new FPSButton("Back");
		mSingleplayBackButton.setHighLightFont(mHighLightFont);
		mSingleplayBackButton.adjustSize();
		mSingleplayBackButton.setActionEventId("back");
		mSingleplayBackButton.addActionListener(new ButtonActionListener());
		mSingleplayBackButton.setFont(mFont);
		mSingleplayBackButton.adjustSize();
		
		mSingleplay.add(mSingleplayBackButton);
	}
	
	private void cleanSingleplay() throws GuichanException {
		mSingleplayBoxIcon.destroy();
		mSingleplayBoxIcon = null;
		
		mSingleplayText.destroy();
		mSingleplayText = null;
		
		mSingleplayLabel.destroy();
		mSingleplayLabel = null;
		
		mSingleplay.destroy();
		mSingleplay = null;
	}
	
	private void initMultiplay() throws GuichanException {
		mMultiplay = new Container();
		mMultiplay.setVisible(false);
		mMultiplay.setOpaque(false);
		mTop.add(mMultiplay);

		mMultiplayBoxIcon = new Icon(mBoxImage);
		mMultiplay.add(mMultiplayBoxIcon);
	 
		mMultiplayLabel = new Label("Multiplayer");
		mMultiplayLabel.setFont(mWhiteFont);
		mMultiplayLabel.adjustSize();  
		mMultiplay.add(mMultiplayLabel);
		
		mMultiplayText = new TextBox(
			"I'm verry sorry but this is not an actuall game.\n" + 
			"It's a demonstration of the GUI library Guichan.\n" + 
			"But who knows...\n" + 
			"Maybe it will be a game here someday.\n");
		mMultiplayText.setFont(mWhiteFont);
		mMultiplayText.setOpaque(false);
		mMultiplayText.setEditable(false);
//	    mMultiplayText.setBorderSize(0);
		mMultiplayText.setFrameSize(0);
		mMultiplay.add(mMultiplayText);

		mMultiplayBackButton = new FPSButton("Back");
		mMultiplayBackButton.setHighLightFont(mHighLightFont);
		mMultiplayBackButton.adjustSize();
		mMultiplayBackButton.setActionEventId("back");
		mMultiplayBackButton.addActionListener(new ButtonActionListener());
		mMultiplayBackButton.setFont(mFont);
		
		mMultiplayBackButton.adjustSize();
		
		mMultiplay.add(mMultiplayBackButton);
	}
	
	private void cleanMultiplay() throws GuichanException {
		mMultiplayBoxIcon.destroy();
		mMultiplayBoxIcon = null;
		
		mMultiplayBackButton.destroy();
		mMultiplayBackButton = null;
		
		mMultiplayText.destroy();
		mMultiplayText = null;
		
		mMultiplayLabel.destroy();
		mMultiplayLabel = null;
		
		mMultiplay.destroy();
		mMultiplay = null;
	}
	
	private void initOptions() throws GuichanException {
		mOptions = new Container();
		mOptions.setVisible(false);
		mOptions.setOpaque(false);
		mTop.add(mOptions);
		
		mOptionsBoxIcon = new Icon(mBoxImage);
		mOptions.add(mOptionsBoxIcon);
		
		mOptionsLabel = new Label("Options");
		mOptionsLabel.setFont(mWhiteFont);
		mOptionsLabel.adjustSize();
		mOptions.add(mOptionsLabel);

		mFullScreen = new FPSCheckBox("Fullscreen");
		mFullScreen.setFont(mWhiteFont);
		mFullScreen.adjustSize();
		mFullScreen.setBackgroundColor(new Color(0x331010));
		mFullScreen.setForegroundColor(new Color(0xffffff));
		mFullScreen.setBaseColor(new Color(0x771010));
		mFullScreen.setActionEventId("fullscreen");
		mFullScreen.addActionListener(new ButtonActionListener());
		//mFullScreen.setMarked(mHaveFullscreen);
		mFullScreen.setSelected(mHaveFullscreen);
		mOptions.add(mFullScreen);

		mResolutionScrollArea = new ScrollArea();
		mResolutionScrollArea.setBackgroundColor(new Color(0x331010));
		mResolutionScrollArea.setForegroundColor(new Color(0x331010));
		mResolutionScrollArea.setBaseColor(new Color(0x771010));

		mResolutionListBox = new ListBox();
		mResolutionListBox.setBackgroundColor(new Color(0x331010));
		mResolutionListBox.setForegroundColor(new Color(0x331010));
		mResolutionListBox.setBaseColor(new Color(0x771010));
		//FIXME:
		mResolutionListBox.setSelectionColor(new Color(0x000000));
		
		mResolutionListModel = new ResolutionListModel();
		mResolution = new DropDown(mResolutionListModel,
			mResolutionScrollArea,
			mResolutionListBox);
		mResolution.setWidth(200);
		mResolution.setBackgroundColor(new Color(0x331010));
		mResolution.setForegroundColor(new Color(0x331010));
		mResolution.setBaseColor(new Color(0x771010));
		//FIXME:
		mResolution.setSelectionColor(new Color(0x000000));
		
		if (mWidth == 800) {
			mResolution.setSelected(1);
		} else {
			mResolution.setSelected(0);
		}	
		mResolution.setActionEventId("resolution");
		mResolution.addActionListener(new ButtonActionListener());
		mOptions.add(mResolution);

		mVolume = new Slider(0.0, 1.0);
		mVolume.setWidth(200);
		mVolume.setHeight(20);
		mVolume.setValue(0.7);
		mVolume.setBackgroundColor(new Color(0x331010));
		mVolume.setForegroundColor(new Color(0x331010));
		mVolume.setBaseColor(new Color(0x771010));
		mVolume.setActionEventId("volume");
		mVolume.addActionListener(new ButtonActionListener());
		mVolume.setMarkerLength(20);
		mOptions.add(mVolume);

		mVolumePercent = new Label("70%");
		mOptions.add(mVolumePercent);

		mVolumeLabel = new Label("Volume");
		mOptions.add(mVolumeLabel);

		mResolutionLabel = new Label("Resolution");
		mOptions.add(mResolutionLabel);

		mOptionsBackButton = new FPSButton("Back");
		mOptionsBackButton.setHighLightFont(mHighLightFont);
		mOptionsBackButton.adjustSize();
		mOptionsBackButton.setActionEventId("back");
		mOptionsBackButton.addActionListener(new ButtonActionListener());
		mOptionsBackButton.setFont(mFont);
		mOptionsBackButton.adjustSize();
		mOptions.add(mOptionsBackButton);
	}
	
	private void cleanOptions() throws GuichanException {
		mOptionsBoxIcon.destroy();
		mOptionsBoxIcon = null;
		
		mOptionsBackButton.destroy();
		mOptionsBackButton = null;
		
		mResolutionLabel.destroy();
		mResolutionLabel = null;
		
		mVolumeLabel.destroy();
		mVolumeLabel = null;
		
		mVolumePercent.destroy();
		mVolumePercent = null;
		
		mVolume.destroy();
		mVolume = null;
		
		mResolutionListBox.destroy();
		mResolutionListBox = null;
		
		mResolutionScrollArea.destroy();
		mResolutionScrollArea = null;
		
		mResolutionListModel = null;
		
		mResolution.destroy();
		mResolution = null;
		
		mFullScreen.destroy();
		mFullScreen = null;
		
		mOptionsLabel.destroy();
		mOptionsLabel = null;
		
		mOptions.destroy();
		mOptions = null;
	}
	
	private void loadTextures() throws GuichanException {
	    mCloudImage = Image.load("images/cloudsblackwhite.png");

		mPlanetImage = Image.load("images/planet.png");

		mStarsImage = Image.load("images/background.png");

		mMoonImage = Image.load("images/moon.png");
	    
		mMoonRedImage = Image.load("images/moonred.png");
		
		_mFPSDemoBg = Image.load("images/fpsdemo_bg.png");
	}
	
	private void cleanTextures() {
	 	mPlanetImage.destroy();
	 	mPlanetImage = null;
	 	
	 	mCloudImage.destroy();
	 	mCloudImage = null;
	 	
	 	mStarsImage.destroy();
	 	mStarsImage = null;
	 	
	 	mMoonImage.destroy();
	 	mMoonImage = null;
	 	
	 	mMoonRedImage.destroy();
	 	mMoonRedImage = null;
	 	
	 	_mFPSDemoBg.destroy();
	 	_mFPSDemoBg = null;
	}
	
	//FIXME:
	private void input() {
		//FIXME:
		
//		while(SDL_PollEvent(&mEvent))
//		{
//			if (mEvent.type == SDL_KEYDOWN)
//			{
//				if (mEvent.key.keysym.sym == SDLK_ESCAPE)
//				{
//					mMain->setVisible(true);
//	 				mSingleplay->setVisible(false);
//	 				mMultiplay->setVisible(false);
//	 				mOptions->setVisible(false);
//				}
//			}
//			else if (mEvent.type == SDL_QUIT)
//			{
//				mRunning = false;
//			}
//			// We ignore keyboard input and just sends mouse input to Guichan
//			else if (mEvent.type == SDL_MOUSEMOTION
//							 || mEvent.type == SDL_MOUSEBUTTONDOWN
//							 || mEvent.type == SDL_MOUSEBUTTONUP)
//			{
//				mSDLInput->pushInput(mEvent);
//			}
//		}
	}
	
	//FIXME:
	private void drawSpace() {
//		int y = -200;
//		glMatrixMode(GL_PROJECTION);
//		glPushMatrix();
//		glLoadIdentity();
//		glOrtho(0.0, 800, 600, 0.0, 1, 0.0);
//
//		glEnable(GL_TEXTURE_2D);
//	 	glBindTexture(GL_TEXTURE_2D, mStarsTexture);
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
//
//		glScalef(2,1.5,1);
//		glBegin(GL_QUADS);    
//		glTexCoord2f(0.0f, 0.0f);
//		glVertex3i(0,y,0);
//		glTexCoord2f(1.0f, 0.0f);
//		glVertex3i(mStarsImage->getWidth(),y,0);
//		glTexCoord2f(1.0f, 1.0f);
//		glVertex3i(mStarsImage->getWidth(),mStarsImage->getHeight()+y,0); 
//		glTexCoord2f(0.0f, 1.0f);
//		glVertex3i(0,mStarsImage->getHeight()+y,0);
//		glEnd();
//		glDisable(GL_TEXTURE_2D);
//		
//		glPopMatrix();
//		
//		glMatrixMode(GL_MODELVIEW);
	}
	
	//FIXME:
	private void drawBackground() {
//		glEnable(GL_LIGHTING);
//		mRotation+= mDeltaTime / 2000;
//		
//		glPushMatrix();
//		glTranslatef(0.0,0.0,1.0);
//		glRotatef(80,0.0, 0.0, 1.0);
//		glRotatef(mRotation*7,0.0, -1.0, 0.0); 
//		glTranslatef(0,0.0,2.1);
//		glRotatef(mRotation*50,1.0, 0.0, 0.0); 
//		glEnable(GL_TEXTURE_2D);
//		glBindTexture(GL_TEXTURE_2D, mMoonTexture);
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
//		gluSphere(mMoon, .07, 10, 10);
//		glDisable(GL_TEXTURE_2D);
//		glPopMatrix();
//
//		glPushMatrix();
//		glTranslatef(0.0,0.0,1.0);
//		glRotatef(110,0.0, 1.0, 0.0);
//	 	glRotatef(mRotation*5,0.0, 0.0, 1.0); 
//	 	glTranslatef(2.2,0.0,0.0);
//	 	glRotatef(mRotation*30,1.0, 0.0, 0.0); 
//		glEnable(GL_TEXTURE_2D);
//		glBindTexture(GL_TEXTURE_2D, mMoonRedTexture);
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
//		gluSphere(mMoonRed, 0.15, 10, 10);
//		glDisable(GL_TEXTURE_2D);
//		glPopMatrix();
//
//		glPushMatrix();
//	 	glTranslatef(0.0,0.0,1.0);
//	 	glRotatef(mRotation*3,1.0, 0.0, 0.0); 
//		glRotatef(77,0.0, 1.0, 0.0); 
//		glEnable(GL_TEXTURE_2D);
//		glBindTexture(GL_TEXTURE_2D, mPlanetTexture);
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
//		gluSphere(mQuad1, 1.93, 60, 60);
//		glDisable(GL_TEXTURE_2D);
//		glPopMatrix();
//
//		glPushMatrix();
//		glDepthMask(GL_FALSE);
//		glEnable(GL_BLEND);
//		glBlendFunc(GL_ONE,GL_ONE);
//		glTranslatef(0.0,0.0,1.0);
//		glRotatef(mRotation*6,1.0, 0.0, 0.0);
//		glRotatef(90,0.0, 1.0, 0.0);		
//		glEnable(GL_TEXTURE_2D);
//		glBindTexture(GL_TEXTURE_2D, mCloudTexture);
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
//		gluSphere(mQuad2, 2.0, 60, 60);
//		glDisable(GL_BLEND);
//		glDepthMask(GL_TRUE);
//		glDisable(GL_TEXTURE_2D);
//		glPopMatrix();
//
//		glPushMatrix();
//		glDepthMask(GL_FALSE);
//		glEnable(GL_BLEND);
//		glBlendFunc(GL_ONE,GL_ONE);
//		glTranslatef(0.0,0.0,1.0);
//		glRotatef(mRotation*5,1.0, 0.0, 0.0);
//		glRotatef(90,0.0, 1.0, 0.0);		
//		glRotatef(90,0.0, 0.0, 1.0);
//		glEnable(GL_TEXTURE_2D);
//		glBindTexture(GL_TEXTURE_2D, mCloudTexture);
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
//		gluSphere(mQuad2, 1.98, 60, 60);
//		glDisable(GL_BLEND);
//		glDepthMask(GL_TRUE);
//		glDisable(GL_TEXTURE_2D);
//		glPopMatrix();
//		glDisable(GL_LIGHTING);
	}

	@Override
	public void onInit() throws GuichanException {
		init();
	}

	@Override
	public void preDraw(Graphics graphics) throws GuichanException {
		graphics.setColor(new Color(0, 0, 0, 255));
		graphics.fillRectangle(new Rectangle(0, 0, 
			Globals.app.getWindowWidth(), Globals.app.getWindowHeight()));//FIXME:
		if (_status == 1) {
			graphics.drawImage(_mFPSDemoBg, 0, 0);
		}
	}

	@Override
	public void onTimer() throws GuichanException {
		run();
	}

	@Override
	public void onHalt() throws GuichanException {
		halt();
	}

	@Override
	public void onKeyPressed(KeyEvent keyEvent) throws GuichanException {
//		asdfasd
		if (keyEvent.getType() == KeyInput.PRESSED &&
			keyEvent.getKey().getValue() == Key.ESCAPE) {
			mMain.setVisible(true);
			mSingleplay.setVisible(false);
			mMultiplay.setVisible(false);
			mOptions.setVisible(false);
		}
	}
}
