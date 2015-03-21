package com.iteye.weimingtom.guichan.widget;

import java.util.List;
import java.util.Vector;

import com.iteye.weimingtom.guichan.basic.GuichanException;
import com.iteye.weimingtom.guichan.basic.Key;
import com.iteye.weimingtom.guichan.basic.Rectangle;
import com.iteye.weimingtom.guichan.event.KeyEvent;
import com.iteye.weimingtom.guichan.event.MouseEvent;
import com.iteye.weimingtom.guichan.gui.Widget;
import com.iteye.weimingtom.guichan.listener.KeyListener;
import com.iteye.weimingtom.guichan.listener.MouseListener;
import com.iteye.weimingtom.guichan.platform.Graphics;

public class TextBox extends Widget implements MouseListener, KeyListener {
    protected List<String> mTextRows = new Vector<String>();
    protected int mCaretColumn;
    protected int mCaretRow;
    protected boolean mEditable;
    protected boolean mOpaque;
    
    protected void drawCaret(Graphics graphics, int x, int y) {
        graphics.setColor(getForegroundColor());
    	graphics.drawLine(x, getFont().getHeight() + y, x, y);
    }

    protected void adjustSize() throws GuichanException {
        int i;
        int width = 0;
        for (i = 0; i < mTextRows.size(); ++i) {
            int w = getFont().getWidth(mTextRows.get(i));
            if (width < w) {
                width = w;
            }
        }
        
        setWidth(width + 1);
        setHeight(getFont().getHeight() * mTextRows.size());
    }
    
    public TextBox() throws GuichanException {
        mCaretColumn = 0;
        mCaretRow = 0;
        mEditable = true;
        mOpaque = true;

        setText("");

        setFocusable(true);

        addMouseListener(this);
        addKeyListener(this);
        adjustSize();
    }
    
    public TextBox(String text) throws GuichanException {
        mCaretColumn = 0;
        mCaretRow = 0;
        mEditable = true;
        mOpaque = true;

        setText(text);

        setFocusable(true);

        addMouseListener(this);
        addKeyListener(this);
        adjustSize();
    }
    
