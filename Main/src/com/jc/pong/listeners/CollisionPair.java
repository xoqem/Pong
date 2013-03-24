package com.jc.pong.listeners;

import com.badlogic.gdx.Gdx;
import com.jc.pong.elements.Entity;

public class CollisionPair {

  //---------------------------------------------------------------------------
  // Variables - Private
  //---------------------------------------------------------------------------

  Entity targetEntity;
  Entity contactingEntity;

  //---------------------------------------------------------------------------
  // Constructor
  //---------------------------------------------------------------------------

  public CollisionPair(Entity targetEntity, Entity contactingEntity) {
    this.targetEntity = targetEntity;
    this.contactingEntity = contactingEntity;
  }

  //---------------------------------------------------------------------------
  // Methods - Public
  //---------------------------------------------------------------------------

  public void processCollision()
  {
    // TODO: since Java can't do proper callback functions, GameScreen will need to implement some interface
    // that we call from here (so each collision pair would have an associated class that implements the class
    // that will have the callback method).
    Gdx.app.log("CollisionPair", "processCollision");
  }
}
