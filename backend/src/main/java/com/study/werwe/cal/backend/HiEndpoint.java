package com.study.werwe.cal.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;

import java.util.logging.Logger;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "hiApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.cal.werwe.study.com",
                ownerName = "backend.cal.werwe.study.com",
                packagePath = ""
        )
)
public class HiEndpoint {

    private static final Logger logger = Logger.getLogger(HiEndpoint.class.getName());

    @ApiMethod(name = "hi")
    public Hi hi(@Named("name") String name) {
        logger.info("Calling insertHi method : " + name);
        return new Hi("권성저");
    }
}