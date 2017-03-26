(ns wckit.playground.core
  (:require
    [wckit.core :refer [from-edn, spit-png]]
    [wckit.core]
    )
  (:gen-class))



(defn -main
  [& args]
  (time
    (->
      (wckit.core/create-new)
      (spit-png "./fuckidk.png")
      ;(from-edn "epictetus/config.edn")
      ;(spit-png "epictetusout.png")
      )))


