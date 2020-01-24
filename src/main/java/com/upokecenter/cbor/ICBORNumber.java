package com.upokecenter.cbor;
/*
Written by Peter O. in 2013.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://peteroupc.github.io/
 */

import com.upokecenter.util.*;
import com.upokecenter.numbers.*;

  /**
   * This is an internal API.
   */
  interface ICBORNumber {
  /**
   * This is an internal API.
   * @param obj The parameter {@code obj} is an arbitrary object.
   * @return The return value is an internal value.
   */
    boolean IsPositiveInfinity(Object obj);

  /**
   * This is an internal API.
   * @param obj The parameter {@code obj} is an arbitrary object.
   * @return The return value is an internal value.
   */
    boolean IsInfinity(Object obj);

  /**
   * This is an internal API.
   * @param obj The parameter {@code obj} is an arbitrary object.
   * @return The return value is an internal value.
   */
    boolean IsNegativeInfinity(Object obj);

  /**
   * This is an internal API.
   * @param obj The parameter {@code obj} is an arbitrary object.
   * @return The return value is an internal value.
   */
    boolean IsNaN(Object obj);

  /**
   * This is an internal API.
   * @param obj The parameter {@code obj} is an arbitrary object.
   * @return The return value is an internal value.
   */
    boolean IsNegative(Object obj);

  /**
   * This is an internal API.
   * @param obj The parameter {@code obj} is an arbitrary object.
   * @return The return value is an internal value.
   */
    double AsDouble(Object obj);

  /**
   * This is an internal API.
   * @param obj The parameter {@code obj} is an arbitrary object.
   * @return The return value is an internal value.
   */
    Object Negate(Object obj);

  /**
   * This is an internal API.
   * @param obj The parameter {@code obj} is an arbitrary object.
   * @return The return value is an internal value.
   */
    Object Abs(Object obj);

  /**
   * This is an internal API.
   * @param obj The parameter {@code obj} is an arbitrary object.
   * @return The return value is an internal value.
   */
    EDecimal AsEDecimal(Object obj);

  /**
   * This is an internal API.
   * @param obj The parameter {@code obj} is an arbitrary object.
   * @return The return value is an internal value.
   */
    EFloat AsEFloat(Object obj);

  /**
   * This is an internal API.
   * @param obj The parameter {@code obj} is an arbitrary object.
   * @return The return value is an internal value.
   */
    ERational AsERational(Object obj);

  /**
   * This is an internal API.
   * @param obj The parameter {@code obj} is an arbitrary object.
   * @return The return value is an internal value.
   */
    float AsSingle(Object obj);

  /**
   * This is an internal API.
   * @param obj The parameter {@code obj} is an arbitrary object.
   * @return The return value is an internal value.
   */
    EInteger AsEInteger(Object obj);

  /**
   * This is an internal API.
   * @param obj The parameter {@code obj} is an arbitrary object.
   * @return The return value is an internal value.
   */
    long AsInt64(Object obj);

  /**
   * This is an internal API.
   * @param obj The parameter {@code obj} is an arbitrary object.
   * @return The return value is an internal value.
   */
    boolean CanFitInSingle(Object obj);

  /**
   * This is an internal API.
   * @param obj The parameter {@code obj} is an arbitrary object.
   * @return The return value is an internal value.
   */
    boolean CanFitInDouble(Object obj);

  /**
   * This is an internal API.
   * @param obj The parameter {@code obj} is an arbitrary object.
   * @return The return value is an internal value.
   */
    boolean CanFitInInt32(Object obj);

  /**
   * This is an internal API.
   * @param obj The parameter {@code obj} is an arbitrary object.
   * @return The return value is an internal value.
   */
    boolean CanFitInInt64(Object obj);

  /**
   * This is an internal API.
   * @param obj The parameter {@code obj} is an arbitrary object.
   * @return The return value is an internal value.
   */
    boolean CanTruncatedIntFitInInt64(Object obj);

  /**
   * This is an internal API.
   * @param obj The parameter {@code obj} is an arbitrary object.
   * @return The return value is an internal value.
   */
    boolean CanTruncatedIntFitInInt32(Object obj);

  /**
   * This is an internal API.
   * @return The return value is an internal value.
   */
    int AsInt32(Object obj, int minValue, int maxValue);

  /**
   * This is an internal API.
   * @param obj The parameter {@code obj} is an arbitrary object.
   * @return The return value is an internal value.
   */
    boolean IsNumberZero(Object obj);

  /**
   * This is an internal API.
   * @param obj The parameter {@code obj} is an arbitrary object.
   * @return The return value is an internal value.
   */
    int Sign(Object obj);

  /**
   * This is an internal API.
   * @param obj The parameter {@code obj} is an arbitrary object.
   * @return The return value is an internal value.
   */
    boolean IsIntegral(Object obj);
  }
