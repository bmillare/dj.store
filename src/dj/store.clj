(ns dj.store
  (:require [dj]
            [dj.io]))

(defn resolve-store-id [store-paths store-id]
  (->> store-paths
       (map #(dj.io/file % store-id))
       (filter dj.io/exists?)
       first))