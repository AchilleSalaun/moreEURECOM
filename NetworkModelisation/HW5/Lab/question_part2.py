import sys, networkx, matplotlib, re
import matplotlib.pyplot as plt
# sys.argv, networkx 
# WARNING : To get the real node number you need to compute (index+1)
# WARNING : you have to sample the input file before the computation

# load gnutella in a list of pairs (node1,node2)
def loadGnutella():
        listLines = list(open(str(sys.argv[1]),'r').read().splitlines())
        startPattern = re.compile(r'^[0-9]+')
        endPattern = re.compile(r'[0-9]+$')
        gnutella = [[int(startPattern.findall(listLines[i])[0]),int(endPattern.findall(listLines[i])[0])] for i in range(0,len(listLines))]
        return gnutella

def buildGraph(gnutella):
	graph = networkx.Graph()
#	graph.add_node([1])
#	graph.remove_node(992)
	for i in range(0,len(gnutella)):
		graph.add_edge(gnutella[i][0],gnutella[i][1])
	return graph

#########################################################################################################
gnutella = loadGnutella()
graph = buildGraph(gnutella)

print("QUESTION 8")
#print graph.nodes()
print("diameter of the subnetwork = "+str(networkx.diameter(graph,e=None)))
print("QUESTION 9")
print("average distance = "+str(networkx.average_shortest_path_length(graph)))
#-------------------------------------------------------------------------------------------------------#
networkx.draw(graph)
plt.show()

