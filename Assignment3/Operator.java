// Operator.java - operator token (+ - * / ( ) %)
//
// Kurt Schmidt
// 3/07
//
// javac 1.6.0, on Linux version 2.6.18.6 (gcc version 3.4.6 (Gentoo
//		 3.4.6-r1, ssp-3.4.5-1.0, pie-8.7.9))
//
// EDITOR:  tabstop=2, cols=80
//
// NOTES:
// 	Tokens come in 2 flavors:
// 		operands (only integers here)
// 		operators (just +, -, *, /, %, parenthesis)
// 
//	This is so you can have a single container (Vector, ArrayList, whatever)
//	that holds an entire expression (Token).
//


public class Operator extends Token {

	protected opType val;

	public boolean isOperator() { return true; }
	public boolean isOperand() { return false; }

	// helper method, returns (assigns) precedence for operators
	protected int getPrec()
	{
	// TODO:  use a switch, whatever, assign ordinals to operators
		
			if (val == opType.ADD)
				return 1;
			else if (val == opType.SUB)
				return 1;
			else if (val == opType.MULT)
				return 2;
			else if (val == opType.DIV)
				return 2;
			else if (val == opType.MOD)
					return 2;
			else if (val == opType.LPAR)
				return 3;
			else if (val == opType.RPAR)
				return 4;
			else
				return 0;

	}

		// handy for comparing 2 operators
	public static int compare( Operator a, Operator b )
	{
		if( a.getPrec() == b.getPrec() )
			return 0;
		else if( a.getPrec() < b.getPrec() )
			return -1;
		else
			return 1;
	}

	public opType getVal() { return val; }

	public Operator( opType v ) { val = v; }

}	// class Operator
