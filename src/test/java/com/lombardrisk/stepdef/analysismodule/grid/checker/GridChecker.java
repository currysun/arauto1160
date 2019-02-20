package com.lombardrisk.stepdef.analysismodule.grid.checker;

import com.lombardrisk.page.analysismodule.grid.CellReference;
import com.lombardrisk.page.analysismodule.grid.PeriodDiff;
import com.lombardrisk.page.analysismodule.returnselectionpanel.ReturnSelection;

import java.util.List;
import java.util.Map;

public interface GridChecker {

    void shouldHaveDiffs(
            final Map<CellReference, List<PeriodDiff>> expectedPeriodDiffsByCellReferenceRows,
            final ReturnSelection returnSelection);
}
