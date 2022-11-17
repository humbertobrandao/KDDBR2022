
# Feature generation (Part #1)

This repository contains the very first part for generating features of the 
challenge "KDD BR Competition 2022" of the team Artificial Psycho Killer.

## Requirements

* Unix-like system. It has been created on Ubuntu 22.04;
* R version 4.1.2 or higher and only 2 packages;
```
$ sudo sudo apt update -y ;
$ sudo apt install r-base - y ;
$ sudo R
$ > install.packages( c("data.table", "matrixStats") );
```

* Java 8 or higher;

```
$ sudo sudo apt update -y ;
$ sudo apt install openjdk-8-jdk -y ;
```

## Running the feature generation (part #1)

This procedure will create 3 data sets. Each one will take 6 hours of processing, approximately.

Here, I am showing two manners of running this project.

Easy method:

```
$ sudo chmod 755 ./script.sh
$ ./script.sh
```

Or you can run each command line, separately:

```
$ Rscript script_00_gettingFileNamesBySet.R
$ java -cp 2022_KDDBR.jar mainPackage.Main_04
$ Rscript script_01_creating_complete_04.R
$ java -cp 2022_KDDBR.jar mainPackage.Main_05
$ Rscript script_02_creating_complete_05.R
$ java -cp 2022_KDDBR.jar mainPackage.Main_06
$ Rscript script_03_creating_complete_06.R
```

Three datasets will be generated with the names:
* complete_04.csv
* complete_05.csv
* complete_06.csv

These three files are inputs for the next steps of the team's solution. You can see these generated files in the fold `./outputs`, but you generate as explained before, if you want.

If you have any question, you can ask me: `humberto@brandao.org`

