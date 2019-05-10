/*
 * Autopsy Forensic Browser
 *
 * Copyright 2019 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 *
 * Copyright 2013 Willi Ballenthin
 * Contact: willi.ballenthin <at> gmail <dot> com
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
package org.sleuthkit.autopsy.rejview;

import com.williballenthin.rejistry.RegistryValue;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import org.openide.util.NbBundle.Messages;
import java.util.logging.Level;
import org.sleuthkit.autopsy.coreutils.Logger;

public final class RejTreeValueNode implements RejTreeNode {

    private static final Logger logger = Logger.getLogger(RejTreeValueNode.class.getName());
    private final RegistryValue _value;

    public RejTreeValueNode(RegistryValue value) {
        this._value = value;
    }

    @Messages({"RejTreeValueNode.defaultValueName.text=(Default)",
        "RejTreeValueNode.failureValueName.text=PARSE FAILED"})
    @Override
    public String toString() {
        try {
            String valueName = this._value.getName();
            if (valueName.isEmpty()) {
                return Bundle.RejTreeValueNode_defaultValueName_text();
            }
            return valueName;
        } catch (UnsupportedEncodingException ex) {
            logger.log(Level.WARNING, "Failed to parse _value name", ex);
            return Bundle.RejTreeValueNode_failureValueName_text();
        }
    }

    @Override
    public boolean hasChildren() {
        return false;
    }

    @Override
    public List<RejTreeNode> getChildren() {
        return new LinkedList<>();
    }

    /**
     * @scope: package-protected
     */
    RegistryValue getValue() {
        return this._value;
    }

    /**
     * TODO(wb): this isn't exactly MVC...
     */
    @Override
    public RejTreeNodeView getView() {
        return new RejTreeValueView(this);
    }
}
