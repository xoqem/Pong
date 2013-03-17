package com.jc.pong.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
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
  Matrix4 debugMatrix;

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

    debugMatrix = new Matrix4(camera.combined);
    debugMatrix.scale(BOX_TO_WORLD, BOX_TO_WORLD, 1f);

    shapeRenderer = new ShapeRenderer();

    player1 = new Paddle(world, createBoxVector(30f, height / 2f), createBoxVector(10f, 50f));
    player2 = new Paddle(world, createBoxVector(width - 30f, height / 2f), createBoxVector(10f, 50f));
    puck = new Puck(world, createBoxVector(width / 2f, height / 2f), createBoxDistance(10f));
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
  // Methods - Public
  //---------------------------------------------------------------------------

  public void draw(float delta) {
    Gdx.gl.glClearColor(0, 0, 0.2f, 1);
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

    camera.update();

    debugRenderer.render(world, debugMatrix);
  }

  public void update(float delta) {
    world.step(delta, 6, 2);

    float paddleSpeed = 0f;
    if (Gdx.input.isKeyPressed(Keys.DPAD_UP) || Gdx.input.isKeyPressed(Keys.W)) {
      paddleSpeed = createBoxDistance(1000f);
    }
    else if (Gdx.input.isKeyPressed(Keys.DPAD_DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
      paddleSpeed = createBoxDistance(-1000f);
    }
    player1.getBody().setLinearVelocity(0f, paddleSpeed);
  }

  //---------------------------------------------------------------------------
  // Methods - Private
  //---------------------------------------------------------------------------

  private Vector2 createBoxVector(float x, float y) {
    return new Vector2(x, y).mul(WORLD_TO_BOX);
  }


  private float createBoxDistance(float dist) {
    return dist * WORLD_TO_BOX;
  }
}
