(ns wckit.playground
  (:require
    [wckit.core :refer [create-new, input, spit-png, size, font-color, max-font-size, min-font-size, font-style, limit, collision-mode, background-color]]
    [clojure.java.io :as io]
    )
  (:import (com.kennycason.kumo CollisionMode))
  (:gen-class))

(def starwars-example
  (->
    (create-new)
    (input 
      (->
        "starwars/starwars.txt"
        io/resource
        io/file))
    (size 1600 1200)
    ; (max-font-size 250)
    (max-font-size 150)
    (min-font-size 40)
    ; (limit 100)
    (limit 500)
    (background-color "white")
    (font-style "impact")
    (font-color "blue" )
    ; (collision-mode "bobmode")
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

