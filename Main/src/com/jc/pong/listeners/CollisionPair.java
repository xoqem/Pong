package com.jc.pong.listeners;

import com.jc.pong.elements.Entity;

public class CollisionPair {

  //---------------------------------------------------------------------------
  // Variables - Public
  //---------------------------------------------------------------------------

  public Entity targetEntity;
  public Entity contactingEntity;
  public CollisionProcessor collisionProcessor;

  //---------------------------------------------------------------------------
  // Constructor
  //---------------------------------------------------------------------------

  public CollisionPair(Entity targetEntity, Entity contactingEntity, CollisionProcessor collisionProcessor) {
    this.targetEntity = targetEntity;
    this.contactingEntity = contactingEntity;
    this.collisionProcessor = collisionProcessor;
  }
}
