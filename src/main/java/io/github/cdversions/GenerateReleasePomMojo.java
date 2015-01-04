package io.github.cdversions;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * Goal that rewrites pom file to replace all occurances of the allowed variables 
 * for continuous delivery style versions (revision, sha1, changelist).
 */
@Mojo(name = "generate-release-pom", defaultPhase = LifecyclePhase.PACKAGE)
public class GenerateReleasePomMojo extends AbstractMojo {

    private static final String RELEASE_POM = "release.pom";
    
    @Parameter(property="revision", defaultValue="99999-SNAPSHOT")
    String revision;
    
    @Parameter(property="sha1", defaultValue="99999-SNAPSHOT")
    String sha1;
    
    @Parameter(property="changelist", defaultValue="99999-SNAPSHOT")
    String changelist;
    
    @Component
    MavenProject project;
    
    public void execute() throws MojoExecutionException {
        try {
            String pomText = new String(Files.readAllBytes(project.getFile().toPath()));
            File output = new File(project.getFile().getParentFile(), RELEASE_POM);
            Files.write(output.toPath(), replaceVariables(pomText).getBytes());
            project.setFile(output);
        } catch (IOException e) {
            throw new MojoExecutionException("failed up create release pom file!", e);
        }
    }

    private String replaceVariables(String pomText) {
        return pomText.replace("${revision}", revision)
                      .replace("${sha1}", sha1)
                      .replace("${changelist}", changelist);
    }
}
