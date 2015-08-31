package com.iteye.weimingtom.guichan.demo.ff;

import com.iteye.weimingtom.guichan.basic.Color;
import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Key;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.event.ActionEvent;
import com.iteye.weimingtom.guichan.event.KeyEvent;
import com.iteye.weimingtom.guichan.font.ImageFont;
import com.iteye.weimingtom.guichan.gui.Globals;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.ActionListener;
import com.iteye.weimingtom.guichan.listener.ApplicationListener;
import com.iteye.weimingtom.guichan.listener.KeyListener;
import com.iteye.weimingtom.guichan.platform.Font;
import com.iteye.weimingtom.guichan.platform.Graphics;
import com.iteye.weimingtom.guichan.platform.Image;
import com.iteye.weimingtom.guichan.widget.Container;
import com.iteye.weimingtom.guichan.widget.Icon;
import com.iteye.weimingtom.guichan.widget.Label;
import com.iteye.weimingtom.guichan.widget.TextBox;

public class FFDemo implements ApplicationListener, ActionListener, KeyListener {
	//private boolean mRunning;

	//SDL_Surface* mScreen;
	//SDL_Event mEvent;
	//Mix_Chunk* mChooseSound;
	//Mix_Chunk* mEscapeSound;

	//private AWTGraphics mSDLGraphics;
	//private AWTInput mSDLInput;
	//private AWTImageLoader mSDLImageLoader;
	//private Gui mGui;

	private Container mTop;
	private FFContainer mMain;
	private FFContainer mStatus;
	private FFContainer mItems;
	private FFContainer mMagicSkills;
	private FFContainer mTime;
	private FFContainer mGoldFootsteps;
	private FFContainer mMenu;
	private FFContainer mAbout;
	private FFContainer mItemsInfo;

	private Icon mPerIcon;
	private Icon mOlofIcon;
	private Icon mTomasIcon;
	private Image mPerImage;
	private Image mOlofImage;
	private Image mTomasImage;
	private Image mSplashImage;
	private Container mSplashTop; //FIXME:added
	private Icon mSplashIcon; //FIXME:added
	private Font mFontWhite;
	private Font mFontCyan;

	private FFListBox mMenuList;

	private FFListBox mMagicSkillsList;
	private FFScrollArea mMagicSkillsScroll;

	private StringListModel mPerSkills;
	private StringListModel mPerMagic;
	private StringListModel mOlofSkills;
	private StringListModel mOlofMagic;
	private StringListModel mTomasSkills;
	private StringListModel mTomasMagic;

	private TextBox mPerInfo1;
	private TextBox mOlofInfo1;
	private TextBox mTomasInfo1;
	private TextBox mPerInfo2;
	private TextBox mOlofInfo2;
	private TextBox mTomasInfo2;
	private TextBox mItemsInfoInfo;
	private TextBox mOlofStatus1;
	private TextBox mOlofStatus2;
	private TextBox mPerStatus1;
	private TextBox mPerStatus2;
	private TextBox mTomasStatus1;
	private TextBox mTomasStatus2;

	private TextBox mGoldFootstepsInfo1;
	private TextBox mGoldFootstepsInfo2;
	private Label mTimeLabel1;
	private Label mTimeLabel2;

	private Label mNavigationLabel;

	private TextBox mAboutInfo;
	private FFScrollArea mAboutScrollArea;

	private FFListBox mItemsList;
	private FFScrollArea mItemsScrollArea;
	private StringListModel mItemsListModel;
	private StringListModel mItemsInfoListModel;
	private StringListModel mMenuListModel;

	private FFCharacterChooser mCharacterChooser;
	
	private void input() {
		/*
		while(SDL_PollEvent(&mEvent))
		{
			if (mEvent.type == SDL_KEYDOWN)
			{
				if (mEvent.key.keysym.sym == SDLK_ESCAPE)
				{  
					Mix_PlayChannel(-1, mEscapeSound, 0);
					
					action(gcn::ActionEvent(NULL, "escape"));
				}
				else if (mEvent.key.keysym.sym == SDLK_RETURN
						|| mEvent.key.keysym.sym == SDLK_UP
						|| mEvent.key.keysym.sym == SDLK_DOWN)
				{  
					Mix_PlayChannel(-1, mChooseSound, 0);
				}
				else if (mEvent.key.keysym.sym == SDLK_f)
				{
					// Works with X11 only
					SDL_WM_ToggleFullScreen(mScreen);
				}
				mSDLInput->pushInput(mEvent);			
			}
			else if (mEvent.type == SDL_KEYUP)
			{
				mSDLInput->pushInput(mEvent);
			}
			else if (mEvent.type == SDL_QUIT)
			{
				mRunning = false;
			}
		}
		*/
	}
		
