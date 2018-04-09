/*
 * Autopsy Forensic Browser
 *
 * Copyright 2018 Basis Technology Corp.
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
package org.sleuthkit.autopsy.experimental.autoingest;

import java.awt.Dimension;
import java.beans.PropertyVetoException;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import org.netbeans.swing.outline.DefaultOutlineModel;
import org.netbeans.swing.outline.Outline;
import org.openide.explorer.ExplorerManager;
import org.openide.nodes.Node;
import org.sleuthkit.autopsy.datamodel.EmptyNode;
import org.sleuthkit.autopsy.experimental.autoingest.AutoIngestNode.AutoIngestJobType;
import org.sleuthkit.autopsy.experimental.autoingest.AutoIngestNode.JobNode;

/**
 *
 * @author wschaefer
 */
final class AutoIngestJobsPanel extends javax.swing.JPanel implements ExplorerManager.Provider {

    private static final long serialVersionUID = 1L;
    private final org.openide.explorer.view.OutlineView outlineView;
    private final Outline outline;
    private ExplorerManager explorerManager;
    private final AutoIngestJobType type;

    /**
     * Creates new form PendingJobsPanel
     */
    AutoIngestJobsPanel(AutoIngestJobType jobType) {
        initComponents();
        type = jobType;
        outlineView = new org.openide.explorer.view.OutlineView();
        outline = outlineView.getOutline();
        customize();
    }

    void customize() {

        switch (type) {
            case PENDING_JOB:
                outlineView.setPropertyColumns(Bundle.AutoIngestNode_dataSource_text(), Bundle.AutoIngestNode_dataSource_text(),
                        Bundle.AutoIngestNode_jobCreated_text(), Bundle.AutoIngestNode_jobCreated_text(),
                        Bundle.AutoIngestNode_priority_text(), Bundle.AutoIngestNode_priority_text());
                outline.setColumnSorted(3, false, 1);
                outline.setColumnSorted(0, true, 2);
                break;
            case RUNNING_JOB:
                outlineView.setPropertyColumns(Bundle.AutoIngestNode_dataSource_text(), Bundle.AutoIngestNode_dataSource_text(),
                        Bundle.AutoIngestNode_hostName_text(), Bundle.AutoIngestNode_hostName_text(),
                        Bundle.AutoIngestNode_stage_text(), Bundle.AutoIngestNode_stage_text(),
                        Bundle.AutoIngestNode_stageTime_text(), Bundle.AutoIngestNode_stageTime_text());
                outline.setColumnSorted(0, true, 1);
                break;
            case COMPLETED_JOB:
                outlineView.setPropertyColumns(Bundle.AutoIngestNode_dataSource_text(), Bundle.AutoIngestNode_dataSource_text(),
                        Bundle.AutoIngestNode_jobCreated_text(), Bundle.AutoIngestNode_jobCreated_text(),
                        Bundle.AutoIngestNode_jobCompleted_text(), Bundle.AutoIngestNode_jobCompleted_text(),
                        Bundle.AutoIngestNode_status_text(), Bundle.AutoIngestNode_status_text());
                outline.setColumnSorted(3, false, 1);
                break;
            default:
        }
        ((DefaultOutlineModel) outline.getOutlineModel()).setNodesColumnLabel(Bundle.AutoIngestNode_caseName_text());
        outline.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        outline.setRootVisible(false);

        outline.getColumnModel().getColumn(0).setPreferredWidth(160);
        outline.getColumnModel().getColumn(1).setPreferredWidth(260);
        if (null == explorerManager) {
            explorerManager = new ExplorerManager();

        }
        outline.setRowSelectionAllowed(false);
        add(outlineView, java.awt.BorderLayout.CENTER);
        EmptyNode emptyNode = new EmptyNode("Please wait...");
        explorerManager.setRootContext(emptyNode);
    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        outlineView.setMaximumSize(new Dimension(400, 100));
        outline.setPreferredScrollableViewportSize(new Dimension(400, 100));
    }

    void addListSelectionListener(ListSelectionListener listener) {
        outline.getSelectionModel().addListSelectionListener(listener);
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return explorerManager;
    }

    void refresh(AutoIngestMonitor.JobsSnapshot jobsSnapshot) {
        synchronized (this) {
            outline.setRowSelectionAllowed(false);
            Node[] selectedNodes = explorerManager.getSelectedNodes();
            AutoIngestNode autoIngestNode = new AutoIngestNode(jobsSnapshot, type);
            explorerManager.setRootContext(autoIngestNode);
            outline.setRowSelectionAllowed(true);
            if (selectedNodes.length > 0) {
                try {
                     explorerManager.setSelectedNodes(new Node[]{autoIngestNode.getChildren().findChild(selectedNodes[0].getName())});
                } catch (PropertyVetoException ignore) {        
                    //Unable to select previously selected node
                }

            }
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

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    AutoIngestJob getSelectedAutoIngestJob() {
        Node[] selectedRows = getSelectedNodes();
        if (selectedRows.length == 1) {
            return ((JobNode) selectedRows[0]).getAutoIngestJob();
        }
        return null;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
