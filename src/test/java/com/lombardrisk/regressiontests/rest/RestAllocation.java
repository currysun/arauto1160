package com.lombardrisk.regressiontests.rest;

import com.jayway.restassured.RestAssured;
import com.lombardrisk.config.BeanConfig;
import com.lombardrisk.config.StepDef;
import com.lombardrisk.page.LoginPage;
import com.lombardrisk.rest.gv.DatasetContent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BeanConfig.class})
public class RestAllocation extends StepDef {

    @Autowired
    protected LoginPage loginPage;

    @Autowired
    private DatasetContent datasetContent;

    @Before
    public void setUp(){

        RestAssured.useRelaxedHTTPSValidation();
        datasetContent.authorize("admin","password");
    }

    @Test
    //https://10.20.0.12:8443/agilereporter/rest/api/HKMA/allocationReportContent?formInstanceId=364361
    public void checkPageSize(){
//        String body = datasetContent.getResponse("/agilereporter/rest/api/HKMA/allocationReportContent?formInstanceId=????","430398").asString();
//        assertThat(from(body).get("totalRowCount"),is(4173));
        Map<String,String> map = new HashMap<>();
        map.put("formInstanceId","430398");
        map.put("search","HKCCY=CNY");
        map.put("sort","QA_AccrualAmount");
        //JsonPath jp =datasetContent.getJosnPathWithCond(map);
        //assertThat(jp.get("pageSize"),is(51));
        datasetContent.callReportsWithCond(map);


    }

    @Test
    //https://10.20.0.12:8443/agilereporter/rest/api/HKMA/allocationReportContent?formInstanceId=364361
    public void checkColumnName(){
        //JsonPath jp =datasetContent.getJosnPath("/agilereporter/rest/api/HKMA/allocationReportContent?formInstanceId=????","430398");
       // assertThat(jp.get("header.columnName"),hasItem("S_Amount"));

    }

    @Test
    //https://10.20.0.12:8443/agilereporter/rest/api/HKMA/allocationReportContent?formInstanceId=364361
    public void check(){
        Map<String,String> map = new HashMap<>();
        map.put("formInstanceId","430398");

        //JsonPath jp =datasetContent.getJosnPathWithCond(map);

//        List<String> columnList = Arrays.asList("STBITEM","STBGROUP","STBInstance","HKGLC1","HKGLC2","HKGLC3","S_Amount","S_DealRef","HKGLC4","HKBOOK","HKCCY",
//                "HKACCAMNT","STBDrillRef","STBDrillTable","HKINST","HKBRANCHCTRY","HKCCYBASE","S_HK_Premium","HKIMATBAND","QA_AccrualAmount","QA_AmountCurrency",
//                "QA_BadDebtProvisions","QA_BalShtInd","QA_DrillRef","STBIMPINDEX");

        //jp.getList("header.columnName.findAll{columnName -> columnName.startsWith('QA_') }")
        //jp.getList("header.columnName.findAll{columnName -> columnName.contains('QA_') }")

        //assertThat(jp.getList("header.columnName.findAll{columnName -> columnName.startsWith('QA_') }"),hasSize(5));

//        assertThat(Arrays.asList("foo", "bar"), containsInAnyOrder(Arrays.asList(equalTo("bar"), equalTo("foo"))));
//        assertThat(Arrays.asList("foo", "bar"), containsInAnyOrder("bar", "foo"));

//        assertThat(jp.getList("header.columnName.findAll{columnName -> columnName.startsWith('QA_') }"),
//                containsInAnyOrder("QA_BadDebtProvisions","QA_AmountCurrency","QA_BalShtInd","QA_AccrualAmount","QA_DrillRef"));


    }

    @Test
    //https://10.20.0.12:8443/agilereporter/rest/api/HKMA/allocationReportContent?formInstanceId=364361
    public void check1(){
        datasetContent.authorize("admin","password");
        //String body = datasetContent.getResponse("/agilereporter/rest/api/HKMA/allocationReportContent?formInstanceId=????","364361").asString();

        //Also same as: from(body).getList("header.visible.findAll{visible -> visible == true}")
        //assertThat(from(body).getList("header.findAll { it.visible == true}.columnName"),hasSize(25));
    }

