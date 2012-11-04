package com.android.angle;

/**
 * 2D vector
 * 
 * @author Ivan Pajuelo
 * 
 */
public class AngleVector
{
	private float mX;
	private float mY;

	public AngleVector()
	{
		setmX(0.0f);
		setmY(0.0f);
	}

	public AngleVector(float x, float y)
	{
		setmX(x);
		setmY(y);
	}

	public AngleVector(AngleVector src)
	{
		setmX(src.getmX());
		setmY(src.getmY());
	}

	public void set(AngleVector src)
	{
		setmX(src.getmX());
		setmY(src.getmY());
	}

	public void set(float x, float y)
	{
		setmX(x);
		setmY(y);
	}

	/*
	 * 
	 * public float length() { return (float) Math.sqrt((mX * mX) + (mY * mY)); }
	 * 
	 * public void normalize() { float len = length();
	 * 
	 * if (len != 0.0f) { mX /= len; mY /= len; } else { mX = 0.0f; mY = 0.0f; }
	 * }
	 */
	public void add(AngleVector vector)
	{
		setmX(getmX() + vector.getmX());
		setmY(getmY() + vector.getmY());
	}

	/*
	 * public void add(float x, float y) { x += x; y += y; }
	 */
	public void sub(AngleVector vector)
	{
		setmX(getmX() - vector.getmX());
		setmY(getmY() - vector.getmY());
	}

	public void subAt(AngleVector vector)
	{
		setmX(vector.getmX() - getmX());
		setmY(vector.getmY() - getmY());
	}

	/*
	 * public void sub(float x, float y) { x -= x; y -= y; }
	 * 
	 * public void mul(AngleVector vector) { mX *= vector.mX; mY *= vector.mY; }
	 * 
	 * public void mul(float x, float y) { x += x; y += y; }
	 */
	public void mul(float scalar)
	{
		setmX(getmX() * scalar);
		setmY(getmY() * scalar);
	}

	public float dot(AngleVector vector)
	{
		return (getmX() * vector.getmX()) + (getmY() * vector.getmY());
	}

	public void rotate(float dAlfa)
	{
		float nCos = (float) Math.cos(dAlfa);
		float nSin = (float) Math.sin(dAlfa);

		float iX = getmX() * nCos - getmY() * nSin;
		float iY = getmY() * nCos + getmX() * nSin;

		setmX(iX);
		setmX(iY);
	}

	public synchronized void setmY(float mY) {
		this.mY = mY;
	}

	public synchronized float getmY() {
		return mY;
	}

	public synchronized void setmX(float mX) {
		this.mX = mX;
	}

	public synchronized float getmX() {
		return mX;
	}

}
