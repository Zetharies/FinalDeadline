package controllers;

import com.badlogic.gdx.InputAdapter;

import models.EnumPlayerFacing;
import models.Player;

import com.badlogic.gdx.Input.Keys;

public class PlayerController extends InputAdapter {
	
	private Player p;
	private boolean up, down, left, right;
	
	public PlayerController(Player p) {
		this.p = p;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		
		if(keycode == Keys.UP || keycode == Keys.W) {
			up = true;
			//p.movePlayer(0, 1); // 0 on the x axis, 1 on the y axis
		}
		if(keycode == Keys.DOWN || keycode == Keys.S) {
			down = true;
			//p.movePlayer(0, -1); // 0 on the x axis, -1 on the y axis
		}
		if(keycode == Keys.LEFT || keycode == Keys.A) {
			left = true;
			//p.movePlayer(-1, 0); // -1 on the x axis, 0 on the y axis
		}
		if(keycode == Keys.RIGHT || keycode == Keys.D) {
			right = true;
			//p.movePlayer(1, 0); // 1 on the x axis, 0 on the y axis
		}
		
		return false;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		
		if(keycode == Keys.UP || keycode == Keys.W) {
			up = false;
			//p.movePlayer(0, 1); // 0 on the x axis, 1 on the y axis
		}
		if(keycode == Keys.DOWN || keycode == Keys.S) {
			down = false;
			//p.movePlayer(0, -1); // 0 on the x axis, -1 on the y axis
		}
		if(keycode == Keys.LEFT || keycode == Keys.A) {
			left = false;
			//p.movePlayer(-1, 0); // -1 on the x axis, 0 on the y axis
		}
		if(keycode == Keys.RIGHT || keycode == Keys.D) {
			right = false;
			//p.movePlayer(1, 0); // 1 on the x axis, 0 on the y axis
		}
		
		return false;
	}
	
	public void update(float delta) {
		if(up){
            p.move(EnumPlayerFacing.UP);
            return;
        }
        if(down){
            p.move(EnumPlayerFacing.DOWN);
            return;
        }
        if(left){
            p.move(EnumPlayerFacing.LEFT);
            return;
        }
        if(right){
            p.move(EnumPlayerFacing.RIGHT);
            return;
        }
	}

}
