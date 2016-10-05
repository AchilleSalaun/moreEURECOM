import sys, re, numpy, random
# sys.argv, re.compime, numpy.mean, random.randint
# WARNING : To get the real node number you need to compute (index+1)

# load gnutella in a list of pairs (node1,node2)
def loadGnutella():
	listLines = list(open(str(sys.argv[1]),'r').read().splitlines())
	startPattern = re.compile(r'^[0-9]+')
	endPattern = re.compile(r'[0-9]+$')
	gnutella = [[int(startPattern.findall(listLines[i])[0]),int(endPattern.findall(listLines[i])[0])] for i in range(0,len(listLines))]
	return gnutella

# count the total number of nodes (WARNING : need the nodes named by their index)
def numberNodes(gnutella):
	number = 0
	for i in range(0,len(gnutella)):
		for j in range(0,2):
			if(number<gnutella[i][j]):
				number = gnutella[i][j]
	return number

# build the adjacency matrix
def buildAdjacencyMatrix(gnutella):
	N = numberNodes(gnutella)
	matrix = [[0 for j in range(0,N)] for i in range(0,N)]
	for l in range(0,len(gnutella)):
			line = gnutella[l]
			i = line[0]
			j = line[1]
			matrix[i-1][j-1]=1
			matrix[j-1][i-1]=1
	return matrix

# for a given list of nodes, will return the list of associated degrees
def nodeDegree(matrix,listNodes):
	listDegree = list()
	for l in range(0,len(listNodes)):
		line = matrix[listNodes[l]]
		degree = 0
		for i in range(0,len(line)):
			degree += line[i]
		listDegree.append(degree)
	return listDegree

# give the degree distribution in a list (d : x --> distribution[x])
def degreeDistribution(listDegree):
	degreeMax = max(listDegree)
	distribution = [0 for _ in range(0,degreeMax+1)]
	for i in range(0,len(listDegree)):
		distribution[listDegree[i]]+=1
	return distribution

# create a "dictionary" : the node n leads to the list of nodes graph[n]
def buildGraph(matrix):
	graph = list()
	N = len(matrix)
	for i in range (0,N):
		line = list()
		for j in range(0,N):
			if(matrix[i][j]==1):
				line.append(j)
		graph.append(line)
	return graph

# compute the clustering coefficient (float)
def clusteringCoefficient(graph,matrix):
	triangles = 0
	triples = 0
	for n in range(0,len(graph)):
		n_neighbours = len(graph[n])
		triples += n_neighbours*(n_neighbours-1)/2
		if(len(graph[n])>1):
			for i in range(0,n_neighbours-1):
				for j in range(i+1,n_neighbours):
					node_i = graph[n][i]
					node_j = graph[n][j]
					if(matrix[node_i][node_j]==1):
						triangles += 1
	return float(triangles)/float(triples)

# choose a node following an uniform law
def uniform(matrix,line):
	return line[random.randint(0,len(line)-1)]

# choose a node following the Monte Carlo rule
def monteCarlo(matrix,line):
	l = len(line)
	neighbourNumber = sum(nodeDegree(matrix,line))
	listProba = list()
	for i in range(0,l):
		listNodes = list()
		listNodes.append(line[i])
		p = float(nodeDegree(matrix,listNodes)[0])/float(neighbourNumber)
		listProba.append(p)
	normalisation = 0
	for i in range(0,l):
		listProba[i]=1./float(listProba[i])
		normalisation += listProba[i]
	for i in range(0,l):
		listProba[i] = float(listProba[i])/float(normalisation)
	for i in range(1,l):
		listProba[i]+=listProba[i-1]
	a = random.random()
	for i in range(0,l):
		if(a<listProba[i]):
			return line[i]
	return line[l]

# implement a random walk during "steps" steps, starting from "start" and given the "nextOne" rule.
# this method returns the list of all the steps
def randomWalk(matrix,graph,start,steps,nextOne):
	if(graph[start]==[]):
		print("WARNING : random walk from a lonely node !")
		return [start]
	listSteps = [start]
	node = start
	for n in range(0,steps):
		line = graph[node]
		node = nextOne(matrix,line)
		listSteps.append(node)
	return listSteps

def resize(matrix,size):
	newMatrix = [[0 for j in range(0,size)] for i in range(0,size)]
	for i in range(0,size):
		for j in range(0,size):
			newMatrix[i][j]=matrix[i][j]
	return newMatrix


#########################################################################################################

print"QUESTION 1"
gnutella = loadGnutella()

N = numberNodes(gnutella)
print "N = "+str(N)
M = len(gnutella)
print "M = "+str(M)
ADJ = buildAdjacencyMatrix(gnutella)
print "ADJ built succesfully :-)"
graph = buildGraph(ADJ)
print "Graph built succesfully :-) \n"

#-------------------------------------------------------------------------------------------------------#
print"QUESTION 2"
print "p = averageDegree/totalNodeNumber\n" # !!! TO CHECK !!!

#-------------------------------------------------------------------------------------------------------#
print"QUESTION 3"
listDegree = nodeDegree(ADJ,[i for i in range(0,N)])
distribution = degreeDistribution(listDegree)
print "degree distribution = "+str(distribution)+"\n"
maximum = max(listDegree)
print "degree maximum = "+str(maximum)
minimum = min(listDegree)
print "degree minimum = "+str(minimum)
average = numpy.mean(listDegree)
print "average degree = "+str(average)+"\n"

#-------------------------------------------------------------------------------------------------------#
print"QUESTION 4"
cc = clusteringCoefficient(graph,ADJ)
print"clustering coefficient = "+str(cc)+"\n"

#-------------------------------------------------------------------------------------------------------#
print"QUESTION 5"
samples = list()
for i in range(0,5):
	start = random.randint(0,N-1)
	while(graph[start]==[]):
		start = start + 1 % N
#	print str(start)+" "+str(graph[start])
	walk = randomWalk(ADJ,graph,start,400,uniform)
	samples+=walk
samples = list(set(samples))
# print(samples)
listDegree_sampled = nodeDegree(ADJ,samples)
distribution_sampled = degreeDistribution(listDegree_sampled)
print("sampled degree distribution = "+str(distribution_sampled))

#-------------------------------------------------------------------------------------------------------#
print"QUESTION 6"
samples = list()
for i in range(0,5):
        start = random.randint(0,N-1)
        while(graph[start]==[]):
                start = start + 1 % N
#       print str(start)+" "+str(graph[start])
        walk = randomWalk(ADJ,graph,start,400,monteCarlo)
        samples+=walk
samples = list(set(samples))
# print(samples)
listDegree_sampled = nodeDegree(ADJ,samples)
distribution_sampled = degreeDistribution(listDegree_sampled)
print("sampled (Monte Carlo) degree distribution = "+str(distribution_sampled))
