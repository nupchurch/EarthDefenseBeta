package com.ntu.way2fungames.earthdefensebeta.interfaces;


public interface EdBuildable extends EdEnemyCanTarget {
float GetBuildCost();
boolean BuildAt(int x,int y);
}
