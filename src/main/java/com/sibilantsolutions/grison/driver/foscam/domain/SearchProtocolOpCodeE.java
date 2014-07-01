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
        final static public int Search_Req  = 0;    //user -> broadcast address
        final static public int Search_Resp = 1;    //camera -> broadcast address
        final static public int Init_Req    = 2;    //user -> broadcast address
        final static public int Init_Resp   = 3;    //camera -> broadcast address
    }

    private int value;

    private SearchProtocolOpCodeE( int value )
    {
        this.value = value;
    }

    public static SearchProtocolOpCodeE fromValue( int value )
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
    public int getValue()
    {
        return value;
    }

    @Override
    public DatastreamI parse( String data )
    {
        switch ( this )
        {
            case Search_Resp:
                return SearchRespText.parse( data );

            case Init_Resp:
                return InitRespText.parse( data );

            default:
                throw new IllegalArgumentException( "Unexpected value=" + this );
        }
    }

}
