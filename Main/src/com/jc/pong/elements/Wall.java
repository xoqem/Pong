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

  public Wall(World world, Vector2 position, Vector2[] vertices) {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyType.StaticBody;
    bodyDef.position.set(position);

    body = world.createBody(bodyDef);

    PolygonShape polygon = new PolygonShape();
    polygon.set(vertices);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = polygon;
    fixtureDef.density = 0f;
    fixtureDef.friction = 0f;
    fixtureDef.restitution = 0f;

    body.createFixture(fixtureDef);

    polygon.dispose();
  }

  //---------------------------------------------------------------------------
  // Properties - Public
  //---------------------------------------------------------------------------

  public Body getBody() {
    return body;
  }
}
