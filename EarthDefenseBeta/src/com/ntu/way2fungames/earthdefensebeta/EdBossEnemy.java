package com.ntu.way2fungames.earthdefensebeta;

import com.ntu.way2fungames.earthdefensebeta.interfaces.EdDestructable;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdEnemy;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdFireable;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdThreeLayers;
import com.ntu.way2fungames.earthdefensebeta.objects.EdObject;
import com.ntu.way2fungames.earthdefensebeta.weaponeffects.EdWeaponEffect;

public class EdBossEnemy extends EdObject implements EdDestructable, EdEnemy, EdFireable, EdThreeLayers{

	@Override
	public boolean Destruct() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int GetBottomOfBox() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean IsDead() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean IsExploding() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean IsGone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean IsInMyBox(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean TakeDamage(int nDmg) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float GetTargetValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int GetDmg() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int GetHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int GetLvl() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int GetValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getColor(int rORgORb) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public EdDestructable[] CheckForHit(EdDestructable[] targets) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean Fire(EdObject nearestTarget) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void FireAutoMode(boolean turnOn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int GetSpriteResId(int whatLayer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean DoAIStep(EdObject edObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setWeaponEffect(EdWeaponEffect wEff) {
		// TODO Auto-generated method stub
		
	}

	

}
