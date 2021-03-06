
public class BSTLowestCommonAncestor {
	// @include
	  public static BinaryTree<Integer> findLCA(
	      BinaryTree<Integer> x, BinaryTree<Integer> s, BinaryTree<Integer> b) {
		  if(s.getData()==x.getData() || b.getData()==x.getData())
			  return x;
		  else if(s.getData()<x.getData() && b.getData()>x.getData())
			  return x;
		  else if(s.getData()<x.getData() && b.getData()<x.getData())
			  return findLCA(x.getLeft(), s, b);
		  else
			  return findLCA(x.getRight(), s, b);
	  }
	  // @exclude

	  public static void main(String[] args) {
	    // 3
	    // 2 5
	    // 1 4 6
	    BinaryTree<Integer> root = new BinaryTree<>(3);
	    root.setLeft(new BinaryTree<>(2));
	    root.getLeft().setLeft(new BinaryTree<>(1));
	    root.setRight(new BinaryTree<>(5));
	    root.getRight().setLeft(new BinaryTree<>(4));
	    root.getRight().setRight(new BinaryTree<>(6));
	    assert (3 == findLCA(root, root.getLeft().getLeft(),
	        root.getRight().getLeft()).getData());
	    assert (5 == findLCA(root, root.getRight().getLeft(),
	        root.getRight().getRight()).getData());
	    assert (2 == findLCA(root, root.getLeft().getLeft(), root.getLeft())
	        .getData());
	  }
}