	private void initMain() throws GuichanException {
		mMain = new FFContainer();
		mMain.setDimension2(new Rectangle(0, 0, 320, 240));
		mTop.add(mMain);

		mPerImage = Image.load("images/finalman.png");
		mOlofImage = Image.load("images/yakslem.png");
		mTomasImage = Image.load("images/peak.png");

		mPerIcon = new Icon(mPerImage);
		mOlofIcon = new Icon(mOlofImage);
		mTomasIcon = new Icon(mTomasImage);

		mPerInfo1 = new TextBox("\n  LV\n  HP\n  MP");
		mPerInfo1.setFont(mFontCyan);
		mPerInfo1.setOpaque(false);
		mPerInfo1.setEditable(false);
		mPerInfo1.setFocusable(false);
		//mPerInfo1.setBorderSize(0);
		mPerInfo1.setFrameSize(0);
		
		mPerInfo2 = new TextBox("FINALMAN\n     13\n       12/ 336\n       33/  40");
		mPerInfo2.setOpaque(false);
		mPerInfo2.setEditable(false);
		mPerInfo2.setFocusable(false);
		//mPerInfo2.setBorderSize(0);
		mPerInfo2.setFrameSize(0);	
		
		mOlofInfo1 = new TextBox("\n  LV\n  HP\n  MP");
		mOlofInfo1.setFont(mFontCyan);
		mOlofInfo1.setOpaque(false);
		mOlofInfo1.setEditable(false);
		mOlofInfo1.setFocusable(false);
		//mOlofInfo1.setBorderSize(0);
		mOlofInfo1.setFrameSize(0);
		
		mOlofInfo2 = new TextBox("YAKSLEM\n     41\n     1304/2932\n      298/ 300");
		mOlofInfo2.setOpaque(false);
		mOlofInfo2.setEditable(false);
		mOlofInfo2.setFocusable(false);
		//mOlofInfo2.setBorderSize(0);
		mOlofInfo2.setFrameSize(0);
		
		mTomasInfo1 = new TextBox("\n  LV\n  HP\n  MP");
		mTomasInfo1.setFont(mFontCyan);
		mTomasInfo1.setOpaque(false);
		mTomasInfo1.setEditable(false);
		mTomasInfo1.setFocusable(false);
		//mTomasInfo1.setBorderSize(0);
		mTomasInfo1.setFrameSize(0);
		
		mTomasInfo2 = new TextBox("PEAK\n      6\n      101/ 101\n        0/   0");
		mTomasInfo2.setOpaque(false);
		mTomasInfo2.setEditable(false);
		mTomasInfo2.setFocusable(false);
		//mTomasInfo2.setBorderSize(0);
		mTomasInfo2.setFrameSize(0);
		
		int offset = 6;
		mMain.add(mPerIcon, 10, offset);
		mMain.add(mPerInfo2, 60, offset);
		mMain.add(mPerInfo1, 60, offset);
		offset += 76;
		mMain.add(mOlofIcon, 10, offset);
		mMain.add(mOlofInfo2, 60, offset);
		mMain.add(mOlofInfo1, 60, offset);
		offset += 76;
		mMain.add(mTomasIcon, 10, offset);
		mMain.add(mTomasInfo2, 60, offset);
		mMain.add(mTomasInfo1, 60, offset);	
		
		mCharacterChooser = new FFCharacterChooser();	
		mCharacterChooser.setActionEventId("character");
		mCharacterChooser.addActionListener(this);

		mMain.add(mCharacterChooser, 5, 25);

		mNavigationLabel = new Label("STATUS ");
		mNavigationLabel.setVisible(false);
		mMain.add(mNavigationLabel, 230, 20);		
	}
	
	private void cleanMain() throws GuichanException {
		mNavigationLabel.destroy();
		mNavigationLabel = null;
		mCharacterChooser.destroy();
		mCharacterChooser = null;
		
		mPerInfo1.destroy();
		mPerInfo1 = null;
		mOlofInfo1.destroy();
		mOlofInfo1 = null;
		mTomasInfo1.destroy();
		mTomasInfo1 = null;
		
		mPerInfo2.destroy();
		mPerInfo2 = null;
		mOlofInfo2.destroy();
		mOlofInfo2 = null;
		mTomasInfo2.destroy();
		mTomasInfo2 = null;
		
		mPerIcon.destroy();
		mPerIcon = null;
		mOlofIcon.destroy();
		mOlofIcon = null;
		mTomasIcon.destroy();
		mTomasIcon = null;
		
		mPerImage.destroy();
		mPerImage = null;
		mOlofImage.destroy();
		mOlofImage = null;
		mTomasImage.destroy();
		mTomasImage = null;
	}
	
