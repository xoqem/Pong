package com.jc.pong.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Puck {

  //---------------------------------------------------------------------------
  // Variables - Private
  //---------------------------------------------------------------------------

  Body body;

  //---------------------------------------------------------------------------
  // Constructor
  //---------------------------------------------------------------------------

  public Puck(World world, Vector2 position, float radius) {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyType.DynamicBody;
    bodyDef.position.set(position);

    body = world.createBody(bodyDef);

    CircleShape circle = new CircleShape();
    circle.setRadius(radius);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = circle;
    fixtureDef.density = 0.5f;
    fixtureDef.friction = 0.15f;
    fixtureDef.restitution = 0.6f;

    Fixture fixture = body.createFixture(fixtureDef);

    circle.dispose();
  }

  //---------------------------------------------------------------------------
  // Properties - Public
  //---------------------------------------------------------------------------

  public Body getBody()
  {
    return body;
  }
}
