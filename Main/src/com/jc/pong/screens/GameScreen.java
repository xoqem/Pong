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
import com.jc.pong.elements.*;
import com.jc.pong.enums.GameEvent;
import com.jc.pong.listeners.CollisionPair;
import com.jc.pong.listeners.CollisionProcessor;
import com.jc.pong.listeners.GameContactListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GameScreen implements Screen, CollisionProcessor {

  //---------------------------------------------------------------------------
  // Finals - Static - Private
  //---------------------------------------------------------------------------

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
  GameContactListener contactListener;
  Queue<GameEvent> gameEvents = new LinkedList<GameEvent>();

  Paddle player1;
  Paddle player2;
  Sensor goal1;
  Sensor goal2;
  Puck puck;
  ArrayList walls;

  float width;
  float height;

  //---------------------------------------------------------------------------
  // Constructor
  //---------------------------------------------------------------------------

  public GameScreen(float width, float height) {
    this.width = width;
    this.height = height;

    world = new World(new Vector2(0, GRAVITY), true);
    debugRenderer = new Box2DDebugRenderer();
    contactListener = new GameContactListener(world, this);

    camera = new OrthographicCamera();
    camera.setToOrtho(false, width, height);

    debugMatrix = new Matrix4(camera.combined);
    debugMatrix.scale(BOX_TO_WORLD, BOX_TO_WORLD, 1f);

    shapeRenderer = new ShapeRenderer();

    player1 = new Paddle(world, createBoxVector(50f, height / 2f), createBoxVector(20f, 100f));
    player2 = new Paddle(world, createBoxVector(width - 50f, height / 2f), createBoxVector(20f, 100f));
    puck = new Puck(world, createBoxVector(width / 2f, height / 2f), createBoxDistance(10f));

    float padding = 10f;
    float thickness = 10f;
    float goalSize = 200f;
    float endWallLength = (height - goalSize - padding - thickness) / 2;
    Vector2[] vertices;

    walls = new ArrayList();

    // left goal sensor
    vertices = new Vector2[4];
    vertices[0] = createBoxVector(0f, 0f);
    vertices[1] = createBoxVector(thickness, 0f);
    vertices[2] = createBoxVector(thickness, goalSize);
    vertices[3] = createBoxVector(0f, goalSize);
    goal1 = new Sensor(world, createBoxVector(padding, padding + endWallLength), vertices);
    contactListener.addCollisionPair(goal1, puck);

    // right goal sensor
    vertices = new Vector2[4];
    vertices[0] = createBoxVector(0f, 0f);
    vertices[1] = createBoxVector(thickness, 0f);
    vertices[2] = createBoxVector(thickness, goalSize);
    vertices[3] = createBoxVector(0f, goalSize);
    goal2 = new Sensor(world, createBoxVector(width - padding - thickness, padding + endWallLength), vertices);
    contactListener.addCollisionPair(goal2, puck);

    // bottom wall
    vertices = new Vector2[4];
    vertices[0] = createBoxVector(0f, 0f);
    vertices[1] = createBoxVector(width - padding * 2f, 0f);
    vertices[2] = createBoxVector(width - padding * 2f, thickness);
    vertices[3] = createBoxVector(0f, thickness);
    walls.add(new StaticPolygon(world, createBoxVector(padding, padding), vertices));

    // bottom left wall
    vertices = new Vector2[4];
    vertices[0] = createBoxVector(0f, 0f);
    vertices[1] = createBoxVector(thickness, 0f);
    vertices[2] = createBoxVector(thickness, endWallLength);
    vertices[3] = createBoxVector(0f, endWallLength);
    walls.add(new StaticPolygon(world, createBoxVector(padding, padding), vertices));


    // bottom right wall
    vertices = new Vector2[4];
    vertices[0] = createBoxVector(0f, 0f);
    vertices[1] = createBoxVector(thickness, 0f);
    vertices[2] = createBoxVector(thickness, endWallLength);
    vertices[3] = createBoxVector(0f, endWallLength);
    walls.add(new StaticPolygon(world, createBoxVector(width - padding * 2, padding), vertices));

    // top wall
    vertices = new Vector2[4];
    vertices[0] = createBoxVector(0f, 0f);
    vertices[1] = createBoxVector(0f, -thickness);
    vertices[2] = createBoxVector(width - padding * 2f, -thickness);
    vertices[3] = createBoxVector(width - padding * 2f, 0f);
    walls.add(new StaticPolygon(world, createBoxVector(padding, height - padding), vertices));

    // top left wall
    vertices = new Vector2[4];
    vertices[0] = createBoxVector(0f, 0f);
    vertices[1] = createBoxVector(0f, -endWallLength);
    vertices[2] = createBoxVector(thickness, -endWallLength);
    vertices[3] = createBoxVector(thickness, 0f);
    walls.add(new StaticPolygon(world, createBoxVector(padding, height - padding), vertices));

    // top right wall
    vertices = new Vector2[4];
    vertices[0] = createBoxVector(0f, 0f);
    vertices[1] = createBoxVector(0f, -endWallLength);
    vertices[2] = createBoxVector(thickness, -endWallLength);
    vertices[3] = createBoxVector(thickness, 0f);
    walls.add(new StaticPolygon(world, createBoxVector(width - padding * 2, height - padding), vertices));

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

  public void reset() {
    player1.reset();
    player2.reset();
    puck.reset();
  }

  public void update(float delta) {
    float paddleSpeed = 500f;

    Vector2 paddleVelocity = new Vector2(0f, 0f);
    if (Gdx.input.isKeyPressed(Keys.W)) {
      paddleVelocity.y = createBoxDistance(paddleSpeed);
    } else if (Gdx.input.isKeyPressed(Keys.S)) {
      paddleVelocity.y = createBoxDistance(-paddleSpeed);
    }

    if (Gdx.input.isKeyPressed(Keys.D)) {
      paddleVelocity.x = createBoxDistance(paddleSpeed);
    } else if (Gdx.input.isKeyPressed(Keys.A)) {
      paddleVelocity.x = createBoxDistance(-paddleSpeed);
    }
    player1.getBody().setLinearVelocity(paddleVelocity);

    paddleVelocity = new Vector2(0f, 0f);
    if (Gdx.input.isKeyPressed(Keys.DPAD_UP)) {
      paddleVelocity.y = createBoxDistance(paddleSpeed);
    } else if (Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) {
      paddleVelocity.y = createBoxDistance(-paddleSpeed);
    }

    if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) {
      paddleVelocity.x = createBoxDistance(paddleSpeed);
    } else if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) {
      paddleVelocity.x = createBoxDistance(-paddleSpeed);
    }
    player2.getBody().setLinearVelocity(paddleVelocity);

    if (Gdx.input.isKeyPressed(Keys.R)) {
      reset();
    }

    world.step(delta, 6, 2);

    while (!gameEvents.isEmpty()) {
      switch (gameEvents.remove()) {
        case PLAYER1_SCORED:
          puck.reset();
          break;

        case PLAYER2_SCORED:
          puck.reset();
          break;
      }
    }
  }

  //---------------------------------------------------------------------------
  // Methods - CollisionProcessor Implementation
  //---------------------------------------------------------------------------

  public void processCollision(CollisionPair collisionPair)
  {
    if (collisionPair.targetEntity == goal1) {
      gameEvents.add(GameEvent.PLAYER2_SCORED);
    } else if (collisionPair.targetEntity == goal2) {
      gameEvents.add(GameEvent.PLAYER1_SCORED);
    }
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
