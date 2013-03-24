package com.jc.pong.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public abstract class Entity {

  //---------------------------------------------------------------------------
  // Variables - Private
  //---------------------------------------------------------------------------

  World world;
  Body body;
  BodyType bodyType;
  Vector2 initialPosition;

  //---------------------------------------------------------------------------
  // Constructor
  //---------------------------------------------------------------------------

  public Entity(World world, Vector2 initialPosition, BodyType bodyType) {
    this.world = world;
    this.bodyType = bodyType;
    this.initialPosition = initialPosition;
  }

  //---------------------------------------------------------------------------
  // Properties - Public
  //---------------------------------------------------------------------------

  public Body getBody() {
    return body;
  }

  //---------------------------------------------------------------------------
  // Methods - Abstract - Protected
  //---------------------------------------------------------------------------

  protected abstract FixtureDef createFixtureDef();

  protected abstract Shape createShape();

  //---------------------------------------------------------------------------
  // Methods - Public
  //---------------------------------------------------------------------------

  public void reset() {
    Body body = getBody();
    if (body != null) {
      body.setLinearVelocity(0, 0);
      body.setAngularVelocity(0);
      Gdx.app.log("Entity", "reset 1");
      body.setTransform(initialPosition, 0);
      Gdx.app.log("Entity", "reset 2");
    }
  }

  //---------------------------------------------------------------------------
  // Methods - Protected
  //---------------------------------------------------------------------------

  protected void createBody() {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = bodyType;
    bodyDef.position.set(initialPosition);

    body = world.createBody(bodyDef);

    FixtureDef fixtureDef = createFixtureDef();
    fixtureDef.shape = createShape();

    body.createFixture(fixtureDef);

    fixtureDef.shape.dispose();
  }
}
