package com.ntu.way2fungames.earthdefensebeta.interfaces;

import com.ntu.way2fungames.earthdefensebeta.objects.EdObject;

public interface EdEnemy {
int GetValue();
int GetHealth();
int GetDmg();
int GetLvl();
float getColor(int rORgORb);
boolean DoAIStep(EdObject edObject);

}
