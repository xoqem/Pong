package com.jc.pong;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Puck {

  //---------------------------------------------------------------------------
  // Variables - Private
  //---------------------------------------------------------------------------

  float gravity = 9.8f; // gravity
  float friction = 0.15f; // coefficient of friction for a puck on ice
  float friction_gravity = friction * gravity; // pre-calculate

  float radius = 10f;

  float x;
  float y;
  float vx;
  float vy;

  //---------------------------------------------------------------------------
  // Constructor
  //---------------------------------------------------------------------------

  public Puck() {
  }

  //---------------------------------------------------------------------------
  // Properties - Public
  //---------------------------------------------------------------------------

  public float getX() {
    return x;
  }

  public float getY() {
    return y;
  }

  //---------------------------------------------------------------------------
  // Methods - Public
  //---------------------------------------------------------------------------

  public void move(float x, float y)
  {
    this.x = x;
    this.y = y;
  }

  public void setVelocity(float vx, float vy) {
    this.vx = vx;
    this.vy = vy;
  }

  public void simulate(float delta) {
    vx = getNewVelocity(vx, -friction_gravity, delta);
    vy = getNewVelocity(vy, -friction_gravity, delta);

    x = x + vx;
    y = y + vy;
  }

  public void render(ShapeRenderer shapeRenderer) {
    shapeRenderer.begin(ShapeType.FilledCircle);
    shapeRenderer.setColor(new Color(1, 1, 1, 1));
    shapeRenderer.filledCircle(x, y, radius);
    shapeRenderer.end();
  }

  //---------------------------------------------------------------------------
  // Methods - Private
  //---------------------------------------------------------------------------

  private float getNewVelocity(float velocity, float acceleration, float time) {
    float changeInVelocity = acceleration * time;
    if (Math.abs(velocity) - Math.abs(changeInVelocity) < 0) {
      return 0;
    } else {
      return velocity + acceleration * time;
    }
  }
}
