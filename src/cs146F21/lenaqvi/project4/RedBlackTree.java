package cs146F21.lenaqvi.project4;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RedBlackTree {
	private RedBlackTree.Node root;

	public static void main(String[] args) {
		RedBlackTree rbt = new RedBlackTree();
		rbt.insert("D");
		rbt.insert("B");
		rbt.insert("A");
		rbt.insert("C");
		rbt.insert("F");
		rbt.insert("E");
		rbt.insert("H");
		rbt.insert("G");
		rbt.insert("I");
		rbt.insert("J");
		System.out.println(RBTTester.makeString(rbt));
		System.out.println(RBTTester.makeStringDetails(rbt));

	}
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

	// place a new node in the RB tree with data the parameter and color it red.
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

				// if value of new node is less than that of current node

				if (newNode.compareTo(currNode) > 0) {
					if (currNode.rightChild == null) {
						newNode.parent = currNode;
						currNode.rightChild = newNode;

						break;
					}
					// Proceed to the right subtree
					currNode = currNode.rightChild;

				}
				else if (newNode.compareTo(currNode) < 0) {
					if (currNode.leftChild == null) {
						newNode.parent = currNode;
						currNode.leftChild = newNode;

						break;
					}
					// Proceed to the left subtree
					currNode = currNode.leftChild;
				}
				// if value of new node is similar to that of current node
				//else {
				// Place new node to the right of current node
				//	currNode.rightChild = newNode;
				//}
			}
		}
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


	public RedBlackTree.Node getAunt(RedBlackTree.Node n){
		if (n.parent == null)
			return null;
		return getSibling(n.parent);
	}

	public RedBlackTree.Node getGrandparent(RedBlackTree.Node n){
		if (n.parent.parent == null)
			return null;
		return n.parent.parent;
	}

	public void rotateLeft(RedBlackTree.Node n){
		// Set a reference to the right subtree of n
		RedBlackTree.Node tempRightChild = n.rightChild;
		n.rightChild = tempRightChild.leftChild;

		if (tempRightChild.leftChild != null) {
			tempRightChild.leftChild.parent = n;
		}

		tempRightChild.parent = n.parent;

		if (n.parent == null) {
			root = tempRightChild;
		}
		else if (n == n.parent.leftChild) {
			n.parent.leftChild = tempRightChild;
		}
		else {
			n.parent.rightChild = tempRightChild;
		}

		tempRightChild.leftChild = n;
		n.parent = tempRightChild;
	}

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


	public void fixTree(RedBlackTree.Node current) {
		// case 1
		// case 1
		if (current == root) {
			current.color = 1;
			current.isRed = false;
			return;
		}
		if (current.parent.color == 1) {
			return;
		}

		if (current.isRed == true && current.parent.isRed == true)
		{


			if (getAunt(current) == null || getAunt(current).isRed == false)
			{


				if (getGrandparent(current).leftChild == current.parent && current == current.parent.rightChild) {
					rotateLeft(current.parent);
					RedBlackTree.Node originalParent = current.parent;
					fixTree(originalParent);
				}
				else if (getGrandparent(current).rightChild == current.parent && current == current.parent.leftChild) {
					rotateRight(current.parent);
					RedBlackTree.Node originalParent = current.parent;
					fixTree(originalParent);
				}
				else if (getGrandparent(current).leftChild == current.parent && current == current.parent.leftChild) {
					current.parent.color = 1;
					current.parent.isRed = false;
					getGrandparent(current).color = 0;
					getGrandparent(current).isRed = true;
					rotateRight(getGrandparent(current));
					return;
				}

				else if (getGrandparent(current).rightChild == current.parent && current == current.parent.rightChild) {
					current.parent.color = 1;
					current.parent.isRed = false;
					getGrandparent(current).color = 0;
					getGrandparent(current).isRed = true;
					rotateLeft(getGrandparent(current));
					return;
				}
			}

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