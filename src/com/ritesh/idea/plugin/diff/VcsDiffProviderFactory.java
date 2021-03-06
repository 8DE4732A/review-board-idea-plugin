/*
 * Copyright 2015 Ritesh Kapoor
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

package com.ritesh.idea.plugin.diff;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.AbstractVcs;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import com.intellij.openapi.vcs.VcsDataKeys;
import com.intellij.openapi.vcs.VcsKey;
import com.ritesh.idea.plugin.state.Configuration;
import git4idea.GitVcs;
import org.jetbrains.idea.svn.SvnVcs;

import java.util.Objects;

/**
 * @author ritesh
 */
public class VcsDiffProviderFactory {
    public static IVcsDiffProvider getVcsDiffProvider(Project project, Configuration configuration, AnActionEvent action) {
        AbstractVcs vcsFor = ProjectLevelVcsManager.getInstance(project).getVcsFor(project.getProjectFile());
        if (configuration.useRbTools == Boolean.TRUE) {
            return new RbToolsDiffProvider(configuration.url, configuration.username, configuration.password,
                    configuration.rbtPath, vcsFor);
        }
        VcsKey vcsKey = action.getData(VcsDataKeys.VCS);
        if (!Objects.isNull(vcsKey) && "svn".equals(vcsKey.getName())) {
            return new SvnDiffProvider();
        } else if (!Objects.isNull(vcsKey) && "git".equals(vcsKey.getName())) {
            return new GitDiffProvider();
        }
        return null;
    }
}
