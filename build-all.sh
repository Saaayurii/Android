#!/bin/bash
set -e

echo "=== Building all Android practical works ==="

PROJECTS=(
    "practical1/basic"
    "practical1/advanced"
    "practical2/basic"
    "practical2/advanced"
    "practical3/basic"
    "practical3/advanced"
    "practical4/basic"
    "practical4/advanced"
)

ROOT_DIR="$(cd "$(dirname "$0")" && pwd)"

for project in "${PROJECTS[@]}"; do
    echo ""
    echo "--- Building $project ---"
    docker run --rm \
        -v "$ROOT_DIR/$project:/workspace" \
        -w /workspace \
        -e ANDROID_SDK_ROOT=/opt/android-sdk \
        -e GRADLE_USER_HOME=/workspace/.gradle-home \
        $(docker build -q "$ROOT_DIR") \
        gradle assembleDebug
    echo "--- $project DONE ---"
done

echo ""
echo "=== All projects built successfully ==="
