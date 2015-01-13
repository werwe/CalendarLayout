package com.study.werwe.cal.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "hiApi",
        version = "v1",
        resource = "hi",
        namespace = @ApiNamespace(
                ownerDomain = "backend.cal.werwe.study.com",
                ownerName = "backend.cal.werwe.study.com",
                packagePath = ""
        )
)
public class HiEndpoint {

    private static final Logger logger = Logger.getLogger(HiEndpoint.class.getName());

//    /**
//     * This method gets the <code>Hi</code> object associated with the specified <code>id</code>.
//     *
//     * @param id The id of the object to be returned.
//     * @return The <code>Hi</code> associated with <code>id</code>.
//     */
//    @ApiMethod(name = "getHi")
//    public Hi getHi(@Named("id") Long id) {
//        // TODO: Implement this function
//        logger.info("Calling getHi method");
//        return null;
//    }

    /**
     * This inserts a new <code>Hi</code> object.
     *
     * @param hi The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertHi")
    public Hi insertHi(Hi hi) {
        // TODO: Implement this function
        logger.info("Calling insertHi method");
        return hi;
    }
}