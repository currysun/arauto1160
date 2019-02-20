package com.lombardrisk.regressiontests.fortestsuit;

import com.lombardrisk.config.BeanConfig;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BeanConfig.class})
public class ForTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testDivisionWithException3() {

        thrown.expect(ArithmeticException.class);
        thrown.expectMessage(containsString("/ by zero"));

        int i = 1 / 0;

    }


    @Test
    public void testDivisionWithException() {
        try {
            int i = 1 / 0;

            fail();
        } catch (ArithmeticException e) {
            assertThat(e.getMessage(), is("/ by zero"));

        }
    }


    /**
     * 如果测试该方法时产生一个ArithmeticException的异常，则表示测试通过
     * 你可以改成int i = 1 / 1;运行时则会测试不通过-因为与你的期望的不符
     */
    @Test(expected = ArithmeticException.class)
    public void testDivisionWithException2() {
        int i = 1 / 0;
    }

    /**
     * 运行时抛出一个IndexOutOfBoundsException异常才会测试通过
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testEmptyList() {
        new ArrayList<>().get(0);
    }

    @Test(timeout=5000)
    public void helloTooSlow(){
        System.out.println("模拟超时测试");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }

}
