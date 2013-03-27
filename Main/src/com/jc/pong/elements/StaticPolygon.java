package com.jc.pong.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class StaticPolygon extends Entity{

  //---------------------------------------------------------------------------
  // Variables - Private
  //--------------------------------------------------------------------------

  Vector2[] vertices;

  //---------------------------------------------------------------------------
  // Constructor
  //---------------------------------------------------------------------------

  public StaticPolygon(World world, Vector2 position, Vector2[] vertices) {
    super(world, position, BodyType.StaticBody);

    this.vertices = vertices;
    createBody();
  }

  //---------------------------------------------------------------------------
  // Methods - Public - Overrides
  //---------------------------------------------------------------------------

  @Override
  public void render(ShapeRenderer shapeRenderer) {
    Vector2 position = body.getPosition();
    Vector2 prev = null;
    Vector2 first = null;
    shapeRenderer.begin(ShapeType.Line);
    for (int i = 0; i < vertices.length; i++) {
      Vector2 curr = vertices[i].cpy().add(position);
      if (prev != null) {
        shapeRenderer.line(prev.x, prev.y, curr.x, curr.y);
      } else {
        first = curr;
      }
      prev = curr;
    }
    shapeRenderer.line(prev.x, prev.y, first.x, first.y);
    shapeRenderer.end();
  }

  //---------------------------------------------------------------------------
  // Methods - Entity Implementation
  //---------------------------------------------------------------------------

  protected FixtureDef createFixtureDef() {
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.density = 0f;
    fixtureDef.friction = 0f;
    fixtureDef.restitution = 0f;
    return fixtureDef;
  }

  protected Shape createShape() {
    PolygonShape polygon = new PolygonShape();
    polygon.set(vertices);
    return polygon;
  }
}
