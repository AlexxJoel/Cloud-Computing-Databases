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
package com.db4o;

import java.io.*;

import com.db4o.foundation.*;
import com.db4o.internal.*;
import com.db4o.internal.handlers.*;
import com.db4o.internal.slots.*;


/**
 * @exclude 
 */
public class DTrace {
	
    public static boolean enabled = false;
    
    public static boolean writeToLogFile = false;
    
    public static boolean writeToConsole = true; 
    
	private static final String logFilePath = "C://";
	
	private static String logFileName;
	
	private static final Object lock = new Object();
	
	private static final LatinStringIO stringIO = new LatinStringIO();
	
	public static RandomAccessFile _logFile;
	
	private static int UNUSED = -1;
    
    private static void breakPoint(){
        if(enabled){ /* breakpoint here */ }
    }
    
    private static final void configure(){
        if(enabled){
        
            // breakOnEvent(5);
            
            // addRange(4874);
            
            // addRangeWithEnd(3835808, 3836267);
            
//        	 addRangeWithLength(6539,1);
//        	addRangeWithLength(17673,1);
            
//            addRangeWithLength(455404,1);
//            
//            addRangeWithLength(455570,1);
//            
//            addRangeWithLength(455926,1);
//
            
            // addRangeWithLength(20161,1);
            
            
            trackEventsWithoutRange();
            
//            turnAllOffExceptFor(new DTrace[] {YAPMETA_SET_ID});
        	
            turnAllOffExceptFor(new DTrace[] {
                PERSISTENT_OWN_LENGTH,
                });
            
//            turnAllOffExceptFor(new DTrace[] {
//                FREESPACEMANAGER_GET_SLOT,
//                FREESPACEMANAGER_RAM_FREE,
//                FREESPACEMANAGER_BTREE_FREE,
//                });
        	
//          turnAllOffExceptFor(new DTrace[] {BTREE_NODE_COMMIT_OR_ROLLBACK });
//            turnAllOffExceptFor(new DTrace[] {BTREE_NODE_REMOVE, BTREE_NODE_COMMIT_OR_ROLLBACK YAPMETA_SET_ID});
        }
    }
    
