package com.ntu.way2fungames.earthdefensebeta.interfaces;

import com.ntu.way2fungames.earthdefensebeta.objects.EdObject;

public interface EdUpgradeable {
String[] GetUpgradeButtons();
int[]    GetUpgradeIcons();
int[]    GetUpgradeCosts();
int[]    GetUpgradeLvls();

EdObject  Upgrade(int UpgradeIdx);
int  GetUpgradeCost(int idx);
}
