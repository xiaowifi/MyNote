# 前言 
# 正文
这个调调是： Set, List, Map, SortedSet, SortedMap, HashSet, TreeSet, ArrayList, LinkedList, Vector, Collections, Arrays, AbstractCollection
等数组的父类。
## Iterable 迭代器 

## Spliterator 
Spliterator（splitable iterator可分割迭代器）接口是Java为了并行遍历数据源中的元素而设计的迭代器，这个可以类比最早Java提供的顺序遍历迭代器Iterator，但一个是顺序遍历，一个是并行遍历。
<br>从最早Java提供顺序遍历迭代器Iterator时，那个时候还是单核时代，但现在多核时代下，顺序遍历已经不能满足需求了，如何把多个任务分配到不同核上并行执行，才是能最大发挥多核的能力，所以Spliterator应运而生啦

### Spliterators
## Stream 
### StreamSupport 
# 结束