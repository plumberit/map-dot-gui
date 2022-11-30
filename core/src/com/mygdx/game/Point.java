package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Point {
    Texture ball;
    Pixmap origPic;
    Pixmap changedPic;
    int x;
    int y;
    int deltaX;
    int deltaY;
    boolean destroy;
    int counter = 1;
    boolean color;


    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void firstColor() {
        if(!color) {
            resizeDark();
            color = true;
        }
    }

    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            deltaX += 4;
            deltaY += 4;
            counter++;
            resizeDark();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            deltaX -= 4;
            deltaY -= 4;
            counter--;
            resizeDark();
            if(deltaX<10) {
                destroy = true;
            }
        }
    }

    public void resizeRed() {
        origPic = new Pixmap(Gdx.files.internal("ball.png"));
        changedPic = new Pixmap(deltaX, deltaY, origPic.getFormat());
        changedPic.drawPixmap(origPic,
                0, 0, origPic.getWidth(), origPic.getHeight(),
                0, 0, changedPic.getWidth(), changedPic.getHeight()
        );
        ball = new Texture(changedPic);
    }

    public void resizeDark() {
        origPic = new Pixmap(Gdx.files.internal("dark.png"));
        changedPic = new Pixmap(deltaX, deltaY, origPic.getFormat());
        changedPic.drawPixmap(origPic,
                0, 0, origPic.getWidth(), origPic.getHeight(),
                0, 0, changedPic.getWidth(), changedPic.getHeight()
        );
        ball = new Texture(changedPic);
    }

    public void dispose() {
        ball.dispose();
        origPic.dispose();
        changedPic.dispose();
    }
}