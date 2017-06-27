package com.server.model.loader.worldObjects;

import java.awt.*;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Target extends WorldObject implements Serializable {

	public Target() {}
	public Target(Point position) {	super(position); }

	@Override
	public WorldObjectType getWorldObjectType() {return WorldObjectType.TARGET;}

	@Override
	public char getObjRep() {return '@';}

	@Override
	public boolean onTarget() {return false;} // target can't be on a target

}
