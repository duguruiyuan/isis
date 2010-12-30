/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */


package org.apache.isis.viewer.dnd.view.debug;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;

import org.apache.isis.core.metamodel.consent.Consent;
import org.apache.isis.core.metamodel.consent.ConsentAbstract;
import org.apache.isis.core.metamodel.spec.ActionType;
import org.apache.isis.viewer.dnd.drawing.Location;
import org.apache.isis.viewer.dnd.view.MenuOptions;
import org.apache.isis.viewer.dnd.view.UserActionSet;
import org.apache.isis.viewer.dnd.view.View;
import org.apache.isis.viewer.dnd.view.Workspace;
import org.apache.isis.viewer.dnd.view.option.UserActionAbstract;


public class LoggingOptions implements MenuOptions {

    public void menuOptions(final UserActionSet options) {
        options.add(loggingOption("Off", Level.OFF));
        options.add(loggingOption("Error", Level.ERROR));
        options.add(loggingOption("Warn", Level.WARN));
        options.add(loggingOption("Info", Level.INFO));
        options.add(loggingOption("Debug", Level.DEBUG));

        options.add(new DebugDumpSnapshotOption());
    }

    private UserActionAbstract loggingOption(final String name, final Level level) {
        return new UserActionAbstract("Log level " + level, ActionType.DEBUG) {
            @Override
            public Consent disabled(final View component) {
                return ConsentAbstract.allowIf(LogManager.getRootLogger().getLevel() != level);
            }

            @Override
            public void execute(final Workspace workspace, final View view, final Location at) {
                LogManager.getRootLogger().setLevel(level);
            }
        };
    }

}

