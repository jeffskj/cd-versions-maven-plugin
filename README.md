Continuous Delivery Versions Maven Plugin
=========================================

[![Build Status](https://travis-ci.org/jeffskj/cd-versions-maven-plugin.svg?branch=master)](https://travis-ci.org/jeffskj/cd-versions-maven-plugin)

Versioning in Maven can be a pain if you're doing continuous delivery. One solution is to have your version be parameterized like so: `<version>${revision}</version>`. The problem with this is approach is that when the resulting pom gets deployed it still contains the expression `${revision}`. This causes all sorts of weird issues especially in multi-module projects as well as tools like Ivy which validate the pom files. 

This plugin replaces those expressions with their resolved values. Only supports variables 'blessed' in 3.2.x: revision, sha1, and changelist.

Usage
-----

      <build>
        <plugins>
          <plugin>
            <groupId>${project.groupId}</groupId>
            <artifactId>${project.artifactId}</artifactId>
            <version>${cdversions.version}</version>
            <executions>
              <execution>
                <goals>
                  <goal>generate-release-pom</goal>
                </goals>
              </execution>
            </executions>
          </plugin>
        </plugins>
      </build>

This will create a file `release.pom` right next to each pom file with the expressions replaced so you will also want to add this to your .gitignore file.