	private void initStatus() throws GuichanException {
		mStatus = new FFContainer();
		mStatus.setDimension2(new Rectangle(0, 80, 320, 160));
		mStatus.setVisible(false);
		mTop.add(mStatus);

		mPerStatus1 = new TextBox("  STR           EXP\n" +
                                   "  INT           NEXT\n" +
                                   "  DEF\n" +
                                   "  MAGDEF\n");
		mPerStatus1.setFont(mFontCyan);
		mPerStatus1.setOpaque(false);
		mPerStatus1.setEditable(false);
		mPerStatus1.setFocusable(false);
		mPerStatus1.setVisible(false);
		//mPerStatus1.setBorderSize(0);
		mPerStatus1.setFrameSize(0);
		
		mPerStatus2 = new TextBox("         32          12382\n" +
                                   "         56          13872\n" +
                                   "         12\n" +
                                   "         11\n\n" +
                                   " FINALMAN is immune against\n" +
                                   " poisinous attacks, thanks to his\n" +
                                   " face mask.");
		mPerStatus2.setOpaque(false);
		mPerStatus2.setEditable(false);
		mPerStatus2.setFocusable(false);
		mPerStatus2.setVisible(false);	
		//mPerStatus2.setBorderSize(0);
		mPerStatus2.setFrameSize(0);
		
		mOlofStatus1 = new TextBox("  STR           EXP\n" + 
                                    "  INT           NEXT\n" +
                                    "  DEF\n" +
                                    "  MAGDEF\n");
		mOlofStatus1.setFont(mFontCyan);
		mOlofStatus1.setOpaque(false);
		mOlofStatus1.setEditable(false);
		mOlofStatus1.setFocusable(false);
		mOlofStatus1.setVisible(false);
		//mOlofStatus1.setBorderSize(0);
		mOlofStatus1.setFrameSize(0);
		
		mOlofStatus2 = new TextBox("          2          412382\n" +
                                    "         72          513872\n" +
                                    "          4\n" +
                                    "         34\n\n" +
                                    " YAKSLEM has one passion in life,\n" +
                                    " to annoy other people...\n" +
                                    " especially FINALMAN.");
		mOlofStatus2.setOpaque(false);
		mOlofStatus2.setEditable(false);
		mOlofStatus2.setFocusable(false);
		mOlofStatus2.setVisible(false);	
		//mOlofStatus2.setBorderSize(0);
		mOlofStatus2.setFrameSize(0);
		
		mTomasStatus1 = new TextBox("  STR           EXP\n" + 
                                     "  INT           NEXT\n" +
                                     "  DEF\n" +
                                     "  MAGDEF\n");
		mTomasStatus1.setFont(mFontCyan);
		mTomasStatus1.setOpaque(false);
		mTomasStatus1.setEditable(false);
		mTomasStatus1.setFocusable(false);
		mTomasStatus1.setVisible(false);
		//mTomasStatus1.setBorderSize(0);
		mTomasStatus1.setFrameSize(0);
		
		mTomasStatus2 = new TextBox("          1          412382\n" + 
                                     "          3          513872\n" +
                                     "          9\n" +
                                     "         24\n\n" +
                                     " PEAK is very weak but so cute!\n" +
                                     " He has a tendency of answering\n" +
                                     " any question with \"KUPO!\"");
		mTomasStatus2.setOpaque(false);
		mTomasStatus2.setEditable(false);
		mTomasStatus2.setFocusable(false);
		mTomasStatus2.setVisible(false);	
		//mTomasStatus2.setBorderSize(0);
		mTomasStatus2.setFrameSize(0);
		
		mStatus.add(mPerStatus2, 5, 10);
		mStatus.add(mPerStatus1, 5, 10);
		mStatus.add(mOlofStatus2, 5, 10);
		mStatus.add(mOlofStatus1, 5, 10);
		mStatus.add(mTomasStatus2, 5, 10);
		mStatus.add(mTomasStatus1, 5, 10);
	}
	
	private void cleanStatus() throws GuichanException {
		mStatus.destroy();
		mStatus = null;
		mPerStatus1.destroy();
		mPerStatus1 = null;
		mPerStatus2.destroy();
		mPerStatus2 = null;
		mOlofStatus1.destroy();
		mOlofStatus1 = null;
		mOlofStatus2.destroy();
		mOlofStatus2 = null;
		mTomasStatus1.destroy();
		mTomasStatus1 = null;
		mTomasStatus2.destroy();
		mTomasStatus2 = null;
	}
		
