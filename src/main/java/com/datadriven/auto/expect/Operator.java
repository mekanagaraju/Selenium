package com.datadriven.auto.expect;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.google.common.base.Objects;

enum Operator
{
 EQUALS("=", "==", "equals", "equal"),
 NOT_EQUALS("!=", "not", "not-equals", "not-equal"),
 GT(">", "greater-than", "greater"),
 LT("<", "less-than", "less"),
 GTE(">=", "greater-than-or-equal"),
 LTE("<=", "less-than-or-equal"),
 CONTAINS("contains", "contain", "contain-all"),
 NOT_CONTAINS("!contains", "!contain", "not-contain");
   //
   List<String> symbols;


   Operator(String... symbols)
   {
      this.symbols = Arrays.asList(symbols);
   }


   public boolean evaluate(Object left, Object right)
   {
      if (left instanceof Collection)
      {
         @SuppressWarnings("unchecked")
         Collection<Object> coll = (Collection<Object>)left;
         return evaluateCollection(coll, right);
      }
      else if (left == null || right == null)
      {
         return evaluateNull(left, right);
      }
      else if (left instanceof String)
      {
         return evaluateStrings(String.valueOf(left), String.valueOf(right));
      }
      else if (left instanceof Number && right instanceof Number)
      {
         return evaluateNumbers((Number)left, (Number)right);
      }
      else if (left instanceof Boolean && right instanceof Boolean)
      {
         return evaluateBooleans((Boolean)left, (Boolean)right);
      }
      else
      {
         return evaluateMixedObjects(left, right);
      }
   }


   public boolean evaluateNull(Object left, Object right)
   {
      int compare = 0;
      if (left == null && right != null)
      {
         compare = -1;
      }
      else if (left != null && right == null)
      {
         compare = 1;
      }
      switch (this)
      {
         case CONTAINS:
         case NOT_CONTAINS:
            return false;
         default:
            break;
      }
      return determineComparisonValue(compare, left, right);
   }


   public boolean evaluateNumbers(Number left, Number right)
   {
      int compare = Double.compare(left.doubleValue(), right.doubleValue());
      switch (this)
      {
         case CONTAINS:
            return throwCompareError(left, right);
         default:
            return determineComparisonValue(compare, left, right);
      }
   }


   public boolean evaluateStrings(String left, String right)
   {
      int compare = left.compareTo(right);
      switch (this)
      {
         case CONTAINS:
            return left.contains(right);
         case NOT_CONTAINS:
            return !left.contains(right);
         default:
            return determineComparisonValue(compare, left, right);
      }
   }


   public boolean evaluateBooleans(Boolean left, Boolean right)
   {
      int compare = left.compareTo(right);
      return determineComparisonValue(compare, left, right);
   }


   public boolean evaluateMixedObjects(Object left, Object right)
   {
      switch (this)
      {
         case EQUALS:
            return Objects.equal(left, right);
         case NOT_EQUALS:
            return !Objects.equal(left, right);
         default:
            return throwCompareError(left, right);
      }
   }


   public boolean evaluateCollection(Collection<Object> left, Object right)
   {
      if (right instanceof Collection)
      {
         Collection< ? > rc = (Collection< ? >)right;
         switch (this)
         {
            case CONTAINS:
               return left.containsAll(rc);
            case EQUALS:
               return left.containsAll(rc) && rc.containsAll(left);
            case NOT_CONTAINS:
            case NOT_EQUALS:
               Optional<Object> opt = left.stream().filter(rc::contains).findAny();
               return !opt.isPresent();
            default:
               return throwCompareError(left, right);
         }
      }
      else
      {
         switch (this)
         {
            case CONTAINS:
            case EQUALS:
               return left.contains(right);
            case NOT_CONTAINS:
            case NOT_EQUALS:
               return !left.contains(right);
            default:
               return throwCompareError(left, right);
         }
      }
   }


   public static Operator forSymbol(String symbolStr)
   {
      for (Operator operator : Operator.values())
      {
         for (String symbol : operator.symbols)
         {
            if (symbol.equals(symbolStr))
            {
               return operator;
            }
         }
      }
      return Operator.valueOf(symbolStr);
   }


   public static boolean isOperator(String str)
   {
      for (Operator operator : Operator.values())
      {
         for (String symbol : operator.symbols)
         {
            if (symbol.equals(str))
            {
               return true;
            }
         }
      }
      return false;
   }


   private boolean throwCompareError(Object left, Object right)
   {
      throw new RuntimeException("Can't use [" + this.symbols + "] to compare " + left.getClass().getSimpleName() + " with " + right.getClass().getSimpleName());
   }


   private boolean determineComparisonValue(int compare, Object left, Object right)
   {
      switch (this)
      {
         case EQUALS:
            return compare == 0;
         case GT:
            return compare > 0;
         case GTE:
            return compare >= 0;
         case LT:
            return compare < 0;
         case LTE:
            return compare <= 0;
         case NOT_EQUALS:
            return compare != 0;
         default:
            return throwCompareError(left, right);
      }
   }
}