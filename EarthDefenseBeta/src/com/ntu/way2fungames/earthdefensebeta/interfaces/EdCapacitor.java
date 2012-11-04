package com.ntu.way2fungames.earthdefensebeta.interfaces;

import com.ntu.way2fungames.earthdefensebeta.R;

public interface EdCapacitor {
	int Sprite1 = R.drawable.ed_glowcap;
	float   CapEnergyIn(float nEnergy);
	float   CapEnergyOut(float nEnergy);
	float   CapGetMaxEnergyIn();
	float   CapGetMaxEnergy();
}
