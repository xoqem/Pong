package com.jc.pong.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Puck extends Entity {

  //---------------------------------------------------------------------------
  // Variables - Private
  //--------------------------------------------------------------------------

  float radius;

  //---------------------------------------------------------------------------
  // Constructor
  //---------------------------------------------------------------------------

  public Puck(World world, Vector2 position, float radius) {
    super(world, position, BodyType.DynamicBody);

    this.radius = radius;
    createBody();
  }

  //---------------------------------------------------------------------------
  // Methods - Entity Implementation
  //---------------------------------------------------------------------------

  protected FixtureDef createFixtureDef() {
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.density = 0.5f;
    fixtureDef.friction = 0.15f;
    fixtureDef.restitution = 0.6f;
    return fixtureDef;
  }

  protected Shape createShape() {
    CircleShape circle = new CircleShape();
    circle.setRadius(radius);
    return circle;
  }
}
