package org.nsesa.editor.gwt.core.client.ui.resources;

import com.google.gwt.i18n.client.Messages;

/**
 * Date: 17/10/12 22:57
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
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