	private void initMagicSkills() throws GuichanException {
		mMagicSkills = new FFContainer();
		mMagicSkills.setDimension2(new Rectangle(0, 80, 320, 160));
		mMagicSkills.setVisible(false);
		
		mMagicSkillsScroll = new FFScrollArea();
		mMagicSkillsScroll.setDimension(new Rectangle(5, 5, 310, 150));

		mMagicSkillsList = new FFListBox();
		mMagicSkillsList.setWidth(300);
		mMagicSkillsScroll.setContent(mMagicSkillsList);
		mMagicSkills.add(mMagicSkillsScroll);
		mTop.add(mMagicSkills);

		mPerSkills = new StringListModel();
		mPerMagic = new StringListModel();
		mOlofSkills = new StringListModel();
		mOlofMagic = new StringListModel();
		mTomasSkills = new StringListModel();
		mTomasMagic = new StringListModel();	

		mPerSkills.add("Use");
		mPerSkills.add("Steal");
		mPerSkills.add("Disassemble");	
		mPerSkills.add("Tech-Talk");
		mPerSkills.add("Double Compile");

		mPerMagic.add("Fire");
		mPerMagic.add("Fire 2");
	 	mPerMagic.add("Bio");
		mPerMagic.add("Magic Missile");

		mOlofSkills.add("Annoy");
		mOlofSkills.add("Juggle");
		mOlofSkills.add("Somersault");
		mOlofSkills.add("Evil Laughter");
		mOlofSkills.add("Meta-circular Evaluation");
		mOlofSkills.add("Lisp");
		mOlofSkills.add("Cursing PHP");	
		mOlofSkills.add("Paint");
		mOlofSkills.add("Compose obscure music");
		
		mOlofMagic.add("Ultima");
		mOlofMagic.add("Sonic Blast");	

		mTomasSkills.add("Precision Throw");
		mTomasSkills.add("Jump");
		mTomasSkills.add("Dance");	
		mTomasSkills.add("Much talk and little factory");
		mTomasSkills.add("Cheat");
		mTomasSkills.add("Wear hotpants");
		mTomasSkills.add("Programming Pong games");
		mTomasSkills.add("Eat meat pie");
			
		mTomasMagic.add("Slow");
		mTomasMagic.add("Sleep");
		mTomasMagic.add("Doom");		
	}
	
	private void cleanMagicSkills() throws GuichanException {
		mMagicSkills.destroy();
		mMagicSkillsList.destroy();
		mMagicSkillsScroll.destroy();
		mPerSkills.destroy();
		mPerMagic.destroy();
		mOlofSkills.destroy();
		mOlofMagic.destroy();
		mTomasSkills.destroy();
		mTomasMagic.destroy();
	}
	
	private void initItems() throws GuichanException {
		mItems = new FFContainer();

		mItemsListModel = new StringListModel();
		mItemsInfoListModel = new StringListModel();
		mItemsListModel.add("23 x Potion");
		mItemsInfoListModel.add("Restores 100 HP");
		mItemsListModel.add("12 x Ether");
		mItemsInfoListModel.add("Restores 50 MP");
		mItemsListModel.add(" 8 x Elixir");
		mItemsInfoListModel.add("Restores all HP/MP");
		mItemsListModel.add("16 x Fenix Up");
		mItemsInfoListModel.add("Kills a party member");
		mItemsListModel.add(" 1 x Brass Key");
		mItemsInfoListModel.add("No idea...");
		mItemsListModel.add(" 1 x Atma Weapon");		
		mItemsInfoListModel.add("Grows with it's user");
		mItemsListModel.add(" 1 x Converse Allstars");
		mItemsInfoListModel.add("Yakslems red shoes");
		mItemsListModel.add(" 1 x Oil Canister");
		mItemsInfoListModel.add("Get greasy!");
		mItemsListModel.add(" 1 x Geeky t-shirt");
		mItemsInfoListModel.add("Belongs to finalman");
		mItemsListModel.add(" 1 x Synthesizer");
		mItemsInfoListModel.add("Yakslems mega cool Ensoniq EPS 16+");
		mItemsListModel.add(" 1 x Graphic Pen");
		mItemsInfoListModel.add("Someone left it here. Maybe\nNodajo?");
		mItemsListModel.add(" 1 x Floppy Disk");
		mItemsInfoListModel.add("Stores your important data");
		mItemsListModel.add(" 1 x Gui-chan Plush Doll");
		mItemsInfoListModel.add("Soooo cute and soooo plushy!!!");
		mItemsListModel.add(" 1 x Fenix Blade");
		mItemsInfoListModel.add("We are waiting for Demo3");
		mItemsListModel.add(" 2 x Joy Division LP");
		mItemsInfoListModel.add("Unknown Pleasures and Closer");
		
		mItemsInfo = new FFContainer();
		mItemsInfo.setDimension2(new Rectangle(0, 0, 320, 50));
		mItemsInfo.setVisible(false);
		
		mItemsInfoInfo = new TextBox();
		mItemsInfoInfo.setOpaque(false);
		mItemsInfoInfo.setEditable(false);
		mItemsInfoInfo.setFocusable(false);
		mItemsInfoInfo.setDimension(new Rectangle(5, 5, 310, 40));
	    //mItemsInfoInfo.setBorderSize(0);
		mItemsInfoInfo.setFrameSize(0);
		mItemsInfo.add(mItemsInfoInfo);
		
		mItemsList = new FFListBox();
		mItemsList.setActionEventId("items");	
		mItemsList.addKeyListener(this);
		mItemsList.setWidth(300);
		mItemsList.setListModel(mItemsListModel);
		mItemsScrollArea = new FFScrollArea();
		mItemsScrollArea.setContent(mItemsList);
		mItemsScrollArea.setDimension(new Rectangle(5, 5, 310, 180));
		mItems = new FFContainer();
		mItems.setDimension2(new Rectangle(0, 50, 320, 190));
		mItems.setVisible(false);
		mItems.add(mItemsScrollArea);
		mTop.add(mItems);	
		mTop.add(mItemsInfo);		
	}
		
