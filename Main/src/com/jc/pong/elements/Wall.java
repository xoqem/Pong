package com.jc.pong.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Wall {
  //---------------------------------------------------------------------------
  // Variables - Private
  //---------------------------------------------------------------------------

  Body body;

  //---------------------------------------------------------------------------
  // Constructor
  //---------------------------------------------------------------------------

  public Wall(World world, Vector2 min, Vector2 max) {
    Vector2 size = max.cpy().sub(min);
    Vector2 position = min.cpy().add(size.cpy().div(2f));

    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyType.StaticBody;
    bodyDef.position.set(position);

    body = world.createBody(bodyDef);

    PolygonShape rect = new PolygonShape();
    rect.setAsBox(size.x / 2, size.y / 2);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = rect;
    fixtureDef.density = 1f;
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
