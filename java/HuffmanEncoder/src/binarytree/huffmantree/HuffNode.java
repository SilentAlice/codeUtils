package binarytree.huffmantree;

import binarytree.*;

public class HuffNode implements BinNode {

	private int intFreq;	// int for this node
	private int intCode;
	
	public String strLabel;
	public String strPath;
	
	private BinNode left;	// Pointer to left child
	private BinNode right;	// Pointer to right child
	
	// Constructors
	public HuffNode() {
		super();
		intFreq = -1;
		intCode = -1;
		strLabel = "";
		strPath = "";
		left = right = null;
	}
	
	
	
	public HuffNode(int code, int freq) {
		this();
		intFreq = freq;
		intCode = code;
	}
	
	public HuffNode(int freq, BinNode l, BinNode r) {
		this();
		intFreq = freq;
		left = l;
		right = r;
		
	}
	
	public HuffNode(int code, int freq, BinNode l, BinNode r ) {
		this(code, freq);
		left = l;
		right = r;
	}
	
	// Functions
	public int code() {
		return intCode;
	}
	
	public int setCode(int code) {
		int oldCode = intCode;
		intCode = code;
		return oldCode;
	}
	
//	public String label() {
//		return strLabel;
//	}
//	
//	public String setLabel(String label) {
//		String oldLabel = strLabel;
//		strLabel = label;
//		return oldLabel;
//	}
//	
//	public String path() {
//		return strPath;
//	}
//	
//	public String setPath(String path) {
//		String oldPath = strPath;
//		strPath = path;
//		return oldPath;
//	}
	
	@Override
	public int freq() {
		return intFreq;
	}

	@Override
	public int setFreq(int freq) {
		int oldFreq = intFreq;
		intFreq = freq;
		return oldFreq;
	}

	@Override
	public boolean isLeaf() {
		return (left == null) && (right == null);
	}

	@Override
	public BinNode right() {
		return right;
	}
	@Override
	public BinNode setRight(BinNode it) {
		return right = it;
	}
	@Override
	public BinNode left() {
		return left;
	}
	@Override
	public BinNode setLeft(BinNode it) {
		return left = it;
	}

}
