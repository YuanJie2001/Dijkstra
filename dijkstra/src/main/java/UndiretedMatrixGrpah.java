import java.io.IOError;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * 
 * @author chp
 *
 * @param <V>
 * 
 *            模拟实现一个无向图（邻接矩阵）
 */

public class UndiretedMatrixGrpah<V> implements Graph<V> {
	private List<V> vertexList; // 保存顶点信息
	private int[][] edgeMatrix; // 保存邻接矩阵
	private int edges; // 边的数量
	private int vertices; // 顶点数量
	private int size; // 规模

	public UndiretedMatrixGrpah(int size) {
		this.size = size;
		vertexList = new ArrayList<>();
		edgeMatrix = new int[size][size];
		this.edges = 0;
		this.vertices = 0;
	}

	@Override
	public int edgesSize() {
		// TODO Auto-generated method stub
		return this.edges;
	}

	@Override
	public int verticesSize() {
		// TODO Auto-generated method stub
		return this.vertices;
	}

	@Override
	public void addVertex(V v) {
		// 加入一个顶点
		this.vertexList.add(v);
		this.vertices++;
	}

	@Override
	public void addEdge(V from, V to) {
		// TODO Auto-generated method stub
		this.addEdge(from, to, 1);
	}

	@Override
	public void addEdge(V from, V to, int weight) {
		// TODO Auto-generated method stub
		int i = this.vertexList.indexOf(from);
		int j = this.vertexList.indexOf(to);
		this.edgeMatrix[i][j] = weight;
		this.edgeMatrix[j][i] = weight;
		this.edges++;

	}

	@Override
	public void removeEdge(V from, V to) {
		// 删除一条边
		int i = this.vertexList.indexOf(from);
		int j = this.vertexList.indexOf(to);
		this.edgeMatrix[i][j] = 0;
		this.edgeMatrix[j][i] = 0;
		this.edges--;
	}

	@Override
	public void removeVertex(V v) {
		// 删除一个顶点
		int index = this.vertexList.indexOf(v); // 被删除顶点的序号

		for (int i = 0; i < this.vertices; i++) {
			if (this.edgeMatrix[index][i] != 0) { // 表示index与i之间有一条边
				this.edges--;
			}
		}

		// index后面的所有行前移一行
		for (int i = index; i < this.vertices - 1; i++) {
			for (int j = 0; j < this.vertices; j++) {
				this.edgeMatrix[i][j] = this.edgeMatrix[i + 1][j];
			}
		}

		// index后面的所有列前移一列
		for (int i = 0; i < this.vertices; i++) {
			for (int j = index; j < this.vertices - 1; j++) {
				this.edgeMatrix[i][j] = this.edgeMatrix[i][j + 1];
			}
		}

		this.vertexList.remove(index);
		this.vertices--;

	}