    private static final void init() {
		if (enabled) {
			ADD_TO_CLASS_INDEX = new DTrace(true, true, "add to class index tree",
					true);
			BEGIN_TOP_LEVEL_CALL = new DTrace(true, true, "begin top level call",
					true);
			BIND = new DTrace(true, true, "bind", true);
			BLOCKING_QUEUE_STOPPED_EXCEPTION = new DTrace(true, true, "blocking queue stopped exception", true);
			BTREE_NODE_REMOVE = new DTrace(true, true, "btreenode remove", true);
			BTREE_NODE_COMMIT_OR_ROLLBACK = new DTrace(true, true,
					"btreenode commit or rollback", true);
			CANDIDATE_READ = new DTrace(true, true, "candidate read", true);
            CLASSMETADATA_BY_ID = new DTrace(true, true, "classmetadata by id", true);
            CLASSMETADATA_INIT = new DTrace(true, true, "classmetadata init", true);
			CLIENT_MESSAGE_LOOP_EXCEPTION = new DTrace(true, true, "client message loop exception", true);
			CLOSE = new DTrace(true, true, "close", true);
			CLOSE_CALLED = new DTrace(true, true, "close called", true);
			COLLECT_CHILDREN = new DTrace(true, true, "collect children", true);
			COMMIT = new DTrace(false, false, "commit", true);
			CONTINUESET = new DTrace(true, true, "continueset", true);
			CREATE_CANDIDATE = new DTrace(true, true, "create candidate", true);
			DELETE = new DTrace(true, true, "delete", true);
			DONOTINCLUDE = new DTrace(true, true, "donotinclude", true);
			END_TOP_LEVEL_CALL = new DTrace(true, true, "end top level call", true);
			EVALUATE_SELF = new DTrace(true, true, "evaluate self", true);
			FATAL_EXCEPTION = new DTrace(true, true, "fatal exception", true);
			FREE = new DTrace(true, true, "free", true);
			FILE_FREE = new DTrace(true, true, "fileFree", true);
            FREESPACEMANAGER_GET_SLOT = new DTrace(true, true, "FreespaceManager getSlot", true);
            FREESPACEMANAGER_RAM_FREE = new DTrace(true, true, "RamFreeSpaceManager free", true);
            FREESPACEMANAGER_BTREE_FREE = new DTrace(true, true, "BTreeFreeSpaceManager free", true);
			FREE_ON_COMMIT = new DTrace(true, true, "trans freeOnCommit", true);
	        FREE_ON_ROLLBACK = new DTrace(true, true, "trans freeOnRollback", true);
	        FREE_POINTER_ON_ROLLBACK = new DTrace(true, true, "freePointerOnRollback", true);
	        GET_POINTER_SLOT = new DTrace(true, true, "getPointerSlot", true);
	        GET_SLOT = new DTrace(true, true, "getSlot", true);
			GET_FREESPACE_RAM = new DTrace(true, true, "getFreespaceRam", true);
			GET_YAPOBJECT = new DTrace(true, true, "get yapObject", true);
			ID_TREE_ADD = new DTrace(true, true, "id tree add", true);
			ID_TREE_REMOVE = new DTrace(true, true, "id tree remove", true);
			IO_COPY = new DTrace(true, true, "io copy", true);
			JUST_SET = new DTrace(true, true, "just set", true);
			NEW_INSTANCE = new DTrace(true, true, "newInstance", true);
			PERSISTENT_OWN_LENGTH = new DTrace(true, true, "Persistent own length",
                true);
            PERSISTENTBASE_WRITE = new DTrace(true, true, "persistentbase write", true);
            PERSISTENTBASE_SET_ID = new DTrace(true, true, "persistentbase setid", true);
            PRODUCE_SLOT_CHANGE = new DTrace(true, true, "produce slot change",
                true);
			QUERY_PROCESS = new DTrace(true, true, "query process", true);
			READ_ARRAY_WRAPPER = new DTrace(true, true, "read array wrapper", true);
			READ_BYTES = new DTrace(true, true, "readBytes", true);
			READ_ID = new DTrace(true, true, "read ID", true);
			READ_SLOT = new DTrace(true, true, "read slot", true);
			REFERENCE_REMOVED = new DTrace(true, true, "reference removed", true);
			REGULAR_SEEK = new DTrace(true, true, "regular seek", true);
			REMOVE_FROM_CLASS_INDEX = new DTrace(true, true,
					"trans removeFromClassIndexTree", true);
			REREAD_OLD_UUID = new DTrace(true, true, "reread old uuid", true);
			SERVER_MESSAGE_LOOP_EXCEPTION = new DTrace(true, true, "server message loop exception", true);
			SLOT_SET_POINTER = new DTrace(true, true, "slot set pointer", true);
			SLOT_DELETE = new DTrace(true, true, "slot delete", true);
			SLOT_FREE_ON_COMMIT = new DTrace(true, true, "slot free on commit",
					true);
			SLOT_FREE_ON_ROLLBACK_ID = new DTrace(true, true,
					"slot free on rollback id", true);
			SLOT_FREE_ON_ROLLBACK_ADDRESS = new DTrace(true, true,
					"slot free on rollback address", true);
			TRANS_COMMIT = new DTrace(false, false, "trans commit", false);
			TRANS_DELETE = new DTrace(true, true, "trans delete", true);
			TRANS_DONT_DELETE = new DTrace(true, true, "trans dontDelete", true);
			TRANS_FLUSH = new DTrace(true, true, "trans flush", true);
			WRITE_BYTES = new DTrace(true, true, "writeBytes", true);
			WRITE_POINTER = new DTrace(true, true, "write pointer", true);
			WRITE_UPDATE_DELETE_MEMBERS = new DTrace(true, true,
					"trans writeUpdateDeleteMembers", true);
			WRITE_XBYTES = new DTrace(true, true, "writeXBytes", true);
			configure();
		}
	}
    
    private static void trackEventsWithoutRange() {
        _trackEventsWithoutRange = true;
    }

    private DTrace(boolean enabled_, boolean break_, String tag_, boolean log_){
        if(enabled){
            _enabled = enabled_;
            _break = break_;
            _tag = tag_;
            _log = log_;
            if(all == null){
                all = new DTrace[100];
            }
            all[current++] = this;
        }
    }
    
    private boolean _enabled;
    private boolean _break;
    private boolean _log;
    private String _tag;
    
    private static long[] _rangeStart;
    private static long [] _rangeEnd;
    private static int _rangeCount;
    
    public static long _eventNr;
    private static long[] _breakEventNrs;
    private static int _breakEventCount;
    
