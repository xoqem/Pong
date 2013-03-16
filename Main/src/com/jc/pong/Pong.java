package com.jc.pong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Pong extends Game{

  //---------------------------------------------------------------------------
  // Variables - Private
  //---------------------------------------------------------------------------

  OrthographicCamera camera;
  ShapeRenderer shapeRenderer;

  Paddle player1;
  Paddle player2;

  //---------------------------------------------------------------------------
  // Methods - Public - Overrides
  //---------------------------------------------------------------------------

  @Override
  public void create() {

    float windowWidth = 800;
    float windowHeight = 480;

    camera = new OrthographicCamera();
    camera.setToOrtho(false, windowWidth, windowHeight);

    shapeRenderer = new ShapeRenderer();

    player1 = new Paddle(30, 0, 30, windowHeight);
    player1.move(player1.x, windowHeight / 2);

    player2 = new Paddle(windowWidth - 30, 0, windowWidth - 30, windowHeight);
    player2.move(player2.x, windowHeight / 2);
  }

  @Override
  public void render() {
    Gdx.gl.glClearColor(0, 0, 0.2f, 1);
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

    camera.update();
    shapeRenderer.setProjectionMatrix(camera.combined);

    player1.render(shapeRenderer);
    player2.render(shapeRenderer);
  }
}
