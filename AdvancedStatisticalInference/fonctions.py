import numpy as np
import random as rd
import math
#### REGRESSION ####
def linearRegression(dataset,model):
# dataset : set of the (x,t)
# model : we model our problem as t=sum_i(w_i*model[i](x)) with w as what we're looking for
	N= len(dataset)

	# defines t
        t = [dataset[i][1] for i in range(0,N)]

        # Computes X given model
        X = np.array([[ model[i](dataset[j][0])for i in range(0,len(model))] for j in range(0,N)])
        tX = np.transpose(X)

        # Computes w
        w = np.dot(np.dot(np.linalg.inv(np.dot(tX,X)),tX),t)

	# Computes sigma2 : loss a priori between our model (ideal) and the real values (noiisy)
	s2 = distance(t,np.dot(X,w))/N

	return X,w,t,s2

def predict(model,w,x_new):
	xx = [model[i](x_new) for i in range(0,len(model))]
#	e  = np.random.normal(0,math.sqrt(s2)) >> no noise in prediction
	return np.dot(np.transpose(w) ,xx)#+e

#### SUPERVISED CLUSTERING ####

def kNearestNeighbours(dataset, newPoint, distance, k):
# newPoint   : element whose we want the label
# dataset : dataset of pairs (element, label)
# distance   : function defining the distance between two elements
# k          : parameter of the k-nn algorithm

	# We sort the dataset : the closest elements to newPoint first
	sortedDataset = sorted(dataset, key= lambda x : distance(newPoint,x[0]))

	# We pick the k closest/first elements
	neighbours = sortedDataset[0:k]

	# We create the list of the different labels present in the k nearest neighbours, we count them
	from collections import Counter
	labelList = [n[1] for n in neighbours]
	labelList = Counter(labelList).most_common()
	
	# We select one of the most present labels
	L=[labelList[0][0]]	
	
	for k in range(0,len(labelList)):
		if labelList[k][1]==labelList[0][1]:
			L.append(labelList[k][0])

	# We pick one
	return rd.choice(L)

def distance(x0,y0):
        x = np.array(x0)
        y = np.array(y0)
        return np.dot(x-y,x-y)

def supportVectorMachine(dataset,newPoint,distance,C): #TO_IMPLEMENT
# dataset : dataset with labels
# C       : paramater for soft-margin

        N = len(dataset)
        # Optimization by quadratic programming
        alpha = [0 for _ in range(0,N)]
                ### todo : ALPHA COMPUTATION ###
                ### cf p42 of lecture 9 (ASI course)

        # Compute w
        w = alpha[0]*dataset[0][1]*np.array(dataset[0][0])
        for n in range(1,N):
                aux = alpha[n]*dataset[n][1]
                aux = aux*np.array(dataset[n][0])
                w = np.sum([w,aux],axis=0)

        # Compute b
        t_n = kNearestNeighbours(dataset,newPoint,distance,1)
        b = t_n-np.dot(w,newPoint)

        return

def pointEstimate(dataset):
	

def micropolisHasting(dataset, model, N,pv): #TO_IMPLEMENT
# dataset : x+t
# N : number of recursions
# pv : prior variance for Laplace computation
        
	# initialize by a first linear regression
	lr= linearRegression(dataset,model)
	X = lr[0]
	w = lr[1]
	t = lr[2]

        # Computes g and log g for the laplace model
        def laplacecomp(w,X,t,pv):
                logg = -(1/(2*pv))*np.dot(w,w)
                P = 1./(1+math.exp(-np.dot(X*w)))
                logl = sum(np.dot(t,math.log(P)+np.dot((1-t),log(1-P))))
                logg = logg + logl

        for s in range(0,N):
                old = w_s
                w_s = np.random.randn(2,1)
                logr = laplacecomp(w_s,X,t,pv)-laplacecomp(old,X,t,pv)

                if r<0 :
                        u = np.random.rand()
                        if u>math.exp(r) :
                                w_s = old

        return w_s


#### UNSUPERVISED CLUSTERING ####

def kCentroids(dataset,computeCentroids,distance,k):
# dataset         : dataset without any label
# computeCentroid : function returning the centroid of a given cluster
# distance        : function defining the distance between two elements
# k               : parameter of the k-cenntroids algorithm

	# INITIALIZATION : we split equally our dataset defining k clusters, we compute our first centroids	
	l = int(len(dataset)/k)	
	clusters = [dataset[l*i:l*(i+1)] for i in range(0,k-1)]
	clusters.append(dataset[len(clusters):len(dataset)])
	newCentroids = [computeCentroids(cluster) for cluster in clusters]
	centroids = list()

	# RECURSION      : while we didn't reach the equilibrium (exists since the dataset is finite)
	while (set(map(tuple,centroids)) != set(map(tuple,newCentroids))):
		centroids = newCentroids
		l = len(centroids)
		labelledDataset = [[centroids[i],i] for i in range(0,l)]

		# ... we compute new cluster given new centroids
		clusters = [[] for _ in range(0,k)]
		for element in dataset :
			index = kNearestNeighbours(element,labelledDataset,distance,1)
			clusters[index].append(element)

		# ... we compute new centroids given new clusters
	        newCentroids = [computeCentroids(cluster) for cluster in clusters]

	return clusters

def computeCentroids(cluster):
        # cluster : liste de points
        l = len(cluster)
        return [sum([e[0] for e in cluster])/l,sum([e[1] for e in cluster])/l]

#### VALIDATION ####

def crossValidation(algorithm,errorFunction,parameters,trainingData,testData): # TO_IMPLEMENT
# algorithm : algorithm we want to obtain the best set of parameters
# parameters : set of values/configuration for parameters we want to compare
# trainingData : dataset used to train the algorithm 
# testData : dataset used to benchmark the algorithm

	bestL = 0

	for p in parameters:
		# Recrecreate paramaters for algorithm
		trainingParam = [trainingData].append(p)
		# Train the algorithm
		trained = algorithm(trainingParam)

		# Compute the discriminating criteria
		prediction  = [predict(trained,testData[i][0]) for i in range(0,len(testData))]
		expectation = [testData[i][1] for i in range(0,len(testData))]
		L = errorFunction(expectation,prediction)
			
		# Compare what is best
		if L > bestL :
			bestL = L
			bestParameters = p
	return bestParameters, bestL

def loss(expectation,prediction):
	# just return the mean square error
	return distance(expectation,prediction)/len(prediction)

def likelihood(expectation,prediction,s2):
	# joint likelihood, expectation are supposed to be independent
	s = math.sqrt(s2)
	return np.prod([ 1/(s*math.sqrt(2*math.pi))*math.exp(-(expectation[i]-prediction[i])**2/(2*s2)) for i in range(0,len(expectation))])
