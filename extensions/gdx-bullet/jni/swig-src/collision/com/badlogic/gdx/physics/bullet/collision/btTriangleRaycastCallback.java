/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

public class btTriangleRaycastCallback extends btTriangleCallback {
	private long swigCPtr;
	
	protected btTriangleRaycastCallback(final String className, long cPtr, boolean cMemoryOwn) {
		super(className, CollisionJNI.btTriangleRaycastCallback_SWIGUpcast(cPtr), cMemoryOwn);
		swigCPtr = cPtr;
	}
	
	/** Construct a new btTriangleRaycastCallback, normally you should not need this constructor it's intended for low-level usage. */
	public btTriangleRaycastCallback(long cPtr, boolean cMemoryOwn) {
		this("btTriangleRaycastCallback", cPtr, cMemoryOwn);
		construct();
	}
	
	@Override
	protected void reset(long cPtr, boolean cMemoryOwn) {
		if (!destroyed)
			destroy();
		super.reset(CollisionJNI.btTriangleRaycastCallback_SWIGUpcast(swigCPtr = cPtr), cMemoryOwn);
	}
	
	public static long getCPtr(btTriangleRaycastCallback obj) {
		return (obj == null) ? 0 : obj.swigCPtr;
	}

	@Override
	protected void finalize() throws Throwable {
		if (!destroyed)
			destroy();
		super.finalize();
	}

  @Override protected synchronized void delete() {
		if (swigCPtr != 0) {
			if (swigCMemOwn) {
				swigCMemOwn = false;
				CollisionJNI.delete_btTriangleRaycastCallback(swigCPtr);
			}
			swigCPtr = 0;
		}
		super.delete();
	}

  protected void swigDirectorDisconnect() {
    swigCMemOwn = false;
    delete();
  }

  public void swigReleaseOwnership() {
    swigCMemOwn = false;
    CollisionJNI.btTriangleRaycastCallback_change_ownership(this, swigCPtr, false);
  }

  public void swigTakeOwnership() {
    swigCMemOwn = true;
    CollisionJNI.btTriangleRaycastCallback_change_ownership(this, swigCPtr, true);
  }

  public void setFrom(btVector3 value) {
    CollisionJNI.btTriangleRaycastCallback_from_set(swigCPtr, this, btVector3.getCPtr(value), value);
  }

  public btVector3 getFrom() {
    long cPtr = CollisionJNI.btTriangleRaycastCallback_from_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btVector3(cPtr, false);
  }

  public void setTo(btVector3 value) {
    CollisionJNI.btTriangleRaycastCallback_to_set(swigCPtr, this, btVector3.getCPtr(value), value);
  }

  public btVector3 getTo() {
    long cPtr = CollisionJNI.btTriangleRaycastCallback_to_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btVector3(cPtr, false);
  }

  public void setFlags(long value) {
    CollisionJNI.btTriangleRaycastCallback_flags_set(swigCPtr, this, value);
  }

  public long getFlags() {
    return CollisionJNI.btTriangleRaycastCallback_flags_get(swigCPtr, this);
  }

  public void setHitFraction(float value) {
    CollisionJNI.btTriangleRaycastCallback_hitFraction_set(swigCPtr, this, value);
  }

  public float getHitFraction() {
    return CollisionJNI.btTriangleRaycastCallback_hitFraction_get(swigCPtr, this);
  }

  public btTriangleRaycastCallback(Vector3 from, Vector3 to, long flags) {
    this(CollisionJNI.new_btTriangleRaycastCallback__SWIG_0(from, to, flags), true);
    CollisionJNI.btTriangleRaycastCallback_director_connect(this, swigCPtr, swigCMemOwn, true);
  }

  public btTriangleRaycastCallback(Vector3 from, Vector3 to) {
    this(CollisionJNI.new_btTriangleRaycastCallback__SWIG_1(from, to), true);
    CollisionJNI.btTriangleRaycastCallback_director_connect(this, swigCPtr, swigCMemOwn, true);
  }

  public void processTriangle(btVector3 triangle, int partId, int triangleIndex) {
    if (getClass() == btTriangleRaycastCallback.class) CollisionJNI.btTriangleRaycastCallback_processTriangle(swigCPtr, this, btVector3.getCPtr(triangle), triangle, partId, triangleIndex); else CollisionJNI.btTriangleRaycastCallback_processTriangleSwigExplicitbtTriangleRaycastCallback(swigCPtr, this, btVector3.getCPtr(triangle), triangle, partId, triangleIndex);
  }

  public float reportHit(Vector3 hitNormalLocal, float hitFraction, int partId, int triangleIndex) {
    return CollisionJNI.btTriangleRaycastCallback_reportHit(swigCPtr, this, hitNormalLocal, hitFraction, partId, triangleIndex);
  }

  public final static class EFlags {
    public final static int kF_None = 0;
    public final static int kF_FilterBackfaces = 1 << 0;
    public final static int kF_KeepUnflippedNormal = 1 << 1;
    public final static int kF_UseSubSimplexConvexCastRaytest = 1 << 2;
    public final static int kF_Terminator = 0xFFFFFFFF;
  }

}
