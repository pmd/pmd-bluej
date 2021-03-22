#!/usr/bin/env bash

# Exit this script immediately if a command/function exits with a non-zero status.
set -e

SCRIPT_INCLUDES="log.bash utils.bash setup-secrets.bash openjdk.bash maven.bash sourceforge-api.bash github-releases-api.bash"
# shellcheck source=inc/fetch_ci_scripts.bash
source "$(dirname "$0")/inc/fetch_ci_scripts.bash" && fetch_ci_scripts

function build() {
    pmd_ci_log_group_start "Install OpenJDK"
        pmd_ci_openjdk_install_adoptopenjdk 11
        pmd_ci_openjdk_setdefault 11
    pmd_ci_log_group_end

    echo
    pmd_ci_maven_display_info_banner
    pmd_ci_utils_determine_build_env pmd/pmd-bluej
    echo

    pmd_ci_log_group_start "Build with mvnw"
        ./mvnw clean verify --show-version --errors --batch-mode --no-transfer-progress
    pmd_ci_log_group_end

    if pmd_ci_utils_is_fork_or_pull_request; then
        exit 0
    fi

    # only builds on pmd/pmd-bluej continue here

    if pmd_ci_maven_isReleaseBuild; then
        pmd_ci_log_group_start "Setup environment"
            pmd_ci_setup_secrets_private_env
            pmd_ci_setup_secrets_gpg_key
            pmd_ci_maven_setup_settings
        pmd_ci_log_group_end

        pmd_ci_log_group_start "Update Github Releases"
            # create a draft github release
            pmd_ci_gh_releases_createDraftRelease "${PMD_CI_TAG}" "$(git rev-list -n 1 "${PMD_CI_TAG}")"
            GH_RELEASE="$RESULT"

            # Deploy to github releases
            pmd_ci_gh_releases_uploadAsset "$GH_RELEASE" "target/PMDExecExt.jar"

            # extract the release notes
            RELEASE_NAME="${PMD_CI_MAVEN_PROJECT_VERSION}"
            BEGIN_LINE=$(grep -n "^## " ReleaseNotes.md|head -1|cut -d ":" -f 1)
            END_LINE=$(grep -n "^## " ReleaseNotes.md|head -2|tail -1|cut -d ":" -f 1)
            END_LINE=$((END_LINE - 1))

            RELEASE_BODY="$(head -$END_LINE ReleaseNotes.md | tail -$((END_LINE - BEGIN_LINE)))"

            pmd_ci_gh_releases_updateRelease "$GH_RELEASE" "$RELEASE_NAME" "$RELEASE_BODY"
            # Publish release - this sends out notifications on github
            pmd_ci_gh_releases_publishRelease "$GH_RELEASE"

            # Upload it to sourceforge
            pmd_ci_sourceforge_uploadFile "pmd-bluej/pmd-bluej-${PMD_CI_MAVEN_PROJECT_VERSION}" "target/PMDExecExt.jar"
            pmd_ci_sourceforge_uploadReleaseNotes "pmd-bluej/pmd-bluej-${PMD_CI_MAVEN_PROJECT_VERSION}" "$(cat ReleaseNotes.md)"
        pmd_ci_log_group_end
    fi
}

build

exit 0
