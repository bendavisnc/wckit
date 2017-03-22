(ns wckit.playground
  (:require
    [wckit.core :refer [create-new, input, spit-png, size]]
    [clojure.java.io :as io]
    )
  (:gen-class))

(org.apache.log4j.BasicConfigurator/configure)

(def starwars-example
  (->
    (create-new)
    (input 
      (->
        "starwars/starwars.txt"
        io/resource
        io/file))
    (size 1600 1200)
    ))

(def minimal-example
  (->
    (create-new)
    (input 
      (->
        "minimal/minimal.txt"
        io/resource
        io/file))))

(defn starwars-test []
  (spit-png starwars-example "starwarstest.png"))

(defn minimal-test []
  (spit-png minimal-example "minimaltest.png"))


(defn -main
  [& args]
  (time
    ; (minimal-test)))
    (starwars-test)))

