package com.ntu.way2fungames.earthdefensebeta.weapons;

import com.ntu.way2fungames.earthdefensebeta.objects.EdObject;
import com.ntu.way2fungames.earthdefensebeta.objects.EdStructure;

public class EdWeapon extends EdStructure{
	public EdWeapon(String structureConnections) {
		super(structureConnections);
	}

	protected boolean autoMode= false;
	
	public void SetAutoMode(boolean onoff){
		autoMode = onoff;
	}
	
}
