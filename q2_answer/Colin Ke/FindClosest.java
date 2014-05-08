import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FindClosest {

    static private final int WEIBO = 0;
    static private final int TOPIC = 1;
    static private final int STD = 0;
    static private final int FILE = 1;
    static private final int OUTPUT = STD;

    private class Point {
        public Point() {
        }

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        double x, y;
    }

    private class Item {
        public Item(int id) {
            this.id = id;
            point = new Point();
        }

        public Item(int id, Point p) {
            this.id = id;
            this.point = p;
        }

        int id;
        Point point;
    }

    private class Query {
        public Query(int type) {
            this.type = type;
            point = new Point();
        }

        int type;
        int count;
        Point point;
    }

    private class ResultBean implements Comparable {
        int id;
        double distance;

        @Override
        public int compareTo(Object o) {
            if (this == o) return 0;
            if (!(o instanceof ResultBean)) return 1;
            ResultBean obj = (ResultBean) o;
            if (this.distance < obj.distance) return -1;
            else if (this.distance > obj.distance) return 1;
            else {
                if (this.id < obj.id) return -1;
                else if (this.id > obj.id) return 1;
                else return 0;
            }
        }
    }

    private class Node {
        public Node(Item item) {
            this.item = item;
            next = null;
            set = new HashSet<Integer>();
            set.add(item.id);
        }

        public Node(Item item, Node next) {
            this.item = item;
            this.next = next;
            this.set = next.set;
            set.add(item.id);
        }

        public int getBucketCount() {
            return set.size();
        }

        Item item;
        Node next;
        HashSet<Integer> set;
    }

    Node[][] weibos = new Node[365][365];
    Node[][] topics = new Node[365][365];
    Query[] queries;

    private Node getNode(Point p, int type) {
        int xi = (int) p.x + 180;
        int yi = (int) p.y + 180;
        if (type == WEIBO)
            return weibos[xi][yi];
        if (type == TOPIC)
            return topics[xi][yi];
        return null;
    }

    private void addNode(Item item, int type) {
        int xi = (int) item.point.x + 180;
        int yi = (int) item.point.y + 180;
        if (type == WEIBO) {
            if (weibos[xi][yi] == null)
                weibos[xi][yi] = new Node(item);
            else
                weibos[xi][yi] = new Node(item, weibos[xi][yi]);
        }
        if (type == TOPIC) {
            if (topics[xi][yi] == null)
                topics[xi][yi] = new Node(item);
            else
                topics[xi][yi] = new Node(item, topics[xi][yi]);
        }
    }

    private double getDistance(Point a, Point b) {
        return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
    }

    List<ResultBean> getClosestItems(Query qy) {
        int itemCount = 0;
        List<Node> nodes = new ArrayList<Node>();
        Point queryPoint = qy.point;
        int x1 = (int) queryPoint.x, x2 = (int) queryPoint.x, y1 = (int) queryPoint.y, y2 = (int) queryPoint.y;
        while (itemCount < qy.count) {
//            nodes.clear();
            itemCount = 0;
            for (int i = x1; i <= x2; ++i) {
                for (int j = y1; j <= y2; ++j) {
                    Node tem = getNode(new Point(i, j), qy.type);
                    if (tem == null) continue;
                    itemCount += tem.getBucketCount();
//                    nodes.add(tem);
                }
            }
            if (x1 != -180) --x1;
            if (x2 != 180) ++x2;
            if (y1 != -180) --y1;
            if (y2 != 180) ++y2;
//            System.out.println(x1+","+y1+" "+x2+","+y2+" itemCount:"+itemCount);
        }
        Point topLeft = new Point(x2, y2);
        double r = Math.sqrt(getDistance(topLeft, queryPoint))+3;
        while (x2 - queryPoint.x < r && queryPoint.x - x1 < r && y2 - queryPoint.y < r && queryPoint.y - y1 < r) {
            if (x1 != -180) --x1;
            if (x2 != 180) ++x2;
            if (y1 != -180) --y1;
            if (y2 != 180) ++y2;
//            System.out.println(x1+","+y1+" "+x2+","+y2+" r:"+r);
        }

        nodes.clear();
        itemCount = 0;
        for (int i = x1; i <= x2; ++i) {
            for (int j = y1; j <= y2; ++j) {
                Node tem = getNode(new Point(i, j), qy.type);
                if(tem == null) continue;
                itemCount += tem.getBucketCount();
                nodes.add(tem);
            }
        }

//        System.out.println("===========================");
        PriorityQueue<ResultBean> priorityQ = new PriorityQueue<ResultBean>();
        for (int i = 0; i < nodes.size(); ++i) {
            Node node = nodes.get(i);
            while (node != null) {
                ResultBean rb = new ResultBean();
                rb.distance = getDistance(node.item.point, queryPoint);
                rb.id = node.item.id;
                priorityQ.add(rb);
//                System.out.println("current node: "+node.item.id+node.item.point.x+","+node.item.point.y+" distance:"+rb.distance);
                node = node.next;
            }
        }
        List<ResultBean> res = new ArrayList<ResultBean>();
        if (qy.type == WEIBO) {
            for (int i = 0; i < qy.count; ++i) {
                res.add(priorityQ.poll());
            }
            return res;
        }
        if (qy.type == TOPIC) {
            HashSet<Integer> set = new HashSet<Integer>();
            while (res.size() != qy.count) {
                ResultBean rb = priorityQ.poll();
                if (!set.contains(rb.id)) {
                    set.add(rb.id);
                    res.add(rb);
                }
            }
            return res;
        }
        return null;
    }


    void dataInput() {
        int w, t, q;
        HashMap<Integer, Point> map = new HashMap<Integer, Point>();
        Scanner scanner = new Scanner(System.in);
        w = scanner.nextInt();
        t = scanner.nextInt();
        q = scanner.nextInt();
        queries = new Query[q];
        for (int i = 0; i < w; ++i) {
            Item weibo = new Item(scanner.nextInt());
            weibo.point.x = scanner.nextDouble();
            weibo.point.y = scanner.nextDouble();
            addNode(weibo, WEIBO);
            map.put(weibo.id, weibo.point);
        }
//        System.out.println("A");
//        String line = scanner.nextLine();
        for (int i = 0; i < t; ++i) {
            String line = scanner.nextLine();
            while(line.isEmpty())
                line = scanner.nextLine();
//            System.out.println(line);
            String[] arr = line.split(" ");

            int index = 0;
            int tId = Integer.parseInt(arr[index++]);
            int tNum = Integer.parseInt(arr[index++]);

            for (int j = 0; j < tNum; ++j) {
                int wId = Integer.parseInt(arr[index++]);
                Item topic = new Item(tId, map.get(wId));
                addNode(topic, TOPIC);
            }
        }
//        System.out.println("B");
        for (int i = 0; i < q; ++i) {
            Query qy = new Query(scanner.nextInt());
            qy.count = scanner.nextInt();
            qy.point.x = scanner.nextDouble();
            qy.point.y = scanner.nextDouble();
            queries[i] = qy;
        }
    }


    public static void main(String[] agrs) throws IOException {
        Long start = System.currentTimeMillis();
        FindClosest obj = new FindClosest();
        obj.dataInput();

//        System.out.println("input finished");
        File file = new File("c:\\b.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));

        List<ResultBean> list;
        for (Query qy : obj.queries) {
            list = obj.getClosestItems(qy);
            for (ResultBean rb : list){
                if(OUTPUT == STD)
                    System.out.print(rb.id+" ");
                if(OUTPUT == FILE)
                    bw.write(rb.id + " ");
            }
            if(OUTPUT == STD)
                System.out.println();
            if(OUTPUT == FILE)
                bw.write("\n");
        }



        Long end = System.currentTimeMillis();
//        System.out.println(end - start);
        bw.flush();
        bw.close();
    }
}