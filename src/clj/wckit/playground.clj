(ns wckit.playground
  (:require
    [wckit.core :refer [create-new, input, size, max-font-size, min-font-size, limit, background-color, font-style, font-color, spit-png]]
    [clojure.java.io :as io]
    [wckit.core]
    )
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
    (max-font-size 100)
    (min-font-size 40)
    ;(limit 100)
    (limit 500)
    (background-color "black")
    (font-style "impact")
    ;(font-color "blue")
    ; (collision-mode "bobmode")
    ))

;(def minimal-example
;  (->
;    (create-new)
;    (input
;      (->
;        "minimal/minimal.txt"
;        io/resource
;        io/file))))

(defn starwars-test []
  (spit-png starwars-example "starwarstest.png"))

;(defn minimal-test []
;  (spit-png minimal-example "minimaltest.png"))


(defn -main
  [& args]
  (time
;    ; (minimal-test)))
    (starwars-test)))

