package it.reef.interf;
import it.reef.input.WormKeyAdapter;

import java.awt.event.*;
import java.util.LinkedList;
    
public interface KeyProcessableWorm
{
    public void handleKeyEvent(int direction);
    
    public void setKeyAdapter(WormKeyAdapter ka);
}