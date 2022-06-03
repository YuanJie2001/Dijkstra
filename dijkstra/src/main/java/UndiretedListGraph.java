
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * @author chp
 *
 * @param <V>
 * 
 *            模拟一个无向图的邻接表实现
 */
public class UndiretedListGraph<V> implements Graph<V> {
	Vertex<V>[] vertexList; // 邻接 数组
	private int edges; // 边的数量
	private int vertices; // 顶点的数量

	public UndiretedListGraph(int size) {
		vertexList = new Vertex[size];
		this.edges = 0;
		this.vertices = 0;
	}

	class Vertex<V> {
		V data; // 顶点值
		LinkedList<Integer> adj; // 邻接表

		Vertex(V data) {
			this.data = data; // 顶点值
			this.adj = new LinkedList<>(); // 每个顶点的邻接点构成的邻接表
		}
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
		// 增加一个顶点
		this.vertexList[this.vertices++] = new Vertex<>(v);

	}

	@Override
	public void addEdge(V from, V to) {
		// 增加一条边
		int i = this.getPosition(from);
		int j = this.getPosition(to);

		this.vertexList[i].adj.add(j);
		this.vertexList[j].adj.add(i);
		this.edges++;
	}

	private int getPosition(V v) {
		// TODO Auto-generated method stub
		for (int i = 0; i < this.vertices; i++) {
			if (this.vertexList[i].data.equals(v)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void addEdge(V from, V to, int weight) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeEdge(V from, V to) {
		// 删除一条边
		int i = this.getPosition(from);
		int j = this.getPosition(to);

		this.vertexList[i].adj.remove(new Integer(j)); // 思考？
		this.vertexList[j].adj.remove(new Integer(i)); // 删除的应该是一个对象，而不是根据索引index来进行删除
		this.edges++;

	}

	@Override
	public void removeVertex(V v) {
		// 删除一个顶点
		/*
		 * 1. 查询顶点v的序号index 2. 更新边的个数 3. 删除所有邻接点对应的index 4. 在数组中删除当前的顶点 5. 更新顶点的个数 6.
		 * 更新所有邻接表中序号值，将所有大于index的值全部减
		 */
		int index = this.getPosition(v);

		this.edges = this.edges - this.degree(v);

		for (int i : this.vertexList[index].adj) {
			this.vertexList[i].adj.remove(new Integer(index));
		}

		for (int i = index; i < this.vertices - 1; i++) {
			this.vertexList[i] = this.vertexList[i + 1];
		}
		this.vertices--;
		for (int i = 0; i < this.vertices; i++) {
			LinkedList<Integer> list = vertexList[i].adj;
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j) > index) {
					list.set(j, list.get(j) - 1);
				}

			}
		}

	}

	@Override
	public void displayGraph() {
		// 显示当前图
		System.out.println("这是一个使用邻接表存储的图：");
		for (int i = 0; i < this.vertices; i++) {
			System.out.print("顶点：" + this.vertexList[i].data);
			System.out.println(",邻接表" + this.vertexList[i].adj);
		}

	}

	@Override
	public int degree(V v) {
		// TODO Auto-generated method stub
		return this.vertexList[getPosition(v)].adj.size();
	}

	@Override
	public void dfs() {
		// 深度优先遍历
		boolean[] beTraversed = new boolean[this.vertices];
		// 保存顶点的遍历状态，默认为false
		beTraversed[0] = true;
		System.out.print("深度优先遍历结果：");
		System.out.print(this.vertexList[0].data);
		this.dfs(0, beTraversed);
		System.out.println();
	}

	private void dfs(int i, boolean[] beTraversed) {
		// 从第i个顶点开始深度优先遍历
		for (int j : this.vertexList[i].adj) {
			if (!beTraversed[j]) {
				beTraversed[j] = true;
				System.out.print(this.vertexList[j].data);
				this.dfs(j, beTraversed);
			}
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
			System.out.print(this.vertexList[index].data);
			for (int i : this.vertexList[index].adj) {
				// 所有未被访问过的邻接点入队
				if (!beTraversed[i]) {
					beTraversed[i] = true;
					queue.offer(i);
				}
			}
		}
		System.out.println();

	}



}
