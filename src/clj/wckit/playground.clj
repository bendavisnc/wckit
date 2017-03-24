(ns wckit.playground
  (:require
    [wckit.core :refer [create-new]]
    [clojure.java.io :as io]
    [wckit.core]
    )
  (:import
    [wckit.addcore IWCKit]
    )
  (:gen-class))

;ISeq
;[wckit.core WCKitProto]
;[wckit.core IWCKit]
;  [wckit.core :only [fontcolor]])
;(wckit.core WCKitProto)
;[wckit.core IWCKit]
;[wckit.core :refer [WCKitProto]]
;[wckit.core :refer [create-new, WCKitProto]]

;(def starwars-example
;  (->
;    (create-new)
;    ;(.input
;    ;  (->
;    ;    "starwars/starwars.txt"
;    ;    io/resource
;    ;    io/file))
;    (.size 1600 1200)
;    (.maxfontsize 150)
;    (.minfontsize 40)
;    ; (limit 100)
;    (.limit 500)
;    (.backgroundcolor "white")
;    (.fontstyle "impact")
;    (.fontcolor "blue")
;    ; (collision-mode "bobmode")
;    ))

;(def minimal-example
;  (->
;    (create-new)
;    (input
;      (->
;        "minimal/minimal.txt"
;        io/resource
;        io/file))))

;(defn starwars-test []
;  (.spit-png starwars-example "starwarstest.png"))

;(defn minimal-test []
;  (spit-png minimal-example "minimaltest.png"))


;(defn -main
;  [& args]
;  (time
;    ; (minimal-test)))
;    (starwars-test)))