	@Override
	public void displayGraph() {
		// 显示当前图
		System.out.println("这是一个无向图（邻接矩阵）：");
		System.out.println("顶点：" + this.vertexList);
		System.out.println("图的邻接矩阵是：");
		for (int i = 0; i < this.vertices; i++) {
			for (int j = 0; j < this.vertices; j++) {
				System.out.print(this.edgeMatrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	@Override
	public int degree(V v) {
		// 返回图的顶点v的度

		int index = this.vertexList.indexOf(v); // 获取顶点的序号
		int count = 0;
		for (int i = 0; i < this.vertices; i++) {
			if (this.edgeMatrix[index][i] != 0) { // 表示index与i之间有一条边
				count++;
			}
		}
		return count;
	}

	@Override
	public void dfs() {
		// 深度优先遍历
		boolean[] beTraversed = new boolean[this.vertices];
		// 保存顶点的遍历状态，默认为false
		beTraversed[0] = true;
		System.out.print("深度优先遍历结果：");
		System.out.print(this.vertexList.get(0));
		this.dfs(0, 1, beTraversed);
		System.out.println();

	}

	private void dfs(int x, int y, boolean[] beTraversed) {
		// 遍历x的第y个邻接点
		while (y < this.vertices) {
			if (this.edgeMatrix[x][y] != 0 && !beTraversed[y]) {
				beTraversed[y] = true;
				System.out.print(this.vertexList.get(y));
				this.dfs(y, 0, beTraversed); // 从y的第0个邻接点开始深度优先遍历
			}
			y++;
		}

	}

	@Override
	public void bfs() {
		// 宽度优先遍历
		boolean[] beTraversed = new boolean[this.vertices];
		// 保存顶点的遍历状态，默认为false
		beTraversed[0] = true;
		Queue<Integer> queue = new LinkedList<>();
		beTraversed[0] = true;
		queue.offer(0);

		System.out.print("宽度优先遍历：");
		while (!queue.isEmpty()) {
			int index = queue.poll();
			System.out.print(this.vertexList.get(index));
			for (int i = 0; i < this.vertices; i++) {
				if (this.edgeMatrix[index][i] != 0 && !beTraversed[i]) {
					// 找出所有没有访问过的邻接点
					beTraversed[i] = true;
					queue.offer(i);
				}
			}
		}
		System.out.println();

	}

	/*
	 * 使用prim算法生成最小生成树
	 */
	public void prim() {
		ArrayList<Integer> listU = new ArrayList<>(); // 存储已经生成树的顶点集合
		listU.add(0);
		ArrayList<Integer> listV = new ArrayList<>(); // 还没有纳入生成树的顶点集合
		for (int i = 1; i < this.vertices; i++) {
			listV.add(i);
		}
		int miniWeight;
		int miniFrom = -1;
		int miniTo = -1;
		while (!listV.isEmpty()) {
			// 找出两个集合之间所有边的最小值
			miniWeight = Integer.MAX_VALUE; // 新增一个顶点时，查找每次两个集合之间边权值最小值
			for (int i : listU) {
				for (int j : listV) {
					if (this.edgeMatrix[i][j] != 0 && this.edgeMatrix[i][j] < miniWeight) {
						miniWeight = this.edgeMatrix[i][j];
						miniFrom = i;
						miniTo = j;
					}
				}
			}
			listU.add(miniTo);
			listV.remove(new Integer(miniTo));

			System.out.println("Edge: <" + this.vertexList.get(miniFrom) + ", " + this.vertexList.get(miniTo) + "> : "
					+ miniWeight);

		}
	}
	/**
	 * 	内部类：边
	 */
	class Edge<V>{
		V from;			//起始顶点
		V to ;			//结束顶点
		int weigth;	//边的权值
		public Edge(V from, V to, int weigth) {
			super();
			this.from = from;
			this.to = to;
			this.weigth = weigth;
		}
		@Override
		public String toString() {
			return "Edge [from=" + from + ", to=" + to + ", weigth=" + weigth + "]";
		}
		
	}
	
	public void kruskal(){
		ArrayList<Edge> edgelist = new ArrayList<>(); //边列表
		int[] list = new int[this.vertices];		//list[i]保存了顶点i所在连通分量的序号最大的顶点序号
		int count = 0 ;			//最小生成树边的数量
		for(int i = 0;i<this.vertices;i++) {
			for(int  j = i+1 ; j<this.vertices;j++) {
				if(this.edgeMatrix[i][j] != 0) {
					edgelist.add( new Edge<V>(this.vertexList.get(i), this.vertexList.get(j), this.edgeMatrix[i][j]));
				}
			}
		}
		Collections.sort(edgelist, new Comparator<Edge>() {

			@Override
			public int compare(Edge o1, Edge o2) {
				// TODO Auto-generated method stub
				return o1.weigth - o2.weigth;
			}
		});
		System.out.println(edgelist);
		
		System.out.println("最小生成树边：");
		
		for(Edge edge:edgelist) {					//依次处理权值最小的边
			int from = this.vertexList.indexOf(edge.from);   //边起始顶点的序号
			int to = this.vertexList.indexOf(edge.to);		//边结束顶点的序号
			
			int  m = this.getEnd(from,list);		//查找当前顶点所在连通分量序号值最大的顶点
			int  n = this.getEnd(to,list);			//查找当前顶点所在连通分量序号值最大的顶点
			
			if(m != n) {				//加入当前这条边，不构成回路
				list[m] = n;			//修改前面一个连通分量最大顶点指向的下一个最大顶点序号
				System.out.println(edge);
				count++;
				if(count == this.vertices-1) {
					break;
				}
			}
			
		}
	}

	private int getEnd(int x,int[] list) {
		while(list[x] != 0) {
			x = list[x];
		}
		
		return x;
	}
	
}
