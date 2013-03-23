package com.jc.pong.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Paddle extends Entity {

  //---------------------------------------------------------------------------
  // Variables - Private
  //--------------------------------------------------------------------------

  Vector2 size;

  //---------------------------------------------------------------------------
  // Constructor
  //---------------------------------------------------------------------------

  public Paddle(World world, Vector2 position, Vector2 size) {
    super(world, position, BodyType.DynamicBody);

    this.size = size;
    createBody();
  }

  //---------------------------------------------------------------------------
  // Methods - Protected
  //---------------------------------------------------------------------------

  @Override
  protected void createBody() {
    super.createBody();
    getBody().setFixedRotation(true);
  }

  //---------------------------------------------------------------------------
  // Methods - Entity Implementation
  //---------------------------------------------------------------------------

  public FixtureDef createFixtureDef() {
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.density = 5.0f;
    fixtureDef.friction = 1f;
    fixtureDef.restitution = 0f;
    return fixtureDef;
  }

  public Shape createShape() {
    PolygonShape rect = new PolygonShape();
    rect.setAsBox(size.x / 2, size.y / 2);
    return rect;
  }
}
