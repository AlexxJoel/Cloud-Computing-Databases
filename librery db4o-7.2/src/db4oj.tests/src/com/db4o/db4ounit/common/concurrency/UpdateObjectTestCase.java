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

import com.db4o.*;
import com.db4o.db4ounit.common.persistent.*;
import com.db4o.ext.*;
import com.db4o.query.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class UpdateObjectTestCase extends Db4oClientServerTestCase {

	public static void main(String[] args) {
		new UpdateObjectTestCase().runConcurrency();
	}

	private static String testString = "simple test string";

	private static int COUNT = 100;

	protected void store() throws Exception {
		for (int i = 0; i < COUNT; i++) {
			store(new SimpleObject(testString + i, i));
		}

	}

	public void concUpdateSameObject(ExtObjectContainer oc, int seq)
			throws Exception {
		Query query = oc.query();
		query.descend("_s").constrain(testString + COUNT / 2);
		ObjectSet result = query.execute();
		Assert.areEqual(1, result.size());
		SimpleObject o = (SimpleObject) result.next();
		o.setI(COUNT + seq);
		oc.store(o);

	}

	public void checkUpdateSameObject(ExtObjectContainer oc) throws Exception {
		Query query = oc.query();
		query.descend("_s").constrain(testString + COUNT / 2);
		ObjectSet result = query.execute();
		Assert.areEqual(1, result.size());
		SimpleObject o = (SimpleObject) result.next();
		int i = o.getI();
		Assert.isTrue(COUNT <= i && i < COUNT + threadCount());

	}

	public void concUpdateDifferentObject(ExtObjectContainer oc, int seq)
			throws Exception {
		Query query = oc.query();
		query.descend("_s").constrain(testString + seq).and(
				query.descend("_i").constrain(new Integer(seq)));
		ObjectSet result = query.execute();
		Assert.areEqual(1, result.size());
		SimpleObject o = (SimpleObject) result.next();
		o.setI(seq + COUNT);
		oc.store(o);
	}

	public void checkUpdateDifferentObject(ExtObjectContainer oc)
			throws Exception {

		ObjectSet result = oc.query(SimpleObject.class);
		Assert.areEqual(COUNT, result.size());
		while (result.hasNext()) {
			SimpleObject o = (SimpleObject) result.next();
			int i = o.getI();
			if (i >= COUNT) {
				i -= COUNT;
			}
			Assert.areEqual(testString + i, o.getS());
		}

	}

}
