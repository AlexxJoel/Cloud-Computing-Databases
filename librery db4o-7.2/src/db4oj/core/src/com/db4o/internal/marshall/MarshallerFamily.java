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
package com.db4o.internal.marshall;

import com.db4o.internal.convert.conversions.*;

/**
 * Represents a db4o file format version, assembles all the marshallers
 * needed to read/write this specific version.
 * 
 * A marshaller knows how to read/write certain types of values from/to its
 * representation on disk for a given db4o file format version.
 * 
 * Responsibilities are somewhat overlapping with TypeHandler's.
 * 
 * @exclude
 */
public class MarshallerFamily {
    
    
    public static class FamilyVersion{
        
        public static final int PRE_MARSHALLER = 0;
        
        public static final int MARSHALLER = 1;
        
        public static final int BTREE_FIELD_INDEXES = 2; 
        
    }
    
    private static int CURRENT_VERSION = FamilyVersion.BTREE_FIELD_INDEXES;
    
    public final ArrayMarshaller _array;
    
    public final ClassMarshaller _class;
    
    public final FieldMarshaller _field;
    
    public final ObjectMarshaller _object;

    public final PrimitiveMarshaller _primitive;

    public final StringMarshaller _string;
    
    public final UntypedMarshaller _untyped;

    private final int _converterVersion;
    
    private final int _handlerVersion;

    private final static MarshallerFamily[] allVersions = new MarshallerFamily[] {
        
        // LEGACY => before 5.4
        
        new MarshallerFamily(
            0,
            0,
            new ArrayMarshaller0(),
            new ClassMarshaller0(),
            new FieldMarshaller0(),
            new ObjectMarshaller0(), 
            new PrimitiveMarshaller0(),
            new StringMarshaller0(),
            new UntypedMarshaller0()),
        
        new MarshallerFamily(
            ClassIndexesToBTrees_5_5.VERSION,
            1,
            new ArrayMarshaller1(),
            new ClassMarshaller1(),
            new FieldMarshaller0(),
            new ObjectMarshaller1(), 
            new PrimitiveMarshaller1(),
            new StringMarshaller1(),
            new UntypedMarshaller1()),
    
            latestFamily(2),
            latestFamily(3),
        
        };

    public MarshallerFamily(
            int converterVersion,
            int handlerVersion,
            ArrayMarshaller arrayMarshaller,
            ClassMarshaller classMarshaller,
            FieldMarshaller fieldMarshaller,
            ObjectMarshaller objectMarshaller,
            PrimitiveMarshaller primitiveMarshaller, 
            StringMarshaller stringMarshaller,
            UntypedMarshaller untypedMarshaller) {
        _converterVersion = converterVersion;
        _handlerVersion = handlerVersion;
        _array = arrayMarshaller;
        _array._family = this;
        _class = classMarshaller;
        _class._family = this;
        _field = fieldMarshaller;
        _object = objectMarshaller;
        _object._family = this;
        _primitive = primitiveMarshaller;
        _primitive._family = this;
        _string = stringMarshaller;
        _untyped = untypedMarshaller;
        _untyped._family = this;
    }
    
    public static MarshallerFamily latestFamily(int version){
        return new MarshallerFamily(
            FieldIndexesToBTrees_5_7.VERSION,
            version,
            new ArrayMarshaller1(),
            new ClassMarshaller2(),
            new FieldMarshaller1(),
            new ObjectMarshaller1(), 
            new PrimitiveMarshaller1(),
            new StringMarshaller1(),
            new UntypedMarshaller1());
    }

    public static MarshallerFamily version(int n) {
        return allVersions[n];
    }

    public static MarshallerFamily current() {
        if(CURRENT_VERSION < FamilyVersion.BTREE_FIELD_INDEXES){
            throw new IllegalStateException("Using old marshaller versions to write database files is not supported, source code has been removed.");
        }
        return version(CURRENT_VERSION);
    }
    
    public static MarshallerFamily forConverterVersion(int n){
        MarshallerFamily result = allVersions[0];
        for (int i = 1; i < allVersions.length; i++) {
            if(allVersions[i]._converterVersion > n){
                return result;
            }
            result = allVersions[i]; 
        }
        return result;
    }
    
    public int handlerVersion(){
    	return _handlerVersion;
    }
    
}
