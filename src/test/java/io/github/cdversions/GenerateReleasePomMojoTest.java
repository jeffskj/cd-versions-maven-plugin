package io.github.cdversions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.IOUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class GenerateReleasePomMojoTest {

    @Rule
    public TemporaryFolder tmp = new TemporaryFolder();
    
    @Test
    public void canReplaceVersions() throws IOException, MojoExecutionException {
        GenerateReleasePomMojo mojo = new GenerateReleasePomMojo();
        mojo.changelist = "chg123";
        mojo.sha1 = "a1b2c3";
        mojo.revision = "456";
        mojo.project = new MavenProject();
        mojo.project.setFile(tmp.newFile("pom.xml"));
        
        InputStream testPom = getClass().getClassLoader().getResourceAsStream("test.pom");
        IOUtil.copy(testPom, new FileOutputStream(mojo.project.getFile()));
        
        mojo.execute();
        
        String released = new String(Files.readAllBytes(new File(tmp.getRoot(), "release.pom").toPath()));
        System.out.println(released);
        
        assertFalse(released.contains("${revision}-${sha1}-${changelist}"));
        assertTrue(released.contains("456-a1b2c3-chg123"));
    }
}
