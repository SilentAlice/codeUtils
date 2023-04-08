package binarytree.huffmantree;

import java.util.HashMap;

import binarytree.*; 
 
// Huffman coding tree
public class HuffTree{

	private HuffNode root;	// Root of the Huffman coding tree	
	public HashMap<Byte, String> CodeMap;
	
		
	public HuffTree(HuffNode node) {
		super();
		CodeMap = new HashMap<Byte, String>();
		root = node;
	}
	
	public HuffTree(HuffTree l, HuffTree r) {
		super();
		CodeMap = new HashMap<Byte, String>();
		root = new HuffNode(l.freq()+r.freq(), l.root(), r.root());
	}
	
	public BinNode root() {
		return root;
	}
	
	public int freq() {
		return root.freq();
	}
	
	public void beginCode() {
		
		root.strLabel="";
		
		if(root.isLeaf()) {
			root.strPath = "11";
		} else {
		
			if(root.left().isLeaf() || root.right().isLeaf()) {
				root.strPath = "1";
			}
		}
		
		setLabel(root);
	}
	private void setLabel(HuffNode h) {

		if (!h.isLeaf()) {
			((HuffNode)h.left()).strLabel = "0"; //	left label is 0
			((HuffNode)h.left()).strPath = h.strPath + ((HuffNode)h.left()).strLabel;
			((HuffNode)h.right()).strLabel = "1"; // right label is 1
			((HuffNode)h.right()).strPath = h.strPath + ((HuffNode)h.right()).strLabel;
			setLabel((HuffNode)h.left()); // Recursion
			setLabel((HuffNode)h.right()); // Recursion
		}
		else {
			CodeMap.put((byte)h.code(), h.strPath);
		}
	}
	
}
