# Useful commands. Run 'make help' to show available tasks.
# ------
# Linux: no requirements needed (except Gradle and a JDK), it should work as it.
# Windows: tested with GNU Make 4.3 installed with Chocolatey, and Unix tools (installed with Git) available from path. WSL may also work.
# macOS: I don't have an Apple computer so I can't test, but it should work as it.

ifeq ($(OS),Windows_NT)
    gradlew_cmd := gradlew
else
    gradlew_cmd := ./gradlew
endif


default: help


.PHONY: intro
intro:
	@echo -e '\n\e[1;34m------ [ij-ilove-devtoys] $(shell date) ------\e[0m\n'


.PHONY: log
log: intro ## run "git log" with pretty colors
	git log --pretty=format:"%C(green)%h\\ %C(yellow)[%ad]%Cred%d\\ %Creset%s%C(cyan)\\ [%cn]" --decorate --date=relative


.PHONY: wrapper
wrapper: intro ## set or upgrade gradle wrapper to version "v" (example: make wrapper v=7.4.2)
	gradle wrapper --gradle-version=${v} --no-daemon


.PHONY: fixgit
fixgit: intro ## fix executable permission flag on git index for required files
	git update-index --chmod=+x gradlew
	@echo -e '\e[1m"gradlew" should now have executable flag on git\e[0m'


.PHONY: run
run: intro ## run plugin in IntelliJ
	${gradlew_cmd} buildPlugin runIde --warning-mode all


.PHONY: runeap
runeap: intro ## run plugin in latest IntelliJ EAP Snapshot
	${gradlew_cmd} buildPlugin runIde --warning-mode all -PpluginIdeaVersion=IC-LATEST-EAP-SNAPSHOT -PpluginDownloadIdeaSources=false


.PHONY: build
build: intro ## build and package plugin to build/distribution/ (see generated ZIP file)
	${gradlew_cmd} clean buildPlugin test verifyPlugin --warning-mode all


.PHONY: test
test: intro ## run unit tests
	${gradlew_cmd} cleanTest test verifyPlugin --warning-mode all


.PHONY: cv
cv: intro ## check dependencies and Gradle updates
	${gradlew_cmd} dependencyUpdates --warning-mode all


.PHONY: cvnd
cvnd: intro ## check dependencies and Gradle updates (use a single-use Gradle daemon process by using --no-daemon)
	${gradlew_cmd} dependencyUpdates --warning-mode all --no-daemon


.PHONY: oga
oga: intro ## check for deprecated groupId and artifactId coordinates
	${gradlew_cmd} biz-lermitage-oga-gradle-check


.PHONY: svgo
svgo: intro ## optimize SVG icons with SVGO (SVGO must be present, type "npm install -g svgo" if needed)
	svgo --folder=src/main/resources/ --recursive


.PHONY: help
help: intro
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":[^:]*?## "}; {printf "\033[1;38;5;69m%-15s\033[0;38;5;38m %s\033[0m\n", $$1, $$2}'

