public interface Graph<V> {
	int edgesSize();	//边的数量
	int  verticesSize() ;		//顶点数量
	void addVertex(V v);	//增加一个顶点
	void addEdge(V from,V to);	//增加一条边
	void addEdge(V from,V to,int weight); //增加一条带权值的边
	
	void removeEdge(V from,V to);	//删除一条边
	void removeVertex(V v);			//删除某个顶点
	
	void displayGraph();		//显示一个图
	
	int degree(V v) ;		//返回顶点v的度
	
	void dfs();			//深度优先遍历
	void bfs();			//宽度优先遍历

}
