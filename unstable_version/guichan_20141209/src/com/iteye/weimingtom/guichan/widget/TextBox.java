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
                    mCaretColumn = mTextRows.get(mCaretRow).length();
                }
            }
        } else if (key.getValue() == Key.RIGHT) {
            ++mCaretColumn;
            if (mCaretColumn > (int)mTextRows.get(mCaretRow).length()) {
                ++mCaretRow;

                if (mCaretRow >= (int)mTextRows.size()) {
                    mCaretRow = mTextRows.size() - 1;
                    if (mCaretRow < 0) {
                        mCaretRow = 0;
                    }

                    mCaretColumn = mTextRows.get(mCaretRow).length();
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
            mCaretColumn = mTextRows.get(mCaretRow).length();
        } else if (key.getValue() == Key.ENTER && mEditable) {
        	mTextRows.add(mCaretRow + 1, mTextRows.get(mCaretRow).substring(mCaretColumn, mTextRows.get(mCaretRow).length() - mCaretColumn));
            mTextRows.set(mCaretRow, mTextRows.get(mCaretRow).substring(0, mCaretColumn));
            ++mCaretRow;
            mCaretColumn = 0;
        } else if (key.getValue() == Key.BACKSPACE
                 && mCaretColumn != 0
                 && mEditable) {
            mTextRows.set(mCaretRow, mTextRows.get(mCaretRow).substring(0, mCaretColumn - 1) +  mTextRows.get(mCaretRow).substring(mCaretColumn));
            --mCaretColumn;
        } else if (key.getValue() == Key.BACKSPACE
                 && mCaretColumn == 0
                 && mCaretRow != 0
                 && mEditable) {
            mCaretColumn = mTextRows.get(mCaretRow - 1).length();
            mTextRows.set(mCaretRow - 1, mTextRows.get(mCaretRow - 1) + mTextRows.get(mCaretRow));
            mTextRows.remove(mCaretRow);
            --mCaretRow;
        } else if (key.getValue() == Key.DELETE
                 && mCaretColumn < (int)mTextRows.get(mCaretRow).length()
                 && mEditable) {
        	mTextRows.set(mCaretRow, mTextRows.get(mCaretRow).substring(0, mCaretColumn) + mTextRows.get(mCaretRow).substring(mCaretColumn + 1));
        } else if (key.getValue() == Key.DELETE
                 && mCaretColumn == (int)mTextRows.get(mCaretRow).length()
                 && mCaretRow < ((int)mTextRows.size() - 1)
                 && mEditable) {
            mTextRows.set(mCaretRow, mTextRows.get(mCaretRow) + mTextRows.get(mCaretRow + 1));
            mTextRows.remove(mCaretRow + 1);
        } else if (key.getValue() == Key.PAGE_UP) {
            Widget par = getParent();

            if (par != null) {
                int rowsPerPage = par.getChildrenArea().height / getFont().getHeight();
                mCaretRow -= rowsPerPage;

                if (mCaretRow < 0)
                {
                    mCaretRow = 0;
                }
            }
        } else if (key.getValue() == Key.PAGE_DOWN) {
            Widget par = getParent();

            if (par != null) {
                int rowsPerPage = par.getChildrenArea().height / getFont().getHeight();
                mCaretRow += rowsPerPage;

                if (mCaretRow >= (int)mTextRows.size())
                {
                    mCaretRow = mTextRows.size() - 1;
                }
            }
        } else if(key.getValue() == Key.TAB
                && mEditable) {
            mTextRows.set(mCaretRow, mTextRows.get(mCaretRow).substring(0, mCaretColumn) +  "    " + mTextRows.get(mCaretRow).substring(mCaretColumn));
            mCaretColumn += 4;
        } else if (key.isCharacter()
                 && mEditable) {
            mTextRows.set(mCaretRow, mTextRows.get(mCaretRow).substring(0, mCaretColumn) + (char)key.getValue() + mTextRows.get(mCaretRow).substring(mCaretColumn));
            ++mCaretColumn;
        }

        adjustSize();
        scrollToCaret();

        keyEvent.consume();
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
            
            if (mCaretRow >= (int)mTextRows.size()) {
                mCaretRow = mTextRows.size() - 1;
            }

            mCaretColumn = getFont().getStringIndexAt(mTextRows.get(mCaretRow), mouseEvent.getX());
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
            drawCaret(graphics, getFont().getWidth(mTextRows.get(mCaretRow).substring(0, mCaretColumn)), mCaretRow * getFont().getHeight());
        }

        graphics.setColor(getForegroundColor());
        graphics.setFont(getFont());

        for (i = 0; i < mTextRows.size(); i++) {
            // Move the text one pixel so we can have a caret before a letter.
            graphics.drawText(mTextRows.get(i), 1, i * getFont().getHeight());
        }
	}


    public void setText(String text) throws GuichanException {
        mCaretColumn = 0;
        mCaretRow = 0;

        mTextRows.clear();
        
        if (text != null) {
        	String[] lines = text.split("\\n|\\r");
        	for (String line : lines) {
        		if (line != null && !line.equals("")) {
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

        if (mCaretColumn > (int)mTextRows.get(mCaretRow).length()) {
            mCaretColumn = mTextRows.get(mCaretRow).length();
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
        scroll.x = getFont().getWidth(mTextRows.get(mCaretRow).substring(0, mCaretColumn));
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
}