    @Test
    //https://10.20.0.12:8443/agilereporter/rest/api/HKMA/allocationReportContent?formInstanceId=364361
    public void check2(){
        datasetContent.authorize("admin","password");
        //String body = datasetContent.getResponse("/agilereporter/rest/api/HKMA/allocationReportContent?formInstanceId=????","364361").asString();
        //from(body).get("data[1]");

        //Also same as: from(body).getList("header.visible.findAll{visible -> visible == true}")

    }

    @Test
    //https://10.20.0.12:8443/agilereporter/rest/api/HKMA/allocationReportContent?formInstanceId=364361
    public void checkSearch(){
        Map<String,String> map = new HashMap<>();
        map.put("formInstanceId","430398");
        map.put("search","HKCCY=CNY");
        //map.put("sort","QA_AccrualAmount");

//        String path = "data.HKCCY.value.findAll{value -> value =='CNY'}";
//
        //JsonPath jp =datasetContent.getJosnPathWithCond(map);
       // String respTxt = datasetContent.getRespTxtWithCond(map);

//
//        assertThat(jp.getList(path),hasSize(51));
//        assertThat(jp.getInt(path),is(51));

        //String t=from(respTxt).getJsonObject("data[1]").toString();


//        jp.getList("data");
//        //jp.getObject("data[1]",HKIB_HKIBREJ.class);
//
//
//
//        //JSONObject obj = jp.getJsonObject("data[1]");
//        String txt = jp.get("data[1]").toString();
//        //txt.replaceAll("=")
//        String txt1 = jp.getJsonObject("data[1]").toString();
//
//        //List ls = jp.getList("data[1]");
//        Map<String,Map<String,String>> map1 = jp.getMap("data[0]");
//        List ls = new ArrayList();
//        ls.add(map1.get("STBIMPINDEX").get("value"));
//        ls.add(map1.get("STBInstance").get("value"));
//        ls.add(map1.get("STBGROUP").get("value"));
//        ls.add(map1.get("HKGLC1").get("value"));
//        ls.add(map1.get("HKGLC2").get("value"));
//        ls.add(map1.get("HKINST").get("value"));
//        ls.add(map1.get("QA_CustomerID").get("value"));
//        ls.add(map1.get("QA_AmountCurrency").get("value"));
//        ls.add(map1.get("HKMATDATE").get("value"));
//        ls.add(map1.get("HKCCY").get("value"));
//        ls.add(map1.get("S_DealRef").get("value"));
//        ls.add(map1.get("STBDrillRef").get("value"));
//        ls.add(map1.get("HKBRANCHCTRY").get("value"));
//        ls.add(map1.get("STBITEM").get("value"));
//        ls.add(map1.get("S_Amount").get("value"));
//        ls.add(map1.get("QA_AccrualAmount").get("value"));
//        ls.add(map1.get("QA_MaturityDate").get("value"));
//        ls.add(map1.get("HKACCAMNT").get("value"));




        //将String类型转化为JSON
//        JSONObject obj = (JSONObject) JSONValue.parse(respTxt);
//        obj.get("data");
//
        System.out.println("hi");

    }





    @Test
    //http://localhost:8085/product/detail.do?productId=28
    public void productDetail(){
        given().param("productId",28)
                .when().get("/product/detail.do")
                .then().body("data.id",equalTo(28),
                "data.categoryId",equalTo(100012),
                "status",equalTo(0));

    }


//    @Test
//    public void test(){
//        Response response = given().contentType("application/x-www-form-urlencoded").param("username","admin")
//                .param("password","admin")
//                .post("http://localhost:8085/user/login.do");
//        response.prettyPrint();
//
//    }

//    @Test
//    public void testLogin(){
//        alloctaionTest.authorize("admin","password");
//        alloctaionTest.getResponseBody("/agilereporter/reporter/page/reporter.xhtml").print();
//    }


}
