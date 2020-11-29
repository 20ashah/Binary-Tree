/*
 This is a standard node class that contains its 
 parent and children as well as its height and balance
*/
public class Node {

	// declare instace variables
	private Node left;
	private Node right;
	private Node parent;
	private int data;
	private int balance;
	private int height;

	// constructor
	public Node(int d, Node p) {
		data = d;
		parent = p;
	}

	// getting value of node
	public int getData() {
		return data;
	}

	// getting balance of node
	public int getBalance() {
		return balance;
	}

	// getting height of node
	public int getHeight() {
		return height;
	}

	// getting left child of node
	public Node getLeft() {
		return left;
	}

	// getting right child of node
	public Node getRight() {
		return right;
	}

	// getting parent of node
	public Node getParent() {
		return parent;
	}

	// setting value of node
	public void setData(int d) {
		data = d;
	}

	// setting balance of node
	public void setBalance(int b) {
		balance = b;
	}

	// setting height of node
	public void setHeight(int h) {
		height = h;
	}

	// setting left child of node
	public void setLeft(Node l) {
		left = l;
	}

	// setting right child of node
	public void setRight(Node r) {
		right = r;
	}

	// setting parent of node
	public void setParent(Node p) {
		parent = p;
	}
}