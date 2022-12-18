package io.github.teamten5.views;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.teamten5.Team15Game;

public class MenuScreen implements Screen {

        private Stage stage;
        private Team15Game parent;

        public MenuScreen(Team15Game team15game){
                parent = team15game;
                stage = new Stage(new ScreenViewport());
                Gdx.input.setInputProcessor(stage);
        }
        @Override
        public void show() {

                Table table = new Table();
                table.setFillParent(true);
                table.setDebug(true);
                stage.addActor(table);


                //temporary skin
                Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

                TextButton startGame = new TextButton("START", skin);
                TextButton exit = new TextButton("Exit", skin);

                table.add(startGame).fillX().uniformX();
                table.row().pad(20,0,10,0);
                table.row();
                table.add(exit).fillX().uniformX();


                exit.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                                Gdx.app.exit();
                        }
                });


        }
        @Override
        public void render(float delta) {
            // TODO Auto-generated method stub
        }
        @Override
        public void resize(int width, int height) {
                // change the stage's viewport when teh screen size is changed
                stage.getViewport().update(width, height, true);

        }
        @Override
        public void pause() {
            // TODO Auto-generated method stub
        }
        @Override
        public void resume() {
            // TODO Auto-generated method stub
        }
        @Override
        public void hide() {
            // TODO Auto-generated method stub
        }
        @Override
        public void dispose() {
            stage.dispose();
        }

    }

