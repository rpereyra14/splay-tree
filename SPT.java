//Implements Splay Trees
//By Renato Pereyra
//COMP 410 - Prof. Stotts
class SPT{
	
	protected SPT rchild;
	protected SPT lchild;
	protected SPT parent;
	protected static int nodeCount = 0;
	protected String element;
	private int level;
	
	//Constructors
	public SPT(){
		element = null;
		level = 0;
		nodeCount = nodeCount;
		rchild = null;
		lchild = null;
		parent = null;
	}
		
	public SPT( String val ){
		element = val;
		level = 0;
		nodeCount = nodeCount + 1;
		rchild = null;
		lchild = null;
		parent = null;
	}
	
	
	
	//Get the node's level in the real tree.
	public int getLevel(){
		this.getRoot().recomputeLevels();
		return this.level;
	}
	
	
	
	
	//Determine if node is a leaf.
	protected boolean isLeaf(){
		if ( this == null ){
			throw new IllegalArgumentException("Attempting to access a null node.");
		}
		if ( this.rchild == null && this.lchild == null ){
			return true;
		}else{
			return false;
		}
	}





	//Determine if node is a root.
	protected boolean isRoot(){
		if ( this == null ){
			throw new IllegalArgumentException("Attempting to access a null node.");
		}
		if ( this.parent == null ){
			return true;
		}else{
			return false;
		}
	}
	
	
	
	
	//Get the node's element.
	public String getElement(){
		if( this != null ){ 
			return this.element;
		}else{
			throw new IllegalArgumentException("The parameter you requested is not defined.");
		}
	}
	
	
	
	
	//Get the node's left child.
	public SPT getLChild(){
		if( this != null ){ 
			return this.lchild;
		}else{
			throw new IllegalArgumentException("The parameter you requested is not defined.");
		}
	}
	
	
	
	
	//Get the node's right child.
	public SPT getRChild(){
		if( this != null ){ 
			return this.rchild;
		}else{
			throw new IllegalArgumentException("The parameter you requested is not defined.");
		}
	}
	
	
	
	
	//Determine if node is a left child.
	public boolean isLChild(){
		if ( this == null ){
			throw new IllegalArgumentException("Attempting to access a null node.");
		}
		if ( this.parent != null && this == this.parent.lchild ){
			return true;
		}else{
			return false;
		}
	}
	
	
	
	
	//Determine if node is a right child.
	public boolean isRChild(){
		if ( this == null ){
			throw new IllegalArgumentException("Attempting to access a null node.");
		}
		if ( this.parent != null && this == this.parent.rchild ){
			return true;
		}else{
			return false;
		}
	}
	
	
	
	
	
	//Get the node's parent.
	public SPT getParent(){
		if( this != null ){ 
			return this.parent;
		}else{
			throw new IllegalArgumentException("The parameter you requested is not defined.");
		}
	}
	
	
	
	
	
	//Recompute the tree's levels. These are used in determining height.
	private void recomputeLevels(){ 
		
		if ( this.isRoot() ){
			this.level = 0;
		}else{
			this.level = this.parent.level + 1;
		}
		if ( this.lchild != null ){
			this.lchild.recomputeLevels();
		}
		if ( this.rchild != null ){
			this.rchild.recomputeLevels();
		}
			
	}
	
	
	
	
	//Determine height.
	public int getHeight(){
		
		if ( this.element == null ){ //Tree contains no elements
			return -1;
		}
		
		return this.getRoot().getSubTreeHeight(); //Ensure root is caller to get entire tree height.
	}
	
	
	
	
	
	//Get the height of the subtree rooted by the caller.
	public int getSubTreeHeight(){
	
		this.recomputeLevels(); //Ensure the levels used to calculate height are correct.
		
		if ( this.element == null ){ //Tree contains no elements
			return -1;
		}
	
		if ( this == null ){
			throw new IllegalArgumentException("Attempting to get the height of a null node.");
		}
		
		//Height will be calculated by determining the max sub level of any node below and subtracting the current level.
		return this.getMaxSubLevel() - this.level;
		
	}
	
	
	
	
	
