package cs146F21.lenaqvi.project4;

public class RedBlackTree {	
	private RedBlackTree.Node root;
    
	public static class Node {
		
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
	 //	fill
		
	}	

	public void insert(String data){
		addNode(data);	
	}
	
	public RedBlackTree.Node lookup(String k){ 
		//fill
	}
 	
	
	public RedBlackTree.Node getSibling(RedBlackTree.Node n){  
		//
	}
	
	
	public RedBlackTree.Node getAunt(RedBlackTree.Node n){
		//
	}
	
	public RedBlackTree.Node getGrandparent(RedBlackTree.Node n){
		return n.parent.parent;
	}
	
	public void rotateLeft(RedBlackTree.Node n){
		//
	}
	
	public void rotateRight(RedBlackTree.Node n){
		//
	}
	
	public void fixTree(RedBlackTree.Node current) {
		//
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

