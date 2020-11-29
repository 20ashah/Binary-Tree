
/*
This is a tree class that creates the binary search 
tree structure using a root. It contains methods for 
inserting, deleting, and balancing.
*/
import java.util.*;

public class Tree {

	private Node root;

	// inserting a value into the tree
	public void insert(int val) {
		// set value to root if there is none
		if (root == null) {
			root = new Node(val, null);
			return;
		}

		Node cur = root; // start from top
		int count = 0;

		while (count == 0) {
			Node orig = cur;

			// determining direction to go
			boolean isRight = false;
			if (val > cur.getData()) {
				isRight = true;
			}

			if (isRight) {
				cur = cur.getRight(); // go right if bigger
			} else {
				cur = cur.getLeft(); // go left if smaller
			}

			if (cur == null) { // reaches end of the tree
				Node newNode = new Node(val, orig);
				// set new node in correct place
				if (isRight) {
					orig.setRight(newNode);
				} else {
					orig.setLeft(newNode);
				}
				balance(orig); // balance tree
				count = 1;
			}
		}
	}

	// deleting a particular node in the tree
	private void deleteNode(Node del) {
		if (del.getLeft() == null && del.getRight() == null) {
			if (del.getParent() == null) { // no node
				root = null;
			} else {
				Node delPar = del.getParent();
				// set children to null if parent exists
				if (delPar.getLeft() == del) {
					delPar.setLeft(null);
				} else {
					delPar.setRight(null);
				}
				balance(delPar); // re-balance tree
			}
		} else if (del.getLeft() != null) { // has left child
			Node delChild = del.getLeft();
			// find right-most node
			while (delChild.getRight() != null) {
				delChild = delChild.getRight();
			}
			// replace with right-most and delete node
			del.setData(delChild.getData());
			deleteNode(delChild);
		} else { // has right child
			Node delChild = del.getRight();
			// find left-most node
			while (delChild.getLeft() != null) {
				delChild = delChild.getLeft();
			}
			// replace with left-most and delete node
			del.setData(delChild.getData());
			deleteNode(delChild);
		}
	}

	// deleting a value from the tree
	public void deleteVal(int val) {
		if (root != null) {
			Node sub = root; // start from top
			while (sub != null) {
				Node top = sub;
				if (val < top.getData()) { // go left if less
					sub = top.getLeft();
				} else { // go right if bigger
					sub = top.getRight();
				}

				// if the top matches the key value
				if (val == top.getData()) {
					deleteNode(top);
					break;
				}
			}
		}

	}

	// balancing a particular node
	private void balance(Node x) {
		// find balances for each node
		ArrayList<Node> list = new ArrayList<Node>();
		list.add(x);
		setBalances(list);

		// perform correct rotation depending on heights of subtrees
		if (x.getBalance() == -2) { // node has a balance of -2
			Node leftSub = x.getLeft().getLeft();
			Node leftRightSub = x.getLeft().getRight();
			if (getHeight(leftSub) < getHeight(leftRightSub)) {
				x = leftRightRotation(x);
			} else {
				x = rightRotation(x);
			}
		} else if (x.getBalance() == 2) { // node has a balance of 2
			Node rightSub = x.getRight().getRight();
			Node rightLeftSub = x.getRight().getLeft();
			if (getHeight(rightSub) < getHeight(rightLeftSub)) {
				x = rightLeftRotation(x);
			} else {
				x = leftRotation(x);
			}
		}

		if (x.getParent() == null) { // stop balancing
			root = x;
		} else { // continue balancing up the tree
			balance(x.getParent());
		}
	}

	// perform a left rotation on a node
	private Node leftRotation(Node node) {

		Node rightChild = node.getRight();
		rightChild.setParent(node.getParent()); // set new parent of right child

		// set new child of original node
		node.setRight(rightChild.getLeft());
		if (node.getRight() != null) {
			node.getRight().setParent(node);
		}

		// set new left child of right subtree
		rightChild.setLeft(node);
		node.setParent(rightChild);

		// make left child of what was the right child
		// of the former root to new child of former root
		if (rightChild.getParent() != null) {
			Node par = rightChild.getParent();
			// set children of right subtree
			if (par.getRight() != node) {
				par.setLeft(rightChild);
			} else {
				par.setRight(rightChild);
			}
		}

		// re-calculate node balances
		ArrayList<Node> list = new ArrayList<Node>();
		list.add(node);
		list.add(rightChild);
		setBalances(list);

		return rightChild;
	}

