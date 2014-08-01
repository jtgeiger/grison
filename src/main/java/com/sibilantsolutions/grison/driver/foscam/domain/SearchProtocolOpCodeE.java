package com.sibilantsolutions.grison.driver.foscam.domain;

public enum SearchProtocolOpCodeE implements OpCodeI
{

    Search_Req( Values.Search_Req ),
    Search_Resp( Values.Search_Resp ),
    Init_Req( Values.Init_Req ),
    Init_Resp( Values.Init_Resp ),
    ;

    static private interface Values
    {
        final static public short Search_Req  = 0;    //user -> broadcast address
        final static public short Search_Resp = 1;    //camera -> broadcast address
        final static public short Init_Req    = 2;    //user -> broadcast address
        final static public short Init_Resp   = 3;    //camera -> broadcast address
    }

    private short value;

    private SearchProtocolOpCodeE( short value )
    {
        this.value = value;
    }

    public static SearchProtocolOpCodeE fromValue( short value )
    {
        switch ( value )
        {
            case Values.Search_Req:
                return Search_Req;

            case Values.Search_Resp:
                return Search_Resp;

            case Values.Init_Req:
                return Init_Req;

            case Values.Init_Resp:
                return Init_Resp;

            default:
                throw new IllegalArgumentException( "Unexpected value=" + value );
        }
    }

    @Override
    public short getValue()
    {
        return value;
    }

    @Override
    public DatastreamI parse( byte[] data, int offset, int length )
    {
        switch ( this )
        {
            case Search_Resp:
                return SearchRespText.parse( data, offset, length );

            case Init_Resp:
                return InitRespText.parse( data, offset, length );

            default:
                throw new IllegalArgumentException( "Unexpected value=" + this );
        }
    }

}
