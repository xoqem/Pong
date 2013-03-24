package com.jc.pong.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Sensor extends StaticPolygon {

  //---------------------------------------------------------------------------
  // Constructor
  //---------------------------------------------------------------------------

  public Sensor(World world, Vector2 position, Vector2[] vertices) {
    super(world, position, vertices);
  }

  //---------------------------------------------------------------------------
  // Methods - Protected - Overrides
  //---------------------------------------------------------------------------

  @Override
  protected FixtureDef createFixtureDef() {
    FixtureDef fixtureDef = super.createFixtureDef();
    fixtureDef.isSensor = true;
    return fixtureDef;
  }
}
