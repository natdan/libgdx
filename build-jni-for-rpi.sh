#!/bin/bash

cd gdx/jni
ant -f build-linux32-arm.xml
ant -f build-linux32-armgnueabi.xml
ant -f ant -f build-linux32-armgnueabihf.xml

cd ../../extensions/gdx-bullet/jni
ant -f build-linux32-arm.xml
ant -f build-linux32-armgnueabi.xml
ant -f ant -f build-linux32-armgnueabihf.xml

cd ../../gdx-freetype/jni
ant -f build-linux32-arm.xml
ant -f build-linux32-armgnueabi.xml
ant -f ant -f build-linux32-armgnueabihf.xml

cd ../../gdx-box2d/gdx-box2d/jni
ant -f build-linux32-arm.xml
ant -f build-linux32-armgnueabi.xml
ant -f ant -f build-linux32-armgnueabihf.xml

cd ../../../gdx-controllers/gdx-controllers-desktop/jni
ant -f build-linux32-arm.xml
ant -f build-linux32-armgnueabi.xml
ant -f ant -f build-linux32-armgnueabihf.xml

cd ../../../..

