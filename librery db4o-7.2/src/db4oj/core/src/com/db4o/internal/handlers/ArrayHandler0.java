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
package com.db4o.internal.handlers;

import java.io.*;

import com.db4o.*;
import com.db4o.ext.*;
import com.db4o.internal.*;
import com.db4o.internal.marshall.*;
import com.db4o.internal.query.processor.*;
import com.db4o.internal.slots.*;
import com.db4o.marshall.*;


/**
 * @exclude
 */
public class ArrayHandler0 extends ArrayHandler2 {

    public void delete(DeleteContext context) throws Db4oIOException {
    	context.readSlot();
    	context.defragmentRecommended();
    }
    
    public void readCandidates(int handlerVersion, ByteArrayBuffer reader, QCandidates candidates) throws Db4oIOException {
        Transaction transaction = candidates.transaction();
        ByteArrayBuffer arrayBuffer = reader.readEmbeddedObject(transaction);
        if(Deploy.debug){
            arrayBuffer.readBegin(identifier());
        }
        int count = elementCount(transaction, arrayBuffer);
        for (int i = 0; i < count; i++) {
            candidates.addByIdentity(new QCandidate(candidates, null, arrayBuffer.readInt(), true));
        }
    }

    public Object read(ReadContext readContext) {
        
        InternalReadContext context = (InternalReadContext) readContext;
        
        ByteArrayBuffer buffer = (ByteArrayBuffer) context.readIndirectedBuffer(); 
        if (buffer == null) {
            return null;
        }
        
        // With the following line we ask the context to work with 
        // a different buffer. Should this logic ever be needed by
        // a user handler, it should be implemented by using a Queue
        // in the UnmarshallingContext.
        
        // The buffer has to be set back from the outside!  See below
        ReadWriteBuffer contextBuffer = context.buffer(buffer);
        
        Object array = super.read(context);
        
        // The context buffer has to be set back.
        context.buffer(contextBuffer);
        
        return array;
    }
    
    public static void defragment(DefragmentContext context, ArrayHandler handler) {
        int sourceAddress = context.sourceBuffer().readInt();
        int length = context.sourceBuffer().readInt();
        if(sourceAddress == 0 && length == 0) {
            context.targetBuffer().writeInt(0);
            context.targetBuffer().writeInt(0);
            return;
        }
        Slot slot = context.allocateMappedTargetSlot(sourceAddress, length);
        ByteArrayBuffer sourceBuffer = null;
        try {
            sourceBuffer = context.sourceBufferByAddress(sourceAddress, length);
        }
        catch (IOException exc) {
            throw new Db4oIOException(exc);
        }
        DefragmentContextImpl payloadContext = new DefragmentContextImpl(sourceBuffer, (DefragmentContextImpl) context);
        handler.defrag1(payloadContext);
        payloadContext.writeToTarget(slot.address());
        context.targetBuffer().writeInt(slot.address());
        context.targetBuffer().writeInt(length);
    }
    
    public void defragment(DefragmentContext context) {
        defragment(context, this);
    }

    public void defrag2(DefragmentContext context) {
		int elements = readElementsDefrag(context);
		for (int i = 0; i < elements; i++) {
		    delegateTypeHandler().defragment(context);
		}
    }
}
