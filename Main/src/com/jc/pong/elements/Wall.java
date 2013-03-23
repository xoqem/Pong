package com.jc.pong.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Wall extends Entity{

  //---------------------------------------------------------------------------
  // Variables - Private
  //--------------------------------------------------------------------------

  Vector2[] vertices;

  //---------------------------------------------------------------------------
  // Constructor
  //---------------------------------------------------------------------------

  public Wall(World world, Vector2 position, Vector2[] vertices) {
    super(world, position, BodyType.StaticBody);

    this.vertices = vertices;
    createBody();
  }

  //---------------------------------------------------------------------------
  // Methods - Entity Implementation
  //---------------------------------------------------------------------------

  public FixtureDef createFixtureDef() {
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.density = 0f;
    fixtureDef.friction = 0f;
    fixtureDef.restitution = 0f;
    return fixtureDef;
  }

  public Shape createShape() {
    PolygonShape polygon = new PolygonShape();
    polygon.set(vertices);
    return polygon;
  }
}
