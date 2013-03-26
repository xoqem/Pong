package com.jc.pong.listeners;

import com.badlogic.gdx.physics.box2d.*;
import com.jc.pong.elements.Entity;

import java.util.HashMap;
import java.util.Map;

public class GameContactListener implements ContactListener{

  //---------------------------------------------------------------------------
  // Variables - Private
  //---------------------------------------------------------------------------

  Map<Body, CollisionPair> collisionMap = new HashMap<Body, CollisionPair>();

  //---------------------------------------------------------------------------
  // Constructor
  //---------------------------------------------------------------------------

  public GameContactListener(World world) {
    world.setContactListener(this);
  }

  //---------------------------------------------------------------------------
  // Methods - Public
  //---------------------------------------------------------------------------

  public void addCollisionPair(Entity targetEntity, Entity contactingEntity, CollisionProcessor collisionProcessor) {
    collisionMap.put(targetEntity.getBody(), new CollisionPair(targetEntity, contactingEntity, collisionProcessor));
  }

  //---------------------------------------------------------------------------
  // Methods - ContactListener Implementation
  //---------------------------------------------------------------------------

  public void beginContact(Contact contact) {
    Body bodyA = contact.getFixtureA().getBody();
    Body bodyB = contact.getFixtureB().getBody();

    checkCollision(bodyA, bodyB);
    checkCollision(bodyB, bodyA);

    //Gdx.app.log("GameContactListener", "beingContact");
  }

  public void endContact(Contact contact) {
    //Gdx.app.log("GameContactListener", "endContact");
  }

  public void preSolve(Contact contact, Manifold oldManifold) {
    //Gdx.app.log("GameContactListener", "preSolve");
  }

  public void postSolve(Contact contact, ContactImpulse impulse){
    //Gdx.app.log("GameContactListener", "postSolve");
  }

  //---------------------------------------------------------------------------
  // Methods - Private
  //---------------------------------------------------------------------------

  private void checkCollision(Body targetBody, Body contactingBody)
  {
    if (collisionMap.containsKey(targetBody)) {
      CollisionPair collisionPair = collisionMap.get(targetBody);
      if(collisionPair.contactingEntity.getBody() == contactingBody) {
        collisionPair.collisionProcessor.processCollision(collisionPair);
      }
    }
  }
}
