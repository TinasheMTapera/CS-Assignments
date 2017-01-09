/** @author - Tinashe M Tapera
  *
  */

/** 	Parsing Infix Expressions
*		CS265 assignment 3
*		Tinashe M Tapera
*		November 2016
*
* 		Prompt: write a program that parses infix expressions (described below)
* 		into appropriate Tokens (operator or operand), stored in some linear
* 		container (ArrayList ?), passes the infix expression to a function that
* 		returns the expression to postfix form, then passes it to a function
* 		which evaluates the postfix expression, returns an integer.
*/


import java.io.*;
import java.util.*;

public class calculator{

	/**   A number of methods here were added, including a switch statement
	  * 	for operators, a method to compare precedences, a check to see
	  *	if a string can be parsed as an int, the parser itself,
	  * 	and finally the evaluator.
	  */

	//a quick function to make parsing operators simpler
	//because operator.java requires these exact strings to create opTypes
	//works like enumerator
	public static opType operatorSwitch(String s)
	{
		switch(s){
			case "+":
				return opType.ADD;
			case "-":
				return opType.SUB;
			case "*":
				return opType.MULT;
			case "/":
				return opType.DIV;
			case "%":
				return opType.MOD;
			case "(":
				return opType.LPAR;
			default:
				return opType.RPAR;
		}
	} //operator switch

	//method to do the precedence comparison; the one in the token file was
	//stubborn
   public static int comparison( int a, int b )
		   {
				if( a == b )
					return 0;
				else if( a < b )
					return -1;
				else
					return 1;
			}

	//method to check if a string is an integer
	static boolean isInt(String s)
	{
		try
		{ int i = Integer.parseInt(s); return true; }
		catch(NumberFormatException er)
		{ return false; }
	}

	//method that parses each token and creates the postfix expression
	//modifies the stacks created for each line (operator stack, operand
	//stack); does not return
	public static void parser(Scanner c, Stack tors, Stack ands, Stack main){
		//in the line, parse by character, using space as delimiter
		Stack aux = new Stack();

		while( c.hasNext()){
			String current = c.next();

			int prec;
			opType currentOp = operatorSwitch(current);
			Operator currentOpTemp = new Operator(currentOp);
			int currentOpVal = currentOpTemp.getPrec();

			//its an operand; push it to the stack
			if(isInt(current)){
				main.push(current);
				ands.push(current);
			}
			
			//its an operator; push it to the stack if its empty
			else if(aux.empty()){
				aux.push(current);
				tors.push(current);
			}

			//its an operator and the stack isnt empty; compare operators
			else{
				tors.push(current);
				
				opType peekOp = operatorSwitch((String) aux.peek());
				Operator peekOpTemp = new Operator(peekOp);
				int peekOpVal = peekOpTemp.getPrec();
				prec = comparison(currentOpVal,peekOpVal);
				
				//if it is a RPAR, there is an LPAR in the stack; pop
				//and place everything until the LPAR and remove the LPAR
				if(currentOpVal == 4){
					
					while(peekOpVal != 3){
						
						Object temp = aux.pop();
						main.push(temp);
						if(aux.empty())
							break;
						peekOp = operatorSwitch((String) aux.peek());
						peekOpTemp = new Operator(peekOp);
						peekOpVal = peekOpTemp.getPrec();
					}
					//aux.pop();
				}
				//compare operators; push while precedence is higher
				while(prec < 1 && !aux.empty()){
					
					Object temp = aux.pop();
					main.push(temp);
					if(aux.empty()){
						break;
					}

					peekOp = operatorSwitch((String) aux.peek());
					peekOpTemp = new Operator(peekOp);
					peekOpVal = peekOpTemp.getPrec();
					prec = comparison(currentOpVal, peekOpVal);	
				}
				aux.push(current);
			}

		} //end of line
		main.push(aux.pop());

	} //parser

	//evaluates the postfix expression; is easiest if given a string, so the
	//stack is converted in main to string first and then fed
	//returns the actual integer answer to keep simplicity
	static int evaluator(String atLast)
	{
		Scanner s = new Scanner(atLast);
		Stack<Integer> result = new Stack<Integer>();
		
		//go through the string, push each element to a stack and calculate step by
		//step
		while (s.hasNext()) {

			//push integer to the result stack
			//only keep going if they are integers, if not, compute
			if (s.hasNextInt()) {
				result.push(s.nextInt());
				continue;
			}

			//compute
			int temp;
			int second = result.pop();
			int first = result.pop();
			char operator = s.next().charAt(0);
			if(operator == '+'){	
				temp = first+second;
				result.push(temp);
			}
			else if(operator == '-'){
				temp = first-second;
				result.push(temp);
			}	  
			else if(operator == '*'){ 
				temp = first*second;
				result.push(temp);
			}
			else if(operator == '/'){ 
				temp = first/second;
				result.push(temp);
			}
			else if(operator == '%'){
				temp = first%second;
				result.push(temp);
			}
		}
		return result.pop();
	}

	/**   The main function, relatively short and sweet.
	  */

	public static void main( String [] args ) throws IOException
  	{
		
		Scanner src = new Scanner( new FileReader( "input.infix" )) ;
		String l = "";
		
		//parse input file line by line
		while( src.hasNextLine() ){
			
			l = src.nextLine() ;
			Scanner c = new Scanner(l);
			c.useDelimiter(" ");

			//set up an array list for the line
			Stack operators = new Stack();
			Stack operands = new Stack();
			Stack postFixLine = new Stack();

			parser(c, operators, operands, postFixLine);
			//output for my own sake
			/*
			System.out.println("\nBegin new expression:");
			System.out.println(l);
			System.out.println("Operators: " + operators);
			System.out.println("Operands: " + operands);
			System.out.println("Postfix: " + postFixLine.toString());
			System.out.println("End of expression.");
			*/
			String postFixString = postFixLine.toString();
			postFixString =
			postFixString.replaceAll("\\[","").replaceAll("]","").replaceAll(",","").replaceAll("\\(","").replaceAll("\\)","");
		
			//evaluate the postfix expression
			int result = evaluator(postFixString);
			System.out.println(postFixString + " = " + result);
		
		}



	} //main

}

/** End
  */