	private void cleanItems() throws GuichanException {
		mItems.destroy();
		mItems = null;
		mItemsInfo.destroy();
		mItemsInfo = null;
		mItemsInfoInfo.destroy();
		mItemsInfoInfo = null;
		mItemsList.destroy();
		mItemsList = null;
		mItemsScrollArea.destroy();
		mItemsScrollArea = null;
	}
	
	private void initAbout() throws GuichanException {
		mAbout = new FFContainer();
		mAbout.setDimension2(new Rectangle(0, 0, 320, 240));
		mAbout.setVisible(false);
		mTop.add(mAbout);
	 
		mAboutInfo = new TextBox();
		mAboutInfo.setOpaque(false);
		mAboutInfo.setEditable(false);
		mAboutInfo.setFocusable(false);
		mAboutInfo.setText("Welcome to Guichan FF Demo!\n\n" + 
							"What is this, you wonder?\n" + 
							"Well, this is a little proof of\n" + 
							"concept (proof of l33tness) demo\n" + 
							"for the Guichan GUI library.\n" + 
							"It demonstrates the\n" + 
							"flexibility of the library,\n" + 
							"how to overload widgets to get a\n" + 
							"custom look and feel.\n\n" + 
							"Guichan is a GUI library\n" + 
							"especially made with games in\n" + 
							"mind. It has a modular, object\n" + 
							"oriented API. The back-end is\n" + 
							"replaceable, so it can work\n" + 
							"on any platform. It is bundled\n" + 
							"with graphics back-ends for\n" + 
							"SDL, OpenGL and Allegro, and\n" + 
							"input-backends for SDL and\n" + 
							"Allegro. And don't be fooled\n" + 
							"by this demo, it does support\n" + 
							"mouse input!\n\n" + 
							"Read more about Guichan on:\n" + 
							"http://guichan.darkbits.org/\n\n\n" + 
							"Guichan developed by:\n" + 
							" - Per Larsson (finalman)\n" + 
							" - Olof Naessen (yakslem)\n\n" + 
							"Demo developed by:\n" + 
							" - Per Larsson (finalman)\n" + 
							"       code\n\n" + 
							" - Olof Naessen (yakslem)\n" + 
							"       code, character art\n\n" + 
							" - Tomas Almgren (peak)\n" + 
							"       font\n\n" + 
							" - Henrik Vahlgren (haiko)\n" + 
							"       Darkbits logo\n"
							);
		
		mAboutScrollArea = new FFScrollArea();
		mAboutScrollArea._useCustomKeyboard = true; //FIXME: added
		mAboutScrollArea.setContent(mAboutInfo);
		mAboutScrollArea.setFocusable(true);
		mAboutScrollArea.setDimension(new Rectangle(5, 5, 310, 230));
		//mAboutScrollArea.setBorderSize(0);
		mAboutScrollArea.setFrameSize(0);
		mAbout.add(mAboutScrollArea);
		//mAbout.setBorderSize(0);
		mAbout.setFrameSize(0);
	}
	
	private void cleanAbout() throws GuichanException {
		mAboutInfo.destroy();
		mAboutInfo = null;
		mAboutScrollArea.destroy();
		mAboutScrollArea = null;
		mAbout.destroy();
		mAbout = null;		
	}
		
	@Override
	public void onInit() throws GuichanException {
		_startTicks = System.currentTimeMillis();
		//init();
		initSplash();
	}

	@Override
	public void preDraw(Graphics graphics) throws GuichanException {
//		graphics.setColor(new Color(0, 0, 0, 255));
//		graphics.fillRectangle(new Rectangle(0, 0, 
//			Globals.app.getWindowWidth(), Globals.app.getWindowHeight()));//FIXME:
//		if (_status == 0) {
//			graphics.drawImage(mSplashImage, 0, 0);
//		}		
	}

