(ns wckit.playground.core
  (:require
    [wckit.core :refer [map->WCKitProto, from-edn, input, size, max-font-size, min-font-size, limit, background-color, font-style, font-color, spit-png]]
    [clojure.java.io :as io]
    [wckit.core]
    [clojure.edn :as edn]
    )
  (:gen-class))



(defn -main
  [& args]
  (time
    (->
      (wckit.core/create-new)
      ;(from-edn "aurelius/config.edn")
      ;(spit-png "aureliusout.png")
      (from-edn "epictetus/config.edn")
      (spit-png "epictetusout.png")
      )))


