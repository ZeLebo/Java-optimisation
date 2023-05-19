## Strong, Weak, Soft and Phantom reference to an object in memory
### Table of contents
1. [Strong reference](#strong-reference)
2. [Weak reference](#weak-reference)
3. [Soft reference](#soft-reference)
4. [Phantom reference](#phantom-reference)

### Intro
All types in Java lie under two categories
Primitive Types: There are 8 primitive types (byte, short, int, long, float, double, char and boolean) in Java which holds their values directly in form of bits. 
Reference Types: All types other than primitive lies under the category of reference types e.g. Classes, Interfaces, Enums, Arrays etc. 

### Strong reference

If you have a strong reference to an object, then the object can never be collected/reclaimed by GC (Garbage Collector).
These are your regular object references which we code daily:
```java
    Employee emp = new Employee();
```
The variable “emp” holds a strong reference to an Employee object and objects that are reachable through any chain of strong references are not eligible for garbage collection. 
Usually, this is what you want but not always. Now suppose we are fetching lots of employees from database in a collection or map, and we need to do a lot of processing on them regularly, 
So in order keep performance we will keep them in the cache.

As far as this is good, but now we need different data, and we don’t need those Employee objects and these are not referenced from anywhere except the cache. 
Which is causing a memory leak because these objects are not in use but still not eligible for the garbage collection, and we cannot remove those objects from cache 
because we don’t have reference to them? 
So here either we need to empty the entire cache manually which is tedious, or we could use other kind references e.g. Weak References.

### Weak reference
If you only have weak references to an object (with no strong references), then the object will be reclaimed by GC in the very next GC cycle.
A weak reference does not pin an object into memory and will be GC’d in next GC cycle if not referenced from other references. 
We can use WeakReference class which is provided by Java to create above kind of caches, which will not store objects which are not referenced from somewhere else.

```java
    WeakReference<Cache> cache = new WeakReference<Cache>(data);
```
To access data you need to call ```cache.get()```. This call to get may return null if the weak reference was garbage collected: you must check the returned value to avoid NPEs. 
Java provides collections that use weak references e.g., the WeakHashMap class stores keys (not values) as weak references. 
If the key is GC’d then the value will automatically be removed from the map too.

Since weak references are objects too we need a way to clean them up (they’re no longer useful when the object they were referencing has been GC’d). 
If you pass a ReferenceQueue into the constructor for a weak reference then the garbage collector will append that weak reference to the ReferenceQueue before they’re finalized or GC’d. 
You can periodically process this queue and deal with dead references.

### Soft reference

If you only have soft references to an object (with no strong references), then the object will be reclaimed by GC only when JVM runs out of memory.

A SoftReference is like a WeakReference, but it is less likely to be garbage collected. 
Soft references are cleared at the discretion of the garbage collector in response to memory demand. 

The virtual machine guarantees that all soft references to softly reachable objects will have been cleared before it would ever throw an OutOfMemoryError.


### Phantom reference
We create phantom references to an object to keep track of when the object gets enqueued into the ReferenceQueue. 
Once you know that you can perform fine-grained finalization. (This would save you from accidentally resurrecting the object as phantom-reference don't give you the referent)
Phantom references are the weakest of all reference types, calling get on them will always return null. An object is phantom referenced after it has been finalized, but before its allocated memory has been reclaimed, 
As opposed to weak references which are enqueued before they’re finalized or GC’d Phantom references are rarely used.

So how are they useful? When you construct a phantom reference you must always pass in a ReferenceQueue. This indicates that you can use a phantom reference to see when your object is GC’d.

Hey, so if weak references are enqueued when they’re considered finalize but not yet GC’d we could create a new strong reference to the object in the finalizer block and prevent the object being GC’d. 
Yep, you can, but you probably shouldn’t do this. To check for this case the GC cycle will happen at least twice for each object unless that object is reachable only by a phantom reference. 
This is why you can run out of heap even when your memory contains plenty of garbage. Phantom references can prevent this.
