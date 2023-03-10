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
package com.db4o.reflect.jdk;

import com.db4o.reflect.*;

/**
 * db4o wrapper for JDK reflector functionality
 * @see com.db4o.ext.ExtObjectContainer#reflector()
 * @see com.db4o.reflect.generic.GenericReflector
 * 
 * @sharpen.ignore
 */
public class JdkReflector implements Reflector{
	
    private final JdkLoader _classLoader;
    private Reflector _parent;
    private ReflectArray _array;
    
    /**
     * Constructor
     * @param classLoader class loader
     */
	public JdkReflector(ClassLoader classLoader){
		this(new ClassLoaderJdkLoader(classLoader));
	}
	
	/**
     * Constructor
     * @param classLoader class loader
     */
	public JdkReflector(JdkLoader classLoader){
		_classLoader = classLoader;
	}
	
	/**
	 * ReflectArray factory
	 * @return ReflectArray instance
	 */
	public ReflectArray array(){
        if(_array == null){
            _array = new JdkArray(_parent);
        }
		return _array;
	}
	
	/**
	 * Method stub
	 * @return true
	 */
	public boolean constructorCallsSupported(){
		return true;
	}
    
	/**
	 * Creates a copy of the object
	 * @param obj object to copy
	 * @return object copy
	 */
    public Object deepClone(Object obj) {
        return new JdkReflector(_classLoader);
    }
	
    /**
     * Returns ReflectClass for the specified class
     * @param clazz class 
     * @return ReflectClass for the specified class
     */
	public ReflectClass forClass(Class clazz){
        return new JdkClass(_parent, clazz);
	}
	
	/**
     * Returns ReflectClass for the specified class name
     * @param className class name
     * @return ReflectClass for the specified class name
     */
	public ReflectClass forName(String className) {
		Class clazz = _classLoader.loadClass(className);
		if (clazz == null) {
			return null;
		}
		return new JdkClass(_parent, clazz);
	}
	
	/**
     * Returns ReflectClass for the specified class object
     * @param a_object class object
     * @return ReflectClass for the specified class object
     */
	public ReflectClass forObject(Object a_object) {
		if(a_object == null){
			return null;
		}
		return _parent.forClass(a_object.getClass());
	}
	
	/**
	 * Method stub. Returns false.
	 */
	public boolean isCollection(ReflectClass candidate) {
		return false;
	}

	/**
	 * Method stub. Returns false.
	 */
	public boolean methodCallsSupported(){
		return true;
	}

	/**
	 * Sets parent reflector
	 * @param reflector parent reflector
	 */
    public void setParent(Reflector reflector) {
        _parent = reflector;
    }

    /**
     * Creates ReflectClass[] array from the Class[]
     * array using the reflector specified 
     * @param reflector reflector to use
     * @param clazz class
     * @return ReflectClass[] array 
     */
    public static ReflectClass[] toMeta(Reflector reflector, Class[] clazz){
        ReflectClass[] claxx = null;
        if(clazz != null){
            claxx = new ReflectClass[clazz.length];
            for (int i = 0; i < clazz.length; i++) {
                if(clazz[i] != null){
                    claxx[i] = reflector.forClass(clazz[i]);
                }
            }
        }
        return claxx;
    }
    
    
    /**
     * Creates Class[] array from the ReflectClass[]
     * array  
     * @param claxx ReflectClass array
     * @return Class[] array 
     */
    static Class[] toNative(ReflectClass[] claxx){
        Class[] clazz = null;
        if(claxx != null){
            clazz = new Class[claxx.length];
            for (int i = 0; i < claxx.length; i++) {
                clazz[i] = toNative(claxx[i]);
            }
        }
        return clazz;
    }
 
    /**
     * Translates a ReflectClass into a native Class
     * @param claxx ReflectClass to translate
     * @return Class 
     */
    public static Class toNative(ReflectClass claxx){
        if(claxx == null){
            return null;
        }
        if(claxx instanceof JavaReflectClass){
            return ((JavaReflectClass)claxx).getJavaClass();
        }
        ReflectClass d = claxx.getDelegate();
        if(d == claxx){
            return null;
        }
        return toNative(d);
    }

}
