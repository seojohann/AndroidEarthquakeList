package com.jseo.earthquakelist;

/**
 * Created by seojohann on 4/26/17.
 */

public class GeoJsonParser extends DataParser {

    @Override
    void parse() {



        OnParseCompleteListener listener = getOnParseCompleteListener();
        if (listener != null) {
            listener.onParseComplete();
        }
    }
}
