package vcdn.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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
//        VCDNServerApp vcdnServerApp = new VCDNServerApp();
//        vcdnServerApp.initTest();

        List<Integer> list = Arrays.asList(1, 6,1, 3, 7, 5);
        int a = list.parallelStream()
                .peek(num -> System.out.println("will filter " + num))
                .filter(x -> x > 6)
                .filter(x->x<8)
                .findFirst().orElse(-1);
                //.get();
        System.out.println(a);

//        int index = 0;
//        while (index < 1000) {
//            VCDNServerApp.logger.error("error msg");
//            VCDNServerApp.logger.info("info msg");
//            index++;
//        Thread.sleep(1000);
    }
//    }

}