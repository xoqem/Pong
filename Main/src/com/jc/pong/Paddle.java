package com.jc.pong;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Paddle {

  //---------------------------------------------------------------------------
  // Variables - Private
  //---------------------------------------------------------------------------

  float width = 20;
  float height = 100;

  float x;
  float y;

  float minX;
  float minY;
  float maxX;
  float maxY;

  //---------------------------------------------------------------------------
  // Constructor
  //---------------------------------------------------------------------------

  public Paddle(float minX, float minY, float maxX, float maxY) {
    this.minX = minX;
    this.minY = minY;
    this.maxX = maxX;
    this.maxY = maxY;

    move(0, 0);
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

  public void move(float newX, float newY) {
    newX = Math.max(newX, minX);
    newX = Math.min(newX, maxX);
    newY = Math.max(newY, minY + height / 2);
    newY = Math.min(newY, maxY - height / 2);

    x = newX;
    y = newY;
  }


  public void render(ShapeRenderer shapeRenderer) {
    shapeRenderer.begin(ShapeRenderer.ShapeType.FilledRectangle);
    shapeRenderer.setColor(new Color(1, 1, 1, 1));
    shapeRenderer.filledRect(x - width / 2, y - height / 2, width, height);
    shapeRenderer.end();
  }
}
