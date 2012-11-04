package com.ntu.way2fungames.earthdefensebeta.weaponeffects;

import com.ntu.way2fungames.earthdefensebeta.objects.EdObject;

public interface EdWeaponEffect {
	public void AimAt(int x,int y);
	public void AimAt(EdObject atObj);
	public void TurnOn();
	public void TurnOff();
	public void AimAt( EdObject oSource, EdObject nearestTarget);
	
	
	
}