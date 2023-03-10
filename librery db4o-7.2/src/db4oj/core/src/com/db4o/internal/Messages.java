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

import java.io.*;

import com.db4o.*;
import com.db4o.config.*;


/**
 * @exclude
 */
public final class Messages {
	
	public final static int INCOMPATIBLE_FORMAT=17;
	public final static int CLOSED_OR_OPEN_FAILED=20;
	public final static int FAILED_TO_SHUTDOWN=28; 
    public final static int FATAL_MSG_ID=44;
    public final static int NOT_IMPLEMENTED=49;
    public final static int OLD_DATABASE_FORMAT=65;
    public final static int ONLY_FOR_INDEXED_FIELDS=66;
    public final static int CLIENT_SERVER_UNSUPPORTED=67;
    public final static int COULD_NOT_OPEN_PORT=30;
	public final static int SERVER_LISTENING_ON_PORT=31;
    
    private static String[] i_messages;
	
	public static String get(int a_code){
		return get(a_code, null);
	}

	public static String get(int a_code, String param){
		if(a_code < 0){
			return param;
		}
		load();
		if(i_messages == null || a_code > i_messages.length - 1){
			return "msg[" + a_code + "]";
		}
		String msg = i_messages[a_code];
		if(param != null){
			int pos = msg.indexOf("%",0);
			if(pos > -1){
				msg = msg.substring(0, pos)
					  + "'"
					  + param
					  + "'"
					  + msg.substring(pos + 1);
			}
		}
		return msg;
	}
	
	private static void load(){
	    if(i_messages == null) {
			 if(Tuning.readableMessages){
			            
		        i_messages = new String[] {
						"", // unused
						"blocksize should be between 1 and 127", 
						"% close request",
						"% closed",
						"Exception opening %",
						"% opened O.K.", // 5
						"Class %: Instantiation failed. \n Check custom ObjectConstructor code.",
						"Class %: Instantiation failed.\n Add a constructor for use with db4o, ideally with zero arguments.",
						"renaming %",
						"rename not possible. % already exists",
						"rename failed", // 10
						"File close failed.",
						"File % not available for readwrite access.",
						"File read access failed.",
						"File not found: % Creating new file",
						"Creation of file failed: %", // 15
						"File write failed.",
						"File format incompatible.",
						"Uncaught Exception. Engine closed.",
						"writing log for %",
						"% is closed. close() was called or open() failed.", // 20
						"Filename not specified.",
						"The database file is locked by another process.",
						"Class not available: %. Check CLASSPATH settings.",
						"finalized while performing a task.\n DO NOT USE CTRL + C OR System.exit() TO STOP THE ENGINE.",
						"Please mail the following to exception@db4o.com:\n <db4o " + Db4oVersion.NAME + " stacktrace>", // 25
						"</db4o " + Db4oVersion.NAME + " stacktrace>",
						"Creation of lock file failed: %",
						"Previous session was not shut down correctly",
						"This method call is only possible on stored objects",
						"Could not open port: %", // 30
						"Server listening on port: %",
						"Client % connected.",
						"Client % timed out and closed.",
						"Connection closed by client %.",
						"Connection closed by server. %.",// 35
						"% connected to server.",
						"The directory % can neither be found nor created.",
						"This blob was never stored.",
						"Blob file % not available.",
						"Failure finding blob filename.", // 40
						"File does not exist %.",
						"Failed to connect to server.",
						"No blob data stored.",
						"Uncaught Exception. db4o engine closed.",
						"Add constructor that won't throw exceptions, configure constructor calls, or provide a translator to class % and make sure the class is deployed to the server with the same package/namespace + assembly name.", // 45
						"This method can only be called before opening the database file.",
						"AccessibleObject#setAccessible() is not available. Private fields can not be stored.",
						"ObjectTranslator could not be installed: %.",
						"Not implemented",
						"% closed by ShutdownHook.", // 50
						"This constraint is not persistent. It has no database identity.",
						"Add at least one ObjectContainer to the Cluster", 
						"Unsupported Operation",
						"Database password does not match user-provided password.",
						"Thread interrupted.", // 55
						"Password can not be null.",
						"Classes does not match.", 
						"rename() needs to be executed on the server.",
						"Primitive types like % can not be stored directly. Store and retrieve them in wrapper objects.",
						"Backups can not be run from clients and memory files.", // 60
						"Backup in progress.", 
                        "Only use persisted first class objects as keys for IdentityHashMap.",
                        "This functionality is only available from version 5.0 onwards.",
                        "By convention a Predicate needs the following method: public boolean match(ExtentClass extent){}",
                        "Old database file format detected. To allow automatic updates call Db4o.configure().allowVersionUpdates(true).", // 65
                        "This functionality is only available for indexed fields.", // 66
                        "This functionality is not supported for db4o clients in Client/Server mode.", // 67
                        "Invalid address: %", 
                        "Maximum file size reached", // 69
		                };
		        }else{
		            i_messages = new String[0];
		        }
            }

    }

    public static void logErr (Configuration config, int code, String msg, Throwable t) {
    	if(config == null){
    		config = Db4o.configure();
    	}
    	PrintStream ps = ((Config4Impl)config).errStream();
    	new Message(msg, code,ps);
    	if(t != null){
    		new Message(null,25,ps);
    		t.printStackTrace(ps);
    		new Message(null,26,ps, false);
    	}
    }

    public static void logMsg (Configuration config, int code, String msg) {
    	if(Deploy.debug){
    		if(code == 0){
    			System.out.println(msg);
    			return;
    		}
    	}
    	Config4Impl c4i = (Config4Impl)config;
    	
    	if(c4i.messageLevel() > Const4.NONE){
    		new Message(msg,code,c4i.outStream());
    	}
    }
}

