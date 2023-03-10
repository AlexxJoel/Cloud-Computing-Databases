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
package com.db4o.reflect.generic;

import com.db4o.reflect.*;

/**
 * @exclude
 */
public class GenericArrayReflector implements ReflectArray{
    
    private final ReflectArray _delegate;
    
    public GenericArrayReflector(GenericReflector reflector){
        _delegate = reflector.getDelegate().array();
    }

    public int[] dimensions(Object arr) {
        return _delegate.dimensions(arr);
    }

    public int flatten(Object a_shaped, int[] a_dimensions, int a_currentDimension, Object[] a_flat, int a_flatElement) {
        return _delegate.flatten(a_shaped, a_dimensions, a_currentDimension, a_flat, a_flatElement);
    }

    public Object get(Object onArray, int index) {
        if(onArray instanceof GenericArray){
            return ((GenericArray)onArray)._data[index];
        }
        return _delegate.get(onArray, index);
    }

    public ReflectClass getComponentType(ReflectClass claxx) {
        claxx = claxx.getDelegate();
        if(claxx instanceof GenericClass){
            return claxx;
        }
        return _delegate.getComponentType(claxx);
    }

    public int getLength(Object array) {
        if(array instanceof GenericArray){
            return ((GenericArray)array).getLength();
        }
        return _delegate.getLength(array);
    }
    
    public boolean isNDimensional(ReflectClass a_class) {
        if(a_class instanceof GenericArrayClass){
            return false;
        }
        return _delegate.isNDimensional(a_class.getDelegate());
    }

    public Object newInstance(ReflectClass componentType, int length) {
        componentType = componentType.getDelegate();
        if(componentType instanceof GenericClass){
            return new GenericArray(((GenericClass)componentType).arrayClass(), length);
        }
        return _delegate.newInstance(componentType, length);
    }

    public Object newInstance(ReflectClass componentType, int[] dimensions) {
        return _delegate.newInstance(componentType.getDelegate(), dimensions);
    }

    public void set(Object onArray, int index, Object element) {
        if(onArray instanceof GenericArray){
            ((GenericArray)onArray)._data[index] = element;
            return;
        }
        _delegate.set(onArray, index, element);
    }

    public int shape(Object[] a_flat, int a_flatElement, Object a_shaped, int[] a_dimensions, int a_currentDimension) {
        return _delegate.shape(a_flat, a_flatElement, a_shaped, a_dimensions, a_currentDimension);
    }

}
