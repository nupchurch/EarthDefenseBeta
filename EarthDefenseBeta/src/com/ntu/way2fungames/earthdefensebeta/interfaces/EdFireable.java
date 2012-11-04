package com.ntu.way2fungames.earthdefensebeta.interfaces;

import com.ntu.way2fungames.earthdefensebeta.objects.EdObject;
import com.ntu.way2fungames.earthdefensebeta.weaponeffects.EdWeaponEffect;

public interface EdFireable {
	
	void FireAutoMode(boolean turnOn);
	EdDestructable[] CheckForHit(EdDestructable[] targets);
	boolean Fire(EdObject nearestTarget);
	void setWeaponEffect(EdWeaponEffect wEff);
	
}
