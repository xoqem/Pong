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
import com.jc.pong.listeners.CallbackFunction;
import com.jc.pong.listeners.GameContactListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GameScreen implements Screen {

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
  Matrix4 box2WorldMatrix;

  OrthographicCamera camera;
  ShapeRenderer shapeRenderer;
  GameContactListener contactListener;
  Queue<CallbackFunction> gameUpdateCallbacks = new LinkedList<CallbackFunction>();

  Paddle player1;
  Paddle player2;
  Sensor goal1;
  Sensor goal2;
  Puck puck;
  ArrayList<Entity> entities = new ArrayList<Entity>();

  float width;
  float height;

  int score1 = 0;
  int score2 = 0;

  boolean debug = false;

  //---------------------------------------------------------------------------
  // Constructor
  //---------------------------------------------------------------------------

  public GameScreen(float width, float height) {
    this.width = width;
    this.height = height;

    world = new World(new Vector2(0, GRAVITY), true);
    debugRenderer = new Box2DDebugRenderer();
    contactListener = new GameContactListener(world);

    camera = new OrthographicCamera();
    camera.setToOrtho(false, width, height);

    box2WorldMatrix = new Matrix4(camera.combined);
    box2WorldMatrix.scale(BOX_TO_WORLD, BOX_TO_WORLD, 1f);

    shapeRenderer = new ShapeRenderer();

    player1 = new Paddle(world, createBoxVector(50f, height / 2f), createBoxVector(20f, 100f));
    entities.add(player1);

    player2 = new Paddle(world, createBoxVector(width - 50f, height / 2f), createBoxVector(20f, 100f));
    entities.add(player2);

    puck = new Puck(world, createBoxVector(width / 2f, height / 2f), createBoxDistance(10f));
    entities.add(puck);

    float padding = 10f;
    float thickness = 10f;
    float goalSize = 200f;
    float endWallLength = (height - goalSize - padding - thickness) / 2;
    Vector2[] vertices;

    // left goal sensor
    vertices = new Vector2[4];
    vertices[0] = createBoxVector(0f, 0f);
    vertices[1] = createBoxVector(thickness, 0f);
    vertices[2] = createBoxVector(thickness, goalSize);
    vertices[3] = createBoxVector(0f, goalSize);
    goal1 = new Sensor(world, createBoxVector(padding, padding + endWallLength), vertices);
    contactListener.addCollisionPair(goal1, puck, new CallbackFunction() {
      public void execute() {
        gameUpdateCallbacks.add(new CallbackFunction() {
          public void execute() {
            score2++;
            puck.reset();
          }
        });
      }
    });

    // right goal sensor
    vertices = new Vector2[4];
    vertices[0] = createBoxVector(0f, 0f);
    vertices[1] = createBoxVector(thickness, 0f);
    vertices[2] = createBoxVector(thickness, goalSize);
    vertices[3] = createBoxVector(0f, goalSize);
    goal2 = new Sensor(world, createBoxVector(width - padding - thickness, padding + endWallLength), vertices);
    contactListener.addCollisionPair(goal2, puck, new CallbackFunction() {
      public void execute() {
        gameUpdateCallbacks.add(new CallbackFunction() {
          public void execute() {
            score1++;
            puck.reset();
          }
        });
      }
    });

    // bottom wall
    vertices = new Vector2[4];
    vertices[0] = createBoxVector(0f, 0f);
    vertices[1] = createBoxVector(width - padding * 2f, 0f);
    vertices[2] = createBoxVector(width - padding * 2f, thickness);
    vertices[3] = createBoxVector(0f, thickness);
    entities.add(new StaticPolygon(world, createBoxVector(padding, padding), vertices));

    // bottom left wall
    vertices = new Vector2[4];
    vertices[0] = createBoxVector(0f, 0f);
    vertices[1] = createBoxVector(thickness, 0f);
    vertices[2] = createBoxVector(thickness, endWallLength);
    vertices[3] = createBoxVector(0f, endWallLength);
    entities.add(new StaticPolygon(world, createBoxVector(padding, padding), vertices));


    // bottom right wall
    vertices = new Vector2[4];
    vertices[0] = createBoxVector(0f, 0f);
    vertices[1] = createBoxVector(thickness, 0f);
    vertices[2] = createBoxVector(thickness, endWallLength);
    vertices[3] = createBoxVector(0f, endWallLength);
    entities.add(new StaticPolygon(world, createBoxVector(width - padding * 2, padding), vertices));

    // top wall
    vertices = new Vector2[4];
    vertices[0] = createBoxVector(0f, 0f);
    vertices[1] = createBoxVector(0f, -thickness);
    vertices[2] = createBoxVector(width - padding * 2f, -thickness);
    vertices[3] = createBoxVector(width - padding * 2f, 0f);
    entities.add(new StaticPolygon(world, createBoxVector(padding, height - padding), vertices));

    // top left wall
    vertices = new Vector2[4];
    vertices[0] = createBoxVector(0f, 0f);
    vertices[1] = createBoxVector(0f, -endWallLength);
    vertices[2] = createBoxVector(thickness, -endWallLength);
    vertices[3] = createBoxVector(thickness, 0f);
    entities.add(new StaticPolygon(world, createBoxVector(padding, height - padding), vertices));

    // top right wall
    vertices = new Vector2[4];
    vertices[0] = createBoxVector(0f, 0f);
    vertices[1] = createBoxVector(0f, -endWallLength);
    vertices[2] = createBoxVector(thickness, -endWallLength);
    vertices[3] = createBoxVector(thickness, 0f);
    entities.add(new StaticPolygon(world, createBoxVector(width - padding * 2, height - padding), vertices));

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

    if (debug) {
      debugRenderer.render(world, box2WorldMatrix);
    } else {
      shapeRenderer.setProjectionMatrix(box2WorldMatrix);
      int i = 0;
      for (Entity entity : entities) {
        entity.render(shapeRenderer);
      }
    }
  }

  public void reset() {
    player1.reset();
    player2.reset();
    puck.reset();

    score1 = 0;
    score2 = 0;
  }

  public void update(float delta) {
    float paddleSpeed = 500f;

    // TODO: use a proper InputProcessor and move the key checks out of update
    
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

    if (Gdx.input.isKeyPressed(Keys.Z)) {
      debug = !debug;
    }

    world.step(delta, 6, 2);

    while (!gameUpdateCallbacks.isEmpty()) {
      gameUpdateCallbacks.remove().execute();
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
