package com.ntu.way2fungames.earthdefensebeta.objects;

import android.util.Log;
import android.widget.Toast;

import com.ntu.way2fungames.earthdefensebeta.R;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdUpgradeable;
import com.ntu.way2fungames.earthdefensebeta.weapons.EdRadiationFlinger;
import com.ntu.way2fungames.earthdefensebeta.weapons.EdRailGun;
import com.ntu.way2fungames.earthdefensebeta.weapons.EdTriGun;
import com.ntu.way2fungames.earthdefensebeta.weapons.EdWeapon;

public class EdStructure extends EdObject implements EdUpgradeable {
	
	

	public EdStructure(String structureConnections) {
		restructure(structureConnections);		
	}
	
	
	public void restructure(String structureConnections){
		if (structureConnections.contentEquals("1234")){SpritesIDs[0] = R.drawable.ed_st_n_1234;}
		if (structureConnections.contentEquals("123")){SpritesIDs[0] = R.drawable.ed_st_n_123;}
		if (structureConnections.contentEquals("124")){SpritesIDs[0] = R.drawable.ed_st_n_124;}
		if (structureConnections.contentEquals("134")){SpritesIDs[0] = R.drawable.ed_st_n_134;}
		if (structureConnections.contentEquals("234")){SpritesIDs[0] = R.drawable.ed_st_n_234;}
		
		if (structureConnections.contentEquals("12")){SpritesIDs[0] = R.drawable.ed_st_n_12;}
		if (structureConnections.contentEquals("23")){SpritesIDs[0] = R.drawable.ed_st_n_23;}
		if (structureConnections.contentEquals("34")){SpritesIDs[0] = R.drawable.ed_st_n_34;}
		if (structureConnections.contentEquals("14")){SpritesIDs[0] = R.drawable.ed_st_n_14;}
		
		if (structureConnections.contentEquals("13")){SpritesIDs[0] = R.drawable.ed_st_n_13;}
		if (structureConnections.contentEquals("24")){SpritesIDs[0] = R.drawable.ed_st_n_24;}
		
		if (structureConnections.contentEquals("1")){SpritesIDs[0] = R.drawable.ed_st_n_1;}
		if (structureConnections.contentEquals("2")){SpritesIDs[0] = R.drawable.ed_st_n_2;}
		if (structureConnections.contentEquals("3")){SpritesIDs[0] = R.drawable.ed_st_n_3;}
		if (structureConnections.contentEquals("4")){SpritesIDs[0] = R.drawable.ed_st_n_4;}

		
		if (structureConnections==""){SpritesIDs[0] = R.drawable.ed_st_n_1234;}
		if (this instanceof EdWeapon){
			Log.v("way2fungames","-=====< Weapon >=====-" );
			Log.v("way2fungames","-=====< "+ structureConnections+" >=====-" );
		}
		Log.v("way2fungames","-=====<"+ SpritesIDs[0] + ">=====-" );

	}
	
	@Override
	public int GetSpriteResId(int whatLayer) {
		if( whatLayer == 0) {return SpritesIDs[0];}else{return 0;}
		 
	}

	@Override
	public int GetUpgradeCost(int idx) {
		
		return GetUpgradeCosts()[idx];
	}

	@Override
	public int[] GetUpgradeCosts() {
		return new int[]{50,100,200};
	}

	@Override
	public int[] GetUpgradeLvls() {
		return new int[]{-1,-1,-1};
	}

	@Override
	public String[] GetUpgradeButtons() {
		return new String[]{"Railgun","Trigun","Radiation Cannon"};
	}

	@Override
	public EdObject Upgrade(int UpgradeIdx) {
		if (UpgradeIdx==0){return new EdRailGun();}
		if (UpgradeIdx==1){return new EdTriGun();}
		if (UpgradeIdx==2){return new EdRadiationFlinger("1234");}
		return null;
	}

	@Override
	public int[] GetUpgradeIcons() {
		return new int[]{R.drawable.ed_plaincap,R.drawable.ed_plaincap,R.drawable.ed_plaincap};
	}
}
