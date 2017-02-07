package vcdn.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jack on 2017/1/19.
 */
public class VCDNServerAppTest {
    @Before
    public void setUp() throws Exception {
//        VCDNServerApp vcdnServerApp = new VCDNServerApp();


    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void init() throws Exception {
        VCDNServerApp vcdnServerApp = new VCDNServerApp();
        vcdnServerApp.initTest();
//        int index = 0;
//        while (index < 1000) {
//            VCDNServerApp.logger.error("error msg");
//            VCDNServerApp.logger.info("info msg");
//            index++;
//        Thread.sleep(1000);
        }
//    }

}