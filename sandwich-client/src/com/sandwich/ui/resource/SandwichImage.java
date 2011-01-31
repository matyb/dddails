package com.sandwich.ui.resource;
import javax.swing.ImageIcon;

public class SandwichImage extends ImageIcon {

	private static final long serialVersionUID = -4449793535437531709L;
	public static final SandwichImage ERROR = new SandwichImage("error.png");
	
	private SandwichImage(){};
	private SandwichImage(String filePath){
		super(filePath);
	}
}