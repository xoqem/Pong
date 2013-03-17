package com.jc.pong.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.jc.pong.elements.Paddle;
import com.jc.pong.elements.Puck;

public class GameScreen implements Screen {

  //---------------------------------------------------------------------------
  // Finals - Static - Private
  //---------------------------------------------------------------------------

  static final float IDEAL_DELTA = 1f/ 60f; // 1 second / 60 fps: this is our ideal delta given our ideal fps
  static final float GRAVITY = 0;//-9.8f;

  static final float WORLD_TO_BOX = 0.01f;
  static final float BOX_TO_WORLD = 100f;

  //---------------------------------------------------------------------------
  // Variables - Private
  //---------------------------------------------------------------------------

  World world;
  Box2DDebugRenderer debugRenderer;

  OrthographicCamera camera;
  ShapeRenderer shapeRenderer;

  Paddle player1;
  Paddle player2;
  Puck puck;

  //---------------------------------------------------------------------------
  // Constructor
  //---------------------------------------------------------------------------

  public GameScreen(float width, float height) {
    world = new World(new Vector2(0, GRAVITY), true);
    debugRenderer = new Box2DDebugRenderer();

    camera = new OrthographicCamera();
    camera.setToOrtho(false, width, height);

    shapeRenderer = new ShapeRenderer();

    player1 = new Paddle(world, new Vector2(30, height / 2));
    player2 = new Paddle(world, new Vector2(width - 30, height / 2));
    puck = new Puck(world, new Vector2(width / 2, height / 2));
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

    debugRenderer.render(world, camera.combined);
  }

  public void update(float delta) {
    world.step(delta, 6, 2);

    float paddleSpeed = 0f;
    if (Gdx.input.isKeyPressed(Keys.DPAD_UP) || Gdx.input.isKeyPressed(Keys.W)) {
      paddleSpeed = 100f;
    }
    else if (Gdx.input.isKeyPressed(Keys.DPAD_DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
      paddleSpeed = -100f;
    }
    player1.getBody().setLinearVelocity(0f, paddleSpeed);
  }
}
