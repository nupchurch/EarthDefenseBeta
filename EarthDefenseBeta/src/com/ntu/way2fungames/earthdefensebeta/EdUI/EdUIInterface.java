package com.ntu.way2fungames.earthdefensebeta.EdUI;

import com.android.angle.AngleSurfaceView;

public interface EdUIInterface {
	public void AddToAngle(AngleSurfaceView mGLSurfaceView, int x, int y);
	public boolean PendingPress();
	public void UsePendingPress();
	public void UpdateGFX();
	public void ButtonPess();
}
