(ns dj.store.code
  (:require [dj]
            [dj.io]))

(defn load-store-id [store-path store-id]
  (-> (if (re-matches #"(?i)\.clj"
                      (dj/substring store-id -4))
        (dj.io/file store-path store-id)
        (dj.io/file store-path
                    store-id
                    "load.clj"))
      dj.io/get-path
      load-file))