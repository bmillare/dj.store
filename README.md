dj.store
========

Structure specification for a file based data store

# Rationale

Databases provide consistent access to data blobs, but do not provide tools for managing files. Filesystems are more optimized to managing large quantities of files of various sizes, but do not provide tools for organizing files. Users would like to be able to store and access files. Naively storing files can lead to name clashes.

dj.store takes inspiration from the design of datomic

# Concepts

* a store-id is a namespace and a number, which maps to a unique folder in the store
* namespaces act somewhat like partitions in datomic but serve to group ids together, namespaces are simply folders
* a store is a folder that contains namespaces
* the content corresponding to a store-id is the files in that folder
* while mutability is allowed, one should prefer creating new store-ids and copying content vs changing the content in existing store-ids
* a database can be used to manage store-ids so that arbitrary merging of different stores will not clash. In this use, different stores act as database "shards" in that the sum provides the abstraction of a full store.
* the namespace "db" and "user" is reserved
* multiple store-paths can act as a collective classpath