	@Override
	public void onKeyPressed(KeyEvent keyEvent) throws GuichanException {
		int key = keyEvent.getKey().getValue();
		if (key == Key.ESCAPE) {
			//Mix_PlayChannel(-1, mEscapeSound, 0);
			
			action(new ActionEvent(null, "escape"));
		} else if (key == Key.ENTER || key == Key.UP || key == Key.DOWN) {
			//Mix_PlayChannel(-1, mChooseSound, 0);
		} else if (key == 'F') {
			//SDL_WM_ToggleFullScreen(mScreen);
		}
	}

	private void initSplash() throws GuichanException {
		mSplashTop = new Container();
		mSplashTop.setBaseColor(new Color(0x000000));
		mSplashTop.setDimension(new Rectangle(0, 0, 
			Globals.app.getWindowWidth(), 
			Globals.app.getWindowHeight()));		
		
		mSplashImage = Image.load("images/splash.png");	
		mSplashIcon = new Icon(mSplashImage);
		if (false) {
	    	mSplashIcon.setPosition(
					Globals.app.getWindowWidth() / 2 -mSplashImage.getWidth() / 2,
					Globals.app.getWindowHeight() / 2 -mSplashImage.getHeight() / 2);	
		} else {
			mSplashIcon.setPosition(10, 50);
		}
    	mSplashTop.add(mSplashIcon);
		
		Globals.gui.setTop(mSplashTop);
	}
	
	//FIXME:
	private long _startTicks = System.currentTimeMillis();
	private int _status = -1;	
	@Override
	public void onTimer() throws GuichanException {
		long curLastTicks = System.currentTimeMillis();
		long deltaTicks = curLastTicks - _startTicks;
		if (deltaTicks < 3000) {
			_status = 0;
		} else {
			if (_status == 0) {
				init();
			}
			_status = 1;
			
			int sec = (int)(deltaTicks / 1000);
			int min = sec / 60;
			sec = sec % 60;
			StringBuffer os = new StringBuffer();

			if (min < 10) {
				os.append(" ").append(Integer.toString(min)).append(":");
			} else {
				os.append(Integer.toString(min)).append(":");
			}

			if (sec < 10) {
				os.append("0").append(Integer.toString(sec));
			} else {
				os.append(Integer.toString(sec));
			}

			if (mTimeLabel2 != null) {
				mTimeLabel2.setCaption(os.toString());
				mTimeLabel2.adjustSize();
			}
		}
	}

	@Override
	public void onHalt() throws GuichanException {
		destroy();
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) throws GuichanException {
		mItemsInfoInfo.setText(
			mItemsInfoListModel.getElementAt(
				mItemsList.getSelected()));		
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) throws GuichanException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void action(ActionEvent actionEvent) throws GuichanException {
		if ("menu".equals(actionEvent.getId())) {
			switch (mMenuList.getSelected()) {
			case 0:
				mItems.setVisible(true);
				mItemsList.setSelected(0);
				mItemsList.requestFocus();
				mItemsInfo.setVisible(true);
				mItemsInfoInfo.setText(
					mItemsInfoListModel.getElementAt(
						mItemsList.getSelected()));
				break;

			case 1:
			case 2:
			case 3:
				mCharacterChooser.setSelected(0);
				mCharacterChooser.requestFocus();
				break;
				
			case 4:
				mAbout.setVisible(true);
				mAboutScrollArea.setVerticalScrollAmount(0);
				mAboutScrollArea.requestFocus();
				break;
				
			case 6:
				//mRunning = false;
	        	System.out.println("FFDemo exit");
				if (Globals.app != null) {
	        		Globals.app.dispose();
	        	}
				break;
				
			default:
				break;
			}
		}

		if ("escape".equals(actionEvent.getId())) {
			mAbout.setVisible(false);
			mItems.setVisible(false);
			mItemsInfo.setVisible(false);
			mMenu.setVisible(true);
			mTime.setVisible(true);
			mGoldFootsteps.setVisible(true);
			mMenuList.requestFocus();
			mMain.slideContentTo(0);
			mStatus.setVisible(false);
			mPerStatus1.setVisible(false);
			mPerStatus2.setVisible(false);
			mOlofStatus1.setVisible(false);
			mOlofStatus2.setVisible(false);
			mTomasStatus1.setVisible(false);
			mTomasStatus2.setVisible(false);
			mMagicSkills.setVisible(false);
			mNavigationLabel.setVisible(false);
		}

		if ("character".equals(actionEvent.getId())) {
			mMain.slideContentTo(-76 * mCharacterChooser.getSelected());
			mMenu.setVisible(false);
			mTime.setVisible(false);
			mGoldFootsteps.setVisible(false);

			Globals.gui.focusNone();

			mNavigationLabel.setVisible(true);
			mNavigationLabel.setY(
				mCharacterChooser.getSelected() * 76 + 30);
				
			switch(mMenuList.getSelected()) {			
			case 1:
				mNavigationLabel.setCaption("STATUS");
											
				if (mCharacterChooser.getSelected() == 0) {
					mPerStatus1.setVisible(true);
					mPerStatus2.setVisible(true);					
				} else if (mCharacterChooser.getSelected() == 1) {
					mOlofStatus1.setVisible(true);
					mOlofStatus2.setVisible(true);
				} else if (mCharacterChooser.getSelected() == 2) {
					mTomasStatus1.setVisible(true);
					mTomasStatus2.setVisible(true);
				}
				mStatus.setVisible(true);
				break;

			case 2:
				mNavigationLabel.setCaption("SKILLS");
											
				if (mCharacterChooser.getSelected() == 0) {
					mMagicSkillsList.setListModel(mPerSkills);
				} else if (mCharacterChooser.getSelected() == 1) {
					mMagicSkillsList.setListModel(mOlofSkills);
				} else if (mCharacterChooser.getSelected() == 2) {
					mMagicSkillsList.setListModel(mTomasSkills);
				}
				mMagicSkillsList.setSelected(0);
				mMagicSkills.setVisible(true);
				mMagicSkillsList.requestFocus();
				break;

			case 3:
				mNavigationLabel.setCaption("MAGIC");			
				
				if (mCharacterChooser.getSelected() == 0) {
					mMagicSkillsList.setListModel(mPerMagic);
				} else if (mCharacterChooser.getSelected() == 1) {
					mMagicSkillsList.setListModel(mOlofMagic);
				} else if (mCharacterChooser.getSelected() == 2) {
					mMagicSkillsList.setListModel(mTomasMagic);
				}
				mMagicSkillsList.setSelected(0);
				mMagicSkills.setVisible(true);
				mMagicSkillsList.requestFocus();
				break;
			}
		}
	}

