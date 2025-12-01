package com.Lissajous_Curves2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Simulation extends ApplicationAdapter {

    public ShapeRenderer shapeRenderer;
    public OrthographicCamera camera;
    public Viewport viewport;
    public UIStage uiStage;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(1800, 1200, camera);
        camera.setToOrtho(false, 1800, 1200);
        camera.update();
        uiStage = new UIStage(1800, 1200);
        Gdx.input.setInputProcessor(uiStage);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1f);

        drawCurves();

        uiStage.act();
        uiStage.draw();

        System.out.println(Gdx.graphics.getFramesPerSecond());

    }

    @Override
    public void resize (int width, int height) {
        viewport.update(width, height);
        uiStage.getViewport().update(width, height, true);
    }

    public void drawCurves(){
        float lineWidth = 4f;
        Gdx.gl.glLineWidth(lineWidth);
        shapeRenderer.setProjectionMatrix(camera.combined);
        viewport.apply();
        shapeRenderer.setAutoShapeType(true);

        float t = 0;
        float max_t = 100;
        float stepScale = 1f;
        float step = 1f/uiStage.getResolution() * stepScale;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0.7f, 0.7f, 0.7f, 1f);
        Vector2 lastPoint = getPoint(t);
        while(t <= max_t){
            t += step;
            Vector2 newPoint = getPoint(t);
            shapeRenderer.line(lastPoint, newPoint);
            lastPoint = new Vector2(newPoint.x, newPoint.y);
        }

        shapeRenderer.end();
    }

    private Vector2 getPoint(float t){
        float scale = 50f;
        float offsetX = 1200f;
        float offsetY = 600f;
        float x = (float) (uiStage.getA()*Math.sin(uiStage.geta()*t + uiStage.getd()));
        float y = (float) (uiStage.getB()*Math.sin(uiStage.getb()*t));
        x = x*scale + offsetX;
        y = y*scale + offsetY;
        return new Vector2(x, y);
    }
}
