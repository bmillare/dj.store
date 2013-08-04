(ns dj.store.clj
  (:require [dj]
            [dj.io]
            [dj.repl]))

(defn load-clj-store-id
  "

to interface storage of clojure code within a store-id, assume code is
loaded in load.clj and data is read in a data.clj file

"
  ([full-store-id-path]
     (load-file (dj/str-path full-store-id-path "load.clj")))
  ([store-path store-id]
     (load-file (dj/str-path store-path store-id "load.clj")))
  ([store-path store-id filename]
     (load-file (dj/str-path store-path store-id filename))))

(defn read-clj-store-id
  "

to interface storage of clojure code within a store-id, assume code is
loaded in load.clj and data is read in a data.clj file

"
  ([full-store-id-path]
     (-> (dj.io/file full-store-id-path "data.clj")
         dj.io/eat
         read-string))
  ([store-path store-id]
     (-> (dj.io/file store-path store-id "data.clj")
         dj.io/eat
         read-string))
  ([store-path store-id filename]
     (-> (dj.io/file store-path store-id filename)
         dj.io/eat
         read-string)))

(defn get-data
  "gets clj data depending on store-id type determined by examining folder"
  [store-path store-id]
  ((if (dj.io/exists? (dj.io/file store-path store-id "data.clj"))
     read-clj-store-id
     (if (dj.io/exists? (dj.io/file store-path store-id "load.clj"))
       load-clj-store-id
       (throw (ex-info "data.clj or load.clj file not found"
                       (dj.repl/local-context)))))
   store-path
   store-id))
