/*
 * Autopsy Forensic Browser
 *
 * Copyright 2020 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sleuthkit.autopsy.datasourcesummary.ui;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;
import org.openide.util.NbBundle.Messages;
import org.sleuthkit.autopsy.casemodule.Case;
import org.sleuthkit.autopsy.datasourcesummary.datamodel.DataSourceAnalysisSummary;
import org.sleuthkit.autopsy.datasourcesummary.uiutils.CellModelTableCellRenderer.DefaultCellModel;
import org.sleuthkit.autopsy.datasourcesummary.uiutils.DataFetchResult;
import org.sleuthkit.autopsy.datasourcesummary.uiutils.DataFetchWorker;
import org.sleuthkit.autopsy.datasourcesummary.uiutils.JTablePanel;
import org.sleuthkit.autopsy.datasourcesummary.uiutils.JTablePanel.ColumnModel;
import org.sleuthkit.datamodel.DataSource;

/**
 * A tab shown in data source summary displaying hash set hits, keyword hits,
 * and interesting item hits within a datasource.
 */
@Messages({
    "AnalysisPanel_keyColumn_title=Name",
    "AnalysisPanel_countColumn_title=Count"
})
public class AnalysisPanel extends BaseDataSourceSummaryPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Default Column definitions for each table
     */
    private static final List<ColumnModel<Pair<String, Long>>> DEFAULT_COLUMNS = Arrays.asList(
            new ColumnModel<>(
                    Bundle.AnalysisPanel_keyColumn_title(),
                    (pair) -> new DefaultCellModel(pair.getKey()),
                    300
            ),
            new ColumnModel<>(
                    Bundle.AnalysisPanel_countColumn_title(),
                    (pair) -> new DefaultCellModel(String.valueOf(pair.getValue())),
                    100
            )
    );

    private final JTablePanel<Pair<String, Long>> hashsetHitsTable = JTablePanel.getJTablePanel(DEFAULT_COLUMNS);

    private final JTablePanel<Pair<String, Long>> keywordHitsTable = JTablePanel.getJTablePanel(DEFAULT_COLUMNS);

    private final JTablePanel<Pair<String, Long>> interestingItemsTable = JTablePanel.getJTablePanel(DEFAULT_COLUMNS);

    private final List<JTablePanel<?>> tables = Arrays.asList(
            hashsetHitsTable,
            keywordHitsTable,
            interestingItemsTable
    );

    /**
     * All of the components necessary for data fetch swing workers to load data
     * for each table.
     */
    private final List<DataFetchWorker.DataFetchComponents<DataSource, ?>> dataFetchComponents;

    /**
     * Creates a new DataSourceUserActivityPanel.
     */
    public AnalysisPanel() {
        this(new DataSourceAnalysisSummary());
    }

    public AnalysisPanel(DataSourceAnalysisSummary analysisData) {
        // set up data acquisition methods
        dataFetchComponents = Arrays.asList(
                // hashset hits loading components
                new DataFetchWorker.DataFetchComponents<>(
                        (dataSource) -> analysisData.getHashsetCounts(dataSource),
                        (result) -> hashsetHitsTable.showDataFetchResult(result)),
                // keyword hits loading components
                new DataFetchWorker.DataFetchComponents<>(
                        (dataSource) -> analysisData.getKeywordCounts(dataSource),
                        (result) -> keywordHitsTable.showDataFetchResult(result)),
                // interesting item hits loading components
                new DataFetchWorker.DataFetchComponents<>(
                        (dataSource) -> analysisData.getInterestingItemCounts(dataSource),
                        (result) -> interestingItemsTable.showDataFetchResult(result))
        );

        initComponents();
    }

    @Override
    protected void onNewDataSource(DataSource dataSource) {
        // if no data source is present or the case is not open,
        // set results for tables to null.
        if (dataSource == null || !Case.isCaseOpen()) {
            this.dataFetchComponents.forEach((item) -> item.getResultHandler()
                    .accept(DataFetchResult.getSuccessResult(null)));

        } else {
            // set tables to display loading screen
            this.tables.forEach((table) -> table.showDefaultLoadingMessage());

            // create swing workers to run for each table
            List<DataFetchWorker<?, ?>> workers = dataFetchComponents
                    .stream()
                    .map((components) -> new DataFetchWorker<>(components, dataSource))
                    .collect(Collectors.toList());

            // submit swing workers to run
            submit(workers);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JScrollPane mainScrollPane = new javax.swing.JScrollPane();
        javax.swing.JPanel mainContentPanel = new javax.swing.JPanel();
        javax.swing.JLabel hashsetHitsLabel = new javax.swing.JLabel();
        javax.swing.Box.Filler filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 2), new java.awt.Dimension(0, 2), new java.awt.Dimension(32767, 2));
        javax.swing.JPanel hashSetHitsPanel = hashsetHitsTable;
        javax.swing.Box.Filler filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 20), new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 20));
        javax.swing.JLabel keywordHitsLabel = new javax.swing.JLabel();
        javax.swing.Box.Filler filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 2), new java.awt.Dimension(0, 2), new java.awt.Dimension(32767, 2));
        javax.swing.JPanel keywordHitsPanel = keywordHitsTable;
        javax.swing.Box.Filler filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 20), new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 20));
        javax.swing.JLabel interestingItemLabel = new javax.swing.JLabel();
        javax.swing.Box.Filler filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 2), new java.awt.Dimension(0, 2), new java.awt.Dimension(32767, 2));
        javax.swing.JPanel interestingItemPanel = interestingItemsTable;
        javax.swing.Box.Filler filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));

        mainContentPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainContentPanel.setMaximumSize(new java.awt.Dimension(32767, 452));
        mainContentPanel.setMinimumSize(new java.awt.Dimension(200, 452));
        mainContentPanel.setLayout(new javax.swing.BoxLayout(mainContentPanel, javax.swing.BoxLayout.PAGE_AXIS));

        org.openide.awt.Mnemonics.setLocalizedText(hashsetHitsLabel, org.openide.util.NbBundle.getMessage(AnalysisPanel.class, "AnalysisPanel.hashsetHitsLabel.text")); // NOI18N
        mainContentPanel.add(hashsetHitsLabel);
        mainContentPanel.add(filler1);

        hashSetHitsPanel.setAlignmentX(0.0F);
        hashSetHitsPanel.setMaximumSize(new java.awt.Dimension(32767, 106));
        hashSetHitsPanel.setMinimumSize(new java.awt.Dimension(10, 106));
        hashSetHitsPanel.setPreferredSize(new java.awt.Dimension(10, 106));
        mainContentPanel.add(hashSetHitsPanel);
        mainContentPanel.add(filler2);

        org.openide.awt.Mnemonics.setLocalizedText(keywordHitsLabel, org.openide.util.NbBundle.getMessage(AnalysisPanel.class, "AnalysisPanel.keywordHitsLabel.text")); // NOI18N
        mainContentPanel.add(keywordHitsLabel);
        mainContentPanel.add(filler4);

        keywordHitsPanel.setAlignmentX(0.0F);
        keywordHitsPanel.setMaximumSize(new java.awt.Dimension(32767, 106));
        keywordHitsPanel.setMinimumSize(new java.awt.Dimension(10, 106));
        keywordHitsPanel.setPreferredSize(new java.awt.Dimension(10, 106));
        mainContentPanel.add(keywordHitsPanel);
        mainContentPanel.add(filler5);

        org.openide.awt.Mnemonics.setLocalizedText(interestingItemLabel, org.openide.util.NbBundle.getMessage(AnalysisPanel.class, "AnalysisPanel.interestingItemLabel.text")); // NOI18N
        mainContentPanel.add(interestingItemLabel);
        mainContentPanel.add(filler6);

        interestingItemPanel.setAlignmentX(0.0F);
        interestingItemPanel.setMaximumSize(new java.awt.Dimension(32767, 106));
        interestingItemPanel.setMinimumSize(new java.awt.Dimension(10, 106));
        interestingItemPanel.setPreferredSize(new java.awt.Dimension(10, 106));
        mainContentPanel.add(interestingItemPanel);
        mainContentPanel.add(filler3);

        mainScrollPane.setViewportView(mainContentPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