	public void run() {
		/*
		while(mRunning)
		{		
			input();

			int sec = SDL_GetTicks() / 1000;
			int min = sec / 60;
			sec = sec % 60;
			std::string str;
			std::ostringstream os(str);

			if (min < 10)
			{
				os << " " << min << ":";
			}
			else
			{
				os << min << ":";			
			}

			if (sec < 10)
			{
				os << "0" << sec;
			}
			else
			{
				os << sec;
			}

			mTimeLabel2->setCaption(os.str());
			mTimeLabel2->adjustSize();
			
			if (SDL_GetTicks() < 3000)
	        {
				SDL_Rect src, dst;
				src.x = src.y = 0;
				src.w = dst.w = mSplashImage->getWidth();
				src.h = dst.h = mSplashImage->getHeight();
				dst.x = 10;
				dst.y = 50;
				gcn::SDLImage* image = (gcn::SDLImage*) mSplashImage;
				SDL_BlitSurface(image->getSurface(), &src, mScreen, &dst);
		}
			else		
			{
				mGui->logic();
				mGui->draw();
			}

			SDL_Flip(mScreen);
			SDL_Delay(10);
		}
		*/
	}
	
	public void init() throws GuichanException {
		//mRunning = true;
		//SDL_Init(SDL_INIT_VIDEO | SDL_INIT_AUDIO);
		//mScreen = SDL_SetVideoMode(320, 240, 32, SDL_HWSURFACE | SDL_DOUBLEBUF | SDL_HWACCEL | SDL_FULLSCREEN);
		//SDL_EnableUNICODE(1);
		//SDL_EnableKeyRepeat(SDL_DEFAULT_REPEAT_DELAY, SDL_DEFAULT_REPEAT_INTERVAL);
		//SDL_ShowCursor(0);
		//SDL_WM_SetCaption("Gui-chan FF demo", NULL);
		//Mix_OpenAudio(22050, MIX_DEFAULT_FORMAT, 2, 1024);

		//mChooseSound = Mix_LoadWAV("sound/sound1.wav");
		//mEscapeSound = Mix_LoadWAV("sound/sound2.wav");

		//mSDLImageLoader = new gcn::SDLImageLoader();
		//gcn::Image::setImageLoader(mSDLImageLoader); 
		//mSDLGraphics = new gcn::SDLGraphics();
		//mSDLGraphics->setTarget(mScreen);
		//mSDLInput = new gcn::SDLInput();
		
		if (mSplashImage == null) {
			mSplashImage = Image.load("images/splash.png");	
		}

		mTop = new Container();
		mTop.setBaseColor(new Color(0x000000));
		mTop.setDimension(new Rectangle(0, 0, 320, 240));
//		mGui = new Gui();
		Globals.gui.setTabbingEnabled(false);
//		mGui.setGraphics(mSDLGraphics);
//		mGui.setInput(mSDLInput);
		Globals.gui.setTop(mTop);
		mFontWhite = new ImageFont("fonts/rpgfont.png", " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,!?-+/():;%&`'*#=[]\""); 	
		mFontCyan = new ImageFont("fonts/rpgfont2.png", " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,!?-+/():;%&`'*#=[]\"");	
		Widget.setGlobalFont(mFontWhite);
		
		initMain();

		mMenu = new FFContainer();
		mMenu.setDimension2(new Rectangle(230, 0, 90, 130));
		mMenu.setOpaque(false);
		mMenu.setBaseColor(new Color(0xff0000));
		mTop.add(mMenu);

		mGoldFootsteps = new FFContainer();
		mGoldFootsteps.setDimension2(new Rectangle(210, 170, 110, 70));
		mGoldFootsteps.setOpaque(false);
		//mGoldFootsteps.setBorderSize(0);
		mGoldFootsteps.setFrameSize(0);
		mTop.add(mGoldFootsteps);

		mTime = new FFContainer();
		mTime.setDimension2(new Rectangle(230, 130, 90, 40));
		mTime.setOpaque(false);
		mTop.add(mTime);
																		 
		mGoldFootstepsInfo1 = new TextBox("Steps\n\nGP");
		mGoldFootstepsInfo1.setFont(mFontCyan);
		mGoldFootstepsInfo1.setOpaque(false);
		mGoldFootstepsInfo1.setEditable(false);
		mGoldFootstepsInfo1.setFocusable(false);
		//mGoldFootstepsInfo1.setBorderSize(0);
		mGoldFootstepsInfo1.setFrameSize(0);
		
		mGoldFootstepsInfo2 = new TextBox("\n    9119092\n\n    1009213");
		mGoldFootstepsInfo2.setOpaque(false);
		mGoldFootstepsInfo2.setEditable(false);
		mGoldFootstepsInfo2.setFocusable(false);
		//mGoldFootstepsInfo2.setBorderSize(0);
		mGoldFootstepsInfo2.setFrameSize(0);
		
		mTimeLabel1 = new Label("Time");
		mTimeLabel1.setFont(mFontCyan);
		mTimeLabel2 = new Label();
		mTime.add(mTimeLabel1, 5, 5);	
		mTime.add(mTimeLabel2, 22, 20);

		
		mGoldFootsteps.add(mGoldFootstepsInfo2, 5, 0);
		mGoldFootsteps.add(mGoldFootstepsInfo1, 5, 5);
		
		mMenuListModel = new StringListModel();
		mMenuListModel.add("Items");
		mMenuListModel.add("Status");
		mMenuListModel.add("Skills");
		mMenuListModel.add("Magic");
		mMenuListModel.add("About");
		mMenuListModel.add("");
		mMenuListModel.add("Quit");
		
		mMenuList = new FFListBox();
		mMenuList.setActionEventId("menu");
		mMenuList.addActionListener(this);	
		mMenuList.setListModel(mMenuListModel);
		mMenu.add(mMenuList, 5, 5);
		mMenuList.setSelected(0);
		mMenuList.requestFocus();
		
		initStatus();
		initAbout();
		initItems();
		initMagicSkills();
	}
	
