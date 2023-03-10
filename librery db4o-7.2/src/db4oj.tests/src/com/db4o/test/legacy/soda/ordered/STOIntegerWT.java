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
package com.db4o.test.legacy.soda.ordered;


import com.db4o.query.*;
import com.db4o.test.legacy.soda.*;

public class STOIntegerWT implements STClass{
	
	public static transient SodaTest st;
	
	Integer i_int;
	
	public STOIntegerWT(){
	}
	
	private STOIntegerWT(int a_int){
		i_int = new Integer(a_int);
	}
	
	public Object[] store() {
		return new Object[]{
			new STOIntegerWT(1001),
			new STOIntegerWT(99),
			new STOIntegerWT(1),
			new STOIntegerWT(909),
			new STOIntegerWT(1001),
			new STOIntegerWT(0),
			new STOIntegerWT(1010),
			new STOIntegerWT()
		};
	}
	
	public void testAscending() {
		Query q = st.query();
		q.constrain(STOIntegerWT.class);
		q.descend("i_int").orderAscending();
		Object[] r = store();
		st.expectOrdered(q, new Object[] { r[5], r[2],  r[1], r[3], r[0], r[4], r[6], r[7] });
	}
	
	public void testDescending() {
		Query q = st.query();
		q.constrain(STOIntegerWT.class);
		q.descend("i_int").orderDescending();
		Object[] r = store();
		st.expectOrdered(q, new Object[] { r[6], r[4],  r[0], r[3], r[1], r[2], r[5], r[7]  });
	}
	
	public void testAscendingGreater(){
		Query q = st.query();
		q.constrain(STOIntegerWT.class);
		Query qInt = q.descend("i_int");
		qInt.constrain(new Integer(100)).greater();
		qInt.orderAscending();
		Object[] r = store();
		st.expectOrdered(q, new Object[] {r[3], r[0], r[4], r[6]});
	}
}

