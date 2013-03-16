package com.jc.pong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.jc.pong.screens.GameScreen;

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
    currentScreen = new GameScreen(800, 480);
    fps = new FPSLogger();
  }

  @Override
  public void render() {
    currentScreen.render(Gdx.graphics.getDeltaTime());
    fps.log();
  }
}
