package com.jc.pong.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Paddle {

  //---------------------------------------------------------------------------
  // Variables - Private
  //---------------------------------------------------------------------------

  Body body;

  //---------------------------------------------------------------------------
  // Constructor
  //---------------------------------------------------------------------------

  public Paddle(World world, Vector2 position, Vector2 size) {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyType.KinematicBody;
    bodyDef.position.set(position);

    body = world.createBody(bodyDef);

    PolygonShape rect = new PolygonShape();
    rect.setAsBox(size.x, size.y);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = rect;
    fixtureDef.density = 0.5f;
    fixtureDef.friction = 0f;
    fixtureDef.restitution = 0.6f;

    body.createFixture(fixtureDef);

    rect.dispose();
  }

  //---------------------------------------------------------------------------
  // Properties - Public
  //---------------------------------------------------------------------------

  public Body getBody()
  {
    return body;
  }
}
