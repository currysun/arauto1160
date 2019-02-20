package com.lombardrisk.stepdef.rest;

import com.lombardrisk.config.StepDef;
import com.lombardrisk.page.PageUtils;
import com.lombardrisk.rest.datatable.HKIB_HKIBREJ;
import com.lombardrisk.rest.gv.DatasetContent;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class DatasetApi extends StepDef {

    @Autowired
    private DatasetContent datasetContent;

    @Given("^a user is authenticated by \"([^\"]*)\" and \"([^\"]*)\"$")
    public void aUserIsAuthenticatedByAnd(String user, String password)  {
        datasetContent.authorize(user,password);

    }

    @Then("^I get a response from AR of (\\d+)$")
    public void iGetAResponseFromAROf(int expectedStatusCode) {
        datasetContent.shouldHaveRespCode(expectedStatusCode);

    }

    @Then("^I see total Row count is (\\d+) and page size is (\\d+)$")
    public void iCanSeeTotalRowCountIsAndPageSizeIs(int totalRow, int pageSize)  {
        datasetContent.shouldHaveTotalRowCount(totalRow);
        datasetContent.shouldHavePageSize(pageSize);
    }

    @Then("^I see response data is filtered with column \"([^\"]*)\" on \"([^\"]*)\" by using operator \"([^\"]*)\"$")
    public void iSeeResponseDataIsFilteredWithColumnOnByUsingOperator(String column, String value, String operator) {
        datasetContent.checkSearchResult(column,value,operator);

    }

    @Then("^I get error info \"([^\"]*)\"$")
    public void iGetErrorInfo(String expectedMessage)  {
        datasetContent.shouldHaveMessage(expectedMessage);

    }

    @Then("^I see no data return$")
    public void iSeeNoDataReturn() {
        datasetContent.shouldGetBlankData();
    }

    @And("^the AR rest response data of rejection includes$")
    public void theARRestResponseDataOfRejectionIncludes(final DataTable dataRows)  {
        List<HKIB_HKIBREJ> dataList = dataRows.asList(HKIB_HKIBREJ.class);
        datasetContent.shouldHaveRowDataOfRejection(dataList);
    }

    @And("^the AR rest response data of allocation includes$")
    public void theARRestResponseDataIncludes(final DataTable dataRows) {
        List<HKIB_HKIBREJ> dataList = dataRows.asList(HKIB_HKIBREJ.class);
        datasetContent.shouldHaveRowDataOfAllocation(dataList);
    }

    @Then("^the AR rest response data of plainDataset includes$")
    public void theARRestResponseDataOfPlainDatasetIncludes(final DataTable dataRows)  {
//        List<HKLNET> dataList = dataRows.asList(HKLNET.class);
//        datasetContent.shouldHavePlainDatasetData(dataList);
        List<Map<String,Object>> dataList = dataRows.asMaps(String.class,Object.class);
        datasetContent.shouldHavePlainDatasetData(dataList);
    }


    @Then("^the AR rest response data of records filter includes$")
    public void theARRestResponseDataOfRecordsIncludes(final DataTable dataRows)  {
        List<List<String>> dataList = dataRows.asLists(String.class);
        datasetContent.shouldHaveRecords(dataList);
    }

    @When("^When I make a Rest call of superC filter on condition$")
    public void whenIMakeARestCallOfSuperCFilterOnCondition(final DataTable condtion) {
        Map<String,String> conMap = condtion.asMap(String.class,String.class);
        datasetContent.callSuperCFilterWithCond(conMap);
    }

    @Then("^the AR rest response data of superC other filters includes$")
    public void theARRestResponseDataOfSuperCOtherFiltersIncludes(final DataTable dataRows)  {
        List<String> dataList = dataRows.topCells();
        datasetContent.shouldHaveOtherFilters(dataList);
    }

    @Then("^I see start Row index is (\\d+)$")
    public void iSeeStartRowIndexIs(int expectedNumber)  {
        datasetContent.shouldHaveStartRowIndex(expectedNumber);
    }

    @When("^When I make a Rest call of reports api on condition$")
    public void whenIMakeARestCallOfReportsApiOnCondition(final DataTable condtion) {
        Map<String,String> conMap = condtion.asMap(String.class,String.class);
        datasetContent.callReportsWithCond(conMap);
    }

    @When("^When I make a Rest call of plaindataset on condition$")
    public void whenIMakeARestCallOfPlaindatasetOnCondition(final DataTable condtion) {
        Map<String,String> conMap = condtion.asMap(String.class,String.class);
        datasetContent.callPlainDatasetWithCond(conMap);
    }

    @Then("^I see table is \"([^\"]*)\"$")
    public void iSeeTableIs(String expectedStr)  {
        datasetContent.shouldHaveTable(expectedStr);
    }


    @Then("^I see records is \"([^\"]*)\"$")
    public void iSeeRecordsIs(String expectedStr)  {
        datasetContent.shouldHaveRecord(expectedStr);
    }

    @Then("^I see alias is \"([^\"]*)\"$")
    public void iSeeAliasIs(String expectedStr) {
        datasetContent.shouldHaveAlias(expectedStr);
    }

    @Then("^I see total Row count is (\\d+) and page size is (\\d+) under reportContent$")
    public void iSeeTotalRowCountIsAndPageSizeIsUnderReportContent(int totalRow, int pageSize)  {
        datasetContent.shouldHaveTotalRowCountUnderRContent(totalRow);
        datasetContent.shouldHavePageSizeUnderRContent(pageSize);
    }

    @Then("^I see start Row index is (\\d+) under reportContent$")
    public void iSeeStartRowIndexIsUnderReportContent(int expectedNumber)  {
        datasetContent.shouldHaveStartRowIndexUnderRContent(expectedNumber);
    }


    @And("^the AR rest response data of dataset includes$")
    public void theARRestResponseDataOfDatasetIncludes(final DataTable dataRows)  {
        List<Map<String,Object>> dataList = dataRows.asMaps(String.class,Object.class);
        datasetContent.shouldHaveFormDatasetData(dataList);
    }

    @Then("^the AR rest response data of superC other filters with columnName includes$")
    public void theARRestResponseDataOfSuperCOtherFiltersWithColumnNameIncludes(final DataTable dataRows) {
        List<List<String>> dataList = dataRows.asLists(String.class);
        datasetContent.shouldHaveOtherFiltersWithColumn(dataList);
    }

    @And("^Get cookie$")
    public void getCookie() {
        String cookieVal = PageUtils.getCookie();
        datasetContent.setCookieVal(cookieVal);

    }

    @Then("^I see no data return of SuperC other filters$")
    public void iSeeNoDataReturnOfSuperCOtherFilters() {
        datasetContent.shouldGetBlankDataOfSuperC();
    }
}