	// perform a right rotation on a node
	private Node rightRotation(Node node) {

		Node leftChild = node.getLeft();
		leftChild.setParent(node.getParent()); // set new parent of left child

		// set new cihld of original node
		node.setLeft(leftChild.getRight());
		if (node.getLeft() != null) {
			node.getLeft().setParent(node);
		}

		// set new right child of left subtree
		leftChild.setRight(node);
		node.setParent(leftChild);

		// make right child of what was the left child
		// of the former root to new child of former root
		if (leftChild.getParent() != null) {
			Node par = leftChild.getParent();
			// set children to left subtree
			if (par.getRight() != node) {
				par.setLeft(leftChild);
			} else {
				par.setRight(leftChild);
			}
		}

		// re-calculate node balances
		ArrayList<Node> list = new ArrayList<Node>();
		list.add(node);
		list.add(leftChild);
		setBalances(list);

		return leftChild;
	}

	// perform a left right rotation
	private Node leftRightRotation(Node n) {
		// do a left rotation and then a right rotation
		Node leftR = leftRotation(n.getLeft());
		n.setLeft(leftR);
		return rightRotation(n);
	}

	// perform a right left rotation
	private Node rightLeftRotation(Node n) {
		// do a right rotation and then a left rotation
		Node rightR = rightRotation(n.getRight());
		n.setRight(rightR);
		return leftRotation(n);
	}

	// getting the height of any node in the tree
	private int getHeight(Node n) {
		// recursively call the next node's height
		if (n != null) {
			return n.getHeight();
		}
		return -1;
	}

	// setting balances of each node in a list
	private void setBalances(ArrayList<Node> nodes) {
		for (int i = 0; i < nodes.size(); i++) {
			// adding one to height as you go up tree
			if (nodes.get(i) != null) {
				nodes.get(i)
						.setHeight(1 + Math.max(getHeight(nodes.get(i).getLeft()), getHeight(nodes.get(i).getRight())));
			}
			// subtract right tree height from left
			nodes.get(i).setBalance(getHeight(nodes.get(i).getRight()) - getHeight(nodes.get(i).getLeft()));
		}
	}

	// for printing the tree in correct format
	public String toString() {
		ArrayList<ArrayList<String>> stringTreeList = new ArrayList<>(); // list of all nodes
		if (root != null) {
			int height = getTreeHeight(root); // height of tree
			int width = (1 << height) - 1;
			// create each level of tree as a row in list
			for (int i = 0; i < height; i++) {
				// add proper amount of spaces between numbers
				ArrayList<String> row = new ArrayList<>();
				for (int j = 0; j < width; j++) {
					row.add("  ");
				}
				stringTreeList.add(row); // add row to the list
			}

			fill(stringTreeList, 0, root, 0, width); // fill list with numbers

			String stringTree = ""; // final tree string
			// loop through the array of arrays
			for (int i = 0; i < stringTreeList.size(); i++) {
				for (int j = 0; j < stringTreeList.get(i).size(); j++) {
					stringTree += stringTreeList.get(i).get(j);
				}
				stringTree += "\n\n"; // new line for new row
			}

			return stringTree;
		}
		return "";

	}

	// for filling the array with numbers from the tree
	private void fill(ArrayList<ArrayList<String>> list, int height, Node current, int left, int right) {
		if (current != null) {
			// set first number in the middle
			List<String> level = list.get(height);
			level.set((right + left) / 2, String.valueOf(current.getData()));
			// fill left subtree
			fill(list, height + 1, current.getLeft(), left, (right + left) / 2 - 1);
			// fill right subtree
			fill(list, height + 1, current.getRight(), (right + left) / 2 + 1, right);
		}
	}

	// getting the height of the tree starting from a node
	private int getTreeHeight(Node start) {
		if (start != null) {
			// left tree height
			int leftTreeHeight = getTreeHeight(start.getLeft());
			int rightTreeHeight = getTreeHeight(start.getRight());
			return Math.max(leftTreeHeight, rightTreeHeight) + 1;
		}
		return 0;
	}

}