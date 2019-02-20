package com.lombardrisk.regressiontests.rest;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.authentication.FormAuthConfig;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.path.xml.XmlPath;
import com.jayway.restassured.response.Cookie;
import com.jayway.restassured.response.Cookies;
import com.jayway.restassured.response.Response;
import com.lombardrisk.config.BeanConfig;
import com.lombardrisk.config.StepDef;
import com.lombardrisk.page.LoginPage;
import com.lombardrisk.rest.gv.DatasetContent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BeanConfig.class})
public class RestTest extends StepDef {

    @Autowired
    protected LoginPage loginPage;

    @Autowired
    private DatasetContent datasetContent;

//    @Before
//    public void setUp(){
//        loginPage.open();
//        loginPage.login("client","password");
//    }

    @Test
    public void testLogin(){


        given().contentType("application/x-www-form-urlencoded").param("username","admin")
                .param("password","admin").
                expect().statusCode(200).
                body("data.username" ,equalTo("admin"),
                "msg", equalTo("Welcome"),
                "data.phone",equalTo("13800138000"))
                .when()
                .post("/user/login.do");
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


    @Test
    public void test(){
        //Response response =get("http://localhost:8080/agilereporter/core/page/login.xhtml");




        Response response = given().relaxedHTTPSValidation().auth().form("test","password",new FormAuthConfig("http://localhost:8080/agilereporter/core/page/login.xhtml?dswid=-9720",
                    "loginForm\\:inputUsername",
                    "loginForm\\:inputPassword"))
                    .when()
                    .get("http://localhost:8080/agilereporter/core/page/login.xhtml");
        response.prettyPrint();
        System.out.println("hi");

    }



    @Test
    public void test1(){

        Response response = given().contentType("application/x-www-form-urlencoded").param("loginForm\\:inputUsername","admin")
                .param("loginForm\\:inputPassword","password")
                .param("loginForm\\:inputPassword","password")
                .post("http://10.20.0.83:8080/agilereporter/core/page/login.xhtml");
        response.prettyPrint();

    }

    @Test
    public void test2(){

        XmlPath xmlPath = get("http://localhost:8080/agilereporter/core/page/login.xhtml").xmlPath();
        xmlPath.prettyPrint();
        xmlPath.get("input.find{it.@name == 'javax.faces.ViewState'}.@value");
        System.out.println("hi");


    }
    @Test
    public void test3(){

        //XmlPath xmlPath = get("http://localhost:8080/agilereporter/core/page/login.xhtml").xmlPath();

        String str =get("http://localhost:8080/agilereporter/core/page/login.xhtml").asString();
        String jViewState= str.substring(str.indexOf("ViewState:0\" value=\"")).substring(20,59);
        //from(str).getString("input.find{it.@name == 'javax.faces.ViewState'}.@value");

//        System.out.println(str.substring(str.indexOf("ViewState:0\" value=\"")));

        System.out.println(jViewState);
        System.out.println("===========================================================");


        Response response = given().contentType("application/x-www-form-urlencoded").param("loginForm","loginForm")
                .param("loginForm\\:inputUsername","client")
                .param("loginForm\\:inputPassword","password")
                .param("loginForm\\:btnSignIn","Sign in")
                .param("javax.faces.ViewState:",jViewState)
                .post("http://10.20.0.83:8080/agilereporter/core/page/login.xhtml");
        response.prettyPrint();



        given().relaxedHTTPSValidation().auth().form("client","password",new FormAuthConfig("http://localhost:8080/agilereporter/core/page/login.xhtml",
                "loginForm\\:inputUsername",
                "loginForm\\:inputPassword"))
                .when()
                .get("http://localhost:8080/agilereporter/core/page/login.xhtml");
        response.prettyPrint();

    }



    @Test
    public void testLogin1(){
        Response response = given().relaxedHTTPSValidation().auth().preemptive().basic("admin","password").
                //Response response = given().relaxedHTTPSValidation().auth().basic("test","password").
                when().get("http://10.20.0.83:8080/agilereporter/rest/api/products/HKMA/allocationReportContent?formInstanceId=430398");
        response.prettyPrint();

    }
    @Test
    public void testLogin3(){
//        Response response = given().relaxedHTTPSValidation().auth().preemptive().basic("admin","password").
//                //Response response = given().relaxedHTTPSValidation().auth().basic("test","password").
//                when().get("http://10.20.0.83:8080/agilereporter/rest/api/products/HKMA/allocationReportContent?formInstanceId=430398");
//        response.prettyPrint();
        loginPage.open();
        loginPage.login("client","password");
        //Map<String,String> map = get("http://10.20.0.83:8080/agilereporter/rest/api/products/HKMA/allocationReportContent?formInstanceId=430398").getCookies();
        get("http://10.20.0.83:8080/agilereporter/rest/api/products/HKMA/allocationReportContent?formInstanceId=430398").prettyPrint();

        //System.out.println(map.values());

    }

    @Test
    public void testLogin6(){

        Response response = given().relaxedHTTPSValidation().auth().preemptive().basic("test","password").
                when().get("http://10.20.0.83:8080/agilereporter/rest/api/products/HKMA/superC/records?batchId=QASIA");
        response.prettyPrint();

    }



    @Test
    public void testLogin2(){

        Response response = given().relaxedHTTPSValidation().auth().preemptive().basic("test","password").
                when().get("http://10.20.0.83:8080/agilereporter/rest/api/products/HKMA/superC/records?batchId=QASIA");
        response.prettyPrint();

    }

    @Test
    public void testLogin4(){
//	JSESSIONID=y-oHT_L2liFN5FGJbePCLCPqCflOv21D9psPmGVY.sha-lri-pc-287; JSESSIONID=N5J2JM_9eZbfcr2BHleA8lx9unoBeEeK98xwXhi2.sha-lri-pc-287JSESSIO
        //JSESSIONID=ageDNoFxD_jxgpgqVJhZ5QZ2WRMO6s-TjkCX8bdp.sha-lri-pc-287; JSESSIONID=N5J2JM_9eZbfcr2BHleA8lx9unoBeEeK98xwXhi2.sha-lri-pc-287
        Cookie cookie = new Cookie.Builder("JSESSIONID","ageDNoFxD_jxgpgqVJhZ5QZ2WRMO6s-TjkCX8bdp.sha-lri-pc-287").build();
        Cookie cookie1 = new Cookie.Builder("JSESSIONID","N5J2JM_9eZbfcr2BHleA8lx9unoBeEeK98xwXhi2.sha-lri-pc-287JSESSIO").build();
        Cookies cookies = new Cookies(cookie,cookie1);
        Response response = given().cookies(cookies).when().get("http://10.20.0.83:8080/agilereporter/rest/api/products/HKMA/superC/processDates");
        //Response response = given().cookies(cookies).when().get("http://10.20.0.83:8080/agilereporter/rest/api/products/HKMA/superC/records?batchId=QASIA");


//        Response response = given().relaxedHTTPSValidation().auth().preemptive().basic("test","password").
//                when().get("http://10.20.0.83:8080/agilereporter/rest/api/products/HKMA/superC/records?batchId=QASIA");
        response.prettyPrint();
    }

    @Test
    public void testLogin5(){
//        Response response = given().relaxedHTTPSValidation().auth().preemptive().basic("client","password").
//                //Response response = given().relaxedHTTPSValidation().auth().basic("test","password").
//                        when().get("http://10.20.0.83:8080/agilereporter/rest/api/products/HKMA/allocationReportContent?formInstanceId=430398");
//        response.prettyPrint();
        //Map<String, String> allCookies = response.getCookies();
//        Cookies cookies = response.getDetailedCookies();
//        String cookieValue = response.getCookie("JSESSIONID");
//        System.out.println(cookieValue);
//        Response response1 = given().cookies(cookies).when().get("http://10.20.0.83:8080/agilereporter/rest/api/products/HKMA/superC/records?batchId=QASIA");
//        response1.prettyPrint();

//        String sessionId = given().auth().preemptive().basic("client","password")
//                .when().get("http://10.20.0.83:8080/agilereporter/rest/api/products/HKMA/allocationReportContent?formInstanceId=430398").andReturn().sessionId();
//        given().sessionId(sessionId).when().get("http://10.20.0.83:8080/agilereporter/rest/api/products/HKMA/superC/records?batchId=QASIA").prettyPrint();

//
//         Cookies cookies =given().auth().preemptive().basic("client","password")
//                .when().get("http://10.20.0.83:8080/agilereporter/rest/api/products/HKMA/allocationReportContent?formInstanceId=430398").andReturn().getDetailedCookies();
//
//        given().cookies(cookies).when().get("http://10.20.0.83:8080/agilereporter/rest/api/products/HKMA/superC/processDates").prettyPrint();

        Cookies cookies1 =given().auth().basic("client","password").
                get("http://10.20.0.83:8080/agilereporter/rest/api/products/HKMA/allocationReportContent?formInstanceId=430398").detailedCookies();
        RestAssured.requestSpecification = new RequestSpecBuilder().addCookies(cookies1).build();
        get("http://10.20.0.83:8080/agilereporter/rest/api/products/HKMA/superC/processDates").prettyPrint();


    }


}