	@Override
	public void keyPressed(KeyEvent keyEvent) throws GuichanException {
        Key key = keyEvent.getKey();

        if (key.getValue() == Key.LEFT) {
            --mCaretColumn;
            if (mCaretColumn < 0) {
                --mCaretRow;

                if (mCaretRow < 0) {
                    mCaretRow = 0;
                    mCaretColumn = 0;
                } else {
                	String text = "";
                	if (mCaretRow >= 0 && mCaretRow < mTextRows.size()) {
                		text = mTextRows.get(mCaretRow);
                	}
                	if (text != null) {
                    	mCaretColumn = text.length();
                	} else {
                		mCaretColumn = 0;
                	}
                }
            }
        } else if (key.getValue() == Key.RIGHT) {
            ++mCaretColumn;
            
            int len = 0;
        	String text = "";
        	if (mCaretRow >= 0 && mCaretRow < mTextRows.size()) {
        		text = mTextRows.get(mCaretRow);
        	}
        	if (text != null) {
            	len = text.length();
        	} else {
        		len = 0;
        	}
            
            if (mCaretColumn > len) {
                ++mCaretRow;

                if (mCaretRow >= (int)mTextRows.size()) {
                    mCaretRow = mTextRows.size() - 1;
                    if (mCaretRow < 0) {
                        mCaretRow = 0;
                    }
                    
                    len = 0;
                	text = "";
                	if (mCaretRow >= 0 && mCaretRow < mTextRows.size()) {
                		text = mTextRows.get(mCaretRow);
                	}
                	if (text != null) {
                    	len = text.length();
                	} else {
                		len = 0;
                	}
                    
                    mCaretColumn = len;
                } else {
                    mCaretColumn = 0;
                }
            }
        } else if (key.getValue() == Key.DOWN) {
            setCaretRow(mCaretRow + 1);
        } else if (key.getValue() == Key.UP) {
            setCaretRow(mCaretRow - 1);
        } else if (key.getValue() == Key.HOME) {
            mCaretColumn = 0;
        } else if (key.getValue() == Key.END) {
            int len = 0;
        	String text = "";
        	if (mCaretRow >= 0 && mCaretRow < mTextRows.size()) {
        		text = mTextRows.get(mCaretRow);
        	}
        	if (text != null) {
            	len = text.length();
        	} else {
        		len = 0;
        	}
            mCaretColumn = len;
        } else if (key.getValue() == Key.ENTER 
        		&& mEditable) {
        	if (mCaretRow >= 0 && mCaretRow < mTextRows.size()) {
            	String text = "";
            	String textLeft = "";
            	String textRight = "";
            	if (mCaretRow >= 0 && mCaretRow < mTextRows.size()) {
            		text = mTextRows.get(mCaretRow);
            	}
            	if (mCaretColumn < text.length()) {
            		textLeft = text.substring(0, mCaretColumn);
            		textRight = text.substring(mCaretColumn);
            	} else {
            		textLeft = text;
            		textRight = "";
            	}
            	mTextRows.add(mCaretRow + 1, textRight);
        		mTextRows.set(mCaretRow, textLeft);
        		++mCaretRow;
        		mCaretColumn = 0;
        	}
        } else if (key.getValue() == Key.BACKSPACE
                 && mEditable) {
        	if (mCaretRow >= 0 && mCaretRow < mTextRows.size()) {
        		//FIXME:
                int len = 0;
            	String text = mTextRows.get(mCaretRow);
            	if (text != null) {
                	len = text.length();
            	} else {
            		len = 0;
            		text = "";
            	}
        		//FIXME: move condition to here
        		//&& mCaretColumn != 0
            	if (mCaretColumn - 1 < len && mCaretColumn > 0) {
	            	String textLeft = "";
	            	String textRight = "";
	            	if (mCaretColumn - 1 < text.length()) {
	            		textLeft = text.substring(0, mCaretColumn - 1);
	            		textRight = text.substring(mCaretColumn);
	            	} else {
	            		textLeft = text;
	            		textRight = "";
	            	}
	        		mTextRows.set(mCaretRow, textLeft + textRight);
	        		--mCaretColumn;
            	} else if (mCaretColumn <= 0 && mCaretRow > 0) {
            		//FIXME: move condition below to here
            		//&& mCaretColumn == 0 && mCaretRow != 0
                    mCaretColumn = mTextRows.get(mCaretRow - 1).length();
                    mTextRows.set(mCaretRow - 1, mTextRows.get(mCaretRow - 1) + mTextRows.get(mCaretRow));
                    mTextRows.remove(mCaretRow);
                    --mCaretRow;
            	}
        	}
        } else if (key.getValue() == Key.DELETE
                 && mEditable) {
        	if (mCaretRow >= 0 && mCaretRow < mTextRows.size()) {
        		//FIXME:
                int len = 0;
            	String text = mTextRows.get(mCaretRow);
            	if (text != null) {
                	len = text.length();
            	} else {
            		len = 0;
            		text = "";
            	}
        		//FIXME: move condition to here
        		//&& mCaretColumn < (int)mTextRows.get(mCaretRow).length()            	
            	if (mCaretColumn < len) {
	            	String textLeft = "";
	            	String textRight = "";
	            	if (mCaretColumn < text.length()) {
	            		textLeft = text.substring(0, mCaretColumn);
	            		textRight = text.substring(mCaretColumn + 1);
	            	} else {
	            		textLeft = text;
	            		textRight = "";
	            	}
	        		mTextRows.set(mCaretRow, textLeft + textRight);
            	} else if (mCaretColumn == len && mCaretRow < mTextRows.size() - 1) {
            		//FIXME: move condition below to here
            		//&& mCaretColumn == (int)mTextRows.get(mCaretRow).length()
                    mTextRows.set(mCaretRow, mTextRows.get(mCaretRow) + mTextRows.get(mCaretRow + 1));
                    mTextRows.remove(mCaretRow + 1);
            	}
        	}
        } else if (key.getValue() == Key.PAGE_UP) {
            Widget par = getParent();
            if (par != null) {
                int rowsPerPage = 0;
                if (getFont() != null && getFont().getHeight() != 0) {
                	rowsPerPage = par.getChildrenArea().height / getFont().getHeight();
                }
                mCaretRow -= rowsPerPage;
                if (mCaretRow < 0) {
                    mCaretRow = 0;
                }
                if (mCaretRow >= mTextRows.size()) {
                	mCaretRow = mTextRows.size() - 1;
                }
                //FIXME: added
                int len = 0;
            	String text = "";
            	if (mCaretRow >= 0 && mCaretRow < mTextRows.size()) {
            		text = mTextRows.get(mCaretRow);
            	}
            	if (text != null) {
                	len = text.length();
            	} else {
            		len = 0;
            	}
            	if (mCaretColumn < 0) {
            		mCaretColumn = 0;
            	}
            	if (mCaretColumn > len) {
            		mCaretColumn = len;
            	}
            }
        } else if (key.getValue() == Key.PAGE_DOWN) {
            Widget par = getParent();
            if (par != null) {
                int rowsPerPage = 0;
                if (getFont() != null && getFont().getHeight() != 0) {
                	rowsPerPage = par.getChildrenArea().height / getFont().getHeight();
                }
                mCaretRow += rowsPerPage;
                if (mCaretRow < 0) {
                    mCaretRow = 0;
                }
                if (mCaretRow >= mTextRows.size()) {
                    mCaretRow = mTextRows.size() - 1;
                }
                //FIXME: added
                int len = 0;
            	String text = "";
            	if (mCaretRow >= 0 && mCaretRow < mTextRows.size()) {
            		text = mTextRows.get(mCaretRow);
            	}
            	if (text != null) {
                	len = text.length();
            	} else {
            		len = 0;
            	}
            	if (mCaretColumn < 0) {
            		mCaretColumn = 0;
            	}
            	if (mCaretColumn > len) {
            		mCaretColumn = len;
            	}
            }
        } else if(key.getValue() == Key.TAB
                && mEditable) {
        	if (mCaretRow >= 0 && mCaretRow < mTextRows.size()) {
            	String text = "";
            	String textLeft = "";
            	String textRight = "";
            	if (mCaretRow >= 0 && mCaretRow < mTextRows.size()) {
            		text = mTextRows.get(mCaretRow);
            	}
            	if (mCaretColumn < text.length()) {
            		textLeft = text.substring(0, mCaretColumn);
            		textRight = text.substring(mCaretColumn);
            	} else {
            		textLeft = text;
            		textRight = "";
            	}
        		
        		mTextRows.set(mCaretRow, textLeft + "    " + textRight);
        		mCaretColumn += 4;
        	}
        } else if (key.isCharacter()
                 && mEditable) {
        	if (mCaretRow >= 0 && mCaretRow < mTextRows.size()) {
            	String text = "";
            	String textLeft = "";
            	String textRight = "";
            	if (mCaretRow >= 0 && mCaretRow < mTextRows.size()) {
            		text = mTextRows.get(mCaretRow);
            	}
            	if (mCaretColumn < text.length()) {
            		textLeft = text.substring(0, mCaretColumn);
            		textRight = text.substring(mCaretColumn);
            	} else {
            		textLeft = text;
            		textRight = "";
            	}
        		mTextRows.set(mCaretRow, textLeft + (char)key.getValue() + textRight);
        		++mCaretColumn;
        	}
        }

        adjustSize();
        scrollToCaret();

        keyEvent.consume();
        
        System.out.println(">>>keyPressed mCaretRow: " + mCaretRow + ", mCaretColumn: " + mCaretColumn);
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseEvent.LEFT) {
            mCaretRow = mouseEvent.getY() / getFont().getHeight();
            
            System.out.println(">>1 mCaretRow: " + mCaretRow);
            
            if (mCaretRow >= (int)mTextRows.size()) {
                mCaretRow = mTextRows.size() - 1;
                System.out.println(">>2 mCaretRow: " + mCaretRow);
            }

            //FIXME:
            String text = "";
            if (mCaretRow >= 0 && mCaretRow < mTextRows.size()) {
            	text = mTextRows.get(mCaretRow);
            }
            
            mCaretColumn = getFont().getStringIndexAt(text, mouseEvent.getX());
            System.out.println(">>3 mCaretRow: " + mCaretRow + ", mCaretColumn: " + mCaretColumn);
        }
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMovedUp(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMovedDown(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent mouseEvent) {
		mouseEvent.consume();
	}

	@Override
	public void draw(Graphics graphics) throws GuichanException {
        int i;

        if (mOpaque) {
            graphics.setColor(getBackgroundColor());
            graphics.fillRectangle(new Rectangle(0, 0, getWidth(), getHeight()));
        }

        if (isFocused() && isEditable()) {
        	//FIXME:
            String text = "";
            if (mCaretRow >= 0 && mCaretRow < mTextRows.size()) {
            	text = mTextRows.get(mCaretRow);
            }
//            System.out.println(">>>xxxx : " + mCaretRow + " / " + mTextRows.size() + ", text : " + text);
            if (mCaretColumn <= text.length()) {
//            	System.out.println("old text : " + text + ", " + mCaretColumn);
            	text = text.substring(0, 0 + mCaretColumn);
            }
            
//            System.out.println(">>>>>>mCaretColumn : " + mCaretColumn + ", " + text + ", " + 
//            		getFont().getWidth(text) + ", " + mCaretRow * getFont().getHeight());
            drawCaret(graphics, getFont().getWidth(text), mCaretRow * getFont().getHeight());
        }

        graphics.setColor(getForegroundColor());
        graphics.setFont(getFont());

        for (i = 0; i < mTextRows.size(); i++) {
            // Move the text one pixel so we can have a caret before a letter.
            graphics.drawText(mTextRows.get(i), 1, i * getFont().getHeight());
        }
	}


    public void setText(String text) throws GuichanException {
    	//FIXME:Java implement
        mCaretColumn = 0;
        mCaretRow = 0;

        mTextRows.clear();
        
        if (text != null) {
        	String[] lines = text.split("\\n"/*"\\n|\\r"*/);
        	for (String line : lines) {
        		if (line != null) {
        			mTextRows.add(line);
        		}
        	}
        }
        adjustSize();
    }
    
    public void setCaretPosition(int position) {
        int row;

        for (row = 0; row < (int)mTextRows.size(); row++) {
            if (position <= mTextRows.get(row).length()) {
                mCaretRow = row;
                mCaretColumn = position;
                return; // we are done
            } else {
                position--;
            }
        }

        // position beyond end of text
        mCaretRow = mTextRows.size() - 1;
        mCaretColumn = mTextRows.get(mCaretRow).length();
    }

    public int getCaretPosition() {
        int pos = 0, row;

        for (row = 0; row < mCaretRow; row++) {
            pos += mTextRows.get(row).length();
        }
        
        return pos + mCaretColumn;
    }

    public void setCaretRowColumn(int row, int column) {
        setCaretRow(row);
        setCaretColumn(column);
    }

    public void setCaretRow(int row) {
        mCaretRow = row;

        if (mCaretRow >= (int)mTextRows.size()) {
            mCaretRow = mTextRows.size() - 1;
        }

        if (mCaretRow < 0) {
            mCaretRow = 0;
        }

        setCaretColumn(mCaretColumn);
    }

    public int getCaretRow() {
        return mCaretRow;
    }

    public void setCaretColumn(int column) {
        mCaretColumn = column;

        int len = 0;
        String text = "";
        if (mCaretRow >= 0 && mCaretRow < mTextRows.size()) {
        	text = mTextRows.get(mCaretRow);
        }
        if (text != null) {
        	len = text.length();
        } else {
        	len = 0;
        }
        
        if (mCaretColumn > len) {
            mCaretColumn = len;
        }

        if (mCaretColumn < 0) {
            mCaretColumn = 0;
        }
    }

    public int getCaretColumn() {
        return mCaretColumn;
    }

    public String getTextRow(int row) {
        return mTextRows.get(row);
    }

    public void setTextRow(int row, String text) throws GuichanException {
        mTextRows.set(row, text);
        
        if (mCaretRow == row) {
            setCaretColumn(mCaretColumn);
        }

        adjustSize();
    }

    public int getNumberOfRows() {
        return mTextRows.size();
    }

    public String getText() {
        if (mTextRows.size() == 0) {
            return "";
        }
        
        int i;
        String text = "";

        for (i = 0; i < (int)mTextRows.size() - 1; ++i) {
            text = text + mTextRows.get(i) + "\n";
        }

        text = text + mTextRows.get(i);

        return text;
    }

    public void fontChanged() throws GuichanException {
        adjustSize();
    }

    public void scrollToCaret() throws GuichanException {
        Rectangle scroll = new Rectangle();
        //FIXME:
        String text = "";
        if (mCaretRow >= 0 && mCaretRow <= mTextRows.size()) {
        	text = mTextRows.get(mCaretRow);
        }
        if (mCaretColumn <= text.length()) {
        	text = text.substring(0, 0 + mCaretColumn);
        }
        
        scroll.x = getFont().getWidth(text);
        scroll.y = getFont().getHeight() * mCaretRow;
        scroll.width = getFont().getWidth(" ");
        scroll.height = getFont().getHeight() + 2; // add 2 for some extra space

        showPart(scroll);
    }

    public void setEditable(boolean editable) {
        mEditable = editable;
    }

    public boolean isEditable() {
        return mEditable;
    }

    public void addRow(String row) throws GuichanException {
        mTextRows.add(row);
        adjustSize();
    }

    public boolean isOpaque() {
        return mOpaque;
    }

    public void setOpaque(boolean opaque) {
        mOpaque = opaque;
    }
    
    public boolean isFocused() {
    	return super.isFocused();
    }
}
