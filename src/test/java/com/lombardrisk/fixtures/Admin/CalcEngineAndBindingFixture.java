package com.lombardrisk.fixtures.Admin;

import com.lombardrisk.page.administration.CalculationEnginesPage;
import com.lombardrisk.page.administration.ConfigPackageBindingPage;

public class CalcEngineAndBindingFixture {

    private final CalculationEnginesPage calculationEnginesPage;
    private final ConfigPackageBindingPage configPackageBindingPage;

    public CalcEngineAndBindingFixture(
            final CalculationEnginesPage calculationEnginesPage,
            final ConfigPackageBindingPage configPackageBindingPage) {
        this.calculationEnginesPage = calculationEnginesPage;
        this.configPackageBindingPage = configPackageBindingPage;
    }

    public CalcEngineAndBindingFixture createCalculationEngine(
            final String calcType,
            final String calcName,
            final String fcrURL) {
        calculationEnginesPage
                .open();
        if (!calculationEnginesPage.calculationEngineOption(calcName).isDisplayed()) {
            calculationEnginesPage
                    .addCalculationEngine(calcName, calcType, fcrURL);
        } else {
            calculationEnginesPage
                    .editCalculationEngine(calcName)
                    .selectCalculationEngineType(calcType)
                    .selectCaculationEngineConfig(calcName)
                    .save();
        }
        return this;
    }

    public CalcEngineAndBindingFixture assignBinding(
            final String calcEngine,
            final String configName,
            final String configPrefix,
            final String product) {
        configPackageBindingPage
                .open()
                .selectProductConfigurationPackage(configName)
                .selectCalculationEngine
                        (calcEngine, product)
                .selectDWBinding(configPrefix)
                .selectDTBinding(configPrefix)
                .selectTemplateBreadcrumbs()
                .selectDrillDownBreadcrumbs()
                .saveConfigBinding();
        return this;
    }

    public void assignReturnToSource(
            final String product,
            final String calcEngine,
            final String returnForm,
            final int version) {
        configPackageBindingPage
                .open()
                .selectProductConfigurationPackage(product)
                .selectProductDataSource(calcEngine, returnForm, version);
    }
}
