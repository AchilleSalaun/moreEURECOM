import urllib, re, sys

def loadListURL(url):
	page = urllib.urlopen(url).read().splitlines()
	listURL = list()
	sublist = list()
	for i in range(0,len(page)):
		sublist = re.findall(r'href="/wiki/[^"]*"',page[i])
		for j in range(0,len(sublist)):
			listURL.append(sublist[j])
	for i in range(0,len(listURL)):
		listURL[i] = re.sub(r'href="','https://en.wikipedia.org',listURL[i])
		listURL[i] = re.sub(r'"','',listURL[i])
	return listURL

def noNone(tree):
	while None in tree:
		tree.remove(None)

def checkDuplicate(url,reference):
	if url in reference:
		return False
	else:
		reference.append(url)
		return True

def visit(tree,i,url,ref):
	noNone(tree)
	node = tree[i]
	tree.remove(tree[i])
	listURL = loadListURL(node[-1])
	if url not in listURL:
		for i in range(0,len(listURL)):	
			if checkDuplicate(listURL[i],ref):
				new = list(node)
				new.append(listURL[i])			
				tree.append(new)
		return []
	else:
		node.append(url)
		return node

def lookForNode(tree):
	n=0
	for i in range(1,len(tree)):
		if len(tree[i])<len(tree[n]):
			n = i
	return n

start = "https://en.wikipedia.org/wiki/"+sys.argv[1]
print('start point = '+start)
end = "https://en.wikipedia.org/wiki/"+sys.argv[2]
print('end point = '+end)
ref=list()
listURL = loadListURL(start)
listPaths = list() 
for i in range(0,len(listURL)):
	node = [start]
	node.append(listURL[i])
	listPaths.append(node)

shortestPath = []
k = 0
while shortestPath == []:
	noNone(listPaths)
	n = lookForNode(listPaths)
	k+=1
	#print(k)
	#print('n= '+str(n)) 
	#print('length= '+str(len(listPaths[n])))
	shortestPath = visit(listPaths,n,end,ref)

print('I visited '+str(k)+' pages to find, between '+start+' and '+end+', the folllowing shortest path :')
print(shortestPath)
print('You can link your two pages by only '+str(len(shortestPath)-1)+' clicks !')
