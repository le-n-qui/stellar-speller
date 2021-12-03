package cs146F21.lenaqvi.project4;

public class RedBlackTree {	
	private RedBlackTree.Node root;
    
	public class Node {
		
		String key;  		  
	  	Node parent;
	  	Node leftChild;
	  	Node rightChild;
	  	boolean isRed;
	  	int color;
	  
	  	public Node(String data){
	  		this.key = data;
	  		leftChild = null;
	  		rightChild = null;
	  	}		
	  
	  	public int compareTo(Node n){ 	//this < that  <0
	  		return key.compareTo(n.key);  	//this > that  >0
	  	}
	  
	  	public boolean isLeaf(){
	  		if (this.equals(root) && this.leftChild == null && this.rightChild == null) return true;
	  		if (this.equals(root)) return false;
	  		if (this.leftChild == null && this.rightChild == null){
	  			return true;
	  		}
	  		return false;
	  	}
	  	
	  	public String getKey() {
	  		return key;
	  	}
	}

	public boolean isLeaf(RedBlackTree.Node n){
		if (n.equals(root) && n.leftChild == null && n.rightChild == null) return true;
		if (n.equals(root)) return false;
		if (n.leftChild == null && n.rightChild == null){
			  return true;
		  }
		  return false;
	}
	
	public interface Visitor{
		/**
		This method is called at each node.
		@param n the visited node
		*/
		void visit(Node n);  
	}
	
	public void visit(Node n){
		System.out.println(n.key);
	}
	
	public void printTree(){  //preorder: visit, go left, go right
		RedBlackTree.Node currentNode = root;	
		printTree(currentNode);
	}
	
	public void printTree(RedBlackTree.Node node){
		System.out.print(node.key);
		if (node.isLeaf()){
			return;
		}
		printTree(node.leftChild);
		printTree(node.rightChild);
	}
	
	// place a new node in the RB tree with data the parameter and color it red. 
	public void addNode(String data){  	//this < that  <0.  this > that  >0
		// Create a new RBTree node
		RedBlackTree.Node newNode = new RedBlackTree.Node(data);
		// Base case
		if (root == null) {
			root = newNode;
			newNode.parent = null;
		 	newNode.isRed = true;
		 	newNode.color = 0;
		}
		else {
			// if root is not null
			// we want new node to be red originally
			newNode.color = 0;
		 	newNode.isRed = true;
			// Have current node point at the root node
			RedBlackTree.Node currNode = root; 
			// Traverse and find the right location for new node
			while (currNode != null) {
				// if value of new node is greater than that of current node
				if (newNode.compareTo(currNode) > 0) {
					if (currNode.rightChild == null) {
						currNode.rightChild = newNode;
						newNode.parent = currNode;
						break;
					}
		 			else {
		 				// Proceed to the right subtree
		 				currNode = currNode.rightChild;
		 			}
				}
				// if value of new node is less than that of current node
				else if (newNode.compareTo(currNode) < 0) {
					if (currNode.leftChild == null) {
						currNode.leftChild = newNode;
						newNode.parent = currNode;
						break;
					}
					else {
						// Proceed to the left subtree
						currNode = currNode.leftChild;
					}
				}
				// if value of new node is similar to that of current node
				//else {
					// Place new node to the right of current node
				//	currNode.rightChild = newNode;
				//}
			}
		}
	}	

	/**
	 * insert method calls addNode method
	 * to add new node to the Red Black tree.
	 * @param data
	 */
	public void insert(String data){
		addNode(data);	
	}
	
	/**
	 * lookup method searches for 
	 * the node with desired string key, k.
	 * @param k
	 * @return reference to the node with string k.
	 */
	public RedBlackTree.Node lookup(String k){ 
		//fill
		RedBlackTree.Node currNode = root;
	    
	    //creating target Nod with given data
	    RedBlackTree.Node targetNode = new RedBlackTree.Node(k);
	    if (root == null)
	      return null;

	    //iterate while the current node isn't null
	    while (currNode != null) {
	      if (targetNode.compareTo(currNode) > 0) {
	    	 
	    	  currNode = currNode.rightChild;
	           
	      }
	      else if (targetNode.compareTo(currNode) < 0) {
	        
	          currNode = currNode.leftChild;
	            
	      }
	      else
	        // equal
	        break;
	    }
	    
	    //if (currNode == null)
	    	
	    return currNode;
	}
 	
	
	/**
	 * getSibling method retrieves the node
	 * that is on the same level as the argument node, n. 
	 * @param n
	 * @return reference to the sibling node
	 */
	public RedBlackTree.Node getSibling(RedBlackTree.Node n){  
		RedBlackTree.Node sibling = null;
		if (n.parent == null)
			return null;

		if(isLeftChild(n.parent, n)) {
		    sibling = n.parent.rightChild;
		}
		else {
		    sibling = n.parent.leftChild;
		}
		
		return sibling;
	}
	
	
	public RedBlackTree.Node getAunt(RedBlackTree.Node n){
		return getSibling(n.parent);
	}
	
	public RedBlackTree.Node getGrandparent(RedBlackTree.Node n){
		return n.parent.parent;
	}
	
	public void rotateLeft(RedBlackTree.Node n){
		// Set a reference to the right subtree of n
		RedBlackTree.Node tempRightChild = n.rightChild;
		// Let the right subtree of n reference tempRightChild's left subtree
		n.rightChild = tempRightChild.leftChild;
		// If left child of tempRightChild is not null
		if(tempRightChild.leftChild != null) {
			// Change its parent to be n
			tempRightChild.leftChild.parent = n;
		}
	    // Let left subtree of tempRightChild reference n
	    tempRightChild.leftChild = n;
	    // Let right subtree of parent node of n reference tempRightChild
	    n.parent.rightChild = tempRightChild; 
	    // Update tempRightChild parent with parent node of n
	    tempRightChild.parent = n.parent;
	}
	
	public void rotateRight(RedBlackTree.Node n){
		RedBlackTree.Node tempLeftChild = n.leftChild;
	    RedBlackTree.Node tempParent = n.parent;

	    n.leftChild = tempLeftChild.rightChild;

	    if (tempLeftChild.rightChild != null)
	      tempLeftChild.rightChild.parent = n;

	    tempLeftChild.rightChild = n;
	    tempParent.leftChild = tempLeftChild;
	    tempLeftChild.parent = tempParent; 
	}
	
	public void fixTree(RedBlackTree.Node current) {
		// case 1
		if (current == root)
			root.color = 1;
		else if (current.parent.color == 1) {}
			// quit
		else {}
	}
	
	public boolean isEmpty(RedBlackTree.Node n){
		if (n.key == null){
			return true;
		}
		return false;
	}
	 
	public boolean isLeftChild(RedBlackTree.Node parent, RedBlackTree.Node child)
	{
		if (child.compareTo(parent) < 0 ) {//child is less than parent
			return true;
		}
		return false;
	}

	public void preOrderVisit(Visitor v) {
	   	preOrderVisit(root, v);
	}
	 
	 
	private static void preOrderVisit(RedBlackTree.Node n, Visitor v) {
	  	if (n == null) {
	  		return;
	  	}
	  	v.visit(n);
	  	preOrderVisit(n.leftChild, v);
	  	preOrderVisit(n.rightChild, v);
	}	
}

