package org.openscada.opc.da.impl;

import java.net.UnknownHostException;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JICallObject;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIString;
import org.openscada.opc.da.Constants;
import org.openscada.opc.da.OPCGroupState;

public class OPCGroup
{
    private IJIComObject _opcGroupStateMgt = null;

    public OPCGroup ( IJIComObject opcGroup ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        _opcGroupStateMgt = (IJIComObject)opcGroup.queryInterface ( Constants.IOPCGroupStateMgt_IID );
    }
    
    public OPCGroupState getState () throws JIException
    {
        JICallObject callObject = new JICallObject ( _opcGroupStateMgt.getIpid (), true );
        callObject.setOpnum ( 0 );
        
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Boolean.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIString ( JIFlags.FLAG_REPRESENTATION_STRING_BSTR ), JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Float.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        
        Object result [] = _opcGroupStateMgt.call ( callObject );
        
        OPCGroupState state = new OPCGroupState ();
        state.setUpdateRate ( (Integer)result[0] );
        state.setActive ( (Boolean )result[1] );
        state.setName ( ((JIString)result[2]).getString () );
        state.setTimeBias ( (Integer) result[3] );
        state.setPercentDeadband ( (Float)result[4] );
        state.setLocaleID ( (Integer)result[5] );
        state.setClientHandle ( (Integer)result[6] );
        state.setServerHandle ( (Integer)result[7] );
        
        return state;
    }
    
    public OPCItemMgt getItemManagement () throws IllegalArgumentException, UnknownHostException, JIException
    {
        return new OPCItemMgt ( _opcGroupStateMgt );
    }
    
    public void setName ( String name ) throws JIException
    {
        JICallObject callObject = new JICallObject ( _opcGroupStateMgt.getIpid (), true );
        callObject.setOpnum ( 2 );
        
        callObject.addInParamAsString ( name, JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );
        
        _opcGroupStateMgt.call ( callObject );
    }
}
