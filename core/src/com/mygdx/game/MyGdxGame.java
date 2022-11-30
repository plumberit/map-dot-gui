package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MyGdxGame extends ApplicationAdapter {
	ArrayList<Point> list;
	SpriteBatch batch;
	Texture img;
	int nowX;
	int nowY;
	boolean letCreate;
	int counter;
	int deltaTime;
	boolean chose;
	int whatChoose;
	boolean pause;
	BitmapFont font;
	BitmapFont fontDigit;
	String number;
	boolean start;
	int listCounter;

	@Override
	public void create() {
		batch = new SpriteBatch();
		list = new ArrayList<>();
		img = new Texture("map.png");
		font = new BitmapFont(Gdx.files.internal("ads.fnt"),Gdx.files.internal("ads.png"),false);
		fontDigit = new BitmapFont(Gdx.files.internal("counter.fnt"),Gdx.files.internal("counter.png"),false);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);

		if(listCounter>600) {
			listCounter=0;

		} else {
			listCounter++;
		}

		if(!start) {
			//addToList();
			start = true;
		}

		drawingBalls();

		if(!chose) {
			font.draw(batch, "left click", 245, 860);
			font.draw(batch, "to add or", 240, 810);
			font.draw(batch, "change", 255, 760);
		}

		if (pause) {
			doPause();
			if (deltaTime > 10) {
				pause = false;
				deltaTime = 0;
			}
		} else {
			if (chose) {
				choose(whatChoose);
				drawInfo(whatChoose);
			} else {
				if (list.isEmpty()) {
					createFirstBall();
				} else {
					createNewBalls();
				}
				letCreateBalls();
				if (!pause) {
					chooseBalls();
				}

			}
		}
		batch.end();
	}
/*
	public void fillList() throws Exception {
		FileWriter wr = new FileWriter("text.txt", true);
		if (!list.isEmpty()) {
			wr.write("\n" + name + "\n" + description + "\n" + requirement);
		} else {
			wr.write(name + "\n" + description + "\n" + requirement);
		}
		wr.close();
	}*/

	public void drawingBalls() {
		if (!list.isEmpty()) { // not empty
			for (int i = 0; i < list.size(); i++) {
				batch.draw(list.get(i).ball, (float) (list.get(i).x - list.get(i).deltaX * 0.5), (float) (list.get(i).y - list.get(i).deltaY * 0.5));
			}
		}
	}

	public void createFirstBall() {
		if (Gdx.input.isTouched()) {
			Point point = new Point();
			point.setPosition(Gdx.input.getX(), 900 - Gdx.input.getY());
			point.deltaX = 10;
			point.deltaY = 10;
			point.resizeRed();
			list.add(point);
			pause = true;
		}
	}

	public void createNewBalls() {
		if (Gdx.input.isTouched()) {
			nowX = Gdx.input.getX();
			nowY = Gdx.input.getY();
			for (int i = 0; i < list.size(); i++) {
				if (!(nowX > list.get(i).x - 0.5*list.get(i).deltaX && nowX < (list.get(i).x + 0.5*list.get(i).deltaX)
						&& (900 - nowY) > list.get(i).y - 0.5*list.get(i).deltaY && (900 - nowY) < (list.get(i).y + 0.5*list.get(i).deltaY))) {
					counter++;
					if (counter == list.size()) {
						letCreate = true;
					}
				}
			}
			counter = 0;
		}
	}

	public void letCreateBalls() {
		if (letCreate) {
			Point point = new Point();
			point.setPosition(nowX, 900 - nowY);
			point.deltaX = 10;
			point.deltaY = 10;
			point.resizeRed();
			list.add(point);
			letCreate = false;
			pause = true;
		}
	}

	public void chooseBalls() {
		if (Gdx.input.isTouched()) {
			nowX = Gdx.input.getX();
			nowY = Gdx.input.getY();
			for (int i = 0; i < list.size(); i++) {
				if (nowX > list.get(i).x - list.get(i).deltaX * 0.5 && nowX < (list.get(i).x + list.get(i).deltaX * 0.5)
						&& (900 - nowY) > list.get(i).y - list.get(i).deltaY * 0.5 && (900 - nowY) < (list.get(i).y + list.get(i).deltaY * 0.5)) {
					whatChoose = i;
					chose = true;
				}
			}
		}
	}

	public void doPause() {
		deltaTime++;
	}

	public void choose(int whatChoose) {
		//list.get(whatChoose).resizeDark();
		list.get(whatChoose).firstColor();

		list.get(whatChoose).update();
		if (list.get(whatChoose).destroy) {
			list.remove(whatChoose);
			chose = false;
		}

		if (Gdx.input.isTouched()) {
			nowX = Gdx.input.getX();
			nowY = Gdx.input.getY();
			if(!(nowX > list.get(whatChoose).x - list.get(whatChoose).deltaX * 0.5 && nowX < (list.get(whatChoose).x + list.get(whatChoose).deltaX * 0.5)
					&& (900 - nowY) > list.get(whatChoose).y - list.get(whatChoose).deltaY * 0.5 && (900 - nowY) < (list.get(whatChoose).y + list.get(whatChoose).deltaY * 0.5))) {
				for (int i = 0; i <list.size() ; i++) {
					list.get(i).resizeRed();
				}
				chose = false;
			}
		}
	}

	public void drawInfo(int whatChoose) {
		if(!chose) {
			return;
		}
		number = Integer.toString(list.get(whatChoose).counter);
		fontDigit.draw(batch, number, 25, 880);
		font.draw(batch, "Press A to +", 240, 860);
		font.draw(batch, "Press F to -", 242, 810);
	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
		font.dispose();

		fontDigit.dispose();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).dispose();
		}
	}
}
