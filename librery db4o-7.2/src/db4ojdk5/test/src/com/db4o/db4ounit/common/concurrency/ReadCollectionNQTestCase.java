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
package com.db4o.db4ounit.common.concurrency;

import java.util.*;

import com.db4o.*;
import com.db4o.db4ounit.common.persistent.*;
import com.db4o.ext.*;
import com.db4o.query.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class ReadCollectionNQTestCase extends Db4oClientServerTestCase {
	
	public static void main(String[] args) {
		new ReadCollectionNQTestCase().runConcurrency();
	}
	
	private static String testString = "simple test string";
	
	private static int LIST_SIZE = 100;
	
	private List<Object> list = new ArrayList<Object>();

	protected void store() throws Exception {
		for (int i = 0; i < LIST_SIZE; i++) {
			SimpleObject o = new SimpleObject(testString + i, i);
			list.add(o);
		}
		store(list);
	}

	public void concReadCollection(ExtObjectContainer oc) throws Exception {
		ObjectSet result = oc.query(new MyPredicate());
		Assert.areEqual(1, result.size());
		List resultList = (List) result.next();
		Assert.areEqual(list, resultList);
	}
	
	public static class MyPredicate extends Predicate<List> {
		public boolean match(List list) {
			return list.size() == LIST_SIZE;
		}
	}
}
