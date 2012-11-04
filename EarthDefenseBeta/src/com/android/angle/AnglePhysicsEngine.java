package com.android.angle;

/**
 * Simple pseudo-physic engine
 * 
 * @author Ivan Pajuelo
 * 
 */
public class AnglePhysicsEngine extends AngleObject
{
	public AngleVector mGravity;
	public float mViscosity;

	public AnglePhysicsEngine(int maxObjects)
	{
		super(maxObjects);
		mGravity = new AngleVector();
		mViscosity = 0;
	}

	protected void physics(float secondsElapsed)
	{
		for (int o = 0; o < mChildsCount; o++)
		{
			if (mChilds[o] instanceof AnglePhysicObject)
			{
				AnglePhysicObject mChildO = (AnglePhysicObject) mChilds[o];
				// Gravity
				mChildO.mVelocity.setmX(mChildO.mVelocity.getmX()
						+ (mChildO.mMass * mGravity.getmX() * secondsElapsed));
				mChildO.mVelocity.setmY(mChildO.mVelocity.getmY()
						+ (mChildO.mMass * mGravity.getmY() * secondsElapsed));
				if ((mChildO.mVelocity.getmX() != 0) || (mChildO.mVelocity.getmY() != 0))
				{
					// Air viscosity
					if (mViscosity > 0)
					{
						float surface = mChildO.getSurface();
						if (surface > 0)
						{
							float decay = surface * mViscosity * secondsElapsed;
							if (mChildO.mVelocity.getmX() > decay)
								mChildO.mVelocity.setmX(mChildO.mVelocity.getmX()
										- decay);
							else if (mChildO.mVelocity.getmX() < -decay)
								mChildO.mVelocity.setmX(mChildO.mVelocity.getmX()
										+ decay);
							else
								mChildO.mVelocity.setmX(0);
							if (mChildO.mVelocity.getmY() > decay)
								mChildO.mVelocity.setmY(mChildO.mVelocity.getmY()
										- decay);
							else if (mChildO.mVelocity.getmY() < -decay)
								mChildO.mVelocity.setmY(mChildO.mVelocity.getmY()
										+ decay);
							else
								mChildO.mVelocity.setmY(0);
						}
					}
				}
				// Velocity
				mChildO.mDelta.setmX(mChildO.mVelocity.getmX() * secondsElapsed);
				mChildO.mDelta.setmY(mChildO.mVelocity.getmY() * secondsElapsed);
			}
		}
	}

	/*
	 * protected void kynetics () { int steps=1;
	 * 
	 * for (int o=0;o<mChildsCount;o++) { int dX=(int)
	 * Math.abs(mChildO.mDelta.mX); int dY=(int) Math.abs(mChildO.mDelta.mX); if
	 * (dX>steps) steps=dX; if (dY>steps) steps=dY; }
	 * 
	 * for (int s=0;s<steps;s++) { for (int o=0;o<mChildsCount;o++) { if
	 * ((mChildO.mDelta.mX!=0)||(mChildO.mDelta.mY!=0)) { //Collision
	 * mChildO.mVisual.mCenter.mX+=mChildO.mDelta.mX/steps;
	 * mChildO.mVisual.mCenter.mY+=mChildO.mDelta.mY/steps; for (int
	 * c=0;c<mChildsCount;c++) { if (c!=o) { if (mChildO.collide(mChilds[c])) {
	 * mChildO.mVisual.mCenter.mX-=mChildO.mDelta.mX/steps;
	 * mChildO.mVisual.mCenter.mY-=mChildO.mDelta.mY/steps; mChildO.mDelta
	 * .mX=mChildO.mVelocity.mX*AngleMainEngine.secondsElapsed; mChildO.mDelta
	 * .mY=mChildO.mVelocity.mY*AngleMainEngine.secondsElapsed; mChilds[c].mDelta
	 * .mX=mChilds[c].mVelocity.mX*AngleMainEngine.secondsElapsed;
	 * mChilds[c].mDelta
	 * .mY=mChilds[c].mVelocity.mY*AngleMainEngine.secondsElapsed; break; } } } }
	 * } } }
	 */
	protected void kynetics(float secondsElapsed)
	{
		for (int o = 0; o < mChildsCount; o++)
		{
			if (mChilds[o] instanceof AnglePhysicObject)
			{
				AnglePhysicObject mChildO = (AnglePhysicObject) mChilds[o];
				if ((mChildO.mDelta.getmX() != 0) || (mChildO.mDelta.getmY() != 0))
				{
					// Collision
					mChildO.mPosition.setmX(mChildO.mPosition.getmX()
							+ mChildO.mDelta.getmX());
					mChildO.mPosition.setmY(mChildO.mPosition.getmY()
							+ mChildO.mDelta.getmY());
					for (int c = 0; c < mChildsCount; c++)
					{
						if (c != o)
						{
							if (mChilds[c] instanceof AnglePhysicObject)
							{
								AnglePhysicObject mChildC = (AnglePhysicObject) mChilds[c];
								if (mChildO.collide(mChildC))
								{
									mChildO.mPosition.setmX(mChildO.mPosition
											.getmX() - mChildO.mDelta.getmX());
									mChildO.mPosition.setmY(mChildO.mPosition
											.getmY() - mChildO.mDelta.getmY());
									mChildC.mDelta.setmX(mChildC.mVelocity.getmX() * secondsElapsed);
									mChildC.mDelta.setmY(mChildC.mVelocity.getmY() * secondsElapsed);
									break;
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void step(float secondsElapsed)
	{
		physics(secondsElapsed);
		kynetics(secondsElapsed);
		super.step(secondsElapsed);
	}
}
