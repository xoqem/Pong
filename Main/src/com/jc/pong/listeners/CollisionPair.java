package com.jc.pong.listeners;

import com.jc.pong.elements.Entity;

public class CollisionPair {

  //---------------------------------------------------------------------------
  // Variables - Public
  //---------------------------------------------------------------------------

  public Entity targetEntity;
  public Entity contactingEntity;

  //---------------------------------------------------------------------------
  // Constructor
  //---------------------------------------------------------------------------

  public CollisionPair(Entity targetEntity, Entity contactingEntity) {
    this.targetEntity = targetEntity;
    this.contactingEntity = contactingEntity;
  }
}
