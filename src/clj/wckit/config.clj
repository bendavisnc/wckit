(ns wckit.config
  (:require
    [clojure.java.io :as io]
    [clojure.edn :as edn]
  ))

(defn load-resource-config [config-path]
  (edn/read-string
    (slurp
      (or
        (io/resource config-path)
        (throw (Exception. "No config found."))))))
