//By Renato Pereyra
//COMP 410 - Prof. Stotts

import java.util.Scanner;

//Class that will provide an interphase for a user to work with the SPT implementation of splay trees.
class SplayTreeDriver{
	
	public static void main( String args[] ){
	
		//program exits when done = true, prompts the user with a wrong input message when wrongInput = true, 
		//and outputs help info when explain = true
		boolean done = false, wrongInput = false, explain = true;
		Scanner InputReader = new Scanner( System.in );
		
		SPT Tree = new SPT();

		while( !done ){
		
			//Instructions of use
			if ( explain ){
				System.out.println( "\n\n/******************************** Splay Tree Driver Class **************************************/" );
				System.out.println( "Type i (insert), d (delete), g (get), or c (contains). Include element as a parameter (space delim). " );
				System.out.println( "Other funcs: m (print min), x (print max), h (print height), n (print node count), p (print tree)." );
				System.out.println( "Shortcut func: f <x> <a> <b> (fill tree with x nodes of length btwn a and b; default: btwn 5 and 25 chars)." );
				System.out.println( "Shortcut func: r <a> <b> (equivalent to f 1 <a> <b>; default: btwn 5 and 25 chars)." );
				System.out.println( "Type ? into prompt to re-print this description." );
				System.out.println( "/******************************** Splay Tree Driver Class **************************************/\n\n" );
				explain = false;
			}
			
			//Will not output if user was already prompted to retry input
			if ( !wrongInput ){
				System.out.print( "Command (i, r, f, d, g, c, m, x, h, n, p, ?, q): " );
			}
						
			String input = InputReader.nextLine();

			//get the actual command inputted. The arguments passed to the command will be parsed later.
			String command = input.substring(0, 1);
			
			//Insert command
			if ( command.compareTo("i") == 0 ){
			
				//parse argument
				if ( input.substring(1).trim().compareTo("") == 0 ){
					System.out.println( "Element cannot be the empty string." );
				}else{
					//insert
					Tree = Tree.insert( input.substring(1).trim() );
				}
				
				wrongInput = false;
			
			//Random Insert
			}else if ( command.compareTo("r") == 0 ){
			
				if ( command.compareTo(input) == 0 ){
					Tree = Tree.insert( MyRandom.nextString() );
				}else{
					//parse input
					String[] tokens = input.substring(1).trim().split(" ");
					int low = Integer.parseInt(tokens[0]);
					int high = Integer.parseInt(tokens[1]);
					
					Tree = Tree.insert( MyRandom.nextString(low, high) );
				}
				
				wrongInput = false;
			
			//Fill up the tree with random strings
			}else if ( command.compareTo("f") == 0 ){
				
				//parse the input
				String[] tokens = input.substring(1).trim().split(" ");
				int numParams = tokens.length;
				int loopCount = Integer.parseInt(tokens[0]);
				
				//Default string lengths are okay
				if ( numParams == 1 ){
					for( int i = 0; i < loopCount; i++ ){
						Tree = Tree.insert( MyRandom.nextString() );
					}
				
				//parse string length parameters
				}else{
					int low = Integer.parseInt(tokens[1]);
					int high = Integer.parseInt(tokens[2]);
					for( int i = 0; i < loopCount; i++ ){
						Tree = Tree.insert( MyRandom.nextString(low, high) );
					}
				}
				
				wrongInput = false;
			
			//Delete command
			}else if ( command.compareTo("d") == 0 ){
			
				//Parse input
				if ( input.substring(1).trim().compareTo("") == 0 ){
					System.out.println( "Element cannot be the empty string." );
				}else{
					SPT node = Tree.delete( input.substring(1).trim() );
					
					//element was not found
					if ( node == null ){
						System.out.println( "Element not found." );
						
					//found and deleted!
					}else{
						Tree = node;
					}
					
				}
				
				wrongInput = false;
			
			//Get command
			}else if ( command.compareTo("g") == 0 ){
				
				if ( input.substring(1).trim().compareTo("") == 0 ){
					System.out.println( "Element cannot be the empty string." );
				}else{
				
					SPT node = Tree.get( input.substring(1).trim() );
					
					if ( node == null ){
						System.out.println( "Element not found." );
					}else{
						Tree = node;
					}
					
				}
				
				wrongInput = false;
				
			//Contains command - returns true/false
			}else if ( command.compareTo("c") == 0 ){
				
				if ( input.substring(1).trim().compareTo("") == 0 ){
					System.out.println( "Element cannot be the empty string." );
				}else{
					System.out.println( Tree.contains( input.substring(1).trim() ) );
				}

				wrongInput = false;
			
			//Find Min
			}else if ( command.compareTo("m") == 0 ){
			
				String min = Tree.findMin();
				
				System.out.println( min );
				
				//Ensure Tree remains pointing to root.
				//This step was not really needed, since a call to getRoot() could be made,
				//but this reassignment saves the program an extra call. Note that min is at the root so get is O(1).
				if ( min != null ){
					Tree = Tree.get( min );
				}
				
				wrongInput = false;
			
			//Find Max
			}else if ( command.compareTo("x") == 0 ){
			
				String max = Tree.findMax();
				
				System.out.println( max );
				
				//Ensure tree remains pointing to root.
				if ( max != null ){
					Tree = Tree.get( max );
				}
				
				wrongInput = false;
			
			//Get Height
			}else if ( command.compareTo("h") == 0 ){
			
				System.out.println( Tree.getHeight() );
				wrongInput = false;
			
			//Get number of nodes
			}else if ( command.compareTo("n") == 0 ){
			
				System.out.println( Tree.getNodeCount() );
				wrongInput = false;
			
			//Print tree
			}else if ( command.compareTo("p") == 0 ){
			
				Tree.printTree();
				wrongInput = false;
			
			//Output help info
			}else if ( command.compareTo("?") == 0 ){
			
				explain = true;
				wrongInput = false;
			
			//Quit
			}else if ( command.compareTo("q") == 0 ){
			
				done = true;
			
			//Input could not be parsed
			}else{
			
				wrongInput = true;
				System.out.print( "Incorrect command. Try again:" );
			
			}
			
		}
		
	}
	
}