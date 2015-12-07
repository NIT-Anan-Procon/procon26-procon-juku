// scala source code

import scala.io.Source

object Dijkstra {

  var nodes :Seq[Node] = null
  var p_queue :PriorityQueue = new PriorityQueue()

  def main(args :Array[String]) {
    // 各値の取得
    val start = args(0).toInt
    val goal = args(1).toInt
    nodes = Node.getNodeSeq(Source.stdin.getLines, start, goal)

    p_queue.enqueue(nodes(start))
    updateCost(p_queue.dequeue)
    println(getRoute(nodes(goal)))
  }

  // 次のノードを調べてコスト更新
  def updateCost(node :Node) :Unit = {
    if(!node.is_used) {
      node.edge_cost.keys.filter{e => !nodes(e).is_used}.foreach { key =>
        // 注目ノードから接続できるで更新できるノードであれば更新する
        val tmp = node.node_cost + node.edge_cost(key)
        if(nodes(key).node_cost > tmp) {
          nodes(key).node_cost = tmp
          nodes(key).prev_node = Some(node)
        }
        p_queue.enqueue(nodes(key))
      }
      node.is_used = true
    }
    println(p_queue.toString)
    // (注目ノードが目的地でない && 優先度付きキューが空でない) ならば再起を続ける
    if(!node.isGoal && !p_queue.isEmpty) updateCost(p_queue.dequeue)
  }
  
  // 目的地から出発地まで戻りながらルートを見ていく
  def getRoute(node :Node) :String = {
    if(node.isStart) s"${node.id}"
    else {
      node.prev_node match {
        case Some(n) => {
          if(node.isGoal) s"最小コスト : ${node.node_cost}\n${getRoute(n)} - ${node.id}"
          else s"${getRoute(n)} - ${node.id}"
        }
        case None => s"目的地に到達できません - ${node.id}"
      }
    }
  }

}

// edge_cost = [next node, edge's cost]
// kind = 0:start, 1:goal, 2:others, -1:empty
case class Node(val id :Int,val edge_cost :Map[Int, Int], var kind :Short = 2, var node_cost :Int = Int.MaxValue, var is_used :Boolean = false, var prev_node :Option[Node] = None) {

  def isGoal() :Boolean = {
    kind == 1
  }

  def isStart() :Boolean = {
    kind == 0
  }

}

object Node {

  // 入力のパース
  def getNodeSeq(input :Iterator[String], start :Int, goal :Int) :Seq[Node] = {
    var i = -1
    var count_edge = 0
    val nodes = input.map{ line => 
      i+=1
      val node_info = line.split(',')
      val edges = Map.newBuilder[Int, Int]
      (0 until node_info.length).foreach{ i => 
        count_edge += 1
        val edge = node_info(i).split(':')
        edges += edge(0).toInt -> edge(1).toInt
      }
      Node(i, edges.result)
    }.toSeq
    println(s"ノード数 : ${nodes.size}")
    println(s"エッジ数 : ${count_edge}")
    nodes(start).kind = 0
    nodes(start).node_cost = 0
    nodes(goal).kind = 1
    nodes
  }

}

// 根ノードがノードコスト最小となるヒープで再現
class PriorityQueue() {

  // リストでヒープを表現
  var heap :Seq[Node] = Seq.empty
  // 空ノードを表現
  val none = Node(-1, null, -1)

  def enqueue(node :Node) :Unit = {
    if(!heap.contains(none)) {
      // 空ノードがなければリストに新しい枠を追加
      heap = upheap(node, heap :+ node, heap.length)
    } else {
      // 空ノードがあればにenqueueしたいノードを代入
      val i = heap.indexOf(none)
      heap = upheap(node, heap.updated(i, node), i)
    }
  }
  def upheap(node :Node, seq :Seq[Node], i :Int) :Seq[Node] = {
    if(i == 0) {
      // 根ノードまで到達したので代入して再起を止める
      seq.updated(i, node)
    }else {
      val parent = seq((i+1)/2-1)
      // 子ノードの方がコストが大きいので再起を止める
      if(node.node_cost >= parent.node_cost) seq.updated(i, node)
      // 子ノードの方がコストが小さいので再起を続ける
      else upheap(node, seq.updated(i, parent), (i+1)/2-1)
    }
  }

  def dequeue() :Node = {
    // 先頭要素(根ノード, 最小コストのノード)を取り出す
    val node :Node = heap.head
    heap = downheap(heap.updated(0, none))
    node
  }
  def downheap(seq :Seq[Node], i :Int = 0) :Seq[Node] = {
    // j は子ノードのインデックス
    var j = (i+1)*2-1
    if(j < seq.length) {
      if(j+1 < seq.length) {
        if(seq(j).node_cost > seq(j+1).node_cost) j += 1
      }
      // 子ノードとの入れ替えが生じるか
      if(seq(i).node_cost <= seq(j).node_cost) seq
      else downheap(seq.updated(i, seq(j)).updated(j, seq(i)), j)
    } else {
      // 子ノードがない
      seq
    }
  }

  def isEmpty() :Boolean = { heap.filter{e => e != none}.isEmpty }

  override def toString() :String = {
    s"${heap.map{e => s"${if(e != none) s"${e.id}:${e.node_cost}" else "none"}"}.toString()}"
  }
}
