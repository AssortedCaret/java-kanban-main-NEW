package history;

import task.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    public Node <Task> first;
    public Node <Task> last;
    private final HashMap<Integer, Node> customMap = new HashMap<>();

    @Override
    public void add(Task task) {
        if (!(task == null)) {
            remove(task.getId());
            linkLast(task);
            customMap.put(task.getId(), last);
        }
        else
            return;
    }

    @Override
    public List getHistory() {
        /**
         * здесь необходимо менять метод для получения либо цифр, либо тасков(для себя)
         */
        return getTasks();
        //return getTasksNumber();
    }

    @Override
    public void remove(int id) {
        removeNode(id);
    }

    public void linkLast(Task task) {
        final Node node = new Node(task, last, null);
        if (first == null) {
            first = node;
        } else{
            node.prev = last;
            last.next = node;
            node.next = null;
        }
        last = node;
    }

    public String toString(Task task) {
        return task + ", \n";
    }

    /**
     * добавление в историю самих тасков
     * @return
     */
    public ArrayList getTasks() {
        ArrayList<String> tasks = new ArrayList<>();
        Node<Task> node = first;
        while (node != null) {
            tasks.add(toString(node.data));
            node = node.next;
        }
        /**
         * коммент проверки
         */
        //System.out.println(tasks);
        return tasks;
    }

    /**
     * добавление ID в историю
     * @return
     */
    public ArrayList getTasksNumber() {
        ArrayList <Integer> tasksID = new ArrayList<>();
        for(Integer id : customMap.keySet()){
            tasksID.add(id);
        }
        System.out.println(tasksID);
        return tasksID;
    }

    public void removeNode(int id) {
        final Node node = customMap.remove(id);
        if (node == null) {
            return;
        }
        final Node next = node.next;
        final Node prev = node.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
    }
}
