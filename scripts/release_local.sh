#!/bin/bash

gradle clean build publishToMavenLocal && sync
