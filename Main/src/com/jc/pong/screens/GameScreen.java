package com.jc.pong.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jc.pong.Paddle;
import com.jc.pong.Puck;

public class GameScreen implements Screen {

  //---------------------------------------------------------------------------
  // Finals - Static - Private
  //---------------------------------------------------------------------------

  final static float IDEAL_DELTA = 1f/ 60f; // 1 second / 60 fps: this is our ideal delta given our ideal fps
  final static float GRAVITY = -9.8f;

  static final float WORLD_TO_BOX = 0.01f;
  static final float BOX_TO_WORLD = 100f;

  //---------------------------------------------------------------------------
  // Variables - Private
  //---------------------------------------------------------------------------

  OrthographicCamera camera;
  ShapeRenderer shapeRenderer;

  Paddle player1;
  Paddle player2;
  Puck puck;

  //---------------------------------------------------------------------------
  // Constructor
  //---------------------------------------------------------------------------

  public GameScreen(float width, float height) {
    camera = new OrthographicCamera();
    camera.setToOrtho(false, width, height);

    shapeRenderer = new ShapeRenderer();

    player1 = new Paddle(30, 0, 30, height);
    player1.move(player1.getX(), height / 2);

    player2 = new Paddle(width - 30, 0, width - 30, height);
    player2.move(player2.getY(), height / 2);

    puck = new Puck();
    puck.move(width / 2, height / 2);
    puck.setVelocity(3, 3);
  }

  //---------------------------------------------------------------------------
  // Methods - Public - Overrides
  //---------------------------------------------------------------------------

  @Override
  public void render(float delta) {
    update(delta);
    draw(delta);
  }

  @Override
  public void resize (int width, int height) {
  }

  @Override
  public void show () {
  }

  @Override
  public void hide () {
  }

  @Override
  public void pause () {
  }

  @Override
  public void resume () {
  }

  @Override
  public void dispose () {
  }

  //---------------------------------------------------------------------------
  // Methods - Private
  //---------------------------------------------------------------------------

  public void draw(float delta) {
    Gdx.gl.glClearColor(0, 0, 0.2f, 1);
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

    camera.update();
    shapeRenderer.setProjectionMatrix(camera.combined);

    player1.render(shapeRenderer);
    player2.render(shapeRenderer);

    puck.render(shapeRenderer);
  }

  public void update(float delta) {
    float timeScale = delta / IDEAL_DELTA;

    if (Gdx.input.isKeyPressed(Keys.DPAD_UP) || Gdx.input.isKeyPressed(Keys.W)) {
      player1.move(player1.getX(), player1.getY() + 10 * timeScale);
      player2.move(player2.getX(), player2.getY() + 10 * timeScale);
    }
    if (Gdx.input.isKeyPressed(Keys.DPAD_DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
      player1.move(player1.getX(), player1.getY() - 10 * timeScale);
      player2.move(player2.getX(), player2.getY() - 10 * timeScale);
    }

    puck.simulate(delta);
  }
}
