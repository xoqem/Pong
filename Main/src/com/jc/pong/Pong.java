package com.jc.pong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.jc.pong.screens.GameLoop;

public class Pong extends Game{

  //---------------------------------------------------------------------------
  // Variables - Private
  //---------------------------------------------------------------------------

  private Screen currentScreen;
  private FPSLogger fps;

  //---------------------------------------------------------------------------
  // Methods - Public - Overrides
  //---------------------------------------------------------------------------

  @Override
  public void create() {
    currentScreen = new GameLoop(800, 480);
    fps = new FPSLogger();
  }

  @Override
  public void render() {
    currentScreen.render(Gdx.graphics.getDeltaTime());
    fps.log();
  }
}
