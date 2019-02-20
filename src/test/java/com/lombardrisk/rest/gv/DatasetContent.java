package com.lombardrisk.rest.gv;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Cookie;
import com.jayway.restassured.response.Cookies;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;
import com.lombardrisk.config.Config;
import com.lombardrisk.rest.datatable.HKIB_HKIBREJ;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.jayway.restassured.RestAssured.given;
import static java.util.Objects.requireNonNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DatasetContent {

    private final Config config;
    private Response response;
    private ValidatableResponse code;
    private RequestSpecification request;
    private JsonPath jsonData;
    private List<String> numTypeColumList;
    private String username;
    private String password;
    private String apiUri = "/agilereporter/rest/api/products/????/@@@@";
    private String superCFilterUri = "/agilereporter/rest/api/products/????/superC/@@@@";
    private String cookieVal;


    public DatasetContent(final Config config) {
        this.config = requireNonNull(config);
    }

    public void authorize(String username, String password)
    {
        RestAssured.useRelaxedHTTPSValidation();
        this.username=username;
        this.password=password;
    }

    public void setCookieVal(String cookieVal){
        this.cookieVal = cookieVal;
    }

    public void callReportsWithCond(final Map<String,String> map) {
        String apiUrl = config.arBaseUrl() + apiUri;
        apiUrl = apiUrl.replace("????",map.get("product"));
        apiUrl = apiUrl.replace("@@@@",map.get("ApiType"));
        apiUrl = (map.get("formInstanceId")==null? apiUrl : apiUrl+"?formInstanceId="+map.get("formInstanceId"));
        apiUrl = (map.get("reportId")==null? apiUrl : apiUrl+"&reportId="+map.get("reportId"));
        apiUrl = (map.get("search")==null? apiUrl : apiUrl+"&search="+map.get("search"));
        apiUrl = (map.get("sort")==null? apiUrl : apiUrl+"&sort="+map.get("sort"));
        apiUrl = (map.get("size")==null? apiUrl : apiUrl+"&size="+map.get("size"));
        apiUrl = (map.get("page")==null? apiUrl : apiUrl+"&page="+map.get("page"));
        //response = given().relaxedHTTPSValidation().auth().preemptive().basic(this.username,this.password).when().get(apiUrl);
        //Cookies cookies = setCookies();
        Cookies cookies = setCookies2();
        response = given().cookies(cookies).when().get(apiUrl);
        jsonData = response.jsonPath();
        //numTypeColumList = jsonData.getList("header.findAll { it.valueType == 'NUMBER' }.columnName");
    }


    public void callReportsWithCond2() {
        String apiUrl = "http://10.20.0.83:8085/agilereporter/rest/api/products/HKMA/rejectionDatasetContent?formInstanceId=430398";
        Cookies cookies = setCookies2();
        response = given().cookies(cookies).when().get(apiUrl);
        jsonData = response.jsonPath();
    }

    public void callPlainDatasetWithCond(final Map<String,String> map) {
        String apiUrl =  config.arBaseUrl() + apiUri;
        apiUrl = apiUrl.replace("????",map.get("product"));
        apiUrl = apiUrl.replace("@@@@",map.get("ApiType"));
        apiUrl = (map.get("table")==null? apiUrl : apiUrl+"&table="+map.get("table"));
        apiUrl = (map.get("chapter")==null? apiUrl : apiUrl+"&chapter="+map.get("chapter"));
        apiUrl = (map.get("batchId")==null? apiUrl : apiUrl+"&batchId="+map.get("batchId"));
        apiUrl = (map.get("referenceDate")==null? apiUrl : apiUrl+"&referenceDate="+map.get("referenceDate"));
        apiUrl = (map.get("tableType")==null? apiUrl : apiUrl+"&tableType="+map.get("tableType"));
        apiUrl = (map.get("search")==null? apiUrl : apiUrl+"&search="+map.get("search"));
        apiUrl = (map.get("sort")==null? apiUrl : apiUrl+"&sort="+map.get("sort"));
        apiUrl = (map.get("size")==null? apiUrl : apiUrl+"&size="+map.get("size"));
        apiUrl = (map.get("page")==null? apiUrl : apiUrl+"&page="+map.get("page"));
        apiUrl = apiUrl.replaceFirst("&","?");

        Cookies cookies = setCookies2();
        response = given().cookies(cookies).when().get(apiUrl);
        jsonData = response.jsonPath();
        //numTypeColumList = jsonData.getList("reportContent.header.findAll { it.valueType == 'NUMBER' }.columnName");
    }

    public Cookies setCookies(){
        Cookie cookie = new Cookie.Builder("JSESSIONID",config.arCookie()).build();
        return new Cookies(cookie);
    }

    public Cookies setCookies2(){
        Cookie cookie = new Cookie.Builder("JSESSIONID",cookieVal).build();
        return new Cookies(cookie);
    }


    public void callSuperCFilterWithCond(final Map<String,String> map) {
        String apiUrl = config.arBaseUrl() + superCFilterUri;
        apiUrl = apiUrl.replace("????",map.get("product"));
        apiUrl = apiUrl.replace("@@@@",map.get("ApiType"));
        apiUrl = (map.get("table")==null? apiUrl : apiUrl+"&table="+map.get("table"));
        apiUrl = (map.get("chapter")==null? apiUrl : apiUrl+"&chapter="+map.get("chapter"));
        apiUrl = (map.get("batchId")==null? apiUrl : apiUrl+"&batchId="+map.get("batchId"));
        apiUrl = (map.get("processDate")==null? apiUrl : apiUrl+"&processDate="+map.get("processDate"));
        apiUrl = apiUrl.replaceFirst("&","?");
        Cookies cookies = setCookies2();
        response = given().cookies(cookies).when().get(apiUrl);
        jsonData = response.jsonPath();

    }


    public JsonPath getJsonData() {
        return jsonData;
    }

    public List<String> getNumTypeColumList(){
        return numTypeColumList;}

    public Response getResponse() {
        return response;
    }

    public void shouldHaveRespCode(int expectedStatusCode){
        response.then().assertThat().statusCode(expectedStatusCode);
    }

    public void shouldHaveTotalRowCount(int expectedNumber){
        assertThat(jsonData.get("totalRowCount"),is(expectedNumber));
    }

    public void shouldHavePageSize(int expectedNumber){
        assertThat(jsonData.get("pageSize"),is(expectedNumber));
    }

    public void shouldHaveStartRowIndex(int expectedNumber){
        assertThat(jsonData.get("startRowIndex"),is(expectedNumber));
    }

    public void shouldHaveTable(String expectedStr){
        assertThat(jsonData.get("reportSummary.TABLE"),is(expectedStr));
    }

    public void shouldHaveRecord(String expectedStr){
        assertThat(jsonData.get("reportSummary.RECORDS"),is(expectedStr));
    }

    public void shouldHaveAlias(String expectedStr){
        assertThat(jsonData.get("reportSummary.ALIAS"),is(expectedStr));
    }

    public void shouldHaveTotalRowCountUnderRContent(int expectedNumber){
        assertThat(jsonData.get("reportContent.totalRowCount"),is(expectedNumber));
    }

    public void shouldHavePageSizeUnderRContent(int expectedNumber){
        assertThat(jsonData.get("reportContent.pageSize"),is(expectedNumber));
    }

    public void shouldHaveStartRowIndexUnderRContent(int expectedNumber){
        assertThat(jsonData.getInt("reportContent.startRowIndex"),is(expectedNumber));
    }

    public int shouldHaveHeaderNumUnderRContent(){
        return jsonData.getInt("reportContent.header.columnName");
    }

    public void shouldGetBlankData(){
        assertThat(jsonData.get("data"),is(nullValue()));
    }

    public void shouldHaveMessage(String expectedMessage){
        assertThat(jsonData.get("errorMessage"),containsString(expectedMessage));
    }

    public void checkSearchResult(String column, String value, String operator){
        //String path = "data."+column+".value.findAll{value -> value =='"+value+"'}";
        String path = "data."+column+".value";
        List list = jsonData.getList(path);
        if(value.contains("/")){
            String[] vl =value.split("/");
            value = vl[2]+"-"+vl[1]+"-"+vl[0];
        }
        if("=".equals(operator)){
            for (int i = 0; i < list.size(); i++) {
                assertThat(list.get(i),is(value));
            }
        }
        if(":".equals(operator)){

            for (int i = 0; i < list.size(); i++) {
                assertThat(String.valueOf(list.get(i)),containsString(value));
            }
        }
        if(">".equals(operator)){

            for (int i = 0; i < list.size(); i++) {
                assertThat(String.valueOf(list.get(i)),greaterThan(value));
            }
        }
        if("<".equals(operator)){
            for (int i = 0; i < list.size(); i++) {
                assertThat(String.valueOf(list.get(i)),lessThan(value));
            }
        }
    }

    public void shouldHaveRowDataOfAllocation(final List<HKIB_HKIBREJ> expectedData){
        //JsonPath jp = getJsonData();
        for(int i=0;i<expectedData.size();i++){
            String path;
            HKIB_HKIBREJ expectRow = expectedData.get(i);
            if(expectRow.getRn()!= null){
                int index = Integer.parseInt(expectRow.getRn())-1;
                path = "data["+index+"]";
            }else {
                path = "data["+i+"]" ;
            }
            Map<String,Map<String,String>> map = jsonData.getMap(path);
            assertThat(map.get("STBIMPINDEX").get("value"),is(expectRow.getStbimpindex()));
            assertThat(map.get("STBInstance").get("value"),is(expectRow.getStbinstance()));
            assertThat(map.get("STBGROUP").get("value"),is(expectRow.getStbgroup()));
            assertThat(map.get("HKGLC1").get("value"),is(expectRow.getHkglc1()));
            assertThat(map.get("HKGLC2").get("value"),is(expectRow.getHkglc2()));
            assertThat(map.get("HKINST").get("value"),is(expectRow.getHkinst()));
            assertThat(map.get("QA_CustomerID").get("value"),is(expectRow.getQa_customerID()));
            assertThat(map.get("QA_AmountCurrency").get("value"),is(expectRow.getQa_amountCurrency()));
            assertThat(convert(map.get("HKMATDATE").get("value")),is(expectRow.getHkmatdate()));
            assertThat(map.get("HKCCY").get("value"),is(expectRow.getHkccy()));
            assertThat(map.get("S_DealRef").get("value"),is(expectRow.getS_DealRef()));
            assertThat(map.get("STBDrillRef").get("value"),is(expectRow.getStbdrillRef()));
            assertThat(map.get("HKBRANCHCTRY").get("value"),is(expectRow.getHkbranchctry()));
            assertThat(map.get("STBITEM").get("value"),is(expectRow.getStbitem()));
            assertThat(map.get("S_Amount").get("value"),is(expectRow.getS_Amount()));
            assertThat(map.get("QA_AccrualAmount").get("value"),is(expectRow.getQa_AccrualAmount()));
            assertThat(convert(map.get("QA_MaturityDate").get("value")),is(expectRow.getQa_MaturityDate()));
            assertThat(map.get("HKACCAMNT").get("value"),is(expectRow.getHkaccamnt()));
        }
    }

    //"Asia/Shanghai"  "UTC"
    public String convert(String dateStr) {
        Instant instant = Instant.parse(dateStr);
        return DateTimeFormatter.ofPattern("yyyy-MM-dd")
                .format(instant.atZone(ZoneId.of("Asia/Shanghai")));
    }

    public void shouldHaveRowDataOfRejection(final List<HKIB_HKIBREJ> expectedData){
        for(int i=0;i<expectedData.size();i++){
            String path;
            HKIB_HKIBREJ expectRow = expectedData.get(i);
            if(expectRow.getRn()!= null){
                int index = Integer.parseInt(expectRow.getRn())-1;
                path = "data["+index+"]";
            }else {
                path = "data["+i+"]" ;
            }
            Map<String,Map<String,String>> map = jsonData.getMap(path);
            assertThat(map.get("STBIMPINDEX").get("value"),is(expectRow.getStbimpindex()));

            if(expectRow.getStbinstance()!= null){
                if( "".equals(expectRow.getStbinstance())){
                    assertThat(map.get("STBInstance").get("value"),is(nullValue()));

                }else {
                    assertThat(map.get("STBInstance").get("value"),is(expectRow.getStbinstance()));
                }
            }
            assertThat(map.get("STBGROUP").get("value"),is(expectRow.getStbgroup()));
            assertThat(map.get("HKGLC1").get("value"),is(expectRow.getHkglc1()));
            assertThat(map.get("HKGLC2").get("value"),is(expectRow.getHkglc2()));
            assertThat(map.get("HKINST").get("value"),is(expectRow.getHkinst()));
            assertThat(map.get("QA_CustomerID").get("value"),is(expectRow.getQa_customerID()));
            assertThat(map.get("QA_AmountCurrency").get("value"),is(expectRow.getQa_amountCurrency()));
            assertThat(convert(map.get("HKMATDATE").get("value")),is(expectRow.getHkmatdate()));
            assertThat(map.get("HKCCY").get("value"),is(expectRow.getHkccy()));
            assertThat(map.get("S_DealRef").get("value"),is(expectRow.getS_DealRef()));
            assertThat(map.get("STBDrillRef").get("value"),is(expectRow.getStbdrillRef()));
            assertThat(map.get("HKBRANCHCTRY").get("value"),is(expectRow.getHkbranchctry()));
            if(expectRow.getStbitem()!=null){
                if("".equals(expectRow.getStbitem())){
                    assertThat(map.get("STBITEM").get("value"),is(nullValue()));
                }else {
                    assertThat(map.get("STBITEM").get("value"),is(expectRow.getStbitem()));
                }
            }
            assertThat(map.get("S_Amount").get("value"),is(expectRow.getS_Amount()));
            assertThat(map.get("QA_AccrualAmount").get("value"),is(expectRow.getQa_AccrualAmount()));
            assertThat(convert(map.get("QA_MaturityDate").get("value")),is(expectRow.getQa_MaturityDate()));
            assertThat(map.get("HKACCAMNT").get("value"),is(expectRow.getHkaccamnt()));
            assertThat(map.get("S_RejectedRecord").get("value"),is(expectRow.getS_RejectedRecord()));
        }
    }

    public void shouldHavePlainDatasetData(final List<Map<String,Object>> expectedData){
        //assertThat(shouldHaveHeaderNumUnderRContent(),is(expectedData.get(0).size()));

        for(int i=0;i<expectedData.size();i++){
            String path;
            Map<String,Object> expectRow = expectedData.get(i);
            if(expectRow.containsKey("rn")){
                int index = Integer.parseInt((String) expectRow.get("rn"))-1;
                path = "reportContent.data["+index+"]";
            }else {
                path = "reportContent.data["+i+"]" ;
            }
            Map<String,Map<String,String>> map = jsonData.getMap(path);
            compareValueofTwoMaps(map,expectRow);
        }
    }

    public void shouldHaveFormDatasetData(final List<Map<String,Object>> expectedData){

        for(int i=0;i<expectedData.size();i++){
            String path;
            Map<String,Object> expectRow = expectedData.get(i);
            if(expectRow.containsKey("RN")){
                int index = Integer.parseInt((String) expectRow.get("RN"))-1;
                path = "data["+index+"]";
            }else {
                path = "data["+i+"]" ;
            }
            Map<String,Map<String,String>> map = jsonData.getMap(path);
            compareValueofTwoMaps(map,expectRow);
        }
    }


    public void compareValueofTwoMaps(Map<String,Map<String,String>> map,Map<String,Object> expectMap){
        Set<String> keySet = expectMap.keySet();
        for (String key : keySet){
            if(!key.equalsIgnoreCase("RN")){

                if(!"".equals(expectMap.get(key))){
                    if(key.contains("DATE") || key.contains("Date") ){
                        assertThat(convert(map.get(key).get("value")),is(expectMap.get(key)));
                    }else {
                        String value = null;
                        Object sv = map.get(key).get("value");

                        if (sv instanceof Integer) {
                            value = ((Integer)sv).toString();
                        }
                        if (sv instanceof Long) {
                            value = ((Long)sv).toString();
                        }
                        if(sv instanceof String){
                            value = sv.toString();
                        }
                        assertThat(value, is(expectMap.get(key)));
                    }
                }else {
                    if(key.contains("DATE") || key.contains("Date")){
                        assertThat(map.get(key).get("value"),is(""));
                    }else {
                        assertThat(map.get(key).get("value"),is(nullValue()));
                    }
                }
            }
        }
    }

    public void shouldGetBlankDataOfSuperC(){
        assertThat(jsonData.get(),is(empty()));
    }

    public void shouldHaveRecords(final List<List<String>> expectedData){
        List list = jsonData.get();
        for(int i=0;i<expectedData.size() && i<list.size();i++){
                String data = expectedData.get(i).get(0)+":"+expectedData.get(i).get(1);
                assertThat(list.get(i),is(data));
        }
    }

    public void shouldHaveOtherFilters(final List<String> expectedData){
        List list = jsonData.get();
        for(int i=0;i<expectedData.size() && i<list.size();i++){
            assertThat(list.get(i),is(expectedData.get(i)));
        }
    }

    public void shouldHaveOtherFiltersWithColumn(final List<List<String>> expectedData){
        List list = jsonData.get();
        for(int i=0;i<expectedData.size() && i<list.size();i++){
            List<String> expectRow = expectedData.get(i);
            int index = Integer.parseInt((String) expectRow.get(0))-1;
            assertThat(list.get(index),is(expectRow.get(1)));
        }
    }

//
//    public static void main(String[] args) {
//
//
//        String sv = "sv";
//        if(!sv.startsWith("\"")) {
//            sv = "\"" + sv + "\"";
//
//        }
//        System.out.println(sv);
//    }

}
