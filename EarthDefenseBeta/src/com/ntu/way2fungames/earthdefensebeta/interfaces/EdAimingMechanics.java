package com.ntu.way2fungames.earthdefensebeta.interfaces;

import com.ntu.way2fungames.earthdefensebeta.objects.EdObject;

public interface EdAimingMechanics extends  EdUpgradeable {
boolean AimAt(float x, float y,float aimspeed);
//boolean AIAim(EdEnemy[] huh);
boolean AIAim(EdObject[] AllObjs);
}
