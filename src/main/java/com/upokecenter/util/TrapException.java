package com.upokecenter.util;
/*
Written in 2014 by Peter O.
Any copyright is dedicated to the Public Domain.
http://creativecommons.org/publicdomain/zero/1.0/
If you like this, you should donate to Peter O.
at: http://upokecenter.dreamhosters.com/articles/donate-now-2/
 */

import com.upokecenter.numbers.*;

    /**
     * Exception thrown for arithmetic trap errors.
     */
  public class TrapException extends ArithmeticException {
private static final long serialVersionUID = 1L;
    ETrapException ete;

    /**
     * Gets the precision context used during the operation that triggered the
     * trap. May be null.
     * @return The precision context used during the operation that triggered the
     * trap. May be null.
     */
    public final PrecisionContext getContext() {
        return new PrecisionContext(ete.getContext());
}

    /**
     * Gets the defined result of the operation that caused the trap.
     * @return The defined result of the operation that caused the trap.
     */
    public final Object getResult() {
        return ete.getResult();
}

    /**
     * Gets the flag that specifies the kind of error (PrecisionContext.FlagXXX).
     * This will only be one flag, such as FlagInexact or FlagSubnormal.
     * @return The flag that specifies the kind of error
     * (PrecisionContext.FlagXXX). This will only be one flag, such as
     * FlagInexact or FlagSubnormal.
     */
    public final int getError() {
        return ete.getError();
}

    private TrapException() {
 super();
    }

    static TrapException Create(ETrapException ete) {
      TrapException ex = new TrapException();
      ex.ete = ete;
      return ex;
    }

    /**
     * Initializes a new instance of the TrapException class.
     * @param flag A 32-bit signed integer.
     * @param ctx An EContext object.
     * @param result An arbitrary object.
     */
    public TrapException (int flag, PrecisionContext ctx, Object result) {
 super("");
      Object wrappedResult = result;
      EDecimal ed = ((result instanceof EDecimal) ? (EDecimal)result : null);
      ERational er = ((result instanceof ERational) ? (ERational)result : null);
      EFloat ef = ((result instanceof EFloat) ? (EFloat)result : null);
      if (ed != null) {
 wrappedResult = new ExtendedDecimal(ed);
}
      if (er != null) {
 wrappedResult = new ExtendedRational(er);
}
      if (ef != null) {
 wrappedResult = new ExtendedFloat(ef);
}
      ete = new ETrapException(flag, ctx == null ? null : ctx.ec,
        wrappedResult);
    }
  }
