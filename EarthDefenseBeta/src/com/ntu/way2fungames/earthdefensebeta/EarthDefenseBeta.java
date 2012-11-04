package com.ntu.way2fungames.earthdefensebeta;

import android.app.Application;
import android.util.DisplayMetrics;

public class EarthDefenseBeta extends Application{
	DisplayMetrics mDisplayMetrics; 

	public void setDisplayMetrics(DisplayMetrics metrics) {
		mDisplayMetrics = metrics;		
	}
	
	public DisplayMetrics getDisplayMetrics() {
		return mDisplayMetrics;		
	}
	
}
