package cs146F21.lenaqvi.project4;

/**
 * RedBlackTree class
 * This class constructs a Red Black tree.
 * @author Andy Qui Le and Mohammad Naqvi
 */
public class RedBlackTree {
	// Root node of the RedBlackTree
	private RedBlackTree.Node root;

	// Node class implementation
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

	public boolean isLeaf(RedBlackTree.Node n) {
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

	/**
	 * addNode method inserts a new node 
	 * into the RedBlackTree and color it red.
	 * @param data
	 */
	public void addNode(String data) {

		// Create a new RBTree node
		RedBlackTree.Node newNode = new RedBlackTree.Node(data);
		// Base case
		if (root == null) {

			root = newNode;
			newNode.parent = null;
			newNode.isRed = false;
			newNode.color = 1;

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
						newNode.parent = currNode;
						currNode.rightChild = newNode;

						break;
					}
					// Proceed to the right subtree
					currNode = currNode.rightChild;

				}
				// if value of new node is less than that of current node
				else if (newNode.compareTo(currNode) < 0) {
					if (currNode.leftChild == null) {
						newNode.parent = currNode;
						currNode.leftChild = newNode;

						break;
					}
					// Proceed to the left subtree
					currNode = currNode.leftChild;
				}
			}
		}
		// Invoke fixTree on new node
		fixTree(newNode);
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
	public RedBlackTree.Node lookup(String k) {
		// Let current node reference the root
		RedBlackTree.Node currNode = root;

		// Create target node with given data
		RedBlackTree.Node targetNode = new RedBlackTree.Node(k);
		// If root is null
		if (root == null)
			return null;

		// Iterate while the current node isn't null
		while (currNode != null) {
			if (targetNode.compareTo(currNode) > 0) {

				currNode = currNode.rightChild;

			}
			else if (targetNode.compareTo(currNode) < 0) {

				currNode = currNode.leftChild;

			}
			else
				// two nodes are equal
				break;
		}

		return currNode;
	}


	/**
	 * getSibling method retrieves the node
	 * that is on the same level as the argument node, n.
	 * @param n
	 * @return reference to the sibling node
	 */
	public RedBlackTree.Node getSibling(RedBlackTree.Node n){
		if (n.parent != null) {
			if (n.parent.leftChild != null && !isLeftChild(n.parent,n))
				return n.parent.leftChild;
			else {
				if(n.parent.rightChild != null && isLeftChild(n.parent, n))
					return n.parent.rightChild;
			}
		}
		return null;
	}

	/**
	 * getAunt method retrieves the
	 * aunt node of the current node.
	 * @param n current node
	 * @return the aunt node
	 */
	public RedBlackTree.Node getAunt(RedBlackTree.Node n){
		if (n.parent == null)
			return null;
		return getSibling(n.parent);
	}

	/**
	 * getGrandparent method retrieves the
	 * grandparent node of the current node.
	 * @param n current node
	 * @return the grandparent node
	 */
	public RedBlackTree.Node getGrandparent(RedBlackTree.Node n){
		if (n.parent.parent == null)
			return null;
		return n.parent.parent;
	}
	
	/**
	 * rotateLeft method rotates the 
	 * current node to the left.
	 * @param n current node
	 */
	public void rotateLeft(RedBlackTree.Node n){
		// Set a reference to the right subtree of current node
		RedBlackTree.Node tempRightChild = n.rightChild;
		// right child of current node references left child of tempRightChild
		n.rightChild = tempRightChild.leftChild;
		// If left child of tempRightChild is not null
		if (tempRightChild.leftChild != null) {
			// change parent of left child of tempRightChild to be current node
			tempRightChild.leftChild.parent = n;
		}
		// change parent of tempRightChild to be parent of current node
		tempRightChild.parent = n.parent;
		// if parent of current node is null
		if (n.parent == null) {
			// let root be tempRightChild
			root = tempRightChild;
		}
		// if current node is left child
		else if (n == n.parent.leftChild) {
			// let left child of parent of current node reference tempRightChild
			n.parent.leftChild = tempRightChild;
		}
		else {
			n.parent.rightChild = tempRightChild;
		}
		// let left child of tempRightChild reference current node
		tempRightChild.leftChild = n;
		// Change parent of current node to be tempRightChild
		n.parent = tempRightChild;
	}
	
	/**
	 * rotateRight method rotates the
	 * current node to the right.
	 * @param n current node
	 */
	public void rotateRight(RedBlackTree.Node n){
		// Set a reference to the left subtree of n
		RedBlackTree.Node tempLeftChild = n.leftChild;
		n.leftChild = tempLeftChild.rightChild;

		if (tempLeftChild.rightChild != null) {
			tempLeftChild.rightChild.parent = n;
		}

		tempLeftChild.parent = n.parent;

		if (n.parent == null) {
			root = tempLeftChild;
		}
		else if (n == n.parent.rightChild) {
			n.parent.rightChild = tempLeftChild;
		}
		else {
			n.parent.leftChild = tempLeftChild;
		}

		tempLeftChild.rightChild = n;
		n.parent = tempLeftChild;
	}

	/**
	 * fixTree method repairs an unbalanced
	 * RedBlackTree and color nodes appropriately.
	 * @param current current node
	 */
	public void fixTree(RedBlackTree.Node current) {
		
		// case 1
		if (current == root) {
			current.color = 1;
			current.isRed = false;
			return;
		}
		// case 2
		if (current.parent.color == 1) {
			return;
		}
		// case 3
		if (current.isRed == true && current.parent.isRed == true)
		{

			// when aunt node is null or black
			if (getAunt(current) == null || getAunt(current).isRed == false)
			{

				// variation 1
				if (getGrandparent(current).leftChild == current.parent && current == current.parent.rightChild) {
					rotateLeft(current.parent);
					RedBlackTree.Node originalParent = current.parent;
					fixTree(originalParent);
				}
				// variation 2
				else if (getGrandparent(current).rightChild == current.parent && current == current.parent.leftChild) {
					rotateRight(current.parent);
					RedBlackTree.Node originalParent = current.parent;
					fixTree(originalParent);
				}
				// variation 3
				else if (getGrandparent(current).leftChild == current.parent && current == current.parent.leftChild) {
					current.parent.color = 1;
					current.parent.isRed = false;
					getGrandparent(current).color = 0;
					getGrandparent(current).isRed = true;
					rotateRight(getGrandparent(current));
					return;
				}
				// variation 4
				else if (getGrandparent(current).rightChild == current.parent && current == current.parent.rightChild) {
					current.parent.color = 1;
					current.parent.isRed = false;
					getGrandparent(current).color = 0;
					getGrandparent(current).isRed = true;
					rotateLeft(getGrandparent(current));
					return;
				}
			}
			// when aunt node is red
			else {
				if (getAunt(current).isRed == true) {
					current.parent.color = 1;
					current.parent.isRed = false;
					getAunt(current).color = 1;
					getAunt(current).isRed = false;
					getGrandparent(current).color = 0;
					getGrandparent(current).isRed = true;
					fixTree(getGrandparent(current));
				}
			}
		}
	}


	/**
	 * isEmpty method verifies whether
	 * the key of node is null or not.
	 * @param n
	 * @return
	 */
	public boolean isEmpty(RedBlackTree.Node n){
		if (n.key == null){
			return true;
		}
		return false;
	}

	/**
	 * isLeftChild method verifies whether
	 * the child node is a left child of the parent node.
	 * @param parent
	 * @param child
	 * @return
	 */
	public boolean isLeftChild(RedBlackTree.Node parent, RedBlackTree.Node child)
	{
		if (child.compareTo(parent) < 0 ) {//child is less than parent
			return true;
		}
		return false;
	}

	/**
	 * preOrderVisit method invokes
	 * a private method to traverse
	 * the tree in preorder fashion.
	 * @param v
	 */
	public void preOrderVisit(Visitor v) {
		preOrderVisit(root, v);
	}

	/**
	 * preOrderVisit method recursively
	 * traverses the tree in preorder fashion.
	 * @param n current node
	 * @param v
	 */
	private static void preOrderVisit(RedBlackTree.Node n, Visitor v) {
		if (n == null) {
			return;
		}
		v.visit(n);
		preOrderVisit(n.leftChild, v);
		preOrderVisit(n.rightChild, v);
	}
}