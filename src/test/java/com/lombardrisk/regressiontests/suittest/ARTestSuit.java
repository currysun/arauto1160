package com.lombardrisk.regressiontests.suittest;

import com.lombardrisk.regressiontests.fortestsuit.ForBaseTest;
import com.lombardrisk.regressiontests.fortestsuit.ForBaseTest222;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ForBaseTest.class,
        ForBaseTest222.class
})
public class ARTestSuit {

}
