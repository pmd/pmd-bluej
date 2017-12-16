# Update bluej extension library

1.  Get the latest bluej version and install it
2.  Install the library `bluejext.jar` into this local repository:

        PATH_TO_BLUEJ_INSTALL_DIR=${HOME}/bluej
        VERSION=4.1.2
        mvn install:install-file -Dfile=${PATH_TO_BLUEJ_INSTALL_DIR}/lib/bluejext.jar \
                                 -DgroupId=bluej \
                                 -DartifactId=bluejext \
                                 -Dversion=${VERSION} \
                                 -Dpackaging=jar \
                                 -DlocalRepositoryPath=.