    private static boolean _trackEventsWithoutRange;

    
    public static DTrace ADD_TO_CLASS_INDEX;
	public static DTrace BEGIN_TOP_LEVEL_CALL;
    public static DTrace BIND;
    public static DTrace BLOCKING_QUEUE_STOPPED_EXCEPTION;
	public static DTrace BTREE_NODE_COMMIT_OR_ROLLBACK;
	public static DTrace BTREE_NODE_REMOVE;
    public static DTrace CANDIDATE_READ;
    public static DTrace CLASSMETADATA_BY_ID;
    public static DTrace CLASSMETADATA_INIT;
    public static DTrace CLIENT_MESSAGE_LOOP_EXCEPTION;
    public static DTrace CLOSE;
    public static DTrace CLOSE_CALLED;
    public static DTrace COLLECT_CHILDREN;
    public static DTrace COMMIT;
    public static DTrace CONTINUESET;
    public static DTrace CREATE_CANDIDATE;
    public static DTrace DELETE;
    public static DTrace DONOTINCLUDE;
	public static DTrace END_TOP_LEVEL_CALL;
    public static DTrace EVALUATE_SELF;
    public static DTrace FATAL_EXCEPTION;
    public static DTrace FILE_FREE;
    public static DTrace FREE;
    public static DTrace FREESPACEMANAGER_GET_SLOT;
    public static DTrace FREESPACEMANAGER_RAM_FREE;
    public static DTrace FREESPACEMANAGER_BTREE_FREE;
    public static DTrace FREE_ON_COMMIT;
    public static DTrace FREE_ON_ROLLBACK;
    public static DTrace FREE_POINTER_ON_ROLLBACK;
    public static DTrace GET_SLOT;
    public static DTrace GET_POINTER_SLOT;
    public static DTrace GET_FREESPACE_RAM;
    public static DTrace GET_YAPOBJECT;
    public static DTrace ID_TREE_ADD;
    public static DTrace ID_TREE_REMOVE;
    public static DTrace IO_COPY;
    public static DTrace JUST_SET;
    public static DTrace NEW_INSTANCE;
    public static DTrace PERSISTENT_OWN_LENGTH;
    public static DTrace PERSISTENTBASE_SET_ID;
    public static DTrace PERSISTENTBASE_WRITE;
	public static DTrace PRODUCE_SLOT_CHANGE;
    public static DTrace QUERY_PROCESS;
    public static DTrace READ_ARRAY_WRAPPER;
    public static DTrace READ_BYTES;
    public static DTrace READ_ID;
    public static DTrace READ_SLOT;
    public static DTrace REFERENCE_REMOVED;
    public static DTrace REGULAR_SEEK;
    public static DTrace REMOVE_FROM_CLASS_INDEX;
    public static DTrace REREAD_OLD_UUID;
    public static DTrace SERVER_MESSAGE_LOOP_EXCEPTION;
	public static DTrace SLOT_SET_POINTER;
	public static DTrace SLOT_DELETE;
	public static DTrace SLOT_FREE_ON_COMMIT;
	public static DTrace SLOT_FREE_ON_ROLLBACK_ID;
	public static DTrace SLOT_FREE_ON_ROLLBACK_ADDRESS;
    public static DTrace TRANS_COMMIT;
    public static DTrace TRANS_DONT_DELETE;
    public static DTrace TRANS_DELETE;
    public static DTrace TRANS_FLUSH;
    public static DTrace WRITE_BYTES;
    public static DTrace WRITE_POINTER;
    public static DTrace WRITE_XBYTES;
    public static DTrace WRITE_UPDATE_DELETE_MEMBERS;
    
    static{
    	init();
    }
	
    private static DTrace[] all;
    private static int current;
    
    public void log(){
        if(enabled){
            log(UNUSED);
        }
    }
    
    public void logStack(String msg){
    	if(enabled){
    		log(msg);
    		log(Platform4.stackTrace());
    	}
    }
    
    public void log(String msg){
        if(enabled){
            log(UNUSED, msg);
        }
    }
    
    public void log(long p){
        if(enabled){
            logLength(p, 1);
        }
    }
    
    public void logInfo(String info){
        if(enabled){
            logEnd(UNUSED,0, info );
        }
    }
    
    public void log(long p, String info){
        if(enabled){
            logEnd(p, 0, info);
        }
        
    }
    
    public void logLength(long start, long length){
        if(enabled){
            logEnd(start, start + length - 1);
        }
    }
    
    public void logLength(Slot slot){
    	if(enabled){
    		logLength(slot.address(), slot.length());
    	}
    }
    
    public void logEnd(long start, long end){
        if(enabled){
            logEnd(start, end, null);
        }
    }
    
