package com.upokecenter.cbor;
/*
Written by Peter O.
Any copyright to this work is released to the Public Domain.
In case this is not possible, this work is also
licensed under Creative Commons Zero (CC0):
https://creativecommons.org/publicdomain/zero/1.0/

 */

import com.upokecenter.numbers.*;

  final class CBORDataUtilitiesTextString {
private CBORDataUtilitiesTextString() {
}
    private static final long DoubleNegInfinity = (0xfffL << 52);
    private static final long DoublePosInfinity = (0x7ffL << 52);

    static CBORObject ParseJSONNumber(
      String chars,
      int offset,
      int count,
      JSONOptions options,
      int[] endOfNumber) {
      if (chars == null || chars.length() == 0 || count <= 0) {
        return null;
      }
      if (offset < 0 || offset > chars.length()) {
        return null;
      }
      if (count > chars.length() || chars.length() - offset < count) {
        return null;
      }

      options = (options == null) ? (CBORDataUtilities.DefaultOptions) : options;
      boolean preserveNegativeZero = options.getPreserveNegativeZero();
      JSONOptions.ConversionMode kind = options.getNumberConversion();
      int endPos = offset + count;
      int initialOffset = offset;
      boolean negative = false;
      if (chars.charAt(initialOffset) == '-') {
        ++offset;
        negative = true;
      }
      int numOffset = offset;
      boolean haveDecimalPoint = false;
      boolean haveDigits = false;
      boolean haveDigitsAfterDecimal = false;
      boolean haveNonzeroDigits = false;
      boolean haveExponent = false;
      int i = offset;
      int decimalPointPos = -1;
      // Check syntax
      int k = i;
      if (endPos - 1 > k && chars.charAt(k) == '0' && chars.charAt(k + 1) >= '0' &&
        chars.charAt(k + 1) <= '9') {
        if (endOfNumber != null) {
          endOfNumber[0] = k + 2;
        }
        return null;
      }
      for (; k < endPos; ++k) {
        char c = chars.charAt(k);
        if (c == '0') {
          haveDigits = true;
          haveDigitsAfterDecimal |= haveDecimalPoint;
        } else if (c >= '1' && c <= '9') {
          haveDigits = true;
          haveDigitsAfterDecimal |= haveDecimalPoint;
          haveNonzeroDigits = true;
        } else if (c == '.') {
          if (!haveDigits || haveDecimalPoint) {
            // no digits before the decimal point,
            // or decimal point already seen
            if (endOfNumber != null) {
              endOfNumber[0] = k;
            }
            return null;
          }
          haveDecimalPoint = true;
          decimalPointPos = k;
        } else if (c == 'E' || c == 'e') {
          ++k;
          haveExponent = true;
          break;
        } else {
          if (endOfNumber != null) {
            endOfNumber[0] = k;
            // Check if character can validly appear after a JSON number
            if (c != ',' && c != ']' && c != '}' &&
              c != 0x20 && c != 0x0a && c != 0x0d && c != 0x09) {
              return null;
            } else {
              endPos = k;
              break;
            }
          }
          return null;
        }
      }
      if (!haveDigits || (haveDecimalPoint && !haveDigitsAfterDecimal)) {
        if (endOfNumber != null) {
          endOfNumber[0] = k;
        }
        return null;
      }
      int exponentPos = -1;
      boolean negativeExp = false;
      if (haveExponent) {
        haveDigits = false;
        if (k == endPos) {
          if (endOfNumber != null) {
            endOfNumber[0] = k;
          }
          return null;
        }
        char c = chars.charAt(k);
        if (c == '+') {
          ++k;
        } else if (c == '-') {
          negativeExp = true;
          ++k;
        }
        for (; k < endPos; ++k) {
          c = chars.charAt(k);
          if (c >= '0' && c <= '9') {
            if (exponentPos < 0 && c != '0') {
              exponentPos = k;
            }
            haveDigits = true;
          } else if (endOfNumber != null) {
            endOfNumber[0] = k;
            // Check if character can validly appear after a JSON number
            if (c != ',' && c != ']' && c != '}' &&
              c != 0x20 && c != 0x0a && c != 0x0d && c != 0x09) {
              return null;
            } else {
              endPos = k;
              break;
            }
          } else {
            return null;
          }
        }
        if (!haveDigits) {
          if (endOfNumber != null) {
            endOfNumber[0] = k;
          }
          return null;
        }
      }
      if (endOfNumber != null) {
        endOfNumber[0] = endPos;
      }
      if (exponentPos >= 0 && endPos - exponentPos > 20) {
        // Exponent too high for precision to overcome (which
        // has a length no bigger than Integer.MAX_VALUE, which is 10 digits
        // long)
        if (!haveNonzeroDigits) {
          // zero
          if (kind == JSONOptions.ConversionMode.Double) {
            return !negative ? CBORObject.FromFloatingPointBits(0, 2) :
CBORObject.FromFloatingPointBits(0x8000, 2);
          } else if (kind == JSONOptions.ConversionMode.IntOrFloatFromDouble ||
            kind == JSONOptions.ConversionMode.IntOrFloat) {
            return CBORObject.FromObject(0);
          }
        } else if (negativeExp) {
          // underflow
          if (kind == JSONOptions.ConversionMode.Double ||
            kind == JSONOptions.ConversionMode.IntOrFloat) {
            return !negative ? CBORObject.FromFloatingPointBits(0, 2) :
CBORObject.FromFloatingPointBits(0x8000, 2);
          } else if (kind == JSONOptions.ConversionMode.IntOrFloatFromDouble) {
            return CBORObject.FromObject(0);
          }
        } else {
          // overflow
          if (kind == JSONOptions.ConversionMode.Double ||
            kind == JSONOptions.ConversionMode.IntOrFloatFromDouble ||
            kind == JSONOptions.ConversionMode.IntOrFloat) {
            return CBORObject.FromFloatingPointBits(
                negative ? DoubleNegInfinity : DoublePosInfinity,
                8);
          } else if (kind == JSONOptions.ConversionMode.Decimal128) {
            return CBORObject.FromObject(negative ?
                EDecimal.NegativeInfinity : EDecimal.PositiveInfinity);
          }
        }
      }
      if (!haveExponent && !haveDecimalPoint &&
        (endPos - numOffset) <= 16) {
        // Very common case of all-digit JSON number strings
        // less than 2^53 (with or without number sign)
        long v = 0L;
        int vi = numOffset;
        for (; vi < endPos; ++vi) {
          v = (v * 10) + (chars.charAt(vi) - '0');
        }
        if ((v != 0 || !negative) && v < (1L << 53) - 1) {
          if (negative) {
            v = -v;
          }
          return kind == JSONOptions.ConversionMode.Double
            ?
CBORObject.FromFloatingPointBits(EFloat.FromInt64(v).ToDoubleBits(), 8) :
            kind == JSONOptions.ConversionMode.Decimal128 ?
CBORObject.FromObject(EDecimal.FromInt64(v)) : CBORObject.FromObject(v);
        }
      }
      if (kind == JSONOptions.ConversionMode.Full) {
        if (!haveDecimalPoint && !haveExponent) {
          EInteger ei = EInteger.FromSubstring(chars, initialOffset, endPos);
          return (preserveNegativeZero && ei.isZero() && negative) ?
CBORObject.FromObject(EDecimal.NegativeZero) :
CBORObject.FromObject(ei);
        }
        if (!haveExponent && haveDecimalPoint) {
          // No more than 18 digits plus one decimal point (which
          // should fit a long)
          long lv = 0L;
          int expo = -(endPos - (decimalPointPos + 1));
          int vi = numOffset;
          int digitCount = 0;
          for (; vi < decimalPointPos; ++vi) {
            if (digitCount < 0 || digitCount >= 18) {
              digitCount = -1;
              break;
            } else if (digitCount > 0 || chars.charAt(vi) != '0') {
              ++digitCount;
            }
            lv = ((lv * 10) + (chars.charAt(vi) - '0'));
          }
          for (vi = decimalPointPos + 1; vi < endPos; ++vi) {
            if (digitCount < 0 || digitCount >= 18) {
              digitCount = -1;
              break;
            } else if (digitCount > 0 || chars.charAt(vi) != '0') {
              ++digitCount;
            }
            lv = ((lv * 10) + (chars.charAt(vi) - '0'));
          }
          if (negative) {
            lv = -lv;
          }
          if (digitCount >= 0 && (!negative || lv != 0)) {
            if (expo == 0) {
              return CBORObject.FromObject(lv);
            } else {
              CBORObject cbor = CBORObject.FromArrayBackedObject(
              new CBORObject[] {
                CBORObject.FromObject(expo),
                CBORObject.FromObject(lv),
              });
              return cbor.WithTag(4);
            }
          }
        }
        // System.out.println("convfull " + chars.substring(initialOffset,(initialOffset)+(endPos -
        // initialOffset)));
        EDecimal ed = EDecimal.FromString(
            chars,
            initialOffset,
            endPos - initialOffset);
        return ed.isZero() && negative ? ed.getExponent().isZero() ?
            preserveNegativeZero ?
              CBORObject.FromObject(EDecimal.NegativeZero) :
              CBORObject.FromObject(0) :
            !preserveNegativeZero ? CBORObject.FromObject(ed.Negate()) :
CBORObject.FromObject(ed) :
          ed.getExponent().isZero() ? CBORObject.FromObject(ed.getMantissa()) :
            CBORObject.FromObject(ed);
      } else if (kind == JSONOptions.ConversionMode.Double) {
        EFloat ef = EFloat.FromString(
            chars,
            initialOffset,
            endPos - initialOffset,
            EContext.Binary64);
        long lb = ef.ToDoubleBits();
        if (!preserveNegativeZero && (lb == 1L << 63 || lb == 0L)) {
          lb = 0L;
        }
        return CBORObject.FromFloatingPointBits(lb, 8);
      } else if (kind == JSONOptions.ConversionMode.Decimal128) {
        EDecimal ed = EDecimal.FromString(
            chars,
            initialOffset,
            endPos - initialOffset,
            EContext.Decimal128);
        if (!preserveNegativeZero && ed.isNegative() && ed.isZero()) {
          ed = ed.Negate();
        }
        return CBORObject.FromObject(ed);
      } else if (kind == JSONOptions.ConversionMode.IntOrFloatFromDouble) {
        EFloat ef = EFloat.FromString(
            chars,
            initialOffset,
            endPos - initialOffset,
            EContext.Binary64);
        long lb = ef.ToDoubleBits();
        return (!CBORUtilities.IsBeyondSafeRange(lb) &&
CBORUtilities.IsIntegerValue(lb)) ?
          CBORObject.FromObject(CBORUtilities.GetIntegerValue(lb)) :
          CBORObject.FromFloatingPointBits(lb, 8);
      } else if (kind == JSONOptions.ConversionMode.IntOrFloat) {
        EContext ctx = EContext.Binary64.WithBlankFlags();
        EFloat ef = EFloat.FromString(
            chars,
            initialOffset,
            endPos - initialOffset,
            ctx);
        long lb = ef.ToDoubleBits();
        if ((ctx.getFlags() & EContext.FlagInexact) != 0) {
          // Inexact conversion to double, meaning that the String doesn't
          // represent an integer in [-(2^53)+1, 2^53), which is representable
          // ((exactly instanceof Double) ? (double)exactly : null), so treat as ConversionMode.Double
          if (!preserveNegativeZero && (lb == 1L << 63 || lb == 0L)) {
            lb = 0L;
          }
          return CBORObject.FromFloatingPointBits(lb, 8);
        } else {
          // Exact conversion; treat as ConversionMode.IntToFloatFromDouble
          return (!CBORUtilities.IsBeyondSafeRange(lb) &&
CBORUtilities.IsIntegerValue(lb)) ?
            CBORObject.FromObject(CBORUtilities.GetIntegerValue(lb)) :
            CBORObject.FromFloatingPointBits(lb, 8);
        }
      } else {
        throw new IllegalArgumentException("Unsupported conversion kind.");
      }
    }
  }
