package com.zjwc.ucar_api;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.happycar.api.Api;
import com.happycar.api.service.CommissionService;
import com.happycar.api.utils.SpringContextUtil;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */

public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
        final ApplicationContext applicationContext = SpringApplication.run(Api.class, new String[]{});
    	SpringContextUtil.setApplicationContext(applicationContext);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	
        assertTrue( true );
    }
    
    public void testCommissionService(){
    	CommissionService commissionService = SpringContextUtil.getApplicationContext().getBean(CommissionService.class);
    	commissionService.allotBySignupPaymentId(20);
    }
}
