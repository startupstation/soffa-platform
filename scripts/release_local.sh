#!/bin/bash

gradle clean install uploadArchives
sync
