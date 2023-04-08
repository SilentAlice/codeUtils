package binarytree;
// Interface BinNode
public interface BinNode {
	/* Return and set the element */
	public int freq();
	public int setFreq(int freq);
	
	/* Return ture if this is leaf node */
	public boolean isLeaf();
	
	/* Return and set the right child */
	public BinNode right();
	public BinNode setRight(BinNode it);
	
	/* Return and set the left child */
	public BinNode left();
	public BinNode setLeft(BinNode it);
	
}
