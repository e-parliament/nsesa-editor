/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.core.client.ui.resources;

import com.google.gwt.i18n.client.Messages;

/**
 * Git messages - its corresponding .properties file is generated automatically by the Maven Git plugin with the
 * latest commit information.
 * Date: 17/10/12 22:57
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface GitMessages extends Messages {
    @Key("git.commit.id.abbrev")
    String gitCommitIdAbbrev();

    @Key("git.commit.id.describe")
    String gitCommitIdDescribe();

    @Key("git.build.time")
    String gitBuildTime();

    @Key("git.branch")
    String gitBranch();
}
