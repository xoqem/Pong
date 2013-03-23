package com.jc.pong.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Paddle extends Entity {

  //---------------------------------------------------------------------------
  // Constructor
  //---------------------------------------------------------------------------

  public Paddle(World world, Vector2 position, Vector2 size) {
    super(world, position);

    Body body = getBody();
    body.setFixedRotation(true);

    PolygonShape rect = new PolygonShape();
    rect.setAsBox(size.x / 2, size.y / 2);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = rect;
    fixtureDef.density = 5.0f;
    fixtureDef.friction = 1f;
    fixtureDef.restitution = 0f;

    body.createFixture(fixtureDef);

    rect.dispose();
  }
}