	public void destroy() throws GuichanException {
		cleanStatus();
		cleanAbout();
		cleanItems();
		cleanMagicSkills();	
		cleanMain();
		
		mSplashImage.destroy();
		mSplashImage = null;

		//FIXME: added
		mSplashIcon.destroy();
		mSplashIcon = null;
		mSplashTop.destroy();
		mSplashTop = null;

		mTimeLabel1.destroy();
		mTimeLabel1 = null;
		mTimeLabel2.destroy(); 
		mTimeLabel2 = null;
		mTime.destroy();
		mTime = null;
		
		mGoldFootstepsInfo1.destroy();
		mGoldFootstepsInfo1 = null;
		mGoldFootstepsInfo2.destroy();
		mGoldFootstepsInfo2 = null;
		mGoldFootsteps.destroy();
		mGoldFootsteps = null;
		
		mMenuList.destroy();
		mMenuList = null;
		mMenuListModel.destroy();
		mMenuListModel = null;
		mMenu.destroy();
		mMenu = null;
		
		mMain.destroy();
		mMain = null;
		
		mFontWhite.destroy();
		mFontWhite = null;
		mFontCyan.destroy();
		mFontCyan = null;
		mTop.destroy();
		mTop = null;
		Globals.gui.destroy();
		Globals.gui = null;
		
		//delete mSDLInput.destroy();
		//delete mSDLGraphics.destroy();
		//delete mSDLImageLoader.destroy();
		 
		//Mix_FreeChunk(mChooseSound);
		//Mix_FreeChunk(mEscapeSound);
		//Mix_CloseAudio();
		
		//SDL_Quit();
	}
}
