# Update bluej extension library

1.  Get the latest bluej version and install it
2.  Install the library `bluejext2.jar` into this local repository:

        PATH_TO_BLUEJ_INSTALL_DIR=${HOME}/bluej
        VERSION=5.0.0
        mvn install:install-file -Dfile=${PATH_TO_BLUEJ_INSTALL_DIR}/lib/bluejext2.jar \
                                 -DgroupId=bluej \
                                 -DartifactId=bluejext2 \
                                 -Dversion=${VERSION} \
                                 -Dpackaging=jar \
                                 -DlocalRepositoryPath=.