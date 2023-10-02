package doubleconnectedList
class ListNode<T>(
    val value: T
) {
    var nextNode: ListNode<T>? = null
    var previousNode: ListNode<T>? = null
}