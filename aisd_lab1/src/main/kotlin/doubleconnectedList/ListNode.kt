package doubleconnectedList
class ListNode<T>(
    var value: T
) {
    var nextNode: ListNode<T>? = null
    var previousNode: ListNode<T>? = null
}