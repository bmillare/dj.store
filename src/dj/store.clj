(ns dj.store
  (:require [dj]
            [dj.io]))

(defn resolve-store-id [store-paths store-id]
  (->> store-paths
       (map #(dj.io/file % store-id))
       (filter dj.io/exists?)
       first))

(defn free-id [store-path store-namespace]
  (let [folders (dj.io/ls (dj.io/file store-path store-namespace))]
    (if (empty? folders)
      1
      (inc (apply max (map (comp #(Integer/parseInt %) dj.io/get-name) folders))))))

(defn ->store-entry
  "assumes valid initial state for atom env

cb is called with the newly created id

returns a string
"
  [env store-namespace]
  (loop []
    (let [denv @env
          new-id (or (-> denv
                         :dj.store/free-id
                         (get store-namespace))
                     1)]
      (if (compare-and-set! env
                            denv
                            (assoc-in denv
                                      [:dj.store/free-id store-namespace]
                                      (inc new-id)))
        (dj/str-path store-namespace
                     (str new-id))
        (recur)))))