	//Get the max sub level of any node below caller node.
	private int getMaxSubLevel(){
	
		if ( this.isLeaf() ){
			return this.level;
			
		//No need to check it null subtrees.
		}else if ( rchild == null ){
			return this.lchild.getMaxSubLevel();
		}else if ( lchild == null ){
			return this.rchild.getMaxSubLevel();
			
		}else{
			//Get Max Sub Level of left and right subtrees. Return the max.
			int l_height = this.lchild.getMaxSubLevel();
			int r_height = this.rchild.getMaxSubLevel();
			
			if( l_height > r_height ){
				return l_height;
			}else{
				return r_height;
			}
			
		}
	
	}
	
	
	
	
	
	
	//Get the subtree's minimum.
	public String findMin(){
		
		if ( this == null ){
			return null;
		}else if ( this.lchild == null ){
		
			//This method is considered accessing nodes. Min is splayed.
			this.SplayNode();
			return this.element;
		}else{
			return this.lchild.findMin();
		}
		
	}
	
	
	
	
	
	
	//Get the subtree's maximum.
	public String findMax(){
		
		if ( this == null ){
			return null;
		}else if ( this.rchild == null ){

			//Splay the max once found.
			this.SplayNode();
			return this.element;
		}else{
			return this.rchild.findMax();
		}
		
	}
	
	
	
	
	//Get the node count of the tree.
	public int getNodeCount(){
		return this.nodeCount;
	}
	
	
	
	
	
		
	//Insert. Start search for proper insertion location from the root.
	public SPT insert( String toInsert ){
		if ( toInsert == null ){
			throw new IllegalArgumentException("Cannot insert null string.");
		}
		return this.getRoot().insertIntoSubTree( toInsert );
	}
	
	
	
	
	//Insert into the subtree below the calling node.
	private SPT insertIntoSubTree( String toInsert ){
	
		if ( this.element == null ){ //Tree is null! Initialize it.
			this.element = toInsert;
			this.nodeCount = this.nodeCount + 1;
			return this;
		}
	
		int comparison = toInsert.compareTo(this.element);
	
		if ( comparison < 0 ){
		
			if ( lchild == null ){
				SPT newNode = new SPT( toInsert );
				newNode.parent = this;
				newNode.level = this.level + 1;
				this.lchild = newNode;
				
				//Splay inserted node
				newNode.SplayNode();
				return newNode;
			}else{
				//Go down one level in tree.
				return this.lchild.insertIntoSubTree( toInsert );
			}
			
		}else if ( comparison > 0 ){
		
			if ( rchild == null ){
				SPT newNode = new SPT( toInsert );
				newNode.parent = this;
				newNode.level = this.level + 1;
				this.rchild = newNode;
				
				//Splay inserted node.
				newNode.SplayNode();
				return newNode;
			}else{
				//Go down one level in tree.
				return this.rchild.insertIntoSubTree( toInsert );
			}
			
		//The string to insert was already in the tree!
		}else{
			//Splay node
			this.SplayNode();
			return this;
		}
	
	}
	
	
	
	
	
	//Get a node from the tree. Begin search at the root.
	public SPT get( String toFind ){
		return this.getRoot().getFromSubTree( toFind );
	}
	
	
	
	
	//Get a node from a subtree
	public SPT getFromSubTree( String toFind ){
	
		//Element was either not found, tree is empty, or null string was passed.
		if ( this == null || this.element == null || toFind == null ){
			return null;
		}
	
		int comparison = toFind.compareTo( this.element );
	
		//Found the element!
		if ( comparison == 0 ){
			this.SplayNode();
			return this;
			
		//Search string is greater than current element and right child is not null
		//search string may be in tree one level lower.
		}else if ( comparison > 0 && this.rchild != null ){
			return this.rchild.getFromSubTree( toFind );
		
		//search string may be one level lower.
		}else if ( comparison < 0 && this.lchild != null ){
			return this.lchild.getFromSubTree( toFind );
		
		//there is no chance the element is in the subtree.
		}else{
			return null;
		}
		
	}
	
	
	
	
	//Check whether an element exists in the tree.
	public boolean contains( String toFind ){
	
		if ( this.get( toFind ) != null ){
			return true;
		}else{
			return false;
		}
	
	}
	
	
	
	
	//Get a node's root
	public SPT getRoot(){
		if ( this == null ){
			throw new IllegalArgumentException("Attempting to access a null node.");
		}
		if ( !this.isRoot() ){
			return parent.getRoot();
		}else{
			return this;
		}
		
	}
	
	
	
	
	//Print whole tree
	public void printTree(){
		this.getRoot().recomputeLevels();
		this.getRoot().printSubTree();
	}
	
	
	
	//Print subtree in reverse in-order order
	//Node level will be used to determine the number of tabs to print before printing the element
	//This way, a tree-like structure can be visualized when looking at computer screen sideways.
	public void printSubTree(){
	
		int i;		
		if ( this.rchild == null ){

			i = 0;
			while( i < this.level ){  
				System.out.print( '\t' );
				i++;
			}
			
			System.out.println( this.element );
		}else{
		
			//print the entire right subtree before printing the current element.
			this.rchild.printSubTree();
			
			i = 0;
			while( i < this.level ){  
				System.out.print( '\t' );
				i++;
			}
			
			System.out.println( this.element );
			
		}	
		if ( this.lchild != null ){
			//print left subtree.
			this.lchild.printSubTree();
		}
			
	}






	//Determine if a node's splay operation is a ZigL
	private boolean isZigL(){
		if ( this.parent.isRoot() && this.isRChild() ){
			return true;
		}else{
			return false;
		}
	}

	//Determine if a node's splay operation is a ZigR
	private boolean isZigR(){
		if ( this.parent.isRoot() && this.isLChild() ){
			return true;
		}else{
			return false;
		}
	}
	
