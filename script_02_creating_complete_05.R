################################################################################
# Author: Humberto Brandão
# Email: humberto@brandao.org
################################################################################

# This script summarize statistically data, which we create using Main_05 in 
# Java, in features to be used in ML

library(data.table);
library(matrixStats);

train <- fread("./input/public.csv");

auxTrain <- fread("./aux_05_train.csv");
auxTest <- fread("./aux_05_test.csv");

aux <- rbind(auxTrain, auxTest);
names(aux)[1] <- "Filename";

cols_W <- names(aux)[seq(2,length(names(aux)),3)];
cols_N <- names(aux)[seq(3,length(names(aux)),3)];
cols_D <- names(aux)[seq(4,length(names(aux)),3)];

ws <- aux[,..cols_W];
ns <- aux[,..cols_N];
ds <- aux[,..cols_D];

n_cols_W <- ncol(ws);
n_cols_N <- ncol(ns);
n_cols_D <- ncol(ds);

w_std    <- matrixStats::rowSds(as.matrix(ws));
w_mean   <- matrixStats::rowMeans2(as.matrix(ws));
w_median <- matrixStats::rowMedians(as.matrix(ws));
w_min    <- matrixStats::rowMins(as.matrix(ws));
w_max    <- matrixStats::rowMaxs(as.matrix(ws));
w_var    <- matrixStats::rowVars(as.matrix(ws));

n_std    <- matrixStats::rowSds(as.matrix(ns));
n_mean   <- matrixStats::rowMeans2(as.matrix(ns));
n_median <- matrixStats::rowMedians(as.matrix(ns));
n_min    <- matrixStats::rowMins(as.matrix(ns));
n_max    <- matrixStats::rowMaxs(as.matrix(ns));
n_var    <- matrixStats::rowVars(as.matrix(ns));

d_std    <- matrixStats::rowSds(as.matrix(ds));
d_mean   <- matrixStats::rowMeans2(as.matrix(ds));
d_median <- matrixStats::rowMedians(as.matrix(ds));
d_min    <- matrixStats::rowMins(as.matrix(ds));
d_max    <- matrixStats::rowMaxs(as.matrix(ds));
d_var    <- matrixStats::rowVars(as.matrix(ds));

ws <- cbind(ws, 
            w_std = w_std,
            w_mean = w_mean,
            w_median = w_median,
            w_min = w_min,
            w_max = w_max,
            w_var = w_var
            );

ns <- cbind(ns, 
            n_std = n_std,
            n_mean = n_mean,
            n_median = n_median,
            n_min = n_min,
            n_max = n_max,
            n_var = n_var
);

ds <- cbind(ds, 
            d_std = d_std,
            d_mean = d_mean,
            d_median = d_median,
            d_min = d_min,
            d_max = d_max,
            d_var = d_var
);

names(ws);
names(ns);
names(ds);

tmp <- cbind(Filename = aux$Filename, ws, ns, ds);

aux <- tmp;

rm(tmp, ns, ws, ds);
gc();

names(aux) <- paste("base_05_",names(aux),sep="");
names(aux)[1] <- "Filename";

fwrite(aux, "./complete_05.csv");
