# AISD
Сложность алгоритма https://github.com/AnastasiaKrachkovskaya/AISD/blob/main/aisd_lab1/src/main/kotlin/doubleconnectedList/DoubleConnectedList.kt#L47 в худшем случае О(1), т.к. мы не пробегаем по всему списку, а просто добавляем в конец.
Сложность алгоритма https://github.com/AnastasiaKrachkovskaya/AISD/blob/main/aisd_lab1/src/main/kotlin/dynamicarray/DynamicArray.kt#L41 в худшем случае O(n), т.к. худший случай - когда size=capacity и нам надо создавать новый массив. Чтобы скопировать элементы старого массива, нам надо пройтись по всем и добавить в новый, поэтому сложность О(n)
https://github.com/AnastasiaKrachkovskaya/AISD/blob/main/aisd_lab1/src/main/kotlin/algorithm/Algorithm.kt#L110  сложность этого алгоритма = О(n), т.к. мы пробегаем по всем элементам массива
