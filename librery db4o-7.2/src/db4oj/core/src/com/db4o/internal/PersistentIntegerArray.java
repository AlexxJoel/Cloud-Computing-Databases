/* Copyright (C) 2004 - 2008  db4objects Inc.  http://www.db4o.com

This file is part of the db4o open source object database.

db4o is free software; you can redistribute it and/or modify it under
the terms of version 2 of the GNU General Public License as published
by the Free Software Foundation and as clarified by db4objects' GPL 
interpretation policy, available at
http://www.db4o.com/about/company/legalpolicies/gplinterpretation/
Alternatively you can write to db4objects, Inc., 1900 S Norfolk Street,
Suite 350, San Mateo, CA 94403, USA.

db4o is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. */
package com.db4o.internal;


/**
 * @exclude
 */
public class PersistentIntegerArray extends PersistentBase {
	
	private int[] _ints;
	
	public PersistentIntegerArray(int id) {
		setID(id);
	}
	
	public PersistentIntegerArray(int[] arr){
		_ints = new int[arr.length];
		System.arraycopy(arr, 0, _ints, 0, arr.length);
	}

	public byte getIdentifier() {
		return Const4.INTEGER_ARRAY;
	}

	public int ownLength() {
		return (Const4.INT_LENGTH * (size() + 1)) + Const4.ADDED_LENGTH;
	}

	public void readThis(Transaction trans, ByteArrayBuffer reader) {
		int length = reader.readInt();
		_ints = new int[length];
		for (int i = 0; i < length; i++) {
			_ints[i] = reader.readInt();
		}
	}

	public void writeThis(Transaction trans, ByteArrayBuffer writer) {
		writer.writeInt(size());
		for (int i = 0; i < _ints.length; i++) {
			writer.writeInt(_ints[i]);
		}
	}
	
	private int size(){
		return _ints.length;
	}
	
	public int[] array(){
		return _ints;
	}

}