    public void logEnd(long start, long end, String info){
        if(enabled){
            if(! _enabled){
                return;
            }
            boolean inRange = false;
            
            if(_rangeCount == 0){
                inRange = true;
            }
            
            for (int i = 0; i < _rangeCount; i++) {
                
                // Case 1 start in range
                if(start >= _rangeStart[i] && start <= _rangeEnd[i]){
                    inRange = true;
                    break;
                }
                
                if(end != 0){
                    
                    // Case 2 end in range
                    if (end >= _rangeStart[i] && end <= _rangeEnd[i]){
                        inRange = true;
                        break;
                    }
                    
                    // Case 3 start before range, end after range
                    if(start <= _rangeStart[i]  && end >= _rangeEnd[i]){
                        inRange = true;
                        break;
                    }
                }
            }
            if(inRange || ( _trackEventsWithoutRange &&  (start == UNUSED) )){
                if(_log){
                    _eventNr ++;
                    StringBuffer sb = new StringBuffer(":");
                    sb.append(formatInt(_eventNr, 6));
                    sb.append(":");
                    sb.append(formatInt(start));
                    sb.append(":");
                    if(end != 0  && start != end){
                        sb.append(formatInt(end));
                        sb.append(":");
                        sb.append(formatInt(end - start + 1));
                    }else{
                        sb.append(formatUnused());
                        sb.append(":");
                        sb.append(formatUnused());
                    }
                    sb.append(":");
                    if(info != null){
                        sb.append(" " + info + " ");
                        sb.append(":");
                    }
                    sb.append(" ");
                    sb.append(_tag);
                    logToOutput(sb.toString());
                }
                if(_break){
                    if(_breakEventCount > 0){
                        for (int i = 0; i < _breakEventCount; i++) {
                            if(_breakEventNrs[i] == _eventNr){
                                breakPoint();
                                break;
                            }
                        }
                    }else{
                        breakPoint();
                    }
                }
            }
        }
    }

    private String formatUnused() {
        return formatInt(UNUSED);
    }
    
    private static void logToOutput(String msg){
    	if(enabled){
	    	logToFile(msg);
	    	logToConsole(msg);
    	}
    }
    
    private static void logToConsole(String msg){
    	if(enabled){
	    	if(writeToConsole){
	    		System.out.println(msg);	
	    	}
    	}
    }
    
    private static void logToFile(String msg){
    	if(enabled){
	    	if(! writeToLogFile){
	    		return;
	    	}
	    	synchronized(lock){

		    	if(_logFile == null){
		    		try {
						_logFile = new RandomAccessFile(logFile(), "rw");
						logToFile("\r\n\r\n ********** BEGIN LOG ********** \r\n\r\n ");
					} catch (IOException e) {
						e.printStackTrace();
					}
		    	}
		    	msg = DateHandlerBase.now() + "\r\n" + msg + "\r\n";
		    	byte[] bytes = stringIO.write(msg);
		    	try {
					_logFile.write(bytes);
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	}
    }
    
    private static String logFile(){
    	if(enabled){
	    	if(logFileName != null){
	    		return logFileName;
	    	}
	    	logFileName = 
	    		"db4oDTrace_" + 
	    		DateHandlerBase.now() +
	    		"_" +  
	    		SignatureGenerator.generateSignature() +
	    		".log";
	    	logFileName = logFileName.replace(' ', '_');
	    	logFileName = logFileName.replace(':', '_');
	    	logFileName = logFileName.replace('-', '_');
	    	return logFilePath  + logFileName;
    	}
    	return null;
    }
    
    public static void addRange(long pos){
        if(enabled){
            addRangeWithEnd(pos, pos);
        }
    }
    
    public static void addRangeWithLength(long start, long length){
        if(enabled){
            addRangeWithEnd(start, start + length - 1);
        }
    }
    
    public static void addRangeWithEnd(long start, long end){
        if(enabled){
            if(_rangeStart == null){
                _rangeStart = new long[100];
                _rangeEnd = new long[100];
            }
            _rangeStart[_rangeCount] = start;
            _rangeEnd[_rangeCount] = end;
            _rangeCount++;
        }
    }
    
    private static void breakOnEvent(long eventNr){
        if(enabled){
            if(_breakEventNrs == null){
                _breakEventNrs = new long[100];
            }
            _breakEventNrs[_breakEventCount] = eventNr;
            _breakEventCount ++;
        }
    }
    
    
    private String formatInt(long i, int len){
        if(enabled){
            String str = "              ";
            if( i != UNUSED){
                str += i + " ";
            }
            return str.substring(str.length() - len);
        }
        return null;
    }
    
    private String formatInt(long i){
        if(enabled){
            return formatInt(i, 10);
        }
        return null;
    }
    
    private static void turnAllOffExceptFor(DTrace[] these){
        if(enabled){
            for (int i = 0; i < all.length; i++) {
                if(all[i] == null){
                    break;
                }
                boolean turnOff = true;
                for (int j = 0; j < these.length; j++) {
                    if(all[i] == these[j]){
                        turnOff = false;
                        break;
                    }
                }
                if(turnOff){
                    all[i]._break = false;
                    all[i]._enabled = false;
                    all[i]._log = false;
                }
            }
        }
    }
    
    public static void noWarnings(){
    	breakOnEvent(0);
    	trackEventsWithoutRange();
    }

    
}
