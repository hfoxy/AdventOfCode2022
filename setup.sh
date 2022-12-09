#!/usr/bin/env bash

if [[ -z "$1" ]]; then
  echo "No day specified"
  exit 1
fi

if [[ -z "$2" ]]; then
  echo "No part specified"
  exit 1
fi

task="day$1-part$2"
dir="days/$task"
name="day $1 part $2"

if [ -d "$dir" ]; then
    echo "Directory $dir already exists."
    exit 2
fi

# if there are issues with this on mac, you need to use set -i '' ...
echo "Copying template"
cp -r template "$dir"
sed -i '' "s#template#$task#g" "$dir/pom.xml"
git add "$dir"

# if there are issues with this on mac, you need to use set -i '' ...
echo "Copying workflow template"
cp .github/workflows/template.yml ".github/workflows/$task.yml"
sed -i "s#name: template#name: $name#g" ".github/workflows/$task.yml"
sed -i "s#template#$dir#g" ".github/workflows/$task.yml"
git add ".github/workflows/$task.yml"

#echo "Copying run configuration template"
#cp .run/template.run.xml ".run/$name.run.xml"
#sed -i "s#<module name=\"template\" />#<module name=\"$task\" />#g" ".run/$name.run.xml"
#sed -i "s#name=\"template\" type=\"Application\"#name=\"$name\" type=\"Application\"#g" ".run/$name.run.xml"
#git add ".run/$name.run.xml"
