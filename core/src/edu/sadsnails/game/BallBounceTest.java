package edu.sadsnails.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Random;

/**
 * Created by Parker on 2/22/2017.
 */
public class BallBounceTest implements Screen {
    MyGdxGame game;

    private Skin skin;
    private Stage stage;

    private ShapeRenderer shapeRenderer;

    private Container<Label> fpsCon;
    private Label fpsText;

    private float ballX, ballY;
    private int ballVel;
    private boolean ballXDir, ballYDir;
    private double fps = 60.0;

    public BallBounceTest(final MyGdxGame game) {
        System.out.println("Created BallBounceTest");

        this.game = game;

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Setup FPS with Container
        fpsText = new Label("FPS: " + Double.toString(fps) ,skin);
        fpsCon = new Container<Label>(fpsText);
        fpsCon.debug();
        fpsCon.setBounds(10, Gdx.graphics.getHeight()-30, fpsText.getWidth(), fpsText.getHeight());

        shapeRenderer = new ShapeRenderer();

        ballX = MathUtils.random(32, Gdx.graphics.getWidth()-32);
        ballY = MathUtils.random(32, Gdx.graphics.getHeight()-32);
        ballVel = 300;
        ballXDir = MathUtils.randomBoolean(.5f);
        ballYDir = MathUtils.randomBoolean(.5f);

        // Add the FPS and Table to the Stage
        stage.addActor(fpsCon);

        fps = 0.0;
    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.2f, .2f, .2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update the FPS Counter
        fpsText.setText("FPS: " + Double.toString(fps));

        // Draw The Scene2d stage
        stage.act();
        stage.draw();

        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle((int)ballX, (int)ballY, 32);
        shapeRenderer.end();

        // Increment ball travel
        if(ballXDir)
            ballX += ballVel*delta;
        else
            ballX -= ballVel*delta;
        if(ballYDir)
            ballY += ballVel*delta;
        else
            ballY -= ballVel*delta;

        // When on edgy, b o u n c e
        if(ballX+32 > Gdx.graphics.getWidth() || ballX-32 < 0)
            ballXDir = !ballXDir;
        if(ballY+32 > Gdx.graphics.getHeight() || ballY-32 < 0)
            ballYDir = !ballYDir;

        fps = (int)(1/delta);

        // Return to the main menu
        if(Gdx.input.isKeyPressed(Keys.BACKSPACE) || Gdx.input.isKeyPressed(Keys.ESCAPE)){
            game.setScreen(new MainMenuScreen(game));
            this.dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        fpsCon.setX(10);
        fpsCon.setY(height-30);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        System.out.println("Disposing Ball Bounce");

        shapeRenderer.dispose();
        stage.dispose();
    }

}
