#!/bin/bash
cd ../

export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
java -version

function gradlePath {
	CD="$PWD"
	while [ "$CD" != "" ]; do
		if [ -x "$CD/gradlew" ]; then
			GRADLE_PATH=$CD/gradlew
			return 0
		fi
		CD="${CD%/*}"
	done

	GRADLE_PATH=$(which 'gradle')

	if [ "$GRADLE_PATH" = "" ]; then
		GRADLE_PATH=/usr/local/bin/gradle
	fi
}
gradlePath

if [ "$XRT_SKIP_DEX" == "Yes" ]; then
	echo "Compiling ART only..."
	"$GRADLE_PATH" --daemon buildArtOat -Pxrt.archs=${arch}
	exit
fi

"$GRADLE_PATH" --daemon compileJava proguard buildRetro buildDex buildArtOat buildPreregister -Pxrt.archs="${ARCHS}"

