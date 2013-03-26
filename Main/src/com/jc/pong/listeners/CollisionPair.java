package com.jc.pong.listeners;

import com.jc.pong.elements.Entity;

public class CollisionPair {

  //---------------------------------------------------------------------------
  // Variables - Public
  //---------------------------------------------------------------------------

  public Entity targetEntity;
  public Entity contactingEntity;
  public CallbackFunction callbackFunction;

  //---------------------------------------------------------------------------
  // Constructor
  //---------------------------------------------------------------------------

  public CollisionPair(Entity targetEntity, Entity contactingEntity, CallbackFunction callbackFunction) {
    this.targetEntity = targetEntity;
    this.contactingEntity = contactingEntity;
    this.callbackFunction = callbackFunction;
  }
}