	//Determine if a node's splay operation is a ZigZigL
	private boolean isZigZigL(){
		if ( !this.parent.isRoot() && this.isRChild() && this.parent.isRChild() ){
			return true;
		}else{
			return false;
		}
	}
	
	//Determine if a node's splay operation is a ZigZigL
	private boolean isZigZigR(){
		if ( !this.parent.isRoot() && this.isLChild() && this.parent.isLChild() ){
			return true;
		}else{
			return false;
		}
	}
	
	//Determine if a node's splay operation is a ZigZagL
	private boolean isZigZagL(){
		if ( !this.parent.isRoot() && this.isLChild() && this.parent.isRChild() ){
			return true;
		}else{
			return false;
		}
	}
	
	//Determine if a node's splay operation is a ZigZagR
	private boolean isZigZagR(){
		if ( !this.parent.isRoot() && this.isRChild() && this.parent.isLChild() ){
			return true;
		}else{
			return false;
		}
	}
	
	
	
	
	
	
	
	//Performs ZigL operation
	private void ZigL(){
	
		SPT bTree = this.lchild;
		SPT parent = this.parent;
		
		//Determine the new parent of the node being splayed
		if ( !this.parent.isRoot() && this.parent.isLChild() ){
			SPT gparent = this.parent.parent;
			gparent.lchild = this;
			this.parent = gparent;
		}else if ( !this.parent.isRoot() && this.parent.isRChild() ){
			SPT gparent = this.parent.parent;
			gparent.rchild = this;
			this.parent = gparent;
		
		//node should have no new parent... it is root!
		}else{
			this.parent = null;
		}
		
		//Fix the pointers so that node is splayed properly
		parent.parent = this;
		this.lchild = parent;
		parent.rchild = bTree;
		if ( bTree != null ){
			bTree.parent = parent;
		}
	
	}
	
	
	
	
	
	
	//Performs ZigR operation
	private void ZigR(){
	
		SPT bTree = this.rchild;
		SPT parent = this.parent;
		
		//Determine the new parent of the node being splayed
		if ( !this.parent.isRoot() && this.parent.isLChild() ){
			SPT gparent = this.parent.parent;
			gparent.lchild = this;
			this.parent = gparent;
		}else if ( !this.parent.isRoot() && this.parent.isRChild() ){
			SPT gparent = this.parent.parent;
			gparent.rchild = this;
			this.parent = gparent;
			
		//node is now root!
		}else{
			this.parent = null;
		}
		
		//Fix the pointers so that node is splayed properly
		parent.parent = this;
		this.rchild = parent;
		parent.lchild = bTree;
		if ( bTree != null ){
			bTree.parent = parent;
		}
		
	}
	
	
	
	
	
	

	//Handles the actual checking of splay type and the splaying of the node itself.
	private void SplayNode(){
	
		//Do not splay anything if it is already root.
		if ( !this.isRoot() ){
		
			if( this.isZigL() ){
				this.ZigL();
			}else if( this.isZigR() ){
				this.ZigR();
			}else if( this.isZigZigL() ){
			
				//ZigZigL operations can be broken down into two ZigL operations.
				this.parent.ZigL();
				this.ZigL();
				
			}else if( this.isZigZigR() ){
				
				//ZigZigR operations can be broken down into two ZigR operations.
				this.parent.ZigR();
				this.ZigR();
			
			}else if( this.isZigZagL() ){
				
				//ZigZagL operations can be broken down into ZigL/ZigR operations.
				this.parent.ZigL();
				this.ZigL();
				this.ZigR();
				
			}else if( this.isZigZagR() ){
			
				//ZigZagR operations can be broken down into ZigL/ZigR operations.
				this.parent.ZigR();
				this.ZigR();
				this.ZigL();

			}
		
			//Splay again until the node reaches the root.
			this.SplayNode();
		
		}
			
	}
	
	
	
	
	
	
	//Delete the node containing the string toDelete
	public SPT delete( String toDelete ){
	
		if ( this.element == null ){
			throw new IllegalArgumentException("Cannot delete from null tree!");
		}
	
		this.nodeCount = this.nodeCount - 1;	//update node count
	
		SPT removeNode = this.get( toDelete ); //Get (and Splay) the node matching toDelete
		
		if ( removeNode == null ){
			return null;
		}
		
		SPT rTree = removeNode.rchild;
		SPT lTree = removeNode.lchild;
		
		removeNode = null;
		
		//The tree only contains one element, the root.
		if ( lTree == null && rTree == null ){
			this.element = null;
			return this;
			
		//Special cases
		}else if( lTree == null ){
			rTree.parent = null;
			rTree.findMin();
			return rTree.getRoot();
		}else if ( rTree == null ){
			lTree.parent = null;
			lTree.findMax();
			return lTree.getRoot();
			
		//Expected case. Min in right subtree is splayed to root and connected to left subtree
		}else{
			rTree.parent = null;
			lTree.parent = null;
			rTree.findMin();
			rTree.getRoot().lchild = lTree.getRoot();
			lTree.getRoot().parent = rTree.getRoot();
			return rTree.getRoot();
		}
		
	}
		
}