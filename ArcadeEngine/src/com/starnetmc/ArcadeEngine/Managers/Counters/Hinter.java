package com.starnetmc.ArcadeEngine.Managers.Counters;

import com.starnetmc.ArcadeCore;
import com.starnetmc.ArcadeEngine.Utils.AF;

public class Hinter implements Runnable {

	int time = 0;
    int hintID = ArcadeCore.getArcadeManager().getGame().getGClass().getHints().size() - 1;
    
	@Override
	public void run() {
	      
		  if (time == 15){
		
          String hint = ArcadeCore.getArcadeManager().getGame().getGClass().getHints().get(hintID);
		  AF.msgAll(AF.hint(hint));
		  if (hintID-1 == -1){
		      hintID = ArcadeCore.getArcadeManager().getGame().getGClass().getHints().size() - 1;
		  } else {
			  hintID--;
		  }
		  
		  time = 0;
		  } else {
			  time++;
		  }
		  
	}

}