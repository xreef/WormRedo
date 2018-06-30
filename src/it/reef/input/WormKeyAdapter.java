package it.reef.input;

import it.reef.interf.KeyProcessableWorm;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WormKeyAdapter extends KeyAdapter {
	private it.reef.interf.KeyProcessableWorm player;
	private int direction=KeyEvent.VK_UP;
	
	public WormKeyAdapter(KeyProcessableWorm p) {
		player = p;
		p.setKeyAdapter(this);
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		super.keyPressed(e);
		direction = e.getKeyCode();
	}

//	@Override
//	public void keyReleased(KeyEvent e) {
//		// TODO Auto-generated method stub
//		super.keyReleased(e);
//	}
//
//	public void keyTyped(KeyEvent e) {
//		super.keyTyped(e);
//		direction = e.getKeyCode();
//	}
  
    public void processKeyEventList()
    {
        player.handleKeyEvent(direction);
    }
}
