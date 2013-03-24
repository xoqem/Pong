package com.jc.pong.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;

public class GameContactListener implements ContactListener{

  //---------------------------------------------------------------------------
  // Constructor
  //---------------------------------------------------------------------------

  public GameContactListener(World world) {
    world.setContactListener(this);
  }

  //---------------------------------------------------------------------------
  // Methods - ContactListener Implementation
  //---------------------------------------------------------------------------

  public void beginContact(Contact contact) {
    Gdx.app.log("GameContactListener", "beingContact");
  }

  public void endContact(Contact contact) {
    Gdx.app.log("GameContactListener", "endContact");
  }

  public void preSolve(Contact contact, Manifold oldManifold) {
    //Gdx.app.log("GameContactListener", "preSolve");
  }

  public void postSolve(Contact contact, ContactImpulse impulse){
    //Gdx.app.log("GameContactListener", "postSolve");
  }
}
