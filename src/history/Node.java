package history;
import task.Task;


class Node <T> {
    public Task data;
    public Node<Task> next;
    public Node<Task> prev;

    Node (Task data, Node next, Node prev){
        this.data = data;
        this.next = next;
        this.prev = prev;
    }
}
