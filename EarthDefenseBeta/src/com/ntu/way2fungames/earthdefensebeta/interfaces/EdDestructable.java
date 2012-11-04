package com.ntu.way2fungames.earthdefensebeta.interfaces;


public interface EdDestructable extends EdEnemyCanTarget {
boolean Destruct();
boolean TakeDamage(int nDmg);
boolean IsInMyBox(float x,float y);
boolean IsDead();
int GetBottomOfBox();
boolean IsGone();
boolean IsExploding();
float[] Intersect(float f, float g, float h, float i);
}
