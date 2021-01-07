#!/bin/bash

gradle build publishToMavenLocal "$@" --watch-fs && sync
