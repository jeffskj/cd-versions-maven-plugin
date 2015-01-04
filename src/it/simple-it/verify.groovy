def releasePom = new File(basedir, "release.pom")

def replaced = 'r123-a1b2c3-whyisthisablessedvar'
def replacedVersionTag = "<version>${replaced}</version>"
assert releasePom.text.contains(replacedVersionTag)

def installed = new File(localRepositoryPath, "io/github/cdversions/it/simple-it/${replaced}/simple-it-${replaced}.pom")
assert installed.text.contains(replacedVersionTag)
