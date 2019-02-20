#!/usr/bin/env bash
echo Control File $1
echo export folder $2
export OFSAAControlFile=$1
export OFSAAOutputDir=$2

mvn clean -Dit.test=OFSAADataQuality verify -P UnitTest -DOFSAAControlFile=$1 -DOFSAAOutputDir=$2


