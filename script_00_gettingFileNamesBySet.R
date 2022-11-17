################################################################################
# Author: Humberto Brandão
# Email: humberto@brandao.org
################################################################################

# This script just split the names of train and test, saving in two different
# files. It helps other processes, that are in charge of generating features.

library(data.table);

all <- fread("./input/public.csv");
train <- subset(all, !is.na(North));
test  <- subset(all, is.na(North));

trainFileNames <- train[,c("Filename")];
testFileNames  <- test[,c("Filename")];

fwrite(trainFileNames,"./train_FileNames.csv");
fwrite(testFileNames, "./test_FileNames.csv");

################################################################################
