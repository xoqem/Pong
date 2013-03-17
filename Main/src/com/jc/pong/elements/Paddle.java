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
    // First we create a body definition
    BodyDef bodyDef = new BodyDef();
    // We set our body to dynamic, for something like ground which doesnt move we would set it to StaticBody
    bodyDef.type = BodyType.KinematicBody;
    // Set our body's starting position in the world
    bodyDef.position.set(position);

    // Create our body in the world using our body definition
    body = world.createBody(bodyDef);

    // Create a circle shape and set its radius to 6
    PolygonShape rect = new PolygonShape();
    rect.setAsBox(size.x, size.y);

    // Create a fixture definition to apply our shape to
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = rect;
    fixtureDef.density = 0.5f;
    fixtureDef.friction = 0f;
    fixtureDef.restitution = 0.6f; // Make it bounce a little bit

    // Create our fixture and attach it to the body
    Fixture fixture = body.createFixture(fixtureDef);

    // Remember to dispose of any shapes after you're done with them!
    // BodyDef and FixtureDef don't need disposing, but shapes do.
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
