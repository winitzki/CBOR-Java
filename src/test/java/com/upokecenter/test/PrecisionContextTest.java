package com.upokecenter.test;

import org.junit.Assert;
import org.junit.Test;
import com.upokecenter.util.*;

  public class PrecisionContextTest {
    @Test
    public void TestConstructor() {
      try {
        Assert.assertEquals(
          null,
          new PrecisionContext(-1, Rounding.HalfEven, 0, 0, false));
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        Assert.assertEquals(
          null,
          new PrecisionContext(0, Rounding.HalfEven, 0, -1, false));
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
    @Test
    public void TestAdjustExponent() {
      // not implemented yet
    }
    @Test
    public void TestClampNormalExponents() {
      // not implemented yet
    }
    @Test
    public void TestCopy() {
      // not implemented yet
    }
    @Test
    public void TestEMax() {
      PrecisionContext ctx = PrecisionContext.Unlimited;
      Assert.assertEquals(BigInteger.valueOf(0), ctx.getEMax());
      ctx = PrecisionContext.Unlimited.WithExponentRange(-5, 5);
      Assert.assertEquals(BigInteger.valueOf(5), ctx.getEMax());
    }
    @Test
    public void TestEMin() {
      PrecisionContext ctx = PrecisionContext.Unlimited;
      Assert.assertEquals(BigInteger.valueOf(0), ctx.getEMin());
      ctx = PrecisionContext.Unlimited.WithExponentRange(-5, 5);
      Assert.assertEquals(BigInteger.valueOf(-5), ctx.getEMin());
    }
    @Test
    public void TestExponentWithinRange() {
  if (!(PrecisionContext.Unlimited.ExponentWithinRange(BigInteger.fromString(
"-9999999"))))Assert.fail();

  if (!(PrecisionContext.Unlimited.ExponentWithinRange(BigInteger.fromString(
"9999999"))))Assert.fail();
      try {
 PrecisionContext.Unlimited.ExponentWithinRange(null);
Assert.fail("Should have failed");
} catch (NullPointerException ex) {
System.out.print("");
} catch (Exception ex) {
 Assert.fail(ex.toString());
throw new IllegalStateException("", ex);
}
    }
    @Test
    public void TestFlags() {
      PrecisionContext ctx = PrecisionContext.Unlimited;
      try {
        ctx.setFlags(5);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      ctx = ctx.WithBlankFlags();
      try {
        ctx.setFlags(5);
      } catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      ctx = ctx.WithNoFlags();
      try {
        ctx.setFlags(5);
        Assert.fail("Should have failed");
      } catch (IllegalStateException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }

    @Test
    public void TestCliDecimal() {
      ExtendedDecimal valueEdTmp;
      valueEdTmp = ExtendedDecimal.FromString(
"-79228162514264337593543950336").RoundToPrecision(PrecisionContext.CliDecimal);
      Assert.assertEquals(
        ExtendedDecimal.NegativeInfinity,
        valueEdTmp);
      valueEdTmp = ExtendedDecimal.FromString(
     "8.782580686213340724E+28").RoundToPrecision(PrecisionContext.CliDecimal);
      Assert.assertEquals(
        ExtendedDecimal.PositiveInfinity,
        valueEdTmp);
      Assert.assertEquals(
        ExtendedDecimal.NegativeInfinity,
        ExtendedDecimal.FromString(
        "-9.3168444507547E+28").RoundToPrecision(PrecisionContext.CliDecimal));
      {
        String stringTemp =

          ExtendedDecimal.FromString(
"-9344285899206687626894794544.04982268810272216796875").RoundToPrecision(PrecisionContext.CliDecimal)
          .ToPlainString();
        Assert.assertEquals(
          "-9344285899206687626894794544",
          stringTemp);
      }
      Assert.assertEquals(
        ExtendedDecimal.PositiveInfinity,
        ExtendedDecimal.FromString(
"96148154858060747311034406200").RoundToPrecision(PrecisionContext.CliDecimal));
      Assert.assertEquals(
        ExtendedDecimal.PositiveInfinity,
        ExtendedDecimal.FromString(
"90246605365627217170000000000").RoundToPrecision(PrecisionContext.CliDecimal));
    }

    @Test
    public void TestForPrecision() {
      // not implemented yet
    }
    @Test
    public void TestForPrecisionAndRounding() {
      // not implemented yet
    }
    @Test
    public void TestForRounding() {
      PrecisionContext ctx;
      ctx = PrecisionContext.ForRounding(Rounding.HalfEven);
      Assert.assertEquals(Rounding.HalfEven, ctx.getRounding());
      ctx = PrecisionContext.ForRounding(Rounding.HalfUp);
      Assert.assertEquals(Rounding.HalfUp, ctx.getRounding());
    }
    @Test
    public void TestHasExponentRange() {
      // not implemented yet
    }
    @Test
    public void TestHasFlags() {
      // not implemented yet
    }
    @Test
    public void TestHasMaxPrecision() {
      // not implemented yet
    }
    @Test
    public void TestIsPrecisionInBits() {
      // not implemented yet
    }
    @Test
    public void TestIsSimplified() {
      // not implemented yet
    }
    @Test
    public void TestPrecision() {
      // not implemented yet
    }
    @Test
    public void TestRounding() {
      // not implemented yet
    }
    @Test
    public void TestToString() {
      if (PrecisionContext.Unlimited.toString() == null) {
 Assert.fail();
 }
    }
    @Test
    public void TestTraps() {
      // not implemented yet
    }
    @Test
    public void TestWithAdjustExponent() {
      // not implemented yet
    }
    @Test
    public void TestWithBigExponentRange() {
      // not implemented yet
    }
    @Test
    public void TestWithBigPrecision() {
      try {
        PrecisionContext.Unlimited.WithBigPrecision(BigInteger.valueOf(-1));
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
    @Test
    public void TestWithBlankFlags() {
      // not implemented yet
    }
    @Test
    public void TestWithExponentClamp() {
      // not implemented yet
    }
    @Test
    public void TestWithExponentRange() {
      try {
        PrecisionContext.Unlimited.WithExponentRange(1, 0);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        PrecisionContext.Unlimited.WithBigExponentRange(null, BigInteger.valueOf(0));
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        PrecisionContext.Unlimited.WithBigExponentRange(BigInteger.valueOf(0), null);
        Assert.fail("Should have failed");
      } catch (NullPointerException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      try {
        BigInteger bigintBig = BigInteger.valueOf(1).shiftLeft(64);
        PrecisionContext.Unlimited.WithBigExponentRange(
          bigintBig,
          BigInteger.valueOf(0));
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
    }
    @Test
    public void TestWithNoFlags() {
      // not implemented yet
    }
    @Test
    public void TestWithPrecision() {
      try {
        PrecisionContext.Unlimited.WithPrecision(-1);
        Assert.fail("Should have failed");
      } catch (IllegalArgumentException ex) {
System.out.print("");
} catch (Exception ex) {
        Assert.fail(ex.toString());
        throw new IllegalStateException("", ex);
      }
      PrecisionContext ctx;
      ctx = PrecisionContext.Unlimited.WithPrecision(6);
      Assert.assertEquals(BigInteger.valueOf(6), ctx.getPrecision());
    }
    @Test
    public void TestWithPrecisionInBits() {
      // not implemented yet
    }
    @Test
    public void TestWithRounding() {
      // not implemented yet
    }
    @Test
    public void TestWithSimplified() {
      PrecisionContext pc = new PrecisionContext(0, Rounding.HalfUp, 0, 5, true);
      if (pc.isSimplified())Assert.fail();
      pc = pc.WithSimplified(true);
      if (!(pc.isSimplified()))Assert.fail();
      pc = pc.WithSimplified(false);
      if (pc.isSimplified())Assert.fail();
    }
    @Test
    public void TestWithTraps() {
      // not implemented yet
    }
    @Test
    public void TestWithUnlimitedExponents() {
      PrecisionContext pc = new PrecisionContext(0, Rounding.HalfUp, 0, 5, true);
      if (!(pc.getHasExponentRange()))Assert.fail();
      pc = pc.WithUnlimitedExponents();
      if (pc.getHasExponentRange())Assert.fail();
    }
  }
