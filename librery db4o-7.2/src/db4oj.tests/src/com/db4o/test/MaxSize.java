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
package com.db4o.test;

import java.io.*;

import com.db4o.*;
import com.db4o.query.*;

/**
 * 
 */
public class MaxSize {

    public String           index;

    private static final int SIZE = 1000000;
    private static final int COMMIT_INTERVAL = 10000;
    private static final int QUERIES = 1000;
    private static final String FILE = "maxSize.yap"; 
    
    public static void main(String[] args) {
        
        // make sure there is an index on the "index" field
        Db4o.configure().objectClass(MaxSize.class).objectField("index").indexed(true);
        
        store();
        query();
    }

    public MaxSize() {

    }

    public MaxSize(String index) {
        this.index = index;
    }

    public static void store() {
        new File(FILE).delete();
        ObjectContainer objectContainer = Db4o.openFile(FILE);
        long start = System.currentTimeMillis();
        long elapsed;
        for (int i = 1; i <= SIZE; i++) {
            objectContainer.store(new MaxSize("" + i));
            if (((double) i / (double) COMMIT_INTERVAL) == i / COMMIT_INTERVAL) {
                objectContainer.commit();
                objectContainer.ext().purge();
                elapsed = System.currentTimeMillis() - start; 
                System.out.println("Committed " + i + " from " + SIZE + " elapsed " + elapsed + "ms");
            }
        }
        objectContainer.close();
        elapsed = System.currentTimeMillis() - start;
        System.out.println("\nTime to store " + SIZE + " objects:\n" + elapsed + "ms");
    }
    
    public static void query() {
        
        ObjectContainer objectContainer = Db4o.openFile(FILE);
        long time = System.currentTimeMillis();
        for (int i = 1; i <= QUERIES; i++) {
            Query q = objectContainer.query();
            q.constrain(MaxSize.class);
            q.descend("index").constrain("" + i);
            q.execute();
        }
        time = System.currentTimeMillis() - time;
        objectContainer.close();
        System.out.println("\nTime for  " + QUERIES + " queries against an indexed field in " + SIZE + " objects:\n"  + time + "ms");
        double perQuery = (double)time / (double)1000;
        System.out.println("\nTime per query:\n"  + perQuery + "ms");
        int perSecond = (int)((double)1000 / perQuery);
        System.out.println("\nQueries per second:\n" + perSecond);
    }
    
    
}