package com.jc.pong.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Entity {

  //---------------------------------------------------------------------------
  // Variables - Private
  //---------------------------------------------------------------------------

  Body body;
  Vector2 originalPosition;

  //---------------------------------------------------------------------------
  // Constructor
  //---------------------------------------------------------------------------

  public Entity(World world, Vector2 position) {
    originalPosition = position;

    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.DynamicBody;
    bodyDef.position.set(position);

    body = world.createBody(bodyDef);
  }

  //---------------------------------------------------------------------------
  // Properties - Public
  //---------------------------------------------------------------------------

  public Body getBody()
  {
    return body;
  }

  //---------------------------------------------------------------------------
  // Methods - Public
  //---------------------------------------------------------------------------

  public void reset()
  {
    Body body = getBody();
    body.setLinearVelocity(0, 0);
    body.setAngularVelocity(0);
    body.setTransform(originalPosition, 0);
  }
}
