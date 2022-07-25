package org.example;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ApiE2EFlow extends APITestBase {

    @Test
    public void testcase() {

        String response = APITestBase.getMMINow();
        Assert.assertEquals(response, "true",
                "API response is " + response);

        // remaining api calls
    }
}
